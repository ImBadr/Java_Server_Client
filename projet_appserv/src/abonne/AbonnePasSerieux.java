package abonne;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Autorise un abonn� � louer apr�s x temps
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/
public class AbonnePasSerieux extends TimerTask{
	private Abonne abo;
	private Timer time;
	
	/**
	 * Initialise l'abonn� pas s�rieux
	 * @param ab : L'abonne � bannir
	 * @param t : Le timer
	 */
	public AbonnePasSerieux(Abonne ab, Timer t) {
		this.abo = ab;
		this.time = t;
	}
	@Override
	public void run() {
		this.abo.setAutoriseALouer(true);
		this.time.cancel();
	}

}
