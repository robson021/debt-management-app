package robert.exeptions;

public class UserAuthException extends Exception {

	public UserAuthException() {
		super("User is not logged in.");
	}

	public UserAuthException(String email) {
		super("Invalid password for user " + email);
	}
}
