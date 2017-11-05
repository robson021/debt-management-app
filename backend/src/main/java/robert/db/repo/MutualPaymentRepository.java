package robert.db.repo;

import org.springframework.data.repository.CrudRepository;
import robert.db.entities.MutualPayment;

import java.util.List;

public interface MutualPaymentRepository extends CrudRepository<MutualPayment, Long> {

    List<MutualPayment> findAll();
}
