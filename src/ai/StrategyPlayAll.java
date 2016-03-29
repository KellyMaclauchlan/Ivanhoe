package ai;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import config.Config;
import game.ActionCard;
import game.Card;
import game.ColourCard;
import game.SupportCard;

public class StrategyPlayAll implements Strategy{
	/* This strategy plays all possible cards in a round even though it might
	 * not be the smartest strategy */
	
	private Logger log = Logger.getLogger("AI");
	private ArrayList<Card> hand = new ArrayList<Card>();
	private String output = Config.OUTPUT;
	private boolean started = false;
	private boolean currentPlayer = false;
	private String name;
	private String tournamentColour;
	private int currentPoints = 0;
	
	// number of each colour cards
	private int red = 0;
	private int blue = 0;
	private int purple = 0;
	private int yellow = 0;
	private int green = 0;
	private int supporter = 0;
	
	// what tokens the AI has
	private boolean redToken = false;
	private boolean blueToken = false;
	private boolean purpleToken = false;
	private boolean yellowToken = false;
	private boolean greenToken = false; 
	private boolean[] booleanTokens = {redToken, blueToken, purpleToken, yellowToken, greenToken};
	
	//ArrayList of each kind of card the AI has
	private ArrayList<Card> redCards = new ArrayList<Card>();
	private ArrayList<Card> blueCards = new ArrayList<Card>();
	private ArrayList<Card> purpleCards = new ArrayList<Card>();
	private ArrayList<Card> yellowCards = new ArrayList<Card>();
	private ArrayList<Card> greenCards = new ArrayList<Card>();
	private ArrayList<Card> supportCards = new ArrayList<Card>();
	private ArrayList<Card> actionCards = new ArrayList<Card>();
	
	private ArrayList<String> currentTokens = new ArrayList<String>();
	private String bestColourChoice = Config.GREEN;
	
	public void setStarted(boolean b){started = b;}
	public boolean getStarted(){return started;}
	
	public StrategyPlayAll(String n){
		log.info("New AI of type 'Play All' has been created");
		this.name = n;
	}

	public String startTournament() {
		if(red >= 2){
			output = Config.RED;
		}
		else if(blue >= 2){
			output = Config.BLUE;
		}
		else if(purple >= 2){
			output = Config.PURPLE;
		}
		else if(yellow >= 2){
			output = Config.YELLOW;
		}
		else if(green >= 2){
			output = Config.GREEN;
		}
		else{
			output = bestColourChoice; 
		}
		tournamentColour = output;
		return output;
	}
	
	// called every time the AI receives something from the server to update the tokens of the player
	// used because the strategy has no knowledge of the player class 
	public void tokenChoice(ArrayList<String> tokens){
		currentTokens = tokens; 
		int tokenIndex = 0;
		
		for(int i = 0; i < currentTokens.size(); i++){
			String t = currentTokens.get(i);
			if(t.equals(Config.RED)){
				redToken = true;
			}
			else if(t.equals(Config.BLUE)){
				blueToken = true;
			}
			else if(t.equals(Config.PURPLE)){
				purpleToken = true;
			}
			else if (t.equals(Config.YELLOW)){
				yellowToken = true;
			}
			else if(t.equals(Config.GREEN)){
				greenToken = true;
			}
		}
		
		for(boolean b : booleanTokens){
			if(!b){
				tokenIndex++;
				break;
			}
		}
		
		if(tokenIndex == 1){
			bestColourChoice = Config.RED;
		}
		else if(tokenIndex == 2){
			bestColourChoice = Config.BLUE;
		}
		else if(tokenIndex == 3){
			bestColourChoice = Config.PURPLE;
		}
		else if(tokenIndex == 4){
			bestColourChoice = Config.YELLOW;
		}
		else{
			bestColourChoice = Config.GREEN;
		}
	}

	public void addToken(){
		if(tournamentColour.equals(Config.RED)){
			redToken = true;
		}
		else if(tournamentColour.equals(Config.BLUE)){
			blueToken = true;
		}
		else if(tournamentColour.equals(Config.PURPLE)){
			if(purpleToken){
				int tokenIndex = 0;
				
				for(boolean b : booleanTokens){
					if(!b){
						tokenIndex++;
						break;
					}
				}
				
				if(tokenIndex == 1){
					redToken = true;
				}
				else if(tokenIndex == 2){
					blueToken = true;
				}
				else if(tokenIndex == 4){
					yellowToken = true;
				}
				else{
					greenToken = true;
				}
				
			}else{
				purpleToken = true;
			}
		}
		else if(tournamentColour.equals(Config.YELLOW)){
			yellowToken = true;
		}
		else if(tournamentColour.equals(Config.GREEN)){
			greenToken = true;
		}
	}
		
	public String playACard() {
		Card toPlay; 
		
		if(tournamentColour.equals(Config.RED)){
			if(redCards.size() != 0){
				toPlay = redCards.get(0);
				output = Config.PLAY + " " + toPlay.getType() + " " + toPlay.getValue(); 
				redCards.remove(0);
				red--;
			}else{
				red = 0;
				output = playSupporter();
			}
			
		}
		else if(tournamentColour.equals(Config.BLUE)){
			if(blueCards.size() != 0){
				toPlay = blueCards.get(0);
				output = Config.PLAY + " " + toPlay.getType() + " " + toPlay.getValue(); 
				blueCards.remove(0);
				blue--;
			}else{
				blue = 0;
				output = playSupporter();
			}
		}
		else if(tournamentColour.equals(Config.PURPLE)){
			if(purpleCards.size() != 0){
				toPlay = purpleCards.get(0);
				output = Config.PLAY + " " + toPlay.getType() + " " + toPlay.getValue(); 
				purpleCards.remove(0);
				purple--;
			}else{
				purple = 0;
				output = playSupporter();
			}
		}
		else if(tournamentColour.equals(Config.YELLOW)){
			if(yellowCards.size() != 0){
				toPlay = yellowCards.get(0);
				output = Config.PLAY + " " + toPlay.getType() + " " + toPlay.getValue(); 
				yellowCards.remove(0);
				yellow--;
			}else{
				yellow = 0;
				output = playSupporter();
			}
		}
		else if(tournamentColour.equals(Config.GREEN)){
			if(greenCards.size() != 0){
				toPlay = greenCards.get(0);
				output = Config.PLAY + " " + toPlay.getType() + " " + toPlay.getValue(); 
				greenCards.remove(0);
				green--;
			}else{
				green = 0;
				output = playSupporter();
			}
		}
		return output;
	}
	
	public String playSupporter(){
		if(supportCards.size() != 0){
			Card toPlay = supportCards.get(0);
			output = Config.PLAY + " " + toPlay.getType() + " " + toPlay.getValue(); 
			supportCards.remove(0);
			supporter--;
		}else{
			supporter = 0;
			output = Config.END_TURN;
		}
		return output;
	}

	public void getHand(ArrayList<Card> c) {
		hand = c;
		
		// counts how many of each colour there are 
		for(int i = 0; i < hand.size(); i++){
			Card nextCard = hand.get(i);
			if(nextCard.getType().equals(Config.RED)){
				red++;
				redCards.add(nextCard);
			}
			else if(nextCard.getType().equals(Config.BLUE)){
				blue++;
				blueCards.add(nextCard);
			}
			else if (nextCard.getType().equals(Config.PURPLE)){
				purple++;
				purpleCards.add(nextCard);
			}
			else if(nextCard.getType().equals(Config.YELLOW)){
				yellow++;
				yellowCards.add(nextCard);
			}
			else if(nextCard.getType().equals(Config.GREEN)){
				green++;
				greenCards.add(nextCard);
			}
			else if (nextCard.getType().equals(Config.SQUIRE) || nextCard.getType().equals(Config.MAIDEN)){
				supporter++;
				supportCards.add(nextCard);
			}
			else{
				actionCards.add(nextCard);
			}
		}
		
		// reset the hand variable because we no longer need it 
		hand.clear();
	}
	
	public void addNewCard(String card){
		String[] input = card.split("_");
		String type = input[0];
		int value = Integer.parseInt(input[1]);
		Card newCard;

		if(type.equals(Config.RED)){
			newCard = new ColourCard(type, value);
			red++;
			redCards.add(newCard);
		}
		else if(type.equals(Config.BLUE)){
			newCard = new ColourCard(type, value);
			blue++;
			blueCards.add(newCard);
		}
		else if(type.equals(Config.PURPLE)){
			newCard = new ColourCard(type, value);
			purple++;
			purpleCards.add(newCard);
		}
		else if(type.equals(Config.YELLOW)){
			newCard = new ColourCard(type, value);
			yellow++;
			yellowCards.add(newCard);
		}
		else if(type.equals(Config.GREEN)){
			newCard = new ColourCard(type, value);
			green++;
			greenCards.add(newCard);
		}
		else if(type.equals(Config.SQUIRE) || type.equals(Config.MAIDEN)){
			newCard = new SupportCard(type, value);
			supporter++;
			supportCards.add(newCard);
		}
		else{
			newCard = new ActionCard(type);
			actionCards.add(newCard);
		}
	}
	
	
	public String processInput(String msg){
		log.info("AI has received: " + msg);

		/* Determines who's turn it is */
		 if (msg.contains(Config.TURN)){
			output = processPlayerTurn(msg);
		}
		
		/* Notifies all players when a card has been played */
		else if (msg.contains(Config.COLOUR)){
			output = processPlay(msg);
		}
		
		/* When a player has ended their turn */
		else if(msg.contains(Config.CONTINUE)||msg.contains(Config.WITHDRAW)){
			output = processContinueWithdraw(msg);
		}
		
		/* When you are not the current player, you are notified when another player
		 * plays a card*/
		else if(msg.contains(Config.WAITING)){
			if(msg.startsWith(Config.WAITING) && currentPlayer){
				output = playACard();
			}else{
				output = Config.OUTPUT;
			}
		}
		return output;
	}
	
	public void processPlayerName(String msg){
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
	}
	
	public String processPlayerTurn(String msg){
		String input[]  = msg.split(" ");
		
		// first tournament
		if(msg.contains(Config.PICKED_PURPLE)){
			if(input[3].equalsIgnoreCase(this.name)){
				String colour = startTournament();
				output = Config.COLOUR_PICKED + " " + colour;
				this.currentPlayer = true; 
				addNewCard(input[4]);
				this.setStarted(true);
			}	
			
		// every other tournament 
		}else{
			if(input[1].equalsIgnoreCase(this.name)){
				this.currentPlayer = true; 
				addNewCard(input[2]);
				String colour = startTournament();
				output = Config.COLOUR_PICKED + " " + colour;
			}else{
				this.currentPlayer = false; 
			}
		}
		return output;
	}
	
	public String processPlay(String msg){
		String input[] = msg.split(" ");
		tournamentColour = input[1];
		
		if(currentPlayer){
			output = playACard();
		}
		return output;
	}
	
	public String processContinueWithdraw(String msg){
		String[] input = msg.split(" ");
		
		if(msg.contains(Config.PURPLE_WIN)){
			addToken();
			resetVariables();
			output = Config.PURPLE_WIN + " " + Config.PURPLE;
		}else{
			
			// ended the AI's turn
			if(input[0].equals(this.name)){
				this.currentPlayer = false;
				output = Config.OUTPUT;
				currentPoints = Integer.parseInt(input[2]);
				
			// ended the other player's turn	
			}else if(input[4].equals(this.name)){
				this.currentPlayer = true;
				
				if(msg.contains(Config.TOURNAMENT_WINNER)){
					addToken();
					resetVariables();
					output = Config.OUTPUT;
					
				}else if(msg.contains(Config.GAME_WINNER)){
					resetVariables();
					output = Config.OUTPUT;
				}else{
					
					int otherPlayer = Integer.parseInt(input[2]);
					if(otherPlayer > currentPoints && started){
						output = Config.WITHDRAW;
					}else{
						addNewCard(input[5]); 
						output = playACard();	
					}
				}
			}
		}
		return output;
	}
	
	public void resetVariables(){
		started = false;
		currentPlayer = false;
	}
}
