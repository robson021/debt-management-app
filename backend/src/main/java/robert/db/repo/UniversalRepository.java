package robert.db.repo;

import org.springframework.data.repository.Repository;

import robert.db.entities.BasicEntity;

public interface UniversalRepository extends Repository<BasicEntity, Long> {
	BasicEntity save(BasicEntity entity);
}