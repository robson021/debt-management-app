package robert.web.rest.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import robert.db.entities.Asset;
import robert.db.entities.Fee;
import robert.db.entities.MutualPayment;
import robert.db.svc.api.PaymentService;
import robert.web.rest.dto.FeeDTO;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.asm.PaymentAssembler;
import robert.web.security.userdetails.provider.UserDetailsProvider;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final UserDetailsProvider userDetailsProvider;

    private final PaymentService paymentService;

    @PostMapping("/add-assets-to-user")
    @ResponseStatus(HttpStatus.OK)
    public void addAssetToTheUser(@RequestBody PaymentDTO borrowerInfo) {
        long userId = userDetailsProvider.getUserId();
        paymentService.addDebtor(userId, borrowerInfo);
    }

    @GetMapping("/my-debtors")
    public List<PaymentDTO> getMyDebtors() {
        long userId = userDetailsProvider.getUserId();
        List<Asset> debtors = paymentService.findUserDebtors(userId);
        return PaymentAssembler.convertToPaymentDTOs(debtors);
    }

    @GetMapping("/my-debts")
    public List<PaymentDTO> getMyDebts() {
        long userId = userDetailsProvider.getUserId();
        List<Asset> userDebts = paymentService.findUserDebts(userId);
        return PaymentAssembler.convertToPaymentDTOs(userDebts);
    }

    @DeleteMapping("/cancel-debt/{id}/")
    @ResponseStatus(HttpStatus.OK)
    public void cancelDebt(@PathVariable long id) {
        long userId = userDetailsProvider.getUserId();
        paymentService.cancelDebt(id, userId);
    }

    @PostMapping("/add-mutual-payment")
    @ResponseStatus(HttpStatus.OK)
    public void addNewMutualPayment(@RequestBody PaymentDTO paymentDTO) {
        paymentService.addMutualPayment(paymentDTO);
    }

    @PostMapping("/add-fee/{id}/{amount}/")
    @ResponseStatus(HttpStatus.OK)
    public void addFeeToMutualPayment(@PathVariable long id, @PathVariable BigDecimal amount) {
        long userId = userDetailsProvider.getUserId();
        paymentService.addUserFeeToPayment(userId, id, amount);
    }

    @GetMapping("/mutual-payment-fees/{id}/")
    public List<FeeDTO> getFeesOfMutualPayment(@PathVariable long id) {
        Set<Fee> fees = paymentService.getFeesForMutualPayment(id);
        return PaymentAssembler.convertFeesToDTOs(fees);
    }

    @GetMapping("/mutual-payments")
    public List<PaymentDTO> getAllMutualPayments() {
        List<MutualPayment> allMutualPayments = paymentService.getAllMutualPayments();
        return PaymentAssembler.convertToMutualPaymentDTO(allMutualPayments);
    }

    @DeleteMapping("/delete-my-fees/{id}/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMyFees(@PathVariable long id) {
        long userId = userDetailsProvider.getUserId();
        paymentService.deleteUserFees(userId, id);
    }

    @DeleteMapping("/delete-mutual-payment/{id}/")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMutualPayment(@PathVariable long id) {
        paymentService.deleteMutualPayment(id);
    }

    @GetMapping("/money-balance")
    public String getMoneyBalance() {
        long userId = userDetailsProvider.getUserId();
        return paymentService.getUserDebtBalance(userId).toString();
    }

    @GetMapping("/money-balance-with-other-user/{id}/")
    public String getMoneyBalanceWithOtherUser(@PathVariable long id) {
        long userId = userDetailsProvider.getUserId();
        return paymentService.getMoneyBalanceWithOtherUser(userId, id).toString();
    }

}
