
public class Bonhomme {
	
	private String nom;
	private String prenom;
	private String nomSociete;
	
	public Bonhomme(String nom, String prenom, String nomSociete) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.nomSociete = nomSociete;
	}
	public String getNomSociete() {
		return nomSociete;
	}
	public void setNomSociete(String nomSociete) {
		this.nomSociete = nomSociete;
	}
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	
	
	
	/*
	 * Les methodes suivantes renvoie une forme differente demail, en appelant la methode addSociete qui ajoute la fin du 
	 * mail avec le @
	 * 
	 * RETURN : chaine de caractere suivant la forme du mail
	 */
	public String prenomDotNom() {
		return this.getPrenom().concat(".").concat(this.getNom()).concat(addSociete());
	}
	
	public String nomDotPrenom() {
		return this.getNom().concat(".").concat(this.getPrenom()).concat(addSociete());
	}
	public String pDotNom(){
		return Character.toString(this.getPrenom().charAt(0)).concat(".").concat(this.getNom()).concat(addSociete());
		
	}
	public String pNom(){
		return Character.toString(this.getPrenom().charAt(0)).concat(this.getNom()).concat(addSociete());
	}
	public String prenomNom(){
		return this.getPrenom().concat(this.getNom()).concat(addSociete());
	}
	public String nomPrenom(){
		return this.getNom().concat(this.getPrenom()).concat(addSociete());
	}
	public String nom(){
		return this.getNom().concat(addSociete());
	}
	public String prenom(){
		return this.getPrenom().concat(addSociete());
	}
	
	/*
	 * Utiliser dans les méthodes de la classe Bonhomme pour ajouter le nom de la societe au mail.
	 * RETURN : la chaine de caractere "@nomSociete".
	 */
	
	public String addSociete() {
		return "@".concat(this.getNomSociete());
	}
	
	
	
	
	
	
}
