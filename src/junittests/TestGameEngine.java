package junittests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import config.Config;
import game.Card;
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
	
	
	//@Test
	public void testOrder(){
		System.out.println("@Test: check the order of players");
	}
	
	//@Test
	public void testSupporters(){
		System.out.println("@Test: Check supporter cards");
	}
	
	//@Test
	public void testTotalValue(){
		System.out.println("@Test: tests that the current player has the largest total value or withdraws");
	}
	
	//@Test
	public void testWinnerToken(){
		System.out.println("@Test: the winner of a tournament gets a token");
	}
	
	@Test
	public void testRedTournament(){
		System.out.println("@Test: testing red tournament");
		player1.chooseTournamentColour(Config.RED);
		game.startTurn();
		assertEquals(Config.RED, game.getTournamentColour());
	}
	
	@Test
	public void testYellowTournament(){
		System.out.println("@Test: testing yellow tournament");
		player1.chooseTournamentColour(Config.YELLOW);
		game.startTurn();
		assertEquals(Config.YELLOW, game.getTournamentColour());
	}

	@Test
	public void testBlueTournament(){
		System.out.println("@Test: testing blue tournament");
		player3.chooseTournamentColour(Config.BLUE);
		game.startTurn();
		assertEquals(Config.BLUE, game.getTournamentColour());
	}
	
	@Test
	public void testGreenTournament(){
		System.out.println("@Test: testing green tournament");
		player4.chooseTournamentColour(Config.GREEN);
		game.startTurn();
		assertEquals(Config.GREEN, game.getTournamentColour());
	}
	
	@Test
	public void testPurpleTournament(){
		System.out.println("@Test: testing purple tournament");
		player5.chooseTournamentColour(Config.PURPLE);
		game.startTurn();
		assertEquals(Config.PURPLE, game.getTournamentColour());
	}
}
