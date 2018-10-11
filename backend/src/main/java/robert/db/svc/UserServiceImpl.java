package robert.db.svc;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robert.cache.annotations.users.OtherUsersCache;
import robert.cache.annotations.users.OtherUsersCacheEvict;
import robert.db.entities.User;
import robert.db.repo.UserRepository;
import robert.db.svc.api.UserService;
import robert.exeptions.InvalidPasswordPatternException;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final EntityManager em;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(EntityManager em, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.em = em;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @OtherUsersCacheEvict
    public User saveNewUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    @OtherUsersCacheEvict
    public User saveNewUser(UserInfoDTO userDTO) {
        User user = UserAssembler.convertDtoToUser(userDTO);
        return this.saveNewUser(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    @Override
    public User findUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    @OtherUsersCache
    public List<User> findOtherUsersExceptGiven(long userId) {
        return em.createQuery("from User u where u.id != :id order by u.surname", User.class)
                .setParameter("id", userId)
                .getResultList();
    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void changePassword(long userId, String newPassword) {
        if (newPassword.length() < 5) {
            throw new InvalidPasswordPatternException();
        }
        User user = em.getReference(User.class, userId);
        user.setPassword(passwordEncoder.encode(newPassword));
    }

    @Override
    public void changeEmail(long userId, String newEmail) {
        User user = em.getReference(User.class, userId);
        user.setEmail(newEmail);
    }
}
