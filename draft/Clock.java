public class Clock {
	private Image clockSprite = import clockSprite.jpg;
	private int incrementTime;
	private int xCoord;
	private int yCoord;
	public Clock {
	this.incrementTime = Filereader(getIncrementTime);
	this.xCoord = Filereader(getClockX);
this.yCoord = Filereader(getClockY);
}
private clockObtained(){
	Timer.increaseTime(this.incrementTime);	
}
public getIncrementTime() {
	return this.incrementTime;
}
}
