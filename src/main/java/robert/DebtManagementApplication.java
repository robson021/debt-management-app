package robert;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = "robert.db.entities")
@EnableJpaRepositories
@EnableTransactionManagement
public class DebtManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(DebtManagementApplication.class, args);
    }
}
