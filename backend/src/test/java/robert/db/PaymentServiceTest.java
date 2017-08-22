package robert.db;

import java.util.List;

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

		paymentService.addDebtor(lender.getId(), TestUtils.generatePayment(borrower));
		paymentService.addDebtor(lender.getId(), TestUtils.generatePayment(borrower));
	}

	@Test
	public void findUserDebts() {
		List<Asset> userDebts = paymentService.findUserDebts(borrower.getId());

		Assertions.assertThat(userDebts.size())
				.isEqualTo(2);

		Assertions.assertThat(userDebts.get(0)
				.getAmount())
				.isGreaterThan(.0);

		for (Asset userDebt : userDebts) {
			Assertions.assertThat(userDebt.getAmount())
					.isGreaterThan(.0);
		}
	}

	@Test
	public void findUserDebtors() {
		List<Asset> userDebtors = paymentService.findUserDebtors(lender.getId());

		Assertions.assertThat(userDebtors.size())
				.isEqualTo(2);

		for (Asset debtor : userDebtors) {
			double amount = debtor.getAmount();
			Assertions.assertThat(amount)
					.isGreaterThan(.0);
		}
	}

	@Test
	public void cancelDebt() throws Exception {
		List<Asset> assets = paymentService.findUserDebtors(lender.getId());

		final int initialAssetsSize = assets.size();

		Assertions.assertThat(initialAssetsSize)
				.isGreaterThan(0);

		long assetId = assets.iterator()
				.next()
				.getId();

		paymentService.cancelDebt(assetId, lender.getId());

		// after delete
		assets = paymentService.findUserDebtors(lender.getId());
		Assertions.assertThat(assets)
				.hasSize(initialAssetsSize - 1);
	}

	@Test
	public void addDebtor() throws Exception {
		List<Asset> debtors = paymentService.findUserDebtors(lender.getId());

		Assertions.assertThat(debtors)
				.hasSize(2);
	}

	@Test
	public void getBalance() {
		double balance = paymentService.getUserDebtBalance(lender.getId());
		Assertions.assertThat(balance)
				.isGreaterThan(0);
	}

	@Test
	public void getBalanceBetweenUsers() {
		paymentService.addDebtor(lender.getId(), TestUtils.generatePayment(borrower));
		double debt = paymentService.getMoneyBalanceWithOtherUser(lender.getId(), borrower.getId());

		Assertions.assertThat(debt)
				.isGreaterThan(.0);
	}

}