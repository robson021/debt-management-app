package robert.web.rest.svc;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import robert.db.DatabaseService;
import robert.db.entities.Asset;
import robert.db.entities.Fee;
import robert.db.entities.MutualPayment;
import robert.web.request.data.UserDataProvider;
import robert.web.rest.dto.FeeDTO;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final DatabaseService dbService;

    private final UserDataProvider userDataProvider;

    @Autowired
    public PaymentController(DatabaseService dbService, UserDataProvider userDataProvider) {
        this.dbService = dbService;
        this.userDataProvider = userDataProvider;
    }

    @RequestMapping(value = "/add-assets-to-user", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public void addAssetToTheUser(@RequestBody PaymentDTO borrowerInfo) {
        dbService.addDebtor(userDataProvider.getUserId(), borrowerInfo);
    }

    @RequestMapping(value = "/my-debtors")
    public List<PaymentDTO> getMyDebtors() {
        Set<Asset> debtors = dbService.findUserDebtors(userDataProvider.getUserId());
        return PaymentAssembler.convertToPaymentDTOs(debtors);
    }

    @RequestMapping(value = "/my-debts")
    public List<PaymentDTO> getMyDebts() {
        List<Asset> userDebts = dbService.findUserDebts(userDataProvider.getUserId());
        return PaymentAssembler.convertToPaymentDTOs(userDebts);
    }

    @RequestMapping(value = "/cancel-debt/{id}/", method = RequestMethod.DELETE)
    public HttpStatus cancelDebt(@PathVariable("id") Long assetId) throws Exception {
        dbService.cancelDebt(assetId, userDataProvider.getUserId());
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/add-mutual-payment", method = RequestMethod.POST)
    public HttpStatus addNewMutualPayment(@RequestBody PaymentDTO paymentDTO) {
        dbService.addMutualPayment(paymentDTO);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/add-fee/{id}/{amount}/", method = RequestMethod.POST)
    public HttpStatus addFeeToMutualPayment(@PathVariable("id") Long paymentId, @PathVariable("amount") Double fee) {
        dbService.addUserFeeToPayment(userDataProvider.getUserId(), paymentId, fee);
        return HttpStatus.OK;
    }

    @RequestMapping("/mutual-payment-fees/{id}/")
    public List<FeeDTO> getFeesOfMutualPayment(@PathVariable("id") Long id) {
        Set<Fee> fees = dbService.getFeesForMutualPayment(id);
        return PaymentAssembler.convertFeesToDTOs(fees);
    }

    @RequestMapping("/mutual-payments")
    public List<PaymentDTO> getAllMutualPayments() {
        List<MutualPayment> allMutualPayments = dbService.getAllMutualPayments();
        return PaymentAssembler.convertToMutualPaymentDTO(allMutualPayments);
    }

    @RequestMapping(value = "/delete-my-fees/{id}/", method = RequestMethod.DELETE)
    public HttpStatus deleteMyFees(@PathVariable("id") Long paymentId) {
        dbService.deleteMyFees(userDataProvider.getUserId(), paymentId);
        return HttpStatus.OK;
    }

    @RequestMapping(value = "/delete-mutual-payment/{id}/", method = RequestMethod.DELETE)
    public HttpStatus deleteMutualPayment(@PathVariable("id") Long mpaymentId) {
        dbService.deleMutualPayment(mpaymentId);
        return HttpStatus.OK;
    }

}
