package graphe;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Sommet {

	private final List<Sommet> voisins;
	private final Vector2 coords;
	
	private boolean soucoupe;
	private boolean block;
	private final Rectangle rect;
	
	public Sommet(Vector2 vec){
		this.voisins = new ArrayList<Sommet>();
		this.coords = vec;
		this.soucoupe = false;
		this.block = false;
		this.rect = new Rectangle(vec.x, vec.y, 1, 1);
	}

	public List<Sommet> getVoisins() {
		return voisins;
	}

	public Vector2 getCoords() {
		return coords;
	}

	public boolean isSoucoupe() {
		return soucoupe;
	}

	public void setSoucoupe(boolean soucoupe) {
		this.soucoupe = soucoupe;
	}

	public boolean isBlock() {
		return block;
	}

	public void setBlock(boolean block) {
		this.block = block;
	}
	
	public Rectangle getRect(){
		return rect;
	}

}
