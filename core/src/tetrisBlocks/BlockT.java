package tetrisBlocks;

import com.badlogic.gdx.math.Vector2;

public class BlockT extends TetrisBlock {
	
	public BlockT(){
		super();
		tabBlock[1] = new Vector2(tabBlock[0].x - 1, tabBlock[0].y);
		tabBlock[2] = new Vector2(tabBlock[0].x, tabBlock[0].y - 1);
		tabBlock[3] = new Vector2(tabBlock[0].x + 1, tabBlock[0].y);
	}
	
	public void rotate(){
		
		etatRotation++;
		if (etatRotation > 3) etatRotation = 0;
		
		switch(etatRotation){
		case 1:
			tabBlock[1].set(tabBlock[0].x, tabBlock[0].y+1);
			tabBlock[2].set(tabBlock[0].x-1, tabBlock[0].y);
			tabBlock[3].set(tabBlock[0].x, tabBlock[0].y-1);
			break;
		case 2:
			tabBlock[1].set(tabBlock[0].x+1, tabBlock[0].y);
			tabBlock[2].set(tabBlock[0].x, tabBlock[0].y+1);
			tabBlock[3].set(tabBlock[0].x-1, tabBlock[0].y);
			break;
		case 3:
			tabBlock[1].set(tabBlock[0].x, tabBlock[0].y-1);
			tabBlock[2].set(tabBlock[0].x+1, tabBlock[0].y);
			tabBlock[3].set(tabBlock[0].x, tabBlock[0].y+1);
			break;
		default:
			tabBlock[1].set(tabBlock[0].x-1, tabBlock[0].y);
			tabBlock[2].set(tabBlock[0].x, tabBlock[0].y-1);
			tabBlock[3].set(tabBlock[0].x+1, tabBlock[0].y);
			break;
		}
	}
}
