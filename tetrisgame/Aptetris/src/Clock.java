
public class Clock {

	private float movepessecond;
	private long lastupdate;
	private int CycleUsed;
	private float Cyclehave;
	private boolean isPaused;

	public Clock(float gameSpeed) {
		setmovePerSecond(gameSpeed);
		reset();
	}

	public void setmovePerSecond(float gameSpeed) {
		this.movepessecond= (1.0f/gameSpeed)*1000;
	}

	public void reset() {
		this.CycleUsed=0;
		this.Cyclehave=0.0f;
		this.isPaused=false;
		this.lastupdate=getCurrenttime();
	}

	private long getCurrenttime() {
		
		return System.nanoTime()/1000000;
	}

	public void setPause(boolean isPaused) {
		this.isPaused=isPaused;
	}

	public void update() {
		long cur=getCurrenttime();
		float a=(float)(cur-lastupdate)+Cyclehave;
		if(!isPaused) {
			this.CycleUsed += (int)Math.floor(a / movepessecond);
			this.Cyclehave = a % movepessecond;
		}
		this.lastupdate=cur;
	}

	public boolean hascycle() {
		if(CycleUsed>0) {
			this.CycleUsed--;
			return true;
		}
		return false;
	}
	
	
	public boolean seeCycleHave() {
		return  CycleUsed>0;
	}
	public boolean isPasused() {
		return isPaused;
	}
	
	

}
