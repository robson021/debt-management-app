package robert.db.dao;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import robert.db.entities.User;
import robert.db.repo.UserRepository;

@Service
@Transactional
public class UserDao {

    private static final Logger log = Logger.getLogger(UserDao.class);

    private final UserRepository userRepository;

    @Autowired
    public UserDao(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        User user = new User();
        user.setEmail("testadm@t.pl");
        user.setName("Admin");
        user.setSurname("Adm");
        user.setPassword("Passwd.123");

        user = userRepository.save(user);
        log.info("saved example admin " + user.getEmail());
    }
}
