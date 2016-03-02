package network;
import config.Config;
import network.Server;
import java.util.Scanner;

import game.GameEngine;

public class StartServer {	
	private static Boolean done = Boolean.FALSE; 
	private static Boolean started = Boolean.FALSE;
	private static Scanner sc = new Scanner(System.in);
	private static Server server = null; 
	
	public static void main(String[] args){
		System.out.println("type run | shutdown");
		
		do{
			String input = sc.nextLine();
			
			if(input.equalsIgnoreCase(Config.RUN) && !started){
				System.out.println("Starting server...");
				//log.info("Starting server");
				server = new Server();
				started = Boolean.TRUE; 
			}
			
			if(input.equalsIgnoreCase(Config.SHUTDOWN) && started){
				System.out.println("Shutting down server...");
				//log.info("Shutting down server");
				server.shutdown();;
				started = Boolean.FALSE;
				done = Boolean.FALSE; 
			}
		}while(!done);
		System.exit(1); 
	}
}	
