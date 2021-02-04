package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameTable {
	public void facilitateGame() {
		List<Card> deck = prepareDeck();
	}

	public List<Card> prepareDeck() {
		String[] suit = { "spade", "heart", "diamond", "club" };
		List<Card> deck = new ArrayList<Card>();
		for (int n = 1; n <= 13; n++) {
			for (int s = 0; s < 4; s++) {
				Card card = new Card(n, suit[s]);
				deck.add(card);
			}
		}
		Collections.shuffle(deck);
		return deck;
	}
}
