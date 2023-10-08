import java.awt.Color;



public enum TileType {
	
	TypeA(new Color(BordPanel.COLORMIN, BordPanel.COLORMAX, BordPanel.COLORMAX), 4, 4, 1, new boolean[][] {
		{
			false,	false,	false,	false,
			true,	true,	true,	true,
			false,	false,	false,	false,
			false,	false,	false,	false,
		},
		{
			false,	false,	true,	false,
			false,	false,	true,	false,
			false,	false,	true,	false,
			false,	false,	true,	false,
		},
		{
			false,	false,	false,	false,
			false,	false,	false,	false,
			true,	true,	true,	true,
			false,	false,	false,	false,
		},
		{
			false,	true,	false,	false,
			false,	true,	false,	false,
			false,	true,	false,	false,
			false,	true,	false,	false,
		}
	}),

	TypeB(new Color(BordPanel.COLORMIN, BordPanel.COLORMIN, BordPanel.COLORMAX), 3, 3, 2, new boolean[][] {
		{
			true,	false,	false,
			true,	true,	true,
			false,	false,	false,
		},
		{
			false,	true,	true,
			false,	true,	false,
			false,	true,	false,
		},
		{
			false,	false,	false,
			true,	true,	true,
			false,	false,	true,
		},
		{
			false,	true,	false,
			false,	true,	false,
			true,	true,	false,
		}
	}),
	

	TypeC(new Color(BordPanel.COLORMAX, 127, BordPanel.COLORMIN), 3, 3, 2, new boolean[][] {
		{
			false,	false,	true,
			true,	true,	true,
			false,	false,	false,
		},
		{
			false,	true,	false,
			false,	true,	false,
			false,	true,	true,
		},
		{
			false,	false,	false,
			true,	true,	true,
			true,	false,	false,
		},
		{
			true,	true,	false,
			false,	true,	false,
			false,	true,	false,
		}
	}),
	

	TypeD(new Color(BordPanel.COLORMAX, BordPanel.COLORMAX, BordPanel.COLORMIN), 2, 2, 2, new boolean[][] {
		{
			true,	true,
			true,	true,
		},
		{
			true,	true,
			true,	true,
		},
		{	
			true,	true,
			true,	true,
		},
		{
			true,	true,
			true,	true,
		}
	}),
	

	TypeE(new Color(BordPanel.COLORMIN, BordPanel.COLORMAX, BordPanel.COLORMIN), 3, 3, 2, new boolean[][] {
		{
			false,	true,	true,
			true,	true,	false,
			false,	false,	false,
		},
		{
			false,	true,	false,
			false,	true,	true,
			false,	false,	true,
		},
		{
			false,	false,	false,
			false,	true,	true,
			true,	true,	false,
		},
		{
			true,	false,	false,
			true,	true,	false,
			false,	true,	false,
		}
	}),
	

	TypeF(new Color(128, BordPanel.COLORMIN, 128), 3, 3, 2, new boolean[][] {
		{
			false,	true,	false,
			true,	true,	true,
			false,	false,	false,
		},
		{
			false,	true,	false,
			false,	true,	true,
			false,	true,	false,
		},
		{
			false,	false,	false,
			true,	true,	true,
			false,	true,	false,
		},
		{
			false,	true,	false,
			true,	true,	false,
			false,	true,	false,
		}
	}),
	

	TypeG(new Color(BordPanel.COLORMAX, BordPanel.COLORMIN, BordPanel.COLORMIN), 3, 3, 2, new boolean[][] {
		{
			true,	true,	false,
			false,	true,	true,
			false,	false,	false,
		},
		{
			false,	false,	true,
			false,	true,	true,
			false,	true,	false,
		},
		{
			false,	false,	false,
			true,	true,	false,
			false,	true,	true,
		},
		{
			false,	true,	false,
			true,	true,	false,
			true,	false,	false,
		}
	});
	
	private Color zaminColor;
	private Color lightColor;
	private Color darkColor;
	private int  ChobCol;
	private int  ChobRow;
	private int  Andaze;
	private int  Col;
	private int  Row;
	private boolean [][] tile;
	
	
	private TileType(Color color, int andaze, int col, int row, boolean[][] tile) {
		this.zaminColor = color;
		this.lightColor = color.brighter();
		this.darkColor = color.darker();
		this.Andaze = andaze;
		this.tile = tile;
		this.Col = col;
		this.Row = row;
		
		this.ChobCol = 5 - (andaze >> 1);
		this.ChobRow = gettop(0);
	}
	
	
	
	
	

	public Color getZaminColor() {
		return zaminColor;
	}

	public int getChobCol() {
		return ChobCol;
	}


	public int getChobRow() {
		return ChobRow;
	}
	public int getAndaze() {
		return Andaze;
	}


	public int getCol() {
		return Col;
	}


	public int getRow() {
		return Row;
	}


	public boolean[][] getTile() {
		return tile;
	}


	public Color getLightColor() {
		return lightColor;
	}

	public Color getDarkColor() {
		return darkColor;
	}
	
	
	public boolean isTile( int x , int y, int charkhesh) {
		return tile[charkhesh][y * Andaze + x];
	}
	
	public int getleft(int charkhesh) {
		for (int i = 0; i < Andaze; i++) {
			for (int j = 0; j < Andaze; j++) {
				if(isTile(i, j, charkhesh))
					return i;
			}
		}
		return -1;
	}
	
	public int getright(int charkhesh) {
		for (int i = Andaze-1; i >=0 ; i--) {
			for (int j = 0; j < Andaze; j++) {
				if(isTile(i, j, charkhesh))
					return Andaze-i;
			}
		}
		return -1;
	}
	
	public int gettop(int charkhesh) {
		for (int j = 0; j < Andaze; j++) {
			for (int i 	= 0; i < Andaze; i++) {
				if(isTile(j, j, charkhesh))
					return j;
			}
		}
		return -1;
	}
	
	public int getbottom(int charkhesh) {
		for (int j = Andaze-1	; j>=0 ; j--) {
			for (int i = 0; i < Andaze; i++) {
				if(isTile(i, j, charkhesh))
					return Andaze-j;
			}
		}
		return -1;
	}

}
