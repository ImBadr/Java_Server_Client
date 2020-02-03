package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import appExceptions.EmpruntException;
import bibliotheque.Bibliotheque;
import communication.Communication;
import verification.QuestionReponseServer;

/**
 * Le service de réservation de document
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/

public class ServiceReservation implements Runnable {
	
	private Bibliotheque biblio;
	private final Socket mySocket;
	
	/**
	 * Initialise le service
	 * @param s : Le socket
	 * @param bib : La bibliothèque
	 */
	public ServiceReservation(Socket s, Bibliotheque bib) {
		this.mySocket = s;
		this.biblio = bib;
	}
	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
			PrintWriter out = new PrintWriter(mySocket.getOutputStream(),true);
			int numeroAbo;
			int numeroDoc;
			do { //Tant que le client ne répond pas non a la question "Voulez-vous quitter ?"
				numeroAbo = QuestionReponseServer.AboExist(in, out, biblio,QuestionReponseServer.Service.Reservation); //Le numéro de l'abo
				numeroDoc = QuestionReponseServer.DocExist(in, out, biblio,QuestionReponseServer.Service.Reservation); //Le numéro du doc
				try {
					biblio.getDoc(numeroDoc).reserver(biblio.getAbo(numeroAbo));
					out.println("Vous avez reserver le document n° "+numeroDoc+"#END#");
				}catch (EmpruntException e) {
					out.println(Communication.encode(e.toString()+"\n #END#"));//Ici voir avec brette pour l'affichage de l'exception
				}
			}while(in.readLine().toLowerCase().equals("non"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	protected void finalize() {
		try {
			this.mySocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
