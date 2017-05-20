package robert.db.repo;

import org.springframework.data.repository.CrudRepository;

import robert.db.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
	User findOneByEmail(String email);
}
