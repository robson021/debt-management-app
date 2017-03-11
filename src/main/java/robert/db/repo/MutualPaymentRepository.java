package robert.db.repo;

import org.springframework.data.repository.CrudRepository;
import robert.db.entities.MutualPayment;

public interface MutualPaymentRepository extends CrudRepository<MutualPayment, Long> {
}
