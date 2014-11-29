package graphe;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Sommet {

	private final List<Sommet> voisins;
	private final Vector2 coords;
	
	private boolean soucoupe;
	private boolean et;
	
	public Sommet(Vector2 vec){
		this.voisins = new ArrayList<Sommet>();
		this.coords = vec;
		this.soucoupe = false;
		this.et = false;
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

	public boolean isEt() {
		return et;
	}

	public void setEt(boolean et) {
		this.et = et;
	}


}
