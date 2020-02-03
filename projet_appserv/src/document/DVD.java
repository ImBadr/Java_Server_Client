package document;

import abonne.Abonne;
import appExceptions.EmpruntException;
import bibliotheque.Bibliotheque;

/**
 * Objet DVD
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/

public class DVD extends Documents{
	private int ageMini;
	
	/**
	 * Initialise le DVD
	 * @param n : Le numéro
	 * @param t : Le titre
	 * @param age : L'age minimum requis
	 */
	public DVD(int n,String t, int age, Bibliotheque bib) {
		super(n,t,bib);
		this.ageMini = age;
	}

	@Override
	public void reserver(Abonne ab) throws EmpruntException {
		if (ab.getAge() >= this.ageMini) {
			super.reserver(ab);
		}
		else
			//N'a pas l'age minimum requis
			//throw new EmpruntException("Vous êtes trop jeune pour ce document !");
			throw new EmpruntException("Vous n'avez pas l'age requis!");
	}

	@Override
	public void emprunter(Abonne ab) throws EmpruntException {
		if (ab.getAge() >= this.ageMini) {
			super.emprunter(ab);
		}
		else {
			throw new  EmpruntException("Vous n'avez pas l'age requis!");
		}
	}
	@Override
	public String toString() {
		return super.toString() + "' " + ((ageMini == 0) ? "tous public" : (ageMini == 12) ? "réservé aux plus de 12 ans" : "réservé aux plus de 16 ans") + " -> " + ((getIdAboReserv()==-1)? "Disponible" : "Indisponible");
	}

}
