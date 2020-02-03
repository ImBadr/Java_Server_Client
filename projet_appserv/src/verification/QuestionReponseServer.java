package verification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import bibliotheque.Bibliotheque;
import bibliotheque.Document;
import communication.Communication;

public class QuestionReponseServer {
	
	public static enum Service{
		Reservation,
		Emprunt,
		Retour
	}
	
	public static int AboExist(BufferedReader in,PrintWriter out,Bibliotheque biblio,Service s) throws IOException {
		boolean exist = true;
		String numeroAbo = "";
		String msgInconnu = "Le numéro d'abonné saisi est inconnu !\n" + "Quel est votre numéro abonné ?\n"; //Message pour un numéro d'abonné inconnu
		String msgFormatAbo = "Votre réponse n'est pas valide !\n" + "Quel est votre numéro d'abonné ?\n"; //Message pour une mauvaise saisi du numéro de l'abonné
		String bonjour = "Bonjour, bienvenue au service ";
		switch(s) {
		case Reservation:
			bonjour += "de réservation.\n";
			break;
		case Emprunt:
			bonjour += "d'emprunt.\n";
			break;
		default:
			bonjour += "inconnu !\n";
			break;
		}
		bonjour+="Quel est votre numéro abonné ?\n";
		do { //Tant que le numéro d'abonné saisi n'existe pas dans la bibliothèque
			if(!exist) {
				if (!InputVerification.isNumber(numeroAbo)) { //Si le client saisi pas un numéro
					out.println(Communication.encode(msgFormatAbo));
				}
				else
					out.println(Communication.encode(msgInconnu));
			}
			else
				//Message de bienvenue
				out.println(Communication.encode(bonjour));
			numeroAbo = in.readLine();//Lecture de la saisi du client
			exist = InputVerification.isNumber(numeroAbo)?exist = biblio.aboExist(Integer.parseInt(numeroAbo)):false;
		}while(!exist);
		return Integer.parseInt(numeroAbo);
	}
	
	public static int DocExist(BufferedReader in,PrintWriter out,Bibliotheque biblio,Service s) throws IOException {
		String docs ="Voici la liste des documents de la bibliothèque :\n";
		String msgFormatDoc = "Votre réponse n'est pas valide !\n";
		String numeroDoc = "";
		boolean exist = true;
		for(Document d : biblio.getDocs()) { //Ajout des docs dispo
			docs += d.toString()+"\n";
		}
		String action = "Quel document voulez-vous ";
		switch(s) {
		case Reservation:
			action += "réserver ?\n";
			break;
		case Emprunt:
			action += "emprunter.\n";
			break;
		default:
			action += "inconnu !\n";
			break;
		}
		msgFormatDoc+=action;
		do { //Tant que le numéro de document saisi n'existe pas dans la bibliothèque
			if(!exist) {
				if (!InputVerification.isNumber(numeroDoc)) { //Si le client saisi pas un numéro
					out.println(msgFormatDoc);
				}
				else
					out.println(Communication.encode("Le numéro du document saisi est inconnu !\n" + action));
			}
			else
				out.println(Communication.encode(docs + action));
			numeroDoc = in.readLine(); //Lecture de la saisi du client
			exist = InputVerification.isNumber(numeroDoc) ? biblio.docExist(Integer.parseInt(numeroDoc)) : false;
		}while(!exist);
		return Integer.parseInt(numeroDoc);
	}
}
