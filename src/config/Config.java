package config;

import java.util.ArrayList;
import java.util.Arrays;

public class Config {
	public static int DEFAULT_PORT = 3000; 
	public static String DEFAULT_HOST = "127.0.0.1";
	
	public static int MAX_PLAYERS = 5; 
	
	/* Network Messages */
	public static String RUN = "run";
	public static String SHUTDOWN = "shutdown";
	
	/* Client to Server messages:  
	 * Commands made by the players will send strings to the server and with those strings
	 * the server will call the games engine to return a response */
	public static String CLIENT_START = "client";
	public static String START = "start";
	public static String PROMPT_JOIN = "prompt join";
	public static String JOIN = "join";
	public static String PLAYER_NAME = "name";
	public static String PLAYER_CARDS = "cards";
	public static String NEED_PLAYERS = "need players";
	public static String START_TOURNAMENT = "begin tournament";
	public static String PLAY = "play"; 
	public static String END_TURN = "end";
	public static String POINTS = "points";
	public static String TOURNAMENT_WINNER = "tournament winner";
	public static String GAME_WINNER = "game winner";
	public static String PICK_COLOUR = "pick a colour";
	public static String COLOUR_PICKED = "colour";
	public static String PICKED_PURPLE = "pickedpurple";
	public static String TURN = "turn";
	public static String CONTINUE = "continue";
	public static String MAX = "maximum " + MAX_PLAYERS;
	public static String UNPLAYABLE = "unplayable";
	public static String FIRSTPLAYER = "firstplayer";
	public static String WAITING = "waiting";
	public static String PURPLE_WIN = "purple win";
	public static String WITHDRAW = "withdraw";
	public static String HAND = "hand";
	public static String DISPLAY = "display"; 
	public static String FROMUPDATE = "update";

	/*UI messages*/
	public static String LEFT_CLICK="leftclick";
	public static String RIGHT_CLICK="rightclick";
	public static String WITHDRAW_CLICK="withdrawclick";
	public static String END_TURN_CLICK="endturnclick";
	public static String PLAYEDCARD="playedcard";
	public static String VIEWDISPLAY="viewdisplay";
	
	
	/* Card names */
	//colours
	public static String COLOUR = "colour";
	/* Coloured Cards */

	public static String PURPLE = "purple";
	public static String RED = "red";
	public static String BLUE = "blue";
	public static String YELLOW = "yellow";
	public static String GREEN = "green";
	
	/* Arrary of colours */
	public static ArrayList<String> colours = new ArrayList<String>(Arrays.asList(BLUE, RED, YELLOW, GREEN, PURPLE));
	

	/* Supporters */
	public static String SUPPORT = "support";
	public static String MAIDEN = "maiden";
	public static String SQUIRE = "squire";

	/* Action */
	public static String ACTION = "action";
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
	
	public static String IMG_DODGE = "resources/cards_small/actionCards.jpg";
	public static String IMG_DISGRACE = "resources/cards_small/actionCards1.jpg";
	public static String IMG_RETREAT = "resources/cards_small/actionCards2.jpg";
	public static String IMG_RIPOSTE = "resources/cards_small/actionCards3.jpg";
	public static String IMG_OUTMANEUVER = "resources/cards_small/actionCards4.jpg";
	public static String IMG_COUNTER_CHARGE = "resources/cards_small/actionCards5.jpg";
	public static String IMG_CHARGE = "resources/cards_small/actionCards6.jpg";
	public static String IMG_BREAK_LANCE = "resources/cards_small/actionCards7.jpg";
	public static String IMG_ADAPT = "resources/cards_small/actionCards8.jpg";
	public static String IMG_DROP_WEAPON = "resources/cards_small/actionCards9.jpg";
	public static String IMG_CHANGE_WEAPON = "resources/cards_small/actionCards10.jpg";
	public static String IMG_UNHORSE = "resources/cards_small/actionCards11.jpg";
	public static String IMG_KNOCK_DOWN = "resources/cards_small/actionCards12.jpg";
	public static String IMG_OUTWIT = "resources/cards_small/actionCards13.jpg";
	public static String IMG_SHIELD = "resources/cards_small/actionCards14.jpg";
	public static String IMG_STUNNED = "resources/cards_small/actionCards15.jpg";
	public static String IMG_IVANHOE = "resources/cards_small/actionCards16.jpg";
	
	public static String IMG_BACK = "resources/cards_small/simpleCards18.jpg";
}
