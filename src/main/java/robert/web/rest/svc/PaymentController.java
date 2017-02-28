package robert.web.rest.svc;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import robert.db.dao.MainDao;
import robert.db.entities.Asset;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;
import robert.web.rest.svc.api.PaymentCtrl;

@RestController
public class PaymentController implements PaymentCtrl {

    private final MainDao dao;

    @Autowired
    public PaymentController(MainDao dao) {
        this.dao = dao;
    }

    @Override
    @RequestMapping(value = ADD_ASSET_TO_THE_USER_URL, method = RequestMethod.POST)
    public ResponseEntity<?> addAssetToTheUser(@RequestBody PaymentDTO paymentDTO) {
        dao.addAssetToTheUser(paymentDTO);
        return new ResponseEntity<>("Debtor added.", HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = GET_MY_DEBTORS_URL)
    public List<PaymentDTO> getMyDebtors() {
        Set<Asset> debtors = dao.getMyDebtors();
        return PaymentAssembler.convertToPaymentDTOs(debtors);
    }

    @Override
    @RequestMapping(value = GET_MY_DEBTS_URL)
    public List<PaymentDTO> getMyDebts() {
        List<Asset> debts = dao.getMyDebts();
        return PaymentAssembler.convertToPaymentDTOs(debts);
    }

    @Override
    @RequestMapping(value = CANCEL_DEBT, method = RequestMethod.DELETE)
    public HttpStatus cancelDebt(@PathVariable(ID) Long assetId) throws Exception {
        dao.cancelDebt(assetId);
        return HttpStatus.OK;
    }
}
