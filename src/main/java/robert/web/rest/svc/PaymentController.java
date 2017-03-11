package robert.web.rest.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robert.db.UniversalDao;
import robert.db.entities.Asset;
import robert.db.entities.Fee;
import robert.web.rest.dto.FeeDTO;
import robert.web.rest.dto.MutualPaymentDTO;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;
import robert.web.security.JwtUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/payments")
public class PaymentController {

	private final UniversalDao dao;

	@Autowired
	public PaymentController(UniversalDao dao) {
		this.dao = dao;
	}

	@RequestMapping(value = "/add-assets-to-user", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	public void addAssetToTheUser(HttpServletRequest request, @RequestBody PaymentDTO borrowerInfo) {
		dao.addDebtor(JwtUtils.getUserId(request), borrowerInfo);
	}

	@RequestMapping(value = "/my-debtors")
	public List<PaymentDTO> getMyDebtors(HttpServletRequest request) {
		Set<Asset> debtors = dao.findUserDebtors(JwtUtils.getUserId(request));
		return PaymentAssembler.convertToPaymentDTOs(debtors);
	}

	@RequestMapping(value = "/my-debts")
	public List<PaymentDTO> getMyDebts(HttpServletRequest request) {
		List<Asset> userDebts = dao.findUserDebts(JwtUtils.getUserId(request));
		return PaymentAssembler.convertToPaymentDTOs(userDebts);
	}

	@RequestMapping(value = "/cancel-debt/{id}/", method = RequestMethod.DELETE)
	public HttpStatus cancelDebt(HttpServletRequest request, @PathVariable("id") Long assetId) throws Exception {
		dao.cancelDebt(assetId, JwtUtils.getUserId(request));
		return HttpStatus.OK;
	}

	@RequestMapping(value = "/add-mutual-payment", method = RequestMethod.POST)
	public HttpStatus addNewMutualPayment(@RequestBody MutualPaymentDTO paymentDTO) {
		dao.addMutualPayment(paymentDTO);
		return HttpStatus.OK;
	}

	@RequestMapping(value = "/add-fee/{mpaymentId}/{amount}/", method = RequestMethod.POST)
	public HttpStatus addFeeToMutualPaymnet(HttpServletRequest request,
											@PathVariable("mpaymentId") Long id,
											@PathVariable("amount") Double fee) {
		dao.addUserFeeToPayment(JwtUtils.getUserId(request), id, fee);
		return HttpStatus.OK;
	}

	@RequestMapping("/mutual-payment-fees/{id}/")
	public List<FeeDTO> getFeesOfMutualPayment(@PathVariable("id") Long id) {
		Set<Fee> fees = dao.getFeesForMutualPayment(id);
		return PaymentAssembler.convertFeesToDTOs(fees);
	}

}
