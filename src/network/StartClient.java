package network;
import java.util.Scanner;

import network.Client;

public class StartClient {
	/*private static Scanner sc = new Scanner(System.in);
	public static String ip;
	public static int port; */
	public static Client client;
	
	public static void main(String[] args){
		//new AppClient(Config.DEFAULT_HOST, Config.DEFAULT_PORT); 

		/*System.out.println("Enter the IP of the Server Machine: ");
		ip = sc.nextLine();
		System.out.println("Enter the Port Number of the server Machine: ");
		port = sc.nextInt(); 
		System.out.println("\n");*/
		//new Client(ip, port);
		client = new Client();
		//client.connectToServer(ip, port);
		
		
	}
}
