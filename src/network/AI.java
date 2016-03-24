package network;

import config.Config;
import config.Strategy;
import game.Player;

public class AI extends Player{
	private Strategy strategy;
	private int id;
	private Server server;
	
	public AI(Strategy s){
		this.strategy = s;
		this.id = 0;
	}
	
	public void processInput(String msg){
		System.out.println("GOT MESSAGE: " + msg);
		//server.handle(id, msg);
	}
	
}
