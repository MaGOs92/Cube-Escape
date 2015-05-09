package com.gdxGames.CubeEscape;

import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import tetrisBlocks.*;
import graphe.*;

public class World implements Constantes {

	public final ET et;
	public final Graphe graphe;
	public Soucoupe soucoupe;
	public TetrisBlock curTetrisBlock;
	Random rand;
	public int etat;
	Block[] testBlocks;
	public Array<Vector2> collidableBlocks;
	private float time;
	private int level;
	private int typeBlock;
	private TetrisBlock testTetrisBlock;
	
	public enum LooseCause {
		CRUSHED,
		TIME,
		SPACESHIP,
		BLOCK_OVERFLOW
	}
	public LooseCause cause;

	public World(int level) {
		
		et = new ET(FRUSTRUM_WIDTH / 2, 1);
		graphe = new Graphe(WORLD_WIDTH, WORLD_HEIGHT);
		this.level = level;
		time = tabLevel[level-1];
		rand = new Random();

		testBlocks = new Block[4];
		for (int i=0; i<testBlocks.length; i++){
			testBlocks[i] = new Block(0,0);
		}

		createTetrisBlock();
		spawnSoucoupe();
		collidableBlocks = new Array<Vector2>();
		etat = RUNNING;
	}

	public void update(float delta, float accelX, int translation, boolean rotation) {
		updateTime(delta);
		updateET(delta, accelX, this);
		updateTetrisBlock(delta, translation, rotation);
		updateSoucoupe(delta);
		checkCollisions();
	}
	
	private void createTestBlock(){
		switch (typeBlock) {
		// I
		case 1:
			testTetrisBlock = new BlockI();
			break;
			// J
		case 2:
			testTetrisBlock = new BlockJ();
			break;
			// L
		case 3:
			testTetrisBlock = new BlockL();
			break;
			// O
		case 4:
			testTetrisBlock = new BlockO();
			break;
			// S
		case 5:
			testTetrisBlock = new BlockS();
			break;
			// T
		case 6:
			testTetrisBlock = new BlockT();
			break;
			// Z
		default:
			testTetrisBlock = new BlockZ();
			break;
		}
	}

	private void createTetrisBlock() {
		typeBlock = rand.nextInt(7) + 1;

		switch (typeBlock) {
		// I
		case 1:
			curTetrisBlock = new BlockI();
			break;
			// J
		case 2:
			curTetrisBlock = new BlockJ();
			break;
			// L
		case 3:
			curTetrisBlock = new BlockL();
			break;
			// O
		case 4:
			curTetrisBlock = new BlockO();
			break;
			// S
		case 5:
			curTetrisBlock = new BlockS();
			break;
			// T
		case 6:
			curTetrisBlock = new BlockT();
			break;
			// Z
		default:
			curTetrisBlock = new BlockZ();
			break;
		}
	}

	private void spawnSoucoupe() {
		int posX = rand.nextInt(10);
		int posY = rand.nextInt(3) + 14;
		this.soucoupe = new Soucoupe(posX, posY);
	}

	private void updateET(float delta, float accelX, World world) {
		et.accel.x = -accelX;
		et.update(delta, world);
	}

	private void updateTetrisBlock(float delta, int translation,
			boolean rotation) {

		curTetrisBlock.update(delta);
		
		if (tetrisBlockIsFalling()){
			unSetGrapheBlocks();
			curTetrisBlock.fall();
			setGrapheBlocks();
		}

		if (rotation && canTetrisBlockRotate()) {
			if (checkIsRotationValid()){
				unSetGrapheBlocks();
				curTetrisBlock.rotate();
				curTetrisBlock.rotateTime = 0;
				setGrapheBlocks();
			}

		}

		switch (translation) {
		case 1:
			if (canMoveTetrisBlockDroite()) {
				unSetGrapheBlocks();
				curTetrisBlock.moveTetrisBlockDroite();
				setGrapheBlocks();
			}
			break;
		case 2:
			if (canMoveTetrisBlockGauche()) {
				unSetGrapheBlocks();
				curTetrisBlock.moveTetrisBlockGauche();
				setGrapheBlocks();
			}
			break;
		case 3:
			if (canTetrisBlockFastFall()){
				unSetGrapheBlocks();
				curTetrisBlock.fastFall();
				setGrapheBlocks();
			}
			break;
		default:
			break;
		}
	}
	
	private void unSetGrapheBlocks(){
		for (int i=0; i<curTetrisBlock.tabBlock.length; i++){
			graphe.getGraphe().get(curTetrisBlock.tabBlock[i]).setTetrisBlock(false);
		}
	}
	
	private void setGrapheBlocks(){
		for (int i=0; i<curTetrisBlock.tabBlock.length; i++){
			graphe.getGraphe().get(curTetrisBlock.tabBlock[i]).setTetrisBlock(true);
		}
	}
	
	private boolean tetrisBlockIsFalling(){
		if (curTetrisBlock.fallTime > FALL_TIME){
			return true;
		}
		return false;
	}
	
	private boolean canTetrisBlockFastFall(){
		if (curTetrisBlock.fastFall>FASTFALL_TIME){
			return true;
		}
		return false;
	}
	
	private boolean canTetrisBlockRotate(){
		if (curTetrisBlock.rotateTime > ROTATE_TIME){
			return true;
		}
		return false;
	}

	private boolean canMoveTetrisBlockDroite() {

		for (int i = 0; i < curTetrisBlock.tabBlock.length; i++) {
			testBlocks[i].move(curTetrisBlock.tabBlock[i].x+1, curTetrisBlock.tabBlock[i].y);
			if (testBlocks[i].position.x >= WORLD_WIDTH) {
				return false;
			} else {
				if (graphe.getGraphe().get(testBlocks[i].position).isBlock()) {
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean checkIsRotationValid(){
		createTestBlock();
		for (int i=0; i<4; i++){
			testTetrisBlock.tabBlock[i] = new Vector2(curTetrisBlock.tabBlock[i].x, curTetrisBlock.tabBlock[i].y); 
		}
		testTetrisBlock.etatRotation = curTetrisBlock.etatRotation;
		testTetrisBlock.rotate();
		for (int i=0; i<4; i++){
			if (testTetrisBlock.tabBlock[i].x < 0 || testTetrisBlock.tabBlock[i].x >= WORLD_WIDTH ||
					graphe.getGraphe().get(testTetrisBlock.tabBlock[i]).isBlock()){
				return false;
			}
		}
		return true;
	}

	private boolean canMoveTetrisBlockGauche() {

		for (int i = 0; i < curTetrisBlock.tabBlock.length; i++) {
			testBlocks[i].move(curTetrisBlock.tabBlock[i].x-1, curTetrisBlock.tabBlock[i].y);
			if (testBlocks[i].position.x < 0) {
				return false;
			} else {
				if (graphe.getGraphe().get(testBlocks[i].position).isBlock()) {
					return false;
				}
			}
		}
		return true;
	}

	private void updateSoucoupe(float delta) {
		soucoupe.update(delta);
	}

	private void checkCollisions() {
		checkTetrisBlockCollisions();
		checkOtherCollisions();
	}

	private void checkOtherCollisions() {
		if (et.bounds.overlaps(soucoupe.bounds)) {
			Assets.winSound.play();
			etat = WIN;
		}
		for (Map.Entry<Vector2, Sommet> e : graphe.getGraphe().entrySet()) {
			Vector2 key = e.getKey();
			if (graphe.getGraphe().get(key).getBlock().bounds.overlaps(soucoupe.bounds) && graphe.getGraphe().get(key).isBlock()) {
				etat = GAME_OVER;
				cause = LooseCause.SPACESHIP;
			}
			else if (key.y > FRUSTRUM_HEIGHT && graphe.getGraphe().get(key).isBlock()){
				etat = GAME_OVER;
				cause = LooseCause.BLOCK_OVERFLOW;
			}
		}
	}
	

	private void checkTetrisBlockCollisions() {
		boolean collision = false;

		for (int i = 0; i < curTetrisBlock.tabBlock.length; i++) {
			if (graphe.getGraphe().get(curTetrisBlock.tabBlock[i]).isBlock()){
				collision = true;
				break;
			}

		}
		if (collision) {
			for (int i = 0; i < curTetrisBlock.tabBlock.length; i++) {
				graphe.getGraphe().get(curTetrisBlock.tabBlock[i]).setTetrisBlock(false);
				curTetrisBlock.tabBlock[i].y += 1;
				graphe.getGraphe().get(curTetrisBlock.tabBlock[i]).setBlock(true);
			}
			Assets.landingSound.play();
			createTetrisBlock();
		}
		else {
			for (int i = 0; i < curTetrisBlock.tabBlock.length; i++) {
				if (graphe.getGraphe().get(curTetrisBlock.tabBlock[i]).getBlock().bounds.overlaps(et.bounds)) {
					etat = GAME_OVER;
					Assets.deathSound.play();
					cause = LooseCause.CRUSHED;
				}
			}
		}
	}
	
	private void updateTime(float delta){
		time -= delta;
		if (time < 0){
			etat = GAME_OVER;
			cause = LooseCause.TIME;
		}
	}
	
	public float getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
