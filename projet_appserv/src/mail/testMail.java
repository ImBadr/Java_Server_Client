package mail;

public class testMail {
	//Simple test du service de mail
	public static void main(String[] args) {
		try {
			MailService.sendEmail("adam41456@live.fr", "Salut je test le truc");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
