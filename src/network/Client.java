package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import config.Config;
import org.apache.log4j.Logger;

public class Client implements Runnable {
	public int ID = 0;
	public Socket socket = null;
	public Thread thread = null;
	public ClientThread client = null;
	public BufferedReader console = null;
	public BufferedReader inStream = null;
	public BufferedWriter outStream = null;
	
	private static Scanner sc = new Scanner(System.in);
	public static String ip;
	public static int port; 
	
	String testing;
	public Logger log = Logger.getLogger("Client");

	public int getID(){
		return this.ID;
	}
	
	public Client(){
		System.out.println("Enter the IP of the Server Machine: ");
		ip = sc.nextLine();
		System.out.println("Enter the Port Number of the server Machine: ");
		port = sc.nextInt(); 
		System.out.println("\n");
		
		connectToServer(ip, port);
	}
	
	public boolean connectToServer(String serverIP, int serverPort) {
		System.out.println(ID + ":Establishing connection. Please wait... ");
		log.info(ID + ":Establishing connection. Please wait... ");
		boolean connected = false;
		
		try{
			this.socket = new Socket(serverIP, serverPort);
			this.ID = socket.getLocalPort(); 
			
			log.info(ID + ": Connected to server: " + socket.getInetAddress());
	    	log.info(ID + ": Connected to portid: " + socket.getLocalPort());
	    	
	    	this.start();
	    	log.info("Client has started");
	    	connected = true;
		}catch(IOException e){
			connected = false;
			log.error(e);
		}
		return connected;
	}
	
	private void start() throws IOException{
		try{
			console = new BufferedReader(new InputStreamReader(System.in));
			inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outStream = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			log.info("Initializing buffers");
			
			if(thread == null){
				client = new ClientThread(this, socket);
				thread = new Thread(this);
				thread.start();
				log.info("New ClientThread has started");
			}
		}catch (IOException e){
			log.error(e);
			throw e; 
		}
	}

	public void run() {
		System.out.println(ID + ": Client Started...");
		System.out.println("\n Hit 'ENTER' to start");
		while (thread != null) {  
			try {  
				if (outStream != null) {
					outStream.flush();
					outStream.write(console.readLine() + "\n");
				} else {
					log.info(ID + ": Stream Closed");
					System.out.println(ID + ": Stream Closed");
				}
         }
         catch(IOException e) {  
         	log.error(e);
         	stop();
         }}
	}
	
	/* Client has to send something twice and then will receive its echo
	 * 
	 */
	
	public void handle(String msg) {
	   	if (msg.equalsIgnoreCase("quit!")) {  
				System.out.println(ID + "Good bye. Press RETURN to exit ...");
				stop();
			} else {
				testing = msg;
				System.out.println(msg);
			}
	}


	public String testMessages() {
		return testing;
	}

	public void stop() {
		try{
			if(thread != null){
				thread = null;
				if(console != null){console.close();}
				if(inStream != null){inStream.close();}
				if(outStream != null){outStream.close();}
				
				if(socket != null){socket.close();}
				
				this.socket = null;
				this.console = null;
				this.inStream = null;
				this.outStream = null; 
			}
		}catch (IOException e){
			log.error(e);
		}
		client.close();
	}
}
