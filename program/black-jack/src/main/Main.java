package main;

import game.GameTable;

public class Main {
	public static void main(String[] args) {
		System.out.println("blackJack");
		GameTable game=new GameTable();
		game.facilitateGame();
	}
}
