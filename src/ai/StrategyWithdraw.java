package ai;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import config.Config;
import game.Card;

public class StrategyWithdraw implements Strategy{
	/* This strategy will withdraw no matter what */
	
	private Logger log = Logger.getLogger("AI");
	private ArrayList<Card> hand = new ArrayList<Card>();
	private String output = "result";
	private boolean started = false;
	private boolean currentPlayer = false;
	private String name;
	
	public void setStarted(boolean b){started = b;}
	public boolean getStarted(){return started;}
	public void setName(String n){this.name = n;}
	
	public StrategyWithdraw(){
		log.info("New AI of type 'Withdraw' has been created");
	}

	public String startTournament() {
		for(int i = 0; i < hand.size(); i++){
			if(hand.get(i).getValue() == 0 || hand.get(i).getType().equals(Config.SQUIRE) || 
					hand.get(i).getType().equals(Config.MAIDEN)){
				continue;
			}else{
				output = hand.get(i).getType();
				break;
			}
		}
		return output;
	}

	public String playACard() {
		if(started){
			for(int i = 0; i < hand.size(); i++){
				if(hand.get(i).getValue() == 0 || hand.get(i).getType().equals(Config.SQUIRE) || 
						hand.get(i).getType().equals(Config.MAIDEN)){
					continue;
				}else{
					output = Config.PLAY + " " +  hand.get(i).getType() + " " + hand.get(i).getValue();
					break;
				}
			}
		}else{
			output = Config.WITHDRAW;
		}
		return output;
	}

	public String continueWithdraw() {
		return "result";
	}

	public void getHand(ArrayList<Card> c) {
		hand = c;
	}
	
	/* Handles what the server has sent from the Game Engine and processes
	 * what the AI will send back to the server */
	public String processInput(String msg){
		log.info("AI has received: " + msg);
		
		System.out.println("STarted: " + started);

		/* Determines who's turn it is */
		 if (msg.contains(Config.TURN)){
			output = processPlayerTurn(msg);
		}
		
		/* Notifies all players when a card has been played */
		else if (msg.contains(Config.COLOUR) && started){
			System.out.println("HERE");
			output = processPlay(msg);
		}
		
		/* When a player has ended their turn */
		else if(msg.contains(Config.CONTINUE)||msg.contains(Config.WITHDRAW)){

			if(msg.length() == 9){
				output = Config.WITHDRAW;
			}else {
				output = processContinueWithdraw(msg);	
			}
		}
		
		/* When you are not the current player, you are notified when another player
		 * plays a card*/
		else if(msg.contains(Config.WAITING)){
			if(currentPlayer){
				output = Config.END_TURN;
				this.currentPlayer = false;
				this.setStarted(false);
			}else{
				output = "result";
			}
		}
		return output;
	}
	
	public void processPlayerName(String msg) {
		System.out.println("HAND");
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
		getHand(hand);
		//return output; 
	}
	
	public String processPlayerTurn(String msg) {
		System.out.println("PLAYERTURN");
		String input[] = msg.split(" ");
		
		if(msg.contains(Config.PICKED_PURPLE)){
			if(input[3].equalsIgnoreCase(this.name)){
				String colour = startTournament();
				output = Config.COLOUR_PICKED + " " + colour;
				this.currentPlayer = true; 
				this.setStarted(true);
			}	
		}else{
			if(input[1].equalsIgnoreCase(this.name)){
				this.currentPlayer = true; 
				String colour = startTournament();
				output = Config.COLOUR_PICKED + " " + colour;
			}else{
				this.currentPlayer = false; 
			}
		}
		return output;
	}

	public String processPlay(String msg) {
		System.out.println("PLAY");
		output = playACard();
		return output;
	}

	
	public String processContinueWithdraw(String msg) {
		System.out.println("CONTINUEWITHDRAW");
		
		String[] input = msg.split(" ");
		
		if(!msg.contains(Config.TOURNAMENT_WINNER)){
			if(input[4].equals(this.name)){
				this.currentPlayer = true;
				output = playACard();
			}else{
				this.currentPlayer = false;
				output = "result";
			}
		}else{
			output = "result";
		}
		return output;
	}
}
