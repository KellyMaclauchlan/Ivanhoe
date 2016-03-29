package ai;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import config.Config;
import game.Card;

public class StrategySmartish implements Strategy{
	/* This strategy is intended to challenge the players to the best of its ability */
	
	private Logger log = Logger.getLogger("AI");
	private ArrayList<Card> hand = new ArrayList<Card>();
	private String output = "result";
	private boolean started = false;
	private boolean currentPlayer = false;
	private String name;
	
	public void setStarted(boolean b){started = b;}
	public boolean getStarted(){return started;}
	
	public StrategySmartish(String n){
		log.info("New AI of type 'Smartish' has been created");
		this.name = n;
	}
	
	public String startTournament(){
		return null;
	}

	public String playACard() {
		return output;
	}
	

	public void getHand(ArrayList<Card> c) {
		hand = c;
	}
	
	public void tokenChoice(ArrayList<String> tokens) {
		
	}
	
	public String processInput(String msg){
		return output; 
	}
	
	public void processPlayerName(String msg){

	}
	
	public String processPlayerTurn(String msg){
		return output;
	}
	
	public String processPlay(String msg){
		return output;
	}
	
	public String processContinueWithdraw(String msg){
		return output;
	}

}
