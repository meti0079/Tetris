import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;


public class BordPanel extends JPanel{


	public static int COLORMIN=35;
	public static int COLORMAX=220;
	private static int BOARDWIDTH=5;
	public static int COLCOUNT=10;
	private static int VISIBLEROW=20;
	private static int HIDDENROW=2;
	public static int ROWCOUNT=22;
	public static int TILESIZE=34;
	public static int SAYEWIDTH=4;
	public static int PANELWIDTH = COLCOUNT * TILESIZE +10;
	public static int PANELHEIGHT= VISIBLEROW * TILESIZE +10;
	private static Font LARGE=new Font("Tahoma", Font.ITALIC, 16);  
	private static Font SMALL=new Font("Tahoma", Font.ITALIC, 12);  
	private static int CENTER_X = COLCOUNT * TILESIZE /2;
	private static int CENTER_Y = VISIBLEROW * TILESIZE / 2; 
	private Tetrisgame tetris;

	private TileType[][] tiles;


	public BordPanel(Tetrisgame tetrisgame) {
		this.tetris=tetrisgame;
		this.tiles=new TileType[ROWCOUNT][COLCOUNT];
		setPreferredSize(new Dimension(PANELWIDTH, PANELHEIGHT));
		setBackground(Color.GRAY);
	}

	public boolean isKhaly(TileType tile, int x, int y, int chaekhesh) {

		if(x < -tile.getleft(chaekhesh) || x+tile.getAndaze() - tile.getright(chaekhesh) >=COLCOUNT) {
			return false;			
		}

		if(y < -tile.gettop(chaekhesh) || y + tile.getAndaze() - tile.getbottom(chaekhesh) >=ROWCOUNT) {
			return false;			
		}
		for(int col = 0; col < tile.getAndaze(); col++) {
			for(int row = 0; row < tile.getAndaze(); row++) {
				if(tile.isTile(col, row, chaekhesh) && isOccupied(x + col, y + row)) {
					return false;
				}
			}
		}
		return true;


	}

	public void addPiece(TileType tile, int x, int y, int chaekhesh) {
		for(int col = 0; col < tile.getAndaze(); col++) {
			for(int row = 0; row < tile.getAndaze(); row++) {
				if(tile.isTile(col, row, chaekhesh)) {
					setTile(col + x, row + y, tile);
				}
			}
		}
	}

	public int checkline() {

		int compelet=0;
		for (int row = 0; row < ROWCOUNT; row++) {
			if(check(row)) {
				compelet++;
			}
		}
		return compelet;
	}
	private boolean check(int r) {

		for (int col = 0; col < COLCOUNT; col++) {
			if(!isOccupied(col,r)) {
				return false;
			}
		}

		for (int row = r - 1; row >=0	 ; row--) {
			for (int col = 0; col < COLCOUNT; col++) {
				setTile(col,row+1,getTile(col,row));
			}
		}

		return true;
	}


	public void clear() {
		for(int i = 0; i < ROWCOUNT; i++) {
			for(int j = 0; j < COLCOUNT; j++) {
				tiles[i][j] = null;
			}
		}
	}

	private boolean isOccupied(int x, int y) {
		return tiles[y][x]!=null;
	}

	private void setTile(int x, int y, TileType tile) {
		tiles[y][x]=tile;
	}
	private TileType getTile(int x, int y) {
		return tiles[y][x];
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		g.translate(BOARDWIDTH, BOARDWIDTH);
		if(tetris.isPaused()) {
			g.setFont(LARGE);
			g.setColor(Color.RED);
			g.drawString("PAUSED", CENTER_X -g.getFontMetrics().stringWidth("PAUSED")/2	, CENTER_Y);
		}else if(tetris.isNewGame()) {
			g.setFont(LARGE);
			g.setColor(Color.WHITE);
			String m= "TETRIS";
			g.drawString(m, CENTER_X - g.getFontMetrics().stringWidth(m)/2, 150);
			g.setFont(SMALL);
			m="Press Enter to Play" ;
			g.drawString(m, CENTER_X - g.getFontMetrics().stringWidth(m) / 2, 300);



		}else if( tetris.isGameOver()) {
			g.setFont(LARGE);
			g.setColor(Color.WHITE);
			String m="GAME OVER";
			g.drawString(m, CENTER_X - g.getFontMetrics().stringWidth(m)/2, 150);
			g.setFont(SMALL);
			m="Press Enter to Play  Again" ;
			g.drawString(m, CENTER_X - g.getFontMetrics().stringWidth(m) / 2, 300);

		}else {
			for(int x = 0; x < COLCOUNT; x++) {
				for(int y = HIDDENROW; y < ROWCOUNT; y++) {
					TileType tile = getTile(x, y);
					if(tile != null) {
						drawTile(tile, x * TILESIZE, (y - HIDDENROW) * TILESIZE, g);
					}
				}
			}

			TileType til=tetris.getPieceType();
			int c=tetris.getPieceCol();
			int r=tetris.getPieceRow();
			int ch=tetris.getchaekhesh();

			//////draw pice 
			for(int col = 0; col < til.getAndaze(); col++) {
				for(int row = 0; row < til.getAndaze(); row++) {
					if(r + row >= 2 && til.isTile(col, row, ch)) {
						drawTile(til, (c + col) * TILESIZE, (r + row - HIDDENROW) * TILESIZE, g);
					}
				}
			}

			///////draw ghost
			Color zamin=til.getZaminColor();
			zamin=new Color(zamin.getRed(),zamin.getGreen(),zamin.getBlue(),20);
			for(int low=r;low<ROWCOUNT;low++) {
				if(isKhaly(til, c, low, ch))
					continue;

				low--;
				/////draw ghost piece
				for(int col = 0; col < til.getAndaze(); col++) {
					for(int row = 0; row < til.getAndaze(); row++) {
						if(low + row >= 2 && til.isTile(col, row, ch)) {
							drawTile(zamin, zamin.brighter(), zamin.darker(), (c + col) * TILESIZE, (low + row - HIDDENROW) * TILESIZE, g);
						}
					}
				}
				break;
			}
			g.setColor(Color.DARK_GRAY);
			for(int x = 0; x < COLCOUNT; x++) {
				for(int y = 0; y < VISIBLEROW; y++) {
					g.drawLine(0, y * TILESIZE, COLCOUNT * TILESIZE, y * TILESIZE);
					g.drawLine(x * TILESIZE, 0, x * TILESIZE, VISIBLEROW * TILESIZE);
				}
			}		
		}

		g.setColor(Color.WHITE);
		g.drawRect(0, 0, TILESIZE * COLCOUNT, TILESIZE * VISIBLEROW);
	}

	private void drawTile(TileType tile, int x, int y, Graphics g) {
		drawTile(tile.getZaminColor(),tile.getLightColor(),tile.getDarkColor(),x,y,g);
	}

	private void drawTile( Color  cl, Color lightColor, Color darkColor, int x, int y, Graphics g) {
		g.setColor(cl);
		g.fillRect(x, y, TILESIZE, TILESIZE);

		g.setColor(Color.BLACK);
		g.fillRect(x, y + TILESIZE - SAYEWIDTH, TILESIZE, SAYEWIDTH);
		g.fillRect(x + TILESIZE - SAYEWIDTH, y, SAYEWIDTH	, TILESIZE);

		g.setColor(Color.BLACK);

		for(int i = 0; i < SAYEWIDTH; i++) {
			g.drawLine(x, y + i, x + TILESIZE - i- 1, y + i);
			g.drawLine(x + i, y, x + i, y + TILESIZE - i - 1);
		}	
	}



}
