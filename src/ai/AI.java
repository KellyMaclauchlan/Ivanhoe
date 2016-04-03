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
		System.out.println("AI of type: " + strategy);
		this.name = name;
	}
	
	public void processInput(String msg){
		if(msg.contains(Config.HAND)){
			this.strategy.processPlayerName(msg);
		}
		
		else if(msg.contains("input") || msg.contains(Config.GAME_WINNER)){
			// do nothing 
			
		}else{
			output = this.strategy.processInput(msg);
			giveTokens2Strategy();
			notifyObservers(output);
		}
	}
	
	public void giveTokens2Strategy(){
		this.strategy.tokenChoice(currentTokens);
	}

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
