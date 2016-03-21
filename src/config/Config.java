package config;

import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ImageIcon;

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
	
	/* Client's joining a game */
	public static final String CLIENT_START = "client";
	public static final String START = "start";
	public static final String PROMPT_JOIN = "prompt join";
	public static final String JOIN = "join";
	public static final String NEED_PLAYERS = "need players";
	public static final String NOT_ENOUGH = "nobuenos";
	public static final String DUPLICATE = "duplicate";
	public static final String NAME_APPROVED = "approve";
	public static final String MAX = "maximum " + MAX_PLAYERS;
	public static final String FIRSTPLAYER = "firstplayer";
	public static final String WAITING = "waiting";
	
	/* Starting a tournament */
	public static final String START_TOURNAMENT = "begin tournament";
	public static final String PICK_COLOUR = "pick a colour";
	public static final String COLOUR_PICKED = "colour";
	public static final String PICKED_PURPLE = "pickedpurple";
	

	/* Starting a new tournament */
	public static final String PLAYER_NAME = "name";
	public static final String PLAYER_CARDS = "cards";
	public static final String COLOUR = "colour";
	
	
	/* Playing a round */
	public static final String PLAY = "play"; 
	public static final String UNPLAYABLE = "NOPE";
	public static final String HAND = "hand";
	public static final String TURN = "turn";
	
	/* End of a round */
	public static final String END_TURN = "end";
	public static final String POINTS = "points";
	public static final String CONTINUE = "continue";
	public static final String WITHDRAW = "withdraw";
	
	
	/* End of a tournament/game */
	public static final String TOURNAMENT_WINNER = "winner";
	public static final String GAME_WINNER = "game winner";
	public static final String PURPLE_WIN = "win purple";
	
	/* Observer */
	public static final String DISPLAY = "display"; 
	public static final String FROMUPDATE = "update";


	/* UI messages */
	public static final String LEFT_CLICK = "leftclick";
	public static final String RIGHT_CLICK = "rightclick";
	public static final String WITHDRAW_CLICK = "withdrawclick";
	public static final String END_TURN_CLICK = "endturnclick";
	public static final String PLAYEDCARD = "playedcard";
	public static final String VIEWDISPLAY = "viewdisplay";
	public static final String DESCRIPTION = "description";
	
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
	public static final String IMG_PURPLE_3 = "cards_small/simpleCards14.jpg";
	public static final String IMG_PURPLE_4 = "cards_small/simpleCards15.jpg";
	public static final String IMG_PURPLE_5 = "cards_small/simpleCards16.jpg";
	public static final String IMG_PURPLE_7 = "cards_small/simpleCards17.jpg";
	
	public static final String IMG_RED_3 = "cards_small/simpleCards7.jpg";
	public static final String IMG_RED_4 = "cards_small/simpleCards8.jpg";
	public static final String IMG_RED_5 = "cards_small/simpleCards9.jpg";

	public static final String IMG_BLUE_2 = "cards_small/simpleCards10.jpg";
	public static final String IMG_BLUE_3 = "cards_small/simpleCards11.jpg";
	public static final String IMG_BLUE_4 = "cards_small/simpleCards12.jpg";
	public static final String IMG_BLUE_5 = "cards_small/simpleCards13.jpg";

	public static final String IMG_YELLOW_2 = "cards_small/simpleCards4.jpg";
	public static final String IMG_YELLOW_3 = "cards_small/simpleCards5.jpg";
	public static final String IMG_YELLOW_4 = "cards_small/simpleCards6.jpg";
	
	public static final String IMG_GREEN_1 = "cards_small/simpleCards3.jpg";
	
	public static final String IMG_MAIDEN_6 = "cards_small/simpleCards2.jpg";
	public static final String IMG_SQUIRE_2 = "cards_small/simpleCards.jpg";
	public static final String IMG_SQUIRE_3 = "cards_small/simpleCards1.jpg";
	
	public static final String IMG_DODGE = "cards_small/actionCards.jpg";
	public static final String IMG_DISGRACE = "cards_small/actionCards1.jpg";
	public static final String IMG_RETREAT = "cards_small/actionCards2.jpg";
	public static final String IMG_RIPOSTE = "cards_small/actionCards3.jpg";
	public static final String IMG_OUTMANEUVER = "cards_small/actionCards4.jpg";
	public static final String IMG_COUNTER_CHARGE = "cards_small/actionCards5.jpg";
	public static final String IMG_CHARGE = "cards_small/actionCards6.jpg";
	public static final String IMG_BREAK_LANCE = "cards_small/actionCards7.jpg";
	public static final String IMG_ADAPT = "cards_small/actionCards8.jpg";
	public static final String IMG_DROP_WEAPON = "cards_small/actionCards9.jpg";
	public static final String IMG_CHANGE_WEAPON = "cards_small/actionCards10.jpg";
	public static final String IMG_UNHORSE = "cards_small/actionCards11.jpg";
	public static final String IMG_KNOCK_DOWN = "cards_small/actionCards12.jpg";
	public static final String IMG_OUTWIT = "cards_small/actionCards13.jpg";
	public static final String IMG_SHIELD = "cards_small/actionCards14.jpg";
	public static final String IMG_STUNNED = "cards_small/actionCards15.jpg";
	public static final String IMG_IVANHOE = "cards_small/actionCards16.jpg";
	
	public static final String IMG_BACK = "cards_small/simpleCards18.jpg";
	
	//token images 
	public static final ArrayList<String> tokenStrings = new ArrayList<String>(Arrays.asList("icons/blue_full.png", 
			"icons/red_full.png", "icons/yellow_full.png", "icons/green_full.png", 
			"icons/purple_full.png"));
	
	public static final String BLUE_EMPTY = "icons/blue_empty.png";
	public static final String RED_EMPTY = "icons/red_empty.png";
	public static final String YELLOW_EMPTY = "icons/yellow_empty.png";
	public static final String GREEN_EMPTY = "icons/green_empty.png";
	public static final String PURPLE_EMPTY = "icons/purple_empty.png";
	
	//arrows
	public static final String ARROW_LEFT = "icons/left-arrow.png";
	public static final String ARROW_RIGHT = "icons/right-arrow.png";
	
	//sheild and stun
	public static final String SHIELD_IMG = "icons/sheild.png";
	public static final String STUN_IMG = "icons/stun.png";
	

	public static final ArrayList<String> emptyTokenStrings = new ArrayList<String>(Arrays.asList("icons/blue_empty.png",
			"icons/red_empty.png", "icons/yellow_empty.png", "icons/green_empty.png", 
			"icons/purple_empty.png"));
	
	//strings for description
	
	public static final ArrayList<String> infoStrings= new ArrayList<String>(Arrays.asList(
			"Yellow: 2","Yellow: 3", "Yellow: 4",
			"Blue: 2", "Blue: 3", "Blue: 4", "Blue: 5",
			"Red: 3", "Red: 4", "Red: 5",
			"Purple: 3", "Purple: 4", "Purple: 5", "Purple: 7",
			"Green: 1",
			"Squire: 2", "Squire: 3", "Maiden: 6",
			"Unhorse: Change the tournament colour from purple to red blue or yellow",
			"Change Weapon: Change the colour from red blue or yellow to a different one of these colours",
			"Drop Weapon: Change the tournament colour from red blue or yellow to green",
			"Shield: Play this card face up in front of you but separate from your display. As long as you have the SHIELD in front of you, the action cards have no effect on your display", 
			"Stunned: You may play this card on any one opponent still in the tournament\n but separate from the opponents display. As long as a player is STUNNED he may only add one new card to his display each turn", 
			"Ivanhoe: You may play this card at anytime as long as you are in a tournament. Use the IVANHOE card to cancel all effects of any one action card just played",
			"Break lance: One opponent must discard all purple cards from their display",
			"Riposte: you may take the last card of any one opponent's display and add it to your own display", 
			"Dodge: You may discard any one card from any one opponent's display", 
			"Retreat: You may take any one card from your display back into your hand", 
			"Knock Down: You may draw at random one card from any one opponent's hand and add it to your own hand (Without revealing the card)", 
			"Outmaneuver: All your opponents must discard their last card from their displays", 
			"Charge: Identify the lowest value card throughout the displays. All players must discard all cards of this value from their displays",
			"Counter-Charge: Identify the highest value card throughout all displays. All players must discard all cards of this value from their displays",
			"Disgrace: All players must discard all their supporters from their displays", 
			"Adapt: Each player may only keep one card of each value in his display. All other cards with the same value are discarded. Each player decides which card he discards", 
			"Outwit: Place one of your face up cards infront of an opponent, take one face up card from your opponent and place it face up infant of yourself. This may include SHEILD and STUNNED cards"));

}
