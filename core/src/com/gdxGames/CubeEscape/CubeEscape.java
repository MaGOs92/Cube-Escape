package com.gdxGames.CubeEscape;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.gdxGames.Screen.MainMenu;

public class CubeEscape extends Game implements Constantes, GestureListener {

	public OrthographicCamera camera;
	public SpriteBatch batch;
	public float height;
	public float width;
	public int translation;
	public boolean rotation;

	public void create() {
		
		
		batch = new SpriteBatch();
		camera = new OrthographicCamera();
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		camera = new OrthographicCamera(FRUSTRUM_WIDTH, FRUSTRUM_HEIGHT);
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
		camera.update();
		
        batch.setProjectionMatrix(camera.combined);

		Assets.load();
		
		Assets.music.play();
		
	    GestureDetector gd = new GestureDetector(this);
		Gdx.input.setInputProcessor(gd);

		this.setScreen(new MainMenu(this));
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		this.rotation = true;
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		// TODO Auto-generated method stub	
		if (Math.abs(deltaX) > Math.abs(deltaY)) {
			if (deltaX > 0) {
				translation = 1;
			} else {
				translation = 2;
			}
		} else {
			if (deltaY > 0) {
				translation = 3;
			}
			else {
				translation = 0;
			}
		}
		return false;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		return false;
	}
}
