package game;

public class ColourCard extends Card {
	private String colour;
	
	public ColourCard(String colour, int value) {
		this.setColour(colour);
		this.setValue(value);
	}
	
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
}
