package ai;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import config.Config;
import game.Card;
import game.Player;
import network.Server;

public class AI extends Player{
	private Strategy strategy;
	private int id;
	private Server server;
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	private Logger log = Logger.getLogger("AI");
	private boolean currentPlayer;
	
	private String output = "result";
	
	public AI(Strategy s){
		this.strategy = s;
		this.id = 0;
	}
	
	/* Handles what the server has sent from the Game Engine and processes
	 * what the AI will send back to the server */
	public void processInput(String msg){
		log.info("AI has received: " + msg);
		
		/* Receives each player and their hand
		 * Input:  hand name <player1> card [player1's card] name <player2> cards [player2's card] ... 
		 * Output: begin tournament 
		 * */
		if (msg.contains(Config.HAND)){
			output = processPlayerName(msg);
		}
		
		/* It is the start currentPlayer's turn 
		 * Input: 
		 * 	First tournament purple <player that picked purple> turn <1st player> <1st player's card> picked
		 * 	Player's turn: turn <name> <card picked> picked 
		 * Output: 
		 * 	Start of new tournament: colour <colour picked>
		 * */
		else if (msg.contains(Config.TURN) && !msg.contains(Config.LOGGING)){
			output = processPlayerTurn(msg);
		}
		
		/* When the player has chosen which card(s) they wish to play
		 * Input: play <colour of tournament>
		 * Output: play <card type> <card value> 
		 * */
		else if (msg.contains(Config.PLAY) ){
			output = processPlay(msg);
		}
		
		/* When the currentPlayer has finished playing their turn and does not withdraw
		 * Input: <currentPlayer's name> points <# of points> continue/withdraw <next player>
		 * or :IF tournament is won, add: tournament winner <winner name>
		 * OR IF tournament is won and tournamentColour is purple, add: purple win <winner name> 
		 * IF game is won, add: game winner <winner name>
		 * Output:
		 * 	Tournament Winner: begin tournament 
		 * 	Game Winner: Nothing (game winner popup) 
		 * 	Next Player's turn: currentPlayer has switched to the next player 
		 * */
		else if(msg.contains(Config.CONTINUE)||msg.contains(Config.WITHDRAW)){

			if(msg.length() == 9){
				output = Config.WITHDRAW;
			}else {
				output = processContinueWithdraw(msg);	
			}
		}
		
		else if(msg.contains(Config.END_TURN)){
			output = Config.END_TURN;
		}


		server.handle(id, output);
	}
	
	public String processPlayerName(String msg) {
		msg = msg.substring(10);
		String name[] = msg.split("name");
		String card[];
		String value[];

		for(int i = 0; i < name.length; i++){
			card = name[i].split(" ");
			
			//if this player is the user
			if(card[1].equalsIgnoreCase(this.name)){
				for(int k = 3; k < card.length; k++){
					value = card[k].split("_");
					
					Card newCard = new Card();
					newCard.setType(value[0]);
					newCard.setValue(Integer.parseInt(value[1]));
					hand.add(newCard);					
				}
			}
		}
		strategy.getHand(hand);
		return Config.START_TOURNAMENT;
	}
	
	public String processPlayerTurn(String msg) {
		String input[] = msg.split(" ");

		// if it is the first tournament 
		if(msg.contains(Config.PICKED_PURPLE)){
			
				if(input[3].equalsIgnoreCase(this.name)){
					String value[] = input[4].split("_");
					
					String colour = strategy.startTournament();
					output = Config.COLOUR_PICKED + " " + colour;
					this.currentPlayer = true; 
				}
				
		}else{
			if(input[1].equalsIgnoreCase(this.name)){
				String value[] = input[2].split("_");
				
				String colour = strategy.startTournament();
				output = Config.COLOUR_PICKED + " " + colour;
			}
		}
		return output;
	}

	public String processPlay(String msg) {
		strategy.playACard();
		return output;
	}

	
	public String processContinueWithdraw(String msg) {
		//return output;
		return strategy.continueWithdraw();
	}	
}
