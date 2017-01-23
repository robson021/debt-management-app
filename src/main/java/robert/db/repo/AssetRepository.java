package robert.db.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import robert.db.entities.Asset;

@Repository
public interface AssetRepository extends CrudRepository<Asset, Long> {

}
