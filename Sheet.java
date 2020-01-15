import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFileChooser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Sheet {

	String nom;
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet spreadsheet = workbook.createSheet(" Employee Info ");

	public Sheet(String nom) {
		this.nom = nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	/*
	 * Creer une boite de dialogue permettant de choisir un repertoire UNIQUEMENT,
	 * Creer un fichier .XLSX avec la variable NOM initialise dans le constructeur.
	 * 
	 */

	public String getNom() {
		return nom;
	}

	public ArrayList<Bonhomme> sheetGetListeBonhomme() {
		
		XSSFRow row;
		ArrayList<Bonhomme> listeB = new ArrayList<Bonhomme>();
		Iterator<Row> rowIterator = spreadsheet.iterator();
		
		while (rowIterator.hasNext()) {
			String s1 = "null";
			String s2 = "null";
			String s3 = "null";
			row = (XSSFRow) rowIterator.next();
			Iterator<Cell> cellIterator = row.iterator();
			while (cellIterator.hasNext()) {
				Cell cell = cellIterator.next();
				if (s1 == "null")
					s1 = cell.getStringCellValue();
					
				else if (s2 == "null")
					s2 = cell.getStringCellValue();
				else
					s3 = cell.getStringCellValue();
			}
			System.out.println(s1 + "\t" + s2 +"\t" +s3);
			listeB.add(new Bonhomme(s1,s2,s3));
		}
		return listeB;
	}

	
	
	public void creerSheet() throws IOException {
		final JFileChooser fcDir = new JFileChooser();
		File currDir = new File("D:\\Users\\\\Azzeras\\\\eclipse-workspace\\\\ToGlory\\".concat(nom));
		// fcDir.setCurrentDirectory(currDir);
		fcDir.setDialogTitle("Choisit le repertoire");
		fcDir.setSelectedFile(currDir);
		int returnVal = fcDir.showOpenDialog(fcDir);
		fcDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			FileOutputStream out = new FileOutputStream(new File(this.nom.concat(".xlsx")));
			workbook.write(out);
			out.close();
			System.out.println("Vous avez creer le nouveau fichier exel : " + nom);
		}
	}

	/*
	 * Initialise la variable currDir au repoertoire courant du projet. Creer une
	 * boite de dialogue permettant de choisir un FICHIER UNIQUEMENT, Ouvre le
	 * fichier
	 */
	public void openSheet() throws IOException {

		File currDir = new File("D:\\Users\\Azzeras\\eclipse-workspace\\ToGlory");
		final JFileChooser fcFile = new JFileChooser();
		fcFile.setCurrentDirectory(currDir);
		int returnVal = fcFile.showOpenDialog(fcFile);
		fcFile.setFileSelectionMode(JFileChooser.FILES_ONLY);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fcFile.getSelectedFile();
			Desktop desk = Desktop.getDesktop();
			if (file.exists()) {
				desk.open(file);
			}
		}
	}

	/*
	 * VARIABLES D ENTREE : une ArrayListe d'objet Celulle, nommee tabC, contenant
	 * toutes les cellules du fichier excel.
	 * 
	 * On appel la methode checkMaxColorLig, qui nous return une chaine de caractere
	 * de type Character+nombre On cree une variable len qui s'initialise avec le
	 * nombre de la chaine avec la methode substring. On cree une variable car qui
	 * prend le premier char de la chaine, SOIT 'C' pour colonne ou 'L' pour ligne.
	 * 
	 * Si il y a plus de colonne que de ligne, on ecrit toutes les colonnes avec la
	 * methode writeSheetAllCol Sinon on ecrit toutes les lignes avec la methode
	 * writeSheetAllLig
	 */
	public void writeSheet(ArrayList<Cellule> tabC) throws IOException {
		int index = 0;
		// Iterator<Row> rowIterator = spreadsheet.iterator();
		for (Cellule cellule : tabC) {
			if (index < tabC.size()) {
				writeSheetCell(cellule.getCol(), cellule.getLig(), cellule.getContenue());
				System.out.println(cellule.getCol() + " " + cellule.getLig() + " " + cellule.getContenue());
				index++;
			} else {
				break;
			}
		}

	}

	/*
	 * Meme methode que writeSheetOneLig, en remplaçant le mot ligne par colonne
	 *
	 */
	public void writeSheetOneCol(ArrayList<Cellule> tabC, int col) throws IOException {
		for (Cellule cellule : tabC) {
			if (cellule.getLig() == col) {
				writeSheetCell(cellule.getCol(), cellule.getLig(), cellule.getContenue());
			}
		}
	}

	/*
	 * VARIABLES D ENTREE : une ArrayListe d'objet Celulle, nommee tabC, contenant
	 * toutes les cellules du fichier excel Un entier lig, permettant de choisir la
	 * ligne a afficher
	 * 
	 * Pour chaque Cellule dans l'Arrayliste, on verifie si la ligne de la cellule
	 * correspond a la variable d'entree. Si oui : on appel la methode
	 * writeSheetCell avec en entree la colonne, la ligne et le contenu de la
	 * cellule.
	 * 
	 * çela permet d'ecrire seulement une ligne du fichier excel.
	 */
	public void writeSheetOneLig(ArrayList<Cellule> tabC, int lig) throws IOException {

		for (Cellule cellule : tabC) {
			if (cellule.getLig() == lig) {
				writeSheetCell(cellule.getCol(), cellule.getLig(), cellule.getContenue());
				System.out.println(cellule.getCol() + " " + cellule.getLig() + " " + cellule.getContenue());
			}
		}
	}

	/*
	 * VARIABLES D ENTREE : Un entier col, permettant de choisir le numero de la
	 * colonne dans laquelle introduire la donnee Un entier lig, permettant de
	 * choisir le numero de la ligne dans laquelle introduire la donnee Une chaine
	 * de caractere content, contenant la donnee a ecrire dans la case
	 * (colonne,ligne)
	 * 
	 * On cree une variable row, qui va recuperer la ligne dans laquelle on cree la
	 * donne On appel la methode 'checkIfRowIsEmpty', qui prend en entre la variable
	 * row et ressort un boolean TRUE si la ligne est vide, FALSE sinon.
	 * 
	 * Si la ligne est vide, on la cree, puis on cree la cellule avec le numero de
	 * la colonne et on insert la donnee. Sinon on cree juste la celulle puis on
	 * insert la donnee.
	 * 
	 * Enfin on sauvegarde le fichier.
	 */

	public void writeSheetCell(int col, int lig, String content) throws IOException {
		XSSFRow row;
		row = spreadsheet.getRow(lig);
		if (checkIfRowIsEmpty(row)) {
			row = spreadsheet.createRow(lig);
			Cell cell = row.createCell(col);
			cell.setCellValue(content);
		} else {
			Cell cell = row.createCell(col);
			cell.setCellValue(content);
		}

		FileOutputStream out = new FileOutputStream(new File(this.nom.concat(".xlsx")));
		workbook.write(out);
		out.close();
	}

	/*
	 * Cette methode est une imbrication des methode readSheetCol et readSheetLig
	 * 
	 * 
	 */
	public void readSheet(ArrayList<Cellule> tabC) {
		System.out.println("READING THE ENTIRE THING POG POGCHAMP POGGERS");
		int maxC = 0;
		int maxL = 0;
		int tabSize = tabC.size();
		boolean findCell = false;

		for (Cellule cell : tabC) {
			if (cell.getCol() < cell.getLig() && cell.getLig() > maxL)
				maxL = cell.getLig();
			else if (cell.getCol() > cell.getLig() && cell.getCol() > maxC)
				maxC = cell.getCol();
		}

		for (int nbLig = 0; nbLig < maxL + 2; nbLig++) {
			for (int nbCol = 0; nbCol < maxC + 2; nbCol++) {
				int indexCell = 0;
				findCell = false;
				while (indexCell < tabSize) {
					if (tabC.get(indexCell).getCol() == nbCol && tabC.get(indexCell).getLig() == nbLig) {
						System.out.print(tabC.get(indexCell).getContenue() + "\t");
						findCell = true;
						break;
					}
					indexCell++;
				}
				if (!findCell) {
					System.out.print("*" + "\t");
				}

			}
			System.out.println();
		}
		System.out.println(" ");

	}

	/*
	 * VOIR LA METHODE READSHEETLIG
	 */
	public void readSheetCol(ArrayList<Cellule> tabC, int col) {
		System.out.println("READING THE COLLUMN NUMBER : " + col);

		int maxL = 0;
		for (Cellule cell : tabC) {
			if (cell.getCol() < cell.getLig() && cell.getLig() > maxL)
				maxL = cell.getLig();
		}
		for (int nbLig = 0; nbLig < maxL + 2; nbLig++) {

			int indexCell = 0;
			int tabSize = tabC.size();
			boolean findCell = false;
			while (indexCell < tabSize) {
				if (tabC.get(indexCell).getCol() == col && tabC.get(indexCell).getLig() == nbLig) {
					System.out.print(tabC.get(indexCell).getContenue() + "\n");
					findCell = true;
					break;
				}
				indexCell++;
			}
			if (!findCell)
				System.out.print("*" + "\n");

		}
		System.out.println(" ");

	}

	/*
	 * Un entier lig, permettant de choisir le numero de la ligne dans laquelle lire
	 * la donnee une ArrayListe d'objet Celulle, nommee tabC, contenant toutes les
	 * cellules du fichier excel
	 * 
	 * On initialise un entier maxC, qui aura pour valeur le nombre max de colonne .
	 * 
	 * Pour chaque On initialse 2 entier indexCell, qui permet d'indexer le nombre
	 * de cellule et tabSize qui contient le nombre de celulle de l'ArrayList. On
	 * initialise le boolean findCell qui permet de savoir si l'on a ecrit une
	 * cellule, qui permet de se fait de casser la boucle while.
	 * 
	 * Tant que : on a pas rechercher dans toutes les cellules, Si on trouve un
	 * match entre une cellule et une ligne+colonne du fichier On ecrit la cellule
	 * dans la console, findCell devient TRUE et on sort de la boucle while, ayant
	 * deja ecrit.
	 * 
	 * Si la variable findCell est false, çela veut dire qu'il n'y a pas de cellule
	 * pour le duo de ligne/colonne, donc on ecrit une "*" dans la console.
	 * 
	 * dans le for, nbCol<maxC+2 : on pourrais mettre nbCol <= maxC+1, le +1 étant
	 * pour soucis de graphisme, rajoutant une ligne/colonne d'*.
	 * 
	 */

	public void readSheetLig(ArrayList<Cellule> tabC, int lig) {
		System.out.println("READING THE LINE NUMBER : " + lig);
		int maxC = 0;
		for (Cellule cell : tabC) {
			if (cell.getCol() < cell.getLig() && cell.getLig() > maxC)
				maxC = cell.getLig();
		}
		for (int nbCol = 0; nbCol < maxC + 2; nbCol++) {

			int indexCell = 0;
			int tabSize = tabC.size();
			boolean findCell = false;
			while (indexCell < tabSize) {
				if (tabC.get(indexCell).getCol() == nbCol && tabC.get(indexCell).getLig() == lig) {
					System.out.print(tabC.get(indexCell).getContenue() + "\t");
					findCell = true;
					break;
				}
				indexCell++;
			}
			if (!findCell)
				System.out.print("*" + "\t");

		}
		System.out.println(" ");

	}

	/*
	 * VARIABLE D'ENTREE : Un entier col, permettant de choisir le numero de
	 * lacolonne dans laquelle lire la donnee Un entier lig, permettant de choisir
	 * le numero de la ligne dans laquelle lire la donnee une ArrayListe d'objet
	 * Celulle, nommee tabC, contenant toutes les cellules du fichier excel
	 * 
	 * Pour toutes les cellules, Si les lignes et colonnes correspondent aux
	 * variable, on affiche sont contenu, puis on arrete la methode. Sinon, çela
	 * veut dire que la case du fichier excel est vide et on affiche une chaine de
	 * caratere vide.
	 */

	public void readSheetCell(ArrayList<Cellule> tabC, int col, int lig) {
		for (Cellule cell : tabC) {
			if (cell.getCol() == col && cell.getLig() == lig) {
				System.out.println(cell.getContenue());
				break;
			}
		}
		System.out.println(" ");
	}

	/*
	 * Creer une boite de dialogue permettant de choisir un fichier UNIQUEMENT,
	 * Detruis le fichier.
	 * 
	 */

	public void destroysheet() {
		final JFileChooser fcFile = new JFileChooser();
		int returnVal = fcFile.showOpenDialog(fcFile);
		File currDir = new File("D:\\Users\\Azzeras\\eclipse-workspace\\ToGlory");
		fcFile.setCurrentDirectory(currDir);
		fcFile.setFileSelectionMode(JFileChooser.FILES_ONLY);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fcFile.getSelectedFile();
			String s = file.getAbsolutePath();
			file.delete();
			System.out.println("Vous avez desintegrer le fichier : " + s);
		}
	}

	/*
	 * VARIABLE D'ENTREE : une ligne ROW de la classe XSSFRow de l'api apache POI
	 * 
	 * RETURN : Si la ligne est vide : TRUE Si la ligne contient quelque chose :
	 * FALSE
	 * 
	 */

	private boolean checkIfRowIsEmpty(XSSFRow row) {
		if (row == null || row.getLastCellNum() <= 0) {
			return true;
		}
		Cell cell = row.getCell((int) row.getFirstCellNum());
		if (cell == null || "".equals(cell.getRichStringCellValue().getString())) {
			return true;
		}
		return false;
	}

	/*
	 * VARIABLE D'ENTREE : une ArrayListe d'objet Celulle, nommee tabC, contenant
	 * toutes les cellules du fichier excel
	 * 
	 * On initialise 2 variable maxC et maxL a 0. Pour chaque cellule, on recherche
	 * la colonne max et la ligne max.
	 * 
	 * RETURN : Si maxL >= maxC on retourne la chaine se composant du char 'L' et de
	 * l'entier maxL. => "L856" Sinon on retourne la chaine se composant du char 'C'
	 * et de l'entier maxC. => "C45"
	 * 
	 */

	public String checkMaxColorLig(ArrayList<Cellule> tabC) {
		int maxC = 0;
		int maxL = 0;
		for (Cellule cell : tabC) {
			if (cell.getCol() < cell.getLig() && cell.getLig() > maxL)
				maxL = cell.getLig();
			else if (cell.getCol() > cell.getLig() && cell.getCol() > maxC)
				maxC = cell.getCol();
		}
		return (maxL >= maxC) ? "L" + maxL : "C" + maxC;

	}

}
