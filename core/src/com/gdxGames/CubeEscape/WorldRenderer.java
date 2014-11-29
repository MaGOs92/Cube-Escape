package com.gdxGames.CubeEscape;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class WorldRenderer implements Constantes{

	World world;
	SpriteBatch batch;
	OrthographicCamera camera;

	public WorldRenderer (SpriteBatch batch, World world, OrthographicCamera camera) {
		this.world = world;
		this.batch = batch;
		this.camera = camera;
		this.camera.update();
	}

	public void render () {
		this.camera.update();
		renderBackGround();
		renderObjects();
	}
	
	private void renderBackGround(){
		batch.disableBlending();
		batch.begin();
		batch.draw(Assets.backGround, 0, 0, FRUSTRUM_WIDTH, FRUSTRUM_HEIGHT);
		batch.end();
	}
	
	private void renderObjects(){
		batch.enableBlending();
		batch.begin();
		renderET();
		renderTetrisBlocks();
		renderSoucoupe();
		renderGraphe();
		batch.end();
	}

	private void renderET () {
		TextureRegion keyFrame;
		switch (world.et.etat) {
		case 1:
			keyFrame = Assets.ETRun.getKeyFrame(world.et.tempsEtat, Animation.ANIMATION_LOOPING);
			break;
		case 2:
			keyFrame = Assets.ETJump.getKeyFrame(world.et.tempsEtat, Animation.ANIMATION_NONLOOPING);
			break;
		case 3:
			keyFrame = Assets.ETFall.getKeyFrame(world.et.tempsEtat, Animation.ANIMATION_NONLOOPING);
			break;
		default : 
			keyFrame = Assets.ETIdle;
		}

		float side = (world.et.velocity.x < 0) ? -1 : 1;
		if (side < 0)
			batch.draw(keyFrame, world.et.position.x + 0.75f, world.et.position.y, side * 1.5f, 2);
		else
			batch.draw(keyFrame, world.et.position.x - 0.75f, world.et.position.y, side * 1.5f, 2);
	}
	
	private void renderTetrisBlocks(){
		for (int i=0; i<4; i++){
			batch.draw(Assets.block, world.curTetrisBlock.tabBlock[i].position.x, world.curTetrisBlock.tabBlock[i].position.y, 1, 1);
		}
	}
	
	private void renderSoucoupe(){
		TextureRegion keyFrame = Assets.soucoupe.getKeyFrame(world.soucoupe.tempsEtat, Animation.ANIMATION_LOOPING);
		batch.draw(keyFrame, world.soucoupe.position.x, world.soucoupe.position.y, 2, 1.5f);
	}
	
	private void renderGraphe(){

		for (Block s : world.graphe.getListBlocks()){
			if (s.position.y == 0) batch.draw(Assets.sol, s.position.x, s.position.y, 1, 1);
			else batch.draw(Assets.block, s.position.x, s.position.y, 1, 1);
		}
	}

}
