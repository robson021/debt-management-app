package robert.db.dao;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import robert.SpringTest;
import robert.TestUtils;
import robert.db.entities.Asset;
import robert.db.entities.User;
import robert.web.session.api.UserDataProvider;

import java.util.List;
import java.util.Set;

public class MainDaoTest extends SpringTest {

	@Autowired
	private MainDao dao;

	@Autowired
	private UserDataProvider userDataProvider;

	@Test
	public void addAssetToUser() throws Exception {
		User lender = dao.saveUser(TestUtils.getTestUser());
		User borrower = dao.saveUser(TestUtils.getTestUser());

		TestUtils.setAssetToUSer(lender, borrower);
		dao.saveUser(lender);

		lender = dao.findUser(lender.getId());

		Assertions.assertThat(lender.getAssets())
				.isNotEmpty();
	}

	@Test
	public void getMyDebts() {
		User borrower = dao.saveUser(TestUtils.getTestUser());
		User lender = dao.saveUser(TestUtils.getTestUser());

		TestUtils.setAssetToUSer(lender, borrower);
		dao.saveUser(lender);

		TestUtils.mockUserDataProvider(userDataProvider, borrower);

		List<Asset> debts = dao.getMyDebts();
		Assertions.assertThat(debts)
				.isNotEmpty()
				.hasSize(1);
	}

	@Test
	public void getMyDebtors() {
		User lender = dao.saveUser(TestUtils.getTestUser());
		User borrower1 = dao.saveUser(TestUtils.getTestUser());
		User borrower2 = dao.saveUser(TestUtils.getTestUser());

		TestUtils.setAssetToUSer(lender, borrower1);
		TestUtils.setAssetToUSer(lender, borrower2);
		dao.saveUser(lender);

		TestUtils.mockUserDataProvider(userDataProvider, lender);

		Set<Asset> debtors = dao.getMyDebtors();
		Assertions.assertThat(debtors)
				.isNotEmpty()
				.hasSize(2);
	}

}