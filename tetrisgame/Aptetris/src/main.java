import java.util.Timer;
import java.util.TimerTask;

public class main {
	public static void main(String[] args) throws Exception {
		Tetrisgame.read();
		Tetrisgame a=new Tetrisgame();
		BackgroundMusic bm = new BackgroundMusic("1.wav");
		Timer t=new Timer();
		t.schedule(new TimerTask() {
			@Override				
			public void run() {
				bm.start();
			}
		},0 ,292000);
		a.startGame();
	}
}
