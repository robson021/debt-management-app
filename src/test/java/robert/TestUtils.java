package robert;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.mockito.Mockito;
import robert.db.entities.Asset;
import robert.db.entities.User;
import robert.web.session.api.UserDataProvider;

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
        asset.setBorrowerName(borrower.getName());
        asset.setBorrowerSurname(borrower.getSurname());

        lender.addAsset(asset);
        asset.setUser(lender);
        return lender;
    }

	public static void mockUserDataProvider(UserDataProvider udp, User user) {
		Mockito.when(udp.getId()).thenReturn(user.getId());
		Mockito.when(udp.getEmail()).thenReturn(user.getEmail());
	}
}
