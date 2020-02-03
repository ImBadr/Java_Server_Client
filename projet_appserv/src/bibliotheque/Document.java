package bibliotheque;

import abonne.Abonne;
import appExceptions.*;

public interface Document {
	int numero();
	void reserver(Abonne ab) throws EmpruntException ;
	void emprunter(Abonne ab) throws EmpruntException;
	void retour() throws RetourException; // retour document ou annulation réservation
}
