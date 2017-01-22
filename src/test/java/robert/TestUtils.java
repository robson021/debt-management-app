package robert;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import robert.db.entities.Asset;
import robert.db.entities.User;

public class TestUtils {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static String asJsonString(final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

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
