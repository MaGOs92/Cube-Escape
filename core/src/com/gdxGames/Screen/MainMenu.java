package com.gdxGames.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.gdxGames.CubeEscape.Assets;
import com.gdxGames.CubeEscape.Constantes;
import com.gdxGames.CubeEscape.CubeEscape;

public class MainMenu extends ScreenAdapter implements Constantes {
	
	final CubeEscape game;
	private Vector3 touchPoint;
	private Rectangle play;
	private Rectangle quit;
	
	public MainMenu(final CubeEscape game){
		this.game = game;
		play = new Rectangle(game.camera.viewportWidth/4, game.camera.viewportHeight/2, game.camera.viewportWidth/2, game.camera.viewportHeight/8);
		quit = new Rectangle(game.camera.viewportWidth/4, game.camera.viewportHeight/4, game.camera.viewportWidth/2, game.camera.viewportHeight/8);
		touchPoint = new Vector3();
	}

	@Override
	public void render(float delta) {
		
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.camera.update();
        
		game.batch.disableBlending();
		game.batch.begin();
		game.batch.draw(Assets.mainMenuBg, 0, 0, game.camera.viewportWidth, game.camera.viewportHeight);
		game.batch.end();
        
		game.batch.enableBlending();
		game.batch.begin();
        game.batch.draw(Assets.playTex, game.camera.viewportWidth/4, game.camera.viewportHeight/2, game.camera.viewportWidth/2, game.camera.viewportHeight/8);
        game.batch.draw(Assets.quitTex, game.camera.viewportWidth/4, game.camera.viewportHeight/4, game.camera.viewportWidth/2, game.camera.viewportHeight/8);
        game.batch.end();
        
		if (Gdx.input.justTouched()){
			game.camera.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		}

        if (play.contains(touchPoint.x, touchPoint.y)) {
        	Assets.selectSound.play();
            game.setScreen(new GameScreen(game, 1));
            dispose();
        }
        else if (quit.contains(touchPoint.x, touchPoint.y)) {
        	Assets.selectSound.play();
        	Gdx.app.exit();
        }
	}

}
