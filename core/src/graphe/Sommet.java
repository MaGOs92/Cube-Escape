package graphe;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.math.Vector2;
import com.gdxGames.CubeEscape.Block;

public class Sommet {

	private final List<Sommet> voisins;
	private final Vector2 coords;
	private boolean isTetrisBlock;
	private boolean isBlock;
	private final Block block;
	
	public Sommet(Vector2 vec){
		voisins = new ArrayList<Sommet>();
		coords = vec;
		isBlock = false;
		
		block = new Block(vec.x, vec.y);
	}

	public List<Sommet> getVoisins() {
		return voisins;
	}

	public Vector2 getCoords() {
		return coords;
	}
	
	public boolean isTetrisBlock() {
		return isTetrisBlock;
	}

	public void setTetrisBlock(boolean isTetrisBlock) {
		this.isTetrisBlock = isTetrisBlock;
	}

	public boolean isBlock() {
		return isBlock;
	}

	public void setBlock(boolean isBlock) {
		this.isBlock = isBlock;
	}

	public Block getBlock(){
		return block;
	}

}
