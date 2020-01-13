
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
	
	
}
