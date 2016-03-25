package game;

import config.Config;

public class ColourCard extends Card {
	public ColourCard(String colour, int value) {
		this.setType(colour);
		this.setValue(value);
		setCardType(Config.COLOUR);
	}
	public ColourCard(String colour, int value,String cardimg) {
		this.setType(colour);
		this.setValue(value);
		setCardType(Config.COLOUR);
		this.setCardImage(cardimg);
	}


	
}
