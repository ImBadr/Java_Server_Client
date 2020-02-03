package verification;

public class InputVerification {
	
	public static boolean isNumber(String s) {
		if(s.isEmpty())
			return false;
		try {
			Integer.parseInt(s);
		}
		catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
}
