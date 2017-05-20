package robert.db.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import robert.db.entities.MutualPayment;

public interface MutualPaymentRepository extends CrudRepository<MutualPayment, Long> {
	List<MutualPayment> findAll();
}
