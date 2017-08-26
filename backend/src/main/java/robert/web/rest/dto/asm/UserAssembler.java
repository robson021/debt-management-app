package robert.web.rest.dto.asm;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import robert.db.entities.User;
import robert.web.rest.dto.UserInfoDTO;

public abstract class UserAssembler {

    public static User convertDtoToUser(UserInfoDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAccountNo(userDTO.getAccountNo());

        return user;
    }

    public static UserInfoDTO convertToUserInfoDTO(User user) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setId(user.getId());
        userInfoDTO.setName(user.getName());
        userInfoDTO.setSurname(user.getSurname());
        userInfoDTO.setEmail(user.getEmail());
        userInfoDTO.setAccountNo(user.getAccountNo());

        return userInfoDTO;
    }

    public static List<UserInfoDTO> convertToUserInfoDTOs(Collection<User> users) {
        if ( CollectionUtils.isEmpty(users) )
            return Collections.emptyList();

        return users.stream()
                .map(UserAssembler::convertToUserInfoDTO)
                .collect(Collectors.toList());
    }

}
