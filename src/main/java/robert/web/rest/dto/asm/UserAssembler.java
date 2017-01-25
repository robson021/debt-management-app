package robert.web.rest.dto.asm;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import robert.db.entities.User;
import robert.exeptions.InvalidEmailException;
import robert.exeptions.InvalidPasswordException;
import robert.web.rest.dto.UserInfoDTO;

public class UserAssembler {

    public static User convertDtoToUser(UserInfoDTO userDTO) throws InvalidEmailException, InvalidPasswordException {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return user;
    }

    public static List<UserInfoDTO> convertToDto(Collection<User> users) {
        return users.stream()
                .map(user -> {
                    UserInfoDTO userInfoDTO = new UserInfoDTO();
                    userInfoDTO.setName(user.getName());
                    userInfoDTO.setSurname(user.getSurname());
                    userInfoDTO.setEmail(user.getEmail());
                    userInfoDTO.setPassword(user.getPassword());
                    return userInfoDTO;
                })
                .collect(Collectors.toList());
    }

}
