package CS230;

public class GameTimer {
    private int duration;

    public GameTimer(int duration) {
        this.duration = duration;
    }

    public int timer() throws InterruptedException {
        this.duration = this.duration - 1;
        //thread.sleep(100);
        return this.duration;
    }
}
