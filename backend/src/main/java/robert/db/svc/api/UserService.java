package robert.db.svc.api;

import java.util.List;

import robert.db.entities.User;
import robert.web.rest.dto.UserInfoDTO;

public interface UserService {
	User saveNewUser(User user);

	void saveNewUser(UserInfoDTO userDTO);

	User findUserByEmail(String email);

	User findUserById(long id);

	List<User> findOtherUsersExceptGiven(long userId);
}
