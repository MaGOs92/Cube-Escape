package com.gdxGames.CubeEscape;

public interface Constantes {
	
	public static final float gravite = -2f;
	
	// Tableau qui associe niveau et temps pour réussir
	public static final int[] tabLevel = {90, 80, 70, 60, 50, 40, 35, 30, 25, 20};
	
	// GUI
    public static final int VIRTUAL_WIDTH = 320;
    public static final int VIRTUAL_HEIGHT = 480;

	// Taille totale du monde
	public final static int WORLD_WIDTH = 10;
	public final static int WORLD_HEIGHT = 24;
	
	// Taille de la zone d'affichage
	public final static int FRUSTRUM_WIDTH = 10;
	public final static int FRUSTRUM_HEIGHT = 18;
	
	// Etats du jeu
	public static final int READY = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	public static final int WIN = 4;
	
	// Constantes tetris
	public static final float FALL_TIME = 1.0f;
	public static final float MOVE_TIME = 0.2f;
	public static final float ROTATE_TIME = 0.1f;
	public static final float FASTFALL_TIME = 0.2f;
	
	// Constantes soucoupe
	public static final float SOUCOUPE_WIDTH = 2.0f;
	public static final float SOUCOUPE_HEIGHT = 1.5f;
	
	// Constantes blocks
	public static final float BLOCK_WIDTH = 1.0f;
	public static final float BLOCK_HEIGHT = 1.0f;
	
	// Constantes ET
	public static final int ET_IDLE = 0;
	public static final int ET_RUN = 1;
	public static final int ET_JUMP = 2;
	public static final int ET_FALL = 3;
	
	public static final float ET_WIDTH = 0.70f;
	public static final float ET_HEIGHT = 0.95f;
	
	public static final float DAMP = 0.8f;
	public static final float MAX_VEL_X = 5f;
	public static final float MAX_VEL_Y = 10f;
	public static final float ACCEL = 20f;
	public static final float JUMP_TIME = 0.25f;
}
