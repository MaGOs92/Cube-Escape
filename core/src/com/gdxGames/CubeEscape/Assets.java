package com.gdxGames.CubeEscape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	
	public static Texture backGround;
	public static Texture etSprite;
	public static Texture block;
	public static Texture sol;
	public static Texture texPause;
	public static Texture texQuitter;
	public static Texture texReprendre;
	public static Texture soucoupeSprite;
	public static TextureRegion ETIdle;
	public static Animation ETRun;
	public static Animation soucoupe;
	public static Animation ETJump;
	public static Animation ETFall;
	
	public static void load(){
		
		// Sons et musique
		
		
		// Textures 
		
		backGround = loadTex("background.jpg");
		etSprite = loadTex("sprite_et.png");
		soucoupeSprite = loadTex("sprite_soucoupe.png");
		
		TextureRegion[][] tabET = TextureRegion.split(etSprite, 72, 96);
		TextureRegion[][] tabSoucoupe = TextureRegion.split(soucoupeSprite, 64, 48);
		
		ETIdle = new TextureRegion(etSprite, 0, 196, 66, 92);
		ETRun = new Animation (0.1f, tabET[0][0], tabET[0][1], tabET[0][2], tabET[0][3], tabET[0][4], tabET[0][5],
				tabET[1][0], tabET[1][1], tabET[1][2], tabET[1][3], tabET[1][4]);
		ETJump = new Animation (0.1f, tabET[1][6]);
		ETFall = new Animation (0.1f, tabET[0][6]);
		block = new Texture(Gdx.files.internal("block.png"));
		sol = new Texture(Gdx.files.internal("ground.png"));
		texPause = new Texture(Gdx.files.internal("Pause-bouton.png"));
		texQuitter = new Texture(Gdx.files.internal("Quitter-bouton.png"));
		texReprendre = new Texture(Gdx.files.internal("Reprendre-bouton.png"));
		soucoupe = new Animation (0.1f, tabSoucoupe[0][0], tabSoucoupe[0][1], tabSoucoupe[0][2], tabSoucoupe[0][3],
				tabSoucoupe[1][0], tabSoucoupe[1][1], tabSoucoupe[1][2], tabSoucoupe[1][3]);
	}
	
	public static Texture loadTex(String tex){
		return new Texture(Gdx.files.internal(tex));
	}
	
}
