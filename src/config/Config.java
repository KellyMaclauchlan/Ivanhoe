package config;

import game.ActionCard;

public class Config {
	public static int DEFAULT_PORT = 3000; 
	public static int PORT; 
	public static String DEFAULT_HOST = "127.0.0.1";
	public static String HOST;
	
	public static int MAX_PLAYERS = 5; 
	
	/* Network Messages */
	public static String RUN = "run";
	public static String SHUTDOWN = "shutdown";
	
	/* Client to Server messages:  
	 * Commands made by the players will send strings to the server and with those strings
	 * the server will call the games engine to return a response */
	public static String START = "start";
	public static String PROMPT_JOIN = "Prompt join";
	public static String JOIN = "join";
	public static String NEED_PLAYERS = "need players";
	public static String START_TURN = "start turn";
	public static String PLAY = "play"; 
	public static String END_TURN = "end turn";
	public static String POINTS = "points";
	public static String TOURNAMENT_WINNER = "tournament winner";
	public static String PICK_COLOUR = "pick a colour";
	public static String COLOUR_PICKED = "colour picked";
	
	
	
	/* STARTING THE TOURNAMENT */
	public static String DRAW_TOKEN = "t";
	public static String DEAL = "d";
	
	/* STARTING A TOURNAMENT */
	// TURN returns true when it is a player's turn
	public static boolean TURN = false;
	// PLAYED returns true when a player has played their cards
	public static boolean PLAYED = false;
	
	/* PLAYING IN A TOURNAMENT */
	public static String DRAW_CARD = "c";
	public static String PLAY_CARD = "p";
	public static boolean WITHDRAW = false; 
	
	/*UI messages*/
	public static String LEFT_CLICK="leftclick";
	public static String RIGHT_CLICK="rightclick";
	public static String WITHDRAW_CLICK="withdrawclick";
	public static String END_TURN_CLICK="endturnclick";
	public static String PLAYEDCARD="playedcard";
	public static String VIEWDISPLAY="viewdisplay";
	
	/* Card names */
	//colours
	public static String PURPLE = "purple";
	public static String RED = "red";
	public static String BLUE = "blue";
	public static String YELLOW = "yellow";
	public static String GREEN = "green";

	//supporters
	public static String MAIDEN = "maiden";
	public static String SQUIRE = "squire";

	//action
	public static String UNHORSE = "unhorse";
	public static String CHANGEWEAPON = "changeweapon";
	public static String DROPWEAPON = "dropweapon";
	public static String BREAKLANCE = "breaklance";
	public static String RIPOSTE = "riposte";
	public static String DODGE = "dodge";
	public static String RETREAT = "retreat";
	public static String KNOCKDOWN = "knockdown";
	public static String OUTMANEUVER = "outmaneuver";
	public static String CHARGE = "charge";
	public static String COUNTERCHARGE = "countercharge";
	public static String DISGRACE = "disgrace";
	public static String ADAPT = "adapt";
	public static String OUTWIT = "outwit";
	public static String SHIELD = "shield";
	public static String STUNNED = "stunned";
	public static String IVANHOE = "ivanhoe";
	
	/* Card resource strings */
	public static String IMG_PURPLE_3 = "resources/cards_small/simpleCards14.jpg";
	public static String IMG_PURPLE_4 = "resources/cards_small/simpleCards15.jpg";
	public static String IMG_PURPLE_5 = "resources/cards_small/simpleCards16.jpg";
	public static String IMG_PURPLE_7 = "resources/cards_small/simpleCards17.jpg";
	
	public static String IMG_RED_3 = "resources/cards_small/simpleCards7.jpg";
	public static String IMG_RED_4 = "resources/cards_small/simpleCards8.jpg";
	public static String IMG_RED_5 = "resources/cards_small/simpleCards9.jpg";

	public static String IMG_BLUE_2 = "resources/cards_small/simpleCards10.jpg";
	public static String IMG_BLUE_3 = "resources/cards_small/simpleCards11.jpg";
	public static String IMG_BLUE_4 = "resources/cards_small/simpleCards12.jpg";
	public static String IMG_BLUE_5 = "resources/cards_small/simpleCards13.jpg";

	public static String IMG_YELLOW_2 = "resources/cards_small/simpleCards4.jpg";
	public static String IMG_YELLOW_3 = "resources/cards_small/simpleCards5.jpg";
	public static String IMG_YELLOW_4 = "resources/cards_small/simpleCards6.jpg";
	
	public static String IMG_GREEN_1 = "resources/cards_small/simpleCards3.jpg";
	
	public static String IMG_MAIDEN_6 = "resources/cards_small/simpleCards2.jpg";
	public static String IMG_SQUIRE_2 = "resources/cards_small/simpleCards.jpg";
	public static String IMG_SQUIRE_3 = "resources/cards_small/simpleCards1.jpg";

	
}
