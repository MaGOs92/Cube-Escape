package graphe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.badlogic.gdx.math.Vector2;
import com.gdxGames.CubeEscape.Block;

public class Graphe {

	private Map<Vector2, Sommet> graphe;
	private int tailleX;
	private int tailleY;
	private List<Block> listBlocks;

	public Graphe(int x, int y) {

		graphe = new HashMap<Vector2, Sommet>();
		listBlocks = new ArrayList<Block>();
		tailleX = x;
		tailleY = y;
		construireSommets();
		ajouterVoisins();
		setSol();
	}

	public void construireSommets() {

		for (int i = 0; i < tailleY; i++) {
			for (int j = 0; j < tailleX; j++) {
				Vector2 vec = new Vector2(j, i);
				graphe.put(vec, new Sommet(vec));
			}
		}
	}

	public void ajouterVoisins() {

		for (int i = 0; i < graphe.size(); i++) {
			int posX = i % tailleX;
			int posY = i / tailleX;

			Sommet curSommet = graphe.get(new Vector2(posX, posY));

			for (int j = posY - 1; j < posY + 2; j++) {
				for (int k = posX - 1; k < posX + 2; k++) {
					if (j != posY || k != posX) {
						Sommet voisin = graphe.get(new Vector2(k, j));
						if (voisin != null) {
							curSommet.getVoisins().add(voisin);
						}
					}

				}
			}
// Debug
//			System.out.println("Sommet courant : " + curSommet.getCoords().x
//					+ ", " + curSommet.getCoords().y);
//			for (Sommet sommet : curSommet.getVoisins()) {
//				System.out.println("Sommet voisin : " + sommet.getCoords().x
//						+ ", " + sommet.getCoords().y);
//			}

		}
	}

	public void setSol() {
		Vector2 v = new Vector2(0, 0);
		for (int i = 0; i < tailleX; i++) {
			v.x = i;
			listBlocks.add(new Block(graphe.get(v)));
		}
	}

	public Map<Vector2, Sommet> getGraphe() {
		return graphe;
	}

	public List<Block> getListBlocks() {
		return listBlocks;
	}

}
