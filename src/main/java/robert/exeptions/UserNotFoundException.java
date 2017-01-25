package robert.exeptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String email) {
        super("User " + email + " not found.");
    }
}
