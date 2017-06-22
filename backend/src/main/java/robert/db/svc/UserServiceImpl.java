package robert.db.svc;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

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

	public UserServiceImpl(EntityManager em, UserRepository userRepository) {
		this.em = em;
		this.userRepository = userRepository;
	}

	@Override
	public User saveNewUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void saveNewUser(UserInfoDTO userDTO) {
		User user = UserAssembler.convertDtoToUser(userDTO);
		userRepository.save(user);
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
}
