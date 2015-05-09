package com.gdxGames.CubeEscape;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Assets {
	
	// Sons et musiques
	public static Music music;
	public static Sound jumpSound;
	public static Sound selectSound;
	public static Sound deathSound;
	public static Sound winSound;
	public static Sound landingSound;
	
	//Assets MainMenu
	public static Texture mainMenuBg;
	public static Texture quitTex;
	public static Texture playTex;
	
	// Assets GameScene
	public static Texture backGround;
	public static Texture etSprite;
	public static Texture uiSprite;
	public static TextureRegion playButton;
	public static TextureRegion stopButton;
	public static TextureRegion pauseButton;
	public static Texture block;
	public static Texture sol;
	public static Texture soucoupeSprite;
	public static TextureRegion ETIdle;
	public static Animation ETRun;
	public static Animation soucoupe;
	public static Animation ETJump;
	public static Animation ETFall;
	
	// Fonts
	public static BitmapFont labelFont;
	public static BitmapFont titleFont;
	
	public static void load(){
		loadSounds();
		loadTextures();
		loadFonts();
	}
	
	public static void loadSounds(){
		// Sons et musique
		music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music_loop.mp3"));
		music.setLooping(true);
		music.setVolume(0.4f);
		
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("sounds/jump.wav"));
		selectSound = Gdx.audio.newSound(Gdx.files.internal("sounds/select.mp3"));
		deathSound = Gdx.audio.newSound(Gdx.files.internal("sounds/death.wav"));
		winSound = Gdx.audio.newSound(Gdx.files.internal("sounds/win.wav"));
		landingSound = Gdx.audio.newSound(Gdx.files.internal("sounds/block_landing.wav"));
	}
	
	public static void loadTextures(){
		// Textures MainMenu
		mainMenuBg = loadTex("textures/main_menu_bg.jpg");
		quitTex = loadTex("textures/quit_button.png");
		playTex = loadTex("textures/play_button.png");
		
		// Textures GameScene	
		backGround = loadTex("textures/background.jpg");
		etSprite = loadTex("textures/sprite_et.png");
		uiSprite = loadTex("textures/ui-buttons.png");
		soucoupeSprite = loadTex("textures/sprite_soucoupe.png");
		
		TextureRegion[][] tabET = TextureRegion.split(etSprite, 72, 96);
		TextureRegion[][] tabSoucoupe = TextureRegion.split(soucoupeSprite, 64, 48);
		TextureRegion[][] tabUI = TextureRegion.split(uiSprite, 183, 155);
		
		ETIdle = new TextureRegion(etSprite, 0, 196, 66, 92);
		ETRun = new Animation (0.1f, tabET[0][0], tabET[0][1], tabET[0][2], tabET[0][3], tabET[0][4], tabET[0][5],
				tabET[1][0], tabET[1][1], tabET[1][2], tabET[1][3], tabET[1][4]);
		ETJump = new Animation (0.1f, tabET[1][6]);
		ETFall = new Animation (0.1f, tabET[0][6]);
		block = new Texture(Gdx.files.internal("textures/block.png"));
		sol = new Texture(Gdx.files.internal("textures/ground.png"));
		soucoupe = new Animation (0.1f, tabSoucoupe[0][0], tabSoucoupe[0][1], tabSoucoupe[0][2], tabSoucoupe[0][3],
				tabSoucoupe[1][0], tabSoucoupe[1][1], tabSoucoupe[1][2], tabSoucoupe[1][3]);
		playButton = tabUI[0][1];
		stopButton = tabUI[0][2];
		pauseButton = tabUI[0][0];
	}
	
    public static void loadFonts(){
    	
    	float scale = Gdx.graphics.getWidth() / Constantes.VIRTUAL_WIDTH ;
     	if (scale < 1){
    	 	scale = 1; 
     	}

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/BraggadocioMT Regular.ttf"));
		FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = ((int) (13 + scale));
		labelFont = generator.generateFont(parameter);
		labelFont.setScale((float) (scale));
		labelFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		labelFont.setColor(Color.BLACK);
		
		parameter.size = ((int) (23 + scale));
		titleFont = generator.generateFont(parameter);
		titleFont.setScale((float) (scale));
		titleFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		titleFont.setColor(Color.BLACK);
		
		generator.dispose();	
    }
	
	public static Texture loadTex(String tex){
		return new Texture(Gdx.files.internal(tex));
	}
}
