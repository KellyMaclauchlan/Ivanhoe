package config;

import java.util.ArrayList;
import java.util.Arrays;

public class Config {
	public static final int DEFAULT_PORT = 3000; 
	public static final String DEFAULT_HOST = "127.0.0.1";
	
	public static final int MAX_PLAYERS = 5; 
	
	/* Network Messages */
	public static final String RUN = "run";
	public static final String SHUTDOWN = "shutdown";
	public static final String QUIT = "quit";
	
	/* Client to Server messages:  
	 * Commands made by the players will send strings to the server and with those strings
	 * the server will call the games engine to return a response */
	public static final String CLIENT_START = "client";
	public static final String START = "start";
	public static final String PROMPT_JOIN = "prompt join";
	public static final String JOIN = "join";
	public static final String PLAYER_NAME = "name";
	public static final String PLAYER_CARDS = "cards";
	public static final String NEED_PLAYERS = "need players";
	public static final String START_TOURNAMENT = "begin tournament";
	public static final String PLAY = "play"; 
	public static final String END_TURN = "end";
	public static final String POINTS = "points";
	public static final String TOURNAMENT_WINNER = "winner";
	public static final String GAME_WINNER = "game winner";
	public static final String PICK_COLOUR = "pick a colour";
	public static final String COLOUR_PICKED = "colour";
	public static final String PICKED_PURPLE = "pickedpurple";
	public static final String TURN = "turn";
	public static final String COLOUR = "colour";
	public static final String CONTINUE = "continue";
	public static final String MAX = "maximum " + MAX_PLAYERS;
	public static final String UNPLAYABLE = "NOPE";
	public static final String FIRSTPLAYER = "firstplayer";
	public static final String WAITING = "waiting";
	public static final String PURPLE_WIN = "win purple";
	public static final String WITHDRAW = "withdraw";
	public static final String HAND = "hand";
	public static final String DISPLAY = "display"; 
	public static final String FROMUPDATE = "update";
	public static final String NOT_ENOUGH = "nobuenos";
	public static final String DUPLICATE = "duplicate";
	public static final String NAME_APPROVED = "approve";

	/*UI messages*/
	public static final String LEFT_CLICK = "leftclick";
	public static final String RIGHT_CLICK = "rightclick";
	public static final String WITHDRAW_CLICK = "withdrawclick";
	public static final String END_TURN_CLICK = "endturnclick";
	public static final String PLAYEDCARD = "playedcard";
	public static final String VIEWDISPLAY = "viewdisplay";
	
	
	/* Card names */
	/* Coloured Cards */
	public static final String PURPLE = "purple";
	public static final String RED = "red";
	public static final String BLUE = "blue";
	public static final String YELLOW = "yellow";
	public static final String GREEN = "green";
	
	/* Arrary of colours */
	public static final ArrayList<String> colours = new ArrayList<String>(Arrays.asList(BLUE, RED, YELLOW, GREEN, PURPLE));
	

	/* Supporters */
	public static final String SUPPORT = "support";
	public static final String MAIDEN = "maiden";
	public static final String SQUIRE = "squire";

	/* Action */
	public static final String ACTION = "action";
	public static final String UNHORSE = "unhorse";
	public static final String CHANGEWEAPON = "changeweapon";
	public static final String DROPWEAPON = "dropweapon";
	public static final String BREAKLANCE = "breaklance";
	public static final String RIPOSTE = "riposte";
	public static final String DODGE = "dodge";
	public static final String RETREAT = "retreat";
	public static final String KNOCKDOWN = "knockdown";
	public static final String OUTMANEUVER = "outmaneuver";
	public static final String CHARGE = "charge";
	public static final String COUNTERCHARGE = "countercharge";
	public static final String DISGRACE = "disgrace";
	public static final String ADAPT = "adapt";
	public static final String OUTWIT = "outwit";
	public static final String SHIELD = "shield";
	public static final String STUNNED = "stunned";
	public static final String IVANHOE = "ivanhoe";
	
	/* Card resource strings */
	public static final String IMG_PURPLE_3 = "resources/cards_small/simpleCards14.jpg";
	public static final String IMG_PURPLE_4 = "resources/cards_small/simpleCards15.jpg";
	public static final String IMG_PURPLE_5 = "resources/cards_small/simpleCards16.jpg";
	public static final String IMG_PURPLE_7 = "resources/cards_small/simpleCards17.jpg";
	
	public static final String IMG_RED_3 = "resources/cards_small/simpleCards7.jpg";
	public static final String IMG_RED_4 = "resources/cards_small/simpleCards8.jpg";
	public static final String IMG_RED_5 = "resources/cards_small/simpleCards9.jpg";

	public static final String IMG_BLUE_2 = "resources/cards_small/simpleCards10.jpg";
	public static final String IMG_BLUE_3 = "resources/cards_small/simpleCards11.jpg";
	public static final String IMG_BLUE_4 = "resources/cards_small/simpleCards12.jpg";
	public static final String IMG_BLUE_5 = "resources/cards_small/simpleCards13.jpg";

	public static final String IMG_YELLOW_2 = "resources/cards_small/simpleCards4.jpg";
	public static final String IMG_YELLOW_3 = "resources/cards_small/simpleCards5.jpg";
	public static final String IMG_YELLOW_4 = "resources/cards_small/simpleCards6.jpg";
	
	public static final String IMG_GREEN_1 = "resources/cards_small/simpleCards3.jpg";
	
	public static final String IMG_MAIDEN_6 = "resources/cards_small/simpleCards2.jpg";
	public static final String IMG_SQUIRE_2 = "resources/cards_small/simpleCards.jpg";
	public static final String IMG_SQUIRE_3 = "resources/cards_small/simpleCards1.jpg";
	
	public static final String IMG_DODGE = "resources/cards_small/actionCards.jpg";
	public static final String IMG_DISGRACE = "resources/cards_small/actionCards1.jpg";
	public static final String IMG_RETREAT = "resources/cards_small/actionCards2.jpg";
	public static final String IMG_RIPOSTE = "resources/cards_small/actionCards3.jpg";
	public static final String IMG_OUTMANEUVER = "resources/cards_small/actionCards4.jpg";
	public static final String IMG_COUNTER_CHARGE = "resources/cards_small/actionCards5.jpg";
	public static final String IMG_CHARGE = "resources/cards_small/actionCards6.jpg";
	public static final String IMG_BREAK_LANCE = "resources/cards_small/actionCards7.jpg";
	public static final String IMG_ADAPT = "resources/cards_small/actionCards8.jpg";
	public static final String IMG_DROP_WEAPON = "resources/cards_small/actionCards9.jpg";
	public static final String IMG_CHANGE_WEAPON = "resources/cards_small/actionCards10.jpg";
	public static final String IMG_UNHORSE = "resources/cards_small/actionCards11.jpg";
	public static final String IMG_KNOCK_DOWN = "resources/cards_small/actionCards12.jpg";
	public static final String IMG_OUTWIT = "resources/cards_small/actionCards13.jpg";
	public static final String IMG_SHIELD = "resources/cards_small/actionCards14.jpg";
	public static final String IMG_STUNNED = "resources/cards_small/actionCards15.jpg";
	public static final String IMG_IVANHOE = "resources/cards_small/actionCards16.jpg";
	
	public static final String IMG_BACK = "resources/cards_small/simpleCards18.jpg";
}
