package CS230.npc;

/**
 * Class that creates an exception, inheritance from the IllegalArgumentException superclass
 * @author
 * @version 1.0
 */
public class IllegalDirectionException extends IllegalArgumentException {
    /**
     * Constructs an exception that relates to the NPC's direction
     * @param message - string that allows the user to know what the exception is about
     */
    public IllegalDirectionException(String message) {
        super(message);
    }
}
