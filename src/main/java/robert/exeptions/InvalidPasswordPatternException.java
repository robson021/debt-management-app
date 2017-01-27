package robert.exeptions;

public class InvalidPasswordPatternException extends Exception {
	public InvalidPasswordPatternException() {
		super("Invalid password pattern.");
    }
}
