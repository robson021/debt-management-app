package robert.db.repo;

import org.springframework.data.repository.CrudRepository;
import robert.db.entities.Fee;

public interface FeeRepository extends CrudRepository<Fee, Long> {
}
