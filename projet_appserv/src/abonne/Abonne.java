package abonne;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import bibliotheque.Document;

/**
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/
public class Abonne {
	private int id;
	private int age;
	private String mail;
	private String prenom;
	private String nom;
	private boolean autoriseALouer;
	private long dernierLivreEnRetard;
	private HashMap<Document, Long> dateEmprunts;
	
	private static final long TEMPS_BANNISSEMENT = 1000 * 60 * 60 * 24 * 30;//1000 * 60 * 60 * 24 * 30;//1 mois = 30 jours = 1000 * 60 * 60 * 24 * 30
	
	/**
	 * Permet d'initialiser l'abonn�
	 * @param numero : Le num�ro de l'abonn�
	 * @param age : L'age
	 * @param mail : Le Mail de contact
	 * @param prenom : Le pr�nom
	 * @param nom : Le nom
	 */
	public Abonne(int numero, int age, String mail,String prenom,String nom) {
		this.id = numero;
		this.age = age;
		this.mail = mail;
		this.prenom = prenom;
		this.nom = nom;
		this.autoriseALouer = true;
		this.dernierLivreEnRetard = 0;
		this.dateEmprunts = new HashMap<>();
	}
	
	/**
	 * Permet d'avoir le num�ro de l'abonn�
	 * @return num�ro
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Permet d'avoir l'age de l'abonn�
	 * @return age
	 */
	public int getAge() {
		return this.age;
	}
	
	/**
	 * Permet d'avoir le mail de contact de l'abonn�
	 * @return mail
	 */
	public String getMail() {
		return this.mail;
	}
	
	/**
	 * Permet d'avoir le pr�nom de l'abonn�
	 * @return pr�nom
	 */
	public String getPrenom() {
		return this.prenom;
	}
	
	/**
	 * Permet d'avoir le nom de l'abonn�
	 * @return nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Setter pour autoriser un Abonne � Emprunter ou R�server
	 * @param b
	 */
	public void setAutoriseALouer(boolean b) {
		if (!b) {
			this.dernierLivreEnRetard = new Date().getTime();
			deblocage();
		}
		this.autoriseALouer = b;
	}
	
	/**
	 * Permet de savoir si l'abonn� est autoris� � louer
	 * @return True si il est autoris�, sinon false
	 */
	public boolean getAutoriseALouer() {
		return this.autoriseALouer;
	}
	
	/**
	 * Permet d'ajouter un document a la liste des emprunt de l'abonn�
	 * @param d : Le document
	 * @param dateEmprunt : La date de l'emprunt
	 */
	public void ajouterDoc(Document d, long dateEmprunt) {
		this.dateEmprunts.put(d, dateEmprunt);
	}
	
	/**
	 * Permet d'enlever le document de la list des documents lou�s
	 * @param d : le document
	 */
	public void retourDoc(Document d) {
		this.dateEmprunts.remove(d);
	}
	
	/**
	 * Permet de savoir si un Abonn� a un retard sur un de ses livres emprunt�s
	 * @param retard : Le temps de retard autoriser
	 * @return True si au moins un des document est en retard
	 */
	public void estEnRetard(long tempsEmprunt, long retard) {
		if(!dateEmprunts.isEmpty()) {
			for(Map.Entry<Document, Long> entry : dateEmprunts.entrySet()) {
				if ((new Date().getTime() - entry.getValue()) >= retard + tempsEmprunt) {
					setAutoriseALouer(false);
				}
			}
		}
		deblocage();
	}
	
	private void deblocage() {
		if ((new Date().getTime() - this.dernierLivreEnRetard) >= TEMPS_BANNISSEMENT) {
			setAutoriseALouer(true);
			this.dernierLivreEnRetard = 0;
		}
	}
	
}
