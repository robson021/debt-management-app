package robert.db.svc;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import robert.db.entities.User;
import robert.db.repo.UserRepository;
import robert.db.svc.api.UserService;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;

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
	public User saveNewUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User saveNewUser(UserInfoDTO userDTO) {
		String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
		userDTO.setPassword(encodedPassword);
		User user = UserAssembler.convertDtoToUser(userDTO);
		return userRepository.save(user);
	}

	@Override
	public User findUserByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}

	@Override
	public User findUserById(long id) {
		return userRepository.findOne(id);
	}

	@Override
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
			throw new IllegalArgumentException("Password is too short");
		}
		User user = em.getReference(User.class, userId);
		user.setPassword(passwordEncoder.encode(newPassword));
	}
}
