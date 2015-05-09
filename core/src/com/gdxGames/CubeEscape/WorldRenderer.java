package com.gdxGames.CubeEscape;

import graphe.Sommet;

import java.util.Map;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class WorldRenderer implements Constantes{

	World world;
	SpriteBatch batch;
	OrthographicCamera GameCamera;

	public WorldRenderer (SpriteBatch batch, OrthographicCamera GameCamera, World world) {
		this.world = world;
		this.batch = batch;
		this.GameCamera = GameCamera;
		GameCamera.update();
	}

	public void render () {
		GameCamera.update();
		batch.setProjectionMatrix(GameCamera.combined);
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
		//renderTetrisBlocks();
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
			batch.draw(keyFrame, world.et.position.x + ET_WIDTH, world.et.position.y, side * ET_WIDTH, ET_HEIGHT);
		else
			batch.draw(keyFrame, world.et.position.x, world.et.position.y, side * ET_WIDTH, ET_HEIGHT);
	}
	
	private void renderSoucoupe(){
		TextureRegion keyFrame = Assets.soucoupe.getKeyFrame(world.soucoupe.tempsEtat, Animation.ANIMATION_LOOPING);
		batch.draw(keyFrame, world.soucoupe.position.x, world.soucoupe.position.y, SOUCOUPE_WIDTH, SOUCOUPE_HEIGHT);
	}
	
	private void renderGraphe(){
		for (Map.Entry<Vector2, Sommet> e : world.graphe.getGraphe().entrySet()) {
			Vector2 key = e.getKey();
			if (key.y == 0) batch.draw(Assets.sol, key.x, key.y, BLOCK_WIDTH, BLOCK_HEIGHT);
			else if (world.graphe.getGraphe().get(key).isBlock() || 
					world.graphe.getGraphe().get(key).isTetrisBlock()){
				batch.draw(Assets.block, key.x, key.y, BLOCK_WIDTH, BLOCK_HEIGHT);
			}
		}
	}

}
