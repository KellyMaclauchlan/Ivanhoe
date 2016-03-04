package network;

import java.util.Scanner;
import network.Client;

public class StartClient {
	public static Client client;
	public static Scanner sc = new Scanner(System.in);
	public static String ip; 
	public static int port; 
	
	public static void main(String[] args){
		
		/*System.out.println("Enter the IP of the Server Machine: ");
		ip = sc.nextLine();
		System.out.println("Enter the Port Number of the server Machine: ");
		port = sc.nextInt(); 
		System.out.println("\n");
		*/
		client = new Client();
		//client.connectToServer(ip, port);
	}
}
