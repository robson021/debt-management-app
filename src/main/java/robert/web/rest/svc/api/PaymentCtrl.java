package robert.web.rest.svc.api;

import org.springframework.http.ResponseEntity;
import robert.web.rest.dto.PaymentDTO;

import java.util.List;

public interface PaymentCtrl {

	String ADD_ASSET_TO_THE_USER_URL = "/add-asset/";

	String GET_MY_DEBTORS_URL = "/my-debtors/";

	String GET_MY_DEBTS_URL = "/my-debts/";

	ResponseEntity<?> addAssetToTheUser(PaymentDTO paymentDTO);

	List<PaymentDTO> getMyDebtors();

	List<PaymentDTO> getMyDebts();
}
