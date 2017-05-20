package robert.web.rest.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserInfoDTO {

	private long id;

	private String name;

	private String surname;

	private String email;

	private String accountNo;

	private String password;

}
