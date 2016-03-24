package network;

import config.Config;
import config.Strategy;

public class AI{
	private Strategy strategy;
	private int id;
	private Server server;
	
	
	public AI(Strategy s){
		this.strategy = s;
		this.id = 0;
		System.out.println("New AI of type: " +  strategy);
	}
	
	public void processInput(String msg){
		System.out.println("GOT MESSAGE: " + msg);
		//server.handle(id, msg);
	}
	
}
