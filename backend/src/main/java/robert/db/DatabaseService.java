package robert.db;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import robert.db.entities.Asset;
import robert.db.entities.BasicEntity;
import robert.db.entities.Fee;
import robert.db.entities.MutualPayment;
import robert.db.entities.User;
import robert.db.entities.Validation;
import robert.db.repo.AssetRepository;
import robert.db.repo.MutualPaymentRepository;
import robert.db.repo.UniversalRepository;
import robert.db.repo.UserRepository;
import robert.exeptions.AuthException;
import robert.exeptions.BadParameterException;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.PaymentAssembler;
import robert.web.rest.dto.asm.UserAssembler;

@Service
@Transactional
@AllArgsConstructor
public class DatabaseService {

	private final UserRepository userRepository;

	private final AssetRepository assetRepository;

	private final UniversalRepository universalRepository;

	private final MutualPaymentRepository mutualPaymentRepository;

	private final EntityManager em;

	public <T> T saveEntity(BasicEntity entity, Class<T> castClass) {
		BasicEntity saved = universalRepository.save(entity);
		return castClass.cast(saved);
	}

	public void saveEntity(BasicEntity entity) {
		universalRepository.save(entity);
	}

	public void saveNewUser(UserInfoDTO userDTO) {
		User user = UserAssembler.convertDtoToUser(userDTO);
		validateUserEmailAndPassword(user);
		userRepository.save(user);
	}

	public User findUserByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}

	public User findUserById(long id) {
		return userRepository.findOne(id);
	}

	public List<Asset> findUserDebts(long borrowerId) {
		List<Asset> debts = em.createQuery("from Asset a where a.borrowerId = :id order by a.user.surname", Asset.class)
				.setParameter("id", borrowerId)
				.getResultList();

		return debts;
	}

	public List<Asset> findUserDebtors(long userId) {
		return em.createQuery("from Asset a where a.user.id = :id order by a.borrowerSurname", Asset.class)
				.setParameter("id", userId)
				.getResultList();
	}

	public void cancelDebt(long assetId, long userId) throws BadParameterException {
		if ( !doesAssetBelongToUser(assetId, userId) )
			throw new BadParameterException("User tried to cancel not his debt");

		em.createQuery("delete from Asset a where a.id = :id")
				.setParameter("id", assetId)
				.executeUpdate();
	}

	public void addDebtor(long lenderId, PaymentDTO borrowerInfo) {
		User lender = userRepository.findOne(lenderId);
		Asset asset = PaymentAssembler.paymentDtoToAsset(borrowerInfo);
		asset.setUser(lender);
		assetRepository.save(asset);
	}

	public List<User> findOtherUsersExceptGiven(long userId) {
		return em.createQuery("from User u where u.id != :id order by u.surname", User.class)
				.setParameter("id", userId)
				.getResultList();
	}

	public void addMutualPayment(PaymentDTO paymentDTO) {
		MutualPayment payment = PaymentAssembler.convertMutualPaymentDTO(paymentDTO);
		mutualPaymentRepository.save(payment);
	}

	public void addUserFeeToPayment(long userId, long mutualPaymentId, double feeAmount) {
		MutualPayment mutualPayment = mutualPaymentRepository.findOne(mutualPaymentId);
		Fee fee = new Fee();
		fee.setUser(userRepository.findOne(userId));
		fee.setPayedFee(feeAmount);
		fee.setMutualPayment(mutualPayment);

		mutualPayment.addFee(fee);
		mutualPaymentRepository.save(mutualPayment);
	}

	public Set<Fee> getFeesForMutualPayment(long mpaymentId) {
		MutualPayment mutualPayment = mutualPaymentRepository.findOne(mpaymentId);
		return mutualPayment.getPayedFees();
	}

	public List<MutualPayment> getAllMutualPayments() {
		return mutualPaymentRepository.findAll();
	}

	public void deleteUserFees(long userId, long mutualPaymentId) {
		em.createQuery("delete from Fee f where f.user.id = :uid and f.mutualPayment.id = :pid")
				.setParameter("uid", userId)
				.setParameter("pid", mutualPaymentId)
				.executeUpdate();
	}

	public void deleteMutualPayment(long mutualPaymentId) {
		em.createQuery("delete from Fee f where f.mutualPayment.id = :pid")
				.setParameter("pid", mutualPaymentId)
				.executeUpdate();

		mutualPaymentRepository.delete(mutualPaymentId);
	}

	public double getUserDebtBalance(long userId) {
		Double debtorsSum = em.createQuery("select sum(amount) from Asset a where a.user.id = :id", Double.class)
				.setParameter("id", userId)
				.getSingleResult();

		Double userSum = em.createQuery("select sum(amount) from Asset a where a.borrowerId = :id", Double.class)
				.setParameter("id", userId)
				.getSingleResult();

		return getDifference(debtorsSum, userSum);
	}

	public double getMoneyBalanceWithOtherUser(long userId, long otherUserId) {
		String query = "select sum(amount) from Asset a where a.user.id = :id1 and borrowerId = :id2";

		Double otherUserDebts = em.createQuery(query, Double.class)
				.setParameter("id1", userId)
				.setParameter("id2", otherUserId)
				.getSingleResult();

		Double userDebts = em.createQuery(query, Double.class)
				.setParameter("id1", otherUserId)
				.setParameter("id2", userId)
				.getSingleResult();

		return getDifference(otherUserDebts, userDebts);
	}

	private boolean doesAssetBelongToUser(long assetId, long userId) {
		long id = assetRepository.findOne(assetId)
				.getUser()
				.getId();

		return id == userId;
	}

	private void validateUserEmailAndPassword(User user) {
		boolean isValid = Validation.VALID_PASSWORD_REGEX.matcher(user.getPassword())
				.find() && Validation.VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail())
				.find();

		if ( !isValid ) {
			throw new AuthException("Invalid password or email pattern");
		}

	}

	private double getDifference(Double x, Double y) {
		if ( x == null )
			x = .0;

		if ( y == null )
			y = .0;

		return x - y;
	}
}
