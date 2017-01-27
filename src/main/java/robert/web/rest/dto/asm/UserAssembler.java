package robert.web.rest.dto.asm;

import org.springframework.util.CollectionUtils;
import robert.db.entities.User;
import robert.exeptions.InvalidEmailException;
import robert.exeptions.InvalidPasswordException;
import robert.web.rest.dto.UserInfoDTO;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UserAssembler {

	public static User convertDtoToUser(UserInfoDTO userDTO) throws InvalidEmailException, InvalidPasswordException {
		User user = new User();
		user.setName(userDTO.getName());
		user.setSurname(userDTO.getSurname());
		user.setEmail(userDTO.getEmail());
		user.setPassword(userDTO.getPassword());

		return user;
	}

	public static UserInfoDTO convertToUserInfoDTO(User user) {
		UserInfoDTO userInfoDTO = new UserInfoDTO();
		userInfoDTO.setName(user.getName());
		userInfoDTO.setSurname(user.getSurname());
		userInfoDTO.setEmail(user.getEmail());
		userInfoDTO.setPassword(user.getPassword());
		return userInfoDTO;
	}

	public static List<UserInfoDTO> convertToUserInfoDTOs(Collection<User> users) {
		if (CollectionUtils.isEmpty(users))
			return Collections.emptyList();

		return users.stream()
				.map(UserAssembler::convertToUserInfoDTO)
				.collect(Collectors.toList());
	}

}
