package communication;
/**
 * Encoder et décoder le caractère "\n" pas pris en compte par le readLine de BufferedReader  
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/
public class Communication {
	/**
	 * Remplace le caractère \n par #n
	 * @param s : Le message à encoder
	 * @return Le message sans \n
	 */
	public static String encode(String s) {
		return s.replace("\n" , "#n");
	}
	
	/**
	 * Remplace le caractère #n par \n
	 * @param s : Le message à décoder
	 * @return Le message avec \n
	 */
	public static String decode(String s) {
		return s.replace("#n" , "\n");
	}
}
