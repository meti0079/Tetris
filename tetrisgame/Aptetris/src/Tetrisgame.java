import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.omg.CORBA.PUBLIC_MEMBER;

import com.google.gson.Gson;


import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class Tetrisgame extends JFrame{

	private static final long Frametime=1000/50;
	public static int [] sum=new int[100];
	public static int co=0;
	private BordPanel bord;
	private SidePanel Side;
	private boolean isPaused;
	private boolean isNewGame;
	private boolean isGameOver;
	private int level;
	private int score;
	private int li;
	private Random random;
	private Clock  timer;
	private TileType Tile;
	private TileType newType;
	private int Col;
	private int Row;
	private int chaekhesh;
	private int timebrake;
	private float gameSpeed;


	public Tetrisgame() throws IOException {

		/////////set up main window
		super("Tetris");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		this.bord=new BordPanel(this);
		this.Side=new SidePanel(this);
		add(bord,BorderLayout.CENTER);
		add(Side,BorderLayout.EAST);

		////// set up bar
		JMenuBar bar=new JMenuBar();
		JMenu file=new JMenu("File");
		JMenu sho=new JMenu("Help");

		JMenuItem about=new JMenuItem("about game");
		JMenuItem neww=new JMenuItem("new");
		JMenuItem save=new JMenuItem("Save");
		JMenuItem exit=new JMenuItem("Exit");

		sho.add(about);
		file.add(neww);
		file.addSeparator();
		file.add(save);
		file.addSeparator();
		file.add(exit);
		bar.add(file);
		bar.add(sho);

		neww.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				restartGame();

			}
		});
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.ALT_MASK));
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				int actin=JOptionPane.showConfirmDialog(Tetrisgame.this,
						"Do you really want to exit ????",
						"Confirm Exit",JOptionPane.OK_CANCEL_OPTION);

				if(actin==JOptionPane.OK_OPTION) {
					try {
						save();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
		});
		setJMenuBar(bar);



		addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent a) {
				switch (a.getKeyCode()) {
				case KeyEvent.VK_S:
					timer.setmovePerSecond(gameSpeed);
					timer.reset();
					break;
				}
			}


			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {

				case KeyEvent.VK_T:
					Row=0;
					break;

				case KeyEvent.VK_S:
					if(!isPaused && timebrake==0)
						timer.setmovePerSecond((float) 25.0);
					break;
				case KeyEvent.VK_A:
					if (!isPaused && bord.isKhaly(Tile,Col-1,Row,chaekhesh)) 
						Col--;

					break;
				case KeyEvent.VK_D:
					if (!isPaused && bord.isKhaly(Tile,Col+1,Row,chaekhesh)) 
						Col++;				
					break;
				case KeyEvent.VK_Q:
					if(!isPaused) 
						Charkhandan((chaekhesh == 0) ? 3 : chaekhesh - 1);
					break;
				case KeyEvent.VK_E:
					if(!isPaused) 
						Charkhandan((chaekhesh == 3) ? 0 : chaekhesh + 1);
					break;

				case KeyEvent.VK_P:
					if(!isGameOver && !isNewGame) {
						isPaused=!isPaused;
						timer.setPause(isPaused);
					}
					break;
				case KeyEvent.VK_ENTER:
					if(isGameOver || isNewGame) {
						if(isGameOver) {
							sum[co]=getScore();
							co++;
						}

						restartGame();
					}
					break;			
				}	
			}


			public void keyTyped(KeyEvent arg0) {			
			}
		});
		pack();
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public void startGame() throws Exception {
		this.random=new Random(6);
		this.isNewGame=true;
		this.gameSpeed=1.0f;
		this.timer=new Clock(gameSpeed);
		timer.setPause(true);
		int i=1;
		while(true) {

			long s=System.nanoTime();
			timer.update();

			if(timer.hascycle()) {
				updateGame();
			}

			if(timebrake > 0)
				timebrake--;

			paintGame();

			long e = ( System.nanoTime()-s)/1000000;

			if(e<Frametime) {
				Thread.sleep(Frametime-e);
			}		
		}	

	}


	private void paintGame() {
		bord.repaint();
		Side.repaint();
	}

	private void updateGame() {
		if(bord.isKhaly(Tile, Col, Row+1, chaekhesh)) {
			Row++;
		}else {
			bord.addPiece(Tile,Col,Row,chaekhesh);
			int Pak=bord.checkline();
			if(Pak>0) {
				score+=10<<Pak;
				li++;
			}
			//			gameSpeed+=0.025f;
			//			timer.setmovePerSecond(gameSpeed);
			timer.reset();
			timebrake=25;

			level=(int) (score/150);
			writeNextPiece();
		}
	}



	public void writeNextPiece(){
		this.Tile=newType;
		this.Col=Tile.getCol();
		this.Row=Tile.getRow();
		this.chaekhesh=0;
		this.newType=TileType.values()[random.nextInt(6)];

		if(!bord.isKhaly(Tile, Col, Row, chaekhesh)) {
			this.isGameOver=true;
			timer.setPause(true);
		}	
	}

	private void restartGame() {
		this.level=1;
		this.score=0;
		this.gameSpeed=1.0f;
		this.newType=TileType.values()[random.nextInt(6)];
		this.isNewGame=false;
		this.isGameOver=false;
		bord.clear();
		timer.reset();
		timer.setmovePerSecond(gameSpeed);
		writeNextPiece();

	}
	private void Charkhandan(int i) {
		int newcol=Col;
		int newrow=Row;
		int left=Tile.getleft(i);
		int right=Tile.getright(i);
		int top=Tile.gettop(i);
		int bottom=Tile.getbottom(i);

		if(Col<-left ) {
			newcol-=Col-left;
		}else if( Col + Tile.getAndaze() - right >= BordPanel.COLCOUNT) {
			newcol	-= ( Col + Tile.getAndaze() - right ) - BordPanel.COLCOUNT + 1 ;
		}


		if(Row <  -top) {
			newrow -= Row - top;			
		}else if(Row + Tile.getAndaze() - bottom >= BordPanel.ROWCOUNT) {	
			newrow -= ( Row + Tile.getAndaze() - bottom ) - BordPanel.ROWCOUNT + 1;
		}

		if(bord.isKhaly(Tile, newcol, newrow, i)) {
			chaekhesh =i;
			Row = newrow ;
			Col =newcol;
		}
	}

	public boolean isPaused() {
		return isPaused;
	}

	public boolean isGameOver() {
		return isGameOver;
	}


	public boolean isNewGame() {
		return isNewGame;
	}


	public int getScore() {
		return score;
	}
	public int getline() {
		return li;
	}

	public int getLevel() {
		return level;
	}


	public TileType getPieceType() {
		return Tile;
	}


	public TileType getNextPieceType() {
		return newType;
	}


	public int getPieceCol() {
		return Col;
	}


	public int getPieceRow() {
		return Row;
	}


	public int getchaekhesh() {
		return chaekhesh;
	}

	public void save() throws IOException {
		FileWriter ff=new FileWriter("scorse.txt");
		for (int i = 0; i < 10; i++) {
			String a=sum[90+i]+"\n";
			ff.write(a);
		}
		ff.close();
	}
	public static void read() throws FileNotFoundException {
		File ff=new File("scorse.txt");
		Scanner sca=new Scanner(ff);
		for (int i = 0; i < 10; i++) {
			String a=sca.nextLine();
			int b=0;
			for (int j =0; j <a.length(); j++) {
				b=b*10+(a.charAt(j)-'0')*10;
			}
			sum[90+i]=b/10;
		}
	}


}
