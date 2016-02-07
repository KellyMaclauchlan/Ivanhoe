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

public class Test2PlayerManual {
	static GameEngine game;
	static int testNumber = 0;
	static Player player1;
	static Player player2;
	static ArrayList<Card> player1Cards;
	static ArrayList<Card> player2Cards;
	private Card pickup = new ColourCard("yellow", 4);

	@BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: Setting up tests for a manually set 2 player game, dealing cards, selecting tokens");
        //These tests set the hands and played cards manually to test specific scenarios
        
		game = new GameEngine();
		
    	game.startGame();
    	
    	//add 2 players to the game
    	player1 = new Player("Katie");
    	player2 = new Player("Kelly");
    	game.joinGame(player1);
    	game.joinGame(player2);
    	
    	//select tokens
       	player1.setStartTokenColour("purple");
    	player2.setStartTokenColour("blue");
    	
    	//begin game, deal cards
    	game.startGame(); //will automatically deal cards to players, but we will replace them with specific cards for testing
    	game.addAllToDeck(player1.getCards());
    	game.addAllToDeck(player2.getCards());
    	
    	player1Cards = new ArrayList<>();
    	player1Cards.add(new ColourCard("blue", 4));
    	player1Cards.add(new ColourCard("purple", 5));
    	player1Cards.add(new ColourCard("yellow", 3));
    	player1Cards.add(new ColourCard("red", 4));
    	player1Cards.add(new ColourCard("yellow", 3));
    	player1Cards.add(new ColourCard("yellow", 2));
    	player1Cards.add(new ActionCard("riposte"));
    	player1Cards.add(new SupportCard("maiden", 6));
    	player1.setCards(player1Cards);
    	game.removeAllFromDeck(player1Cards);
    	
    	player2Cards = new ArrayList<>();
    	player2Cards.add(new ColourCard("green", 1));
    	player2Cards.add(new ColourCard("green", 1));
    	player2Cards.add(new ColourCard("yellow", 4));
    	player2Cards.add(new ColourCard("blue", 4));
    	player2Cards.add(new ColourCard("green", 1));
    	player2Cards.add(new ActionCard("drop weapon"));
    	player2Cards.add(new SupportCard("squire", 2));
    	player2Cards.add(new SupportCard("squire", 3));
    	player2.setCards(player2Cards);
    	game.removeAllFromDeck(player2Cards);
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
    public void test1Player1() {
    	System.out.println("@Test: Player1 sets tournament to yellow and plays 5 cards");
    	//test that we have the correct current player
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());
    	
    	//make sure that the first player in the players array is the one that picked purple, and the second player did not
    	assertEquals("purple", game.getPlayers().get(0).getStartTokenColour());
    	assertNotEquals("purple", game.getPlayers().get(1).getStartTokenColour());
    	
    	//test correct number of players
    	int players = game.getNumPlayers();
    	assertEquals(2, players);
    	
    	//test that the draw deck has the correct number of cards
    	assertEquals(94, game.getDrawDeck().size());
    	
    	//test that players have the right size hand
    	assertEquals(9, game.getCurrentPlayer().getCards().size());
    	assertEquals(8, player2.getCards().size());
    	
    	//test that the first player chooses the tournament colour
    	player1.chooseTournamentColour("yellow");
    	assertEquals("yellow", game.getTournamentColour());  	
    	
    	game.playCard(game.getCurrentPlayer().getCards().get(2));
    	game.playCard(game.getCurrentPlayer().getCards().get(4));
    	game.playCard(game.getCurrentPlayer().getCards().get(5));
    	game.playCard(pickup);
    	game.playCard(game.getCurrentPlayer().getCards().get(7));
    	
    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(18, player1.getTotalCardValue());
    	assertEquals(0, player2.getTotalCardValue());
    	assertTrue(player1.getTotalCardValue() > player2.getTotalCardValue());
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(4, game.getCurrentPlayer().getCards().size());
    	
    	//next player card to pick up 
    	pickup = new ColourCard("green", 1);

    }
    
    @Test
    public void test2Player2() {
    	System.out.println("@Test: Player2 sets tournament to green and plays 6 other cards");

    	//make sure that we have the correct current player
    	assertEquals(player2.getName(), game.getCurrentPlayer().getName());
    	
    	//test that players have the correct number of cards
    	assertEquals(4, player1.getCards().size());
    	assertEquals(9, game.getCurrentPlayer().getCards().size());
    	    	
    	game.playCard(game.getCurrentPlayer().getCards().get(5)); //tournament colour is green and cards are each worth 1 point
    	game.playCard(game.getCurrentPlayer().getCards().get(0));
    	game.playCard(game.getCurrentPlayer().getCards().get(1));
    	game.playCard(game.getCurrentPlayer().getCards().get(4));
    	game.playCard(pickup);
    	game.playCard(game.getCurrentPlayer().getCards().get(6));
    	game.playCard(game.getCurrentPlayer().getCards().get(7));
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(2, game.getCurrentPlayer().getCards().size());
    	game.endTurn();

    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(5, player1.getTotalCardValue());
    	assertEquals(6, player2.getTotalCardValue());
    	assertTrue(player2.getTotalCardValue() > player1.getTotalCardValue());
    	

    	//next player card to pick up
    	pickup = new ColourCard("blue", 4);

    }
    
    @Test
    public void test3Player1() {
    	System.out.println("@Test: Player1 plays RIPOSTE and takes a card from player2's display");
    	//make sure that we have the correct current player
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());
    	
    	//test that players have the correct number of cards
    	assertEquals(5, game.getCurrentPlayer().getCards().size());
    	assertEquals(2, player2.getCards().size());
    	
    	
    	game.playCard(game.getCurrentPlayer().getCards().get(3)); //player1 plays riposte and takes player2's last played card, a squire of 3
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(3, game.getCurrentPlayer().getCards().size());
    	game.endTurn();
    	
    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(6, player1.getTotalCardValue());
    	assertEquals(5, player2.getTotalCardValue());
    	assertTrue(player2.getTotalCardValue() > player1.getTotalCardValue());
    	
    	//next player pickup card
    	Card pickup = new ColourCard("red", 3);
    	
    }
    
    @Test
    public void test4Player2() {    	
    	System.out.println("@Test: Player2 has no playable cards");

    	//make sure that we have the correct current player
    	assertEquals(player2.getName(), game.getCurrentPlayer().getName());
    	
    	//test that the current player picks up a card at the beginning of their turn

    	assertEquals(3, player1.getCards().size());
    	assertEquals(3, game.getCurrentPlayer().getCards().size());
    	
    	//current player has no playable cards and is withdrawn
    	assertEquals(1, game.getPlayers().size());
    	pickup = new ColourCard("red", 3);
    }
    
    @Test
    public void test5Player1() {
    	System.out.println("@Test: Player1 wins the tournament");

    	//make sure that we have the correct current player
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());
    	
    	//test that the current player picks up a card at the beginning of their turn
    	assertEquals(4, game.getCurrentPlayer().getCards().size());
    	assertEquals(3, player2.getCards().size());
    	
    	//player1 is the last remaining, and is announced as the winner
    	assertTrue(player1.isWinner());
    	assertFalse(player2.isWinner());
    }
    
    
}
