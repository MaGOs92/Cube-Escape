package com.gdxGames.CubeEscape;

public class ET extends DynamicGameObject implements Constantes{
	
	public int etat;
	public boolean isOnFloor;
	public boolean isOnWall;
	public boolean isJumping;
	float tempsEtat;
	float jumpTime;

	public ET(float x, float y) {
		super(x, y, ET_WIDTH, ET_HEIGHT);
		etat = ET_IDLE;
		tempsEtat = 0;
		isJumping = false;
		jumpTime = 0;
	}
	
	private void updateJump(float delta){
		if (isJumping){
			accel.y = 10;
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
	
	private void updatePhysics(float delta){
		
		updateJump(delta);
		
		accel.add(accel.x * delta, accel.y * delta);
		
		velocity.add(accel.x, accel.y);
		
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
	
	public void update(float delta){

		updatePhysics(delta);
		
		updateEtat(delta);
		
		position.add(velocity.x * delta, velocity.y * delta);
		
		// Vérifier que le personnage ne sorte pas de l'écran
		
		if (position.x < ET_WIDTH/2) position.x = ET_WIDTH/2;
		if (position.x > WORLD_WIDTH - ET_WIDTH/2) position.x = WORLD_WIDTH - ET_WIDTH/2;
		if (position.y < 0) position.y = 0;				
	
		bounds.setPosition(position);

	}

}
