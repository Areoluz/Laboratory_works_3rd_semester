package exceptions;

public class MathOperationException extends RuntimeException {
    public MathOperationException(String message) {
        super(message);
    }

    public MathOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
