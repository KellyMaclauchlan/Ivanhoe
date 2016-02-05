package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

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
	String testing;
	public Logger log = Logger.getLogger("Client");

	public int getID(){
		return this.ID;
	}
	
	public Client(){
		connectToServer(Config.DEFAULT_HOST, Config.DEFAULT_PORT);
	}
	
	public boolean connectToServer(String serverIP, int serverPort) {
		System.out.println(ID + ":Establishing connection. Please wait... ");
		//log.info(ID + ":Establishing connection. Please wait... ");
		boolean connected = false;
		
		try{
			this.socket = new Socket(serverIP, serverPort);
			this.ID = socket.getLocalPort(); 
			
			//log.info(ID + ": Connected to server: " + socket.getInetAddress());
	    	//log.info(ID + ": Connected to portid: " + socket.getLocalPort());
	    	
	    	this.start();
	    	//log.info("Client has started");
	    	connected = true;
		}catch(IOException e){
			connected = false;
			//log.error(e);
		}
		return connected;
	}
	private void start() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
