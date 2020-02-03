package mail;

import java.util.List;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import abonne.Abonne;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
/**
 * Service pour envoyer des mails
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/
public class MailService {
	private static final String account = "aba.appserv@gmail.com";
	private static final String password = "BretteSoft01";
	
	/**
	 * Permet d'envoyer un mail
	 * @param mailTo : Le mail du destinataire
	 * @param msg : Le message à envoyer
	 * @throws Exception
	 */
	public static void sendEmail(String mailTo, String msg) throws Exception {
		System.out.println("Mail en cours de préparation...");
		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(account, password);
			}
		});
		
		Message message = prepareMessage(session, mailTo, msg);
		Transport.send(message);
		System.out.println("Mail envoyer avec succes !");
	}

	private static Message prepareMessage(Session session, String mailTo, String msg) {
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(account));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
			message.setSubject("Un document est disponible");
			message.setText(msg);
			return message;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void sendAll(List<Abonne> listeAbonnesANotifier, int numLivre) {
		String personnel;
		String msg;
		for(Abonne ab : listeAbonnesANotifier) {
			personnel = "Bonjour " + ab.getNom() +" "+ ab.getPrenom() + ",\n";
			msg = "Le Document n°" + numLivre +  " est à nouveau disponible !\nA bientôt sur notre service réservation !";
			msg+="\nSigné : « Sitting bull » BretteSoft©";
			personnel += msg;
			try {
				sendEmail(ab.getMail(),personnel);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
