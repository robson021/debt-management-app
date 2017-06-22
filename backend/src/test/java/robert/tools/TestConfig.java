package robert.tools;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import robert.web.svc.UserInfoProvider;

@Configuration
public class TestConfig {

	@Bean
	@Primary
	public UserInfoProvider userInfoProvider() {
		return Mockito.mock(UserInfoProvider.class);
	}
}
