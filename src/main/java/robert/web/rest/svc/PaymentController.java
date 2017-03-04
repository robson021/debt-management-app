package robert.web.rest.svc;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;

import java.util.List;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	@RequestMapping(value = "/add-assets-to-user", method = RequestMethod.POST)
	public ResponseEntity<?> addAssetToTheUser(@RequestBody PaymentDTO paymentDTO) {
		return ResponseEntity.ok("Debtor added.");
	}

	@RequestMapping(value = "/my-debtors")
	public List<PaymentDTO> getMyDebtors() {
		return PaymentAssembler.convertToPaymentDTOs(null);
	}

	@RequestMapping(value = "/my-debts")
	public List<PaymentDTO> getMyDebts() {
		return PaymentAssembler.convertToPaymentDTOs(null);
	}

	@RequestMapping(value = "/cancel-debt/{id}/", method = RequestMethod.DELETE)
	public HttpStatus cancelDebt(@PathVariable("id") Long assetId) throws Exception {
		return HttpStatus.OK;
	}
}
