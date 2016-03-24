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
	
	
	public StrategyWithdraw(){
		System.out.println("Withdraw");
		log.info("New AI of type 'Withdraw' has been created");
	}

	public String startTournament() {
		
		for(int i = 0; i < hand.size(); i++){
			if(hand.get(i).getType() == Config.ACTION){
				break;
			}else{
				output = hand.get(i).getType();
			}
		}
		return output;
	}

	public String playACard() {
		return Config.WITHDRAW;
	}

	public String continueWithdraw() {
		return Config.WITHDRAW;
		
	}

	public void getHand(ArrayList<Card> c) {
		hand = c;
	}

}
