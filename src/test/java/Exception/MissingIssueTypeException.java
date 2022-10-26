package Exception;

public class MissingIssueTypeException extends RuntimeException {
    public MissingIssueTypeException(String message) {
        super(message);
    }
}
