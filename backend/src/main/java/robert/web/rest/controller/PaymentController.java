package robert.web.rest.controller;

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

import robert.db.entities.Asset;
import robert.db.entities.Fee;
import robert.db.entities.MutualPayment;
import robert.db.svc.DatabaseService;
import robert.web.rest.dto.FeeDTO;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;
import robert.web.svc.UserInfoProvider;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	private final DatabaseService databaseService;

	private final UserInfoProvider userInfoProvider;

	public PaymentController(DatabaseService databaseService, UserInfoProvider userInfoProvider) {
		this.databaseService = databaseService;
		this.userInfoProvider = userInfoProvider;
	}

	@PostMapping("/add-assets-to-user")
	@ResponseStatus(HttpStatus.OK)
	public void addAssetToTheUser(@RequestBody PaymentDTO borrowerInfo) {
		databaseService.addDebtor(userInfoProvider.getUserDetails()
				.getUserId(), borrowerInfo);
	}

	@GetMapping("/my-debtors")
	public List<PaymentDTO> getMyDebtors() {
		List<Asset> debtors = databaseService.findUserDebtors(userInfoProvider.getUserDetails()
				.getUserId());
		return PaymentAssembler.convertToPaymentDTOs(debtors);
	}

	@GetMapping("/my-debts")
	public List<PaymentDTO> getMyDebts() {
		List<Asset> userDebts = databaseService.findUserDebts(userInfoProvider.getUserDetails()
				.getUserId());
		return PaymentAssembler.convertToPaymentDTOs(userDebts);
	}

	@DeleteMapping("/cancel-debt/{id}/")
	public HttpStatus cancelDebt(@PathVariable long id) {
		databaseService.cancelDebt(id, userInfoProvider.getUserDetails()
				.getUserId());
		return HttpStatus.OK;
	}

	@PostMapping("/add-mutual-payment")
	public HttpStatus addNewMutualPayment(@RequestBody PaymentDTO paymentDTO) {
		databaseService.addMutualPayment(paymentDTO);
		return HttpStatus.OK;
	}

	@PostMapping("/add-fee/{id}/{amount}/")
	public HttpStatus addFeeToMutualPayment(@PathVariable long id, @PathVariable double amount) {
		databaseService.addUserFeeToPayment(userInfoProvider.getUserDetails()
				.getUserId(), id, amount);
		return HttpStatus.OK;
	}

	@GetMapping("/mutual-payment-fees/{id}/")
	public List<FeeDTO> getFeesOfMutualPayment(@PathVariable long id) {
		Set<Fee> fees = databaseService.getFeesForMutualPayment(id);
		return PaymentAssembler.convertFeesToDTOs(fees);
	}

	@GetMapping("/mutual-payments")
	public List<PaymentDTO> getAllMutualPayments() {
		List<MutualPayment> allMutualPayments = databaseService.getAllMutualPayments();
		return PaymentAssembler.convertToMutualPaymentDTO(allMutualPayments);
	}

	@DeleteMapping("/delete-my-fees/{id}/")
	public HttpStatus deleteMyFees(@PathVariable long id) {
		databaseService.deleteUserFees(userInfoProvider.getUserDetails()
				.getUserId(), id);
		return HttpStatus.OK;
	}

	@DeleteMapping("/delete-mutual-payment/{id}/")
	public HttpStatus deleteMutualPayment(@PathVariable long id) {
		databaseService.deleteMutualPayment(id);
		return HttpStatus.OK;
	}

	@GetMapping("/money-balance")
	public double getMoneyBalance() {
		return databaseService.getUserDebtBalance(userInfoProvider.getUserDetails()
				.getUserId());
	}

	@GetMapping("/money-balance-with-other-user/{id}/")
	public double getMoneyBalanceWithOtherUser(@PathVariable long id) {
		return databaseService.getMoneyBalanceWithOtherUser(userInfoProvider.getUserDetails()
				.getUserId(), id);
	}

}
