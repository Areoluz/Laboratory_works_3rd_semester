package exeptions;

public class NotSortedException extends RuntimeException {
    public NotSortedException() {
        super();
    }

    public NotSortedException(String message) {
        super(message);
    }
}
