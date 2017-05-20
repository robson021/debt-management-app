package robert.db.entities;

import java.util.regex.Pattern;

public interface Validation {

	Pattern VALID_EMAIL_ADDRESS_REGEX = //
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	Pattern VALID_PASSWORD_REGEX = //
			Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$");
}
