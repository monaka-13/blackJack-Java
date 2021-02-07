package main;

import game.GameController;

public class Main {
	public static void main(String[] args) {
		System.out.println("blackJack");
		GameController game=new GameController();
		game.loop();
	}
}
