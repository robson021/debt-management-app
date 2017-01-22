package robert;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import robert.db.entities.Asset;
import robert.db.entities.User;

public class TestUtils {

    public static User getTestUser() {
        User user = new User();
        try {
            user.setEmail(RandomStringUtils.randomAlphabetic(3) + "testuser@t.pl");
            user.setPassword("Passwd.123");
        } catch (Exception ignored) {
        }
        user.setName("Test");
        user.setSurname("User");
        return user;
    }

    public static User setAssetToUSer(User lender, User borrower) {
        Asset asset = new Asset();
        asset.setAmount(RandomUtils.nextDouble(1., 55.));
        long id = borrower.getId();
        asset.setBorrowerId(id);
        asset.setDescription(RandomStringUtils.randomAlphanumeric(20));

        lender.addAsset(asset);
        asset.setUser(lender);
        return lender;
    }
}
