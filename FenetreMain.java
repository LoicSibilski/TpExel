import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FenetreMain extends JFrame {
	JPanel container = new JPanel();
	
	private JButton boutonOuvrirFichierExel = new JButton("Ouvrir un fichier via LibreOffice");
	private JButton boutonCreeFichierExel = new JButton("Creer un nouveau fichier exel");
	private JButton boutonDeleteFichierExel = new JButton("Suprimer un fichier");
	private JButton boutonPatternEmail = new JButton("Choisit un pattern d'email");
	private JButton boutonChoisirFichierExel = new JButton("Choisit un fichier pour commencer");
	private JButton boutonStartProgiciel = new JButton("CEST PARTIS LETS GO EN AVANT LES AMIS");

	JLabel label;
	private String labelBonhomme;
	JTextArea jText = new JTextArea();

	Sheet sheet = new Sheet("oui");
	private int sizeH = 900;
	private int sizeW = 450;

	private boolean boolPattern;
	private boolean boolFichier;
	private boolean boolBoutProgicielVisible;
	
	public FenetreMain() {
		this.setTitle("PLE : Progiciel Ludiciel Exel");
		this.setSize(sizeH, sizeW);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		boutonCreeFichierExel.addActionListener(new BoutonListenerCreerFichier());
		boutonOuvrirFichierExel.addActionListener(new BoutonListenerOuvrirFichier());
		boutonDeleteFichierExel.addActionListener(new BoutonListenerDeleteFichier());
		boutonChoisirFichierExel.addActionListener(new BoutonListenerWinterSpellFichier());
		boutonPatternEmail.addActionListener(new BoutonListenerPatternMailFichier());
		boutonStartProgiciel.addActionListener(new BoutonListenerCommencerProgiciel());

		label = new JLabel();
		JPanel top = new JPanel();

		JPanel bot = new JPanel();
		JPanel middle = new JPanel();

		JPanel panelListe = new JPanel();
		panelListe.setBackground(Color.CYAN);
		panelListe.setPreferredSize(new Dimension(sizeH / 2, sizeW / 2));
		panelListe.setBorder(BorderFactory.createTitledBorder("Le fichier contient :"));
		panelListe.setForeground(Color.RED);
		panelListe.setLocation(sizeH, 1000);
		panelListe.add(label);
		middle.add(panelListe);
		
		bot.add(boutonPatternEmail);
		top.add(boutonCreeFichierExel);
		top.add(boutonOuvrirFichierExel);
		top.add(boutonDeleteFichierExel);
		top.add(boutonChoisirFichierExel);

		top.add(middle);
		top.add(bot);
		top.setBackground(Color.PINK);

		this.setContentPane(top);
		this.setVisible(true);
	}

	public void ajoutBoutonCommencerProgiciel() {
		if (boolFichier && boolPattern && !boolBoutProgicielVisible) {
			JPanel ajoutBout = new JPanel();
			ajoutBout.add(boutonStartProgiciel);
			this.add(ajoutBout);
			System.out.println("DEAR OF THE EKKO");
			SwingUtilities.updateComponentTreeUI(this);
			boolBoutProgicielVisible = true;
		}
	}

	public class BoutonListenerCreerFichier implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFrame jframe = new JFrame();
			JButton boutonEnvoyeNom = new JButton("OK");
			JDialog cont = new JDialog(jframe, "dialog box");
			cont.setTitle("CHOOE THE NAME");
			cont.setSize(450, 100);
			cont.setLocationRelativeTo(null);

			boutonEnvoyeNom.addActionListener(new BoutonListenerEnvoyeNom());
			jText = new JTextArea("Entre le nom du fichier ", 1, 30);

			JPanel top = new JPanel();
			top.add(boutonEnvoyeNom);
			top.add(jText);

			cont.add(top);
			cont.setVisible(true);

		}

		public class BoutonListenerEnvoyeNom implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String s = e.getActionCommand();
				if (s.equals("OK")) {
					sheet.setNom(jText.getText());
					System.out.println(sheet.getNom());
					try {
						sheet.creerSheet();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		}

	}

	public class BoutonListenerOuvrirFichier implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				sheet.openSheet();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}
	}

	public class BoutonListenerPatternMailFichier implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			
			
			
			
			
			boolPattern = true;
			ajoutBoutonCommencerProgiciel();
		}
	}

	public class BoutonListenerCommencerProgiciel implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			System.out.println("JADORE MANGER DES PATES MIAM MIAM YUUMI");
		}
	}

	public class BoutonListenerDeleteFichier implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			sheet.destroysheet();

		}
	}

	public class BoutonListenerWinterSpellFichier implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			File currDir = new File("D:\\Users\\Azzeras\\eclipse-workspace\\ToGlory");
			final JFileChooser fcFile = new JFileChooser();
			fcFile.setCurrentDirectory(currDir);
			int returnVal = fcFile.showOpenDialog(fcFile);
			fcFile.setFileSelectionMode(JFileChooser.FILES_ONLY);

			File file;
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fcFile.getSelectedFile();
				if (file.exists()) {
					try {
						FileInputStream fis = new FileInputStream(file);
						XSSFRow row;
						XSSFSheet sheetBouton = new XSSFWorkbook(fis).getSheetAt(0);
						ArrayList<Bonhomme> listeB = new ArrayList<Bonhomme>();
						Iterator<Row> rowIterator = sheetBouton.iterator();

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
							listeB.add(new Bonhomme(s1, s2, s3));
						}

						labelBonhomme = "<html><p>";

						for (Bonhomme bonhomme : listeB) {
							labelBonhomme += "<p>" + bonhomme.getPrenom() + "\t" + bonhomme.getNom() + "\t"
									+ bonhomme.getNomSociete() + "</p";
						}

						label.setText(" ");
						label.setText(labelBonhomme);
						boolFichier = true;
						ajoutBoutonCommencerProgiciel();
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			}

		}
	}

}
