package com.gdxGames.CubeEscape;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ET extends DynamicGameObject implements Constantes{
	
	public int etat;
	public boolean isOnFloor;
	float tempsEtat;
	boolean isJumping;
	float jumpTime;
	Rectangle etCrashTest;

	public ET(float x, float y) {
		super(x, y, ET_WIDTH, ET_HEIGHT);
		etat = ET_IDLE;
		tempsEtat = 0;
		isJumping = false;
		jumpTime = 0;
		etCrashTest = new Rectangle(bounds.x, bounds.y, bounds.width, bounds.height);
	}
	
	private void updateJump(float delta){
		if (isJumping){
			accel.y = 4;
			jumpTime += delta;
			if (jumpTime > JUMP_TIME){
				isJumping = false;
				jumpTime = 0;
			}
		}
		else {
			accel.y = 0;
		}
		accel.y += gravite;	
	}
	
	private void updateCollisions(float delta, World world){
		
        etCrashTest.set(bounds.x, bounds.y, bounds.width, bounds.height);
        int startX, endX;
        int startY = (int) bounds.y;
        int endY = (int) (bounds.y + bounds.height);
        
        if (velocity.x < 0) {
            startX = endX = (int) Math.floor(bounds.x + velocity.x * delta);
        } else {
            startX = endX = (int) Math.floor(bounds.x + bounds.width + velocity.x * delta);
        }
      
        populateCollidableBlocks(startX, endX, startY, endY, world);
      
        etCrashTest.x += velocity.x * delta;
        for (Vector2 block : world.collidableBlocks) {
            if (block == null) continue;
            if (etCrashTest.overlaps(world.graphe.getGraphe().get(block).getBlock().bounds) && 
            		world.graphe.getGraphe().get(block).isBlock() ||
            		etCrashTest.overlaps(world.graphe.getGraphe().get(block).getBlock().bounds) && 
            		world.graphe.getGraphe().get(block).isTetrisBlock()) {
                velocity.x = 0;
                if (isOnFloor){
                	Assets.jumpSound.play(0.1f);
                	isJumping = true;
                }
                break;
            }
        }
        etCrashTest.x = position.x;
        
        isOnFloor = false;
        
        startX = (int) bounds.x;
        endX = (int) (bounds.x + bounds.width);
        if (velocity.y < 0) {
            startY = endY = (int) Math.floor(bounds.y + velocity.y * delta);
        } else {
            startY = endY = (int) Math.floor(bounds.y + bounds.height + velocity.y * delta);
        }

        populateCollidableBlocks(startX, endX, startY, endY, world);
        etCrashTest.y += velocity.y * delta;

        for (Vector2 block : world.collidableBlocks) {
            if (block == null) continue;
            if (etCrashTest.overlaps(world.graphe.getGraphe().get(block).getBlock().bounds) && 
            		world.graphe.getGraphe().get(block).isBlock() ||
            		etCrashTest.overlaps(world.graphe.getGraphe().get(block).getBlock().bounds) && 
            		world.graphe.getGraphe().get(block).isTetrisBlock()) {
                if (velocity.y < 0) {
                	isOnFloor = true;
                }
                velocity.y = 0;
                break;
            }
        }
        
        etCrashTest.y = position.y;
		
		if (accel.x == 0){
			velocity.x *= DAMP;
			// Arrete le personnage quand la vélocité devient trop faible
			if (velocity.x < 0.01 && velocity.x > 0 || velocity.x > -0.01 && velocity.x < 0){
				velocity.x = 0;
			}
		}
		
		if (velocity.x > MAX_VEL_X){
			velocity.x = MAX_VEL_X;
		}
		else if (velocity.x < -MAX_VEL_X){
			velocity.x = -MAX_VEL_X;
		}
		
		if (velocity.y > MAX_VEL_Y){
			velocity.y = MAX_VEL_Y;
		}
		else if (velocity.y < -MAX_VEL_Y){
			velocity.y = -MAX_VEL_Y;
		}
		
	}
	
	private void updateEtat(float delta){
		
		if (velocity.x != 0 && accel.x != 0 && isOnFloor) {
			if (etat != ET_RUN){
				etat = ET_RUN;
				tempsEtat = 0;
			}
		}
		else if (velocity.y > 0 && !isOnFloor){
			if (etat != ET_JUMP){
				etat = ET_JUMP;
				tempsEtat = 0;
			}
		}
		else if (velocity.y < 0 && !isOnFloor){
			if (etat != ET_FALL){
				etat = ET_FALL;
				tempsEtat = 0;
			}
		}
		else {
			if (etat != ET_IDLE){
				etat = ET_IDLE;
				tempsEtat = 0;
			}
		}
		tempsEtat += delta;
	}
	
	public void update(float delta, World world){
		
		updateJump(delta);
		
		accel.add(accel.x * delta, accel.y * delta);

		velocity.add(accel.x, accel.y);
		
		updateCollisions(delta, world);
		
		updateEtat(delta);
		
		position.add(velocity.x * delta, velocity.y * delta);
		
		if (position.x < 0){
			position.x = 0;
			velocity.x = 0;
		}
		else if (position.x > FRUSTRUM_WIDTH - 1){
			position.x = FRUSTRUM_WIDTH - 1;
			velocity.x = 0;
		}
		
		if (position.y > FRUSTRUM_HEIGHT - 1) position.y = FRUSTRUM_HEIGHT - 1;
	
		bounds.setPosition(position);
	}
	
	private void populateCollidableBlocks(int startX, int endX, int startY, int endY, World world){
        world.collidableBlocks.clear();
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                if (x >= 0 && x < WORLD_WIDTH && y >= 0 && y < WORLD_HEIGHT) {
                    world.collidableBlocks.add(new Vector2(x, y));
                }
            }
        }
	}

}
