package CS230;

import java.util.ArrayList;

public class Timer {
    private String fileName;
    private int timerDuration;

    public Timer(String fileName) {
        this.fileName = fileName;
    }

    public int timerSetter(){

        MapReader t = new MapReader(fileName);
        this.timerDuration = t.getTimer();
        return this.timerDuration;
    }
}
