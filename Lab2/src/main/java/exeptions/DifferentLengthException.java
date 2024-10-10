package exeptions;

public class DifferentLengthException extends RuntimeException {
    public DifferentLengthException() {
        super();
    }

    public DifferentLengthException(String message) {
        super(message);
    }
}
