package robert.web.rest.svc;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import robert.db.DatabaseService;
import robert.db.entities.Asset;
import robert.db.entities.Fee;
import robert.db.entities.MutualPayment;
import robert.web.request.data.UserDataProvider;
import robert.web.rest.dto.FeeDTO;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController {

    private final DatabaseService dbService;

    private final UserDataProvider userDataProvider;

	@PostMapping("/add-assets-to-user")
	@ResponseStatus(HttpStatus.OK)
    public void addAssetToTheUser(@RequestBody PaymentDTO borrowerInfo) {
        dbService.addDebtor(userDataProvider.getUserId(), borrowerInfo);
    }

	@GetMapping("/my-debtors")
	public List<PaymentDTO> getMyDebtors() {
        Set<Asset> debtors = dbService.findUserDebtors(userDataProvider.getUserId());
        return PaymentAssembler.convertToPaymentDTOs(debtors);
    }

	@GetMapping("/my-debts")
	public List<PaymentDTO> getMyDebts() {
        List<Asset> userDebts = dbService.findUserDebts(userDataProvider.getUserId());
        return PaymentAssembler.convertToPaymentDTOs(userDebts);
    }

	@DeleteMapping("/cancel-debt/{id}/")
	public HttpStatus cancelDebt(@PathVariable Long assetId) {
		dbService.cancelDebt(assetId, userDataProvider.getUserId());
        return HttpStatus.OK;
    }

	@PostMapping("/add-mutual-payment")
	public HttpStatus addNewMutualPayment(@RequestBody PaymentDTO paymentDTO) {
        dbService.addMutualPayment(paymentDTO);
        return HttpStatus.OK;
    }

	@PostMapping("/add-fee/{id}/{amount}/")
	public HttpStatus addFeeToMutualPayment(@PathVariable Long paymentId, @PathVariable Double fee) {
		dbService.addUserFeeToPayment(userDataProvider.getUserId(), paymentId, fee);
        return HttpStatus.OK;
    }

	@GetMapping("/mutual-payment-fees/{id}/")
	public List<FeeDTO> getFeesOfMutualPayment(@PathVariable Long id) {
        Set<Fee> fees = dbService.getFeesForMutualPayment(id);
        return PaymentAssembler.convertFeesToDTOs(fees);
    }

	@GetMapping("/mutual-payments")
	public List<PaymentDTO> getAllMutualPayments() {
        List<MutualPayment> allMutualPayments = dbService.getAllMutualPayments();
        return PaymentAssembler.convertToMutualPaymentDTO(allMutualPayments);
    }

	@DeleteMapping("/delete-my-fees/{id}/")
	public HttpStatus deleteMyFees(@PathVariable Long paymentId) {
        dbService.deleteUserFees(userDataProvider.getUserId(), paymentId);
        return HttpStatus.OK;
    }

	@DeleteMapping("/delete-mutual-payment/{id}/")
	public HttpStatus deleteMutualPayment(@PathVariable Long mutualPayment) {
        dbService.deleteMutualPayment(mutualPayment);
        return HttpStatus.OK;
    }

	@GetMapping("/money-balance")
	public double getMoneyBalance() {
        return dbService.getUserDebtBalance(userDataProvider.getUserId());
    }

	@GetMapping("/money-balance-with-other-user/{id}/")
	public double getMoneyBalanceWithOtherUser(@PathVariable Long otherUserId) {
		return dbService.getMoneyBalanceWithOtherUser(userDataProvider.getUserId(), otherUserId);
	}

}
