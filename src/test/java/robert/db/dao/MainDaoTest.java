package robert.db.dao;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import robert.SpringTest;
import robert.TestUtils;
import robert.db.entities.User;

public class MainDaoTest extends SpringTest {

    @Autowired
    private MainDao mainDao;

    @Test
    public void addAssetToUser() throws Exception {
        User user = mainDao.saveUser(TestUtils.getTestUser());
        User borrower = mainDao.saveUser(TestUtils.getTestUser());

        TestUtils.setAssetToUSer(user, borrower);
        mainDao.saveUser(user);

        user = mainDao.findUser(user.getId());

        Assertions.assertThat(user.getAssets())
                .isNotEmpty();
    }

}