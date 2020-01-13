import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.swing.JFileChooser;

import org.apache.poi.ss.usermodel.Cell;
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

	public void creerSheet() throws IOException {
		final JFileChooser fcDir = new JFileChooser();
		File currDir = new File("D:\\Users\\Azzeras\\eclipse-workspace\\ToGlory");
		fcDir.setCurrentDirectory(currDir);
		int returnVal = fcDir.showOpenDialog(fcDir);
		fcDir.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
			FileOutputStream out = new FileOutputStream(new File(this.nom.concat(".xlsx")));
			workbook.write(out);
			out.close();
			System.out.println("Vous avez creer le nouveau fichier exel : " + nom);
		}
	}

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

	public void writeSheet(ArrayList<Cellule> tabC) throws IOException {
		String ligOrCol = checkMaxColorLig(tabC);
		int len = Integer.parseInt(ligOrCol.substring(1));
		char car = ligOrCol.charAt(0);
		if (car == 'C')
			writeSheetAllCol(tabC, len);
		else if (car == 'L')
			writeSheetAllLig(tabC, len);
		System.out.println(ligOrCol + " " + len + " " + car);

	}

	public void writeSheetAllCol(ArrayList<Cellule> tabC, int col) throws IOException {
		int index = 0;
		for (Cellule cellule : tabC) {
			if (index < col) {
				writeSheetCell(cellule.getCol(), cellule.getLig(), cellule.getContenue());
				index++;
			}
		}
	}

	public void writeSheetOneCol(ArrayList<Cellule> tabC, int col) throws IOException {
		for (Cellule cellule : tabC) {
			if (cellule.getLig() == col) {
				writeSheetCell(cellule.getCol(), cellule.getLig(), cellule.getContenue());
			}
		}
	}

	public void writeSheetAllLig(ArrayList<Cellule> tabC, int lig) throws IOException {
		int index = 0;
		for (Cellule cellule : tabC) {
			if (index < lig) {
				writeSheetCell(cellule.getCol(), cellule.getLig(), cellule.getContenue());
				index++;
			}
		}
	}

	public void writeSheetOneLig(ArrayList<Cellule> tabC, int lig) throws IOException {

		for (Cellule cellule : tabC) {
			if (cellule.getLig() == lig) {
				writeSheetCell(cellule.getCol(), cellule.getLig(), cellule.getContenue());
				System.out.println(cellule.getCol() + " " + cellule.getLig() + " " + cellule.getContenue());
			}
		}
	}

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

	public void readSheet() {
		//TODO readSheet
	}

	public void readSheetCol(int col) {

	}

	public void readSheetLig(int lig) {

	}

	public void readSheetCell(int col, int lig) {

	}

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
