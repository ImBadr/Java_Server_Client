package appli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import communication.Communication;

public class AppliClient {
	private static String HOST = "127.0.0.1"; 
	
	public static String decodeEnd(String s) {
		return s.replace("#END#", "");
	}
	
	public static void main(String[] args) throws IOException {
		Socket socket = null;
		
		try {
			socket = new Socket(HOST, Integer.parseInt(args[0]));
			BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter (socket.getOutputStream(), true);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));
			String repServ = "";
			String repUser = "";
			boolean fin = false;
			do {
				repServ = Communication.decode(in.readLine());
				if (repServ.contains("#END#")) {
					System.out.println(AppliClient.decodeEnd(repServ));
					do {
						System.out.println("Voulez-vous quitter ?");
						repUser = clavier.readLine();
					}while(!repUser.toLowerCase().equals("oui") && !repUser.toLowerCase().equals("non"));
					fin = repUser.toLowerCase().equals("oui") ? true : false;
				}
				else {
					System.out.println(repServ);
					repUser = Communication.encode(clavier.readLine());
					fin = false;
				}
				out.println(repUser);
			}while(!repUser.toLowerCase().equals("oui") && !fin);
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
