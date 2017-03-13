package robert.db.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import robert.db.entities.Fee;
import robert.db.entities.User;

public interface FeeRepository extends CrudRepository<Fee, Long> {
    List<Fee> findByUser(User u);
}
