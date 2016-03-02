package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import org.apache.log4j.Logger;

import config.Config;
import ui.MainWindowController;

public class Client implements Runnable {
	public int ID = 0;
	public Socket socket = null;
	public Thread thread = null;
	public ClientThread client = null;
	public BufferedReader console = null;
	public BufferedReader inStream = null;
	public BufferedWriter outStream = null;
	
	public String testing = null;
	public Logger log = Logger.getLogger("Client");
	public MainWindowController window;

	public int getID(){
		return this.ID;
	}
	
	public Client(){}
	
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
			
			window = new MainWindowController();
			window.showWindow();
		}catch (IOException e){
			log.error(e);
			throw e; 
		}
	}

	public void run() {
		System.out.println(ID + ": Client Started...");
		//System.out.println("\n Hit 'ENTER' to start");
		
		/*
		 * check number of clients currently connected first
		 * prompt user for name
		 * send user name to server, add server to arraylist of players 
		 * 
		 */
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
		String send = "waiting";
		
		log.info("Message Received: " + msg);
		
	   	if (msg.equalsIgnoreCase("quit!")) {  
				System.out.println(ID + "Good bye. Press RETURN to exit ...");
				stop();
			} else {
				testing = msg;
				//System.out.println("Message Received: " + msg);
				send = processInput(msg);
				System.out.println(msg);
			}
	}


	public String testMessages() {
		return testing;
	}
	
	public String processInput(String msg){
		String output = "result";
		
		if(msg.equals(Config.PROMPT_JOIN)){
			
		}
		
		else if (msg.equals(Config.NEED_PLAYERS)){
			
			
		}
		
		else if (msg.equals(Config.PICK_COLOUR)){
			
		}
		
		else if (msg.equals(Config.PLAY)){
			
		}
		
		return msg; 
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
