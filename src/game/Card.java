package game;

public class Card {
	private String type;
	private int value;
	private String cardType;
	private String cardImage;
	
	public Card() {
		type = "";
		value = 0;
		cardType = "";
		cardImage = "";
	}
		
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getCardImage() {
		return cardImage;
	}
	public void setCardImage(String cardImage) {
		this.cardImage = cardImage;
	}

}
