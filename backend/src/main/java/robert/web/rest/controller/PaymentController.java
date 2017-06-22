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
import robert.db.svc.api.PaymentService;
import robert.web.rest.dto.FeeDTO;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;
import robert.web.svc.UserInfoProvider;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	private final UserInfoProvider userInfoProvider;

	private final PaymentService paymentService;

	public PaymentController(UserInfoProvider userInfoProvider, PaymentService paymentService) {
		this.userInfoProvider = userInfoProvider;
		this.paymentService = paymentService;
	}

	@PostMapping("/add-assets-to-user")
	@ResponseStatus(HttpStatus.OK)
	public void addAssetToTheUser(@RequestBody PaymentDTO borrowerInfo) {
		paymentService.addDebtor(userInfoProvider.getUserDetails()
				.getUserId(), borrowerInfo);
	}

	@GetMapping("/my-debtors")
	public List<PaymentDTO> getMyDebtors() {
		List<Asset> debtors = paymentService.findUserDebtors(userInfoProvider.getUserDetails()
				.getUserId());
		return PaymentAssembler.convertToPaymentDTOs(debtors);
	}

	@GetMapping("/my-debts")
	public List<PaymentDTO> getMyDebts() {
		List<Asset> userDebts = paymentService.findUserDebts(userInfoProvider.getUserDetails()
				.getUserId());
		return PaymentAssembler.convertToPaymentDTOs(userDebts);
	}

	@DeleteMapping("/cancel-debt/{id}/")
	public HttpStatus cancelDebt(@PathVariable long id) {
		paymentService.cancelDebt(id, userInfoProvider.getUserDetails()
				.getUserId());
		return HttpStatus.OK;
	}

	@PostMapping("/add-mutual-payment")
	public HttpStatus addNewMutualPayment(@RequestBody PaymentDTO paymentDTO) {
		paymentService.addMutualPayment(paymentDTO);
		return HttpStatus.OK;
	}

	@PostMapping("/add-fee/{id}/{amount}/")
	public HttpStatus addFeeToMutualPayment(@PathVariable long id, @PathVariable double amount) {
		paymentService.addUserFeeToPayment(userInfoProvider.getUserDetails()
				.getUserId(), id, amount);
		return HttpStatus.OK;
	}

	@GetMapping("/mutual-payment-fees/{id}/")
	public List<FeeDTO> getFeesOfMutualPayment(@PathVariable long id) {
		Set<Fee> fees = paymentService.getFeesForMutualPayment(id);
		return PaymentAssembler.convertFeesToDTOs(fees);
	}

	@GetMapping("/mutual-payments")
	public List<PaymentDTO> getAllMutualPayments() {
		List<MutualPayment> allMutualPayments = paymentService.getAllMutualPayments();
		return PaymentAssembler.convertToMutualPaymentDTO(allMutualPayments);
	}

	@DeleteMapping("/delete-my-fees/{id}/")
	public HttpStatus deleteMyFees(@PathVariable long id) {
		paymentService.deleteUserFees(userInfoProvider.getUserDetails()
				.getUserId(), id);
		return HttpStatus.OK;
	}

	@DeleteMapping("/delete-mutual-payment/{id}/")
	public HttpStatus deleteMutualPayment(@PathVariable long id) {
		paymentService.deleteMutualPayment(id);
		return HttpStatus.OK;
	}

	@GetMapping("/money-balance")
	public double getMoneyBalance() {
		return paymentService.getUserDebtBalance(userInfoProvider.getUserDetails()
				.getUserId());
	}

	@GetMapping("/money-balance-with-other-user/{id}/")
	public double getMoneyBalanceWithOtherUser(@PathVariable long id) {
		return paymentService.getMoneyBalanceWithOtherUser(userInfoProvider.getUserDetails()
				.getUserId(), id);
	}

}
