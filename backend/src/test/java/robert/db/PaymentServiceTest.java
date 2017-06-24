package robert.db;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import robert.db.entities.Asset;
import robert.db.entities.User;
import robert.db.svc.api.PaymentService;
import robert.db.svc.api.UserService;
import robert.tools.SpringTest;
import robert.tools.TestUtils;
import robert.web.rest.dto.PaymentDTO;

import java.util.List;

public class PaymentServiceTest extends SpringTest {

	@Autowired
	private UserService userService;

	@Autowired
	private PaymentService paymentService;

	private User borrower;

	private User lender;

	@Before
	public void setup() {
		borrower = userService.saveNewUser(TestUtils.generateNewUser());
		lender = userService.saveNewUser(TestUtils.generateNewUser());
	}

	@Test
	public void cancelDebt() throws Exception {
		paymentService.addDebtor(lender.getId(), TestUtils.generatePayment(borrower));

		List<Asset> assets = paymentService.findUserDebtors(lender.getId());
		Assertions.assertThat(assets)
				.hasSize(1);

		long assetId = assets.iterator()
				.next()
				.getId();

		paymentService.cancelDebt(assetId, lender.getId());

		// after delete
		assets = paymentService.findUserDebtors(lender.getId());
		Assertions.assertThat(assets)
				.hasSize(0);
	}

	@Test
	public void addDebtor() throws Exception {
		paymentService.addDebtor(lender.getId(), TestUtils.generatePayment(borrower));
		paymentService.addDebtor(lender.getId(), TestUtils.generatePayment(borrower));

		List<Asset> debtors = paymentService.findUserDebtors(lender.getId());

		Assertions.assertThat(debtors)
				.hasSize(2);
	}

	@Test
	public void getBalance() {
		paymentService.addDebtor(lender.getId(), TestUtils.generatePayment(borrower));
		paymentService.addDebtor(lender.getId(), TestUtils.generatePayment(borrower));

		double balance = paymentService.getUserDebtBalance(lender.getId());
		Assertions.assertThat(balance)
				.isGreaterThan(0);
	}

	@Test
	public void getBalanceBetweenUsers() {
		PaymentDTO paymentDTO = TestUtils.generatePayment(borrower);
		paymentDTO.setAmount(25.22);
		paymentService.addDebtor(lender.getId(), paymentDTO);

		double debt = paymentService.getMoneyBalanceWithOtherUser(lender.getId(), borrower.getId());
		Assertions.assertThat(debt)
				.isEqualTo(paymentDTO.getAmount());
	}

}