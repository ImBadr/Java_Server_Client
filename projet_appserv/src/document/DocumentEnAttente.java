package document;

import java.util.Timer;
import java.util.TimerTask;

import appExceptions.RetourException;
import bibliotheque.Document;
/**
 * Retourn le document qui n'est pas emprunter au bout de x temps
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/
public class DocumentEnAttente extends TimerTask{
	private Document doc;
	private Timer time;
	
	/**
	 * Initialise le documentEnAttente
	 * @param d : Le document
	 * @param t : Le timer
	 */
	public DocumentEnAttente(Document d, Timer t) {
		this.doc = d;
		this.time = t;
	}
	
	@Override
	public void run() {
		//Retourne le document lorsque le temps est écoulé
		try {
			this.doc.retour();
			this.time.cancel();
		} catch (RetourException e) {
			e.printStackTrace();
		}
	}

}
