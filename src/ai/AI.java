package ai;

import org.apache.log4j.Logger;

import config.Config;
import game.Player;
import network.Server;

public class AI extends Player{
	private Strategy strategy;
	private int id;
	private Server server;
	
	private Logger log = Logger.getLogger("AI");
	
	public AI(Strategy s){
		this.strategy = s;
		this.id = 0;
		
		log.info("New AI has been created");
	}
	
	public void processInput(String msg){
		log.info("AI has received: " + msg);
		System.out.println("GOT MESSAGE: " + msg);
		//server.handle(id, msg);
	}
	
}
