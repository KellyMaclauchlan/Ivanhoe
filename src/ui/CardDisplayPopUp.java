package ui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

import config.Config;
import game.Card;

public class CardDisplayPopUp extends JFrame{
	JButton leftArrow;
	JButton rightArrow;
	int moved;
	//players cards
	JButton[] playerCards;
	ArrayList<Card>cards;
	
	public CardDisplayPopUp(ArrayList<Card> cards){
		this.cards = cards;
		moved = 0;
		setUp();
		addButtonListners();
		placeCards();
		name();
	}
	
	public void name(){
		this.leftArrow.setName("leftArrow");
		this.rightArrow.setName("rightArrow");
		
		//Player's cards 
		this.playerCards[0].setName("card1");
		this.playerCards[1].setName("card2");
		this.playerCards[2].setName("card3");
		this.playerCards[3].setName("card4");
		this.playerCards[4].setName("card5");
		this.playerCards[5].setName("card6");
		this.playerCards[6].setName("card7");
		this.playerCards[7].setName("card8");
		this.playerCards[8].setName("card9");
		this.playerCards[9].setName("card10");
	}
	
	public void placeCards(){
		int max = Math.min(10, cards.size());
		for(int i = 0; i < max; i++){
			this.playerCards[i].setIcon(ResourceLoader.loadImage(cards.get(i+moved).getCardImage()));
		}
	}
	
	private void addButtonListners() {
		this.leftArrow.addActionListener(new ActionListener() {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			leftArrowClicked();
		}});
		this.rightArrow.addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent e) {
			rightArrowClicked();
		}});
	}
	
	public void leftArrowClicked(){
		if(moved != 0){
			moved--;
			for(int i = 9; i > 0; i--){
				this.playerCards[i].setIcon(this.playerCards[i-1].getIcon());
			}
			this.playerCards[0].setIcon(ResourceLoader.loadImage(cards.get(moved).getCardImage()));
		}
 	}
	
 	public void rightArrowClicked(){
 		if(moved < cards.size()-10){
			moved++;
			for(int i = 0; i < 9; i++){
	 			this.playerCards[i].setIcon(this.playerCards[i+1].getIcon());
	 		}
	 		this.playerCards[9].setIcon(ResourceLoader.loadImage(cards.get(moved+9).getCardImage()));
		}
 	}
	
	public void setUp() {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(1360, 200);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 40, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{ 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JButton leftArrow = new JButton("");
		leftArrow.setIcon(ResourceLoader.loadImage(Config.ARROW_LEFT));
		GridBagConstraints gbc_leftArrow = new GridBagConstraints();
		gbc_leftArrow.gridheight = 3;
		gbc_leftArrow.gridwidth = 2;
		gbc_leftArrow.insets = new Insets(0, 0, 5, 5);
		gbc_leftArrow.gridx = 2;
		gbc_leftArrow.gridy = 1;
		getContentPane().add(leftArrow, gbc_leftArrow);
		
		JButton card1 = new JButton("");
		GridBagConstraints gbc_card1 = new GridBagConstraints();
		gbc_card1.gridheight = 3;
		gbc_card1.gridwidth = 3;
		gbc_card1.insets = new Insets(0, 0, 5, 5);
		gbc_card1.gridx = 4;
		gbc_card1.gridy = 1;
		getContentPane().add(card1, gbc_card1);
		
		JButton card2 = new JButton("");
		GridBagConstraints gbc_card2 = new GridBagConstraints();
		gbc_card2.gridheight = 3;
		gbc_card2.gridwidth = 3;
		gbc_card2.insets = new Insets(0, 0, 5, 5);
		gbc_card2.gridx = 7;
		gbc_card2.gridy = 1;
		getContentPane().add(card2, gbc_card2);
		
		JButton card3 = new JButton("");
		GridBagConstraints gbc_card3 = new GridBagConstraints();
		gbc_card3.gridheight = 3;
		gbc_card3.gridwidth = 3;
		gbc_card3.insets = new Insets(0, 0, 5, 5);
		gbc_card3.gridx = 10;
		gbc_card3.gridy = 1;
		getContentPane().add(card3, gbc_card3);
		
		JButton card4 = new JButton("");
		GridBagConstraints gbc_card4 = new GridBagConstraints();
		gbc_card4.gridheight = 3;
		gbc_card4.gridwidth = 3;
		gbc_card4.insets = new Insets(0, 0, 5, 5);
		gbc_card4.gridx = 13;
		gbc_card4.gridy = 1;
		getContentPane().add(card4, gbc_card4);
		
		JButton card5 = new JButton("");
		GridBagConstraints gbc_card5 = new GridBagConstraints();
		gbc_card5.gridheight = 3;
		gbc_card5.gridwidth = 3;
		gbc_card5.insets = new Insets(0, 0, 5, 5);
		gbc_card5.gridx = 16;
		gbc_card5.gridy = 1;
		getContentPane().add(card5, gbc_card5);
		
		JButton card6 = new JButton("");
		GridBagConstraints gbc_card6 = new GridBagConstraints();
		gbc_card6.gridheight = 3;
		gbc_card6.gridwidth = 3;
		gbc_card6.insets = new Insets(0, 0, 5, 5);
		gbc_card6.gridx = 19;
		gbc_card6.gridy = 1;
		getContentPane().add(card6, gbc_card6);
		
		JButton card7 = new JButton("");
		GridBagConstraints gbc_card7 = new GridBagConstraints();
		gbc_card7.gridheight = 3;
		gbc_card7.gridwidth = 2;
		gbc_card7.insets = new Insets(0, 0, 5, 5);
		gbc_card7.gridx = 22;
		gbc_card7.gridy = 1;
		getContentPane().add(card7, gbc_card7);
		
		JButton card8 = new JButton("");
		GridBagConstraints gbc_card8 = new GridBagConstraints();
		gbc_card8.gridwidth = 3;
		gbc_card8.gridheight = 3;
		gbc_card8.insets = new Insets(0, 0, 5, 5);
		gbc_card8.gridx = 24;
		gbc_card8.gridy = 1;
		getContentPane().add(card8, gbc_card8);
		
		JButton card9 = new JButton("");
		GridBagConstraints gbc_card9 = new GridBagConstraints();
		gbc_card9.gridheight = 3;
		gbc_card9.gridwidth = 3;
		gbc_card9.insets = new Insets(0, 0, 5, 5);
		gbc_card9.gridx = 27;
		gbc_card9.gridy = 1;
		getContentPane().add(card9, gbc_card9);
		
		JButton card10 = new JButton("");
		GridBagConstraints gbc_card10 = new GridBagConstraints();
		gbc_card10.gridheight = 3;
		gbc_card10.gridwidth = 3;
		gbc_card10.insets = new Insets(0, 0, 5, 5);
		gbc_card10.gridx = 30;
		gbc_card10.gridy = 1;
		getContentPane().add(card10, gbc_card10);
		
		JButton rightArrow = new JButton("");
		rightArrow.setIcon(ResourceLoader.loadImage(Config.ARROW_RIGHT));
		GridBagConstraints gbc_rightArrow = new GridBagConstraints();
		gbc_rightArrow.gridheight = 3;
		gbc_rightArrow.gridwidth = 2;
		gbc_rightArrow.insets = new Insets(0, 0, 5, 5);
		gbc_rightArrow.gridx = 33;
		gbc_rightArrow.gridy = 1;
		getContentPane().add(rightArrow, gbc_rightArrow);
		
		this.leftArrow = leftArrow;
		this.rightArrow = rightArrow;
		this.playerCards = new JButton[10];
		this.playerCards[0] = card1;
		this.playerCards[1] = card2;
		this.playerCards[2] = card3;
		this.playerCards[3] = card4;
		this.playerCards[4] = card5;
		this.playerCards[5] = card6;
		this.playerCards[6] = card7;
		this.playerCards[7] = card8;
		this.playerCards[8] = card9;
		this.playerCards[9] = card10;
	}
}
