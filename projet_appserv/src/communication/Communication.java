package communication;
/**
 * Encoder et d�coder le caract�re "\n" pas pris en compte par le readLine de BufferedReader  
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/
public class Communication {
	/**
	 * Remplace le caract�re \n par #n
	 * @param s : Le message � encoder
	 * @return Le message sans \n
	 */
	public static String encode(String s) {
		return s.replace("\n" , "#n");
	}
	
	/**
	 * Remplace le caract�re #n par \n
	 * @param s : Le message � d�coder
	 * @return Le message avec \n
	 */
	public static String decode(String s) {
		return s.replace("#n" , "\n");
	}
}
