package network;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import org.apache.log4j.Logger;


public class ClientThread extends Thread {
	private Socket         socket   = null;
	private Client      client   = null;
	private BufferedReader streamIn = null;
	private BufferedWriter  streamOut = null;
	private boolean done = false;
	private Logger log = Logger.getLogger("ClientThread");
	
	public ClientThread(Client client, Socket socket) throws IOException {  
		this.client = client;
		this.socket = socket;
		this.open();  
		this.start();
	}
	
	public void open () throws IOException {
		streamIn  = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		streamOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
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
	
	public void send(String msg) {
		try {
			streamOut.write(msg);
			streamOut.flush();
		} catch (IOException ioe) {
			log.error(ioe);
		}
	}
	
	public void run() {
		log.info("Client Thread " + socket.getLocalPort() + " running.");
		while (!done) { 
			System.out.println("In loop");
			try {  
				System.out.println("streamin: " + streamIn.readLine());
				client.handle(streamIn.readLine());
			} catch(IOException ioe) {  
	    }}
	}	

}
