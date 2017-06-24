package robert.web.rest.controller;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import robert.db.entities.User;
import robert.db.repo.UserRepository;
import robert.db.svc.api.PaymentService;
import robert.tools.SpringWebMvcTest;
import robert.tools.TestUtils;
import robert.web.rest.dto.PaymentDTO;
import robert.web.security.auth.JwtAuthenticationToken;
import robert.web.svc.UserInfoProvider;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PaymentControllerTest extends SpringWebMvcTest {

	@Autowired
	private UserInfoProvider userInfoProvider;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private UserRepository userRepository;

	private User borrower;

	private User lender;

	private JwtAuthenticationToken auth;

	@Before
	public void setup() {
		borrower = userRepository.save(TestUtils.generateNewUser());
		lender = userRepository.save(TestUtils.generateNewUser());

		auth = new JwtAuthenticationToken(null, lender.getEmail(), lender.getId());

		Mockito.when(userInfoProvider.getUserDetails())
				.thenReturn(auth);
	}

	@Test
	public void addAssetToTheUser() throws Exception {
		PaymentDTO paymentDTO = TestUtils.generatePayment(borrower);
		mockMvc.perform(post("/payments/add-assets-to-user")
				.content(TestUtils.asJsonString(paymentDTO))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void getMyDebtors() throws Exception {
		paymentService.addDebtor(lender.getId(), TestUtils.generatePayment(borrower));
		paymentService.addDebtor(lender.getId(), TestUtils.generatePayment(borrower));

		String response = mockMvc.perform(get("/payments/my-debtors"))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		PaymentDTO[] payments = TestUtils.jsonToObject(response, PaymentDTO[].class);

		Assertions.assertThat(payments).hasSize(2);

		for (PaymentDTO payment : payments) {
			Assertions.assertThat(payment.getBorrowerName())
					.isEqualTo(borrower.getName());

			Assertions.assertThat(payment.getBorrowerSurname())
					.isEqualTo(borrower.getSurname());

			Assertions.assertThat(payment.getBorrowerId())
					.isEqualTo(borrower.getId());
		}
	}

	@Test
	public void getMyDebts() throws Exception {
	}

	@Test
	public void cancelDebt() throws Exception {
	}

	@Test
	public void addNewMutualPayment() throws Exception {
	}

	@Test
	public void addFeeToMutualPayment() throws Exception {
	}

	@Test
	public void getFeesOfMutualPayment() throws Exception {
	}

	@Test
	public void getAllMutualPayments() throws Exception {
	}

	@Test
	public void deleteMyFees() throws Exception {
	}

	@Test
	public void deleteMutualPayment() throws Exception {
	}

	@Test
	public void getMoneyBalance() throws Exception {
	}

	@Test
	public void getMoneyBalanceWithOtherUser() throws Exception {
	}

}