package robert.db.dao;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import robert.db.entities.Asset;
import robert.db.entities.User;
import robert.db.repo.UserRepository;
import robert.web.session.api.UserDataProvider;

@Service
@Transactional
public class UserDao {

    private static final Logger log = Logger.getLogger(UserDao.class);

    private final UserDataProvider userDataProvider;

    private final UserRepository userRepository;

    @Autowired
    public UserDao(UserDataProvider userDataProvider, UserRepository userRepository) {
        this.userDataProvider = userDataProvider;
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        User saved = this.userRepository.save(user);
        log.info("Saved new user: " + saved.getEmail());
        return saved;
    }

    public void addAssetToUser(String borrowerEmail, String descreption, double ammount, long borrowerId) {
        User user = userRepository.findOne(userDataProvider.getId());

        Asset asset = new Asset();
        asset.setAmount(ammount);
        asset.setBorrowerId(borrowerId);
        asset.setDescription(descreption);

        user.addAsset(asset);
        asset.setUser(user);
        userRepository.save(user);
    }

    public User findUser(long id) {
        return userRepository.findOne(id);
    }


    @PostConstruct
    public void init() throws Exception {
        User user = new User();
        user.setEmail("testadm@t.pl");
        user.setName("Admin");
        user.setSurname("Adm");
        user.setPassword("Passwd.123");

        userRepository.save(user);
    }
}
