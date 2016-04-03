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
	
	public Strategy getStrategy(){return strategy;}
	
	public AI(Strategy s, String name){
		this.strategy = s;
		System.out.println("AI of type: " + strategy);
		this.name = name;
	}
	
	public void processInput(String msg){
		if(msg.contains(Config.HAND)){
			strategy.processPlayerName(msg);
		}
		
		else if(msg.contains("input") || msg.contains(Config.GAME_WINNER)){
			// do nothing 
			
		}else{
			output = strategy.processInput(msg);
			giveTokens2Strategy();
			notifyObservers(output);
		}
	}
	
	public void giveTokens2Strategy(){
		strategy.tokenChoice(currentTokens);
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
