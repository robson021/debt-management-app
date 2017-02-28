package robert.web.rest.svc.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import robert.web.rest.dto.PaymentDTO;

public interface PaymentCtrl {

	String ID = "id";

	String ADD_ASSET_TO_THE_USER_URL = "/add-asset/";

	String GET_MY_DEBTORS_URL = "/my-debtors/";

	String GET_MY_DEBTS_URL = "/my-debts/";

	String CANCEL_DEBT = "/cancel-debt/" + "{" + ID + "}/";

	ResponseEntity<?> addAssetToTheUser(PaymentDTO paymentDTO);

	List<PaymentDTO> getMyDebtors();

	List<PaymentDTO> getMyDebts();

	HttpStatus cancelDebt(Long assetId) throws Exception;
}
