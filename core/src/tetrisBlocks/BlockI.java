package tetrisBlocks;

import com.badlogic.gdx.math.Vector2;

public class BlockI extends TetrisBlock {
	
	public BlockI(){
		super();
		tabBlock[1] = new Vector2(tabBlock[0].x - 1, tabBlock[0].y);
		tabBlock[2] = new Vector2(tabBlock[0].x + 1, tabBlock[0].y);
		tabBlock[3] = new Vector2(tabBlock[0].x + 2, tabBlock[0].y);
	}
	
	public void rotate(){
		
		if (etatRotation % 2 == 0){
			tabBlock[1].set(tabBlock[0].x, tabBlock[0].y+1);
			tabBlock[2].set(tabBlock[0].x, tabBlock[0].y-1);
			tabBlock[3].set(tabBlock[0].x, tabBlock[0].y-2);
		}
		else{
			tabBlock[1].set(tabBlock[0].x-1, tabBlock[0].y);
			tabBlock[2].set(tabBlock[0].x+1, tabBlock[0].y);
			tabBlock[3].set(tabBlock[0].x+2, tabBlock[0].y);
		}
		etatRotation++;
	}
		
	
}
