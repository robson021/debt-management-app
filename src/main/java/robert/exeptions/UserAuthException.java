package robert.exeptions;

public class UserAuthException extends Exception {

    public UserAuthException() {
        super("User is not logged in.");
    }

}
