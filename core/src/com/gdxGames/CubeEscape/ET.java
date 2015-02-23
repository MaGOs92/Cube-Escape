package com.gdxGames.CubeEscape;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class ET extends DynamicGameObject implements Constantes{
	
	public int etat;
	public boolean isOnFloor;
	public boolean isJumping;
	float tempsEtat;
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
			accel.y = 10 - (jumpTime*7.5f);
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
            if (etCrashTest.overlaps(world.graphe.getGraphe().get(block).getRect()) && 
            		world.graphe.getGraphe().get(block).isBlock()) {
                velocity.x = 0;
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
            if (etCrashTest.overlaps(world.graphe.getGraphe().get(block).getRect()) && 
            		world.graphe.getGraphe().get(block).isBlock()) {
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
		}
		
		if (velocity.x > MAX_VEL_X){
			velocity.x = MAX_VEL_X;
		}
		else if (velocity.x < -MAX_VEL_X){
			velocity.x = -MAX_VEL_X;
		}
		
	}
	
	private void updateEtat(float delta){
		
		if (accel.x != 0 && isOnFloor) {
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
		
		if (position.x < ET_WIDTH/2) position.x = ET_WIDTH/2;
		if (position.x > WORLD_WIDTH - ET_WIDTH/2) position.x = WORLD_WIDTH - ET_WIDTH/2;
		if (position.y < 0) position.y = 0;				
	
		bounds.setPosition(position);
		System.out.println(position.x);
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
