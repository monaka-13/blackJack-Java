package game;

import java.util.ArrayList;
import java.util.List;

public class Game {
	private List<Gamer> gamers = new ArrayList<Gamer>();
	private List<Card> deck = new ArrayList<Card>();

	public List<Gamer> getGamers() {
		return this.gamers;
	}
	public void setGamers(List<Gamer> gamers) {
		this.gamers = gamers;
	}
	public List<Card> getDeck() {
		return deck;
	}
	public void setDeck(List<Card> deck) {
		this.deck = deck;
	}


}
