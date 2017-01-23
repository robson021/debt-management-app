package robert;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import robert.web.session.api.UserDataProvider;

@Configuration
public class TestConfig {

    @Bean
    @Primary
    public UserDataProvider userDataProvider() {
        return Mockito.mock(UserDataProvider.class);
    }
}
