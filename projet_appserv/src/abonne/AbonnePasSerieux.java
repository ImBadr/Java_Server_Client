package abonne;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Autorise un abonné à louer après x temps
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/
public class AbonnePasSerieux extends TimerTask{
	private Abonne abo;
	private Timer time;
	
	/**
	 * Initialise l'abonné pas sérieux
	 * @param ab : L'abonne à bannir
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
