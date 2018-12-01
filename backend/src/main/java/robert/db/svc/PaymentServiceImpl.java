package robert.db.svc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robert.annotations.cache.payments.*;
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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final EntityManager em;

    private final AssetRepository assetRepository;

    private final UserRepository userRepository;

    private final MutualPaymentRepository mutualPaymentRepository;

    @Override
    @DebtsCache
    @Transactional(readOnly = true)
    public List<Asset> findUserDebts(long borrowerId) {
        return em.createQuery("from Asset a where a.borrowerId = :id order by creationDate desc", Asset.class)
                .setParameter("id", borrowerId)
                .getResultList();
    }

    @Override
    @DebtorsCache
    @Transactional(readOnly = true)
    public List<Asset> findUserDebtors(long userId) {
        return em.createQuery("from Asset a where a.user.id = :id order by creationDate desc", Asset.class)
                .setParameter("id", userId)
                .getResultList();
    }

    @Override
    @PaymentsCacheEvict
    public void cancelDebt(long assetId, long userId) {
        if (!doesAssetBelongToUser(assetId, userId))
            throw new BadParameterException("User tried to cancel not his debt");

        Asset asset = em.createQuery("from Asset a where a.id = :id", Asset.class)
                .setParameter("id", assetId)
                .getSingleResult();

        em.remove(asset);

        User lender = asset.getUser();
        log.info("Cancelled debt: '{} {}' --> '{} {}', amount: '{}', desc: '{}'", //
                lender.getName(), lender.getSurname(), asset.getBorrowerName(), asset.getBorrowerSurname(), asset.getAmount(), asset.getDescription());
    }

    @Override
    @PaymentsCacheEvict
    public void addDebtor(long lenderId, PaymentDTO borrowerInfo) {
        User lender = userRepository.findById(lenderId).get();
        Asset asset = PaymentAssembler.paymentDtoToAsset(borrowerInfo);
        asset.setUser(lender);
        asset.setCreationDate(new Date());
        assetRepository.save(asset);
        log.info("Added debt '{} {}' --> '{} {}': {}, {}$", //
                lender.getName(), lender.getSurname(), asset.getBorrowerName(), asset.getBorrowerSurname(), asset.getDescription(), asset.getAmount()
        );
    }

    @Override
    public void addMutualPayment(PaymentDTO paymentDTO) {
        MutualPayment payment = PaymentAssembler.convertMutualPaymentDTO(paymentDTO);
        mutualPaymentRepository.save(payment);
        log.info("New mutual payment: '{}'; amount: {}$", payment.getDescription(), payment.getAmount());
    }

    @Override
    public void addUserFeeToPayment(long userId, long mutualPaymentId, BigDecimal feeAmount) {
        MutualPayment mutualPayment = mutualPaymentRepository.findById(mutualPaymentId).get();
        Fee fee = new Fee();
        fee.setUser(userRepository.findById(userId).get());
        fee.setPayedFee(feeAmount);
        fee.setMutualPayment(mutualPayment);

        mutualPayment.addFee(fee);
        mutualPaymentRepository.save(mutualPayment);
        log.info("Added fee for mutual payment: '{}'; amount: {}$; user: '{}'", //
                fee.getMutualPayment().getDescription(), feeAmount, fee.getUser().getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Fee> getFeesForMutualPayment(long mutualPaymentId) {
        MutualPayment mutualPayment = mutualPaymentRepository.findById(mutualPaymentId).get();
        return mutualPayment.getPayedFees();
    }

    @Override
    @Transactional(readOnly = true)
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

        mutualPaymentRepository.deleteById(mutualPaymentId);
    }

    @Override
    @DebtBalanceCache
    @Transactional(readOnly = true)
    public BigDecimal getUserDebtBalance(long userId) {
        BigDecimal debtorsSum = em.createQuery("select sum(amount) from Asset a where a.user.id = :id", BigDecimal.class)
                .setParameter("id", userId)
                .getSingleResult();

        BigDecimal userSum = em.createQuery("select sum(amount) from Asset a where a.borrowerId = :id", BigDecimal.class)
                .setParameter("id", userId)
                .getSingleResult();

        return getDifference(debtorsSum, userSum);
    }

    @Override
    @MoneyBalanceCache
    @Transactional(readOnly = true)
    public BigDecimal getMoneyBalanceWithOtherUser(long userId, long otherUserId) {
        BigDecimal otherUserDebts = getSingleResult(userId, otherUserId);
        BigDecimal userDebts = getSingleResult(otherUserId, userId);
        return getDifference(otherUserDebts, userDebts);
    }

    private BigDecimal getSingleResult(long userId, long otherUserId) {
        return em.createQuery("select sum(amount) from Asset a where a.user.id = :id1 and borrowerId = :id2", BigDecimal.class)
                .setParameter("id1", userId)
                .setParameter("id2", otherUserId)
                .getSingleResult();
    }

    private boolean doesAssetBelongToUser(long assetId, long userId) {
        Asset asset = em.getReference(Asset.class, assetId);
        return userId == asset.getUser().getId();
    }

    private BigDecimal getDifference(BigDecimal x, BigDecimal y) {
        BigDecimal a = x == null ? BigDecimal.ZERO : x;
        BigDecimal b = y == null ? BigDecimal.ZERO : y;
        return a.subtract(b);
    }
}
