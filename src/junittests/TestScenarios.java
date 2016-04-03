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

public class TestScenarios {
	/* Game Logic Testing without networking
	 * Testing for each type of play during a tournament
	 */
	GameEngine game;
	GameProcessor processor;
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
		processor = new GameProcessor();
		game = processor.getGame();
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
		genericCards();

	}
	
	public void genericCards(){
	   	ArrayList<Card> player1Cards = new ArrayList<>();
    	player1Cards.add(new ColourCard(Config.RED, 3));
    	player1Cards.add(new ColourCard(Config.PURPLE, 5));
    	player1Cards.add(new ColourCard(Config.YELLOW, 3));
    	player1Cards.add(new ColourCard(Config.BLUE, 4));
    	player1Cards.add(new ColourCard(Config.YELLOW, 3));
    	player1Cards.add(new ColourCard(Config.YELLOW, 3));
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
	
	/* Scenario Testing */
	@Test
	public void testPlayerCannotStart() {
		System.out.println("Test: Starting player cannot start tournament");
	   	//give player1 all action cards
		ArrayList<Card> player1Cards = new ArrayList<>();
    	player1Cards.add(new ActionCard(Config.UNHORSE));
    	player1Cards.add(new ActionCard(Config.BREAKLANCE));
    	player1Cards.add(new ActionCard(Config.RIPOSTE));
    	player1Cards.add(new ActionCard(Config.CHANGEWEAPON));
    	player1Cards.add(new ActionCard(Config.DODGE));
    	player1Cards.add(new ActionCard(Config.OUTWIT));
    	player1Cards.add(new ActionCard(Config.RIPOSTE));
    	player1Cards.add(new ActionCard(Config.STUNNED));
    	player1.setCards(player1Cards);
    	game.removeAllFromDeck(player1Cards);
    	
    	game.setTournamentColour(Config.RED);
    	game.setCurrentPlayer(player1);
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());		
		game.startTurn();
		
		//player 1 has no playable cards and has been automatically withdrawn
		assertTrue(player1.hasWithdrawn());
	}
	

	
	@Test
	public void testTotalValue(){
		System.out.println("@Test: tests that the current player has the largest total value or withdraws");
		
		game.setCurrentPlayer(player2);
		Card toPlay = player2.getCardFromHand(Config.RED, 5);
    	game.playCard(toPlay);
    	assertEquals(Config.RED, toPlay.getType());
    	assertEquals(5, toPlay.getValue());
    	
    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(5, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(0, player1.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player5.getTotalCardValue());
    	
    	assertTrue(game.getCurrentPlayer().getTotalCardValue() > player1.getTotalCardValue());
    	assertTrue(game.getCurrentPlayer().getTotalCardValue() > player3.getTotalCardValue());
    	assertTrue(game.getCurrentPlayer().getTotalCardValue() > player4.getTotalCardValue());
    	assertTrue(game.getCurrentPlayer().getTotalCardValue() > player5.getTotalCardValue());
    	
		
		game.setCurrentPlayer(player1);
		toPlay = player1.getCardFromHand(Config.RED, 3);
    	game.playCard(toPlay);
    	assertEquals(Config.RED, toPlay.getType());
    	assertEquals(3, toPlay.getValue());
    	
    	// player 2 does not have more points than player 1
    	assertFalse(game.getCurrentPlayer().getTotalCardValue() > player1.getTotalCardValue());
    	assertTrue(game.getCurrentPlayer().getTotalCardValue() > player3.getTotalCardValue());
    	assertTrue(game.getCurrentPlayer().getTotalCardValue() > player4.getTotalCardValue());
    	assertTrue(game.getCurrentPlayer().getTotalCardValue() > player5.getTotalCardValue());
    	
    	//player 2 ends their turn
    	game.endTurn();
    	
    	//player 2 was automatically withdrawn
    	assertTrue(player1.hasWithdrawn());
    	
	}
	
	@Test
	public void testInvalidCards() {
		
		player1.chooseTournamentColour(Config.RED);
		game.setCurrentPlayer(player1);
		game.startTurn();
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		
		int players = game.getPlayers().size();
		assertEquals(5, players);

		assertEquals(Config.RED, game.getTournamentColour()); 
		
		String unplayable = processor.processPlay("play blue 4");
		assertEquals("waiting " + Config.UNPLAYABLE, unplayable);
 
		//TO DO: Check a bunch of unplayable shit
	}
	
	public void testEndOfDeck() {
		//TO DO
	}
	
	public void chargeOneCardRemains() {
		//TO DO
	}
	
	public void oneCardRemains1() {
		//TO DO
	}
	
	public void oneCardRemains2() {
		//TO DO
	}
	
	@Test
	public void testWinnerToken(){
		System.out.println("@Test: the winner of a tournament gets a token");
		// Player 1 will win this tournament
		
		player1.chooseTournamentColour(Config.RED);
		game.setCurrentPlayer(player1);
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
		game.endTurn();
		game.startTurn();

		assertTrue(player5.isWinner());
		assertFalse(player1.isWinner());
		assertFalse(player2.isWinner());
		assertFalse(player3.isWinner());
		assertFalse(player4.isWinner());
		
		assertTrue(game.getCurrentPlayer().getCurrentTokens().contains(game.getTournamentColour()));
	}
	
	@Test
	public void testNumCards(){
		System.out.println("@Test: checks that 110 cards are being used");
		game.createDeck();
		assertEquals(110, game.getDrawDeck().size());
	}
	
}
