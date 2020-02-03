package document;


import bibliotheque.Bibliotheque;


/**
 * Objet Livre
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/
public class Livre extends Documents {
	/**
	 * Initialise le livre
	 * @param numero : Le numéro
	 * @param titre : Le titre
	 */
	public Livre(int numero,String titre, Bibliotheque bib) {
		super(numero, titre,bib);
	}
	@Override
	public String toString() {
		return super.toString() + " -> " + ((getIdAboReserv()==-1)? "Disponible" : "Indisponible");
	}
	
}
