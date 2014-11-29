package com.gdxGames.CubeEscape.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.gdxGames.CubeEscape.CubeEscape;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Cube Escape";
		config.width = 320;
		config.height = 480;
		new LwjglApplication(new CubeEscape(), config);
	}
}
