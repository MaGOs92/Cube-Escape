package com.gdxGames.CubeEscape;

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

	public World() {
		this.et = new ET(FRUSTRUM_WIDTH / 2, 2);
		this.graphe = new Graphe(WORLD_WIDTH, WORLD_HEIGHT);
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

	public void update(float delta, float accelX, float accelY,
			int translation, boolean rotation) {
		updateET(delta, accelX, accelY, this);
		updateTetrisBlock(delta, translation, rotation);
		updateSoucoupe(delta);
		checkCollisions();
	}

	private void createTetrisBlock() {
		int model = rand.nextInt(7) + 1;

		switch (model) {
		// I
		case 1:
			this.curTetrisBlock = new BlockI();
			break;
			// J
		case 2:
			this.curTetrisBlock = new BlockJ();
			break;
			// L
		case 3:
			this.curTetrisBlock = new BlockL();
			break;
			// O
		case 4:
			this.curTetrisBlock = new BlockO();
			break;
			// S
		case 5:
			this.curTetrisBlock = new BlockS();
			break;
			// T
		case 6:
			this.curTetrisBlock = new BlockT();
			break;
			// Z
		default:
			this.curTetrisBlock = new BlockZ();
			break;
		}
	}

	private void spawnSoucoupe() {
		int posX = rand.nextInt(10);
		int posY = rand.nextInt(3) + 14;
		this.soucoupe = new Soucoupe(posX, posY);
	}

	private void updateET(float delta, float accelX, float accelY, World world) {
		et.accel.x = -accelX;
		et.velocity.y = accelY;
		et.update(delta, world);

	}

	private void updateTetrisBlock(float delta, int translation,
			boolean rotation) {

		curTetrisBlock.update(delta);

		if (rotation) {
			curTetrisBlock.checkRotation();
		}

		switch (translation) {
		case 1:
			if (canMoveTetrisBlockDroite()) {
				curTetrisBlock.moveTetrisBlockDroite();
			}
			break;
		case 2:
			if (canMoveTetrisBlockGauche()) {
				curTetrisBlock.moveTetrisBlockGauche();
			}
			break;
		case 3:
			curTetrisBlock.fastFall();
			break;
		default:
			break;
		}
	}

	private boolean canMoveTetrisBlockDroite() {

		for (int i = 0; i < curTetrisBlock.tabBlock.length; i++) {
			testBlocks[i].move(curTetrisBlock.tabBlock[i].position.x+1, curTetrisBlock.tabBlock[i].position.y);
			if (testBlocks[i].position.x >= WORLD_WIDTH) {
				return false;
			} else {
				for (Block b : graphe.getListBlocks()) {
					if (testBlocks[i].bounds.overlaps(b.bounds)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean canMoveTetrisBlockGauche() {

		for (int i = 0; i < curTetrisBlock.tabBlock.length; i++) {
			testBlocks[i].move(curTetrisBlock.tabBlock[i].position.x-1, curTetrisBlock.tabBlock[i].position.y);
			if (testBlocks[i].position.x < 0) {
				return false;
			} else {
				for (Block b : graphe.getListBlocks()) {
					if (testBlocks[i].bounds.overlaps(b.bounds)) {
						return false;
					}
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
			etat = NEXT_LEVEL;
		}
		for (Block b : graphe.getListBlocks()) {
			if (b.bounds.overlaps(soucoupe.bounds) || b.position.y > 18) {
				etat = GAME_OVER;
			}
		}
	}
	

	private void checkTetrisBlockCollisions() {
		boolean collision = false;
		for (int i = 0; i < curTetrisBlock.tabBlock.length; i++) {
			for (Block b : graphe.getListBlocks()) {
				if (curTetrisBlock.tabBlock[i].bounds.overlaps(b.bounds)) {
					collision = true;
				}
			}
		}
		if (collision) {
			for (int i = 0; i < curTetrisBlock.tabBlock.length; i++) {
				curTetrisBlock.tabBlock[i].move(
						curTetrisBlock.tabBlock[i].position.x,
						curTetrisBlock.tabBlock[i].position.y + 1);
				graphe.getListBlocks().add(curTetrisBlock.tabBlock[i]);
				graphe.getGraphe().get(new Vector2(curTetrisBlock.tabBlock[i].position.x, curTetrisBlock.tabBlock[i].position.y)).setBlock(true);
				
			}
			createTetrisBlock();
		}
	}

}
