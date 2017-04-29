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

    public void saveNewUser(UserInfoDTO userDTO) {
        User user = UserAssembler.convertDtoToUser(userDTO);
        validateUserEmailAndPassword(user);
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    public List<Asset> findUserDebts(Long borrowerId) {
        List<Asset> debts = em.createQuery("from Asset a where a.borrowerId = :id", Asset.class)
                .setParameter("id", borrowerId)
                .getResultList();

        return debts;
    }

    public Set<Asset> findUserDebtors(Long userId) {
        return userRepository.findOne(userId)
                .getAssets();
    }

    public void cancelDebt(Long assetId, Long userId) throws BadParameterException {
        if ( !doesAssetBelongToUser(assetId, userId) )
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

    public List<MutualPayment> getAllMutualPayments() {
        return mutualPaymentRepository.findAll();
    }

    public void deleteUserFees(Long userId, Long mutualPaymentId) {
        em.createQuery("delete from Fee f where f.user.id = :uid and f.mutualPayment.id = :pid")
                .setParameter("uid", userId)
                .setParameter("pid", mutualPaymentId)
                .executeUpdate();
    }

    public void deleteMutualPayment(Long mutualPaymentId) {
        em.createQuery("delete from Fee f where f.mutualPayment.id = :pid")
                .setParameter("pid", mutualPaymentId)
                .executeUpdate();

        mutualPaymentRepository.delete(mutualPaymentId);
    }

    private boolean doesAssetBelongToUser(Long assetId, Long userId) {
        Long id = assetRepository.findOne(assetId)
                .getUser()
                .getId();

        return id.equals(userId);
    }

    private void validateUserEmailAndPassword(User user) {
        boolean isValid = Validation.VALID_PASSWORD_REGEX.matcher(user.getPassword())
                .find() && Validation.VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail())
                .find();

        if ( !isValid ) {
            throw new AuthException("Invalid password or email pattern");
        }

    }
}
