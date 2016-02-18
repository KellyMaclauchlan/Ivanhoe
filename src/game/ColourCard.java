package game;

public class ColourCard extends Card {
	public ColourCard(String colour, int value) {
		this.setType(colour);
		this.setValue(value);
		setCardType("colour");
	}
	public ColourCard(String colour, int value,String cardimg) {
		this.setType(colour);
		this.setValue(value);
		setCardType("colour");
		this.setCardImage(cardimg);
	}


	
}
