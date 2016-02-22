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
	static boolean c1;
	static boolean c2;
	static boolean c3;
	static boolean c4;
	static boolean c5;
	
	@BeforeClass
	public static void BeforeClass(){
		System.out.println("@BeforeClass: TestClient");
		server = new Server();
		server.runServer(Config.DEFAULT_PORT);
		client1 = new Client();
		c1 = client1.connectToServer("127.0.0.1", 3000);
		client2 = new Client();
		c2 = client2.connectToServer("127.0.0.1", 3000);
		client3 = new Client();
		c3 = client3.connectToServer("127.0.0.1", 3000);
		client4 = new Client();
		c4 = client4.connectToServer("127.0.0.1", 3000);
		client5 = new Client();
		c5 = client5.connectToServer("127.0.0.1", 3000);
	}
	
	@Before
	public void setUp(){
		System.out.println("@Before: TestClient");
	}
	
	/*@After
	public void tearDown(){
		server.shutdown();
	}*/
	
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
	public void testMessage1(){
		System.out.println("@Test: testMessage1");
		assertTrue(c1); 
		
		String message = "Test message 1";
		int id = client1.getID();
		server.handle(id, message);
		assertEquals(message, client1.testMessages());
	}
	
	@Test
	public void testMessage2(){
		System.out.println("@Test: testMessage2");
		assertTrue(c2); 
		
		String message = "Test message 2";
		int id = client2.getID();
		server.handle(id, message);
		assertEquals(message, client2.testMessages());
	}
	
	@Test
	public void testMessage3(){
		System.out.println("@Test: testMessage3");
		assertTrue(c3); 
		
		String message = "Test message 3";
		int id = client3.getID();
		server.handle(id, message);
		assertEquals(message, client3.testMessages());
	}
	
	@Test
	public void testMessage4(){
		System.out.println("@Test: testMessage4");
		assertTrue(c4); 
		
		String message = "Test message 4";
		int id = client4.getID();
		server.handle(id, message);
		assertEquals(message, client4.testMessages());
	}
	
	@Test
	public void testMessage5(){
		System.out.println("@Test: testMessage5");
		assertTrue(c5); 
		
		String message = "Test message 2";
		int id = client5.getID();
		server.handle(id, message);
		assertEquals(message, client5.testMessages());
	}
}
