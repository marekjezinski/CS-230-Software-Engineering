package CS230.npc;

/**
 * Class that creates an exception, inheritance from the IllegalArgumentException superclass
 * @author Wiktoira Bruzgo
 * @version 1.0
 */
public class IllegalDirectionException extends IllegalArgumentException {
    /**
     * Constructs an exception that is thrown when a direction that isn't n-s-e-w
     * @param message - string that allows the user to know what the exception is about
     */
    public IllegalDirectionException(String message) {
        super(message);
    }
}
