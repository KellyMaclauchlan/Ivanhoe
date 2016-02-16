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

public class Test4PlayerManual {
	static GameEngine game;
	static int testNumber = 0;
	static Player player1;
	static Player player2;
	static Player player3;
	static Player player4;

	private Card pickup = new ColourCard("yellow", 4);

	@BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: Setting up tests for a manually set 4 player round, dealing cards, selecting tokens");
        //These tests set the hands and played cards manually to test specific scenarios
        
		game = new GameEngine();
		
    	game.startGame();
    	
    	//add 2 players to the game
    	player1 = new Player("Katie");
    	player2 = new Player("Kelly");
    	player3 = new Player("Brit");
    	player4 = new Player("Ahmed");
    	game.joinGame(player1);
    	game.joinGame(player2);
    	game.joinGame(player3);
    	game.joinGame(player4);
    	
    	//select tokens
       	player1.setStartTokenColour("blue");
    	player2.setStartTokenColour("purple");
    	player3.setStartTokenColour("red");
    	player4.setStartTokenColour("yellow");
    	
    	//begin game, deal cards
    	game.startGame(); //will automatically deal cards to players, but we will replace them with specific cards for testing
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
    }
	
    @Before
    public void setUp() {
    	testNumber++;
		System.out.println("@Before: Start turn " + testNumber);
    	//test that the current player picks up a card at the beginning of their turn
			game.removeCardfromDeck(pickup);
	    	game.getCurrentPlayer().addCard(pickup);
	    	game.startTurn();
    }
    
    @After
    public void tearDown() {
    	System.out.println("@After: end turn " + testNumber + "\n");  	
    	game.endTurn();
    }
    
    @Test
    public void test1Player2() {
    	System.out.println("@Test: Player2 sets tournament to red and plays 1 card");
    	//test that we have the correct current player
    	assertEquals(player2.getName(), game.getCurrentPlayer().getName());
    	
    	//make sure that the first player in the players array is the one that picked purple, and the second player did not
    	assertEquals("purple", game.getPlayers().get(0).getStartTokenColour());
    	
    	//test correct number of players
    	int players = game.getNumPlayers();
    	assertEquals(4, players);
    	
    	//test that the draw deck has the correct number of cards
    	assertEquals(77, game.getDrawDeck().size());
    	
    	//test that players have the right size hand
    	assertEquals(9, game.getCurrentPlayer().getCards().size());
    	assertEquals(8, player1.getCards().size());
    	assertEquals(8, player3.getCards().size());
    	assertEquals(8, player4.getCards().size());

    	//test that the first player chooses the tournament colour
    	player1.chooseTournamentColour("red");
    	assertEquals("red", game.getTournamentColour());  	
    	
    	Card toPlay = game.getCurrentPlayer().getCards().get(0);
    	game.playCard(toPlay);
    	assertEquals("red", toPlay.getType());
    	assertEquals(3, toPlay.getValue());
    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(3, game.getCurrentPlayer().getTotalCardValue());
    	assertEquals(0, player1.getTotalCardValue());
    	assertEquals(0, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());

    	//test that the current player has the correct number of cards left
    	assertEquals(8, game.getCurrentPlayer().getCards().size());
    	
    	//next player card to pick up 
    	pickup = new ColourCard("green", 1);

    }
    
    @Test
    public void test2Player3() {
    	System.out.println("@Test: Player3 plays 1 card");

    	//make sure that we have the correct current player
    	assertEquals(player3.getName(), game.getCurrentPlayer().getName());
    	
    	//test that current player has correct number of cards
    	assertEquals(9, game.getCurrentPlayer().getCards().size());
    	Card toPlay = game.getCurrentPlayer().getCards().get(0);
    	game.playCard(toPlay);
    	assertEquals("red", toPlay.getType());
    	assertEquals(3, toPlay.getValue());

    	//test that the current player has the correct number of cards left
    	assertEquals(8, game.getCurrentPlayer().getCards().size());

    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(5, player3.getTotalCardValue());
    	assertEquals(3, player2.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(0, player1.getTotalCardValue());

    	//next player card to pick up
    	pickup = new ColourCard("blue", 4);
    }
    
    @Test
    public void test3Player4() {
    	System.out.println("@Test: Player4 has no playable cards");

    	//make sure that we have the correct current player
    	assertEquals(player4.getName(), game.getCurrentPlayer().getName());
    	
    	//test that the current player picks up a card at the beginning of their turn

    	assertEquals(9, game.getCurrentPlayer().getCards().size());
    	
    	//current player has no playable cards and is withdrawn
    	pickup = new ColourCard("blue", 3);
    	
    }
    
    @Test
    public void test4Player1() {    	
    	System.out.println("@Test: Player1 plays 2 cards");

    	//make sure that we have the correct current player
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());
    	
    	//test that current player has correct number of cards
    	assertEquals(9, game.getCurrentPlayer().getCards().size());
    	Card toPlay1 = game.getCurrentPlayer().getCards().get(0);
    	Card toPlay2 = game.getCurrentPlayer().getCards().get(1);

    	game.playCard(toPlay1);
    	game.playCard(toPlay2);

    	assertEquals("red", toPlay1.getType());
    	assertEquals(4, toPlay1.getValue());
    	
    	assertEquals("red", toPlay1.getType());
    	assertEquals(3, toPlay1.getValue());

    	//test that the current player has the correct number of cards left
    	assertEquals(7, game.getCurrentPlayer().getCards().size());

    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(3, player2.getTotalCardValue());
    	assertEquals(5, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(7, player1.getTotalCardValue());

    	//next player card to pick up
    	pickup = new ColourCard("red", 5);
    }
    
    @Test
    public void test5Player2() {
    	System.out.println("@Test: Player2 plays 1 card");
    	assertEquals(3, game.getPlayers().size());

    	//make sure that we have the correct current player
    	assertEquals(player2.getName(), game.getCurrentPlayer().getName());
    	
    	Card toPlay = game.getCurrentPlayer().getCards().get(game.getCurrentPlayer().getCards().size()-1);
    	game.playCard(toPlay);
    	assertEquals("red", toPlay.getType());
    	assertEquals(5, toPlay.getValue());
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(8, game.getCurrentPlayer().getCards().size());

    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(8, player2.getTotalCardValue());
    	assertEquals(5, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(7, player1.getTotalCardValue());

    	//next player card to pick up
    	pickup = new ColourCard("yellow", 5);
    }
    
    @Test
    public void test6Player3() {
    	System.out.println("@Test: Player3 withdraws");

    	//make sure that we have the correct current player
    	assertEquals(player3.getName(), game.getCurrentPlayer().getName());
    	
    	//test that the current player picks up a card at the beginning of their turn
    	assertEquals(9, game.getCurrentPlayer().getCards().size());
    	
    	//next player card to pick up
    	pickup = new ColourCard("red", 3);
    }
    
    @Test
    public void test7Player1() {    	
    	System.out.println("@Test: Player1 plays 2 cards");

    	//make sure that we have the correct current player (meaning player 4 is withdrawn)
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());
    	assertEquals(2, game.getPlayers().size());

    	//test that current player has correct number of cards
    	assertEquals(8, game.getCurrentPlayer().getCards().size());
    	Card toPlay1 = game.getCurrentPlayer().getCards().get(game.getCurrentPlayer().getCards().size()-1);
    	Card toPlay2 = game.getCurrentPlayer().getCards().get(6);

    	game.playCard(toPlay1);
    	game.playCard(toPlay2);
    	
    	assertEquals("red", toPlay1.getType());
    	assertEquals(3, toPlay1.getValue());
    	
    	assertEquals("squire", toPlay2.getType());
    	assertEquals(2, toPlay2.getValue());
    	
    	game.playCard(game.getCurrentPlayer().getCards().get(game.getCurrentPlayer().getCards().size()-1));

    	//test that the current player has the correct number of cards left
    	assertEquals(6, game.getCurrentPlayer().getCards().size());

    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(8, player2.getTotalCardValue());
    	assertEquals(5, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(12, player1.getTotalCardValue());

    	//next player card to pick up
    	pickup = new ColourCard("maiden", 6);
    }
 
    @Test
    public void test8Player2() {    	
    	System.out.println("@Test: Player2 plays 1 card");

    	//make sure that we have the correct current player (meaning player 4 is withdrawn)
    	assertEquals(player2.getName(), game.getCurrentPlayer().getName());
    	assertEquals(2, game.getPlayers().size());

    	//test that current player has correct number of cards
    	assertEquals(9, game.getCurrentPlayer().getCards().size());
    	Card toPlay = game.getCurrentPlayer().getCards().get(game.getCurrentPlayer().getCards().size()-1);
    	game.playCard(toPlay);
    	
    	assertEquals("maiden", toPlay.getType());
    	assertEquals(6, toPlay.getValue());
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(8, game.getCurrentPlayer().getCards().size());

    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(14, player2.getTotalCardValue());
    	assertEquals(5, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(12, player1.getTotalCardValue());

    	//next player card to pick up
    	pickup = new ColourCard("squire", 3);
    }
    
    @Test
    public void test9Player1() {    	
    	System.out.println("@Test: Player1 plays 1 card");

    	//make sure that we have the correct current player (meaning player 4 is withdrawn)
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());
    	assertEquals(2, game.getPlayers().size());

    	//test that current player has correct number of cards
    	assertEquals(7, game.getCurrentPlayer().getCards().size());
    	
    	Card toPlay = game.getCurrentPlayer().getCards().get(game.getCurrentPlayer().getCards().size()-1);
    	game.playCard(toPlay);

    	assertEquals("squire", toPlay.getType());
    	assertEquals(3, toPlay.getValue());
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(6, game.getCurrentPlayer().getCards().size());

    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(14, player2.getTotalCardValue());
    	assertEquals(5, player3.getTotalCardValue());
    	assertEquals(0, player4.getTotalCardValue());
    	assertEquals(15, player1.getTotalCardValue());

    	//next player card to pick up
    	pickup = new ColourCard("green", 1);
    }
    
    @Test
    public void test10Player2() {
    	System.out.println("@Test: Player2 withdraws");

    	//make sure that we have the correct current player
    	assertEquals(player2.getName(), game.getCurrentPlayer().getName());
    	
    	//test that the current player picks up a card at the beginning of their turn
    	assertEquals(9, game.getCurrentPlayer().getCards().size());
    	
    	//next player card to pick up
    	pickup = new ColourCard("red", 3);
    }
    
    @Test
    public void test11Player1() {
    	System.out.println("@Test: Player1 wins the tournament");
    	assertEquals(1, game.getPlayers().size());

    	//make sure that we have the correct current player
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());
    	
    	//test that the current player picks up a card at the beginning of their turn
    	assertEquals(7, game.getCurrentPlayer().getCards().size());
    	
    	//player1 is the last remaining, and is announced as the winner
    	assertTrue(player1.isWinner());
    	assertFalse(player2.isWinner());
    	assertFalse(player3.isWinner());
    	assertFalse(player4.isWinner());
    	
    	//test that the winning player gets the correct token
    	assertTrue(game.getCurrentPlayer().getCurrentTokens().contains(game.getTournamentColour()));
    }
    
}
