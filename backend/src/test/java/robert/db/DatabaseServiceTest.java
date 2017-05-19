package robert.db;

import java.util.Set;

import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import robert.SpringTest;
import robert.TestUtils;
import robert.db.entities.Asset;
import robert.db.entities.User;
import robert.web.rest.dto.PaymentDTO;

public class DatabaseServiceTest extends SpringTest {

	@Autowired
	private DatabaseService dbService;

	@Test
	public void cancelDebt() throws Exception {
		User lender = dbService.saveEntity(TestUtils.generateNewUser(), User.class);
		User borrower = dbService.saveEntity(TestUtils.generateNewUser(), User.class);

		dbService.addDebtor(lender.getId(), generatePayment(borrower));

		Set<Asset> assets = dbService.findUserDebtors(lender.getId());
		Assertions.assertThat(assets)
				.hasSize(1);

		long assetId = assets.iterator()
				.next()
				.getId();

		dbService.cancelDebt(assetId, lender.getId());

		// after delete
		assets = dbService.findUserDebtors(lender.getId());
		Assertions.assertThat(assets)
				.hasSize(0);
	}

	@Test
	public void addDebtor() throws Exception {
		User lender = dbService.saveEntity(TestUtils.generateNewUser(), User.class);
		User borrower = dbService.saveEntity(TestUtils.generateNewUser(), User.class);

		dbService.addDebtor(lender.getId(), generatePayment(borrower));
		dbService.addDebtor(lender.getId(), generatePayment(borrower));

		Set<Asset> debtors = dbService.findUserDebtors(lender.getId());

		Assertions.assertThat(debtors)
				.hasSize(2);
	}

	@Test
	public void getBalance() {
		User lender = dbService.saveEntity(TestUtils.generateNewUser(), User.class);
		User borrower = dbService.saveEntity(TestUtils.generateNewUser(), User.class);

		dbService.addDebtor(lender.getId(), generatePayment(borrower));
		dbService.addDebtor(lender.getId(), generatePayment(borrower));

		double balance = dbService.getUserDebtBalance(lender.getId());
		Assertions.assertThat(balance)
				.isGreaterThan(0);
	}

	@Test
	public void getBalanceBetweenUsers() {
		User lender = dbService.saveEntity(TestUtils.generateNewUser(), User.class);
		User borrower = dbService.saveEntity(TestUtils.generateNewUser(), User.class);

		PaymentDTO paymentDTO = generatePayment(borrower);
		paymentDTO.setAmount(25.22);
		dbService.addDebtor(lender.getId(), paymentDTO);

		double debt = dbService.getMoneyBalanceWithOtherUser(lender.getId(), borrower.getId());
		Assertions.assertThat(debt)
				.isEqualTo(paymentDTO.getAmount());
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