package network;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import org.apache.log4j.Logger;


public class ClientThread extends Thread {
	private Socket         socket   = null;
	private Client      client   = null;
	private BufferedReader streamIn = null;
	private boolean done = false;
	private Logger log = Logger.getLogger("ClientThread");
	
	public ClientThread(Client client, Socket socket) throws IOException {  
		super();
		this.client = client;
		this.socket = socket;
		this.open();  
		this.start();
	}
	
	public void open (){
		try {
			streamIn  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			log.error(e);
			client.stop();
		}
	}
	
	public void close () {
		done = true;
		try {  
			if (streamIn != null) streamIn.close();
			if (socket != null) socket.close();
			this.socket = null;
			this.streamIn = null;
		} catch(IOException ioe) { 
			log.error(ioe);
	   }	
	   log.info("Closing Thread" + socket.getLocalPort());
	}
	
	public void run() {
		log.info("Client Thread " + socket.getLocalPort() + " running.");
		while (!done) { 
			try {  
				log.info(streamIn.readLine());
				client.handle(streamIn.readLine());
			} catch(IOException ioe) {  
				log.error(ioe);
			}
	    }
	}	

}
