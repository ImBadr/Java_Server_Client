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
	 * Simple initialisation de la Biblioth�que
	 * @param abos : La liste des abonn�s
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
	 * Permet de savoir si le num�ro du document saisi est pr�sent dans la Biblioth�que
	 * @param num : Le num�ro du document rechercher
	 * @return True si il existe, sinon false 
	 */
	public boolean docExist(int num) {
		return this.docs.containsKey(num);
	}
	
	/**
	 * Permet de savoir si le num�ro de l'abonn� saisi correspond � un abonn� de la biblioth�que
	 * @param num : Le num�ro de l'abonn� rechercher
	 * @return True si il existe, sinon false
	 */
	public boolean aboExist(int num) {
		return this.abos.containsKey(num);
	}
	
	/**
	 * Permet d'avoir la liste des Documents de la biblioth�que
	 * @return La liste des Documents
	 */
	public ArrayList<Document> getDocs(){
		return new ArrayList<Document>(this.docs.values());   
	}
	
	/**
	 * Permet d'avoir l'abonn� avec son num�ro
	 * @param num : Le num�ro de l'abonn�
	 * @return L'abonn� si il existe dans la liste, sinon null 
	 */
	public Abonne getAbo(int num) {
		return this.abos.get(num);
	}
	
	/**
	 * Permet d'avoir un documment avec son num�ro
	 * @param num : Le num�ro du doccument
	 * @return Le document si il est pr�sent dans la liste, sinon null
	 */
	public Document getDoc(int num) {
		return this.docs.get(num);
	}

}
