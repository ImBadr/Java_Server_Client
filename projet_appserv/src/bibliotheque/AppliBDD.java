package bibliotheque;

import java.io.IOException;
import java.util.HashMap;

import abonne.Abonne;
import document.DVD;
import document.Livre;
import server.Server;
/**
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
 *Permet de lancer les différents serveurs et d'initialiser les abonnés et les documents
*/
public class AppliBDD {
	private final static int PORT_RESERVATION = 2500;
	private final static int PORT_EMPRUNT = 2600;
	private final static int PORT_RETOUR = 2700;
	public static void main(String[] args) {
		
		HashMap<Integer,Abonne> abos = new HashMap<>();//Liste des abonnés
		HashMap<Integer,Document> docs = new HashMap<>();//Liste des Documents (Livres et DVD)
		
		//Initialisation des abonnés et des documents
		abos.put(1,new Abonne(1,16,"adam41456@live.fr","Lowell","Rocheleau"));
		abos.put(2,new Abonne(2,16,"adam41456@live.fr","Roux","Bouchard"));
		abos.put(3,new Abonne(3,16,"adam41456@live.fr","Florus","Vaillancour"));
		abos.put(4,new Abonne(4,18,"adam41456@live.fr","Mayhew","Deslauriers"));
		abos.put(5,new Abonne(5,12,"adam41456@live.fr","Marcelle","Dennis"));
		abos.put(6,new Abonne(6,5,"adam41456@live.fr","Arianne","Tachel"));
		
		Bibliotheque biblio = new Bibliotheque(abos, docs);
		
		biblio.ajouterDocument(new Livre(1,"Harry Potter", biblio));
		biblio.ajouterDocument(new Livre(2,"Bible", biblio));
		biblio.ajouterDocument(new Livre(3,"Coran", biblio));
		biblio.ajouterDocument(new Livre(4,"Torah", biblio));
		biblio.ajouterDocument(new Livre(5,"Le Hobbit", biblio));
		biblio.ajouterDocument(new Livre(6,"Seigneur Des Anneaux", biblio));
		biblio.ajouterDocument(new DVD(7,"Deadpool", 16, biblio));
		biblio.ajouterDocument(new DVD(8,"300", 12, biblio));
		biblio.ajouterDocument(new DVD(9,"Bob l'éponge", 0, biblio));
		
		//Lancement des différents serveurs
		try {
			new Thread(new Server(PORT_RESERVATION, biblio)).start();//Reservation
            System.out.println("Le server de reservation est ON.");
            new Thread(new Server(PORT_RETOUR, biblio)).start();//Retour
            System.out.println("Le server de retour est ON.");
            new Thread(new Server(PORT_EMPRUNT, biblio)).start();//Emprunt
            System.out.println("Le server de emprunt est ON.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
