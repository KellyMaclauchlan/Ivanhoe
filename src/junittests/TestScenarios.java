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
		System.out.println("@BeforeClass: TestScenarios");
	}
	
	@Before
	public void setUp(){
<<<<<<< HEAD
		System.out.println("@Before: TestGameEngine");
=======
		System.out.println("@Before: TestScenarios");
>>>>>>> 266aae073c495b1836e36d7b38aaacb2714a0e3d
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
    	game.addAllToDeck(player5.getCards());

	}
	
	@After
	public void tearDown(){
		System.out.println("@After: end game");
		game.resetPlayers();
	}
	
	/* Scenario Testing */
<<<<<<< HEAD
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
=======
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
    	
    	//player can only pick up an action card
    	game.setDrawDeck(player1Cards);
    	
    	game.setCurrentPlayer(player1);
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());	
		
		//starting tournament moves to the next player if the first cannot play
		processor.processStartTournament();
		
		//player 1 has no playable cards and has been automatically withdrawn
		assertNotEquals(player1.getName(), game.getCurrentPlayer().getName());	
		assertEquals(player2.getName(), game.getCurrentPlayer().getName());
	}
	
	@Test
	public void testTotalValue(){
		System.out.println("@Test: trying to play an insufficient number of cards to become the leader on my turn");
		
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
>>>>>>> 266aae073c495b1836e36d7b38aaacb2714a0e3d
    	assertTrue(game.getCurrentPlayer().getTotalCardValue() > player3.getTotalCardValue());
    	assertTrue(game.getCurrentPlayer().getTotalCardValue() > player4.getTotalCardValue());
    	assertTrue(game.getCurrentPlayer().getTotalCardValue() > player5.getTotalCardValue());
    	
<<<<<<< HEAD
    	//player 2 ends their turn
    	game.endTurn();
    	
    	//player 2 was automatically withdrawn
    	assertTrue(player1.hasWithdrawn());
    	
	}
	
	@Test
	public void testInvalidCards() {
=======
>>>>>>> 266aae073c495b1836e36d7b38aaacb2714a0e3d
		
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
		System.out.println("@Test: trying to play invalid cards");

		player1.chooseTournamentColour(Config.RED);
		game.setCurrentPlayer(player1);
		game.startTurn();
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		player1.addCard(new ActionCard(Config.UNHORSE));
		int players = game.getPlayers().size();
		assertEquals(5, players);

		assertEquals(Config.RED, game.getTournamentColour()); 
		
		//play wrong colour
		String unplayable = processor.processPlay("play blue 4");
		assertEquals("waiting " + Config.UNPLAYABLE, unplayable);
 
		//play wrong colour
		unplayable = processor.processPlay("play yellow 3");
		assertEquals("waiting " + Config.UNPLAYABLE, unplayable);
		

		//play wrong colour
		unplayable = processor.processPlay("play purple 5");
		assertEquals("waiting " + Config.UNPLAYABLE, unplayable);
		
		//unhorse only valid on purple tournament
		unplayable = processor.processPlay("play unhorse blue");
		assertEquals("waiting " + Config.UNPLAYABLE, unplayable);
		
		
		// no points accumulated
		assertEquals(0, player1.getTotalCardValue());
	}
	
	@Test
	public void testEndOfDeck() {
<<<<<<< HEAD
=======
		System.out.println("@Test: coming to end of the deck");

>>>>>>> 266aae073c495b1836e36d7b38aaacb2714a0e3d
		player1.chooseTournamentColour(Config.RED);
		game.setCurrentPlayer(player1);
		game.startTurn();
		
		//discard all cards from deck to discard pile
		ArrayList<Card> cardsToRemove = new ArrayList<>();
		for (Card c: game.getDrawDeck()) {
			game.discard(c);
			cardsToRemove.add(c);
		}
		game.removeAllFromDeck(cardsToRemove);
		
		//test that draw deck is empty
		assertTrue(game.getDrawDeck().isEmpty());
		
		//when player picks up card discard deck is reshuffled and put into drawdeck
		game.pickupCard();
		
		//test that drawdeck is no longer empty
		assertFalse(game.getDrawDeck().isEmpty());
	}
	

	@Test
	public void testChargeGreenOneCard() {
<<<<<<< HEAD
		
		
=======
		System.out.println("@Test: using 'Charge' in a green tournament with every player with only green 1s: one card must remain");

>>>>>>> 266aae073c495b1836e36d7b38aaacb2714a0e3d
		//player 4 plays 1 green 1
		player4.chooseTournamentColour(Config.GREEN);

		game.setCurrentPlayer(player4);
		game.startTurn();
		player4.addCard(new ColourCard(Config.GREEN, 1));
		game.playCard(player4.getCardFromHand(Config.GREEN, 1));
		game.endTurn();
		
		
		//player 2 plays 2 green 1's
		game.setCurrentPlayer(player2);
		game.startTurn();
		player2.addCard(new ColourCard(Config.GREEN, 1));
		game.playCard(player2.getCardFromHand(Config.GREEN, 1));
		game.playCard(player2.getCardFromHand(Config.GREEN, 1));
		game.endTurn();
		
		//player 5 plays 3 green 1's
		game.setCurrentPlayer(player5);
		game.startTurn();
		player5.addCard(new ColourCard(Config.GREEN, 1));
		game.playCard(player5.getCardFromHand(Config.GREEN, 1));
		game.playCard(player5.getCardFromHand(Config.GREEN, 1));
		game.playCard(player5.getCardFromHand(Config.GREEN, 1));
		game.endTurn();
		
		//player 3 plays 4 green 1's
		game.setCurrentPlayer(player3);
		game.startTurn();
		game.playCard(player3.getCardFromHand(Config.GREEN, 1));
		game.playCard(player3.getCardFromHand(Config.GREEN, 1));
		game.playCard(player3.getCardFromHand(Config.GREEN, 1));
		game.playCard(player3.getCardFromHand(Config.GREEN, 1));
		game.endTurn();
		

		game.setCurrentPlayer(player1);
		game.startTurn();
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		
		//player1 plays one green card
		player1.addCard(new ColourCard(Config.GREEN, 1));
		game.playCard(player1.getCardFromHand(Config.GREEN, 1));
		
		//check player current points
		assertEquals(1, player1.getDisplay().size());		
		assertEquals(2, player2.getDisplay().size());
		assertEquals(4, player3.getDisplay().size());		
		assertEquals(1, player4.getDisplay().size());
		assertEquals(3, player5.getDisplay().size());	
		
		//player1 plays charge
		player1.addCard(new ActionCard(Config.CHARGE));
		processor.processPlay("play charge");		

		//all players have 1 card each in their displays
		assertEquals(1, player1.getDisplay().size());		
		assertEquals(1, player2.getDisplay().size());
		assertEquals(1, player3.getDisplay().size());		
		assertEquals(1, player4.getDisplay().size());
		assertEquals(1, player5.getDisplay().size());	
		
		//all players have one point
		assertEquals(1, player1.getTotalCardValue());		
		assertEquals(1, player2.getTotalCardValue());
		assertEquals(1, player3.getTotalCardValue());		
		assertEquals(1, player4.getTotalCardValue());
		assertEquals(1, player5.getTotalCardValue());			
	}
	
	
	
	@Test
	public void testOneCardRemains() {
<<<<<<< HEAD
		
=======
		System.out.println("@Test: other example of overriding rule: at least one card must remain");

>>>>>>> 266aae073c495b1836e36d7b38aaacb2714a0e3d
		//player 2 has 1 card in their display
		player2.chooseTournamentColour(Config.YELLOW);
		game.setCurrentPlayer(player2);
		game.startTurn();
		game.playCard(player2.getCardFromHand(Config.YELLOW, 4));
		game.endTurn();
		
		//player 1 begins and attempts to play cards that take last card in display
		game.setCurrentPlayer(player1);
		game.startTurn();
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		ArrayList<Card> player1Cards = new ArrayList<>();
    	player1Cards.add(new ActionCard(Config.BREAKLANCE));
    	player1Cards.add(new ActionCard(Config.RIPOSTE));
    	player1Cards.add(new ActionCard(Config.DODGE));
    	player1Cards.add(new ActionCard(Config.RETREAT));
    	player1Cards.add(new ActionCard(Config.OUTMANEUVER));
    	player1Cards.add(new ActionCard(Config.CHARGE));
    	player1Cards.add(new ActionCard(Config.COUNTERCHARGE));
    	player1Cards.add(new ActionCard(Config.DISGRACE));
    	player1Cards.add(new ActionCard(Config.ADAPT));
    	player1Cards.add(new ColourCard(Config.YELLOW, 3));
    	player1.setCards(player1Cards);
    	game.removeAllFromDeck(player1Cards);

		assertEquals(Config.YELLOW, game.getTournamentColour()); 
		
		game.playCard(player1.getCardFromHand(Config.YELLOW, 3));
<<<<<<< HEAD
		
		//action cards that cannot be played with only one card in the display and return unplayable messages
		String unplayable = processor.processPlay("play breaklance " + player2.getName());
		assertEquals("waiting " + Config.UNPLAYABLE, unplayable);

		unplayable = processor.processPlay("play riposte " + player2.getName());
		assertEquals("waiting " + Config.UNPLAYABLE, unplayable);
		
		unplayable = processor.processPlay("play dodge " + player2.getName() + " yellow 4");
		assertEquals("waiting " + Config.UNPLAYABLE, unplayable);
		
		unplayable = processor.processPlay("play retreat yellow 3");
		assertEquals("waiting " + Config.UNPLAYABLE, unplayable);

		//action cards that cards do not return unplayable but does not change current scores
		unplayable = processor.processPlay("play outmaneuver");
		assertEquals(4, player2.getTotalCardValue());
		assertEquals(3, player1.getTotalCardValue());
		
=======
		
		//action cards that cannot be played with only one card in the display and return unplayable messages
		String unplayable = processor.processPlay("play breaklance " + player2.getName());
		assertEquals("waiting " + Config.UNPLAYABLE, unplayable);

		unplayable = processor.processPlay("play riposte " + player2.getName());
		assertEquals("waiting " + Config.UNPLAYABLE, unplayable);
		
		unplayable = processor.processPlay("play dodge " + player2.getName() + " yellow 4");
		assertEquals("waiting " + Config.UNPLAYABLE, unplayable);
		
		unplayable = processor.processPlay("play retreat yellow 3");
		assertEquals("waiting " + Config.UNPLAYABLE, unplayable);

		//action cards that cards do not return unplayable but does not change current scores
		unplayable = processor.processPlay("play outmaneuver");
		assertEquals(4, player2.getTotalCardValue());
		assertEquals(3, player1.getTotalCardValue());
		
>>>>>>> 266aae073c495b1836e36d7b38aaacb2714a0e3d
		unplayable = processor.processPlay("play charge");
		assertEquals(4, player2.getTotalCardValue());
		assertEquals(3, player1.getTotalCardValue());		

		unplayable = processor.processPlay("play countercharge");
		assertEquals(4, player2.getTotalCardValue());
		assertEquals(3, player1.getTotalCardValue());	
		
		unplayable = processor.processPlay("play disgrace");
		assertEquals(4, player2.getTotalCardValue());
		assertEquals(3, player1.getTotalCardValue());		

		unplayable = processor.processPlay("play adapt");
		assertEquals(4, player2.getTotalCardValue());
		assertEquals(3, player1.getTotalCardValue());	
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
