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
	private int highestTotalValue = 0;
	
	// number of each coloured cards
	private int red = 0;
	private int blue = 0;
	private int purple = 0;
	private int yellow = 0;
	private int green = 0;
	private int supporter = 0;
	
	// checks for the maiden card played twice in a tournament
	private boolean playedMaiden = false; 
	
	// what tokens the AI has
	private ArrayList<String> currentTokens = new ArrayList<String>();
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
	
	// used to determine what choice of tournament colour to choose or the token needed 
	private String bestColourChoice = Config.GREEN;
	
	private Card toPlay;
	
	public void setStarted(boolean b){started = b;}
	public boolean getStarted(){return started;}
	
	public void setToPlay(Card c){toPlay = c;}
	public Card getToPlay(){return toPlay;}
	
	public void setCurrentPlayer(boolean p){currentPlayer = p;}
	public boolean getCurrentPlayer(){return currentPlayer;}

	public String getName(){return name;}
	
	public StrategyPlayAll(String n){
		log.info("New AI of type 'Play All' has been created");
		this.name = n;
	}

	/* Chooses the tournament color if the AI gets the choice */
	public String startTournament() {
		if(this.red >= 2){
			output = Config.RED;
		}
		else if(this.blue >= 2){
			output = Config.BLUE;
		}
		else if(this.purple >= 2){
			output = Config.PURPLE;
		}
		else if(this.yellow >= 2){
			output = Config.YELLOW;
		}
		else if(this.green >= 2){
			output = Config.GREEN;
		}
		else{
			output = this.bestColourChoice; 
		}
		tournamentColour = output;
		return output;
	}
	
	/* Called every time the AI receives something from the server to update the tokens of the player
	*  used because the strategy has no knowledge of the player class */ 
	public void tokenChoice(ArrayList<String> tokens){
		this.currentTokens = tokens; 
		int tokenIndex = 0;
		
		for(int i = 0; i < currentTokens.size(); i++){
			String t = currentTokens.get(i);
			if(t.equals(Config.RED)){
				this.redToken = true;
			}
			else if(t.equals(Config.BLUE)){
				this.blueToken = true;
			}
			else if(t.equals(Config.PURPLE)){
				this.purpleToken = true;
			}
			else if (t.equals(Config.YELLOW)){
				this.yellowToken = true;
			}
			else if(t.equals(Config.GREEN)){
				this.greenToken = true;
			}
		}
		
		for(boolean b : booleanTokens){
			if(!b){
				tokenIndex++;
				break;
			}
		}
		
		if(tokenIndex == 1){
			this.bestColourChoice = Config.RED;
		}
		else if(tokenIndex == 2){
			this.bestColourChoice = Config.BLUE;
		}
		else if(tokenIndex == 3){
			this.bestColourChoice = Config.PURPLE;
		}
		else if(tokenIndex == 4){
			this.bestColourChoice = Config.YELLOW;
		}
		else{
			this.bestColourChoice = Config.GREEN;
		}
	}

	/* Adds a token to the boolean array keeping track of what tokens the AI has */
	public void addToken(){
		if(tournamentColour.equals(Config.RED)){
			this.redToken = true;
		}
		else if(tournamentColour.equals(Config.BLUE)){
			this.blueToken = true;
		}
		else if(this.tournamentColour.equals(Config.PURPLE)){
			if(this.purpleToken){
				int tokenIndex = 0;
				
				// loops through the boolean array looking for the next token needed 
				for(boolean b : booleanTokens){
					if(!b){
						tokenIndex++;
						break;
					}
				}
				
				if(tokenIndex == 1){
					this.redToken = true;
				}
				else if(tokenIndex == 2){
					this.blueToken = true;
				}
				else if(tokenIndex == 4){
					this.yellowToken = true;
				}
				else{
					this.greenToken = true;
				}
				
			}else{
				this.purpleToken = true;
			}
		}
		else if(tournamentColour.equals(Config.YELLOW)){
			this.yellowToken = true;
		}
		else if(tournamentColour.equals(Config.GREEN)){
			this.greenToken = true;
		}
	}
	
	/* Plays a card depending on the tournament colour and the number of coloured cards available */
	public String playACard() {
		
		if(highestTotalValue <= this.currentPoints || !this.started){
			
			if(tournamentColour.equals(Config.RED)){
				if(this.redCards.size() != 0){
					toPlay = redCards.get(0);
					output = Config.PLAY + " " + toPlay.getType() + " " + toPlay.getValue(); 
					this.redCards.remove(0);
					this.red--;
				}else{
					this.red = 0;
					output = playSupporter();
				}
				
			}
			else if(tournamentColour.equals(Config.BLUE)){
				if(this.blueCards.size() != 0){
					toPlay = blueCards.get(0);
					output = Config.PLAY + " " + toPlay.getType() + " " + toPlay.getValue(); 
					this.blueCards.remove(0);
					this.blue--;
				}else{
					this.blue = 0;
					output = playSupporter();
				}
			}
			else if(tournamentColour.equals(Config.PURPLE)){
				if(this.purpleCards.size() != 0){
					toPlay = purpleCards.get(0);
					output = Config.PLAY + " " + toPlay.getType() + " " + toPlay.getValue(); 
					this.purpleCards.remove(0);
					this.purple--;
				}else{
					this.purple = 0;
					output = playSupporter();
				}
			}
			else if(tournamentColour.equals(Config.YELLOW)){
				if(this.yellowCards.size() != 0){
					toPlay = yellowCards.get(0);
					output = Config.PLAY + " " + toPlay.getType() + " " + toPlay.getValue(); 
					this.yellowCards.remove(0);
					this.yellow--;
				}else{
					this.yellow = 0;
					output = playSupporter();
				}
			}
			else if(tournamentColour.equals(Config.GREEN)){
				if(this.greenCards.size() != 0){
					toPlay = greenCards.get(0);
					output = Config.PLAY + " " + toPlay.getType() + " " + toPlay.getValue(); 
					this.greenCards.remove(0);
					this.green--;
				}else{
					this.green = 0;
					output = playSupporter();
				}
			}
		}else{
			output = Config.WITHDRAW;
		}
		return output;
	}
	
	/* Only plays a supporter if there are no more coloured cards avaliable to play */
	public String playSupporter(){
		if(this.supportCards.size() != 0){
			toPlay = supportCards.get(0);
			if(checkPlayedMaiden(toPlay)){
				output = Config.PLAY + " " + toPlay.getType() + " " + toPlay.getValue(); 
				this.supportCards.remove(0);
				this.supporter--;
			}else{
				output = Config.END_TURN;
			}
		}else{
			this.supporter = 0;
			output = Config.END_TURN;
		}
		return output;
	}
	
	/* When playing a supporter, checks to see if a Maiden was played and ensures that the AI cann't play
	 * another Maiden in the same tournament */
	public boolean checkPlayedMaiden(Card toPlay){
		String type = toPlay.getType();
		if(type.equals(Config.MAIDEN) && !playedMaiden){
			this.playedMaiden = true;
			return true;
		}else if(type.equals(Config.MAIDEN) && playedMaiden){
			return false;
		}
		else{
			return true;
		}
	}

	/* Receives the AI's hand from the server and sorts the hand into their colours */
	public void getHand(ArrayList<Card> c) {
		this.hand = c;
		
		// counts how many of each colour there are 
		for(int i = 0; i < hand.size(); i++){
			Card nextCard = hand.get(i);
			if(nextCard.getType().equals(Config.RED)){
				this.red++;
				this.redCards.add(nextCard);
			}
			else if(nextCard.getType().equals(Config.BLUE)){
				this.blue++;
				this.blueCards.add(nextCard);
			}
			else if (nextCard.getType().equals(Config.PURPLE)){
				this.purple++;
				this.purpleCards.add(nextCard);
			}
			else if(nextCard.getType().equals(Config.YELLOW)){
				this.yellow++;
				this.yellowCards.add(nextCard);
			}
			else if(nextCard.getType().equals(Config.GREEN)){
				this.green++;
				this.greenCards.add(nextCard);
			}
			else if (nextCard.getType().equals(Config.SQUIRE) || nextCard.getType().equals(Config.MAIDEN)){
				this.supporter++;
				supportCards.add(nextCard);
			}
			else{
				this.actionCards.add(nextCard);
			}
		}
		
		// reset the hand variable because we no longer need it 
		hand.clear();
	}
	
	/* Adds a new picked up card to the AI's hand */
	public void addNewCard(String card){
		String[] input = card.split("_");
		String type = input[0];
		int value = Integer.parseInt(input[1]);
		Card newCard;

		if(type.equals(Config.RED)){
			newCard = new ColourCard(type, value);
			this.red++;
			this.redCards.add(newCard);
		}
		else if(type.equals(Config.BLUE)){
			newCard = new ColourCard(type, value);
			this.blue++;
			this.blueCards.add(newCard);
		}
		else if(type.equals(Config.PURPLE)){
			newCard = new ColourCard(type, value);
			this.purple++;
			this.purpleCards.add(newCard);
		}
		else if(type.equals(Config.YELLOW)){
			newCard = new ColourCard(type, value);
			this.yellow++;
			this.yellowCards.add(newCard);
		}
		else if(type.equals(Config.GREEN)){
			newCard = new ColourCard(type, value);
			this.green++;
			this.greenCards.add(newCard);
		}
		else if(type.equals(Config.SQUIRE) || type.equals(Config.MAIDEN)){
			newCard = new SupportCard(type, value);
			this.supporter++;
			this.supportCards.add(newCard);
		}
		else{
			newCard = new ActionCard(type);
			this.actionCards.add(newCard);
		}
	}
	
	/* Proesses all messages sent from the Server */
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
					this.hand.add(newCard);					
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
				this.started = true;
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
		
		if(this.currentPlayer){
			output = playACard();
		}
		return output;
	}
	
	public String processContinueWithdraw(String msg){
		String[] input = msg.split(" ");
		System.out.println("continue");
		System.out.println(msg);
		
		if(msg.contains(Config.PURPLE_WIN) ){
			if(input[7].equals(this.name)){
				addToken();
				resetVariables();
				output = Config.PURPLE_WIN + " " + Config.PURPLE;
			}else{
				output = Config.OUTPUT;
			}
		}else{
		
			if(msg.contains(Config.TOURNAMENT_WINNER)){
				if(input[4].equals(this.name)){
					addToken();
					resetVariables();
				}
				output = Config.OUTPUT;
			}
			else if(!msg.contains(Config.TOURNAMENT_WINNER)){
				if(input[0].equals(this.name)){
					this.currentPlayer = false;
					output = Config.OUTPUT;
					this.currentPoints = Integer.parseInt(input[2]);
				}
				else if(input[4].equals(this.name)){
					this.currentPlayer = true;
					
					// check the highest total value played by any player 
					if(Integer.parseInt(input[2]) > highestTotalValue){
						highestTotalValue = Integer.parseInt(input[2]);
					}
					
					if(highestTotalValue > this.currentPoints && this.started){
						output = Config.WITHDRAW;
					}else{
						addNewCard(input[5]); 
						output = playACard();	
					}
				}
			}
			else{
				output = Config.OUTPUT; 
			}
		}
		System.out.println("output: " + output);
		return output;
	}
	
	public void resetVariables(){
		this.started = false;
		this.currentPlayer = false;
		this.playedMaiden = false;
	}
}
