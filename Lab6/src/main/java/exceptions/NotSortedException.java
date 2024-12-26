package exceptions;

public class NotSortedException extends RuntimeException {
    public NotSortedException() {
        super();
    }

    public NotSortedException(String message) {
        super(message);
    }
}
