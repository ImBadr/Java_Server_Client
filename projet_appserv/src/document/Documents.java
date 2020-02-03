package document;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;

import abonne.Abonne;
import appExceptions.EmpruntException;
import appExceptions.RetourException;
import bibliotheque.Bibliotheque;
import bibliotheque.Document;
import mail.MailService;

public abstract class Documents implements Document{
	private int numero;
	private String titre;
	private boolean dispo;
	private int idAboReserv; //Le numéro de l'abonné qui a réserver le Livre
	private Timer time;
	private Date dateEmprunt;
	private Bibliotheque biblio;
	private List<Abonne> listeAbonnesANotifier; //La liste des abonnés voulant reserver ou emprunter ce document
	
	private static final long TEMPS_EMPRUNT = 1000 * 60 * 60 * 24 * 14;//2 semaines d'emprunt
	private static final long RETARD_AUTORISE = 1000 * 60 * 60 * 24 * 14;//14 jours = 1 209 600 000 = 1000 * 60 * 60 * 24 * 14
	private static final long TEMPS_RESERVATION = 2000 * 60 * 60;//2h
	
	public Documents(int numero,String titre, Bibliotheque bib) {
		this.numero = numero;
		this.dispo = true;
		this.idAboReserv = -1; //Numéro par défaut = -1 : Pas de réservation
		this.titre = titre;
		this.biblio = bib;
		this.listeAbonnesANotifier = new ArrayList<>();
	}
	
	@Override
	public int numero() {
		return this.numero;
	}

	@Override
	public synchronized void reserver(Abonne ab) throws EmpruntException{
		ab.estEnRetard(TEMPS_EMPRUNT, RETARD_AUTORISE);
		if (ab.getAutoriseALouer()) {
			if(dispo && idAboReserv==-1) {//Si disponible et non réserver
				this.idAboReserv = ab.getId();
				//Timer pour le retour au bout de x temps
				this.time = new Timer();
				time.schedule(new DocumentEnAttente(this,time),TEMPS_RESERVATION);//2h = 2000 * 60 * 60
			}
			else {
				if(this.idAboReserv!=ab.getId() && !this.listeAbonnesANotifier.contains(ab))
					this.listeAbonnesANotifier.add(ab);
				throw new  EmpruntException("Ce document est indisponible pour le moment. Un mail vous sera envoyé lorsque ce document sera de nouveau disponible.");
			}
		}
		else
			throw new EmpruntException("Vous n'avez pas le droit de réserver des documents !");
	}

	@Override
	public synchronized void emprunter(Abonne ab) throws EmpruntException{
		ab.estEnRetard(TEMPS_EMPRUNT, RETARD_AUTORISE);
		if (ab.getAutoriseALouer()) {
			if(idAboReserv==ab.getId() && dispo) {
				this.dispo = false;
				this.time.cancel();
				this.dateEmprunt = new Date();
			}
			else if(idAboReserv==-1 && dispo){
				this.dispo = false;
				this.idAboReserv = ab.getId();
				this.dateEmprunt = new Date();
				ab.ajouterDoc(this, this.dateEmprunt.getTime());
			}
			else {
				if(this.idAboReserv!=ab.getId() && !this.listeAbonnesANotifier.contains(ab))
					this.listeAbonnesANotifier.add(ab);
				throw new  EmpruntException("Ce document est indisponible pour le moment. Un mail vous sera envoyé lorsque ce document sera de nouveau disponible.");
			}
		}
		else 
			throw new EmpruntException("Vous n'avez pas le droit d'emprunter des documents !");
	}

	@Override
	public synchronized void retour() throws RetourException {
		if (dispo && idAboReserv!=-1) {
			idAboReserv = -1;
			MailService.sendAll(listeAbonnesANotifier, this.numero);
			this.listeAbonnesANotifier.removeAll(this.listeAbonnesANotifier);
		}
		else if (!dispo) {
			if (Math.random() < 1/100)//1 chance sur 100 que le document soit endommager
				this.biblio.getAbo(this.idAboReserv).setAutoriseALouer(false);
			this.biblio.getAbo(this.idAboReserv).estEnRetard(TEMPS_EMPRUNT, RETARD_AUTORISE);
			this.dispo = true;
			this.biblio.getAbo(this.idAboReserv).retourDoc(this);
			this.idAboReserv = -1;
			MailService.sendAll(listeAbonnesANotifier, this.numero);
			this.listeAbonnesANotifier.removeAll(this.listeAbonnesANotifier);
		}
		else
			throw new RetourException("Ce document n'a pas besoin d'être retourner !");
	}
	
	public String toString() {
		return "#" + this.numero + " " + this.titre;
	}
	
	protected int getIdAboReserv() {
		return this.idAboReserv;
	}
}
