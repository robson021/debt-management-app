package robert.tools;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import robert.web.svc.api.UserDetailsProvider;

@Configuration
public class TestConfig {

	@Bean
	@Primary
	public UserDetailsProvider userInfoProvider() {
		return Mockito.mock(UserDetailsProvider.class);
	}
}
