package game;

public class Card {
	private String type;
	private int value;
	private String cardType;
	private String cardImage;
	private String cardDescription;
	
	public Card(String type, int value) {
		this.type = type;
		this.value = value;
		this.cardType = "";
		cardImage = "";
		setCardDescription("");
	}
	
	public Card() {
		type = "";
		value = 0;
		cardType = "";
		cardImage = "";
		setCardDescription("");
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

	public String getCardDescription() {
		return cardDescription;
	}

	public void setCardDescription(String cardDescription) {
		this.cardDescription = cardDescription;
	}

}
