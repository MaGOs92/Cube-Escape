package tetrisBlocks;

import com.gdxGames.CubeEscape.Block;

public class BlockJ extends TetrisBlock {

	int etatRotation;
	
	public BlockJ(){
		super();
		etatRotation = 0;
		tabBlock[1] = new Block(tabBlock[0].position.x - 1, tabBlock[0].position.y);
		tabBlock[2] = new Block(tabBlock[0].position.x + 1, tabBlock[0].position.y);
		tabBlock[3] = new Block(tabBlock[0].position.x + 1, tabBlock[0].position.y - 1);
	}
	
	public void rotate(){
		
		etatRotation++;
		if (etatRotation > 3) etatRotation = 0;
		
		switch(etatRotation){
		case 1:
			tabBlock[1].move(tabBlock[0].position.x, tabBlock[0].position.y+1);
			tabBlock[2].move(tabBlock[0].position.x, tabBlock[0].position.y-1);
			tabBlock[3].move(tabBlock[0].position.x-1, tabBlock[0].position.y-1);
			break;
		case 2:
			tabBlock[1].move(tabBlock[0].position.x+1, tabBlock[0].position.y);
			tabBlock[2].move(tabBlock[0].position.x-1, tabBlock[0].position.y);
			tabBlock[3].move(tabBlock[0].position.x-1, tabBlock[0].position.y+1);
			break;
		case 3:
			tabBlock[1].move(tabBlock[0].position.x, tabBlock[0].position.y-1);
			tabBlock[2].move(tabBlock[0].position.x, tabBlock[0].position.y+1);
			tabBlock[3].move(tabBlock[0].position.x+1, tabBlock[0].position.y+1);
			break;
		default:
			tabBlock[1].move(tabBlock[0].position.x-1, tabBlock[0].position.y);
			tabBlock[2].move(tabBlock[0].position.x+1, tabBlock[0].position.y);
			tabBlock[3].move(tabBlock[0].position.x+1, tabBlock[0].position.y-1);
			break;
		}
		
	}
}
