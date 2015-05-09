package com.gdxGames.CubeEscape;

import graphe.Sommet;

public class Block extends GameObject implements Constantes {

	public Block(float x, float y) {
		super(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
	}
	
	public Block(Sommet s){
		super(s.getCoords().x, s.getCoords().y, BLOCK_WIDTH, BLOCK_HEIGHT);
	}
	
	public void move(float x, float y){
		this.position.x = x;
		this.position.y = y;
		this.bounds.setPosition(position);
	}

}
