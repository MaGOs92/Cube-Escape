package tetrisBlocks;

import java.util.Random;

import com.gdxGames.CubeEscape.Block;
import com.gdxGames.CubeEscape.Constantes;

public abstract class TetrisBlock implements Constantes {

	public Block[] tabBlock;
	float fallTime;
	float moveTime;
	float rotateTime;
	float fastFall;
	Random rand;
	
	public TetrisBlock(){
		rand = new Random();
		tabBlock = new Block[4];
		tabBlock[0] = new Block(WORLD_WIDTH/2, WORLD_HEIGHT-2);
		fallTime = 0;
		moveTime = 0;
		rotateTime = 0;
		fastFall = 0;
	}
	
	public void moveTetrisBlockGauche(){
		if (moveTime>MOVE_TIME){
			for (int i=0; i<4; i++){
				tabBlock[i].move(tabBlock[i].position.x-1, tabBlock[i].position.y);
			}
			moveTime = 0;
		}

	}
	
	public void moveTetrisBlockDroite(){
		if (moveTime>MOVE_TIME){
			for (int i=0; i<4; i++){
				tabBlock[i].move(tabBlock[i].position.x+1, tabBlock[i].position.y);
			}
			moveTime = 0;
		}
	}
	
	public void fastFall(){
		if (fastFall>FASTFALL_TIME){
			for (int i=0; i<4; i++){
				tabBlock[i].move(tabBlock[i].position.x, tabBlock[i].position.y-1);
			}
			moveTime = 0;
		}
	}
	
	public void checkRotation(){
		if (rotateTime > ROTATE_TIME){
			rotate();
		}
		rotateTime = 0;
	}
	
	public abstract void rotate();
	
	public void update(float delta){
		
		fallTime += delta;
		moveTime += delta;
		rotateTime += delta;
		fastFall += delta;
		
		if (fallTime > FALL_TIME){
			for (int i=0; i<4; i++){
				tabBlock[i].move(tabBlock[i].position.x, tabBlock[i].position.y-1);
			}
			fallTime = 0;
		}
	}
}
