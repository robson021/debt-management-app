package robert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import robert.svc.api.MailerService;

@SpringBootApplication
@EntityScan(basePackages = "robert.db.entities")
@EnableJpaRepositories(basePackages = "robert.db.repo")
@EnableTransactionManagement
@EnableCaching
public class DebtManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(DebtManagementApplication.class, args);
    }

    @Bean
    @Lazy
    @ConditionalOnMissingBean
    public MailerService mailerService() {
        return (receiverEmail, topic, body, file, deleteFileAfterIsSent) -> System.out.println("[mock] sending email to: " + receiverEmail);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }
}
