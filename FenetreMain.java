import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

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

@SuppressWarnings("serial")
public class FenetreMain extends JFrame {
	JPanel container = new JPanel();

	private JButton boutonOuvrirFichierExel = new JButton("Ouvrir un fichier via LibreOffice");
	private JButton boutonCreeFichierExel = new JButton("Creer un nouveau fichier exel");
	private JButton boutonDeleteFichierExel = new JButton("Suprimer un fichier");
	private JButton boutonPatternEmail = new JButton("Choisit un pattern d'email");
	private JButton boutonChoisirFichierExel = new JButton("Choisit un fichier pour commencer");
	private JButton boutonStartProgiciel = new JButton("CEST PARTIS LETS GO EN AVANT LES AMIS");

	public JLabel label;
	public JLabel labelChoixPattern = new JLabel();

	public JDialog cont;
	public JDialog dialChemFich = new JDialog();

	public JTextArea jText = new JTextArea();

	public Sheet sheet = new Sheet("oui");

	public Color color;

	public Random rand = new Random();

	public ArrayList<Bonhomme> listeB = new ArrayList<Bonhomme>();

	public Bonhomme premBonhomme;

	private String labelBonhomme;
	public String fileCurrentPath = new String();

	private int sizeH = 900;
	private int sizeW = 450;
	int red = 0;
	int green = 0;
	int blue = 0;

	private boolean boolPattern;
	private boolean boolFichier;
	private boolean boolBoutProgicielVisible;
	private boolean boolPrenomDotNom = false;
	private boolean boolNomDotPrenom = false;
	private boolean boolPDotNom = false;
	private boolean boolPNom = false;
	private boolean boolPrenomNom = false;
	private boolean boolNomPrenom = false;
	private boolean boolNom = false;
	private boolean boolPrenom = false;

	public FenetreMain() {
		this.setTitle("PLE : Progiciel Ludiciel Exel");
		this.setSize(sizeH, sizeW);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.setBackground(Color.GREEN);

		boutonCreeFichierExel.addActionListener(new BoutonListenerCreerFichier());
		boutonOuvrirFichierExel.addActionListener(new BoutonListenerOuvrirFichier());
		boutonDeleteFichierExel.addActionListener(new BoutonListenerDeleteFichier());
		boutonChoisirFichierExel.addActionListener(new BoutonListenerWinterSpellFichier());
		boutonPatternEmail.addActionListener(new BoutonListenerPatternMailFichier());
		boutonStartProgiciel.addActionListener(new BoutonListenerCommencerProgiciel());

		boutonCreeFichierExel.setBackground(new Color(184, 255, 247));
		boutonOuvrirFichierExel.setBackground(new Color(184, 255, 247));
		boutonDeleteFichierExel.setBackground(new Color(184, 255, 247));
		boutonChoisirFichierExel.setBackground(new Color(184, 255, 247));
		boutonPatternEmail.setBackground(new Color(184, 255, 247));
		boutonStartProgiciel.setBackground(new Color(184, 255, 247));

		label = new JLabel();
		JPanel top = new JPanel();

		JPanel bot = new JPanel();
		JPanel middle = new JPanel();

		JPanel panelListe = new JPanel();
		panelListe.setSize(150, 150);
		panelListe.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		panelListe.setBackground(new Color(0, 255, 255));
		panelListe.setPreferredSize(new Dimension(sizeH / 2, sizeW / 2));
		panelListe.setBorder(BorderFactory.createTitledBorder("Le fichier contient :"));
		// panelListe.setForeground(color = new Color( (red=rand.nextInt(255)),
		// (green=rand.nextInt(255)), (blue=rand.nextInt(255))));
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
						fileCurrentPath = sheet.getSheetPath();
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

			// JFrame jframe = new JFrame();
			JPanel top = new JPanel();
			cont = new JDialog();
			JButton boutonPrenomDotNom = new JButton("prenom.nom@soc");
			JButton boutonNomDotPrenom = new JButton("nom.prenom@soc");
			JButton boutonPDotNom = new JButton("p.nom@soc");
			JButton boutonPNom = new JButton("pnom@soc");
			JButton boutonPrenomNom = new JButton("prenomnom@soc");
			JButton boutonNomPrenom = new JButton("nomprenom@soc");
			JButton boutonNom = new JButton("nom@soc");
			JButton boutonPrenom = new JButton("prenom@soc");

			String stringLabelChoixPattern = new String();

			boutonPrenomDotNom.addActionListener(new BoutonListenerPrenomDotNom());
			boutonNomDotPrenom.addActionListener(new BoutonListenerNomDotPrenom());
			boutonPDotNom.addActionListener(new BoutonListenerPDotNom());
			boutonPNom.addActionListener(new BoutonListenerPNom());
			boutonPrenomNom.addActionListener(new BoutonListenerPrenomNom());
			boutonNomPrenom.addActionListener(new BoutonListenerNomPrenom());
			boutonNom.addActionListener(new BoutonListenerNom());
			boutonPrenom.addActionListener(new BoutonListenerPrenom());

			stringLabelChoixPattern = "<html> </br> <p> hbhbhb";
			// boutonPrenomDotNom.setBackground(color = new Color( (red=rand.nextInt(255)),
			// (green=rand.nextInt(255)), (blue=rand.nextInt(255)) ));

			boutonPrenomDotNom.setBackground(new Color(184, 255, 240));
			boutonNomDotPrenom.setBackground(new Color(184, 255, 247));
			boutonPDotNom.setBackground(new Color(184, 255, 247));
			boutonPNom.setBackground(new Color(184, 255, 247));
			boutonPrenomNom.setBackground(new Color(184, 255, 247));
			boutonNomPrenom.setBackground(new Color(184, 255, 247));
			boutonNom.setBackground(new Color(184, 255, 247));
			boutonPrenom.setBackground(new Color(184, 255, 247));

			labelChoixPattern.setText(stringLabelChoixPattern);
			cont.setTitle("CHOOE THE NAME");
			cont.setSize(600, 200);
			cont.setLocationRelativeTo(null);

			top.setBackground(new Color(1, 255, 144));

			top.add(boutonPrenomDotNom);
			top.add(boutonNomDotPrenom);
			top.add(boutonPDotNom);
			top.add(boutonPNom);
			top.add(boutonPrenomNom);
			top.add(boutonNomPrenom);
			top.add(boutonNom);
			top.add(boutonPrenom);
			top.add(labelChoixPattern);

			JButton boutonConfirmerChoixPattern = new JButton("OK");
			boutonConfirmerChoixPattern.addActionListener(new BoutonListenerConfirmerChoixPAttern());
			boutonConfirmerChoixPattern.setBackground(new Color(184, 255, 247));
			top.add(boutonConfirmerChoixPattern);

			cont.add(top);
			cont.setVisible(true);

		}

		public class BoutonListenerConfirmerChoixPAttern implements ActionListener {
			public void actionPerformed(ActionEvent e) {

				boolPattern = true;
				ajoutBoutonCommencerProgiciel();
				cont.dispose();

			}

		}

		public class BoutonListenerPrenomDotNom implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (boolFichier) {

					String stringLabelChoixPattern = new String();
					stringLabelChoixPattern = "<html><p>";
					stringLabelChoixPattern += listeB.get(0).prenomDotNom().concat(".com");
					labelChoixPattern.setText(stringLabelChoixPattern);

					boolPrenomDotNom = true;
					boolNomDotPrenom = false;
					boolPDotNom = false;
					boolPNom = false;
					boolPrenomNom = false;
					boolNomPrenom = false;
					boolNom = false;
					boolPrenom = false;
				} else {
					JFrame jframe = new JFrame();
					JDialog cont = new JDialog(jframe, "dialog box");
					JPanel top = new JPanel();
					JLabel labelErrorPattern = new JLabel();
					cont.setTitle("ERROR RED ALERT AUTODESTRUCTION IN 5 ...");
					cont.setSize(400, 100);
					cont.setLocationRelativeTo(null);

					labelErrorPattern.setText("Veuillez d'abord choisir un fichier");
					top.add(labelErrorPattern);
					cont.add(top);
					cont.setVisible(true);
					System.out.println(boolFichier);

				}

			}

		}

		public class BoutonListenerNomDotPrenom implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (boolFichier) {

					String stringLabelChoixPattern = new String();

					stringLabelChoixPattern = "<html><p>";
					stringLabelChoixPattern += listeB.get(0).nomDotPrenom().concat(".com");
					labelChoixPattern.setText(stringLabelChoixPattern);

					boolPrenomDotNom = false;
					boolNomDotPrenom = true;
					boolPDotNom = false;
					boolPNom = false;
					boolPrenomNom = false;
					boolNomPrenom = false;
					boolNom = false;
					boolPrenom = false;

				} else {
					JFrame jframe = new JFrame();
					JDialog cont = new JDialog(jframe, "dialog box");
					JPanel top = new JPanel();
					JLabel labelErrorPattern = new JLabel();
					cont.setTitle("ERROR RED ALERT AUTODESTRUCTION IN 5 ...");
					cont.setSize(400, 100);
					cont.setLocationRelativeTo(null);

					labelErrorPattern.setText("Veuillez d'abord choisir un fichier");
					top.add(labelErrorPattern);
					cont.add(top);
					cont.setVisible(true);
					System.out.println(boolFichier);

				}

			}
		}

		public class BoutonListenerPDotNom implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (boolFichier) {

					String stringLabelChoixPattern = new String();

					stringLabelChoixPattern = "<html><p>";
					stringLabelChoixPattern += listeB.get(0).pDotNom().concat(".com");
					labelChoixPattern.setText(stringLabelChoixPattern);

					boolPrenomDotNom = false;
					boolNomDotPrenom = false;
					boolPDotNom = true;
					boolPNom = false;
					boolPrenomNom = false;
					boolNomPrenom = false;
					boolNom = false;
					boolPrenom = false;

				} else {
					JFrame jframe = new JFrame();
					JDialog cont = new JDialog(jframe, "dialog box");
					JPanel top = new JPanel();
					JLabel labelErrorPattern = new JLabel();
					cont.setTitle("ERROR RED ALERT AUTODESTRUCTION IN 5 ...");
					cont.setSize(400, 100);
					cont.setLocationRelativeTo(null);

					labelErrorPattern.setText("Veuillez d'abord choisir un fichier");
					top.add(labelErrorPattern);
					cont.add(top);
					cont.setVisible(true);
					System.out.println(boolFichier);

				}

			}
		}

		public class BoutonListenerPNom implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (boolFichier) {

					String stringLabelChoixPattern = new String();

					stringLabelChoixPattern = "<html><p>";
					stringLabelChoixPattern += listeB.get(0).pNom().concat(".com");
					labelChoixPattern.setText(stringLabelChoixPattern);

					boolPrenomDotNom = false;
					boolNomDotPrenom = false;
					boolPDotNom = false;
					boolPNom = true;
					boolPrenomNom = false;
					boolNomPrenom = false;
					boolNom = false;
					boolPrenom = false;

				} else {
					JFrame jframe = new JFrame();
					JDialog cont = new JDialog(jframe, "dialog box");
					JPanel top = new JPanel();
					JLabel labelErrorPattern = new JLabel();
					cont.setTitle("ERROR RED ALERT AUTODESTRUCTION IN 5 ...");
					cont.setSize(400, 100);
					cont.setLocationRelativeTo(null);

					labelErrorPattern.setText("Veuillez d'abord choisir un fichier");
					top.add(labelErrorPattern);
					cont.add(top);
					cont.setVisible(true);
					System.out.println(boolFichier);

				}

			}
		}

		public class BoutonListenerPrenomNom implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (boolFichier) {

					String stringLabelChoixPattern = new String();

					stringLabelChoixPattern = "<html><p>";
					stringLabelChoixPattern += listeB.get(0).prenomNom().concat(".com");
					labelChoixPattern.setText(stringLabelChoixPattern);

					boolPrenomDotNom = false;
					boolNomDotPrenom = false;
					boolPDotNom = false;
					boolPNom = false;
					boolPrenomNom = true;
					boolNomPrenom = false;
					boolNom = false;
					boolPrenom = false;

				} else {
					JFrame jframe = new JFrame();
					JDialog cont = new JDialog(jframe, "dialog box");
					JPanel top = new JPanel();
					JLabel labelErrorPattern = new JLabel();
					cont.setTitle("ERROR RED ALERT AUTODESTRUCTION IN 5 ...");
					cont.setSize(400, 100);
					cont.setLocationRelativeTo(null);

					labelErrorPattern.setText("Veuillez d'abord choisir un fichier");
					top.add(labelErrorPattern);
					cont.add(top);
					cont.setVisible(true);
					System.out.println(boolFichier);

				}

			}
		}

		public class BoutonListenerNomPrenom implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (boolFichier) {

					String stringLabelChoixPattern = new String();

					stringLabelChoixPattern = "<html><p>";
					stringLabelChoixPattern += listeB.get(0).nomPrenom().concat(".com");
					labelChoixPattern.setText(stringLabelChoixPattern);

					boolPrenomDotNom = false;
					boolNomDotPrenom = false;
					boolPDotNom = false;
					boolPNom = false;
					boolPrenomNom = false;
					boolNomPrenom = true;
					boolNom = false;
					boolPrenom = false;

				} else {
					JFrame jframe = new JFrame();
					JDialog cont = new JDialog(jframe, "dialog box");
					JPanel top = new JPanel();
					JLabel labelErrorPattern = new JLabel();
					cont.setTitle("ERROR RED ALERT AUTODESTRUCTION IN 5 ...");
					cont.setSize(400, 100);
					cont.setLocationRelativeTo(null);

					labelErrorPattern.setText("Veuillez d'abord choisir un fichier");
					top.add(labelErrorPattern);
					cont.add(top);
					cont.setVisible(true);
					System.out.println(boolFichier);

				}

			}
		}

		public class BoutonListenerNom implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (boolFichier) {

					String stringLabelChoixPattern = new String();

					stringLabelChoixPattern = "<html><p>";
					stringLabelChoixPattern += listeB.get(0).nom().concat(".com");
					labelChoixPattern.setText(stringLabelChoixPattern);

					boolPrenomDotNom = false;
					boolNomDotPrenom = false;
					boolPDotNom = false;
					boolPNom = false;
					boolPrenomNom = false;
					boolNomPrenom = false;
					boolNom = true;
					boolPrenom = false;

				} else {
					JFrame jframe = new JFrame();
					JDialog cont = new JDialog(jframe, "dialog box");
					JPanel top = new JPanel();
					JLabel labelErrorPattern = new JLabel();
					cont.setTitle("ERROR RED ALERT AUTODESTRUCTION IN 5 ...");
					cont.setSize(400, 100);
					cont.setLocationRelativeTo(null);

					labelErrorPattern.setText("Veuillez d'abord choisir un fichier");
					top.add(labelErrorPattern);
					cont.add(top);
					cont.setVisible(true);
					System.out.println(boolFichier);

				}

			}
		}

		public class BoutonListenerPrenom implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (boolFichier) {

					String stringLabelChoixPattern = new String();

					stringLabelChoixPattern = "<html><p>";
					stringLabelChoixPattern += listeB.get(0).prenom().concat(".com");
					labelChoixPattern.setText(stringLabelChoixPattern);

					boolPrenomDotNom = false;
					boolNomDotPrenom = false;
					boolPDotNom = false;
					boolPNom = false;
					boolPrenomNom = false;
					boolNomPrenom = false;
					boolNom = false;
					boolPrenom = true;
				} else {
					JFrame jframe = new JFrame();
					JDialog cont = new JDialog(jframe, "dialog box");
					JPanel top = new JPanel();
					JLabel labelErrorPattern = new JLabel();
					cont.setTitle("ERROR RED ALERT AUTODESTRUCTION IN 5 ...");
					cont.setSize(400, 100);
					cont.setLocationRelativeTo(null);

					labelErrorPattern.setText("Veuillez d'abord choisir un fichier");
					top.add(labelErrorPattern);
					cont.add(top);
					cont.setVisible(true);
					System.out.println(boolFichier);

				}

			}
		}
	}

	public class BoutonListenerCommencerProgiciel implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JPanel middle = new JPanel();
			JLabel labelTextExplicatif = new JLabel();
			String stringLabel = "<html><p>";
			JButton boutonChoisirAutreFich = new JButton("Choisir un autre fichier");
			JButton boutonCommencer = new JButton("CEST PARTIS LES AMIS");

			boutonChoisirAutreFich.addActionListener(new BoutonListenerChoisirAutreFich());
			boutonCommencer.addActionListener(new BoutonListenerCommencer());

			boutonChoisirAutreFich.setBackground(new Color(184, 255, 240));
			boutonCommencer.setBackground(new Color(184, 255, 240));

			stringLabel += "Les mails se creeront dans le fichier :" + "<p>" + fileCurrentPath + "</html>";
			labelTextExplicatif.setText(stringLabel);

			middle.add(labelTextExplicatif);
			middle.add(boutonCommencer);
			middle.add(boutonChoisirAutreFich);
			dialChemFich.add(middle);

			dialChemFich.setTitle("CHOOE THE NAME");
			dialChemFich.setSize(600, 200);
			dialChemFich.setLocationRelativeTo(null);
			dialChemFich.setVisible(true);

		}

		public class BoutonListenerChoisirAutreFich implements ActionListener {

			public void actionPerformed(ActionEvent e) {

			}
		}

		public class BoutonListenerCommencer implements ActionListener {

			public void actionPerformed(ActionEvent e) {

				File file = new File(fileCurrentPath);

				//FileOutputStream out = new FileOutputStream(file);
				
				

				int i = 0;
				for (Bonhomme bon : listeB) {
					try {
						sheet.writeSheetCell(4, i, bon.prenomDotNom());
						i++;
						System.out.println(bon.prenomDotNom());
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (boolPrenomDotNom) {
					System.out.println("k");
				} else if (boolNomDotPrenom) {

				} else if (boolPDotNom) {

				} else if (boolPNom) {

				} else if (boolPrenomNom) {

				} else if (boolNomPrenom) {

				} else if (boolNom) {

				} else if (boolPrenom) {

				}
			}
		}

	}

	public class BoutonListenerDeleteFichier implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			sheet.destroysheet();

		}
	}

	public class BoutonListenerWinterSpellFichier implements ActionListener {
		@SuppressWarnings("resource")
		public void actionPerformed(ActionEvent e) {

			File currDir = new File("D:\\Users\\Azzeras\\eclipse-workspace\\ToGlory");
			final JFileChooser fcFile = new JFileChooser();
			fcFile.setCurrentDirectory(currDir);
			int returnVal = fcFile.showOpenDialog(fcFile);
			fcFile.setFileSelectionMode(JFileChooser.FILES_ONLY);

			File file;
			listeB.clear();
//			labelBonhomme= " ";
//			label.setText(labelBonhomme);
//			label.r
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				file = fcFile.getSelectedFile();
				if (file.exists()) {
					try {
						FileInputStream fis = new FileInputStream(file);
						XSSFRow row;
						XSSFSheet sheetBouton = new XSSFWorkbook(fis).getSheetAt(0);
						Iterator<Row> rowIterator = sheetBouton.iterator();
						fileCurrentPath = " ";
						fileCurrentPath = file.getAbsolutePath();
						System.out.println(fileCurrentPath);
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
