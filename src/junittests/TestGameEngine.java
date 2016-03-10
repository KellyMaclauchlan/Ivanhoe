package junittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import config.Config;
import game.Card;
import game.GameEngine;
import game.Player;
import game.SupportCard;

public class TestGameEngine {
	GameEngine game;
	
	@BeforeClass
	public static void BeforeClass(){
		System.out.println("@BeforeClass: TestGameEngine");
	}
	
	@Before
	public void setUp(){
		System.out.println("@Before: TestGameEngine");
		
		game = new GameEngine();
		Player player1 = new Player("Katie");
		Player player2 = new Player("Brit");
		Player player3 = new Player("Kelly");
		Player player4 = new Player("Ahmed");
		Player player5 = new Player("Mir");
		
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
	}
	
	@After
	public void tearDown(){
		System.out.println("@After: end game");
	}
	
	@Test
	public void testStartTournament(){
		System.out.println("@Test: Start Tournament");
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
	
	
	@Test
	public void testOrder(){
		System.out.println("@Test: check the order of players");
		
		
		
	}
	
	@Test
	public void testSupporters(){
		System.out.println("@Test: Check supporter cards");
	}
	
	@Test
	public void testTotalValue(){
		System.out.println("@Test: tests that the current player has the largest total value or withdraws");
	}
	
	@Test
	public void testWinnerToken(){
		System.out.println("@Test: the winner of a tournament gets a token");
	}
	
	@Test
	public void testRedTournament(){
		System.out.println("@Test: testing red tournament");
	}
	
	@Test
	public void testYellowTournament(){
		System.out.println("@Test: testing yellow tournament");
	}

	@Test
	public void testBlueTournament(){
		System.out.println("@Test: testing blue tournament");
	}
	
	@Test
	public void testGreenTournament(){
		System.out.println("@Test: testing green tournament");
	}
	
	@Test
	public void testPurpleTournament(){
		System.out.println("@Test: testing purple tournament");
	}
}
