package tetrisBlocks;

import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.gdxGames.CubeEscape.Constantes;

public abstract class TetrisBlock implements Constantes {

	public Vector2[] tabBlock;
	public int etatRotation;
	public float fallTime;
	public float moveTime;
	public float rotateTime;
	public float fastFall;
	Random rand;
	
	public TetrisBlock(){
		rand = new Random();
		tabBlock = new Vector2[4];
		tabBlock[0] = new Vector2(WORLD_WIDTH/2, WORLD_HEIGHT-3);
		etatRotation = 0;
		fallTime = 0;
		moveTime = 0;
		rotateTime = 0;
		fastFall = 0;
	}
	
	public void moveTetrisBlockGauche(){
		if (moveTime>MOVE_TIME){
			for (int i=0; i<4; i++){
				tabBlock[i].x -= 1;
			}
			moveTime = 0;
		}

	}
	
	public void moveTetrisBlockDroite(){
		if (moveTime>MOVE_TIME){
			for (int i=0; i<4; i++){
				tabBlock[i].x += 1;
			}
			moveTime = 0;
		}
	}
	
	public void fastFall(){
		for (int i=0; i<4; i++){
			tabBlock[i].y -= 1;
		}
		moveTime = 0;
	}
	
	public void fall(){
		for (int i=0; i<4; i++){
			tabBlock[i].y -= 1;
		}
		fallTime = 0;
	}
	
	public abstract void rotate();
	
	public void update(float delta){
		fallTime += delta;
		moveTime += delta;
		rotateTime += delta;
		fastFall += delta;
	}
}
