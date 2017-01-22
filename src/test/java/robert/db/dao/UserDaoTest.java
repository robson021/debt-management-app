package robert.db.dao;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import robert.SpringTest;
import robert.TestUtils;
import robert.db.entities.User;

public class UserDaoTest extends SpringTest {

    @Autowired
    private UserDao userDao;

    @Test
    public void addAssetToUser() throws Exception {
        User user = userDao.saveUser(TestUtils.getTestUser());
        User borrower = userDao.saveUser(TestUtils.getTestUser());

        TestUtils.setAssetToUSer(user, borrower);
        userDao.saveUser(user);

        user = userDao.findUser(user.getId());

        Assertions.assertThat(user.getAssets())
                .isNotEmpty();
    }

}