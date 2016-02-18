package game;

public class SupportCard extends Card {
	
	public SupportCard(String supportType, int value) {
		this.setValue(value);
		this.setType(supportType);
		setCardType("support");
	}
	public SupportCard(String supportType, int value, String cardimg) {
		this.setValue(value);
		this.setType(supportType);
		setCardType("support");
		this.setCardImage(cardimg);
	}
}
