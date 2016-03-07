package junittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import config.Config;
import game.Card;
import game.GameEngine;
import game.Player;
import game.SupportCard;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class TestGameStart2Player {
	GameEngine game;
	static int testNumber = 0;

	@BeforeClass
    public static void BeforeClass() {
        System.out.println("@BeforeClass: Testing startup of a new game");
        //These tests choose available cards automatically and ensure proper setup of a game
    }
	
    @Before
    public void setUp() {
		System.out.println("@Before(): Setting up 2 player startup test");
		game = new GameEngine();
		
    	//add 2 players to the game
    	Player player1 = new Player("Katie");
    	Player player2 = new Player("Kelly");
    	game.joinGame(player1);
    	game.joinGame(player2);

		System.out.println("startGame");
    	game.startGame();
    	
    	
	}
    
    @After
    public void tearDown() {
    	System.out.println("@After(): end game \n");
    }
    
    @Test
    public void test1StartGame() {
    	System.out.println("@Test(): Starting game");

    	//players randomly pick colour tokens to determine who starts the game
    	game.pickTokens();
    	game.startGame();

    	//get the first (only) two players from the players array, and ensure that the number of players is correct
    	Player player1 =  game.getPlayers().get(0);
    	Player player2 = game.getPlayers().get(1);
    	int players = game.getPlayers().size();
    	assertEquals(2, players);
    	int totalDeck = game.getDrawDeck().size() + player1.getCards().size() + player2.getCards().size();
    	assertEquals(110, totalDeck);

    	//make sure that the current player is the first in the players array 
    	assertEquals(game.getCurrentPlayer(), player1);
    	
    	//make sure each player has 8 cards after the game has started
    	assertEquals(8, player1.getCards().size());
    	assertEquals(8, player2.getCards().size());
    	
    	//make sure that the round does not yet have a colour
    	assertNull(game.getTournamentColour());
    }
    
    @Test
    public void test2SetColour() {
    	//the first player is the 0th item in the players list
    	Player player = game.getCurrentPlayer();
    	
    	//the first player should be prompted to pick a round colour
    	//for this test, we will choose the first of the possible choices that the player has based on their cards
    	String colour = player.getColourPossibilities().get(0);
    	player.chooseTournamentColour(colour);
    	
    	//set the round colour and make sure that it is in fact the colour that was chosen by the player
    	game.setTournamentColour(colour);
    	assertEquals(colour, game.getTournamentColour());
    }
    
    @Test
    public void test3FirstPlay() {
    	Player player = game.getCurrentPlayer();
    	
    	//for this test, we will choose the first playable card and all subsequent playable cards
    	for (Card cardToPlay: player.getPlayPossibilities(game)) {
    		game.playCard(cardToPlay);
    		player.getPlayPossibilities(game).remove(cardToPlay);
    	}
    	
    	//Test that the first player now has cards on their display
    	assertTrue(player.getDisplay().size() > 0);
    	
    	//Test that the first player now has a total card value greater than 0
    	assertTrue(player.getTotalCardValue() > 0);
    	
    	game.endTurn();	
    	
    	//make sure that ending the first players turn shifts current player to the next player
    	assertTrue(game.getCurrentPlayer() != player);
    	assertEquals(game.getCurrentPlayer(), game.getPlayers().get(1));
    }
    
    @Test
    public void test4SecondPlay() {
    	Player player = game.getCurrentPlayer();
    	game.setTournamentColour(Config.PURPLE);
    	//for this test, we will choose the first playable card and all subsequent playable cards
    	for (Card cardToPlay: player.getPlayPossibilities(game)) {
    		game.playCard(cardToPlay);
    		player.getPlayPossibilities(game).remove(cardToPlay);
    	}
    	

    	player.addCard(new SupportCard(Config.MAIDEN, 6));
    	player.addCard(new SupportCard(Config.MAIDEN, 6));
    	String maiden1 = game.processPlay("play maiden 6");
    	String maiden2 = game.processPlay("play maiden 6");
    	
    	assertEquals("waiting maiden_6", maiden1);
    	assertEquals(Config.UNPLAYABLE, maiden2);
       	
    	//Test that the first player now has cards on their display
    	assertTrue(player.getDisplay().size() > 0);
    	
    	//Test that the first player now has a total card value greater than 0
    	assertTrue(player.getTotalCardValue() > 0);

    	
    	game.endTurn();	
    	
    	//make sure that ending the first players turn shifts current player to the next player
    	assertTrue(game.getCurrentPlayer() != player);
    	assertEquals(game.getCurrentPlayer(), game.getPlayers().get(1));
    	
    }
 
}
