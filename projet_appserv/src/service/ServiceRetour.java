package service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import appExceptions.RetourException;
import bibliotheque.Bibliotheque;
import communication.Communication;
import verification.InputVerification;

/**
 * Le service de retour de document
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/

public class ServiceRetour implements Runnable {

	private Bibliotheque biblio;
	private final Socket mySocket;
	
	public ServiceRetour(Socket s, Bibliotheque bib) {
		this.mySocket = s;
		this.biblio = bib;
	}
	
	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
			PrintWriter out = new PrintWriter(mySocket.getOutputStream(),true);
			String numeroDoc;
			String msgFormatDoc;
			boolean exist;
			do { //Tant que le numéro de document saisi n'existe pas dans la bibliothèque
				exist = true;
				numeroDoc ="";
				msgFormatDoc = "Votre réponse n'est pas valide !\n" + "Quel document voulez-vous retourner?\n";
				do {//Tant que le numéro de document saisi n'existe pas dans la bibliothèque 
					if(!exist) {
						if (!InputVerification.isNumber(numeroDoc)) {//Si le client saisi pas un numéro
							out.println(msgFormatDoc);
						}
						else
							out.println(Communication.encode("Le numéro du document saisi est inconnu !\n" + "Quel document voulez-vous retourner?\n"));
					}
					else
						out.println(Communication.encode("Quel document voulez-vous retourner?\n"));
					numeroDoc = in.readLine();//Lecture de la saisi du client
					exist = InputVerification.isNumber(numeroDoc) ? biblio.docExist(Integer.parseInt(numeroDoc)) : false;
				}while(!exist);
				try {
					biblio.getDoc(Integer.parseInt(numeroDoc)).retour();
					out.println("Le document n° "+Integer.parseInt(numeroDoc)+" a bien été retourner. #END#");
			
				}catch (RetourException e) {
					out.println(Communication.encode(e.toString()+"\n #END#"));
				}
			}while(in.readLine().toLowerCase().equals("non"));
			
		}
		catch (IOException e) {
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
