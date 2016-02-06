package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import game.Card;
import game.GameEngine;
import game.Player;

public class TestGameStart2Player {
	GameEngine game;
	static int testNumber = 0;

	@BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: Testing the game Rules Engine and Player");
    }
	
    @Before
    public void setUp() {
    	testNumber ++;
		System.out.println("@Before(): Setting up 2 player test number " + testNumber);
		game = new GameEngine();
		
		System.out.println("startGame");
    	game.startGame();
    	
    	//add 2 players to the game
    	Player player1 = new Player();
    	Player player2 = new Player();
    	game.joinGame(player1);
    	game.joinGame(player2);
	}
    
    @After
    public void tearDown() {
    	System.out.println("@After(): end game \n");
    }
    
    @Test
    public void testStartGame() {
    	System.out.println("@Test(): Starting game");
    	
    	//players randomly pick colour tokens to determine who starts the game
    	game.pickTokens();
    	game.startGame();

    	//get the first (only) two players from the players array, and ensure that the number of players is correct
    	Player player1 =  game.getPlayers().get(0);
    	Player player2 = game.getPlayers().get(1);
    	int players = game.getNumPlayers();
    	assertEquals(2, players);

    	//make sure that the current player is the first in the players array 
    	assertEquals(game.getCurrentPlayer(), player1);
    	
    	//make sure each player has 8 cards after the game has started
    	assertEquals(8, player1.getCards().size());
    	assertEquals(8, player2.getCards().size());
    	
    	//make sure that the first player in the players array is the one that picked purple, and the second player did not
    	assertEquals("purple", player1.getStartTokenColour());
    	assertNotEquals("purple", player2.getStartTokenColour());
    	
    	//make sure that the round does not yet have a colour
    	assertNull(game.getRoundColour());
    }
    
    @Test
    public void testSetColour() {
    	//the first player is the 0th item in the players list
    	Player player = game.getCurrentPlayer();
    	
    	//the first player should be prompted to pick a round colour
    	//for this test, we will choose the first of the possible choices that the player has based on their cards
    	String colour = player.chooseRoundColour(player.getColourPossibilities().get(0));
    	
    	//set the round colour and make sure that it is in fact the colour that was chosen by the player
    	game.setRoundColour(colour);
    	assertEquals(colour, game.getRoundColour());
    }
    
    @Test
    public void testFirstPlay() {
    	Player player = game.getCurrentPlayer();
    	
    	//for this test, we will choose the first playable card and all subsequent playable cards
    	for (Card cardToPlay: player.getPlayPossibilities()) {
    		game.playCard(player, cardToPlay);
    		player.getPlayPossibilities().remove(cardToPlay);
    	}
    	
    	//Test that the first player now has cards on their display
    	assertTrue(player.getDisplay().size() > 0);
    	
    	//Test that the first player now has a total card value greater than 0
    	assertTrue(player.getTotalCardValue() > 0);
    }
    
    @Test
    public void testEndTurn() {
    	Player player = game.getCurrentPlayer();
    	game.endTurn(player);	
    	
    	//make sure that ending the first players turn shifts current player to the next player
    	assertTrue(game.getCurrentPlayer() != player);
    	assertEquals(game.getCurrentPlayer(), game.getPlayers().get(1));
    }

}
