package robert.web.rest.dto;

import lombok.Data;

@Data
public class UserInfoDTO {

    private long id;

    private String name;

    private String surname;

    private String email;

    private String accountNo;

    private String password;

}
