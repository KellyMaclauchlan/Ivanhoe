package ui;

import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

public class MainWindow extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//buttons
	JButton withdrawButton;
	JButton endTurnButton;
	
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
	
	
	public MainWindow(){
		super();
		setTitle("Ivanho");
		this.setSize(684, 504);
		setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setUpScreen(this.getContentPane());
		this.pack();
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
