package robert.exeptions;

public class InvalidEmailException extends Exception {
    public InvalidEmailException() {
        super("Invalid email pattern.");
    }
}
