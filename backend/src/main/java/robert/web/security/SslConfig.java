package robert.web.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("ssl")
@PropertySource("classpath:ssl.properties")
public class SslConfig {
}
/*
* generate key with:
* "keytool -genkey -alias serverSSL -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore keystore.p12 -validity 3650"
* than put it into src/resources and set values in ssl.properties (alias & password)
*/
