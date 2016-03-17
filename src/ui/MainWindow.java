package ui;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import config.*;
import config.Config;
import config.Observer;
import config.Subject;

public class MainWindow extends JFrame implements Subject {
	private static final long serialVersionUID = 1L;
	
	//observer pattern 
	private ArrayList<Observer>observers =new ArrayList<Observer>();
	
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
	JButton[] playedCards;
	
	//tokens [player][token]
	JLabel[][] tokens;
	public boolean[][] hasTokens;
	
	//decks
	JLabel deck;
	
	GridBagConstraints c = new GridBagConstraints();
	//testing variables
	public int lastCard;
	public int playedCard;
	Boolean leftClick;
	JLabel testLable = new JLabel();
	JLabel textLabel;
	JLabel cardTextLabel;
	public Boolean testing;
	public Boolean close;
	public MainWindow(){
		super();
		setTitle("Ivanhoe");
		this.setSize(1360, 840);
		//setSize(300, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.setFont(new Font("Helvetica",Font.PLAIN,35));
		leftClick = false;
		testing = true;
		setUpScreen(this.getContentPane());
		//this.pack();
		this.hasTokens=new boolean[5][5];
		for (int i = 0; i < 5; i++){
			for (int j = 0; j < 5; j++){
				this.hasTokens[i][j]=false;
			}
		}
		close=false;
		int fontsize=17;
		this.endTurnButton.setFont(new Font("Helvetica",Font.PLAIN,fontsize));
		this.withdrawButton.setFont(new Font("Helvetica",Font.PLAIN,fontsize));
		this.playCardButton.setFont(new Font("Helvetica",Font.PLAIN,fontsize));
		this.playerNames[0].setFont(new Font("Helvetica",Font.PLAIN,fontsize));
		this.playerNames[1].setFont(new Font("Helvetica",Font.PLAIN,fontsize));
		this.playerNames[2].setFont(new Font("Helvetica",Font.PLAIN,fontsize));
		this.playerNames[3].setFont(new Font("Helvetica",Font.PLAIN,fontsize));
		this.playerNames[4].setFont(new Font("Helvetica",Font.PLAIN,fontsize));
		
		this.playerPoints[0].setFont(new Font("Helvetica",Font.PLAIN,fontsize));
		this.playerPoints[1].setFont(new Font("Helvetica",Font.PLAIN,fontsize));
		this.playerPoints[2].setFont(new Font("Helvetica",Font.PLAIN,fontsize));
		this.playerPoints[3].setFont(new Font("Helvetica",Font.PLAIN,fontsize));
		this.playerPoints[4].setFont(new Font("Helvetica",Font.PLAIN,fontsize));
		
		this.addWindowListener(new WindowAdapter() { 
			@Override
			  public void windowClosing(WindowEvent we) {
				    closing();
				  }});
		
	}
	public void closing(){
		this.notifyObservers(Config.QUIT);
		System.exit(0); 
	}
	
	
	@Override
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(String message) {
		for(Observer ob:observers){
			ob.update(message);
		}
	}
	
	public void setUpScreen(Container pane){
		setup2();
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
		this.withdrawButton.setName("withdraw");
		this.endTurnButton.setName("endTurn");
		this.playCardButton.setName("playCard");
		this.testLable.setName("test");
		this.textLabel.setName("text");
		
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
		this.playedCards[0].setName("player1played");
		this.playedCards[1].setName("player2played");
		this.playedCards[2].setName("player3played");
		this.playedCards[3].setName("player4played");
		this.playedCards[4].setName("player5played");
	}
	
	//adds default cards for tests
	private void addDefaults(){

		this.playerNames[0].setSelected(true);
		
		for (int i = 0; i < Config.MAX_PLAYERS; i++) {
			this.tokens[i][0].setIcon(ResourceLoader.loadImage(Config.BLUE_EMPTY));
			this.tokens[i][1].setIcon(ResourceLoader.loadImage(Config.RED_EMPTY));
			this.tokens[i][2].setIcon(ResourceLoader.loadImage(Config.YELLOW_EMPTY));
			this.tokens[i][3].setIcon(ResourceLoader.loadImage(Config.GREEN_EMPTY));
			this.tokens[i][4].setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		}
		
		this.deck.setIcon(ResourceLoader.loadImage(Config.IMG_BACK));
		
		for(int i = 0; i < 5; i++){
				this.playedCards[i].setIcon(ResourceLoader.loadImage(Config.IMG_BACK));
		}
	}


 	private void addButtonListners() {
		this.leftArrow.addActionListener(new ActionListener() {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			if(testing){leftArrowClicked(Config.IMG_BACK);}
			notifyObservers(Config.LEFT_CLICK);
		}});
		this.rightArrow.addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent e) {
			if(testing){rightArrowClicked(Config.IMG_BACK);}
			notifyObservers(Config.RIGHT_CLICK);
		}});
		this.withdrawButton.addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent e) {
			if(testing){withdrawClicked();}
			notifyObservers(Config.WITHDRAW_CLICK);
		}});
		this.endTurnButton.addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent e) {
			if(testing){endTurnClicked();}
			notifyObservers(Config.END_TURN_CLICK);
		}});
		this.playCardButton.addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent e) {
			if(testing){playCardClicked();}
			notifyObservers(Config.PLAYEDCARD);
		}});
		this.playerCards[0].addActionListener(new ActionListener() {
			
		@Override
		public void actionPerformed(ActionEvent e) {
			lastCard = 0;	
			deck.setText(lastCard + "");
		}});
		this.playerCards[1].addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			lastCard = 1;
			deck.setText(lastCard + "");
		}});
		this.playerCards[2].addActionListener(new ActionListener() {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			lastCard = 2;	
			deck.setText(lastCard + "");
		}});
		this.playerCards[3].addActionListener(new ActionListener() {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			lastCard = 3;	
			deck.setText(lastCard + "");
		}});
		this.playerCards[4].addActionListener(new ActionListener() {
	
		@Override
		public void actionPerformed(ActionEvent e) {
			lastCard = 4;	
			deck.setText(lastCard + "");
		}});
		this.playerCards[5].addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			lastCard = 5;	
			deck.setText(lastCard + "");
		}});
		this.playerCards[6].addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			lastCard = 6;	
			deck.setText(lastCard + "");
		}});
		this.playerCards[7].addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			lastCard = 7;	
			deck.setText(lastCard + "");
		}});
		this.playerCards[8].addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			lastCard = 8;	
			deck.setText(lastCard + "");
		}});
		this.playerCards[9].addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			lastCard = 9;
			deck.setText(lastCard + "");
		}});
		this.playedCards[0].addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
		playedCardsClick(0);
		}});
		this.playedCards[1].addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			playedCardsClick(1);
		}});
		this.playedCards[2].addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			playedCardsClick(2);
		}});
		this.playedCards[3].addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			playedCardsClick(3);
		}});
		this.playedCards[4].addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			playedCardsClick(4);
		}});	
	}
 	
 	public void selectCard(int i){
 		lastCard=i;
 		
 	}
 	protected void playCardClicked() {
		this.textLabel.setText("played " + lastCard + "");
		//this.testLable.setText("played "+this.lastCard+"");
	}
 	

	public void withdrawClicked() {
		for(int i = 0; i < 5; i++){
			if(this.playerNames[i].isSelected())
				this.playerNames[i].setSelected(false);
		}
		this.endedTurn();
	}
	
 	public void endTurnClicked() {
		for(int i = 0; i < 5; i++){
			if(this.playerNames[i].isSelected())
				this.playerNames[i].setSelected(false);
		}
		this.endedTurn();
	}

	public void leftArrowClicked(String imageStr){
		for(int i = 9; i > 0;i--){
 			this.playerCards[i].setIcon(this.playerCards[i-1].getIcon());
 		}
 		this.playerCards[0].setIcon(ResourceLoader.loadImage(imageStr));
 		
 	}
 	public void rightArrowClicked(String imageStr){
 		for(int i = 0; i < 9; i++){
 			this.playerCards[i].setIcon(this.playerCards[i+1].getIcon());
 		}
 		this.playerCards[9].setIcon(ResourceLoader.loadImage(imageStr));	
 	}
 	
 	public void playedCardsClick(int i){
 		this.playedCard = i;
 		notifyObservers(Config.VIEWDISPLAY);
 	}
 	
 	public void addPlayerCard(int index, String imageStr){
 		this.playerCards[index].setIcon(ResourceLoader.loadImage(imageStr));
 	}
 	
 	public void addPlayedCard(int index, String imageStr){
 		this.playedCards[index].setIcon(ResourceLoader.loadImage(imageStr));
 	}
 	
	public void setup2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1360, 840);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 40, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{ 40, 40, 40, 40, 40, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel token11 = new JLabel("");
		token11.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token11 = new GridBagConstraints();
		gbc_token11.insets = new Insets(0, 0, 5, 5);
		gbc_token11.gridx = 5;
		gbc_token11.gridy = 1;
		getContentPane().add(token11, gbc_token11);
		
		JLabel token21 = new JLabel("");
		token21.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token21 = new GridBagConstraints();
		gbc_token21.insets = new Insets(0, 0, 5, 5);
		gbc_token21.gridx = 10;
		gbc_token21.gridy = 1;
		getContentPane().add(token21, gbc_token21);
		
		JLabel token31 = new JLabel("");
		token31.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token31 = new GridBagConstraints();
		gbc_token31.insets = new Insets(0, 0, 5, 5);
		gbc_token31.gridx = 15;
		gbc_token31.gridy = 1;
		getContentPane().add(token31, gbc_token31);
		
		JLabel token41 = new JLabel("");
		token41.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token41 = new GridBagConstraints();
		gbc_token41.insets = new Insets(0, 0, 5, 5);
		gbc_token41.gridx = 20;
		gbc_token41.gridy = 1;
		getContentPane().add(token41, gbc_token41);
		
		JLabel token51 = new JLabel("");
		token51.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token51 = new GridBagConstraints();
		gbc_token51.insets = new Insets(0, 0, 5, 5);
		gbc_token51.gridx = 25;
		gbc_token51.gridy = 1;
		getContentPane().add(token51, gbc_token51);
		
		JButton p1d = new JButton("");
		p1d.setIcon(ResourceLoader.loadImage(Config.IMG_BACK));
		GridBagConstraints gbc_p1d = new GridBagConstraints();
		gbc_p1d.gridheight = 3;
		gbc_p1d.gridwidth = 3;
		gbc_p1d.insets = new Insets(0, 0, 5, 5);
		gbc_p1d.gridx = 7;
		gbc_p1d.gridy = 2;
		getContentPane().add(p1d, gbc_p1d);
		
		JLabel token12 = new JLabel("");
		token12.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token12 = new GridBagConstraints();
		gbc_token12.insets = new Insets(0, 0, 5, 5);
		gbc_token12.gridx = 5;
		gbc_token12.gridy = 2;
		getContentPane().add(token12, gbc_token12);
		
		JLabel token22 = new JLabel("");
		token22.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token22 = new GridBagConstraints();
		gbc_token22.insets = new Insets(0, 0, 5, 5);
		gbc_token22.gridx = 10;
		gbc_token22.gridy = 2;
		getContentPane().add(token22, gbc_token22);
		
		JButton p2d = new JButton("");
		p2d.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_p2d = new GridBagConstraints();
		gbc_p2d.gridwidth = 3;
		gbc_p2d.gridheight = 3;
		gbc_p2d.insets = new Insets(0, 0, 5, 5);
		gbc_p2d.gridx = 12;
		gbc_p2d.gridy = 2;
		getContentPane().add(p2d, gbc_p2d);
		
		JLabel token32 = new JLabel("");
		token32.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token32 = new GridBagConstraints();
		gbc_token32.insets = new Insets(0, 0, 5, 5);
		gbc_token32.gridx = 15;
		gbc_token32.gridy = 2;
		getContentPane().add(token32, gbc_token32);
		
		JButton p3d = new JButton("");
		p3d.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_p3d = new GridBagConstraints();
		gbc_p3d.gridheight = 3;
		gbc_p3d.gridwidth = 3;
		gbc_p3d.insets = new Insets(0, 0, 5, 5);
		gbc_p3d.gridx = 17;
		gbc_p3d.gridy = 2;
		getContentPane().add(p3d, gbc_p3d);
		
		JLabel token42 = new JLabel("");
		token42.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token42 = new GridBagConstraints();
		gbc_token42.insets = new Insets(0, 0, 5, 5);
		gbc_token42.gridx = 20;
		gbc_token42.gridy = 2;
		getContentPane().add(token42, gbc_token42);
		
		JButton p4d = new JButton("");
		p4d.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_p4d = new GridBagConstraints();
		gbc_p4d.gridheight = 3;
		gbc_p4d.gridwidth = 3;
		gbc_p4d.insets = new Insets(0, 0, 5, 5);
		gbc_p4d.gridx = 22;
		gbc_p4d.gridy = 2;
		getContentPane().add(p4d, gbc_p4d);
		
		JLabel token52 = new JLabel("");
		token52.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token52 = new GridBagConstraints();
		gbc_token52.insets = new Insets(0, 0, 5, 5);
		gbc_token52.gridx = 25;
		gbc_token52.gridy = 2;
		getContentPane().add(token52, gbc_token52);
		
		JButton p5d = new JButton("");
		p5d.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_p5d = new GridBagConstraints();
		gbc_p5d.gridheight = 3;
		gbc_p5d.gridwidth = 3;
		gbc_p5d.insets = new Insets(0, 0, 5, 5);
		gbc_p5d.gridx = 27;
		gbc_p5d.gridy = 2;
		getContentPane().add(p5d, gbc_p5d);
		
		JLabel token13 = new JLabel("");
		token13.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token13 = new GridBagConstraints();
		gbc_token13.insets = new Insets(0, 0, 5, 5);
		gbc_token13.gridx = 5;
		gbc_token13.gridy = 3;
		getContentPane().add(token13, gbc_token13);
		
		JLabel token23 = new JLabel("");
		token23.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token23 = new GridBagConstraints();
		gbc_token23.insets = new Insets(0, 0, 5, 5);
		gbc_token23.gridx = 10;
		gbc_token23.gridy = 3;
		getContentPane().add(token23, gbc_token23);
		
		JLabel token33 = new JLabel("");
		//token33.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token33 = new GridBagConstraints();
		gbc_token33.insets = new Insets(0, 0, 5, 5);
		gbc_token33.gridx = 15;
		gbc_token33.gridy = 3;
		getContentPane().add(token33, gbc_token33);
		
		JLabel token43 = new JLabel("");
		//token43.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token43 = new GridBagConstraints();
		gbc_token43.insets = new Insets(0, 0, 5, 5);
		gbc_token43.gridx = 20;
		gbc_token43.gridy = 3;
		getContentPane().add(token43, gbc_token43);
		
		JLabel token53 = new JLabel("");
		//token53.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token53 = new GridBagConstraints();
		gbc_token53.insets = new Insets(0, 0, 5, 5);
		gbc_token53.gridx = 25;
		gbc_token53.gridy = 3;
		getContentPane().add(token53, gbc_token53);
		
		JLabel token14 = new JLabel("");
		//token14.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token14 = new GridBagConstraints();
		gbc_token14.insets = new Insets(0, 0, 5, 5);
		gbc_token14.gridx = 5;
		gbc_token14.gridy = 4;
		getContentPane().add(token14, gbc_token14);
		
		JLabel token24 = new JLabel("");
		//token24.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token24 = new GridBagConstraints();
		gbc_token24.insets = new Insets(0, 0, 5, 5);
		gbc_token24.gridx = 10;
		gbc_token24.gridy = 4;
		getContentPane().add(token24, gbc_token24);
		
		JLabel token34 = new JLabel("");
		//token34.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token34 = new GridBagConstraints();
		gbc_token34.insets = new Insets(0, 0, 5, 5);
		gbc_token34.gridx = 15;
		gbc_token34.gridy = 4;
		getContentPane().add(token34, gbc_token34);
		
		JLabel token44 = new JLabel("");
		//token44.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token44 = new GridBagConstraints();
		gbc_token44.insets = new Insets(0, 0, 5, 5);
		gbc_token44.gridx = 20;
		gbc_token44.gridy = 4;
		getContentPane().add(token44, gbc_token44);
		
		JLabel token54 = new JLabel("");
		//token54.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token54 = new GridBagConstraints();
		gbc_token54.insets = new Insets(0, 0, 5, 5);
		gbc_token54.gridx = 25;
		gbc_token54.gridy = 4;
		getContentPane().add(token54, gbc_token54);
		
		JLabel token15 = new JLabel("");
		token15.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token15 = new GridBagConstraints();
		gbc_token15.insets = new Insets(0, 0, 5, 5);
		gbc_token15.gridx = 5;
		gbc_token15.gridy = 5;
		getContentPane().add(token15, gbc_token15);
		
		JLabel token25 = new JLabel("");
		token25.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token25 = new GridBagConstraints();
		gbc_token25.insets = new Insets(0, 0, 5, 5);
		gbc_token25.gridx = 10;
		gbc_token25.gridy = 5;
		getContentPane().add(token25, gbc_token25);
		
		JLabel token35 = new JLabel("");
		token35.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token35 = new GridBagConstraints();
		gbc_token35.insets = new Insets(0, 0, 5, 5);
		gbc_token35.gridx = 15;
		gbc_token35.gridy = 5;
		getContentPane().add(token35, gbc_token35);
		
		JLabel token45 = new JLabel("");
		token45.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token45 = new GridBagConstraints();
		gbc_token45.insets = new Insets(0, 0, 5, 5);
		gbc_token45.gridx = 20;
		gbc_token45.gridy = 5;
		getContentPane().add(token45, gbc_token45);
		
		JLabel token55 = new JLabel("");
		token55.setIcon(ResourceLoader.loadImage(Config.PURPLE_EMPTY));
		GridBagConstraints gbc_token55 = new GridBagConstraints();
		gbc_token55.insets = new Insets(0, 0, 5, 5);
		gbc_token55.gridx = 25;
		gbc_token55.gridy = 5;
		getContentPane().add(token55, gbc_token55);
		
		JRadioButton name1 = new JRadioButton(Config.PLAYER_NAME);
		GridBagConstraints gbc_name1 = new GridBagConstraints();
		gbc_name1.gridwidth = 2;
		gbc_name1.insets = new Insets(0, 0, 5, 5);
		gbc_name1.gridx = 7;
		gbc_name1.gridy = 6;
		getContentPane().add(name1, gbc_name1);
		
		JRadioButton name2 = new JRadioButton(Config.PLAYER_NAME);
		GridBagConstraints gbc_name2 = new GridBagConstraints();
		gbc_name2.gridwidth = 2;
		gbc_name2.insets = new Insets(0, 0, 5, 5);
		gbc_name2.gridx = 12;
		gbc_name2.gridy = 6;
		getContentPane().add(name2, gbc_name2);
		
		JRadioButton name3 = new JRadioButton(Config.PLAYER_NAME);
		GridBagConstraints gbc_name3 = new GridBagConstraints();
		gbc_name3.gridwidth = 2;
		gbc_name3.insets = new Insets(0, 0, 5, 5);
		gbc_name3.gridx = 17;
		gbc_name3.gridy = 6;
		getContentPane().add(name3, gbc_name3);
		
		JRadioButton name4 = new JRadioButton(Config.PLAYER_NAME);
		GridBagConstraints gbc_name4 = new GridBagConstraints();
		gbc_name4.gridwidth = 2;
		gbc_name4.insets = new Insets(0, 0, 5, 5);
		gbc_name4.gridx = 22;
		gbc_name4.gridy = 6;
		getContentPane().add(name4, gbc_name4);
		
		JRadioButton name5 = new JRadioButton(Config.PLAYER_NAME);
		GridBagConstraints gbc_name5 = new GridBagConstraints();
		gbc_name5.gridwidth = 2;
		gbc_name5.insets = new Insets(0, 0, 5, 5);
		gbc_name5.gridx = 27;
		gbc_name5.gridy = 6;
		getContentPane().add(name5, gbc_name5);
		
		JLabel Deck = new JLabel("");
		Deck.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_Deck = new GridBagConstraints();
		gbc_Deck.gridheight = 3;
		gbc_Deck.gridwidth = 2;
		gbc_Deck.insets = new Insets(0, 0, 5, 5);
		gbc_Deck.gridx = 21;
		gbc_Deck.gridy = 10;
		getContentPane().add(Deck, gbc_Deck);
		
		JButton withdrawButton = new JButton("Withdraw");
		GridBagConstraints gbc_withdrawButton = new GridBagConstraints();
		gbc_withdrawButton.gridheight = 2;
		gbc_withdrawButton.gridwidth = 9;
		gbc_withdrawButton.insets = new Insets(0, 0, 5, 5);
		gbc_withdrawButton.gridx = 4;
		gbc_withdrawButton.gridy = 11;
		getContentPane().add(withdrawButton, gbc_withdrawButton);
		
		JLabel textLabel = new JLabel("game instructions");
		GridBagConstraints gbc_textLabel = new GridBagConstraints();
		gbc_textLabel.gridheight = 2;
		gbc_textLabel.gridwidth = 5;
		gbc_textLabel.insets = new Insets(0, 0, 5, 5);
		gbc_textLabel.gridx = 14;
		gbc_textLabel.gridy = 11;
		getContentPane().add(textLabel, gbc_textLabel);
		
		JButton playCardButton = new JButton("Play Card");
		GridBagConstraints gbc_playCardButton = new GridBagConstraints();
		gbc_playCardButton.gridheight = 2;
		gbc_playCardButton.gridwidth = 4;
		gbc_playCardButton.insets = new Insets(0, 0, 5, 5);
		gbc_playCardButton.gridx = 32;
		gbc_playCardButton.gridy = 11;
		getContentPane().add(playCardButton, gbc_playCardButton);
		
		JButton endTurnButton = new JButton("End Turn");
		GridBagConstraints gbc_endTurnButton = new GridBagConstraints();
		gbc_endTurnButton.gridheight = 2;
		gbc_endTurnButton.gridwidth = 5;
		gbc_endTurnButton.insets = new Insets(0, 0, 5, 5);
		gbc_endTurnButton.gridx = 25;
		gbc_endTurnButton.gridy = 11;
		getContentPane().add(endTurnButton, gbc_endTurnButton);
		

		
		JButton leftArrow = new JButton("");
		leftArrow.setIcon(ResourceLoader.loadImage(Config.ARROW_LEFT));
		GridBagConstraints gbc_leftArrow = new GridBagConstraints();
		gbc_leftArrow.gridheight = 3;
		gbc_leftArrow.gridwidth = 2;
		gbc_leftArrow.insets = new Insets(0, 0, 5, 5);
		gbc_leftArrow.gridx = 2;
		gbc_leftArrow.gridy = 15;
		getContentPane().add(leftArrow, gbc_leftArrow);
		
		JButton card1 = new JButton("");
		card1.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card1 = new GridBagConstraints();
		gbc_card1.gridheight = 3;
		gbc_card1.gridwidth = 3;
		gbc_card1.insets = new Insets(0, 0, 5, 5);
		gbc_card1.gridx = 4;
		gbc_card1.gridy = 15;
		getContentPane().add(card1, gbc_card1);
		
		JButton card2 = new JButton("");
		card2.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card2 = new GridBagConstraints();
		gbc_card2.gridheight = 3;
		gbc_card2.gridwidth = 3;
		gbc_card2.insets = new Insets(0, 0, 5, 5);
		gbc_card2.gridx = 7;
		gbc_card2.gridy = 15;
		getContentPane().add(card2, gbc_card2);
		
		JButton card3 = new JButton("");
		card3.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card3 = new GridBagConstraints();
		gbc_card3.gridheight = 3;
		gbc_card3.gridwidth = 3;
		gbc_card3.insets = new Insets(0, 0, 5, 5);
		gbc_card3.gridx = 10;
		gbc_card3.gridy = 15;
		getContentPane().add(card3, gbc_card3);
		
		JButton card4 = new JButton("");
		card4.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card4 = new GridBagConstraints();
		gbc_card4.gridheight = 3;
		gbc_card4.gridwidth = 3;
		gbc_card4.insets = new Insets(0, 0, 5, 5);
		gbc_card4.gridx = 13;
		gbc_card4.gridy = 15;
		getContentPane().add(card4, gbc_card4);
		
		JButton card5 = new JButton("");
		card5.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card5 = new GridBagConstraints();
		gbc_card5.gridheight = 3;
		gbc_card5.gridwidth = 3;
		gbc_card5.insets = new Insets(0, 0, 5, 5);
		gbc_card5.gridx = 16;
		gbc_card5.gridy = 15;
		getContentPane().add(card5, gbc_card5);
		
		JButton card6 = new JButton("");
		card6.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card6 = new GridBagConstraints();
		gbc_card6.gridheight = 3;
		gbc_card6.gridwidth = 3;
		gbc_card6.insets = new Insets(0, 0, 5, 5);
		gbc_card6.gridx = 19;
		gbc_card6.gridy = 15;
		getContentPane().add(card6, gbc_card6);
		
		JButton card7 = new JButton("");
		card7.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card7 = new GridBagConstraints();
		gbc_card7.gridheight = 3;
		gbc_card7.gridwidth = 2;
		gbc_card7.insets = new Insets(0, 0, 5, 5);
		gbc_card7.gridx = 22;
		gbc_card7.gridy = 15;
		getContentPane().add(card7, gbc_card7);
		
		JButton card8 = new JButton("");
		card8.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card8 = new GridBagConstraints();
		gbc_card8.gridwidth = 3;
		gbc_card8.gridheight = 3;
		gbc_card8.insets = new Insets(0, 0, 5, 5);
		gbc_card8.gridx = 24;
		gbc_card8.gridy = 15;
		getContentPane().add(card8, gbc_card8);
		
		JButton card9 = new JButton("");
		card9.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		GridBagConstraints gbc_card9 = new GridBagConstraints();
		gbc_card9.gridheight = 3;
		gbc_card9.gridwidth = 3;
		gbc_card9.insets = new Insets(0, 0, 5, 5);
		gbc_card9.gridx = 27;
		gbc_card9.gridy = 15;
		getContentPane().add(card9, gbc_card9);
		
		JButton card10 = new JButton("");
		GridBagConstraints gbc_card10 = new GridBagConstraints();
		gbc_card10.gridheight = 3;
		gbc_card10.gridwidth = 3;
		gbc_card10.insets = new Insets(0, 0, 5, 5);
		gbc_card10.gridx = 30;
		gbc_card10.gridy = 15;
		getContentPane().add(card10, gbc_card10);
		card10.setIcon(ResourceLoader.loadImage(Config.IMG_IVANHOE));
		
		JButton rightArrow = new JButton("");
		rightArrow.setIcon(ResourceLoader.loadImage(Config.ARROW_RIGHT));
		GridBagConstraints gbc_rightArrow = new GridBagConstraints();
		gbc_rightArrow.gridheight = 3;
		gbc_rightArrow.gridwidth = 2;
		gbc_rightArrow.insets = new Insets(0, 0, 5, 5);
		gbc_rightArrow.gridx = 33;
		gbc_rightArrow.gridy = 15;
		getContentPane().add(rightArrow, gbc_rightArrow);
		
		JLabel cardText = new JLabel("card text");
		GridBagConstraints gbc_cardText = new GridBagConstraints();
		gbc_cardText.gridheight = 2;
		gbc_cardText.gridwidth = 8;
		gbc_cardText.insets = new Insets(0, 0, 5, 5);
		gbc_cardText.gridx = 12;
		gbc_cardText.gridy = 18;
		getContentPane().add(cardText, gbc_cardText);
		
		JLabel points1 = new JLabel("0");
		GridBagConstraints gbc_points1 = new GridBagConstraints();
		gbc_points1.gridwidth = 2;
		gbc_points1.insets = new Insets(0, 0, 5, 5);
		gbc_points1.gridx = 7;
		gbc_points1.gridy = 7;
		getContentPane().add(points1, gbc_points1);
		
		JLabel points2 = new JLabel("0");
		GridBagConstraints gbc_points2 = new GridBagConstraints();
		gbc_points2.gridwidth = 2;
		gbc_points2.insets = new Insets(0, 0, 5, 5);
		gbc_points2.gridx = 12;
		gbc_points2.gridy = 7;
		getContentPane().add(points2, gbc_points2);
		
		JLabel points3 = new JLabel("0");
		GridBagConstraints gbc_points3 = new GridBagConstraints();
		gbc_points3.gridwidth = 2;
		gbc_points3.insets = new Insets(0, 0, 5, 5);
		gbc_points3.gridx = 17;
		gbc_points3.gridy = 7;
		getContentPane().add(points3, gbc_points3);
		
		JLabel points4 = new JLabel("0");
		GridBagConstraints gbc_points4 = new GridBagConstraints();
		gbc_points4.gridwidth = 2;
		gbc_points4.insets = new Insets(0, 0, 5, 5);
		gbc_points4.gridx = 22;
		gbc_points4.gridy = 7;
		getContentPane().add(points4, gbc_points4);
		
		JLabel points5 = new JLabel("0");
		GridBagConstraints gbc_points5 = new GridBagConstraints();
		gbc_points5.gridwidth = 2;
		gbc_points5.insets = new Insets(0, 0, 5, 5);
		gbc_points5.gridx = 27;
		gbc_points5.gridy = 7;
		getContentPane().add(points5, gbc_points5);
		
		//naming the buttons 
		this.withdrawButton = withdrawButton;
		this.deck = Deck;
		this.leftArrow = leftArrow;
		this.rightArrow = rightArrow;
		this.playCardButton = playCardButton;
		this.endTurnButton = endTurnButton;
		this.textLabel = textLabel;
		this.cardTextLabel = cardText;
		
		this.playerCards= new JButton[10];
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
		
		this.tokens = new JLabel[5][5];
		this.tokens[0][0] = token11;
		this.tokens[0][1] = token12;
		this.tokens[0][2] = token13;
		this.tokens[0][3] = token14;
		this.tokens[0][4] = token15;
		
		this.tokens[1][0] = token21;
		this.tokens[1][1] = token22;
		this.tokens[1][2] = token23;
		this.tokens[1][3] = token24;
		this.tokens[1][4] = token25;
		
		this.tokens[2][0] = token31;
		this.tokens[2][1] = token32;
		this.tokens[2][2] = token33;
		this.tokens[2][3] = token34;
		this.tokens[2][4] = token35;
		
		this.tokens[3][0] = token41;
		this.tokens[3][1] = token42;
		this.tokens[3][2] = token43;
		this.tokens[3][3] = token44;
		this.tokens[3][4] = token45;
		
		this.tokens[4][0] = token51;
		this.tokens[4][1] = token52;
		this.tokens[4][2] = token53;
		this.tokens[4][3] = token54;
		this.tokens[4][4] = token55;
		
		
		this.playerNames = new JRadioButton[5];
		this.playerNames[0] = name1;
		this.playerNames[1] = name2;
		this.playerNames[2] = name3;
		this.playerNames[3] = name4;
		this.playerNames[4] = name5;
		for(int i = 0; i < 5; i++){
			this.playerNames[i].setEnabled(false);
		}
		
		this.playedCards = new JButton[5];
		this.playedCards[0] = p1d;
		this.playedCards[1] = p2d;
		this.playedCards[2] = p3d;
		this.playedCards[3] = p4d;
		this.playedCards[4] = p5d;
		
		this.playerPoints = new JLabel[5];
		this.playerPoints[0] = points1;
		this.playerPoints[1] = points2;
		this.playerPoints[2] = points3;
		this.playerPoints[3] = points4;
		this.playerPoints[4] = points5;
	}

	public void setToken(int player,int token,String pic){
		this.tokens[player][token].setIcon(ResourceLoader.loadImage(pic));
		this.hasTokens[player][token]=true;
	}
	
	
	public void startTurn(){
		for (int i = 0; i < 10; i++){
			this.playerCards[i].setEnabled(true);
		}
		this.withdrawButton.setEnabled(true);
		this.endTurnButton.setEnabled(true);
		this.playCardButton.setEnabled(true);
	}

	public void endedTurn(){
		for (int i = 0; i < 10; i++){
			this.playerCards[i].setEnabled(false);
		}
		this.withdrawButton.setEnabled(false);
		this.endTurnButton.setEnabled(false);
		this.playCardButton.setEnabled(false);
	}
	
	
}
