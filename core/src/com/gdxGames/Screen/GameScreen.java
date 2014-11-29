package com.gdxGames.Screen;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
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

	public int etat;
	Vector3 touchPoint;
	Rectangle quitterBouton;
	Rectangle pauseBouton;
	Rectangle reprendreBouton;

	World world;
	WorldRenderer renderer;

	OrthographicCamera camera;

	public GameScreen(final CubeEscape game) {
		this.game = game;
		this.camera = game.camera;
		etat = READY;
		this.camera.update();

		world = new World();
		renderer = new WorldRenderer(this.game.batch, this.world, this.camera);
		touchPoint = new Vector3();

		pauseBouton = new Rectangle(1, FRUSTRUM_HEIGHT - 1, 2, 1);
		quitterBouton = new Rectangle(FRUSTRUM_WIDTH - 3, FRUSTRUM_HEIGHT - 1,
				2, 1);
		reprendreBouton = new Rectangle(1, FRUSTRUM_HEIGHT - 1, 2, 1);
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
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

			if (pauseBouton.contains(touchPoint.x, touchPoint.y)) {
				etat = PAUSE;
				return;
			}
		}


		
		// Controles
		
		ApplicationType appType = Gdx.app.getType();
		
		float accelX = 0, accelY = 0;

		if (appType != ApplicationType.Android) {
			// Controles desktop
			
			if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
				accelX = 3f;
			}
			if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
				accelX = -3f;
			}
			if (Gdx.input.isKeyPressed(Keys.DPAD_UP)
					&& world.et.isOnFloor) {
				world.et.isJumping = true;
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

		world.update(delta, accelX, accelY, game.translation, game.rotation);

		game.rotation = false;
		game.translation = 0;
		
		if (world.etat == NEXT_LEVEL) {
			game.setScreen(new GameScreen(game));
			dispose();
		}
		
		if (world.etat == GAME_OVER) {
			etat = GAME_OVER;
		}
		
	}

	private void updatePaused() {
		if (Gdx.input.justTouched()) {
			camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(),
					0));

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

	private void updateGameOver() {
		if (Gdx.input.justTouched()) {
			game.setScreen(new MainMenu(game));
			dispose();
		}
	}

	public void draw() {

		GL20 gl = Gdx.gl;
		gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		renderer.render();

		camera.update();
		game.batch.setProjectionMatrix(camera.combined);
		game.batch.enableBlending();
		game.batch.begin();

		// Pour dessiner les interfaces propres à chaque état
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
		}
		game.batch.end();
	}

	private void drawReady() {
		game.font.setColor(Color.BLACK);
        game.font.draw(game.batch, "GET READY", Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()*2/3);
        game.font.draw(game.batch, "Tap anywhere to begin", Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/2);
	}

	private void drawRunning() {
		game.batch.draw(Assets.texPause, 1, FRUSTRUM_HEIGHT - 1, 2, 1);
	}

	private void drawPaused() {
		game.batch.draw(Assets.texQuitter, FRUSTRUM_WIDTH - 3,
				FRUSTRUM_HEIGHT - 1, 2, 1);
		game.batch.draw(Assets.texReprendre, 1, FRUSTRUM_HEIGHT - 1, 2, 1);
	}

	private void drawGameOver() {
		game.font.setColor(Color.BLACK);
        game.font.draw(game.batch, "GAME OVER", Gdx.graphics.getWidth()/4, Gdx.graphics.getHeight()*2/3);
        game.font.draw(game.batch, "Tap anywhere to continue", Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/2);
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
