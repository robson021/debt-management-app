package robert.db.svc;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import robert.db.entities.BasicEntity;
import robert.db.entities.User;
import robert.db.entities.Validation;
import robert.db.repo.AssetRepository;
import robert.db.repo.MutualPaymentRepository;
import robert.db.repo.UniversalRepository;
import robert.db.repo.UserRepository;
import robert.exeptions.AuthException;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.UserAssembler;

@Service
@Transactional
public class DatabaseServiceImpl {

	private final UserRepository userRepository;

	private final AssetRepository assetRepository;

	private final UniversalRepository universalRepository;

	private final MutualPaymentRepository mutualPaymentRepository;

	private final EntityManager em;

	public DatabaseServiceImpl(UserRepository userRepository, AssetRepository assetRepository, UniversalRepository universalRepository,
			MutualPaymentRepository mutualPaymentRepository, EntityManager em) {
		this.userRepository = userRepository;
		this.assetRepository = assetRepository;
		this.universalRepository = universalRepository;
		this.mutualPaymentRepository = mutualPaymentRepository;
		this.em = em;
	}

	public <T> T saveEntity(BasicEntity entity, Class<T> entityClass) {
		BasicEntity saved = universalRepository.save(entity);
		return entityClass.cast(saved);
	}

	public BasicEntity saveEntity(BasicEntity entity) {
		return universalRepository.save(entity);
	}

	public void saveNewUser(UserInfoDTO userDTO) {
		User user = UserAssembler.convertDtoToUser(userDTO);
		validateUserEmailAndPassword(user);
		userRepository.save(user);
	}

	public User findUserByEmail(String email) {
		return userRepository.findOneByEmail(email);
	}

	public User findUserById(long id) {
		return userRepository.findOne(id);
	}


	@Cacheable("otherUsers")
	public List<User> findOtherUsersExceptGiven(long userId) {
		return em.createQuery("from User u where u.id != :id order by u.surname", User.class)
				.setParameter("id", userId)
				.getResultList();
	}

	private boolean doesAssetBelongToUser(long assetId, long userId) {
		long id = assetRepository.findOne(assetId)
				.getUser()
				.getId();

		return id == userId;
	}

	private void validateUserEmailAndPassword(User user) {
		boolean isValid = Validation.VALID_PASSWORD_REGEX.matcher(user.getPassword())
				.find() && Validation.VALID_EMAIL_ADDRESS_REGEX.matcher(user.getEmail())
				.find();

		if ( !isValid ) {
			throw new AuthException("Invalid password or email pattern");
		}

	}


}
