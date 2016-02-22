package ui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import config.Config;
import config.Observer;
import config.Subject;

public class MainWindow extends JFrame implements Subject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//observer pattern 
	private ArrayList<Observer>observers =new ArrayList<Observer>();
	private Config config= new Config();
	//buttons
	JButton withdrawButton;
	JButton endTurnButton;
	JButton playCardButton;
	
	//radio buttons
	JRadioButton[] playerNames;
	
	//point labels 
	JLabel[] playerPoints;
	
	//arrows
	JButton leftArrow;
	JButton rightArrow;
	
	//players cards
	JButton[] playerCards;
	
	//cards played this round [player][cards]
	JLabel[][] playedCards;
	
	//tokens [player][token]
	JLabel[][] tokens;
	
	//decks
	JLabel deck;
	JLabel discardPile;
	
	GridBagConstraints c = new GridBagConstraints();
	//testing variables
	public int lastCard;
	Boolean leftClick;
	JLabel testLable=new JLabel();
	
	public MainWindow(){
		super();
		setTitle("Ivanho");
		this.setSize(1360, 840);
		//setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		leftClick=false;
		setUpScreen(this.getContentPane());
		//this.pack();
	}
	
	@Override
	public void registerObserver(Observer observer) {
		// TODO Auto-generated method stub
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(String message) {
		// TODO Auto-generated method stub
		for(Observer ob:observers){
			ob.update(message);
		}
	}
	
	public void setUpScreen(Container pane){
		
		pane.setLayout(new GridBagLayout());
		c.fill=GridBagConstraints.HORIZONTAL;
		
		setUpTokens(pane);
		setUpPlayedCards(pane);
		setUpPlayerLables(pane);
		setUpPoints(pane);
		setUpPlayerCards(pane);
		setUpOtherComponents(pane);
		addNames();
		addDefaults();
		addButtonListners();
	}
	
	//adding names to the components for testing
	private void addNames() {
	
		// Individual Buttons
		this.leftArrow.setName("leftArrow");
		this.rightArrow.setName("rightArrow");
		this.deck.setName("deck");
		this.discardPile.setName("discard");
		this.withdrawButton.setName("withdraw");
		this.endTurnButton.setName("endTurn");
		this.playCardButton.setName("playCard");
		this.testLable.setName("test");
		
		// Player Names
		this.playerNames[0].setName("player1name");
		this.playerNames[1].setName("player2name");
		this.playerNames[2].setName("player3name");
		this.playerNames[3].setName("player4name");
		this.playerNames[4].setName("player5name");
		
		// Player Scores
		this.playerPoints[0].setName("player1points");
		this.playerPoints[1].setName("player2points");
		this.playerPoints[2].setName("player3points");
		this.playerPoints[3].setName("player4points");
		this.playerPoints[4].setName("player5points");
		
		// Player Tokens
		//Player 1
		this.tokens[0][0].setName("player1blue");
		this.tokens[0][1].setName("player1red");
		this.tokens[0][2].setName("player1yellow");
		this.tokens[0][3].setName("player1green");
		this.tokens[0][4].setName("player1purple");

		//Player 2
		this.tokens[1][0].setName("player2blue");
		this.tokens[1][1].setName("player2red");
		this.tokens[1][2].setName("player2yellow");
		this.tokens[1][3].setName("player2green");
		this.tokens[1][4].setName("player2purple");	
		
		//Player 3
		this.tokens[2][0].setName("player3blue");
		this.tokens[2][1].setName("player3red");
		this.tokens[2][2].setName("player3yellow");
		this.tokens[2][3].setName("player3green");
		this.tokens[2][4].setName("player3purple");
		
		//Player 4
		this.tokens[3][0].setName("player4blue");
		this.tokens[3][1].setName("player4red");
		this.tokens[3][2].setName("player4yellow");
		this.tokens[3][3].setName("player4green");
		this.tokens[3][4].setName("player4purple");
	
		//Player 5
		this.tokens[4][0].setName("player5blue");
		this.tokens[4][1].setName("player5red");
		this.tokens[4][2].setName("player5yellow");
		this.tokens[4][3].setName("player5green");
		this.tokens[4][4].setName("player5purple");
		
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
		
		//Played cards
		//Player 1
		this.playedCards[0][0].setName("player1card1");
		this.playedCards[0][1].setName("player1card2");
		this.playedCards[0][2].setName("player1card3");
		this.playedCards[0][3].setName("player1card4");
		this.playedCards[0][4].setName("player1card5");
		this.playedCards[0][5].setName("player1card6");
		this.playedCards[0][6].setName("player1card7");
		
		//Player 2
		this.playedCards[1][0].setName("player2card1");
		this.playedCards[1][1].setName("player2card2");
		this.playedCards[1][2].setName("player2card3");
		this.playedCards[1][3].setName("player2card4");
		this.playedCards[1][4].setName("player2card5");
		this.playedCards[1][5].setName("player2card6");
		this.playedCards[1][6].setName("player2card7");
		
		//Player 3
		this.playedCards[2][0].setName("player3card1");
		this.playedCards[2][1].setName("player3card2");
		this.playedCards[2][2].setName("player3card3");
		this.playedCards[2][3].setName("player3card4");
		this.playedCards[2][4].setName("player3card5");
		this.playedCards[2][5].setName("player3card6");
		this.playedCards[2][6].setName("player3card7");
	
		//Player 4
		this.playedCards[3][0].setName("player4card1");
		this.playedCards[3][1].setName("player4card2");
		this.playedCards[3][2].setName("player4card3");
		this.playedCards[3][3].setName("player4card4");
		this.playedCards[3][4].setName("player4card5");
		this.playedCards[3][5].setName("player4card6");
		this.playedCards[3][6].setName("player4card7");
		
		//Player 5
		this.playedCards[4][0].setName("player5card1");
		this.playedCards[4][1].setName("player5card2");
		this.playedCards[4][2].setName("player5card3");
		this.playedCards[4][3].setName("player5card4");
		this.playedCards[4][4].setName("player5card5");
		this.playedCards[4][5].setName("player5card6");
		this.playedCards[4][6].setName("player5card7");
	}
	//adds default cards for tests
	private void addDefaults(){
		this.playerCards[0].setIcon(new ImageIcon("resources/cards_small/simpleCards.jpg"));
		this.playerCards[1].setIcon(new ImageIcon("resources/cards_small/simpleCards1.jpg"));
		this.playerCards[2].setIcon(new ImageIcon("resources/cards_small/simpleCards2.jpg"));
		this.playerCards[3].setIcon(new ImageIcon("resources/cards_small/simpleCards3.jpg"));
		this.playerCards[4].setIcon(new ImageIcon("resources/cards_small/simpleCards4.jpg"));
		this.playerCards[5].setIcon(new ImageIcon("resources/cards_small/simpleCards5.jpg"));
		this.playerCards[6].setIcon(new ImageIcon("resources/cards_small/simpleCards6.jpg"));
		this.playerCards[7].setIcon(new ImageIcon("resources/cards_small/simpleCards7.jpg"));
		this.playerCards[8].setIcon(new ImageIcon("resources/cards_small/simpleCards8.jpg"));
		this.playerCards[9].setIcon(new ImageIcon("resources/cards_small/simpleCards9.jpg"));
		this.playerNames[0].setSelected(true);
		
		this.tokens[0][0].setIcon(new ImageIcon("resources/icons/blue_empty.png"));
		this.tokens[1][0].setIcon(new ImageIcon("resources/icons/blue_empty.png"));
		this.tokens[2][0].setIcon(new ImageIcon("resources/icons/blue_empty.png"));
		this.tokens[3][0].setIcon(new ImageIcon("resources/icons/blue_empty.png"));
		this.tokens[4][0].setIcon(new ImageIcon("resources/icons/blue_empty.png"));
		
		this.tokens[0][1].setIcon(new ImageIcon("resources/icons/red_empty.png"));
		this.tokens[1][1].setIcon(new ImageIcon("resources/icons/red_empty.png"));
		this.tokens[2][1].setIcon(new ImageIcon("resources/icons/red_empty.png"));
		this.tokens[3][1].setIcon(new ImageIcon("resources/icons/red_empty.png"));
		this.tokens[4][1].setIcon(new ImageIcon("resources/icons/red_empty.png"));
		
		this.tokens[0][2].setIcon(new ImageIcon("resources/icons/yellow_empty.png"));
		this.tokens[1][2].setIcon(new ImageIcon("resources/icons/yellow_empty.png"));
		this.tokens[2][2].setIcon(new ImageIcon("resources/icons/yellow_empty.png"));
		this.tokens[3][2].setIcon(new ImageIcon("resources/icons/yellow_empty.png"));
		this.tokens[4][2].setIcon(new ImageIcon("resources/icons/yellow_empty.png"));
		
		this.tokens[0][3].setIcon(new ImageIcon("resources/icons/green_empty.png"));
		this.tokens[1][3].setIcon(new ImageIcon("resources/icons/green_empty.png"));
		this.tokens[2][3].setIcon(new ImageIcon("resources/icons/green_empty.png"));
		this.tokens[3][3].setIcon(new ImageIcon("resources/icons/green_empty.png"));
		this.tokens[4][3].setIcon(new ImageIcon("resources/icons/green_empty.png"));
		
		this.tokens[0][4].setIcon(new ImageIcon("resources/icons/purple_empty.png"));
		this.tokens[1][4].setIcon(new ImageIcon("resources/icons/purple_empty.png"));
		this.tokens[2][4].setIcon(new ImageIcon("resources/icons/purple_empty.png"));
		this.tokens[3][4].setIcon(new ImageIcon("resources/icons/purple_empty.png"));
		this.tokens[4][4].setIcon(new ImageIcon("resources/icons/purple_empty.png"));
	}


 	private void addButtonListners() {
		// TODO Auto-generated method stub
		this.leftArrow.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				leftArrowClicked();
				
			}});
		this.rightArrow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				rightArrowClicked();
			}});
		this.withdrawButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				withdrawClicked();				
			}});
		this.endTurnButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				endTurnClicked();	
			}});
		this.playCardButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				playCardClicked();	
			}});
		this.playerCards[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				lastCard=0;	
				deck.setText(lastCard+"");
			}});
		this.playerCards[1].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
					lastCard=1;
					deck.setText(lastCard+"");
				}});
		this.playerCards[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				lastCard=2;	
				deck.setText(lastCard+"");
			}});
		this.playerCards[3].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				lastCard=3;	
				deck.setText(lastCard+"");
			}});
		this.playerCards[4].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				lastCard=4;	
			}});
		this.playerCards[5].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				lastCard=5;	
				deck.setText(lastCard+"");
			}});
		this.playerCards[6].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				lastCard=6;	
				deck.setText(lastCard+"");
			}});
		this.playerCards[7].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				lastCard=7;	
				deck.setText(lastCard+"");
			}});
		this.playerCards[8].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				lastCard=8;	
				deck.setText(lastCard+"");
			}});
		this.playerCards[9].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
				lastCard=9;
				deck.setText(lastCard+"");
			}});
		
 	}
 	protected void playCardClicked() {
		// TODO Auto-generated method stub		
		notifyObservers(config.PLAYEDCARD);
		deck.setText("played "+lastCard+"");
		//this.testLable.setText("played "+this.lastCard+"");
	}
 	

	public void withdrawClicked() {
		// TODO Auto-generated method stub
		for(int i=0;i<5;i++){
			if(this.playerNames[i].isSelected())
				this.playerNames[i].setSelected(false);
		}
		notifyObservers(config.WITHDRAW_CLICK);
	}
 	public void endTurnClicked() {
		// TODO Auto-generated method stub
		for(int i=0;i<5;i++){
			if(this.playerNames[i].isSelected())
				this.playerNames[i].setSelected(false);
		}
		notifyObservers(config.END_TURN_CLICK);
	}

	public void leftArrowClicked(){
		for(int i=0;i<9;i++){
 			this.playerCards[i].setIcon(this.playerCards[i+1].getIcon());
 		}
 		this.playerCards[9].setIcon(new ImageIcon("resources/cards_small/simpleCards18.jpg"));
 		notifyObservers(config.LEFT_CLICK);
 	}
 	public void rightArrowClicked(){
 		for(int i=9;i>0;i--){
 			this.playerCards[i].setIcon(this.playerCards[i-1].getIcon());
 		}
 		this.playerCards[0].setIcon(new ImageIcon("resources/cards_small/simpleCards18.jpg"));
 		notifyObservers(config.RIGHT_CLICK);
 	}
 	private void setUpOtherComponents(Container pane) {
		// TODO Auto-generated method stub
		// setup arrows
		leftArrow= new JButton();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=0;
		c.gridy=18;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(leftArrow, c);
		
		rightArrow = new JButton();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=33;
		c.gridy=18;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(rightArrow, c);
		
		//setup withdraw button 
		withdrawButton = new JButton("Withdraw");
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=4;
		c.gridy=14;
		c.gridheight=2;
		c.gridwidth=4;
		pane.add(withdrawButton, c);
		
		//setup end turn button
		endTurnButton = new JButton("End Turn");
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=24;
		c.gridy=14;
		c.gridheight=2;
		c.gridwidth=4;
		pane.add(endTurnButton, c);
		
		//setup play card button
		playCardButton = new JButton("Play Card");
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=30;
		c.gridy=14;
		c.gridheight=2;
		c.gridwidth=4;
		pane.add(playCardButton, c);
		
		// set up draw pile
		deck = new JLabel();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=15;
		c.gridy=13;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(deck, c);
		
		// set up discard pile
		discardPile = new JLabel();
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=19;
		c.gridy=13;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(discardPile, c);
	}


	private void setUpPlayerCards(Container pane) {
		// TODO Auto-generated method stub
		int x=3;
		int y=18;
		int card=0;
		playerCards= new JButton[10];
		for (int i=0;i<10;i++){
			playerCards[i]= new JButton();
		}
		//card 1
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playerCards[card], c);
		x+=3;
		card++;
		//card 2
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playerCards[card], c);
		x+=3;
		card++;
		//card 3
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playerCards[card], c);
		x+=3;
		card++;
		//card 4
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playerCards[card], c);
		x+=3;
		card++;
		//card 5
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playerCards[card], c);
		x+=3;
		card++;
		//card 6
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playerCards[card], c);
		x+=3;
		card++;
		//card 7
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playerCards[card], c);
		x+=3;
		card++;
		//card 8
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playerCards[card], c);
		x+=3;
		card++;
		//card 9
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playerCards[card], c);
		x+=3;
		card++;
		//card 10
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playerCards[card], c);
		
	}


	private void setUpPoints(Container pane) {
		// TODO Auto-generated method stub
		int x=7;
		int y=11;
		int p=0;
		playerPoints= new JLabel[5];
		for (int i=0;i<5;i++){
			playerPoints[i]= new JLabel("0");
		}
		// player 1
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=1;
		c.gridwidth=2;
		playerPoints[p].setText("0");
		pane.add(playerPoints[p], c);
		x+=5;
		p++;
		// player 2
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=1;
		c.gridwidth=2;
		playerPoints[p].setText("0");
		pane.add(playerPoints[p], c);
		x+=5;
		p++;
		// player 3
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=1;
		c.gridwidth=2;
		playerPoints[p].setText("0");
		pane.add(playerPoints[p], c);
		x+=5;
		p++;
		//player 4
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=1;
		c.gridwidth=2;
		playerPoints[p].setText("0");
		pane.add(playerPoints[p], c);
		x+=5;
		p++;
		//player 5
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=1;
		c.gridwidth=2;
		playerPoints[p].setText("0");
		pane.add(playerPoints[p], c);
	}


	private void setUpPlayerLables(Container pane) {
		// TODO Auto-generated method stub
		int x=6;
		int y=10;
		int p=0;
		playerNames= new JRadioButton[5];
		for (int i=0;i<5;i++){
			playerNames[i]= new JRadioButton();
		}
		//player 1
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=1;
		c.gridwidth=3;
		playerNames[p].setText("p1");
		pane.add(playerNames[p], c);
		x+=5;
		p++;
		//player 2
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=1;
		c.gridwidth=3;
		playerNames[p].setText("p2");
		pane.add(playerNames[p], c);
		x+=5;
		p++;
		//player 3
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=1;
		c.gridwidth=3;
		playerNames[p].setText("p3");
		pane.add(playerNames[p], c);
		x+=5;
		p++;
		//player 4
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=1;
		c.gridwidth=3;
		playerNames[p].setText("p4");
		pane.add(playerNames[p], c);
		x+=5;
		p++;
		//player 5
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=1;
		c.gridwidth=3;
		playerNames[p].setText("p5");
		pane.add(playerNames[p], c);
		x+=5;
		p++;

	}


	private void setUpPlayedCards(Container pane) {
		// TODO Auto-generated method stub
		int x=7;
		int y=1;
		int p=0;
		int card=0;
		playedCards= new JLabel[5][7];
		for(int i=0;i<5;i++){
			for(int j=0;j<7;j++){
				playedCards[i][j]=new JLabel();
			}
		}
		//player 1 card 1
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;		
		//player1 card 2
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;
		//player1 card 3
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;
		//player1 card 4
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;	
		//player1 card 5
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;	
		//player1 card 6
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;		
		//player1 card 7
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);	
		x+=3;
		y=1;
		card=0;
		p++;
		
		//player 2 card 1
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;		
		//player 2 card 2
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;
		//player 2 card 3
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;
		//player 2 card 4
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;	
		//player 2 card 5
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;	
		//player 2 card 6
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;		
		//player 2 card 7
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);	
		x+=3;
		y=1;
		card=0;
		p++;
		
		//player 3 card 1
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;		
		//player 3 card 2
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;
		//player 3 card 3
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;
		//player 3 card 4
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;	
		//player 3 card 5
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;	
		//player 3 card 6
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;		
		//player 3 card 7
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);	
		x+=3;
		y=1;
		card=0;
		p++;
		
		//player 4 card 1
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;		
		//player 4 card 2
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;
		//player 4 card 3
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;
		//player 4 card 4
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;	
		//player 4 card 5
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;	
		//player 4 card 6
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;		
		//player 4 card 7
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);	
		x+=3;
		y=1;
		card=0;
		p++;
		
		//player 5 card 1
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;		
		//player 5 card 2
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;
		//player 5 card 3
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;
		//player 5 card 4
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;	
		//player 5 card 5
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;	
		//player 5 card 6
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);
		y++;
		card++;		
		//player 5 card 7
		c.fill=GridBagConstraints.HORIZONTAL;
		c.gridx=x;
		c.gridy=y;
		c.gridheight=3;
		c.gridwidth=2;
		pane.add(playedCards[p][card], c);	
		
	}


	public void setUpTokens(Container pane){
		//tokens
				tokens= new JLabel[5][5];
				for(int i=0;i<5;i++){
					for(int j=0;j<5;j++){
						tokens[i][j]=new JLabel();
					}
				}
				//p1
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=5;
				c.gridy=1;
				
				pane.add(tokens[0][0], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=5;
				c.gridy=2;
				pane.add(tokens[0][1], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=5;
				c.gridy=3;
				pane.add(tokens[0][2], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=5;
				c.gridy=4;
				pane.add(tokens[0][3], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=5;
				c.gridy=5;
				pane.add(tokens[0][4], c);
				
				//p2
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=10;
				c.gridy=1;
				pane.add(tokens[1][0], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=10;
				c.gridy=2;
				pane.add(tokens[1][1], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=10;
				c.gridy=3;
				pane.add(tokens[1][2], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=10;
				c.gridy=4;
				pane.add(tokens[1][3], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=10;
				c.gridy=5;
				pane.add(tokens[1][4], c);
				
				//p3
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=15;
				c.gridy=1;
				pane.add(tokens[2][0], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=15;
				c.gridy=2;
				pane.add(tokens[2][1], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=15;
				c.gridy=3;
				pane.add(tokens[2][2], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=15;
				c.gridy=4;
				pane.add(tokens[2][3], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=15;
				c.gridy=5;
				pane.add(tokens[2][4], c);
				
				//p4
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=20;
				c.gridy=1;
				pane.add(tokens[3][0], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=20;
				c.gridy=2;
				pane.add(tokens[3][1], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=20;
				c.gridy=3;
				pane.add(tokens[3][2], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=20;
				c.gridy=4;
				pane.add(tokens[3][3], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=20;
				c.gridy=5;
				pane.add(tokens[3][4], c);
				
				//p5
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=25;
				c.gridy=1;
				pane.add(tokens[4][0], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=25;
				c.gridy=2;
				pane.add(tokens[4][1], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=25;
				c.gridy=3;
				pane.add(tokens[4][2], c);		
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=25;
				c.gridy=4;
				pane.add(tokens[4][3], c);
				c.fill = GridBagConstraints.HORIZONTAL;
				c.gridx=25;
				c.gridy=5;
				pane.add(tokens[4][4], c);
				
	}

}
