package robert.db;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import robert.db.entities.Asset;
import robert.db.entities.BasicEntity;
import robert.db.entities.Fee;
import robert.db.entities.MutualPayment;
import robert.db.entities.User;
import robert.db.repo.AssetRepository;
import robert.db.repo.FeeRepository;
import robert.db.repo.MutualPaymentRepository;
import robert.db.repo.UniversalRepository;
import robert.db.repo.UserRepository;
import robert.exeptions.BadParameterException;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;

@SuppressWarnings("ALL")
@Component
@Transactional
public class UniversalDao {

	private static final Logger log = Logger.getLogger(UniversalDao.class);

	private final UserRepository userRepository;

	private final AssetRepository assetRepository;

	private final UniversalRepository universalRepository;

	private final FeeRepository feeRepository;

	private final MutualPaymentRepository mutualPaymentRepository;

	private final EntityManager em;

	@Autowired
	public UniversalDao(UserRepository userRepository, AssetRepository assetRepository, UniversalRepository universalRepository, FeeRepository feeRepository, MutualPaymentRepository mutualPaymentRepository, EntityManager em) {
		this.userRepository = userRepository;
		this.assetRepository = assetRepository;
		this.universalRepository = universalRepository;
		this.feeRepository = feeRepository;
		this.mutualPaymentRepository = mutualPaymentRepository;
		this.em = em;
	}

	public <T> T saveEntity(BasicEntity entity, Class<T> castClass) {
		BasicEntity saved = universalRepository.save(entity);
		return castClass.cast(saved);
	}

	public List<Asset> findUserDebts(Long borrowerId) {
		List<Asset> debts = em.createQuery("from Asset a where a.borrowerId = :id", Asset.class)
				.setParameter("id", borrowerId)
				.getResultList();

		return debts;
	}

	public Set<Asset> findUserDebtors(Long userId) {
		Set<Asset> assets = userRepository.findOne(userId)
				.getAssets();

		return assets;
	}

	public void cancelDebt(Long assetId, Long userId) throws BadParameterException {
		if (!doesAssetBelongToUser(assetId, userId))
			throw new BadParameterException("User tried to cancel not his debt");

		em.createQuery("delete from Asset a where a.id = :id")
				.setParameter("id", assetId)
				.executeUpdate();
	}

	public void addDebtor(Long lenderId, PaymentDTO borrowerInfo) {
		User lender = userRepository.findOne(lenderId);
		Asset asset = PaymentAssembler.paymentDtoToAsset(borrowerInfo);
		//lender.addAsset(asset);
		asset.setUser(lender);
		assetRepository.save(asset);
	}

	public List<User> findOtherUsersExceptGiven(Long userId) {
		return em.createQuery("from User u where u.id != :id", User.class)
				.setParameter("id", userId)
				.getResultList();
	}

	public void addMutualPayment(PaymentDTO paymentDTO) {
		MutualPayment payment = PaymentAssembler.convertMutualPaymentDTO(paymentDTO);
		mutualPaymentRepository.save(payment);
	}

	public void addUserFeeToPayment(Long userId, Long mutualPaymentId, Double feeAmount) {
		MutualPayment mutualPayment = mutualPaymentRepository.findOne(mutualPaymentId);
		Fee fee = new Fee();
		fee.setUser(userRepository.findOne(userId));
		fee.setPayedFee(feeAmount);
		fee.setMutualPayment(mutualPayment);

		mutualPayment.addFee(fee);
		mutualPaymentRepository.save(mutualPayment);
	}

	public Set<Fee> getFeesForMutualPayment(Long mpaymentId) {
		MutualPayment mutualPayment = mutualPaymentRepository.findOne(mpaymentId);
		return mutualPayment.getPayedFees();
	}

	private boolean doesAssetBelongToUser(Long assetId, Long userId) {
		Long id = assetRepository.findOne(assetId)
				.getUser()
				.getId();

		return id.equals(userId);
	}
}
