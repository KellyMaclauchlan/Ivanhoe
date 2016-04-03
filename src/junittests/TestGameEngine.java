package junittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import config.Config;
import game.ActionCard;
import game.Card;
import game.ColourCard;
import game.GameEngine;
import game.GameProcessor;
import game.Player;
import game.SupportCard;

public class TestGameEngine {
	GameEngine game;
	GameProcessor gameProcessor;
	Player player1;
	Player player2;
	Player player3;
	Player player4;
	Player player5;
	Card pickup = new ColourCard(Config.YELLOW, 4);

	@BeforeClass
	public static void BeforeClass(){
		System.out.println("@BeforeClass: TestGameEngine");
	}
	
	@Before
	public void setUp(){
		System.out.println("@Before: TestGameEngine");
		
		gameProcessor = new GameProcessor();
		game = gameProcessor.getGame();
		
		player1 = new Player("Katie");
		player2 = new Player("Brit");
		player3 = new Player("Kelly");
		player4 = new Player("Ahmed");
		player5 = new Player("Mir");
		
		game.joinGame(player1);
		assertTrue(game.getJoined());
		game.joinGame(player2);
		assertTrue(game.getJoined());
		game.joinGame(player3);
		assertTrue(game.getJoined());
		game.joinGame(player4);
		assertTrue(game.getJoined());
		game.joinGame(player5);
		assertTrue(game.getJoined());
		
		game.startGame();
	  	game.addAllToDeck(player1.getCards());
    	game.addAllToDeck(player2.getCards());
    	game.addAllToDeck(player3.getCards());
    	game.addAllToDeck(player4.getCards());
    	game.addAllToDeck(player5.getCards());
		
    	ArrayList<Card> player1Cards = new ArrayList<>();
    	player1Cards.add(new ColourCard(Config.RED, 3));
    	player1Cards.add(new ColourCard(Config.PURPLE, 5));
    	player1Cards.add(new ColourCard(Config.YELLOW, 3));
    	player1Cards.add(new ColourCard(Config.BLUE, 4));
    	player1Cards.add(new ColourCard(Config.YELLOW, 3));
    	player1Cards.add(new ColourCard(Config.YELLOW, 2));
    	player1Cards.add(new ActionCard(Config.RIPOSTE));
    	player1Cards.add(new SupportCard(Config.SQUIRE, 2));
    	player1.setCards(player1Cards);
    	game.removeAllFromDeck(player1Cards);
    	
    	ArrayList<Card> player2Cards = new ArrayList<>();
    	player2Cards.add(new ColourCard(Config.RED, 5));
    	player2Cards.add(new ColourCard(Config.GREEN, 1));
    	player2Cards.add(new ColourCard(Config.YELLOW, 4));
    	player2Cards.add(new ColourCard(Config.BLUE, 4));
    	player2Cards.add(new ColourCard(Config.GREEN, 1));
    	player2Cards.add(new ActionCard(Config.UNHORSE));
    	player2Cards.add(new SupportCard(Config.SQUIRE, 2));
    	player2Cards.add(new SupportCard(Config.SQUIRE, 3));
    	player2.setCards(player2Cards);
    	game.removeAllFromDeck(player2Cards);
    	
    	ArrayList<Card> player3Cards = new ArrayList<>();
    	player3Cards.add(new ColourCard(Config.GREEN, 1));
    	player3Cards.add(new ColourCard(Config.GREEN, 1));
    	player3Cards.add(new ColourCard(Config.YELLOW, 4));
    	player3Cards.add(new ColourCard(Config.BLUE, 4));
    	player3Cards.add(new ColourCard(Config.GREEN, 1));
    	player3Cards.add(new ColourCard(Config.YELLOW, 4));
    	player3Cards.add(new ColourCard(Config.BLUE, 4));
    	player3Cards.add(new ColourCard(Config.GREEN, 1));
    	player3.setCards(player3Cards);
    	game.removeAllFromDeck(player3Cards);
    	
    	ArrayList<Card> player4Cards = new ArrayList<>();
    	player4Cards.add(new ColourCard(Config.RED, 4));
    	player4Cards.add(new ColourCard(Config.RED, 3));
    	player4Cards.add(new ColourCard(Config.YELLOW, 4));
    	player4Cards.add(new ColourCard(Config.BLUE, 4));
    	player4Cards.add(new ColourCard(Config.GREEN, 1));
    	player4Cards.add(new ActionCard(Config.DROPWEAPON));
    	player4Cards.add(new ColourCard(Config.YELLOW, 4));
    	player4Cards.add(new ColourCard(Config.BLUE, 4));
    	player4.setCards(player4Cards);
    	game.removeAllFromDeck(player4Cards);
    	
    	
    	ArrayList<Card> player5Cards = new ArrayList<>();
    	player5Cards.add(new ColourCard(Config.RED, 4));
    	player5Cards.add(new SupportCard(Config.SQUIRE, 3));
    	player5Cards.add(new ColourCard(Config.GREEN, 1));
    	player5Cards.add(new ColourCard(Config.BLUE, 4));
    	player5Cards.add(new ColourCard(Config.GREEN, 1));
    	player5Cards.add(new SupportCard(Config.MAIDEN, 6));
    	player5Cards.add(new ColourCard(Config.YELLOW, 4));
    	player5Cards.add(new ColourCard(Config.BLUE, 4));
    	player5.setCards(player5Cards);
    	game.removeAllFromDeck(player5Cards);
	}
	
	@After
	public void tearDown(){
		System.out.println("@After: end game");
		game.resetPlayers();
	}
	
	@Test
	public void testStartTournament(){
		System.out.println("@Test: Start Tournament");
		game.startGame();
		gameProcessor.processStartTournament();
		assertTrue(game.getStart());
	}
	
	@Test
	public void testNumPlayers(){
		System.out.println("@Test: Checks that all players have joined correctly");
    	int players = game.getPlayers().size();
    	assertEquals(5, players);	
	}
	
	
	@Test
	public void testOrder(){
		System.out.println("@Test: check the order of players");
		
		//players have picked their tokens and have been arranged in the setup startGame() function;
		Player pickedPurple = null;
		for (Player p: game.getPlayers()) {
			if (p.getStartTokenColour().equals(Config.PURPLE)) {
				pickedPurple = p;
				break;
			}
		}
		assertTrue(pickedPurple.getStartTokenColour().equals(Config.PURPLE));
		
		game.startTurn();
		assertTrue(game.getCurrentPlayer() == game.getPlayers().get(0));
		game.endTurn();
		assertTrue(game.getCurrentPlayer() == game.getPlayers().get(1));
		game.endTurn();
		game.startTurn();
		assertTrue(game.getCurrentPlayer() == game.getPlayers().get(2));
		game.endTurn();
		game.startTurn();
		assertTrue(game.getCurrentPlayer() == game.getPlayers().get(3));
		game.endTurn();
		game.startTurn();
		assertTrue(game.getCurrentPlayer() == game.getPlayers().get(4));
		game.endTurn();
		// last player, moves back to first
		game.startTurn();
		assertTrue(game.getCurrentPlayer() == game.getPlayers().get(0));
		game.endTurn();
	}
	
	@Test
	public void testSupporters(){
		System.out.println("@Test: Check supporter cards");
		game.setCurrentPlayer(player5);
		Card toPlay = player5.getCardFromHand(Config.SQUIRE, 3);
		game.playCard(toPlay);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(3, toPlay.getValue());
	}
	
	
	/* Each tournament testing */
	@Test
	public void testOnePlayerStarts() {
		System.out.println("Test: One Player draws/starts, other draw but do not participate");
		/* Player 1 will set the tournament colour and play a card
		 * All other players will withdraw and player 1 will be the tournament winner
		 */ 
		
		player1.chooseTournamentColour(Config.RED);
		game.setCurrentPlayer(player1);
		game.startTurn();
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		
		int players = game.getPlayers().size();
		assertEquals(5, players);

		assertEquals(Config.RED, game.getTournamentColour()); 
		
		Card toPlay = player1.getCardFromHand(Config.RED, 3);
		game.playCard(toPlay);
		assertEquals(Config.RED, toPlay.getType());
		assertEquals(3, toPlay.getValue());
		
    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(3, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(0, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	game.endTurn();
    	// end of player 1's turn
    	
    	game.setCurrentPlayer(player2);
		game.startTurn();
		assertEquals(player2.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().setWithdrawn(true);
    	
    	assertEquals(0, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(3, player1.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	game.endTurn();
    	// end of player 2's turn 
    	
    	game.setCurrentPlayer(player3);
		game.startTurn();
		assertEquals(player3.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().setWithdrawn(true);
    	
    	assertEquals(0, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(3, player1.getTotalCardValue());
    	assertEquals(0, player2.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	game.endTurn();
    	// end of player 3's turn
    	
    	game.setCurrentPlayer(player4);
		game.startTurn();
		assertEquals(player4.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().setWithdrawn(true);
    	
    	assertEquals(0, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(3, player1.getTotalCardValue());
    	assertEquals(0, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	game.endTurn();
    	//end of player 4's turn
    	
    	game.setCurrentPlayer(player5);
		game.startTurn();
		assertEquals(player5.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().setWithdrawn(true);
    	
    	assertEquals(0, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(3, player1.getTotalCardValue());
    	assertEquals(0, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	
    	game.endTurn();
    	// end of player 5's turn
    	
    	// player 1 is the one player left in the tournament
    	game.setCurrentPlayer(player1);
		game.startTurn();
    	
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		
    	assertTrue(player1.isWinner());
    	assertFalse(player2.isWinner());
    	assertFalse(player3.isWinner());
    	assertFalse(player4.isWinner());
    	assertFalse(player5.isWinner());
	}
	
	@Test
	public void testSomePlayersParticipate(){
		System.out.println("Test: one player draws/starts, others draw and some participate by playing a card or several");

		// Player 1 and player 2 will play a few cards and player 2 will win the tournament
		player1.chooseTournamentColour(Config.YELLOW);
		game.setCurrentPlayer(player1);
		game.startTurn();
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		game.getCurrentPlayer().addCard(pickup);
		
		int players = game.getPlayers().size();
		assertEquals(5, players);

		assertEquals(Config.YELLOW, game.getTournamentColour()); 
		
		Card toPlay = player1.getCardFromHand(Config.YELLOW, 3);
		game.playCard(toPlay);
		assertEquals(Config.YELLOW, toPlay.getType());
		assertEquals(3, toPlay.getValue());
		
		toPlay = player1.getCardFromHand(Config.YELLOW, 3);
		game.playCard(toPlay);
		assertEquals(Config.YELLOW, toPlay.getType());
		assertEquals(3, toPlay.getValue());
		
    	assertEquals(6, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(0, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
		
    	pickup = new ColourCard(Config.GREEN, 1);
    	game.endTurn();
    	
    	//Start of player 2's turn
		game.setCurrentPlayer(player2);
		game.startTurn();
		assertEquals(player2.getName(), game.getCurrentPlayer().getName());
		game.getCurrentPlayer().addCard(pickup);
    	
		toPlay = player2.getCardFromHand(Config.YELLOW, 4);
		game.playCard(toPlay);
		assertEquals(Config.YELLOW, toPlay.getType());
		assertEquals(4, toPlay.getValue());
		
		toPlay = player2.getCardFromHand(Config.SQUIRE, 3);
		game.playCard(toPlay);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(3, toPlay.getValue());
		
    	assertEquals(7, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(6, player1.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new ColourCard(Config.BLUE, 4);
    	game.endTurn();
    	
    	//Start of player 3's turn 
    	game.setCurrentPlayer(player3);
		game.startTurn();
		assertEquals(player3.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().addCard(pickup);
    	game.getCurrentPlayer().setWithdrawn(true);
    	
    	assertEquals(0, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(6, player1.getTotalCardValue());
    	assertEquals(7, player2.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new ColourCard(Config.PURPLE, 7);
    	game.endTurn();
    	
    	// start of player 4's turn
    	game.setCurrentPlayer(player4);
		game.startTurn();
		assertEquals(player4.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().addCard(pickup);
    	game.getCurrentPlayer().setWithdrawn(true);
    	
    	assertEquals(0, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(6, player1.getTotalCardValue());
    	assertEquals(7, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new SupportCard(Config.MAIDEN, 6);
    	game.endTurn();
    	
    	// Start of player 5's turn
    	game.setCurrentPlayer(player5);
		game.startTurn();
		assertEquals(player5.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().addCard(pickup);
    	game.getCurrentPlayer().setWithdrawn(true);
    	
    	assertEquals(0, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(6, player1.getTotalCardValue());
    	assertEquals(7, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	
    	pickup = new SupportCard(Config.SQUIRE, 4);
    	game.endTurn();
    	// end of player 5's turn
    	
    	// back to player 1's turn
    	game.setCurrentPlayer(player1);
		game.startTurn();
    	game.getCurrentPlayer().addCard(pickup);
    	
		toPlay = player1.getCardFromHand(Config.SQUIRE, 4);
		game.playCard(toPlay);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(4, toPlay.getValue());
		
    	assertEquals(10, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(7, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new ColourCard(Config.BLUE, 4);
    	game.endTurn();
    	// end of player 1's turn
    	
    	game.setCurrentPlayer(player2);
    	game.startTurn();
    	game.getCurrentPlayer().addCard(pickup);
    	game.getCurrentPlayer().setWithdrawn(true);
    	
    	assertEquals(7, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(10, player1.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new ColourCard(Config.GREEN, 1);
    	game.endTurn();
    	
    	game.setCurrentPlayer(player1);
		game.startTurn();
    	game.getCurrentPlayer().addCard(pickup);
    	
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		
    	assertTrue(player1.isWinner());
    	assertFalse(player2.isWinner());
    	assertFalse(player3.isWinner());
    	assertFalse(player4.isWinner());
    	assertFalse(player5.isWinner());
	}
	
	@Test
	public void testStartingWithSupporters(){
		System.out.println("Test: starting with a supporter or several supporters");
		//genericCards();
		
		// Player 2 will start with supporters
		player2.chooseTournamentColour(Config.PURPLE);
		game.setCurrentPlayer(player2);
		game.startTurn();
		assertEquals(player2.getName(), game.getCurrentPlayer().getName());
		game.getCurrentPlayer().addCard(pickup);
		
		int players = game.getPlayers().size();
		assertEquals(5, players);

		assertEquals(Config.PURPLE, game.getTournamentColour()); 
		
		Card toPlay = player2.getCardFromHand(Config.SQUIRE, 2);
		game.playCard(toPlay);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(2, toPlay.getValue());
		
		toPlay = player2.getCardFromHand(Config.SQUIRE, 3);
		game.playCard(toPlay);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(3, toPlay.getValue());
		
    	assertEquals(5, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(5, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	game.endTurn();
    	
	}
	
	@Test
	public void testMultiplayerTournamentWithSupporters(){
		System.out.println("Test: Multiplayer tournament with multiple supporter cards played");
				
		// Player 1 and player 2 will play a few cards and player 2 will win the tournament
		player1.chooseTournamentColour(Config.YELLOW);
		game.setCurrentPlayer(player1);
		game.startTurn();
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		game.getCurrentPlayer().addCard(pickup);
		
		int players = game.getPlayers().size();
		assertEquals(5, players);

		assertEquals(Config.YELLOW, game.getTournamentColour()); 
		
		Card toPlay = player1.getCardFromHand(Config.SQUIRE, 2);
		game.playCard(toPlay);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(2, toPlay.getValue());
		
		toPlay = player1.getCardFromHand(Config.YELLOW, 3);
		game.playCard(toPlay);
		assertEquals(Config.YELLOW, toPlay.getType());
		assertEquals(3, toPlay.getValue());
		
    	assertEquals(5, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(0, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
		
    	pickup = new ColourCard(Config.GREEN, 1);
    	game.endTurn();
    	
    	//Start of player 2's turn
		game.setCurrentPlayer(player2);
		game.startTurn();
		assertEquals(player2.getName(), game.getCurrentPlayer().getName());
		game.getCurrentPlayer().addCard(pickup);
    	
		toPlay = player2.getCardFromHand(Config.SQUIRE, 2);
		game.playCard(toPlay);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(2, toPlay.getValue());
		
		toPlay = player2.getCardFromHand(Config.SQUIRE, 3);
		game.playCard(toPlay);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(3, toPlay.getValue());
		
    	assertEquals(5, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(5, player1.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new SupportCard(Config.SQUIRE, 3);
    	game.endTurn();
    	
    	//Start of player 3's turn 
    	game.setCurrentPlayer(player3);
		game.startTurn();
		assertEquals(player3.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().addCard(pickup);
		
    	toPlay = player3.getCardFromHand(Config.SQUIRE, 3);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(3, toPlay.getValue());
		game.playCard(toPlay);
		game.endTurn();
    	
    	assertEquals(5, player1.getTotalCardValue());
    	assertEquals(5, player2.getTotalCardValue());
    	assertEquals(3, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new ColourCard(Config.SQUIRE, 3);
    	game.endTurn();
    	
    	// start of player 4's turn
    	game.setCurrentPlayer(player4);
		game.startTurn();
		assertEquals(player4.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().addCard(pickup);
    	
    	toPlay = player4.getCardFromHand(Config.SQUIRE, 3);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(3, toPlay.getValue());
		game.playCard(toPlay);
		
		game.endTurn();    	
    	
    	assertEquals(3, player4.getTotalCardValue());
    	assertEquals(5, player1.getTotalCardValue());
    	assertEquals(5, player2.getTotalCardValue());
    	assertEquals(3, player3.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new SupportCard(Config.SQUIRE, 2);
    	game.endTurn();
    	
    	// Start of player 5's turn
    	game.setCurrentPlayer(player5);
		game.startTurn();
		assertEquals(player5.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().addCard(pickup);
    	
    	toPlay = player5.getCardFromHand(Config.SQUIRE, 3);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(3, toPlay.getValue());
		game.playCard(toPlay);
		
    	toPlay = player5.getCardFromHand(Config.MAIDEN, 6);
		assertEquals(Config.MAIDEN, toPlay.getType());
		assertEquals(6, toPlay.getValue());
		game.playCard(toPlay);
		
    	assertEquals(9, player5.getTotalCardValue());
    	assertEquals(5, player1.getTotalCardValue());
    	assertEquals(5, player2.getTotalCardValue());
    	assertEquals(3, player3.getTotalCardValue());
    	assertEquals(3, player4.getTotalCardValue());
    	
    	pickup = new SupportCard(Config.SQUIRE, 4);
    	game.endTurn();
    	// end of player 5's turn
    	
    	// back to player 1's turn
    	game.setCurrentPlayer(player1);
		game.startTurn();
    	game.getCurrentPlayer().addCard(pickup);
    	
		toPlay = player1.getCardFromHand(Config.SQUIRE, 4);
		game.playCard(toPlay);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(4, toPlay.getValue());
		
    	assertEquals(9, player1.getTotalCardValue());
    	assertEquals(5, player2.getTotalCardValue());
    	assertEquals(3, player3.getTotalCardValue());
    	assertEquals(3, player4.getTotalCardValue());
    	assertEquals(9, player5.getTotalCardValue());
    	
    	pickup = new ColourCard(Config.BLUE, 4);
    	game.endTurn();
    	// end of player 1's turn
 
	}

	@Test
	public void testRedTournament(){
		System.out.println("@Test: testing red tournament");
		player1.chooseTournamentColour(Config.RED);
		game.setCurrentPlayer(player1);
		game.startTurn();
		assertEquals(Config.RED, game.getTournamentColour());
	}
	
	@Test
	public void testYellowTournament(){
		System.out.println("@Test: testing yellow tournament");
		player1.chooseTournamentColour(Config.YELLOW);
		game.setCurrentPlayer(player1);
		game.startTurn();
		assertEquals(Config.YELLOW, game.getTournamentColour());
	}

	@Test
	public void testBlueTournament(){
		System.out.println("@Test: testing blue tournament");
		player3.chooseTournamentColour(Config.BLUE);
		game.setCurrentPlayer(player3);
		game.startTurn();
		assertEquals(Config.BLUE, game.getTournamentColour());
	}
	
	@Test
	public void testGreenTournament(){
		System.out.println("@Test: testing green tournament");
		player4.chooseTournamentColour(Config.GREEN);
		game.setCurrentPlayer(player4);
		game.startTurn();
		assertEquals(Config.GREEN, game.getTournamentColour());
	}
	
	@Test
	public void testPurpleTournament(){
		System.out.println("@Test: testing purple tournament");
		player5.chooseTournamentColour(Config.PURPLE);
		game.setCurrentPlayer(player5);
		game.startTurn();
		assertEquals(Config.PURPLE, game.getTournamentColour());
	}
	
	@Test
	public void testMaiden(){
		System.out.println("@Test: Restriction to 1 Maiden per player per tournament");
    	player5.addCard(new SupportCard(Config.MAIDEN, 6));
		player5.chooseTournamentColour(Config.BLUE);
    	game.setCurrentPlayer(player5);
    	game.startTurn();
    	String maiden1 = gameProcessor.processPlay("play maiden 6");
    	assertEquals("waiting maiden_6", maiden1);
    	String maiden2 = gameProcessor.processPlay("play maiden 6");
    	assertEquals(Config.WAITING + " " + Config.UNPLAYABLE, maiden2);
    	int numberOfMaidens = 0;
    	for (Card c: player5.getDisplay()) {
    		if (c.getType().equals(Config.MAIDEN)) {
    			numberOfMaidens ++;
    		}
    	}
    	assertEquals(1, numberOfMaidens);
	}
	
	@Test
	public void testWonPurpleTournament(){
		System.out.println("@Test: won a purple tournament choose token colour");
		player2.chooseTournamentColour(Config.PURPLE);
		assertFalse(player1.getCurrentTokens().contains(Config.RED));
    	game.setCurrentPlayer(player2);
    	game.startTurn();
		game.withdraw();
		game.endTurn();
		game.startTurn();
		game.withdraw();
		game.endTurn();
		game.startTurn();
		game.withdraw();
		game.endTurn();
		game.startTurn();
		game.withdraw();
		String purpleWin = gameProcessor.processEndTurn();
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		
		assertEquals(player5.getName() + " points " + player5.getTotalCardValue() + " " + Config.WITHDRAW 
				+ " " + player1.getName() + " " + Config.PURPLE_WIN + " " + game.getCurrentPlayer().getName(), purpleWin);
		String processPurpleWin = gameProcessor.processPurpleWin(Config.PURPLE_WIN + " " + Config.RED);
		assertEquals(player1.getName() + " points " + player1.getTotalCardValue() + " " + Config.WITHDRAW 
				+ " " + player2.getName() + " " + game.getTournamentColour() + " " + Config.TOURNAMENT_WINNER 
				+ " " + player1.getName(), processPurpleWin);
		assertTrue(player1.getCurrentTokens().contains(Config.RED));
		
    	
	}
	
	@Test
	public void testLoseOnMaiden(){
		System.out.println("@Test: losing with a maiden and losing a token");
		player2.addToken(Config.RED);
		player2.addCard(new SupportCard(Config.MAIDEN, 6));
		player2.chooseTournamentColour(Config.YELLOW);
		assertTrue(player2.getCurrentTokens().contains(Config.RED));
    	game.setCurrentPlayer(player2);
    	game.startTurn();
    	gameProcessor.processPlay("play maiden 6");
    	String withdraw = gameProcessor.processWithdraw(Config.WITHDRAW);
    	assertEquals(Config.MAIDEN, withdraw);
    	withdraw = gameProcessor.processWithdraw("maiden red");
    	assertEquals("maiden red", withdraw);
    	assertFalse(player2.getCurrentTokens().contains(Config.RED));
	}
}
