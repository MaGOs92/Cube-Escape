package tetrisBlocks;

import com.badlogic.gdx.math.Vector2;

public class BlockO extends TetrisBlock {
	
	public BlockO(){
		super();
		tabBlock[1] = new Vector2(tabBlock[0].x + 1, tabBlock[0].y);
		tabBlock[2] = new Vector2(tabBlock[0].x, tabBlock[0].y - 1);
		tabBlock[3] = new Vector2(tabBlock[0].x + 1, tabBlock[0].y - 1);
	}
	
	public void rotate(){
	}

}
