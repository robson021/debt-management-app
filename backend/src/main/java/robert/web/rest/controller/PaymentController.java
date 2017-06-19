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
import robert.db.svc.DbService;
import robert.web.rest.dto.FeeDTO;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;
import robert.web.security.auth.SecurityUtils;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	private final DbService dbService;

	public PaymentController(DbService dbService) {
		this.dbService = dbService;
	}

	@PostMapping("/add-assets-to-user")
	@ResponseStatus(HttpStatus.OK)
	public void addAssetToTheUser(@RequestBody PaymentDTO borrowerInfo) {
		dbService.addDebtor(SecurityUtils.getUserDetails()
				.getUserId(), borrowerInfo);
	}

	@GetMapping("/my-debtors")
	public List<PaymentDTO> getMyDebtors() {
		List<Asset> debtors = dbService.findUserDebtors(SecurityUtils.getUserDetails()
				.getUserId());
		return PaymentAssembler.convertToPaymentDTOs(debtors);
	}

	@GetMapping("/my-debts")
	public List<PaymentDTO> getMyDebts() {
		List<Asset> userDebts = dbService.findUserDebts(SecurityUtils.getUserDetails()
				.getUserId());
		return PaymentAssembler.convertToPaymentDTOs(userDebts);
	}

	@DeleteMapping("/cancel-debt/{id}/")
	public HttpStatus cancelDebt(@PathVariable long id) {
		dbService.cancelDebt(id, SecurityUtils.getUserDetails()
				.getUserId());
		return HttpStatus.OK;
	}

	@PostMapping("/add-mutual-payment")
	public HttpStatus addNewMutualPayment(@RequestBody PaymentDTO paymentDTO) {
		dbService.addMutualPayment(paymentDTO);
		return HttpStatus.OK;
	}

	@PostMapping("/add-fee/{id}/{amount}/")
	public HttpStatus addFeeToMutualPayment(@PathVariable long id, @PathVariable double amount) {
		dbService.addUserFeeToPayment(SecurityUtils.getUserDetails()
				.getUserId(), id, amount);
		return HttpStatus.OK;
	}

	@GetMapping("/mutual-payment-fees/{id}/")
	public List<FeeDTO> getFeesOfMutualPayment(@PathVariable long id) {
		Set<Fee> fees = dbService.getFeesForMutualPayment(id);
		return PaymentAssembler.convertFeesToDTOs(fees);
	}

	@GetMapping("/mutual-payments")
	public List<PaymentDTO> getAllMutualPayments() {
		List<MutualPayment> allMutualPayments = dbService.getAllMutualPayments();
		return PaymentAssembler.convertToMutualPaymentDTO(allMutualPayments);
	}

	@DeleteMapping("/delete-my-fees/{id}/")
	public HttpStatus deleteMyFees(@PathVariable long id) {
		dbService.deleteUserFees(SecurityUtils.getUserDetails()
				.getUserId(), id);
		return HttpStatus.OK;
	}

	@DeleteMapping("/delete-mutual-payment/{id}/")
	public HttpStatus deleteMutualPayment(@PathVariable long id) {
		dbService.deleteMutualPayment(id);
		return HttpStatus.OK;
	}

	@GetMapping("/money-balance")
	public double getMoneyBalance() {
		return dbService.getUserDebtBalance(SecurityUtils.getUserDetails()
				.getUserId());
	}

	@GetMapping("/money-balance-with-other-user/{id}/")
	public double getMoneyBalanceWithOtherUser(@PathVariable long id) {
		return dbService.getMoneyBalanceWithOtherUser(SecurityUtils.getUserDetails()
				.getUserId(), id);
	}

}
