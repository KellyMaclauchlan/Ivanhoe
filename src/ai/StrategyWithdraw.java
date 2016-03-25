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
	
	public void setStarted(boolean b){started = b;}
	public boolean getStarted(){return started;}
	
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
			System.out.println("First");
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
			System.out.println("Second");
			output = Config.WITHDRAW;
		}
		return output;
	}

	public String continueWithdraw() {
		//System.out.println("Continue/Withdraw");
		//return Config.WITHDRAW;
		return "result";
	}

	public void getHand(ArrayList<Card> c) {
		hand = c;
	}

}
