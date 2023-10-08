import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Arrays;

import javax.swing.JPanel;




public class SidePanel extends JPanel{

	private static final int TILESIZE = BordPanel.TILESIZE >> 1;
	private static final int SHADEWIDTH = BordPanel.SAYEWIDTH >> 1;
	private static final int TILECOUNT = 5;
	private static final int SQUARECENTERX = 130;
	private static final int SQUARECENTERY = 65;
	private static final int SQUARESIZE = (TILESIZE * TILECOUNT >> 1);
	private static final int SMALLINSET = 20;
	private static final int LARGENSET = 40;
	private static final int STATSINSET = 175;
	private static final int CONTROLSINSET = 300;
	private static final int TEXTSTRIDE = 25;
	private static final Font SMALLFONT = new Font("Tahoma", Font.BOLD, 11);
	private static final Font LARGEFONT = new Font("Tahoma", Font.BOLD, 13);
	private static final Color DRAWCOLOR = new Color(128, 192, 128);
	private Tetrisgame tetris;

	public SidePanel(Tetrisgame tetrisgame) {
		this.tetris=tetrisgame;
		setPreferredSize(new Dimension(200,BordPanel.PANELHEIGHT));
		setBackground(Color.BLACK);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(DRAWCOLOR);
		int m;		
		g.setFont(LARGEFONT);
		g.drawString("state :", SMALLINSET, m=STATSINSET-40);
		g.setFont(SMALLFONT);
		g.drawString("Level : "+tetris.getLevel(), LARGENSET, m+=TEXTSTRIDE);
		g.drawString("Score : "+tetris.getScore(), LARGENSET, m+=TEXTSTRIDE);
		g.drawString("compeleted lines: "+tetris.getline(),LARGENSET, m+=TEXTSTRIDE);
		
		g.setFont(LARGEFONT);
		g.drawString("Scores :", SMALLINSET, m=420);
		g.setFont(SMALLFONT);
		Arrays.sort(tetris.sum);
		g.setFont(new Font("Tahoma"  , Font.BOLD, 15));
		g.drawString("1st "+tetris.sum[99], LARGENSET+10, m += TEXTSTRIDE);
		g.setFont(SMALLFONT);
		g.drawString("2st : "+tetris.sum[98], LARGENSET, m += TEXTSTRIDE);
		g.drawString("3st : "+tetris.sum[97], LARGENSET, m += TEXTSTRIDE);
		g.drawString("4st : "+tetris.sum[96], LARGENSET,m += TEXTSTRIDE);
		g.drawString("5st : "+tetris.sum[95], LARGENSET, m += TEXTSTRIDE);
		g.drawString("6st : "+tetris.sum[94], LARGENSET, m += TEXTSTRIDE);
		g.drawString("7st : "+tetris.sum[93], LARGENSET, m += TEXTSTRIDE);
		g.drawString("8st : "+tetris.sum[92], LARGENSET, m += TEXTSTRIDE);
		g.drawString("9st : "+tetris.sum[91], LARGENSET, m += TEXTSTRIDE);
		g.drawString("10st : "+tetris.sum[90], LARGENSET, m += TEXTSTRIDE);
	
		g.setFont(LARGEFONT);
		g.drawString("Controls :", SMALLINSET, m = CONTROLSINSET-70);
		g.setFont(SMALLFONT);
		g.drawString("A - Move Left", LARGENSET, m += TEXTSTRIDE);
		g.drawString("D - Move Right", LARGENSET, m += TEXTSTRIDE);
		g.drawString("E - Rotate Anticlockwise", LARGENSET, m += TEXTSTRIDE);
		g.drawString("Q - Rotate Clockwise", LARGENSET,m += TEXTSTRIDE);
		g.drawString("S - Drop", LARGENSET, m += TEXTSTRIDE);
		g.drawString("P - Pause Game", LARGENSET, m += TEXTSTRIDE);
		g.drawString("T - renewal", LARGENSET, m += TEXTSTRIDE);

		g.setFont(LARGEFONT);
		g.drawString("Next Piece:", SMALLINSET, 70);
		g.drawRect(SQUARECENTERX - SQUARESIZE, SQUARECENTERY - SQUARESIZE, SQUARESIZE * 2, SQUARESIZE * 2);

		TileType ne=tetris.getNextPieceType();
		if(!tetris.isGameOver() && ne!=null) {

			int col=ne.getCol();
			int row=ne.getRow();
			int andaze=ne.getAndaze();

			int startx=SQUARECENTERX-(col * TILESIZE/2);
			int starty=SQUARECENTERY-(row * TILESIZE/2);

			int top = ne.gettop(0);
			int left = ne.getleft(0);

			for(int row1 = 0; row1 < andaze; row1++) {
				for(int col1 = 0; col1 < andaze; col1++) {
					if(ne.isTile(col1, row1, 0)) {
						drawTile(ne, startx + ((col1 - left) * TILESIZE), starty + ((row1 - top) * TILESIZE), g);
					}
				}
			}		
		}	
	}
	private void drawTile(TileType type, int x, int y, Graphics g) {
		g.setColor(type.getZaminColor());
		g.fillRect(x, y, TILESIZE, TILESIZE);
		
		g.setColor(type.getDarkColor());
		g.fillRect(x, y + TILESIZE - SHADEWIDTH, TILESIZE, SHADEWIDTH);
		g.fillRect(x + TILESIZE - SHADEWIDTH, y, SHADEWIDTH, TILESIZE);
		
		g.setColor(type.getLightColor());
		for(int i = 0; i < SHADEWIDTH; i++) {
			g.drawLine(x, y + i, x + TILESIZE - i - 1, y + i);
			g.drawLine(x + i, y, x + i, y + TILESIZE - i - 1);
		}
	}
}
