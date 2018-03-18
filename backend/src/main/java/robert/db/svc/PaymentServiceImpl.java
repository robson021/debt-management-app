package robert.db.svc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robert.db.entities.Asset;
import robert.db.entities.Fee;
import robert.db.entities.MutualPayment;
import robert.db.entities.User;
import robert.db.repo.AssetRepository;
import robert.db.repo.MutualPaymentRepository;
import robert.db.repo.UserRepository;
import robert.db.svc.api.PaymentService;
import robert.exeptions.BadParameterException;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final EntityManager em;

    private final AssetRepository assetRepository;

    private final UserRepository userRepository;

    private final MutualPaymentRepository mutualPaymentRepository;

    public PaymentServiceImpl(EntityManager em, AssetRepository assetRepository, UserRepository userRepository, MutualPaymentRepository mutualPaymentRepository) {
        this.em = em;
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
        this.mutualPaymentRepository = mutualPaymentRepository;
    }

    @Override
    public List<Asset> findUserDebts(long borrowerId) {
        return em.createQuery("from Asset a where a.borrowerId = :id order by a.user.surname, a.amount desc", Asset.class)
                .setParameter("id", borrowerId)
                .getResultList();
    }

    @Override
    public List<Asset> findUserDebtors(long userId) {
        return em.createQuery("from Asset a where a.user.id = :id order by a.creationDate asc, a.borrowerSurname, a.amount desc", Asset.class)
                .setParameter("id", userId)
                .getResultList();
    }

    @Override
    public void cancelDebt(long assetId, long userId) {
        if (!doesAssetBelongToUser(assetId, userId))
            throw new BadParameterException("User tried to cancel not his debt");

        Asset asset = em.createQuery("from Asset a where a.id = :id", Asset.class)
                .setParameter("id", assetId)
                .getSingleResult();

        log.info("Canceling debt: borrower: '{} {}', lender: '{} {}', amount: '{}', desc: '{}'", //
                asset.getBorrowerSurname(), asset.getBorrowerName(), asset.getUser().getSurname(), asset.getUser().getName(), asset.getAmount(), asset.getDescription());

        em.remove(asset);
    }

    @Override
    public void addDebtor(long lenderId, PaymentDTO borrowerInfo) {
        User lender = userRepository.findOne(lenderId);
        Asset asset = PaymentAssembler.paymentDtoToAsset(borrowerInfo);
        asset.setUser(lender);
        asset.setCreationDate(new Date());
        assetRepository.save(asset);
        log.info("Added debt for '{} {}' (lender '{} {}'): {}, {}$", //
                asset.getBorrowerName(), asset.getBorrowerSurname(), lender.getName(), lender.getSurname(), asset.getDescription(), asset.getAmount()
        );
    }

    @Override
    public void addMutualPayment(PaymentDTO paymentDTO) {
        MutualPayment payment = PaymentAssembler.convertMutualPaymentDTO(paymentDTO);
        mutualPaymentRepository.save(payment);
    }

    @Override
    public void addUserFeeToPayment(long userId, long mutualPaymentId, double feeAmount) {
        MutualPayment mutualPayment = mutualPaymentRepository.findOne(mutualPaymentId);
        Fee fee = new Fee();
        fee.setUser(userRepository.findOne(userId));
        fee.setPayedFee(feeAmount);
        fee.setMutualPayment(mutualPayment);

        mutualPayment.addFee(fee);
        mutualPaymentRepository.save(mutualPayment);
    }

    @Override
    public Set<Fee> getFeesForMutualPayment(long mutualPaymentId) {
        MutualPayment mutualPayment = mutualPaymentRepository.findOne(mutualPaymentId);
        return mutualPayment.getPayedFees();
    }

    @Override
    public List<MutualPayment> getAllMutualPayments() {
        return mutualPaymentRepository.findAll();
    }

    @Override
    public void deleteUserFees(long userId, long mutualPaymentId) {
        em.createQuery("delete from Fee f where f.user.id = :uid and f.mutualPayment.id = :pid")
                .setParameter("uid", userId)
                .setParameter("pid", mutualPaymentId)
                .executeUpdate();
    }

    @Override
    public void deleteMutualPayment(long mutualPaymentId) {
        em.createQuery("delete from Fee f where f.mutualPayment.id = :pid")
                .setParameter("pid", mutualPaymentId)
                .executeUpdate();

        mutualPaymentRepository.delete(mutualPaymentId);
    }

    @Override
    public double getUserDebtBalance(long userId) {
        Double debtorsSum = em.createQuery("select sum(amount) from Asset a where a.user.id = :id", Double.class)
                .setParameter("id", userId)
                .getSingleResult();

        Double userSum = em.createQuery("select sum(amount) from Asset a where a.borrowerId = :id", Double.class)
                .setParameter("id", userId)
                .getSingleResult();

        return getDifference(debtorsSum, userSum);
    }

    @Override
    public double getMoneyBalanceWithOtherUser(long userId, long otherUserId) {
        final String query = "select sum(amount) from Asset a where a.user.id = :id1 and borrowerId = :id2";

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
        Asset asset = em.getReference(Asset.class, assetId);
        return userId == asset.getUser().getId();
    }

    private double getDifference(Double x, Double y) {
        double a = x == null ? .0 : x;
        double b = y == null ? .0 : y;

        return a - b;
    }
}
