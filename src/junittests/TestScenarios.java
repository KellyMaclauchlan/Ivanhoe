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
import game.Player;
import game.SupportCard;

public class TestScenarios {
	/* Game Logic Testing without networking
	 * Testing for each type of play during a tournament
	 */
	GameEngine game;
	Player player1;
	Player player2;
	Player player3;
	Player player4;
	Player player5;
	
	private Card pickup = new ColourCard("yellow", 4);
	
	@BeforeClass
	public static void BeforeClass(){
		System.out.println("@BeforeClass: TestGameEngine");
	}
	
	@Before
	public void setUp(){
		System.out.println("@Before: TestGameEngine");
		
		game = new GameEngine();
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

	}
	
	public void genericCards(){
	   	ArrayList<Card> player1Cards = new ArrayList<>();
    	player1Cards.add(new ColourCard("red", 3));
    	player1Cards.add(new ColourCard("purple", 5));
    	player1Cards.add(new ColourCard("yellow", 3));
    	player1Cards.add(new ColourCard("blue", 4));
    	player1Cards.add(new ColourCard("yellow", 3));
    	player1Cards.add(new ColourCard("yellow", 3));
    	player1Cards.add(new ActionCard("riposte"));
    	player1Cards.add(new SupportCard("squire", 2));
    	player1.setCards(player1Cards);
    	game.removeAllFromDeck(player1Cards);
    	
    	ArrayList<Card> player2Cards = new ArrayList<>();
    	player2Cards.add(new ColourCard("red", 5));
    	player2Cards.add(new ColourCard("green", 1));
    	player2Cards.add(new ColourCard("yellow", 4));
    	player2Cards.add(new ColourCard("blue", 4));
    	player2Cards.add(new ColourCard("green", 1));
    	player2Cards.add(new ActionCard("unhorse"));
    	player2Cards.add(new SupportCard("squire", 2));
    	player2Cards.add(new SupportCard("squire", 3));
    	player2.setCards(player2Cards);
    	game.removeAllFromDeck(player2Cards);
    	
    	ArrayList<Card> player3Cards = new ArrayList<>();
    	player3Cards.add(new ColourCard("green", 1));
    	player3Cards.add(new ColourCard("green", 1));
    	player3Cards.add(new ColourCard("yellow", 4));
    	player3Cards.add(new ColourCard("blue", 4));
    	player3Cards.add(new ColourCard("green", 1));
    	player3Cards.add(new ColourCard("yellow", 4));
    	player3Cards.add(new ColourCard("blue", 4));
    	player3Cards.add(new ColourCard("green", 1));
    	player3.setCards(player3Cards);
    	game.removeAllFromDeck(player3Cards);
    	
    	ArrayList<Card> player4Cards = new ArrayList<>();
    	player4Cards.add(new ColourCard("red", 4));
    	player4Cards.add(new ColourCard("red", 3));
    	player4Cards.add(new ColourCard("yellow", 4));
    	player4Cards.add(new ColourCard("blue", 4));
    	player4Cards.add(new ColourCard("green", 1));
    	player4Cards.add(new ActionCard("drop weapon"));
    	player4Cards.add(new ColourCard("yellow", 4));
    	player4Cards.add(new ColourCard("blue", 4));
    	player4.setCards(player4Cards);
    	game.removeAllFromDeck(player4Cards);
    	
    	
    	ArrayList<Card> player5Cards = new ArrayList<>();
    	player5Cards.add(new ColourCard("red", 4));
    	player5Cards.add(new SupportCard("squire", 3));
    	player5Cards.add(new ColourCard("green", 1));
    	player5Cards.add(new ColourCard("blue", 4));
    	player5Cards.add(new ColourCard("green", 1));
    	player5Cards.add(new SupportCard("maiden", 6));
    	player5Cards.add(new ColourCard("yellow", 4));
    	player5Cards.add(new ColourCard("blue", 4));
    	player5.setCards(player5Cards);
    	game.removeAllFromDeck(player5Cards);
    	
	  	game.addAllToDeck(player1.getCards());
    	game.addAllToDeck(player2.getCards());
    	game.addAllToDeck(player3.getCards());
    	game.addAllToDeck(player4.getCards());
	}
	
	@After
	public void tearDown(){
		System.out.println("@After: end game");
		game.resetPlayers();
	}
	
	@Test
	public void testScenario1() {
		System.out.println("Test: One Player draws/starts, other draw but do not participate");
		
		// gives each of the players cards 
		genericCards();
		
		/* Player 1 will set the tournament colour and play a card
		 * All other players will withdraw and player 1 will be the tournament winner
		 */ 
		
		player1.chooseTournamentColour(Config.RED);
		game.setCurrentPlayer(player1);
		game.startTurn();
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		game.getCurrentPlayer().addCard(pickup);
		
		//int players = game.getNumPlayers();
		int players = game.getPlayers().size();
		assertEquals(5, players);

		assertEquals(Config.RED, game.getTournamentColour()); 
		
		Card toPlay = game.getCurrentPlayer().getCards().get(0);
		game.playCard(toPlay);
		assertEquals(Config.RED, toPlay.getType());
		assertEquals(3, toPlay.getValue());
		
    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(3, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(0, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new ColourCard(Config.GREEN, 1);
    	game.endTurn();
    	// end of player 1's turn
    	
    	game.setCurrentPlayer(player2);
		game.startTurn();
		assertEquals(player2.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().addCard(pickup);
    	game.getCurrentPlayer().setWithdrawn(true);
    	
    	assertEquals(0, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(3, player1.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new SupportCard(Config.SQUIRE, 3);
    	game.endTurn();
    	// end of player 2's turn 
    	
    	game.setCurrentPlayer(player3);
		game.startTurn();
		assertEquals(player3.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().addCard(pickup);
    	game.getCurrentPlayer().setWithdrawn(true);
    	
    	assertEquals(0, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(3, player1.getTotalCardValue());
    	assertEquals(0, player2.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new ColourCard(Config.PURPLE, 7);
    	game.endTurn();
    	// end of player 3's turn
    	
    	game.setCurrentPlayer(player4);
		game.startTurn();
		assertEquals(player4.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().addCard(pickup);
    	game.getCurrentPlayer().setWithdrawn(true);
    	
    	assertEquals(0, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(3, player1.getTotalCardValue());
    	assertEquals(0, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new SupportCard(Config.MAIDEN, 6);
    	game.endTurn();
    	//end of player 4's turn
    	
    	game.setCurrentPlayer(player5);
		game.startTurn();
		assertEquals(player5.getName(), game.getCurrentPlayer().getName());
    	game.getCurrentPlayer().addCard(pickup);
    	game.getCurrentPlayer().setWithdrawn(true);
    	
    	assertEquals(0, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(3, player1.getTotalCardValue());
    	assertEquals(0, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	
    	pickup = new ColourCard(Config.YELLOW, 2);
    	game.endTurn();
    	// end of player 5's turn
    	
    	// player 1 is the one player left in the tournament
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
	public void testScenario2(){
		System.out.println("Test: one player draws/starts, others draw and some participate by playing a card or several");
		
		genericCards();
		
		// Player 1 and player 2 will play a few cards and player 2 will win the tournament
		player1.chooseTournamentColour(Config.YELLOW);
		game.setCurrentPlayer(player1);
		game.startTurn();
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		game.getCurrentPlayer().addCard(pickup);
		
		int players = game.getPlayers().size();
		assertEquals(5, players);

		assertEquals(Config.YELLOW, game.getTournamentColour()); 
		
		Card toPlay = game.getCurrentPlayer().getCards().get(2);
		game.playCard(toPlay);
		assertEquals(Config.YELLOW, toPlay.getType());
		assertEquals(3, toPlay.getValue());
		
		toPlay = game.getCurrentPlayer().getCards().get(4);
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
    	
		toPlay = game.getCurrentPlayer().getCards().get(2);
		game.playCard(toPlay);
		assertEquals(Config.YELLOW, toPlay.getType());
		assertEquals(4, toPlay.getValue());
		
		toPlay = game.getCurrentPlayer().getCards().get(6);
		game.playCard(toPlay);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(3, toPlay.getValue());
		
    	assertEquals(7, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(6, player1.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new SupportCard(Config.BLUE, 4);
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
    	
    	// start of player 3's turn
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
    	
    	pickup = new ColourCard(Config.SQUIRE, 4);
    	game.endTurn();
    	// end of player 5's turn
    	
    	// back to player 1's turn
    	game.setCurrentPlayer(player1);
		game.startTurn();
    	game.getCurrentPlayer().addCard(pickup);
    	
		toPlay = game.getCurrentPlayer().getCards().get(7);
		game.playCard(toPlay);
		assertEquals(Config.SQUIRE, toPlay.getType());
		assertEquals(4, toPlay.getValue());
		
    	assertEquals(10, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(7, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	pickup = new SupportCard(Config.BLUE, 4);
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
	
	//@Test
	public void testScenario3(){
		System.out.println("Test: starting with a supporter or several supporters");
	}
	
	//@Test
	public void testScenario4(){
		System.out.println("Test: Multiplayer tournament with multiple supporter cards played");
	}
}
