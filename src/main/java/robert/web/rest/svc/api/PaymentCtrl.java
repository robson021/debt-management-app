package robert.web.rest.svc.api;

import org.springframework.http.ResponseEntity;

import robert.web.rest.dto.PaymentDTO;

public interface PaymentCtrl {

    String ADD_ASSET_TO_THE_USER_URL = "/add-asset/";

    ResponseEntity<?> addAssetToTheUser(PaymentDTO paymentDTO);

}
