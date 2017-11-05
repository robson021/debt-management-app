package robert.db.repo;

import org.springframework.data.repository.CrudRepository;
import robert.db.entities.Asset;

public interface AssetRepository extends CrudRepository<Asset, Long> {

}
