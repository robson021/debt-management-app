package robert.db.svc.api;

import java.util.List;
import java.util.Set;

import robert.db.entities.Asset;
import robert.db.entities.Fee;
import robert.db.entities.MutualPayment;
import robert.web.rest.dto.PaymentDTO;

public interface PaymentService {

	List<Asset> findUserDebts(long borrowerId);

	List<Asset> findUserDebtors(long userId);

	void cancelDebt(long assetId, long userId);

	void addDebtor(long lenderId, PaymentDTO borrowerInfo);

	void addMutualPayment(PaymentDTO paymentDTO);

	void addUserFeeToPayment(long userId, long mutualPaymentId, double feeAmount);

	Set<Fee> getFeesForMutualPayment(long mpaymentId);

	List<MutualPayment> getAllMutualPayments();

	void deleteUserFees(long userId, long mutualPaymentId);

	void deleteMutualPayment(long mutualPaymentId);

	double getUserDebtBalance(long userId);

	double getMoneyBalanceWithOtherUser(long userId, long otherUserId);
}
