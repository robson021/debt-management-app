package robert.db;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import robert.db.entities.Asset;
import robert.db.entities.User;
import robert.db.svc.DatabaseService;
import robert.tools.SpringTest;
import robert.tools.TestUtils;
import robert.web.rest.dto.PaymentDTO;

public class DatabaseServiceTest extends SpringTest {

	@Autowired
	private DatabaseService databaseService;

	@Test
	public void cancelDebt() throws Exception {
		User lender = databaseService.saveEntity(TestUtils.generateNewUser(), User.class);
		User borrower = databaseService.saveEntity(TestUtils.generateNewUser(), User.class);

		databaseService.addDebtor(lender.getId(), generatePayment(borrower));

		List<Asset> assets = databaseService.findUserDebtors(lender.getId());
		Assertions.assertThat(assets)
				.hasSize(1);

		long assetId = assets.iterator()
				.next()
				.getId();

		databaseService.cancelDebt(assetId, lender.getId());

		// after delete
		assets = databaseService.findUserDebtors(lender.getId());
		Assertions.assertThat(assets)
				.hasSize(0);
	}

	@Test
	public void addDebtor() throws Exception {
		User lender = databaseService.saveEntity(TestUtils.generateNewUser(), User.class);
		User borrower = databaseService.saveEntity(TestUtils.generateNewUser(), User.class);

		databaseService.addDebtor(lender.getId(), generatePayment(borrower));
		databaseService.addDebtor(lender.getId(), generatePayment(borrower));

		List<Asset> debtors = databaseService.findUserDebtors(lender.getId());

		Assertions.assertThat(debtors)
				.hasSize(2);
	}

	@Test
	public void getBalance() {
		User lender = databaseService.saveEntity(TestUtils.generateNewUser(), User.class);
		User borrower = databaseService.saveEntity(TestUtils.generateNewUser(), User.class);

		databaseService.addDebtor(lender.getId(), generatePayment(borrower));
		databaseService.addDebtor(lender.getId(), generatePayment(borrower));

		double balance = databaseService.getUserDebtBalance(lender.getId());
		Assertions.assertThat(balance)
				.isGreaterThan(0);
	}

	@Test
	public void getBalanceBetweenUsers() {
		User lender = databaseService.saveEntity(TestUtils.generateNewUser(), User.class);
		User borrower = databaseService.saveEntity(TestUtils.generateNewUser(), User.class);

		PaymentDTO paymentDTO = generatePayment(borrower);
		paymentDTO.setAmount(25.22);
		databaseService.addDebtor(lender.getId(), paymentDTO);

		double debt = databaseService.getMoneyBalanceWithOtherUser(lender.getId(), borrower.getId());
		Assertions.assertThat(debt)
				.isEqualTo(paymentDTO.getAmount());
	}

	@Test
	public void findOtherUsersExceptGiven() {
		User user = databaseService.saveEntity(TestUtils.generateNewUser(), User.class);
		List<User> otherUsers = databaseService.findOtherUsersExceptGiven(user.getId());

		Assertions.assertThat(otherUsers)
				.doesNotContain(user);
	}

	private PaymentDTO generatePayment(User borrower) {
		PaymentDTO paymentDTO = new PaymentDTO();
		paymentDTO.setAmount(RandomUtils.nextDouble(1, 9999));
		paymentDTO.setDescription("test payment");
		paymentDTO.setBorrowerId(borrower.getId());
		paymentDTO.setBorrowerName(borrower.getName());
		paymentDTO.setBorrowerSurname(borrower.getSurname());

		return paymentDTO;
	}

}