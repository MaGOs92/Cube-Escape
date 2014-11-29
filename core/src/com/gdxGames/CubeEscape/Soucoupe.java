package com.gdxGames.CubeEscape;

public class Soucoupe extends GameObject {
	
	public static final int SOUCOUPE_WIDTH = 1;
	public static final int SOUCOUPE_HEIGHT = 1;
	
	public float tempsEtat;

	public Soucoupe(float x, float y) {
		super(x, y, SOUCOUPE_WIDTH, SOUCOUPE_HEIGHT);

	}
	
	public void update(float delta){
		tempsEtat += delta;
	}

}
