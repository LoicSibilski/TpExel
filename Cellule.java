import java.util.ArrayList;

public class Cellule {
	private int col;
	private int lig;
	private String contenue;
	
	public int getCol() {
		return col;
	}

	public int getLig() {
		return lig;
	}
	
	public String getContenue() {
		return contenue;
	}

	public void setContenue(String contenue) {
		this.contenue = contenue;
	}

	public Cellule(int col, int lig, String contenue) {
		this.col = col;
		this.lig = lig;
		this.contenue = contenue;
	}


	public boolean deleteCell() {
		return false;	
	}
	
	public Cellule copyCell(Cellule c) {
		return new Cellule(c.col,c.lig,c.contenue);
	}

	@Override
	public String toString() {
		return "Cell [col=" + col + ", lig=" + lig + ", contenue=" + contenue + "]";
	}
	
	public  ArrayList<Cellule> getTabCellViaTabBonhomme(ArrayList<Bonhomme> tabBonhomme){
		ArrayList<Cellule> tabCell = new ArrayList<Cellule>();
		int indexTabCell = 0;
		for (Bonhomme bonH : tabBonhomme) {
			tabCell.add(new Cellule(0,indexTabCell,bonH.getPrenom()));
			tabCell.add(new Cellule(1,indexTabCell,bonH.getNom()));
			tabCell.add(new Cellule(2,indexTabCell,bonH.getNomSociete()));
			indexTabCell++;
		}
		
		return tabCell;
		
	}
	
}
