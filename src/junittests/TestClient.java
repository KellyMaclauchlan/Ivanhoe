package junittests;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
import org.apache.log4j.Logger;
import org.junit.After;

import network.Server;
import network.Client;
import config.Config;

public class TestClient {

	static Server server;
	static Client client1; 
	static Client client2;
	static Client client3;
	static Client client4;
	static Client client5;
	static Client client6;
	static boolean c1;
	static boolean c2;
	static boolean c3;
	static boolean c4;
	static boolean c5;
	static boolean c6; 
	
	@BeforeClass
	public static void BeforeClass(){
		System.out.println("@BeforeClass: TestClient");
		server = new Server();
		server.runServer(Config.DEFAULT_PORT);
		
		client1 = new Client("127.0.0.1", 3000);
		c1 = client1.getSuccessConnect();
		
		client2 = new Client("127.0.0.1", 3000);
		c2 = client2.getSuccessConnect();
		
		client3 = new Client("127.0.0.1", 3000);
		c3 = client3.getSuccessConnect();
		
		client4 = new Client("127.0.0.1", 3000);
		c4 = client4.getSuccessConnect();
		
		client5 = new Client("127.0.0.1", 3000);
		c5 = client5.getSuccessConnect();		
	}
	
	@Before
	public void setUp(){
		System.out.println("@Before: TestClient");
	}
	
	/*@After*/
	public void tearDown(){
		server.shutdown();
	}
	
	@Test
	public void test1Client(){
		System.out.println("@Test: Test1Client");
		assertTrue(c1);
	}
	
	@Test
	public void testManyClients(){
		System.out.println("@Test: TestManyClients");
		assertTrue(c2);
		assertTrue(c3);
		assertTrue(c4);
		assertTrue(c5);
	}
	
	@Test
	public void testMinClients(){
		System.out.println("@Test: TestMinClients");
		assertTrue(c1);
		assertTrue(c2);
		
		assertTrue(server.testMinPlayers());
	}
	
	@Test
	public void testMaxClients(){
		System.out.println("@Test: TestMaxClients");
		assertTrue(c1);
		assertTrue(c2);
		assertTrue(c3);
		assertTrue(c4);
		assertTrue(c5);
		
		client6 = new Client("127.0.0.1", 3000);
		c6 = client6.getSuccessConnect();
		assertFalse(c6);
		
		assertFalse(server.testMaxPlayers());
	}
	
	@Test
	public void testMessage1(){
		System.out.println("@Test: testMessage1");
		assertTrue(c1); 
		
		String message = Config.FIRSTPLAYER;
		int id = client1.getID();
		server.handle(id, message);
		assertEquals(message, client1.testMessages());
	}
	
	@Test
	public void testMessage2(){
		System.out.println("@Test: testMessage2");
		assertTrue(c2); 
		
		String message = Config.PROMPT_JOIN;
		int id = client2.getID();
		server.handle(id, message);
		assertEquals(message, client2.testMessages());
	}
	
	@Test
	public void testMessage3(){
		System.out.println("@Test: testMessage3");
		assertTrue(c3); 
		
		String message = Config.PROMPT_JOIN;
		int id = client3.getID();
		server.handle(id, message);
		assertEquals(message, client3.testMessages());
	}
	
	@Test
	public void testMessage4(){
		System.out.println("@Test: testMessage4");
		assertTrue(c4); 
		
		String message = Config.PROMPT_JOIN;
		int id = client4.getID();
		server.handle(id, message);
		assertEquals(message, client4.testMessages());
	}
	
	@Test
	public void testMessage5(){
		System.out.println("@Test: testMessage5");
		assertTrue(c5); 
		
		String message = Config.PROMPT_JOIN;
		int id = client5.getID();
		server.handle(id, message);
		assertEquals(message, client5.testMessages());
	}
}
