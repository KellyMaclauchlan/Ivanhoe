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

public class TestGameEngine {
	GameEngine game;
	Player player1;
	Player player2;
	Player player3;
	Player player4;
	Player player5;
	
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
	  	game.addAllToDeck(player1.getCards());
    	game.addAllToDeck(player2.getCards());
    	game.addAllToDeck(player3.getCards());
    	game.addAllToDeck(player4.getCards());
		
    	ArrayList<Card> player1Cards = new ArrayList<>();
    	player1Cards.add(new ColourCard("red", 3));
    	player1Cards.add(new ColourCard("purple", 5));
    	player1Cards.add(new ColourCard("yellow", 3));
    	player1Cards.add(new ColourCard("blue", 4));
    	player1Cards.add(new ColourCard("yellow", 3));
    	player1Cards.add(new ColourCard("yellow", 2));
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
		game.processInput(Config.START_TOURNAMENT);
		assertTrue(game.getStart());
	}
	
	@Test
	public void testNumPlayers(){
		System.out.println("@Test: Checks that all players have joined correctly");
    	int players = game.getPlayers().size();
    	assertEquals(5, players);	
	}
	
	@Test
	public void testNumCards(){
		System.out.println("@Test: checks that 110 cards are being used");
		game.createDeck();
		assertEquals(110, game.getDrawDeck().size());
	}
	
	
	//@Test
	public void testOrder(){
		System.out.println("@Test: check the order of players");
	}
	
	@Test
	public void testSupporters(){
		System.out.println("@Test: Check supporter cards");
		
		game.setCurrentPlayer(player5);
		Card toPlay = game.getCurrentPlayer().getCards().get(1);
		game.playCard(toPlay);
		assertEquals("squire", toPlay.getType());
		assertEquals(3, toPlay.getValue());
	}
	
	@Test
	public void testTotalValue(){
		System.out.println("@Test: tests that the current player has the largest total value or withdraws");
		
		game.setCurrentPlayer(player2);
		Card toPlay = game.getCurrentPlayer().getCards().get(0);
    	game.playCard(toPlay);
    	assertEquals("red", toPlay.getType());
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
	}
	
	@Test
	public void testWinnerToken(){
		System.out.println("@Test: the winner of a tournament gets a token");
		// Player 1 will win this tournament
		
		player1.chooseTournamentColour(Config.RED);
		game.setCurrentPlayer(player1);

		player2.setWithdrawn(true);
		player3.setWithdrawn(true);
		player4.setWithdrawn(true);
		player5.setWithdrawn(true);
		
		game.startTurn();

		assertTrue(player1.isWinner());
		// Error is right here... saying everyone is a winner
		assertFalse(player2.isWinner());
		assertFalse(player3.isWinner());
		assertFalse(player4.isWinner());
		assertFalse(player5.isWinner());
		
		assertTrue(game.getCurrentPlayer().getCurrentTokens().contains(game.getTournamentColour()));
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
}
