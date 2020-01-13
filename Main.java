import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hslf.record.Sound;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String nom = "Blackhearth";
		Sheet sheet = new Sheet(nom);

		ArrayList<Cellule> tabCellule = new ArrayList<Cellule>();
		tabCellule.add(new Cellule(5, 0, "EL"));
		tabCellule.add(new Cellule(5, 1, "DORADO"));
		tabCellule.add(new Cellule(6, 1, "UNITED"));
		tabCellule.add(new Cellule(7, 1, "WE"));
		tabCellule.add(new Cellule(8, 1, "STAND"));
		tabCellule.add(new Cellule(9, 1, "DIVIDED"));
		tabCellule.add(new Cellule(10, 1, "WE"));
		tabCellule.add(new Cellule(11, 1, "FALL"));
		tabCellule.add(new Cellule(1, 11, "STENGTH"));
		tabCellule.add(new Cellule(1, 12, "OF"));
		tabCellule.add(new Cellule(1, 13, "A"));
		tabCellule.add(new Cellule(1, 14, "THOUSAND"));
		tabCellule.add(new Cellule(1, 15, "MEN"));

		sheet.writeSheet(tabCellule);
		
		//sheet.readSheetCol(tabCellule, 1);
		//sheet.readSheetLig(tabCellule, 1);
		sheet.readSheet(tabCellule);
		// sheet.creerSheet();
		// sheet.destroysheet();

		sheet.openSheet();

	}

}
