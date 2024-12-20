package ui.exeptions;

import java.io.Serial;

public abstract class BasedException extends Exception {
    @Serial
    private static final long serialVersionUID = -3565937979044468013L;

    public BasedException(String message) {
     super(message);
 }
}
