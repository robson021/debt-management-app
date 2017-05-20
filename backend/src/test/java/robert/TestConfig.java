package robert;

import static org.mockito.Mockito.mock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import robert.web.request.data.UserDataProvider;

@Configuration
public class TestConfig {

	@Bean
	@Primary
	public UserDataProvider userDataProvider() {
		return mock(UserDataProvider.class);
	}

}
