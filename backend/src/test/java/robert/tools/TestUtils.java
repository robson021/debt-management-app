package robert.tools;

import java.io.IOException;

import org.apache.commons.lang3.RandomStringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import robert.db.entities.User;

public class TestUtils {

	private static final ObjectMapper mapper = new ObjectMapper();

	public static String asJsonString(final Object obj) throws Exception {
		return mapper.writeValueAsString(obj);
	}

	public static <T> T jsonToObject(String json, Class<T> clazz) throws IOException {
		return mapper.readValue(json, clazz);
	}

	public static User generateNewUser() {
		User user = new User();
		user.setEmail((RandomStringUtils.randomAlphabetic(10) + "@test.pl").toLowerCase());
		user.setName(RandomStringUtils.randomAlphabetic(8));
		user.setSurname(RandomStringUtils.randomAlphabetic(8));
		user.setPassword("Passwd.123");
		return user;
	}

}
