package tetrisBlocks;

import com.gdxGames.CubeEscape.Block;

public class BlockO extends TetrisBlock {
	
	public BlockO(){
		super();
		tabBlock[1] = new Block(tabBlock[0].position.x + 1, tabBlock[0].position.y);
		tabBlock[2] = new Block(tabBlock[0].position.x, tabBlock[0].position.y - 1);
		tabBlock[3] = new Block(tabBlock[0].position.x + 1, tabBlock[0].position.y - 1);
	}
	
	public void rotate(){
		
	}

}
