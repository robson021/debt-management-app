package robert.web.rest.svc;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import robert.db.UniversalDao;
import robert.db.entities.Asset;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;
import robert.web.security.JwtUtils;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final UniversalDao dao;

    @Autowired
    public PaymentController(UniversalDao dao) {
        this.dao = dao;
    }

    @RequestMapping(value = "/add-assets-to-user", method = RequestMethod.POST)
    public ResponseEntity<?> addAssetToTheUser(HttpServletRequest request, @RequestBody PaymentDTO borrowerInfo) {
        dao.addDebtor(JwtUtils.getUserId(request), borrowerInfo);
        return ResponseEntity.ok("Debtor added.");
    }

	@RequestMapping(value = "/my-debtors")
    public List<PaymentDTO> getMyDebtors(HttpServletRequest request) {
        dao.findUserDebtors(JwtUtils.getUserId(request));
        return PaymentAssembler.convertToPaymentDTOs(null);
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
}
