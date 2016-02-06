package config;

public class Config {
	public static int DEFAULT_PORT = 3000; 
	public static int PORT; 
	public static String DEFAULT_HOST = "127.0.0.1";
	public static String HOST;
	
	public static int MAX_PLAYERS = 5; 
	
	/* Client to Server messages:  
	 * Commands made by the players will send strings to the server and with those strings
	 * the server will call the games engine to return a response */
	
	/* STARTING THE TOURNAMENT */
	public String DRAW_TOKEN = "t";
	public String DEAL = "d";
	
	/* STARTING A TOURNAMENT */
	// TURN returns true when it is a player's turn
	public boolean TURN = false;
	// PLAYED returns true when a player has played their cards
	public boolean PLAYED = false;
	
	/* PLAYING IN A TOURNAMENT */
	public String DRAW_CARD = "c";
	public String PLAY_CARD = "p";
	public boolean WITHDRAW = false; 
	
}
