package network;
import network.Server;

public class StartServer {	
	private static Boolean started = Boolean.FALSE;
	private static Server server = null; 
	
	public static void main(String[] args){
		System.out.println("Starting server...");
		server = new Server();
		started = Boolean.TRUE;
	}
}	
