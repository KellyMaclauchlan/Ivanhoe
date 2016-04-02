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

public class TestActionCards {
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
    	player1Cards.add(new ActionCard(Config.UNHORSE));
    	player1Cards.add(new ActionCard(Config.DROPWEAPON));
    	player1Cards.add(new ActionCard(Config.CHANGEWEAPON));
    	player1Cards.add(new ColourCard(Config.PURPLE, 4));
    	player1Cards.add(new ColourCard(Config.PURPLE, 5));
    	player1Cards.add(new ColourCard(Config.PURPLE, 4));
    	player1Cards.add(new ColourCard(Config.YELLOW, 3));
    	player1Cards.add(new SupportCard(Config.SQUIRE, 2));
    	player1.setCards(player1Cards);
    	game.removeAllFromDeck(player1Cards);
    	
    	ArrayList<Card> player2Cards = new ArrayList<>();
    	player2Cards.add(new ActionCard(Config.BREAKLANCE));
    	player2Cards.add(new ActionCard(Config.RIPOSTE));
    	player2Cards.add(new ActionCard(Config.DODGE));
    	player2Cards.add(new ColourCard(Config.YELLOW, 4));
    	player2Cards.add(new ColourCard(Config.YELLOW, 4));
    	player2Cards.add(new ColourCard(Config.YELLOW, 5));
    	player2Cards.add(new SupportCard(Config.SQUIRE, 2));
    	player2Cards.add(new SupportCard(Config.SQUIRE, 3));
    	player2.setCards(player2Cards);
    	game.removeAllFromDeck(player2Cards);
    	
    	ArrayList<Card> player3Cards = new ArrayList<>();
    	player3Cards.add(new ActionCard(Config.RETREAT));
    	player3Cards.add(new ActionCard(Config.KNOCKDOWN));
    	player3Cards.add(new ActionCard(Config.OUTMANEUVER));
    	player3Cards.add(new ColourCard(Config.YELLOW, 4));
    	player3Cards.add(new ColourCard(Config.PURPLE, 4));
    	player3Cards.add(new ColourCard(Config.YELLOW, 4));
    	player3Cards.add(new ColourCard(Config.YELLOW, 4));
    	player3Cards.add(new ColourCard(Config.PURPLE, 4));
    	player3.setCards(player3Cards);
    	game.removeAllFromDeck(player3Cards);
    	
    	ArrayList<Card> player4Cards = new ArrayList<>();
    	player4Cards.add(new ActionCard(Config.CHARGE));
    	player4Cards.add(new ActionCard(Config.COUNTERCHARGE));
    	player4Cards.add(new ActionCard(Config.DISGRACE));
    	player4Cards.add(new ColourCard(Config.PURPLE, 4));
    	player4Cards.add(new ColourCard(Config.GREEN, 1));
    	player4Cards.add(new ColourCard(Config.YELLOW, 4));
    	player4Cards.add(new ColourCard(Config.YELLOW, 4));
    	player4Cards.add(new SupportCard(Config.MAIDEN, 6));
    	
    	player4.setCards(player4Cards);
    	game.removeAllFromDeck(player4Cards);
    	
    	
    	ArrayList<Card> player5Cards = new ArrayList<>();
    	player5Cards.add(new ActionCard(Config.ADAPT));
    	player5Cards.add(new ActionCard(Config.OUTWIT));
    	player5Cards.add(new ActionCard(Config.SHIELD));
    	player5Cards.add(new ActionCard(Config.STUNNED));
    	player5Cards.add(new ColourCard(Config.PURPLE, 4));
    	player5Cards.add(new SupportCard(Config.PURPLE, 5));
    	player5Cards.add(new ColourCard(Config.PURPLE, 4));
    	player5Cards.add(new ColourCard(Config.MAIDEN, 6));
    	player5.setCards(player5Cards);
    	game.removeAllFromDeck(player5Cards);
	}
	
	@After
	public void tearDown(){
		System.out.println("@After: end game");
		game.resetPlayers();
	}
	
	@Test
	public void testUnhorse(){
		System.out.println("@Test: testing unhorse");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();
		String unhorse = game.processPlay("play unhorse yellow");
		
		//test tournament colour
		assertEquals("waiting unhorse yellow", unhorse);
		assertEquals(Config.YELLOW, game.getTournamentColour());
		
		//test discard
		assertEquals(7, player1.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
	}
	@Test
	public void testUnhorseShielded(){
		System.out.println("@Test: testing unhorse shielded");
		
		player5.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player5.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player5);
		game.startTurn();
		String shield = game.processPlay("play shield");
		
		assertEquals("waiting shield " + player5.getName(), shield);

		game.setCurrentPlayer(player1);
		game.startTurn();
		String unhorse = game.processPlay("play unhorse yellow");
		
		//test discard
		assertEquals(7, player1.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		//test tournament colour
		assertEquals("waiting unhorse yellow", unhorse);
		assertEquals(Config.YELLOW, game.getTournamentColour());
	}
	@Test
	public void testIvanhoeUnhorse(){
		System.out.println("@Test: testing unhorse");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		player2.addCard(new ActionCard(Config.IVANHOE));

		game.setCurrentPlayer(player1);
		game.startTurn();
		String unhorse = game.processPlay("play unhorse yellow");
		assertEquals("waiting plyivnhoe " + player2.getName() + " unhorse", unhorse);
		
		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe unhorse");
		assertEquals("waiting ivanhoe " + player1.getName(), ivanhoe);
		
		//test tournament colour
		assertNotEquals(Config.YELLOW, game.getTournamentColour());
		
		//test discard
		assertEquals(7, player1.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
	}
	@Test
	public void testChangeWeapon(){
		System.out.println("@Test: testing changeweapon");
		player1.chooseTournamentColour(Config.RED);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.RED, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();
		String changeWeapon = game.processPlay("play changeweapon yellow");
		
		//test discard
		assertEquals(7, player1.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		//test tournamentcolour
		assertEquals("waiting changeweapon yellow", changeWeapon);
		assertEquals(Config.YELLOW, game.getTournamentColour());
	}
	@Test
	public void testChangeWeaponShielded(){
		System.out.println("@Test: testing changeweapon shielded");
		
		player5.chooseTournamentColour(Config.RED);
		game.setTournamentColour(player5.getTournamentColour());
		assertEquals(Config.RED, game.getTournamentColour());
		
		game.setCurrentPlayer(player5);
		game.startTurn();
		String shield = game.processPlay("play shield");
		assertEquals("waiting shield " + player5.getName(), shield);

		game.setCurrentPlayer(player1);
		game.startTurn();
		String changeWeapon = game.processPlay("play changeweapon yellow");
		
		//test discard
		assertEquals(7, player1.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		//test tournament colour
		assertEquals("waiting changeweapon yellow", changeWeapon);
		assertEquals(Config.YELLOW, game.getTournamentColour());
	}
	@Test
	public void testIvanhoeChangeWeapon(){
		System.out.println("@Test: testing changeweapon");
		player1.chooseTournamentColour(Config.RED);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.RED, game.getTournamentColour());
		player2.addCard(new ActionCard(Config.IVANHOE));

		game.setCurrentPlayer(player1);
		game.startTurn();
		String changeWeapon = game.processPlay("play changeweapon yellow");
		assertEquals("waiting plyivnhoe " + player2.getName() + " changeweapon", changeWeapon);

		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe changeweapon");
		assertEquals("waiting ivanhoe " + player1.getName(), ivanhoe);
		
		//test discard
		assertEquals(7, player1.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		//test tournamentcolour
		assertNotEquals(Config.YELLOW, game.getTournamentColour());
	}
	@Test
	public void testDropWeapon(){
		System.out.println("@Test: testing dropeweapon");
		player1.chooseTournamentColour(Config.RED);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.RED, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();
		String dropWeapon = game.processPlay("play dropweapon");
		
		//test discard
		assertEquals(7, player1.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		//test tournament colour
		assertEquals("waiting dropweapon green", dropWeapon);
		assertEquals(Config.GREEN, game.getTournamentColour());
	}
	@Test
	public void testDropWeaponShielded(){
		System.out.println("@Test: testing dropweapon shielded");
		
		player5.chooseTournamentColour(Config.RED);
		game.setTournamentColour(player5.getTournamentColour());
		assertEquals(Config.RED, game.getTournamentColour());
		
		game.setCurrentPlayer(player5);
		game.startTurn();
		String shield = game.processPlay("play shield");
		assertEquals("waiting shield " + player5.getName(), shield);

		game.setCurrentPlayer(player1);
		game.startTurn();
		String dropWeapon = game.processPlay("play dropweapon");
		
		//test discard
		assertEquals(7, player1.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		//test tournament colour
		assertEquals("waiting dropweapon green", dropWeapon);
		assertEquals(Config.GREEN, game.getTournamentColour());
	}
	@Test
	public void testIvanhoeDropWeapon(){
		System.out.println("@Test: testing dropeweapon");
		player1.chooseTournamentColour(Config.RED);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.RED, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();
		player2.addCard(new ActionCard(Config.IVANHOE));

		String dropWeapon = game.processPlay("play dropweapon");
		assertEquals("waiting plyivnhoe " + player2.getName() + " dropweapon", dropWeapon);

		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe dropweapon");
		assertEquals("waiting ivanhoe " + player1.getName(), ivanhoe);
		
		//test discard
		assertEquals(7, player1.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		//test tournament colour
		assertNotEquals(Config.GREEN, game.getTournamentColour());
	}
	public void testBreakance(){
		System.out.println("@Test: testing breaklance");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 5));
		assertEquals(13, player1.getTotalCardValue());		
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		
		String breaklance = game.processPlay("play breaklance " + player1.getName());
		
		//test message passed
		assertEquals("waiting breaklance disply name " + player1.getName() + " " + player1.getTotalCardValue() 
		+ " cards purple 5", breaklance);
		
		//test discard 
		assertEquals(7, player2.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		//test action card results
		assertEquals(1, player1.getDisplay().size());
		assertEquals(5, player1.getTotalCardValue());
	}
	@Test
	public void testBreaklanceShielded(){
		System.out.println("@Test: testing breaklance shielded");
		
		player5.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player5.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player5);
		game.startTurn();
		String shield = game.processPlay("play shield");
		assertEquals("waiting shield " + player5.getName(), shield);
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		
		String breaklance = game.processPlay("play breaklance " + player5.getName());
		
		//test discard (none because of shield)
		assertEquals(8, player2.getCards().size());
		assertEquals(0, game.getDiscardPile().size());
		
		//test message passed
		assertEquals(Config.WAITING + " " + Config.UNPLAYABLE, breaklance);
		
		//test action results
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
	}
	@Test
	public void testIvanhoeBreakance(){
		System.out.println("@Test: testing breaklance");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();
		player1.addCard(new ActionCard(Config.IVANHOE));

		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 5));
		assertEquals(13, player1.getTotalCardValue());		
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		
		//prompt ivanhoe
		String breaklance = game.processPlay("play breaklance " + player1.getName());
		assertEquals("waiting plyivnhoe " + player1.getName() + " breaklance", breaklance);

		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe riposte");
		assertEquals("waiting ivanhoe " + player2.getName(), ivanhoe);
		
		//test discard 
		assertEquals(7, player2.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		//test action card results
		assertEquals(3, player1.getDisplay().size());
		assertEquals(13, player1.getTotalCardValue());
	}
	@Test
	public void testRiposte(){
		System.out.println("@Test: testing riposte");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 5));

		assertEquals(13, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		
		String riposte = game.processPlay("play riposte " + player1.getName());
		
		//test discard
		assertEquals(7, player2.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		//test message passed
		assertEquals("waiting riposte " + player1.getName() + " " + player1.getTotalCardValue() 
		+ " purple 5 " + player2.getName() + " " + player2.getTotalCardValue(), riposte);
		
		//test action results
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());
		assertEquals(1, player2.getDisplay().size());
		assertEquals(5, player2.getTotalCardValue());
	}
	@Test
	public void testRiposteShielded(){
		System.out.println("@Test: testing riposte shielded");
		
		player5.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player5.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player5);
		game.startTurn();
		String shield = game.processPlay("play shield");
		assertEquals("waiting shield " + player5.getName(), shield);
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));

		game.setCurrentPlayer(player2);
		game.startTurn();		
		String riposte = game.processPlay("play riposte " + player5.getName());
		
		//test discard (none because of shield)
		assertEquals(8, player2.getCards().size());
		assertEquals(0, game.getDiscardPile().size());
		
		//test message passed
		assertEquals(Config.WAITING + " " + Config.UNPLAYABLE, riposte);
		
		//test action results
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		assertEquals(0, player2.getDisplay().size());
		assertEquals(0, player2.getTotalCardValue());
	}
	@Test
	public void testIvanhoeRiposte(){
		System.out.println("@Test: testing riposte");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();
		player1.addCard(new ActionCard(Config.IVANHOE));

		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 5));

		assertEquals(13, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		
		//prompt ivanhoe
		String riposte = game.processPlay("play riposte " + player1.getName());
		assertEquals("waiting plyivnhoe " + player1.getName() + " riposte", riposte);
		
		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe riposte");
		assertEquals("waiting ivanhoe " + player2.getName(), ivanhoe);
		
		//test discard
		assertEquals(7, player2.getCards().size());
		assertEquals(2, game.getDiscardPile().size());

		//test action results
		assertEquals(3, player1.getDisplay().size());
		assertEquals(13, player1.getTotalCardValue());
		assertEquals(0, player2.getDisplay().size());
		assertEquals(0, player2.getTotalCardValue());
	}
	@Test
	public void testDodge(){
		System.out.println("@Test: testing dodge");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 5));

		assertEquals(13, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		
		String dodge = game.processPlay("play dodge " + player1.getName() + " purple 5");
		
		//test discard
		assertEquals(7, player2.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		//test message passed
		assertEquals("waiting dodge " + player1.getName() + " " + player1.getTotalCardValue() 
		+ " purple 5", dodge);
		
		//test action results
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());
		assertEquals(0, player2.getDisplay().size());
		assertEquals(0, player2.getTotalCardValue());
	}
	
	@Test
	public void testDodgeShielded(){
		System.out.println("@Test: testing dodge shielded");
		
		player5.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player5.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player5);
		game.startTurn();
		String shield = game.processPlay("play shield");
		assertEquals("waiting shield " + player5.getName(), shield);
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));

		game.setCurrentPlayer(player2);
		game.startTurn();		
		String dodge = game.processPlay("play dodge " + player5.getName() + " purple 5");
		
		//test discard (none because of shield)
		assertEquals(8, player2.getCards().size());
		assertEquals(0, game.getDiscardPile().size());
		
		//test message passed
		assertEquals(Config.WAITING + " " + Config.UNPLAYABLE, dodge);
		
		//test action results
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		assertEquals(0, player2.getDisplay().size());
		assertEquals(0, player2.getTotalCardValue());
	}

	@Test
	public void testIvanhoeDodge(){
		System.out.println("@Test: testing dodge");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();
		player1.addCard(new ActionCard(Config.IVANHOE));

		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 5));

		assertEquals(13, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		
		String dodge = game.processPlay("play dodge " + player1.getName() + " purple 5");
		assertEquals("waiting plyivnhoe " + player1.getName() + " dodge", dodge);
		
		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe dodge");
		assertEquals("waiting ivanhoe " + player2.getName(), ivanhoe);
		
		//test discard
		assertEquals(7, player2.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		//test action results
		assertEquals(3, player1.getDisplay().size());
		assertEquals(13, player1.getTotalCardValue());
		assertEquals(0, player2.getDisplay().size());
		assertEquals(0, player2.getTotalCardValue());
	}
	@Test
	public void testRetreat(){
		System.out.println("@Test: testing retreat");
		player3.chooseTournamentColour(Config.YELLOW);
		game.setTournamentColour(player3.getTournamentColour());
		assertEquals(Config.YELLOW, game.getTournamentColour());
		
		game.setCurrentPlayer(player3);
		game.startTurn();		
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		String retreat = game.processPlay("play retreat yellow 4");
		
		// only one card in display
		assertEquals("waiting " + Config.UNPLAYABLE, retreat);
		assertEquals(1, player3.getDisplay().size());
		assertEquals(4, player3.getTotalCardValue());
		
		
		//test discard (none, only one card in display)
		assertEquals(7, player3.getCards().size());
		assertEquals(0, game.getDiscardPile().size());
		
		// second card in display
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		assertEquals(2, player3.getDisplay().size());
		assertEquals(8, player3.getTotalCardValue());
		retreat = game.processPlay("play retreat yellow 4");
		assertEquals("waiting retreat " + player3.getName() + " " + player3.getTotalCardValue() 
		+ " yellow 4", retreat);
		
		//test discard (second colour card placed back into hand, retreat discarded)
		assertEquals(6, player3.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		
		assertEquals(1, player3.getDisplay().size());
		assertEquals(4, player3.getTotalCardValue());
	}
	
	@Test
	public void testRetreatShielded(){
		System.out.println("@Test: testing retreat shielded");
		
		player3.chooseTournamentColour(Config.YELLOW);
		game.setTournamentColour(player3.getTournamentColour());
		assertEquals(Config.YELLOW, game.getTournamentColour());
		
		game.setCurrentPlayer(player3);
		game.startTurn();		
		
		player3.addCard(new ActionCard(Config.SHIELD));
		String shield = game.processPlay("play shield");
		assertEquals("waiting shield " + player3.getName(), shield);
		
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		String retreat = game.processPlay("play retreat yellow 4");
		
		//test discard (none because of shield)
		assertEquals(7, player3.getCards().size());
		assertEquals(0, game.getDiscardPile().size());
		
		// only one card in display
		assertEquals("waiting " + Config.UNPLAYABLE, retreat);
		assertEquals(1, player3.getDisplay().size());
		assertEquals(4, player3.getTotalCardValue());
		
		// second card in display (retreat should still work with shield)
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		assertEquals(2, player3.getDisplay().size());
		assertEquals(8, player3.getTotalCardValue());
		retreat = game.processPlay("play retreat yellow 4");
		assertEquals("waiting retreat " + player3.getName() + " " + player3.getTotalCardValue() 
		+ " yellow 4", retreat);
		
		//test discard
		assertEquals(6, player3.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		//test action results
		assertEquals(1, player3.getDisplay().size());
		assertEquals(4, player3.getTotalCardValue());
	}
	
	@Test
	public void testIvanhoeRetreat(){
		System.out.println("@Test: testing retreat");
		player3.chooseTournamentColour(Config.YELLOW);
		game.setTournamentColour(player3.getTournamentColour());
		assertEquals(Config.YELLOW, game.getTournamentColour());
		
		game.setCurrentPlayer(player3);
		game.startTurn();		
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		String retreat = game.processPlay("play retreat yellow 4");
		
		// only one card in display
		assertEquals("waiting " + Config.UNPLAYABLE, retreat);
		assertEquals(1, player3.getDisplay().size());
		assertEquals(4, player3.getTotalCardValue());
		
		
		//test discard (none, only one card in display)
		assertEquals(7, player3.getCards().size());
		assertEquals(0, game.getDiscardPile().size());
		player3.addCard(new ActionCard(Config.IVANHOE));

		// second card in display
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		assertEquals(2, player3.getDisplay().size());
		assertEquals(8, player3.getTotalCardValue());
		String prompt = game.processPlay("play retreat yellow 4");
		assertEquals("waiting plyivnhoe " + player3.getName() + " retreat", prompt);

		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe outmaneuver");
		assertEquals("waiting ivanhoe " + player3.getName(), ivanhoe);
		
		//test discard (second colour card placed back into hand, retreat discarded)
		assertEquals(5, player3.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		assertEquals(2, player3.getDisplay().size());
		assertEquals(8, player3.getTotalCardValue());
	}
	
	@Test
	public void testOutmaneuver(){
		System.out.println("@Test: testing outmaneuver");
		player1.chooseTournamentColour(Config.YELLOW);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.YELLOW, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		game.playCard(player1.getCardFromHand(Config.YELLOW, 3));
		
		assertEquals(1, player1.getDisplay().size());
		assertEquals(3, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		game.playCard(player2.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player2.getCardFromHand(Config.YELLOW, 4));
		
		assertEquals(2, player2.getDisplay().size());
		assertEquals(8, player2.getTotalCardValue());

		game.setCurrentPlayer(player3);
		game.startTurn();	
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));

		assertEquals(2, player3.getDisplay().size());
		assertEquals(8, player3.getTotalCardValue());
		
		game.processPlay("play outmaneuver");
		
		//test discard (played card and card from display)
		assertEquals(5, player3.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		// player 1: only one card in display
		assertEquals(1, player1.getDisplay().size());
		assertEquals(3, player1.getTotalCardValue());
		
		// player 2: 2 cards in display, one discarded 
		assertEquals(1, player2.getDisplay().size());
		assertEquals(4, player2.getTotalCardValue());
		
		// player 3: should not be affected by this card
		assertEquals(2, player3.getDisplay().size());
		assertEquals(8, player3.getTotalCardValue());
	}
	
	@Test
	public void testOutmaneuverShielded(){
		System.out.println("@Test: testing outmaneuver shielded");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		
		assertEquals(1, player1.getDisplay().size());
		assertEquals(4, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		game.processPlay("play shield");
		assertEquals(2, player5.getDisplay().size());
		assertEquals(9, player5.getTotalCardValue());

		game.setCurrentPlayer(player3);
		game.startTurn();	
		game.playCard(player3.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player3.getCardFromHand(Config.PURPLE, 4));

		assertEquals(2, player3.getDisplay().size());
		assertEquals(8, player3.getTotalCardValue());
		
		game.processPlay("play outmaneuver");
		
		//test discard
		assertEquals(5, player3.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		
		// player 1: only one card in display
		assertEquals(1, player1.getDisplay().size());
		assertEquals(4, player1.getTotalCardValue());
		
		// player 5: shielded and unaffected
		assertEquals(2, player5.getDisplay().size());
		assertEquals(9, player5.getTotalCardValue());
		
		// player 3: should not be affected by this card
		assertEquals(2, player3.getDisplay().size());
		assertEquals(8, player3.getTotalCardValue());
	}

	@Test
	public void testIvanhoeOutmaneuver(){
		System.out.println("@Test: testing outmaneuver");
		player1.chooseTournamentColour(Config.YELLOW);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.YELLOW, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		game.playCard(player1.getCardFromHand(Config.YELLOW, 3));
		
		assertEquals(1, player1.getDisplay().size());
		assertEquals(3, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player2);
		game.startTurn();
		player2.addCard(new ActionCard(Config.IVANHOE));

		game.playCard(player2.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player2.getCardFromHand(Config.YELLOW, 4));
		
		assertEquals(2, player2.getDisplay().size());
		assertEquals(8, player2.getTotalCardValue());

		game.setCurrentPlayer(player3);
		game.startTurn();	
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));

		assertEquals(2, player3.getDisplay().size());
		assertEquals(8, player3.getTotalCardValue());
		
		String prompt = game.processPlay("play outmaneuver");
		assertEquals("waiting plyivnhoe " + player2.getName() + " outmaneuver", prompt);

		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe outmaneuver");
		assertEquals("waiting ivanhoe " + player3.getName(), ivanhoe);
		
		//test discard (played card and card from display)
		assertEquals(5, player3.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		// player 1: only one card in display
		assertEquals(1, player1.getDisplay().size());
		assertEquals(3, player1.getTotalCardValue());
		
		// player 2: none discarded
		assertEquals(2, player2.getDisplay().size());
		assertEquals(8, player2.getTotalCardValue());
		
		// player 3: should not be affected by this card
		assertEquals(2, player3.getDisplay().size());
		assertEquals(8, player3.getTotalCardValue());
	}
	
	@Test
	public void testCharge(){
		System.out.println("@Test: testing charge");
		player2.chooseTournamentColour(Config.YELLOW);
		game.setTournamentColour(player2.getTournamentColour());
		assertEquals(Config.YELLOW, game.getTournamentColour());
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		game.playCard(player2.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player2.getCardFromHand(Config.YELLOW, 5));

		assertEquals(2, player2.getDisplay().size());
		assertEquals(9, player2.getTotalCardValue());
		
		game.setCurrentPlayer(player3);
		game.startTurn();		
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		
		assertEquals(3, player3.getDisplay().size());
		assertEquals(12, player3.getTotalCardValue());

		game.setCurrentPlayer(player4);
		game.startTurn();	
		game.playCard(player4.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player4.getCardFromHand(Config.YELLOW, 4));

		assertEquals(2, player4.getDisplay().size());
		assertEquals(8, player4.getTotalCardValue());
		
		game.processPlay("play charge");
		
		//test discard (four discarded from action)
		assertEquals(5, player4.getCards().size());
		assertEquals(5, game.getDiscardPile().size());
		
		// player 3: 4 discarded, 5 left
		assertEquals(1, player2.getDisplay().size());
		assertEquals(5, player2.getTotalCardValue());
		
		// player 3: 3 cards in display, 2 discarded 
		assertEquals(1, player3.getDisplay().size());
		assertEquals(4, player3.getTotalCardValue());
		
		// player 4: 2 cards in display, 1 discarded
		assertEquals(1, player4.getDisplay().size());
		assertEquals(4, player4.getTotalCardValue());
	}
	
	@Test
	public void testChargeShielded(){
		System.out.println("@Test: testing charge shielded");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		
		assertEquals(1, player1.getDisplay().size());
		assertEquals(4, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		game.processPlay("play shield");
		assertEquals(2, player5.getDisplay().size());
		assertEquals(9, player5.getTotalCardValue());

		game.setCurrentPlayer(player4);
		game.startTurn();	

		assertEquals(0, player4.getDisplay().size());
		assertEquals(0, player4.getTotalCardValue());
		
		game.processPlay("play charge");
		
		//test discard
		assertEquals(7, player4.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		// player 1: only one card in display
		assertEquals(1, player1.getDisplay().size());
		assertEquals(4, player1.getTotalCardValue());
		
		// player 5: shielded and unaffected
		assertEquals(2, player5.getDisplay().size());
		assertEquals(9, player5.getTotalCardValue());
		
		// player 3: did not play any value cards
		assertEquals(0, player4.getDisplay().size());
		assertEquals(0, player4.getTotalCardValue());
	}
	
	@Test
	public void testIvanhoeCharge(){
		System.out.println("@Test: testing charge");
		player2.chooseTournamentColour(Config.YELLOW);
		game.setTournamentColour(player2.getTournamentColour());
		assertEquals(Config.YELLOW, game.getTournamentColour());
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		player2.addCard(new ActionCard(Config.IVANHOE));

		game.playCard(player2.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player2.getCardFromHand(Config.YELLOW, 5));

		assertEquals(2, player2.getDisplay().size());
		assertEquals(9, player2.getTotalCardValue());
		
		game.setCurrentPlayer(player3);
		game.startTurn();		
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		
		assertEquals(3, player3.getDisplay().size());
		assertEquals(12, player3.getTotalCardValue());

		game.setCurrentPlayer(player4);
		game.startTurn();	
		game.playCard(player4.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player4.getCardFromHand(Config.YELLOW, 4));

		assertEquals(2, player4.getDisplay().size());
		assertEquals(8, player4.getTotalCardValue());
		
		//prompt player with ivanhoe to use it
		String prompt = game.processPlay("play charge");
		assertEquals("waiting plyivnhoe " + player2.getName() + " charge", prompt);

		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe charge");
		assertEquals("waiting ivanhoe " + player4.getName(), ivanhoe);
		
		//test discard (four discarded from action)
		assertEquals(5, player4.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		// player 3: none discarded
		assertEquals(2, player2.getDisplay().size());
		assertEquals(9, player2.getTotalCardValue());
		
		// player 3: none discarded
		assertEquals(3, player3.getDisplay().size());
		assertEquals(12, player3.getTotalCardValue());
		
		// player 4: none discarded
		assertEquals(2, player4.getDisplay().size());
		assertEquals(8, player4.getTotalCardValue());
	}
	
	@Test
	public void testCountercharge(){
		System.out.println("@Test: testing countercharge");
		player2.chooseTournamentColour(Config.YELLOW);
		game.setTournamentColour(player2.getTournamentColour());
		assertEquals(Config.YELLOW, game.getTournamentColour());
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		game.playCard(player2.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player2.getCardFromHand(Config.YELLOW, 5));

		assertEquals(2, player2.getDisplay().size());
		assertEquals(9, player2.getTotalCardValue());
		
		game.setCurrentPlayer(player3);
		game.startTurn();		
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		
		assertEquals(3, player3.getDisplay().size());
		assertEquals(12, player3.getTotalCardValue());

		game.setCurrentPlayer(player4);
		game.startTurn();	
		game.playCard(player4.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player4.getCardFromHand(Config.YELLOW, 4));

		assertEquals(2, player4.getDisplay().size());
		assertEquals(8, player4.getTotalCardValue());
		
		game.processPlay("play countercharge");
		
		//test discard
		assertEquals(5, player4.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		// player 3: 5 discarded, 4 left
		assertEquals(1, player2.getDisplay().size());
		assertEquals(4, player2.getTotalCardValue());
		
		// player 3: 3 cards in display, none discarded
		assertEquals(3, player3.getDisplay().size());
		assertEquals(12, player3.getTotalCardValue());
		
		// player 4: 2 cards in display none discarded
		assertEquals(2, player4.getDisplay().size());
		assertEquals(8, player4.getTotalCardValue());
	}
	
	@Test
	public void testCounterchargeShielded(){
		System.out.println("@Test: testing countercharge shielded");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		
		assertEquals(1, player1.getDisplay().size());
		assertEquals(4, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		game.processPlay("play shield");
		assertEquals(2, player5.getDisplay().size());
		assertEquals(9, player5.getTotalCardValue());

		game.setCurrentPlayer(player4);
		game.startTurn();	

		assertEquals(0, player4.getDisplay().size());
		assertEquals(0, player4.getTotalCardValue());
		
		game.processPlay("play countercharge");
		
		//test discard
		assertEquals(7, player4.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		// player 1: only one card in display
		assertEquals(1, player1.getDisplay().size());
		assertEquals(4, player1.getTotalCardValue());
		
		// player 5: shielded and unaffected
		assertEquals(2, player5.getDisplay().size());
		assertEquals(9, player5.getTotalCardValue());
		
		// player 3: did not play any value cards
		assertEquals(0, player4.getDisplay().size());
		assertEquals(0, player4.getTotalCardValue());
	}
	
	@Test
	public void testIvanhoeCountercharge(){
		System.out.println("@Test: testing countercharge");
		player2.chooseTournamentColour(Config.YELLOW);
		game.setTournamentColour(player2.getTournamentColour());
		assertEquals(Config.YELLOW, game.getTournamentColour());
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		player2.addCard(new ActionCard(Config.IVANHOE));

		game.playCard(player2.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player2.getCardFromHand(Config.YELLOW, 5));

		assertEquals(2, player2.getDisplay().size());
		assertEquals(9, player2.getTotalCardValue());
		
		game.setCurrentPlayer(player3);
		game.startTurn();		
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player3.getCardFromHand(Config.YELLOW, 4));
		
		assertEquals(3, player3.getDisplay().size());
		assertEquals(12, player3.getTotalCardValue());

		game.setCurrentPlayer(player4);
		game.startTurn();	
		game.playCard(player4.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player4.getCardFromHand(Config.YELLOW, 4));

		assertEquals(2, player4.getDisplay().size());
		assertEquals(8, player4.getTotalCardValue());
		
		//prompt player with ivanhoe to play it
		String prompt = game.processPlay("play countercharge");
		assertEquals("waiting plyivnhoe " + player2.getName() + " countercharge", prompt);

		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe countercharge");
		assertEquals("waiting ivanhoe " + player4.getName(), ivanhoe);
				
		//test discard
		assertEquals(5, player4.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		// player 3: none discarded
		assertEquals(2, player2.getDisplay().size());
		assertEquals(9, player2.getTotalCardValue());
		
		// player 3: none discarded
		assertEquals(3, player3.getDisplay().size());
		assertEquals(12, player3.getTotalCardValue());
		
		// player 4:  none discarded
		assertEquals(2, player4.getDisplay().size());
		assertEquals(8, player4.getTotalCardValue());
	}
	
	@Test
	public void testDisgrace(){
		System.out.println("@Test: testing disgrace");
		player1.chooseTournamentColour(Config.YELLOW);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.YELLOW, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		game.playCard(player1.getCardFromHand(Config.YELLOW, 3));
		game.playCard(player1.getCardFromHand(Config.SQUIRE, 2));
		
		assertEquals(2, player1.getDisplay().size());
		assertEquals(5, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		game.playCard(player2.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player2.getCardFromHand(Config.YELLOW, 5));
		game.playCard(player2.getCardFromHand(Config.SQUIRE, 2));
		game.playCard(player2.getCardFromHand(Config.SQUIRE, 3));

		assertEquals(4, player2.getDisplay().size());
		assertEquals(14, player2.getTotalCardValue());
		
		game.setCurrentPlayer(player4);
		game.startTurn();	
		game.playCard(player4.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player4.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player4.getCardFromHand(Config.MAIDEN, 6));

		assertEquals(3, player4.getDisplay().size());
		assertEquals(14, player4.getTotalCardValue());
		
		game.processPlay("play disgrace");
		
		//test discard
		assertEquals(4, player4.getCards().size());
		assertEquals(5, game.getDiscardPile().size());
		
		// player 3: 1 squire disgarded
		assertEquals(1, player1.getDisplay().size());
		assertEquals(3, player1.getTotalCardValue());
		
		// player 3: 2 squires disgarded
		assertEquals(2, player2.getDisplay().size());
		assertEquals(9, player2.getTotalCardValue());
		
		// player 4: maiden disgarded
		assertEquals(2, player4.getDisplay().size());
		assertEquals(8, player4.getTotalCardValue());
	}
	
	@Test
	public void testDisgraceShielded(){
		System.out.println("@Test: testing disgrace shielded");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.SQUIRE, 2));
		
		assertEquals(2, player1.getDisplay().size());
		assertEquals(6, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		game.playCard(player5.getCardFromHand(Config.MAIDEN, 6));
		game.processPlay("play shield");

		assertEquals(3, player5.getDisplay().size());
		assertEquals(15, player5.getTotalCardValue());
		
		game.setCurrentPlayer(player4);
		game.startTurn();	
		game.playCard(player4.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player4.getCardFromHand(Config.MAIDEN, 6));

		assertEquals(2, player4.getDisplay().size());
		assertEquals(10, player4.getTotalCardValue());
		
		game.processPlay("play disgrace");
		
		//test discard
		assertEquals(5, player4.getCards().size());
		assertEquals(3, game.getDiscardPile().size());
		
		// player 3: 1 squire discarded
		assertEquals(1, player1.getDisplay().size());
		assertEquals(4, player1.getTotalCardValue());
		
		// player 5: shielded
		assertEquals(3, player5.getDisplay().size());
		assertEquals(15, player5.getTotalCardValue());
		
		// player 4: maiden discarded
		assertEquals(1, player4.getDisplay().size());
		assertEquals(4, player4.getTotalCardValue());
	}
	
	@Test
	public void testIvanhoeDisgrace(){
		System.out.println("@Test: testing disgrace");
		player1.chooseTournamentColour(Config.YELLOW);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.YELLOW, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		player1.addCard(new ActionCard(Config.IVANHOE));
		game.playCard(player1.getCardFromHand(Config.YELLOW, 3));
		game.playCard(player1.getCardFromHand(Config.SQUIRE, 2));
		
		assertEquals(2, player1.getDisplay().size());
		assertEquals(5, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player2);
		game.startTurn();		
		game.playCard(player2.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player2.getCardFromHand(Config.YELLOW, 5));
		game.playCard(player2.getCardFromHand(Config.SQUIRE, 2));
		game.playCard(player2.getCardFromHand(Config.SQUIRE, 3));

		assertEquals(4, player2.getDisplay().size());
		assertEquals(14, player2.getTotalCardValue());
		
		game.setCurrentPlayer(player4);
		game.startTurn();	
		game.playCard(player4.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player4.getCardFromHand(Config.YELLOW, 4));
		game.playCard(player4.getCardFromHand(Config.MAIDEN, 6));

		assertEquals(3, player4.getDisplay().size());
		assertEquals(14, player4.getTotalCardValue());
		
		String prompt = game.processPlay("play disgrace");
		assertEquals("waiting plyivnhoe " + player1.getName() + " disgrace", prompt);

		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe disgrace");
		assertEquals("waiting ivanhoe " + player4.getName(), ivanhoe);
				
		//test discard
		assertEquals(4, player4.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		// player 3: no discard
		assertEquals(2, player1.getDisplay().size());
		assertEquals(5, player1.getTotalCardValue());
		
		// player 3: no discard
		assertEquals(4, player2.getDisplay().size());
		assertEquals(14, player2.getTotalCardValue());
		
		// player 4: no discard
		assertEquals(3, player4.getDisplay().size());
		assertEquals(14, player4.getTotalCardValue());
	}
	
	@Test
	public void testAdapt(){
		System.out.println("@Test: testing adapt");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());

		game.processPlay("play adapt");
		
		//test discard
		assertEquals(4, player5.getCards().size());
		assertEquals(3, game.getDiscardPile().size());
		
		// player 1: 1 card discarded
		assertEquals(1, player1.getDisplay().size());
		assertEquals(4, player1.getTotalCardValue());
		
		// player 5: 1 card discarded
		assertEquals(2, player5.getDisplay().size());
		assertEquals(9, player5.getTotalCardValue());
		
	}
	
	@Test
	public void testAdaptShielded(){
		System.out.println("@Test: testing adapt shielded");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		game.processPlay("play shield");
		game.processPlay("play adapt");
		
		//test discard
		assertEquals(3, player5.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		// player 1: 1 card discarded
		assertEquals(1, player1.getDisplay().size());
		assertEquals(4, player1.getTotalCardValue());
		
		// player 5: shielded, unaffected
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		
	}
	
	@Test
	public void testIvanhoeAdapt(){
		System.out.println("@Test: testing ivanhoe with Adapt");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();	
		player1.addCard(new ActionCard(Config.IVANHOE));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());

		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());

		// player with card is prompted to play ivanhoe
		String prompt = game.processPlay("play adapt ");
		assertEquals("waiting plyivnhoe " + player1.getName() + " adapt", prompt);
		
		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe adapt");
		assertEquals("waiting ivanhoe " + player5.getName(), ivanhoe);
		
		//test discard (both adapt and ivanhoe should be discarded)
		assertEquals(4, player5.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		
	}
	
	@Test
	public void testOutwit(){
		System.out.println("@Test: testing outwit");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		game.processPlay("play stunned " + player1.getName());
		assertTrue(player1.isStunned());
		game.processPlay("play outwit purple 4 " + player1.getName() + " stunned 0");
		
		//test discard
		assertEquals(3, player5.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		// player 1: 3 cards in display
		assertEquals(3, player1.getDisplay().size());
		assertEquals(12, player1.getTotalCardValue());
		
		// player 5: 1 card discarded, stunned
		assertEquals(2, player5.getDisplay().size());
		assertEquals(9, player5.getTotalCardValue());
		assertTrue(player5.isStunned());
		
	}
	
	@Test
	public void testOutwitShielded(){
		System.out.println("@Test: testing outwit shielded");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		
		game.processPlay("play stunned " + player1.getName());
		assertTrue(player1.isStunned());
		
		game.processPlay("play shield");
		assertTrue(player5.hasShield());
		game.processPlay("play outwit shield 0 " + player1.getName() + " stunned 0");
		
		//test discard 
		assertEquals(2, player5.getCards().size());
		assertEquals(1, game.getDiscardPile().size());
		
		
		// player 1: 3 cards in display
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());
		assertTrue(player1.hasShield());
		
		// player 5: shielded
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		assertTrue(player5.isStunned());
		
	}
	
	@Test
	public void testIvanhoeOutwit(){
		System.out.println("@Test: testing outwit");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();	
		player1.addCard(new ActionCard(Config.IVANHOE));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());
		
		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());

		//player with ivanhoe is prompted
		String prompt = game.processPlay("play outwit purple 4 " + player1.getName() + " purple 5");
		assertEquals("waiting plyivnhoe " + player1.getName() + " outwit", prompt);

		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe outwit");
		assertEquals("waiting ivanhoe " + player5.getName(), ivanhoe);
		
		//test discard
		assertEquals(4, player5.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		// player 1: 3 cards in display
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());
		
		// player 5: 1 card discarded, stunned
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		assertFalse(player5.isStunned());
		
	}
	
	@Test
	public void testStunned(){
		System.out.println("@Test: testing stunned");
		player5.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player5.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		game.processPlay("play stunned " + player1.getName());
		assertTrue(player1.isStunned());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		String stunned = game.processPlay("play purple 4");
		// after one play, stunned message is sent to client
		// client sends back end turn
		assertEquals("stn purple_4", stunned);
		
		//test discard (none discarded)
		assertEquals(4, player5.getCards().size());
		assertEquals(0, game.getDiscardPile().size());
		
		
	}
	
	@Test
	public void testStunnedShielded(){
		System.out.println("@Test: testing stunned shielded");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();	
		player1.addCard(new ActionCard(Config.SHIELD));
		game.processPlay("play shield");
		assertTrue(player1.hasShield());
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		
		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		game.processPlay("play stunned " + player1.getName());
		assertTrue(player1.isStunned());
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		String stunned = game.processPlay("play purple 4");
		// after one play, stunned message is sent to client
		// client sends back end turn
		
		//shield has no effect on stunned
		assertEquals("stn purple_4", stunned);
		
		
	}
	
	@Test
	public void testIvanhoeStunned(){
		System.out.println("@Test: testing stunned");
		player5.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player5.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		player1.addCard(new ActionCard(Config.IVANHOE));

		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		
		//prompt player with ivanhoe to use card
		String prompt = game.processPlay("play stunned " + player1.getName());
		assertFalse(player1.isStunned());
		
		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe stunned");
		assertEquals("waiting ivanhoe " + player5.getName(), ivanhoe);
		
		game.setCurrentPlayer(player1);
		game.startTurn();		
		String stunned = game.processPlay("play purple 4");
		// after one play, stunned message is sent to client
		// client sends back end turn
		assertNotEquals("stn purple_4", stunned);
		
		//test discard (none discarded)
		assertEquals(4, player5.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		
	}
	
	@Test
	public void testIvanhoeDeclined(){
		System.out.println("@Test: testing ivanhoe declined");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();	
		player1.addCard(new ActionCard(Config.IVANHOE));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());

		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());

		// player with card is prompted to play ivanhoe
		String prompt = game.processPlay("play adapt ");
		assertEquals("waiting plyivnhoe " + player1.getName() + " adapt", prompt);
		
		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play adapt " + Config.IVANHOE_DECLINED);
		
		//test discard
		assertEquals(4, player5.getCards().size());
		assertEquals(3, game.getDiscardPile().size());
		
		assertEquals(1, player1.getDisplay().size());
		assertEquals(4, player1.getTotalCardValue());
		assertEquals(2, player5.getDisplay().size());
		assertEquals(9, player5.getTotalCardValue());
	}
	
	@Test
	public void testIvanhoeShielded(){
		System.out.println("@Test: testing stunned shielded");
		player1.chooseTournamentColour(Config.PURPLE);
		game.setTournamentColour(player1.getTournamentColour());
		assertEquals(Config.PURPLE, game.getTournamentColour());
		
		game.setCurrentPlayer(player1);
		game.startTurn();	
		player1.addCard(new ActionCard(Config.IVANHOE));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player1.getCardFromHand(Config.PURPLE, 4));
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());

		game.setCurrentPlayer(player5);
		game.startTurn();		
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 4));
		game.playCard(player5.getCardFromHand(Config.PURPLE, 5));
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		
		//shielded
		game.processPlay("play shield");
		
		// player with card is prompted to play ivanhoe
		String prompt = game.processPlay("play adapt ");
		assertEquals("waiting plyivnhoe " + player1.getName() + " adapt", prompt);
		
		// player chooses to play ivanhoe
		String ivanhoe = game.processPlay("play ivanhoe adapt");
		assertEquals("waiting ivanhoe " + player5.getName(), ivanhoe);
		
		//test discard (both adapt and ivanhoe are discarded)
		assertEquals(6, player1.getCards().size());
		assertEquals(2, game.getDiscardPile().size());
		
		//ivanhoe card is unaffected by shield
		assertEquals(2, player1.getDisplay().size());
		assertEquals(8, player1.getTotalCardValue());
		assertEquals(3, player5.getDisplay().size());
		assertEquals(13, player5.getTotalCardValue());
		
	}
	
}