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
 * Le service d'emprunt de document
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/

public class ServiceEmprunt implements Runnable{
	
	private Bibliotheque biblio;
	private final Socket mySocket;
	
	/**
	 * Initialise le service
	 * @param s : Le socket
	 * @param bib : La bibliothèque
	 */
	public ServiceEmprunt(Socket s, Bibliotheque bib) {
		this.biblio = bib;
		this.mySocket = s;
	}
	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
			PrintWriter out = new PrintWriter(mySocket.getOutputStream(),true);
			int numeroAbo;
			int numeroDoc;
			do { //Tant que le client ne répond pas non a la question "Voulez-vous quitter ?"
				numeroAbo = QuestionReponseServer.AboExist(in, out, biblio,QuestionReponseServer.Service.Emprunt); //Le numéro de l'abo
				numeroDoc = QuestionReponseServer.DocExist(in, out, biblio,QuestionReponseServer.Service.Emprunt); //Le numéro du doc
				try {
					biblio.getDoc(numeroDoc).emprunter(biblio.getAbo(numeroAbo));
					out.println("Vous avez emprunter le document n° "+numeroDoc+"#END#");
				}catch (EmpruntException e) {
					out.println(Communication.encode(e.toString()+"\n #END#"));
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
