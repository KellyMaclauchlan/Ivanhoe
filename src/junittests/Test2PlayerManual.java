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
	GameEngine game;
	static int testNumber = 0;
	Player player1;
	Player player2;
	ArrayList<Card> player1Cards;
	ArrayList<Card> player2Cards;

	@BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: Testing a manually set 2 player game");
        //These tests set the hands and played cards manually to test specific scenarios
    }
	
    @Before
    public void setUp() {
		System.out.println("@Before(): Setting up 2 player manual game test");
		game = new GameEngine();
		
		System.out.println("startGame");
    	game.startGame();
    	
    	//add 2 players to the game
    	player1 = new Player();
    	player2 = new Player();
    	game.joinGame(player1);
    	game.joinGame(player2);


    	//choose purple for the first player and leave that player as the first to play
    	player1 =  game.getPlayers().get(0);
    	player2 = game.getPlayers().get(1);
    	
    	player1.setStartTokenColour("purple");
    	player2.setStartTokenColour("blue");
    	
    	//make sure that the first player in the players array is the one that picked purple, and the second player did not
    	assertEquals("purple", player1.getStartTokenColour());
    	assertNotEquals("purple", player2.getStartTokenColour());
    	
    	game.startGame();

    	int players = game.getNumPlayers();
    	assertEquals(2, players);

    	//make sure that the current player is the first in the players array 
    	assertEquals(game.getCurrentPlayer(), player1);
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
    	assertEquals(102, game.getDrawDeck().size());
    	
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
    	assertEquals(94, game.getDrawDeck().size());
    	
    	//make sure each player has 8 cards after the game has started
    	assertEquals(8, player1.getCards().size());
    	assertEquals(8, player2.getCards().size());
    	
    	//make sure that the round does not yet have a colour
    	assertNull(game.getTournamentColour());
    }
    
    @After
    public void tearDown() {
    	System.out.println("@After(): end game \n");
    }
    
    @Test
    public void test1Player1() {
    	//test that we have the correct current player
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());
    	
    	//test that the current player picks up a card at the beginning of their turn
    	Card pickup = new ColourCard("yellow", 4);
    	game.removeCardfromDeck(pickup);
    	player1Cards.add(pickup);
    	assertEquals(9, player1Cards.size());
    	assertEquals(8, player2Cards.size());
    	
    	game.startTurn();
    	
    	//test that the first player chooses the tournament colour
    	player1.chooseTournamentColour("yellow");
    	assertEquals("yellow", game.getTournamentColour());
    	
    	
    	game.playCard(player1Cards.get(2));
    	game.playCard(player1Cards.get(4));
    	game.playCard(player1Cards.get(5));
    	game.playCard(pickup);
    	game.playCard(player1Cards.get(7));
    	game.endTurn();
    	
    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(18, player1.getTotalCardValue());
    	assertEquals(0, player2.getTotalCardValue());
    	assertTrue(player1.getTotalCardValue() > player2.getTotalCardValue());
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(4, player1Cards.size());
    }
    
    @Test
    public void test2Player2() {
    	//make sure that we have the correct current player
    	assertEquals(player2.getName(), game.getCurrentPlayer().getName());
    	
    	//test that the current player picks up a card at the beginning of their turn
    	Card pickup = new ColourCard("green", 1);
    	game.removeCardfromDeck(pickup);
    	player1Cards.add(pickup);
    	assertEquals(4, player1Cards.size());
    	assertEquals(9, player2Cards.size());
    	
    	game.startTurn();
    	
    	game.playCard(player2Cards.get(5)); //tournament colour is green and cards are each worth 1 point
    	game.playCard(player2Cards.get(0));
    	game.playCard(player2Cards.get(1));
    	game.playCard(player2Cards.get(4));
    	game.playCard(pickup);
    	game.playCard(player2Cards.get(6));
    	game.playCard(player2Cards.get(7));
    	game.endTurn();

    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(5, player1.getTotalCardValue());
    	assertEquals(6, player2.getTotalCardValue());
    	assertTrue(player2.getTotalCardValue() > player1.getTotalCardValue());
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(2, player2Cards.size());
    }
    
    @Test
    public void test3Player1() {
    	//make sure that we have the correct current player
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());
    	
    	//test that the current player picks up a card at the beginning of their turn
    	Card pickup = new ColourCard("blue", 4);
    	game.removeCardfromDeck(pickup);
    	player1Cards.add(pickup);
    	assertEquals(5, player1Cards.size());
    	assertEquals(2, player2Cards.size());
    	
    	game.startTurn();
    	
    	game.playCard(player1Cards.get(3)); //player1 plays riposte and takes player2's last played card, a squire of 3
    	game.endTurn();
    	
    	//test the current player's total card value and that it is greater than the value of the other player
    	assertEquals(6, player1.getTotalCardValue());
    	assertEquals(5, player2.getTotalCardValue());
    	assertTrue(player2.getTotalCardValue() > player1.getTotalCardValue());
    	
    	//test that the current player has the correct number of cards left
    	assertEquals(3, player1Cards.size());
    }
    
    @Test
    public void test4Player2() {
    	//make sure that we have the correct current player
    	assertEquals(player2.getName(), game.getCurrentPlayer().getName());
    	
    	//test that the current player picks up a card at the beginning of their turn
    	Card pickup = new ColourCard("red", 3);
    	game.removeCardfromDeck(pickup);
    	player2Cards.add(pickup);
    	assertEquals(3, player1Cards.size());
    	assertEquals(3, player2Cards.size());
    	
    	game.startTurn(); //current player has no playable cards and is withdrawn
    }
    
    @Test
    public void test5Player1() {
    	//make sure that we have the correct current player
    	assertEquals(player1.getName(), game.getCurrentPlayer().getName());
    	
    	//test that the current player picks up a card at the beginning of their turn
    	Card pickup = new ColourCard("red", 3);
    	game.removeCardfromDeck(pickup);
    	player1Cards.add(pickup);
    	assertEquals(4, player1Cards.size());
    	assertEquals(3, player2Cards.size());
    	
    	game.startTurn(); //player1 is the last remaining, and is announced as the winner
    }
    
    
}
