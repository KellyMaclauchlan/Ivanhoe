package ai;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import config.Config;
import config.Observer;
import config.Subject;
import game.Card;
import game.Player;
import network.Server;

public class AI extends Player implements Subject{
	private Strategy strategy;
	private ArrayList<Card> hand = new ArrayList<Card>();
	
	private Logger log = Logger.getLogger("AI");
	private boolean currentPlayer;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	
	private String output = "result";
	
	public AI(Strategy s){
		this.strategy = s;
	}
	
	/* Handles what the server has sent from the Game Engine and processes
	 * what the AI will send back to the server */
	public void processInput(String msg){
		log.info("AI has received: " + msg);
		System.out.println("AI has received: " + msg);

		if (msg.contains(Config.HAND)){
			output = processPlayerName(msg);
		}

		else if (msg.contains(Config.TURN)){
			output = processPlayerTurn(msg);
			System.out.println("Current Player: " + currentPlayer);
			notifyObservers(output);
		}
		
		else if (msg.contains(Config.COLOUR) && strategy.getStarted()){
			output = processPlay(msg);
			System.out.println("Current Player: " + currentPlayer);
			notifyObservers(output);
		}
		
		else if(msg.contains(Config.CONTINUE)||msg.contains(Config.WITHDRAW)){

			if(msg.length() == 9){
				output = Config.WITHDRAW;
			}else {
				output = processContinueWithdraw(msg);	
			}
			System.out.println("Current Player: " + currentPlayer);
			notifyObservers(output);
		}
		
		else if(msg.contains(Config.WAITING)){
			System.out.println("Current Player: " + currentPlayer);
			if(currentPlayer){
				output = Config.END_TURN;
				this.currentPlayer = false;
			}else{
				output = "result";
			}
			
			notifyObservers(output);
		}
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
		return output; 
	}
	
	public String processPlayerTurn(String msg) {
		String input[] = msg.split(" ");
		
		System.out.println("PlayerTurn");
		
		if(msg.contains(Config.PICKED_PURPLE)){
			
				if(input[3].equalsIgnoreCase(this.name)){
					String colour = strategy.startTournament();
					output = Config.COLOUR_PICKED + " " + colour;
					this.currentPlayer = true; 
					this.strategy.setStarted(true);
				}
				
		}else{
			if(input[1].equalsIgnoreCase(this.name)){
				this.currentPlayer = true; 
				String colour = strategy.startTournament();
				output = Config.COLOUR_PICKED + " " + colour;
			}
		}
		return output;
	}

	public String processPlay(String msg) {
		System.out.println("PlayCard");
		output = strategy.playACard();
		return output;
	}

	
	public String processContinueWithdraw(String msg) {
		System.out.println("ContinueWithdraw");
		
		String[] input = msg.split(" ");
		
		if(!msg.contains(Config.TOURNAMENT_WINNER)){
			if(input[4].equals(this.getName())){
				this.currentPlayer = true;
				output = strategy.playACard();
			}else{
				this.currentPlayer = false;
				output = "result";
			}
		}else{
			output = "result";
		}
		return output;
	}

	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	public void notifyObservers(String message) {
		Observer ob = observers.get(0);
		ob.update(message);
	}
}
