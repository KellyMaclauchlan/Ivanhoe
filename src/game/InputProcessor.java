package game;

import config.Config;

public class InputProcessor {
private GameProcessor gameProcessor = new GameProcessor();

	public String processInput(String input) {
		String output = Config.INPUT;
			if (input.contains(Config.START)) {
				output = gameProcessor.processStart(input); 
			} else if (input.contains(Config.JOIN)) {
				output = gameProcessor.processJoin(input); 
			}else if (input.contains(Config.DUPLICATE)){
				output = Config.DUPLICATE;
			} else if (input.contains(Config.START_TOURNAMENT)) {	
				output = gameProcessor.processStartTournament(); 
			} else if (input.contains(Config.COLOUR_PICKED)) {
				output = gameProcessor.processColourPicked(input); 
			} else if (input.contains(Config.PLAY)) {
				output = gameProcessor.processPlay(input); 
			} else if (input.contains(Config.END_TURN)) {
				output = gameProcessor.processEndTurn(); 
			} else if (input.contains(Config.PURPLE_WIN)) {
				output = gameProcessor.processPurpleWin(input); 
			} else if (input.contains(Config.WITHDRAW)) {
				output = gameProcessor.processWithdraw(input);
			} else if (input.contains(Config.PLAYER_LEFT)) {
				gameProcessor.processQuit(input);
			}
		return output;
	}
	
	public void joinGame(Player player) {
		gameProcessor.joinGame(player);
	}
}
