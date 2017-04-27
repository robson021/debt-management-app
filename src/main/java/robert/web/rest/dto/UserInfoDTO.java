package robert.web.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {

    private long id;

    private String name;

    private String surname;

    private String email;

    private String password;
}
