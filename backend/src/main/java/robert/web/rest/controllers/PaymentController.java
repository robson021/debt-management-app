package robert.web.rest.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robert.db.entities.Asset;
import robert.db.entities.Fee;
import robert.db.entities.MutualPayment;
import robert.db.svc.api.PaymentService;
import robert.web.rest.dto.FeeDTO;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;
import robert.web.svc.api.UserDetailsProvider;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	private final UserDetailsProvider userDetailsProvider;

	private final PaymentService paymentService;

	public PaymentController(UserDetailsProvider userDetailsProvider, PaymentService paymentService) {
		this.userDetailsProvider = userDetailsProvider;
		this.paymentService = paymentService;
	}

	@PostMapping("/add-assets-to-user")
	@ResponseStatus(HttpStatus.OK)
	public void addAssetToTheUser(@RequestBody PaymentDTO borrowerInfo) {
		long userId = userDetailsProvider.getUserId();
		paymentService.addDebtor(userId, borrowerInfo);
	}

	@GetMapping("/my-debtors")
	public List<PaymentDTO> getMyDebtors() {
		long userId = userDetailsProvider.getUserId();
		List<Asset> debtors = paymentService.findUserDebtors(userId);
		return PaymentAssembler.convertToPaymentDTOs(debtors);
	}

	@GetMapping("/my-debts")
	public List<PaymentDTO> getMyDebts() {
		long userId = userDetailsProvider.getUserId();
		List<Asset> userDebts = paymentService.findUserDebts(userId);
		return PaymentAssembler.convertToPaymentDTOs(userDebts);
	}

	@DeleteMapping("/cancel-debt/{id}/")
	public HttpStatus cancelDebt(@PathVariable long id) {
		long userId = userDetailsProvider.getUserId();
		paymentService.cancelDebt(id, userId);
		return HttpStatus.OK;
	}

	@PostMapping("/add-mutual-payment")
	public HttpStatus addNewMutualPayment(@RequestBody PaymentDTO paymentDTO) {
		paymentService.addMutualPayment(paymentDTO);
		return HttpStatus.OK;
	}

	@PostMapping("/add-fee/{id}/{amount}/")
	public HttpStatus addFeeToMutualPayment(@PathVariable long id, @PathVariable double amount) {
		long userId = userDetailsProvider.getUserId();
		paymentService.addUserFeeToPayment(userId, id, amount);
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
		long userId = userDetailsProvider.getUserId();
		paymentService.deleteUserFees(userId, id);
		return HttpStatus.OK;
	}

	@DeleteMapping("/delete-mutual-payment/{id}/")
	public HttpStatus deleteMutualPayment(@PathVariable long id) {
		paymentService.deleteMutualPayment(id);
		return HttpStatus.OK;
	}

	@GetMapping("/money-balance")
	public double getMoneyBalance() {
		long userId = userDetailsProvider.getUserId();
		return paymentService.getUserDebtBalance(userId);
	}

	@GetMapping("/money-balance-with-other-user/{id}/")
	public double getMoneyBalanceWithOtherUser(@PathVariable long id) {
		long userId = userDetailsProvider.getUserId();
		return paymentService.getMoneyBalanceWithOtherUser(userId, id);
	}

}
