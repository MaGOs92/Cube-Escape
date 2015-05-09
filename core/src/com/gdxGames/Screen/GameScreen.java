package com.gdxGames.Screen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.gdxGames.CubeEscape.Assets;
import com.gdxGames.CubeEscape.Constantes;
import com.gdxGames.CubeEscape.CubeEscape;
import com.gdxGames.CubeEscape.World;
import com.gdxGames.CubeEscape.WorldRenderer;

public class GameScreen extends ScreenAdapter implements Constantes {

	final CubeEscape game;
	int level;

	public int etat;
	Vector3 touchPoint;
	Rectangle quitterBouton;
	Rectangle pauseBouton;
	Rectangle reprendreBouton;

	World world;
	WorldRenderer renderer;

	OrthographicCamera camera;
	OrthographicCamera GUIcamera;
	
	final float guiWidthUnit;
	final float guiHeightUnit; 

	public GameScreen(final CubeEscape game, int level) {
		
		this.game = game;
		this.level = level;
		camera = game.camera;
		
		// Camera pour afficher les fonts correctement
		GUIcamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		GUIcamera.position.set(GUIcamera.viewportWidth / 2, GUIcamera.viewportHeight / 2, 0);
		GUIcamera.update();
		
		guiWidthUnit =  GUIcamera.viewportWidth/100;
		guiHeightUnit = GUIcamera.viewportHeight/100;
		
		etat = READY;

		world = new World(level);
		renderer = new WorldRenderer(game.batch, camera, world);
		touchPoint = new Vector3();

		pauseBouton = new Rectangle(camera.viewportWidth/2-1, camera.viewportHeight - 2, 2, 2);
		quitterBouton = new Rectangle(camera.viewportWidth/2 + 1, camera.viewportHeight/2 - 1, 2, 2);
		reprendreBouton = new Rectangle(camera.viewportWidth/2 - 3, camera.viewportHeight/2 - 1, 2, 2);
	}

	// Choisi l'update à faire suivant l'état où on est
	public void update(float delta) {
		
		switch (this.etat) {
		case READY:
			updateReady();
			break;
		case RUNNING:
			updateRunning(delta);
			break;
		case PAUSE:
			updatePaused();
			break;
		case GAME_OVER:
			updateGameOver();
			break;
		case WIN:
			updateWin();
			break;
		}
	}

	// Fonctions pour faire le lien entre les différents états

	private void updateReady() {
		if (Gdx.input.justTouched()) {
			etat = RUNNING;
		}
	}

	private void updateRunning(float delta) {
		if (Gdx.input.justTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (pauseBouton.contains(touchPoint.x, touchPoint.y)) {
				etat = PAUSE;
				return;
			}
		}

		// Controles
		
		ApplicationType appType = Gdx.app.getType();
		
		float accelX = 0;
		
		if (appType == ApplicationType.Android){
			accelX = Gdx.input.getAccelerometerX();
			if (Math.abs(accelX) < 1){
				accelX = 0;
			}
			else if (accelX >= 3) {
				accelX = 3;
			}
			else if (accelX <= -3) {
				accelX = -3;
			}
		}
		else {
			// Controles desktop
			if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
				accelX = 3f;
			}
			if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
				accelX = -3f;
			}

			// Controles tetris

			if (Gdx.input.isKeyPressed(Keys.D))
				game.translation = 1;
			else if (Gdx.input.isKeyPressed(Keys.A))
				game.translation = 2;
			else if (Gdx.input.isKeyPressed(Keys.S))
				game.translation = 3;

			if (Gdx.input.isKeyPressed(Keys.W)){
				game.rotation = true;
			}
			
		}

		world.update(delta, accelX, game.translation, game.rotation);

		game.rotation = false;
		game.translation = 0;
		
		if (world.etat == WIN) {
			etat = WIN;
		}
		
		else if (world.etat == GAME_OVER) {
			etat = GAME_OVER;
		}
		
	}

	private void updatePaused() {
		if (Gdx.input.justTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (reprendreBouton.contains(touchPoint.x, touchPoint.y)) {
				etat = RUNNING;
				return;
			}

			if (quitterBouton.contains(touchPoint.x, touchPoint.y)) {
				game.setScreen(new MainMenu(game));
				return;
			}
		}
	}
	
	private void updateWin(){
		if (Gdx.input.justTouched()) {
			if (level < 10){
				game.setScreen(new GameScreen(game, level + 1));
				dispose();
			}
			else {
				game.setScreen(new MainMenu(game));
				dispose();
			}
		}
	}

	private void updateGameOver() {
		if (Gdx.input.justTouched()) {
			game.setScreen(new GameScreen(game, level));
			dispose();
		}
	}

	public void draw() {
		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		renderer.render();
		renderGUI();
	}
	
	private void renderGUI(){
		GUIcamera.update();
		game.batch.setProjectionMatrix(GUIcamera.combined);
		game.batch.begin();
		switch (etat) {
		case READY:
			drawReady();
			break;
		case RUNNING:
			drawRunning();
			break;
		case PAUSE:
			drawPaused();
			break;
		case GAME_OVER:
			drawGameOver();
			break;
		case WIN:
			drawWin();
			break;
		}
		game.batch.end();
		game.batch.setProjectionMatrix(camera.combined);
	}

	private void drawReady() {
        Assets.titleFont.draw(game.batch, "Level " + world.getLevel(), 34*guiWidthUnit, 60*guiHeightUnit);
        Assets.labelFont.draw(game.batch, "Tap anywhere to start", 20*guiWidthUnit, 50*guiHeightUnit);
	}

	private void drawRunning() {
		Assets.labelFont.draw(game.batch, "Level : " + world.getLevel(), 8*guiWidthUnit, 97*guiHeightUnit);
		Assets.labelFont.draw(game.batch, "Time left : " + Math.round(world.getTime()), 60*guiWidthUnit, 97*guiHeightUnit);
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.draw(Assets.pauseButton, camera.viewportWidth/2-1, camera.viewportHeight - 2, 2, 2);
	}

	private void drawPaused() {
		Assets.titleFont.draw(game.batch, "Game paused", 19*guiWidthUnit, 75*guiHeightUnit);
		
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.draw(Assets.stopButton, camera.viewportWidth/2 + 1, camera.viewportHeight/2 - 1, 2, 2);
		game.batch.draw(Assets.playButton, camera.viewportWidth/2 - 3, camera.viewportHeight/2 - 1, 2, 2);
	}
	
	private void drawWin(){
		if (level < 10){
	        Assets.titleFont.draw(game.batch, "You win!", 29*guiWidthUnit, 75*guiHeightUnit);
	        Assets.labelFont.draw(game.batch, "Get ready for the next level", 13*guiWidthUnit, 50*guiHeightUnit);		
		}
		else {
	        Assets.labelFont.draw(game.batch, "Congratulations you beat the game", 7*guiWidthUnit, 60*guiHeightUnit);	
	        Assets.labelFont.draw(game.batch, "Tap to go back to main menu", 12*guiWidthUnit, 50*guiHeightUnit);		
		}
	}

	private void drawGameOver() {
		switch(world.cause){
		case CRUSHED:
	        Assets.labelFont.draw(game.batch, "You diededed an horrible death!", 7*guiWidthUnit, 55*guiHeightUnit);	
	        break;
		case SPACESHIP:
	        Assets.labelFont.draw(game.batch, "Your spaceship is unreachable!", 10*guiWidthUnit, 55*guiHeightUnit);	
	        break;
		case BLOCK_OVERFLOW:
	        Assets.labelFont.draw(game.batch, "The block heap is too high! ", 13*guiWidthUnit, 55*guiHeightUnit);	
	        break;
		case TIME:
	        Assets.labelFont.draw(game.batch, "Time is up, try again!", 23*guiWidthUnit, 55*guiHeightUnit);
	        break;
		}
        Assets.labelFont.draw(game.batch, "Tap anywhere to restart", 16*guiWidthUnit, 45*guiHeightUnit);
	}

	@Override
	public void render(float delta) {
		update(delta);
		draw();
	}

	@Override
	public void pause() {
		if (etat == RUNNING) {
			etat = PAUSE;
		}
	}

}
