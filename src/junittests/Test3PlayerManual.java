package junittests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;


import game.ActionCard;
import game.Card;
import game.ColourCard;
import game.GameEngine;
import game.Player;
import game.SupportCard;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class Test3PlayerManual {
	static GameEngine game;
	static int testNumber = 0;
	static Player player1;
	static Player player2;
	static Player player3;

	//Set the card at the top of the deck, which will be picked up by the next player
	private Card topCard = new ColourCard("red", 3);

	@BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: Setting up tests for a manually set 3 player game, dealing cards, selecting tokens");
        //These tests set the hands and played cards manually to test specific scenarios
        
		game = new GameEngine();
		
    	game.startGame();
    	
    	//add 2 players to the game
    	player1 = new Player("Katie");
    	player2 = new Player("Kelly");
    	player3 = new Player("Brit");
    	game.joinGame(player1);
    	game.joinGame(player2);
    	game.joinGame(player3);
    	
    	//select tokens
       	player1.setStartTokenColour("red");
    	player2.setStartTokenColour("blue");
    	player3.setStartTokenColour("purple");
    	
    	//begin game, deal cards
    	game.startGame(); //will automatically deal cards to players, but we will replace them with specific cards for testing
    	game.addAllToDeck(player1.getCards());
    	game.addAllToDeck(player2.getCards());
    	game.addAllToDeck(player3.getCards());
    	
    	ArrayList<Card> player1Cards = new ArrayList<>();
    	player1Cards.add(new SupportCard("maiden", 6));
    	player1Cards.add(new SupportCard("squire", 2));
    	player1Cards.add(new ColourCard("green", 1));
    	player1Cards.add(new ColourCard("purple", 5));
    	player1Cards.add(new ColourCard("red", 4));
    	player1Cards.add(new ColourCard("yellow", 3));
    	player1Cards.add(new ColourCard("red", 3));
    	player1Cards.add(new ActionCard("adapt"));
    	player1.setCards(player1Cards);
    	game.removeAllFromDeck(player1Cards);
    	
    	ArrayList<Card> player2Cards = new ArrayList<>();
    	player2Cards.add(new ColourCard("purple", 4));
    	player2Cards.add(new ColourCard("purple", 3));
    	player2Cards.add(new ColourCard("yellow", 2));
    	player2Cards.add(new ColourCard("yellow", 3));
    	player2Cards.add(new ColourCard("red", 4));
    	player2Cards.add(new SupportCard("squire", 3));
    	player2Cards.add(new ActionCard("riposte"));
    	player2Cards.add(new SupportCard("maiden", 6));

    	player2.setCards(player2Cards);
    	game.removeAllFromDeck(player2Cards);
    	
    	ArrayList<Card> player3Cards = new ArrayList<>();
    	player3Cards.add(new ColourCard("yellow", 2));
    	player3Cards.add(new ColourCard("blue", 4));
    	player3Cards.add(new ColourCard("yellow", 3));
    	player3Cards.add(new ColourCard("red", 3));
    	player3Cards.add(new ColourCard("blue", 2));
    	player3Cards.add(new SupportCard("squire", 2));
    	player3Cards.add(new ActionCard("riposte"));
    	player3Cards.add(new ActionCard("dodge"));
    	player3.setCards(player3Cards);
    	game.removeAllFromDeck(player3Cards);
    	
    }
	
    @Before
    public void setUp() {
    	testNumber++;
		System.out.println("@Before: Start turn " + testNumber);
    	//current player picks up a card at the beginning of their turn
			game.removeCardfromDeck(topCard);
	    	game.getCurrentPlayer().addCard(topCard);
	    	game.startTurn();
    }
    
    @After
    public void tearDown() {
    	System.out.println("@After: end turn " + testNumber + "\n");  	
    	game.endTurn();
    }
    
    @Test
    public void test1Player1() {
    	System.out.println("@Test: Player 3 picked purple token. Player 1, to their left, starts the game. \n"
    			+ "        Player1 sets tournament to red and plays 2 cards");
    	
    	//reset top card for the next player to pick up
    	topCard = new ColourCard("red", 4);
    	
    	//test that we have the correct current player
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());
    	
    	//test correct number of players
    	int players = game.getNumPlayers();
    	assertEquals(3, players);
    	
    	//make sure that the last player in the players array is the one that picked purple
    	assertEquals("purple", game.getPlayers().get(game.getNumPlayers()-1).getStartTokenColour());
    	
    	//test that the draw deck has the correct number of cards
    	assertEquals(85, game.getDrawDeck().size());
    	
    	//test that players have the right size hand
    	assertEquals(9, game.getCurrentPlayer().getCards().size());
    	assertEquals(8, player2.getCards().size());
    	assertEquals(8, player3.getCards().size());

    	//test that the first player chooses the tournament colour
    	player1.chooseTournamentColour("red");
    	assertEquals("red", game.getTournamentColour());  	
    	
    	//Player1 plays a maiden 6
    	Card toPlay = game.getCurrentPlayer().getCards().get(0);
    	game.playCard(toPlay);
    	assertEquals("maiden", toPlay.getType());
    	assertEquals(6, toPlay.getValue());
    	
    	//Player1 plays a red 4
    	toPlay = game.getCurrentPlayer().getCards().get(4);
    	game.playCard(toPlay);
    	assertEquals("red", toPlay.getType());
    	assertEquals(4, toPlay.getValue());
    	
    	//test the current player's total card value  
    	assertEquals(10, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(0, player1.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());

    	//test that the current player has the correct number of cards left
    	assertEquals(7, game.getCurrentPlayer().getCards().size());
    }
    
    @Test
    public void test2Player2() {
    	System.out.println("@Test: Player2 plays 3 cards");

    	//reset top card for the next player to pick up
    	topCard = new ActionCard("riposte");
    	
    	//make sure that we have the correct current player
    	assertEquals(player2.getName(), game.getCurrentPlayer().getName());
    	
    	//test that current player has correct number of cards
    	assertEquals(9, game.getCurrentPlayer().getCards().size());
    	
    	//player2 plays red 4
    	Card toPlay = game.getCurrentPlayer().getCards().get(4);
    	game.playCard(toPlay);
    	assertEquals("red", toPlay.getType());
    	assertEquals(4, toPlay.getValue());

    	//player2 plays red 4
    	toPlay = game.getCurrentPlayer().getCards().get(7);
    	game.playCard(toPlay);
    	assertEquals("red", toPlay.getType());
    	assertEquals(4, toPlay.getValue());
    	
    	//player2 plays squire 3
    	toPlay = game.getCurrentPlayer().getCards().get(5);
    	game.playCard(toPlay);
    	assertEquals("red", toPlay.getType());
    	assertEquals(4, toPlay.getValue());
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(6, game.getCurrentPlayer().getCards().size());

    	//test the current player's total card value  
    	assertEquals(10, player1.getTotalCardValue());
    	assertEquals(11, player2.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());

    }
    
    @Test
    public void test3Player3() {
    	System.out.println("@Test: Player3 plays 5 cards");

    	//reset top card for the next player to pick up
    	topCard = new ActionCard("riposte");
    	
    	//make sure that we have the correct current player
    	assertEquals(player3.getName(), game.getCurrentPlayer().getName());
    	
    	//test that current player has correct number of cards
    	assertEquals(9, game.getCurrentPlayer().getCards().size());
    	
    	//player3 plays red 3
    	Card toPlay = game.getCurrentPlayer().getCards().get(3);
    	game.playCard(toPlay);
    	assertEquals("red", toPlay.getType());
    	assertEquals(3, toPlay.getValue());

    	//player3 plays squire 2
    	toPlay = game.getCurrentPlayer().getCards().get(5);
    	game.playCard(toPlay);
    	assertEquals("squire", toPlay.getType());
    	assertEquals(2, toPlay.getValue());

    	//player3 plays riposte and steals maiden from the top of Player1's display
    	toPlay = game.getCurrentPlayer().getCards().get(7);
    	game.playCard(toPlay);
    	assertEquals("riposte", toPlay.getType());
    	assertEquals(8, player3.getCards().size());
    	
    	//player3 plays maiden 6
    	toPlay = game.getCurrentPlayer().getCards().get(7);
    	game.playCard(toPlay);
    	assertEquals("maiden", toPlay.getType());
    	assertEquals(6, toPlay.getValue());
    	
    	//player3 plays dodge and discards a red 4 from player2's display
    	toPlay = game.getCurrentPlayer().getCards().get(6);
    	game.playCard(toPlay);
    	assertEquals("dodge", toPlay.getType());
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(5, game.getCurrentPlayer().getCards().size());

    	//test the current player's total card value  
    	assertEquals(4, player1.getTotalCardValue());
    	assertEquals(7, player2.getTotalCardValue());
    	assertEquals(11, player3.getTotalCardValue());
    }
    
    @Test
    public void test4Player1() {    	
    	System.out.println("@Test: Player1 plays 3 cards");
    	
    	//reset top card for the next player to pick up
    	topCard = new ColourCard("green", 1);
    	
    	//make sure that we have the correct current player
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());
    	
    	//test that current player has correct number of cards
    	assertEquals(8, game.getCurrentPlayer().getCards().size());    	

    	//Player1 plays riposte and steals maiden back
    	Card toPlay = game.getCurrentPlayer().getCards().get(7);
    	game.playCard(toPlay);
    	assertEquals("riposte", toPlay.getType());

    	//player1 plays maiden 6
    	toPlay = game.getCurrentPlayer().getCards().get(7);
    	game.playCard(toPlay);
    	assertEquals("maiden", toPlay.getType());
    	assertEquals(6, toPlay.getValue());
    	
    	//player1 plays red 3 
    	toPlay = game.getCurrentPlayer().getCards().get(6);
    	game.playCard(toPlay);
    	assertEquals("red", toPlay.getType());
    	assertEquals(3, toPlay.getValue());
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(6, game.getCurrentPlayer().getCards().size());

    	//test the current player's total card value  
    	assertEquals(13, player1.getTotalCardValue());
    	assertEquals(7, player2.getTotalCardValue());
    	assertEquals(5, player3.getTotalCardValue());
    	
    }
    
    @Test
    public void test5Player2() {
    	System.out.println("@Test: Player2 plays 3 cards");
    	
    	//reset top card for the next player to pick up
    	topCard = new ColourCard("red", 3);

    	//make sure that we have the correct current player
    	assertEquals(player2.getName(), game.getCurrentPlayer().getName());

    	//test that the current player has the correct number of cards left
    	assertEquals(6, game.getCurrentPlayer().getCards().size());
    	
    	//player2 plays maiden6
    	Card toPlay = game.getCurrentPlayer().getCards().get(5);
    	game.playCard(toPlay);
    	assertEquals("maiden", toPlay.getType());
    	assertEquals(6, toPlay.getValue());
    	
    	//player2 plays riposte and steals red 3 from player1's display
    	toPlay = game.getCurrentPlayer().getCards().get(4);
    	game.playCard(toPlay);
    	assertEquals("riposte", toPlay.getType());

    	//player2 plays red 3
    	toPlay = game.getCurrentPlayer().getCards().get(5);
    	game.playCard(toPlay);
    	assertEquals("red", toPlay.getType());
    	assertEquals(3, toPlay.getValue());
    	
    	//test the current player's total card value  
    	assertEquals(16, player1.getTotalCardValue());
    	assertEquals(10, player2.getTotalCardValue());
    	assertEquals(5, player3.getTotalCardValue());


    }
    
    @Test
    public void test6Player3() {
    	System.out.println("@Test: Player3 plays 1 card and withdraws");
    	
    	//reset top card for the next player to pick up
    	topCard = new ColourCard("red", 5);
    	
    	//make sure that we have the correct current player
    	assertEquals(player3.getName(), game.getCurrentPlayer().getName());
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(6, game.getCurrentPlayer().getCards().size());
    	
    	//player3 plays red 3
    	Card toPlay = game.getCurrentPlayer().getCards().get(5);
    	game.playCard(toPlay);
    	assertEquals("red", toPlay.getType());
    	assertEquals(3, toPlay.getValue());
    	
    	//test the current player's total card value  
    	assertEquals(16, player1.getTotalCardValue());
    	assertEquals(10, player2.getTotalCardValue());
    	assertEquals(8, player3.getTotalCardValue());
    	
    	//Withdraw current player from tournament
    	game.withdraw();
    	assertTrue(game.getCurrentPlayer().hasWithdrawn());
    }
    
    @Test
    public void test7Player1() {    	
    	System.out.println("@Test: Player1 plays 1 card");
    	
    	//reset top card for the next player to pick up
    	topCard = new ColourCard("green", 1);

    	//make sure that we have the correct current player (meaning player 3 is withdrawn)
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());

    	//test that current player has correct number of cards
    	assertEquals(8, game.getCurrentPlayer().getCards().size());

    	//player1 plays red 5
    	Card toPlay = game.getCurrentPlayer().getCards().get(7);
    	game.playCard(toPlay);
    	assertEquals("red", toPlay.getType());
    	assertEquals(5, toPlay.getValue());
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(7, game.getCurrentPlayer().getCards().size());

    	//test the current player's total card value  
    	assertEquals(18, player1.getTotalCardValue());
    	assertEquals(16, player2.getTotalCardValue());
    	assertEquals(8, player3.getTotalCardValue());
    }
 
    @Test
    public void test8Player2() {    	
    	System.out.println("@Test: Player2 withdraws");

    	//reset top card for the next player to pick up
    	topCard = new ColourCard("yellow", 3);
    	
    	//make sure that we have the correct current player
    	assertEquals(player2.getName(), game.getCurrentPlayer().getName());

    	//test that current player has correct number of cards
    	assertEquals(6, game.getCurrentPlayer().getCards().size());

    	//test the current player's total card value  
    	assertEquals(18, player1.getTotalCardValue());
    	assertEquals(16, player2.getTotalCardValue());
    	assertEquals(8, player3.getTotalCardValue());
    	
    	//current player withdraws
    	game.withdraw();
    	assertTrue(game.getCurrentPlayer().hasWithdrawn());

    }
    
    @Test
    public void test9Player1() {    	
    	System.out.println("@Test: Player1 wins, begins next round, plays 1 card");

    	//make sure that we have the correct current player (player 2 has withdrawn)
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());

    	//test that the current player picks up a card at the beginning of their turn
    	assertEquals(8, game.getCurrentPlayer().getCards().size());
    	//card picked up is for the beginning of the next turn

    	//player1 is the last remaining, and is announced as the winner
    	assertTrue(player1.isWinner());
    	assertFalse(player2.isWinner());
    	assertFalse(player3.isWinner());
    	
    	//test that the winning player gets the correct token
    	assertTrue(game.getCurrentPlayer().getCurrentTokens().contains(game.getTournamentColour()));
    	
		//reset top card for the next player to pick up
		topCard = new ColourCard("purple", 4);
		
		//test that we have the correct current player
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		
		//test that all players are in the next tournament
		assertFalse(player1.hasWithdrawn());
		assertFalse(player2.hasWithdrawn());
		assertFalse(player3.hasWithdrawn());
		
		//test that players have the right size hand
		assertEquals(10, game.getCurrentPlayer().getCards().size());
		assertEquals(6, player2.getCards().size());
		assertEquals(5, player3.getCards().size());
	
		//test that the first player chooses the tournament colour
		player1.chooseTournamentColour("yellow");
		assertEquals("yellow", game.getTournamentColour());  	
		
		//Player1 plays a yellow 3
		Card toPlay = game.getCurrentPlayer().getCards().get(9);
		game.playCard(toPlay);
		assertEquals("yellow", toPlay.getType());
		assertEquals(3, toPlay.getValue());
		
		//test the current player's total card value 
		assertEquals(3, game.getCurrentPlayer().getTotalCardValue());
		assertEquals(0, player1.getTotalCardValue());
		assertEquals(0, player3.getTotalCardValue());
	
		//test that the current player has the correct number of cards left
		assertEquals(8, game.getCurrentPlayer().getCards().size());
}

	@Test
	public void test10Player2() {
		System.out.println("@Test: Player2 plays 2 cards");
	
		//reset top card for the next player to pick up
		topCard = new ColourCard("purple", 3);
		
		//make sure that we have the correct current player
		assertEquals(player2.getName(), game.getCurrentPlayer().getName());
		
		//test that current player has correct number of cards
		assertEquals(7, game.getCurrentPlayer().getCards().size());
		
		//player2 plays a yellow 3
		Card toPlay = game.getCurrentPlayer().getCards().get(3);
		game.playCard(toPlay);
		assertEquals("yellow", toPlay.getType());
		assertEquals(3, toPlay.getValue());
	
		//player2 plays a yellow 2
		toPlay = game.getCurrentPlayer().getCards().get(2);
		game.playCard(toPlay);
		assertEquals("yellow", toPlay.getType());
		assertEquals(2, toPlay.getValue());
		
		//test that the current player has the correct number of cards left
		assertEquals(5, game.getCurrentPlayer().getCards().size());
	
		//test the current player's total card value  
		assertEquals(3, player1.getTotalCardValue());
		assertEquals(5, player2.getTotalCardValue());
		assertEquals(0, player3.getTotalCardValue());
	
	}
	
	@Test
	public void test11Player3() {
		System.out.println("@Test: Player3 withdraws");
	
		//A new round begins, beginning with the current player
		topCard = new SupportCard("squire", 2);
		
		//make sure that we have the correct current player
		assertEquals(player3.getName(), game.getCurrentPlayer().getName());
		
		//test that current player has correct number of cards
		assertEquals(6, game.getCurrentPlayer().getCards().size());
		
		//test the current player's total card value  
		assertEquals(3, player1.getTotalCardValue());
		assertEquals(5, player2.getTotalCardValue());
		assertEquals(0, player3.getTotalCardValue());
		
		//Withdraw current player from tournament
		game.withdraw();
		assertTrue(game.getCurrentPlayer().hasWithdrawn());
	}
	
	@Test
	public void test12Player1() {    	
	System.out.println("@Test: Player1 plays 1 card");
	
	//reset top card for the next player to pick up
	topCard = new SupportCard("squire", 2);
	
	//make sure that we have the correct current player
	assertEquals(player1.getName(), game.getCurrentPlayer().getName());
	
	//test that current player has correct number of cards
	assertEquals(9, game.getCurrentPlayer().getCards().size());    	

	//Player1 plays yellow 3
	Card toPlay = game.getCurrentPlayer().getCards().get(5);
	game.playCard(toPlay);
	assertEquals("yellow", toPlay.getType());
	assertEquals(3, toPlay.getValue());

	//Player1 plays squire 2
	toPlay = game.getCurrentPlayer().getCards().get(9);
	game.playCard(toPlay);
	assertEquals("squire", toPlay.getType());
	assertEquals(2, toPlay.getValue());
	
	//test that the current player has the correct number of cards left
	assertEquals(7, game.getCurrentPlayer().getCards().size());

	//test the current player's total card value  
	assertEquals(8, player1.getTotalCardValue());
	assertEquals(5, player2.getTotalCardValue());
	assertEquals(0, player3.getTotalCardValue());
	
}

	@Test
	public void test13Player2() {
		System.out.println("@Test: Player2 withdraws");
		
		//reset top card for the next player to pick up
		topCard = new ActionCard("knockdown");
	
		//make sure that we have the correct current player
		assertEquals(player2.getName(), game.getCurrentPlayer().getName());
	
		//test that the current player has the correct number of cards left
		assertEquals(5, game.getCurrentPlayer().getCards().size());
		
		//test the current player's total card value  
		assertEquals(8, player1.getTotalCardValue());
		assertEquals(5, player2.getTotalCardValue());
		assertEquals(0, player3.getTotalCardValue());
	
		//Withdraw current player from tournament
		game.withdraw();
		assertTrue(game.getCurrentPlayer().hasWithdrawn());
	}
	
	@Test
	public void test14Player1() {    	
		System.out.println("@Test: Player1 wins, begins next tournament and plays 1 card");
	
		//make sure that we have the correct current player (player 2 has withdrawn)
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
	
		//test that the current player picks up a card at the beginning of their turn
		assertEquals(8, game.getCurrentPlayer().getCards().size());
		//picked up card is actually for the beginning of next tournament
		
		//player1 is the last remaining, and is announced as the winner
		assertTrue(player1.isWinner());
		assertFalse(player2.isWinner());
		assertFalse(player3.isWinner());
		
		//test that the winning player gets the correct token
		assertTrue(game.getCurrentPlayer().getCurrentTokens().contains(game.getTournamentColour()));
		
		//reset top card for the next player to pick up
		topCard = new ColourCard("squire", 3);
		
		//test that we have the correct current player
		assertEquals(player1.getName(), game.getCurrentPlayer().getName());
		
		//test that all players are in the next tournament
		assertFalse(player1.hasWithdrawn());
		assertFalse(player2.hasWithdrawn());
		assertFalse(player3.hasWithdrawn());
		
		//test that players have the right size hand
		assertEquals(8, game.getCurrentPlayer().getCards().size());
		assertEquals(5, player2.getCards().size());
		assertEquals(6, player3.getCards().size());
	
		//test that the first player chooses the tournament colour
		player1.chooseTournamentColour("green");
		assertEquals("green", game.getTournamentColour());  	
		
		//Player1 plays a green 1
		Card toPlay = game.getCurrentPlayer().getCards().get(1);
		game.playCard(toPlay);
		assertEquals("green", toPlay.getType());
		assertEquals(1, toPlay.getValue());
		
		//test the current player's total card value 
		assertEquals(1, game.getCurrentPlayer().getTotalCardValue());
		assertEquals(0, player2.getTotalCardValue());
		assertEquals(0, player3.getTotalCardValue());
	
		//test that the current player has the correct number of cards left
		assertEquals(7, game.getCurrentPlayer().getCards().size());
}
		
	public void test15Player2() {
	    	System.out.println("@Test: Player2 plays 2 cards");
	    	
	    	//reset top card for the next player to pick up
	    	topCard = new ColourCard("green", 1);
	
	    	//make sure that we have the correct current player
	    	assertEquals(player2.getName(), game.getCurrentPlayer().getName());
	
	    	//test that the current player has the correct number of cards left
	    	assertEquals(6, game.getCurrentPlayer().getCards().size());
	    	
	    	//player2 plays green 1
	    	Card toPlay = game.getCurrentPlayer().getCards().get(2);
	    	game.playCard(toPlay);
	    	assertEquals("green", toPlay.getType());
	    	assertEquals(1, toPlay.getValue());
	    	
	    	//player2 plays green 1
	    	toPlay = game.getCurrentPlayer().getCards().get(2);
	    	game.playCard(toPlay);
	    	assertEquals("green", toPlay.getType());
	    	assertEquals(1, toPlay.getValue());

	    	//test the current player's total card value  
	    	assertEquals(1, player1.getTotalCardValue());
	    	assertEquals(2, player2.getTotalCardValue());
	    	assertEquals(0, player3.getTotalCardValue());
	    }
		    
	@Test
	public void test16Player3() {
		System.out.println("@Test: Player3 withdraws");
	
		//A new round begins, beginning with the current player
		topCard = new SupportCard("blue", 3);
		
		//make sure that we have the correct current player
		assertEquals(player3.getName(), game.getCurrentPlayer().getName());
		
		//test that current player has correct number of cards
		assertEquals(6, game.getCurrentPlayer().getCards().size());
		
		//test the current player's total card value  
		assertEquals(1, player1.getTotalCardValue());
		assertEquals(2, player2.getTotalCardValue());
		assertEquals(0, player3.getTotalCardValue());
		
		//Withdraw current player from tournament
		game.withdraw();
		assertTrue(game.getCurrentPlayer().hasWithdrawn());
	}

	@Test
	public void test17Player1() {
		System.out.println("@Test: Player1 withdraws");
	
		//A new round begins, beginning with the current player
		topCard = new SupportCard("yellow", 3);
		
		//make sure that we have the correct current player
		assertEquals(player3.getName(), game.getCurrentPlayer().getName());
		
		//test that current player has correct number of cards
		assertEquals(6, game.getCurrentPlayer().getCards().size());
		
		//test the current player's total card value  
		assertEquals(1, player1.getTotalCardValue());
		assertEquals(2, player2.getTotalCardValue());
		assertEquals(0, player3.getTotalCardValue());
		
		//Withdraw current player from tournament
		game.withdraw();
		assertTrue(game.getCurrentPlayer().hasWithdrawn());
	}

	@Test
	public void test18Player2() {    	
		System.out.println("@Test: Player2 wins, begins next tournament and plays 1 card");
	
		//make sure that we have the correct current player (player 2 has withdrawn)
		assertEquals(player2.getName(), game.getCurrentPlayer().getName());
	
		//test that the current player picks up a card at the beginning of their turn
		assertEquals(7, game.getCurrentPlayer().getCards().size());
		//picked up card is actually for the beginning of next tournament
		
		//player1 is the last remaining, and is announced as the winner
		assertFalse(player1.isWinner());
		assertTrue(player2.isWinner());
		assertFalse(player3.isWinner());
		
		//test that the winning player gets the correct token
		assertTrue(game.getCurrentPlayer().getCurrentTokens().contains(game.getTournamentColour()));
		
		//reset top card for the next player to pick up
		topCard = new ColourCard("green", 1);
		
		//test that all players are in the next tournament
		assertFalse(player1.hasWithdrawn());
		assertFalse(player2.hasWithdrawn());
		assertFalse(player3.hasWithdrawn());
		
		//test that players have the right size hand
		assertEquals(7, game.getCurrentPlayer().getCards().size());
		assertEquals(7, player1.getCards().size());
		assertEquals(7, player3.getCards().size());
	
		//test that the first player chooses the tournament colour
		player1.chooseTournamentColour("purple");
		assertEquals("purple", game.getTournamentColour());  	
		
		//Player2 plays a purple 3
		Card toPlay = game.getCurrentPlayer().getCards().get(1);
		game.playCard(toPlay);
		assertEquals("purple", toPlay.getType());
		assertEquals(3, toPlay.getValue());
		
		//test the current player's total card value 
		assertEquals(3, game.getCurrentPlayer().getTotalCardValue());
		assertEquals(0, player1.getTotalCardValue());
		assertEquals(0, player3.getTotalCardValue());
	
		//test that the current player has the correct number of cards left
		assertEquals(6, game.getCurrentPlayer().getCards().size());
}

	//ending here for now, may continue if there is time
}