package robert.web.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("ssl")
@PropertySource("classpath:ssl.properties")
public class SslConfig {
}