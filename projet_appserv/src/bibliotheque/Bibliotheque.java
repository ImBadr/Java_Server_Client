package bibliotheque;

import java.util.ArrayList;
import java.util.HashMap;

import abonne.Abonne;
/**
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/
public class Bibliotheque {
	private HashMap<Integer,Abonne> abos;
	private HashMap<Integer,Document> docs;
	
	/**
	 * Simple initialisation de la Bibliothèque
	 * @param abos : La liste des abonnés
	 * @param docs : La liste des documents
	 */
	public Bibliotheque(HashMap<Integer,Abonne> abos,HashMap<Integer,Document> docs) {
		this.abos = abos;
		this.docs = docs;
	}
	
	public void ajouterDocument(Document d) {
		this.docs.put(d.numero(), d);
	}
	/**
	 * Permet de savoir si le numéro du document saisi est présent dans la Bibliothèque
	 * @param num : Le numéro du document rechercher
	 * @return True si il existe, sinon false 
	 */
	public boolean docExist(int num) {
		return this.docs.containsKey(num);
	}
	
	/**
	 * Permet de savoir si le numéro de l'abonné saisi correspond à un abonné de la bibliothèque
	 * @param num : Le numéro de l'abonné rechercher
	 * @return True si il existe, sinon false
	 */
	public boolean aboExist(int num) {
		return this.abos.containsKey(num);
	}
	
	/**
	 * Permet d'avoir la liste des Documents de la bibliothèque
	 * @return La liste des Documents
	 */
	public ArrayList<Document> getDocs(){
		return new ArrayList<Document>(this.docs.values());   
	}
	
	/**
	 * Permet d'avoir l'abonné avec son numéro
	 * @param num : Le numéro de l'abonné
	 * @return L'abonné si il existe dans la liste, sinon null 
	 */
	public Abonne getAbo(int num) {
		return this.abos.get(num);
	}
	
	/**
	 * Permet d'avoir un documment avec son numéro
	 * @param num : Le numéro du doccument
	 * @return Le document si il est présent dans la liste, sinon null
	 */
	public Document getDoc(int num) {
		return this.docs.get(num);
	}

}
