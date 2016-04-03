package network;

import config.Config;

public class InputProcessor {
	Client client;
	GUIProcessor processor;
	public InputProcessor(Client client) {
		this.client = client;
		this.processor = client.getProcessor();
	}
	
	/* Handles what the server has sent from the Game Engine and processes
	 * what buttons/popups/commands the client and GUI must send back */
	public String processInput(String msg){
		if (msg.equals(Config.QUIT)) {  
			client.setOutput(Config.QUIT + " " + client.getThisPlayerName());
		} 
		
		else if(msg.contains(Config.PLAYER_LEFT)){
			client.getWindow().GameOverPopup("No one");
		}
		
		else if (msg.contains(Config.PLAY_IVANHOE)) {
			String[] input = msg.split(" ");
			String cardName = input[3];
			String playerName = input[2];
			if (!client.isIvanhoePrompted()) {
				if (playerName.equals(client.getThisPlayerName())) {
					boolean played = client.getWindow().playIvanhoe(cardName);
					client.setIvanhoePrompted(true);
					if (played) {
						return client.setOutput(Config.PLAY + " " + Config.IVANHOE + " " + cardName);
					} else if (!played) {
						return client.setOutput(Config.PLAY + " " +  Config.IVANHOE_DECLINED);
					}
				}
			}
		} 
		
		else if (msg.contains(Config.IS_STUNNED)) {
			processor.processWaiting(msg);
			client.setOutput(Config.END_TURN);
		}

		/* Processes all actions from the MainWindowController */
		else if (msg.contains(Config.FROMUPDATE)){
			client.setOutput(msg.substring(Config.FROMUPDATE.length()));
		}
		
		/* Check to see if this client is the first player or not */
		else if(msg.contains(Config.CLIENT_START)){
			client.setOutput(Config.CLIENT_START);
		}
		
		/* Prompts the first player for the number of players in the game */
		else if(msg.contains(Config.FIRSTPLAYER)){
			client.setOutput(Config.START + " " + client.getWindow().getNumberOfPlayersFromPlayer() + " " +
					client.getWindow().getNumberOfAIFromPlayer());
		}

		/* Once the player is connected, prompts that player for their name */ 
		else if(msg.contains(Config.PROMPT_JOIN) || msg.contains(Config.DUPLICATE)){
			client.setOutput(processor.processPromptJoin(msg));
		}
		
		/* If there is not a sufficient amount of players yet, a waiting for more players window appears */
		else if(msg.contains(Config.NEED_PLAYERS)){
			client.getWindow().showWaiting();
		}
		
		/* Receives each player and their hand */
		else if (msg.contains(Config.HAND)){
			client.getWindow().hideWaitng();
			client.setOutput(processor.processPlayerName(msg));
		}
		
		/* It is the start currentPlayer's turn */
		else if (msg.contains(Config.TURN)){
			client.setOutput(processor.processPlayerTurn(msg));
		}
		
		/* When the player has chosen which card(s) they wish to play */
		else if (msg.contains(Config.PLAY) ){
			client.setOutput(processor.processPlay(msg));
		}
		
		/* set tournament colour */
		else if (msg.contains(Config.COLOUR)) {
			client.setOutput(processor.processColour(msg));
		}
		
		/* Server is waiting for the next card to be played */
		else if (msg.contains(Config.WAITING)){
			client.setOutput(processor.processWaiting(msg));
		}
		
		/* When the currentPlayer has finished playing their turn and does not withdraw */
		else if(msg.contains(Config.CONTINUE)||msg.contains(Config.WITHDRAW)){
			if(msg.length() == 9){
				client.setOutput(Config.WITHDRAW);
			}else {
				client.setOutput(processor.processContinueWithdraw(msg));	
			}
		}
		
		else if (msg.contains(Config.END_TURN)){
			client.setOutput(Config.END_TURN);
		}
		
		else if (msg.contains(Config.WITHDRAW)) {
			String[] withdrawString = msg.split(" ");
			String name = withdrawString[0];
			client.getWindow().playerWithdraws(name); 
		}
		
		 /* Game winner announcement */
		 
		if (msg.contains(Config.GAME_WINNER)){
			String[] input = msg.split(" ");
			String winner = input[input.length - 1];
			int player = client.getWindow().getPlayerByName(winner);
			client.getWindow().addToken(player, client.getWindow().getTournamentColour());
			client.getWindow().GameOverPopup(winner);
		} else if (msg.contains(Config.MAIDEN) && !msg.contains(Config.PLAY) && !msg.contains(Config.HAND) && !msg.contains("_") && !msg.contains(Config.WAITING)) {
			String tokenToRemove = null;
			String[] input = msg.split(" ");
			if (msg.equals(Config.MAIDEN)) {			
				tokenToRemove = client.getWindow().playerPickTokenRemove();
				client.setOutput(Config.WITHDRAW + " " + tokenToRemove);
			} else {
				tokenToRemove = input[1];
				client.getWindow().removeToken(client.getWindow().getCurrPlayer(), tokenToRemove);
				client.setOutput(Config.WITHDRAW + " " + client.getCurrPlayer());
			}
		}		
		return client.getOutput(); 
	}
	
}
