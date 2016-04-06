package ai;

import java.util.ArrayList;

import config.Config;
import config.Observer;
import config.Subject;
import game.Player;

public class AI extends Player implements Subject{
	private Strategy strategy;
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private String output = Config.OUTPUT;
	
	public Strategy getStrategy(){return strategy;}
	
	public AI(Strategy s, String name){
		super(name);
		this.strategy = s;
		this.name = name;
		System.out.println("AI: " + strategy);
	}
	
	/* Receives messages from the Server just like a client does */
	public void processInput(String msg){

		System.out.println("AI received: " + msg);
		/* When the AI receives its cards */
		if(msg.contains(Config.HAND)){
			this.strategy.processPlayerName(msg);
		}
		
		else if(msg.contains("input") || msg.contains(Config.GAME_WINNER)){
			// do nothing 
			
		}else{
			// sends the message from the server to the appropriate strategy
			output = this.strategy.processInput(msg);
			giveTokens2Strategy();
			System.out.println("m: " + output);
			notifyObservers(output);
		}
	}
	
	/* Sends the player's list of tokens each time for strategy purposes */
	public void giveTokens2Strategy(){
		this.strategy.tokenChoice(currentTokens);
	}

	/* Observer Pattern to communicate with the Server */
	public void registerObserver(Observer observer) {
		this.observers.add(observer);
	}

	public void removeObserver(Observer observer) {
		this.observers.remove(observer);
	}

	public void notifyObservers(String message) {
		Observer ob = this.observers.get(0);
		ob.update(message);
	}
}
