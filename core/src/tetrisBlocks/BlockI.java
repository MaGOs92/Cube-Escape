package tetrisBlocks;

import com.gdxGames.CubeEscape.Block;

public class BlockI extends TetrisBlock {

	int etatRotation;
	
	public BlockI(){
		super();
		etatRotation = 0;
		tabBlock[1] = new Block(tabBlock[0].position.x - 1, tabBlock[0].position.y);
		tabBlock[2] = new Block(tabBlock[0].position.x + 1, tabBlock[0].position.y);
		tabBlock[3] = new Block(tabBlock[0].position.x + 2, tabBlock[0].position.y);
	}
	
	public void rotate(){
		
		if (etatRotation % 2 == 0){
			tabBlock[1].move(tabBlock[0].position.x, tabBlock[0].position.y+1);
			tabBlock[2].move(tabBlock[0].position.x, tabBlock[0].position.y-1);
			tabBlock[3].move(tabBlock[0].position.x, tabBlock[0].position.y-2);
		}
		else{
			tabBlock[1].move(tabBlock[0].position.x-1, tabBlock[0].position.y);
			tabBlock[2].move(tabBlock[0].position.x+1, tabBlock[0].position.y);
			tabBlock[3].move(tabBlock[0].position.x+2, tabBlock[0].position.y);
		}
		etatRotation++;
	}
		
	
}
