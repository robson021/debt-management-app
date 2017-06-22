package robert.db;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import robert.db.entities.Asset;
import robert.db.entities.User;
import robert.db.svc.api.PaymentService;
import robert.db.svc.api.UserService;
import robert.tools.SpringTest;
import robert.tools.TestUtils;
import robert.web.rest.dto.PaymentDTO;

public class PaymentServiceTest extends SpringTest {

	@Autowired
	private UserService userService;

	@Autowired
	private PaymentService paymentService;

	@Test
	public void cancelDebt() throws Exception {
		User lender = userService.saveNewUser(TestUtils.generateNewUser());
		User borrower = userService.saveNewUser(TestUtils.generateNewUser());

		paymentService.addDebtor(lender.getId(), generatePayment(borrower));

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
		User lender = userService.saveNewUser(TestUtils.generateNewUser());
		User borrower = userService.saveNewUser(TestUtils.generateNewUser());

		paymentService.addDebtor(lender.getId(), generatePayment(borrower));
		paymentService.addDebtor(lender.getId(), generatePayment(borrower));

		List<Asset> debtors = paymentService.findUserDebtors(lender.getId());

		Assertions.assertThat(debtors)
				.hasSize(2);
	}

	@Test
	public void getBalance() {
		User lender = userService.saveNewUser(TestUtils.generateNewUser());
		User borrower = userService.saveNewUser(TestUtils.generateNewUser());

		paymentService.addDebtor(lender.getId(), generatePayment(borrower));
		paymentService.addDebtor(lender.getId(), generatePayment(borrower));

		double balance = paymentService.getUserDebtBalance(lender.getId());
		Assertions.assertThat(balance)
				.isGreaterThan(0);
	}

	@Test
	public void getBalanceBetweenUsers() {
		User lender = userService.saveNewUser(TestUtils.generateNewUser());
		User borrower = userService.saveNewUser(TestUtils.generateNewUser());

		PaymentDTO paymentDTO = generatePayment(borrower);
		paymentDTO.setAmount(25.22);
		paymentService.addDebtor(lender.getId(), paymentDTO);

		double debt = paymentService.getMoneyBalanceWithOtherUser(lender.getId(), borrower.getId());
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