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

    /*private PaymentDTO mockPayments() {
        PaymentDTO testDTO = new PaymentDTO();
        testDTO.setId(RandomUtils.nextInt(1, 99999));
        testDTO.setAmount(RandomUtils.nextDouble(1, 30));
        testDTO.setBorrowerId(RandomUtils.nextInt(1, 999));
        testDTO.setDescription("Test desc");
        testDTO.setBorrowerName("Aaaa");
        testDTO.setBorrowerSurname("Bbbbb");
        return testDTO;
    }*/
}
