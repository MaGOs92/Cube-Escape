package tetrisBlocks;

import com.gdxGames.CubeEscape.Block;

public class BlockS extends TetrisBlock {

	int etatRotation;
	
	public BlockS(){
		super();
		etatRotation = 0;
		tabBlock[1] = new Block(tabBlock[0].position.x - 1, tabBlock[0].position.y);
		tabBlock[2] = new Block(tabBlock[0].position.x, tabBlock[0].position.y + 1);
		tabBlock[3] = new Block(tabBlock[0].position.x + 1, tabBlock[0].position.y + 1);
	}
	
	public void rotate(){
		
		if (etatRotation % 2 == 0){
			tabBlock[1].move(tabBlock[0].position.x, tabBlock[0].position.y-1);
			tabBlock[2].move(tabBlock[0].position.x-1, tabBlock[0].position.y);
			tabBlock[3].move(tabBlock[0].position.x-1, tabBlock[0].position.y+1);
		}
		else{
			tabBlock[1].move(tabBlock[0].position.x-1, tabBlock[0].position.y);
			tabBlock[2].move(tabBlock[0].position.x, tabBlock[0].position.y+1);
			tabBlock[3].move(tabBlock[0].position.x+1, tabBlock[0].position.y+1);
		}		
		etatRotation++;
	}
}
