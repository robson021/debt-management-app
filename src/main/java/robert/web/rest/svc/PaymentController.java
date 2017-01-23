package robert.web.rest.svc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import robert.db.dao.MainDao;
import robert.web.rest.dto.PaymentDTO;
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
        return new ResponseEntity<>("Debtor added", HttpStatus.OK);
    }
}
