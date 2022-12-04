package CS230;
/**
 * Class for the timer in the game
 * @author Tom Stevens
 * @version 1.0
 */


public class Timer {
    private String fileName;
    private int timerDuration;

    /**
     * Constructor that sets up the filename for the timer
     * @param fileName
     */
    public Timer(String fileName) {
        this.fileName = fileName;
    }

    /**
     * timerSetter method reads the text document to get location of the timer
     * @return this.TimerDuration
     */
    public int timerSetter(){

        MapReader t = new MapReader(fileName);
        this.timerDuration = t.getTimer();
        return this.timerDuration;
    }
}
