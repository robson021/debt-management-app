package robert.exeptions;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException() {
        super("Invalid password pattern.");
    }
}
