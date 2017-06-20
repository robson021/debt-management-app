package robert.db.svc;

import java.util.List;
import java.util.Set;

import robert.db.entities.Asset;
import robert.db.entities.BasicEntity;
import robert.db.entities.Fee;
import robert.db.entities.MutualPayment;
import robert.db.entities.User;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.UserInfoDTO;

public interface DatabaseService {
	<T> T saveEntity(BasicEntity entity, Class<T> castClass);

	BasicEntity saveEntity(BasicEntity entity);

	void saveNewUser(UserInfoDTO userDTO);

	User findUserByEmail(String email);

	User findUserById(long id);

	List<Asset> findUserDebts(long borrowerId);

	List<Asset> findUserDebtors(long userId);

	void cancelDebt(long assetId, long userId);

	void addDebtor(long lenderId, PaymentDTO borrowerInfo);

	List<User> findOtherUsersExceptGiven(long userId);

	void addMutualPayment(PaymentDTO paymentDTO);

	void addUserFeeToPayment(long userId, long mutualPaymentId, double feeAmount);

	Set<Fee> getFeesForMutualPayment(long mpaymentId);

	List<MutualPayment> getAllMutualPayments();

	void deleteUserFees(long userId, long mutualPaymentId);

	void deleteMutualPayment(long mutualPaymentId);

	double getUserDebtBalance(long userId);

	double getMoneyBalanceWithOtherUser(long userId, long otherUserId);
}
