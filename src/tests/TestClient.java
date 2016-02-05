package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import org.junit.BeforeClass;
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
	
	@After
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
}
