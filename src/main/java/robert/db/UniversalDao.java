package robert.db;

import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import robert.db.entities.Asset;
import robert.db.entities.User;
import robert.db.repo.AssetRepository;
import robert.db.repo.UserRepository;

@Component
@Transactional
public class UniversalDao {

    private static final Logger log = Logger.getLogger(UniversalDao.class);

    private final UserRepository userRepository;

    private final AssetRepository assetRepository;

    private final EntityManager em;

    @Autowired
    public UniversalDao(UserRepository userRepository, AssetRepository assetRepository, EntityManager em) {
        this.userRepository = userRepository;
        this.assetRepository = assetRepository;
        this.em = em;
    }

    public List<Asset> findUserDebts(Long borrowerId) {
        List<Asset> debts = em.createQuery("from Asset a where a.borrowerId = :id", Asset.class)
                .setParameter("id", borrowerId)
                .getResultList();

        if ( log.isDebugEnabled() )
            log.debug("Found " + debts.size() + " debts");

        return debts;
    }

    public Set<Asset> findUserDebtors(Long userId) {
        Set<Asset> assets = userRepository.findOne(userId)
                .getAssets();

        if ( log.isDebugEnabled() )
            log.debug("Found " + assets.size() + " assets");

        return assets;
    }

    @PostConstruct
    public void init() {
        User user = new User();
        user.setEmail("test@t.pl");
        user.setName("Example");
        user.setSurname("User");
        user.setPassword("Passwd.123");

        userRepository.save(user);
    }

}
