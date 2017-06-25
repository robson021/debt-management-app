package robert.db.svc.api;

import robert.db.entities.User;
import robert.web.rest.dto.UserInfoDTO;

import java.util.List;

public interface UserService {
	User saveNewUser(User user);

	User saveNewUser(UserInfoDTO userDTO);

	User findUserByEmail(String email);

	User findUserById(long id);

	List<User> findOtherUsersExceptGiven(long userId);
}
