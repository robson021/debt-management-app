package robert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import robert.svc.api.MailerService;

@SpringBootApplication
@EntityScan(basePackages = "robert.db.entities")
@EnableJpaRepositories(basePackages = "robert.db.repo")
@EnableTransactionManagement
@PropertySource("classpath:mailer.properties")
public class DebtManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DebtManagementApplication.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Bean
	@ConditionalOnMissingBean
	public MailerService mailerService() {
		return receiverEmail -> System.out.println("Fake mailer service");
	}

	@Autowired
	public void authenticationManager(AuthenticationManagerBuilder builder, UserDetailsService details, PasswordEncoder encoder) throws Exception {
		builder
				.userDetailsService(details)
				.passwordEncoder(encoder);
	}
}
