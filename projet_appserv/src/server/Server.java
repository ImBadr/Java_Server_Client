package server;

import java.io.IOException;
import java.net.ServerSocket;

import bibliotheque.Bibliotheque;
import service.ServiceEmprunt;
import service.ServiceReservation;
import service.ServiceRetour;
/**
 * Le serveur d'emprunt des document
 * @author Adam DOUDAEV / Badr TADJER / Alberic CUSIN
 * @version 1.0
*/

public class Server implements Runnable {
    private final static int PORT_RESERVATION = 2500;
    private final static int PORT_EMPRUNT = 2600;
    private final static int PORT_RETOUR = 2700;
    private ServerSocket monServeur;
    private Bibliotheque biblio;
    private int port;

    /**
     * Initialise le serveur
     * @param p : Le numéro du port à écouter
     * @param b : La bibliothèque
     * @throws IOException
     */
    public Server(int p, Bibliotheque b) throws IOException {
        this.port = p;
        this.monServeur = new ServerSocket(p);
        this.biblio = b;
    }

    @Override
    public void run() {
        try {
            while (true) {
                switch (this.port) {
                case PORT_RESERVATION:
                    new Thread(new ServiceReservation(monServeur.accept(), this.biblio)).start();
                    break;
                case PORT_EMPRUNT:
                    new Thread(new ServiceEmprunt(monServeur.accept(), this.biblio)).start();
                    break;
                case PORT_RETOUR:
                    new Thread(new ServiceRetour(monServeur.accept(), this.biblio)).start();
                    break;
                default:
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void finalize() throws Throwable {
        this.monServeur.close();
    }

}
