package tanka;

/**
 * Exception thrown when a user command is invalid or a storage/parse error occurs.
 */
public class TankaException extends Exception {

    /**
     * Creates a TankaException with the given message.
     *
     * @param message the error message
     */
    public TankaException(String message) {
        super(message);
    }
}
