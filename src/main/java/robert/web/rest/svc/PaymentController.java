package robert.web.rest.svc;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import robert.db.UniversalDao;
import robert.db.entities.Asset;
import robert.db.entities.Fee;
import robert.db.entities.MutualPayment;
import robert.web.rest.dto.FeeDTO;
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
    public HttpStatus addNewMutualPayment(@RequestBody PaymentDTO paymentDTO) {
        dao.addMutualPayment(paymentDTO);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/add-fee/{id}/{amount}/", method = RequestMethod.POST)
    public HttpStatus addFeeToMutualPayment(HttpServletRequest request, @PathVariable("id") Long paymentId, @PathVariable("amount") Double fee) {
        dao.addUserFeeToPayment(JwtUtils.getUserId(request), paymentId, fee);
        return HttpStatus.OK;
    }

    @RequestMapping("/mutual-payment-fees/{id}/")
    public List<FeeDTO> getFeesOfMutualPayment(@PathVariable("id") Long id) {
        Set<Fee> fees = dao.getFeesForMutualPayment(id);
        return PaymentAssembler.convertFeesToDTOs(fees);
    }

    @RequestMapping("/mutual-payments")
    public List<PaymentDTO> getAllMutualPayments() {
        List<MutualPayment> allMutualPayments = dao.getAllMutualPayments();
        return PaymentAssembler.convertToMutualPaymentDTO(allMutualPayments);
    }

    @RequestMapping(value = "/delete-my-fees/{id}/", method = RequestMethod.DELETE)
    public HttpStatus deleteMyFees(HttpServletRequest request, @PathVariable("id") Long paymentId) {
        dao.deleteMyFees(JwtUtils.getUserId(request), paymentId);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/delete-mutual-payment/{id}/", method = RequestMethod.DELETE)
    public HttpStatus deleteMutualPayment(@PathVariable("id") Long mpaymentId) {
        dao.deleMutualPayment(mpaymentId);
        return HttpStatus.OK;
    }

}
