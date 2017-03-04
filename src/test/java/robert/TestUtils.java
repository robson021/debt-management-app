package robert;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.RandomStringUtils;
import robert.db.entities.User;

public class TestUtils {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static String asJsonString(final Object obj) throws Exception {
		return mapper.writeValueAsString(obj);
	}

	public static User generateNewUser() {
		User user = new User();
		user.setEmail((RandomStringUtils.randomAlphabetic(10) + "@test.pl").toLowerCase());
		user.setName("Example");
		user.setSurname("User");
		user.setPassword("Passwd.123");
		return user;
	}


}
