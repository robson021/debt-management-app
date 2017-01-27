package robert.db.dao;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import robert.db.entities.Asset;
import robert.db.entities.User;
import robert.db.repo.AssetRepository;
import robert.db.repo.UserRepository;
import robert.exeptions.InvalidEmailException;
import robert.exeptions.InvalidPasswordException;
import robert.exeptions.UserNotFoundException;
import robert.web.rest.dto.PaymentDTO;
import robert.web.rest.dto.UserInfoDTO;
import robert.web.rest.dto.asm.PaymentAssembler;
import robert.web.rest.dto.asm.UserAssembler;
import robert.web.session.api.UserDataProvider;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MainDao {

	private static final Logger log = Logger.getLogger(MainDao.class);

	private final UserDataProvider userDataProvider;

	private final UserRepository userRepository;

	private final AssetRepository assetRepository;

	private final EntityManager em;

	@Autowired
	public MainDao(UserDataProvider userDataProvider, UserRepository userRepository, AssetRepository assetRepository, EntityManager em) {
		this.userDataProvider = userDataProvider;
		this.userRepository = userRepository;
		this.assetRepository = assetRepository;
		this.em = em;
	}

	public User saveUser(User user) {
		User saved = this.userRepository.save(user);
		log.info("Saved new user: " + saved.getEmail());
		return saved;
	}

	public void addAssetToUser(String borrowerEmail, String description, double amount, long borrowerId) {
		User user = userRepository.findOne(userDataProvider.getId());

		Asset asset = new Asset();
		asset.setAmount(amount);
		asset.setBorrowerId(borrowerId);
		asset.setDescription(description);

		user.addAsset(asset);
		asset.setUser(user);
		userRepository.save(user);
	}

	public void addAssetToTheUser(PaymentDTO paymentDTO) {
		User user = userRepository.findOne(userDataProvider.getId());
		Asset asset = PaymentAssembler.addDebtorToTheUser(paymentDTO, user);
		assetRepository.save(asset);
		log.info("Added new debtor to the user " + userDataProvider.getEmail());
	}

	public User findUser(long id) {
		return userRepository.findOne(id);
	}

	public User saveUser(UserInfoDTO userDTO) throws InvalidEmailException, InvalidPasswordException {
		User user = UserAssembler.convertDtoToUser(userDTO);
		user = userRepository.save(user);
		log.info("Saved new user: " + user.getEmail());
		return user;
	}

	public User findUser(UserInfoDTO userDTO) throws UserNotFoundException {
		String email = userDTO.getEmail();
		User user = userRepository.findOneByEmail(email);
		if (user == null) {
			throw new UserNotFoundException(email);
		}
		return user;
	}

	public Set<Asset> getMyDebtors() {
		return userRepository.findOne(userDataProvider.getId())
				.getAssets();
	}

	public List<Asset> getMyDebts() {
		return em.createQuery("from Asset as a where a.borrowerId = :id", Asset.class)
				.setParameter("id", userDataProvider.getId())
				.getResultList();
	}


	@PostConstruct
	public void init() throws Exception {
		User user = new User();
		user.setEmail("test@t.pl");
		user.setName("Admin");
		user.setSurname("Adm");
		user.setPassword("Passwd.123");

		userRepository.save(user);
	}
}
