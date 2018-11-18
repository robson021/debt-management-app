package robert.web.rest.controllers;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import robert.db.entities.User;
import robert.db.repo.UserRepository;
import robert.db.svc.api.PaymentService;
import robert.tools.SpringWebMvcTest;
import robert.tools.TestUtils;
import robert.web.rest.dto.PaymentDTO;
import robert.web.security.userdetails.provider.UserDetailsProvider;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentControllerTest extends SpringWebMvcTest {

    @MockBean
    private UserDetailsProvider userDetailsProvider;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;

    private User borrower;

    private User lender;

    @Before
    public void setup() {
        borrower = userRepository.save(TestUtils.generateNewUser());
        lender = userRepository.save(TestUtils.generateNewUser());

        paymentService.addDebtor(lender.getId(), TestUtils.generatePayment(borrower));
        paymentService.addDebtor(lender.getId(), TestUtils.generatePayment(borrower));

        Mockito.when(userDetailsProvider.getUserId())
                .thenReturn(lender.getId());
    }

    @Test
    public void addAssetToTheUser() throws Exception {
        PaymentDTO paymentDTO = TestUtils.generatePayment(borrower);
        mockMvc.perform(post("/payments/add-assets-to-user").content(TestUtils.asJsonString(paymentDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getMyDebtors() throws Exception {
        // add extra debtor
        User debtor = userRepository.save(TestUtils.generateNewUser());
        paymentService.addDebtor(lender.getId(), TestUtils.generatePayment(debtor));

        String response = mockMvc.perform(get("/payments/my-debtors"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PaymentDTO[] payments = TestUtils.jsonToObject(response, PaymentDTO[].class);

        Assertions.assertThat(payments)
                .hasSize(3);
    }

    @Test
    public void getMyDebts() throws Exception {
        Mockito.when(userDetailsProvider.getUserId())
                .thenReturn(borrower.getId());

        String response = mockMvc.perform(get("/payments/my-debts"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        PaymentDTO[] debts = TestUtils.jsonToObject(response, PaymentDTO[].class);

        Assertions.assertThat(debts)
                .hasSize(2);

        Arrays.stream(debts)
                .forEach(debt -> {
                    Assertions.assertThat(debt.getBorrowerId())
                            .isEqualTo(borrower.getId());
                    Assertions.assertThat(debt.getBorrowerName())
                            .isEqualTo(borrower.getName());
                    Assertions.assertThat(debt.getBorrowerSurname())
                            .isEqualTo(borrower.getSurname());
                });

    }

    @Test
    public void cancelDebt() throws Exception {
        Long debtId = paymentService.findUserDebtors(lender.getId())
                .get(0)
                .getId();
        String url = "/payments/cancel-debt/{id}/".replace("{id}", debtId.toString());

        mockMvc.perform(delete(url))
                .andExpect(status().isOk());
    }

    @Test
    public void getMoneyBalance() throws Exception {
        String response = mockMvc.perform(get("/payments/money-balance"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        double moneyBalance = Double.valueOf(response);

        Assertions.assertThat(moneyBalance)
                .isGreaterThan(.0);
    }

    @Test
    public void getMoneyBalanceWithOtherUser() throws Exception {
        String url = "/payments/money-balance-with-other-user/{id}/".replace("{id}", borrower.getId()
                .toString());

        String response = mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        double moneyBalance = Double.valueOf(response);

        Assertions.assertThat(moneyBalance)
                .isGreaterThan(.0);
    }

}