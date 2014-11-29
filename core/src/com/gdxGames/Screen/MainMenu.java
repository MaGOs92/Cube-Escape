package com.gdxGames.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.gdxGames.CubeEscape.Constantes;
import com.gdxGames.CubeEscape.CubeEscape;

public class MainMenu extends ScreenAdapter implements Constantes {
	
	final CubeEscape game;
	
	public MainMenu(final CubeEscape game){
		this.game = game;
	}

	@Override
	public void render(float delta) {
		
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        game.batch.begin();
        game.font.draw(game.batch, "Welcome to Cube Escape!!! ", w/4, h*2/3);
        game.font.draw(game.batch, "Tap anywhere to begin!", w/3, h/2);
        game.batch.end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
	}

}
