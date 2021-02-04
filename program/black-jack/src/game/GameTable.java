package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameTable {
	public void facilitateGame() {
		List<Card> deck = prepareDeck();
		Gamer player=new Gamer();
		Gamer dealer=new Gamer();
		for(int i=0;i<2;i++) {
			player.addHands(drawCard(deck));
			dealer.addHands(drawCard(deck));
			System.out.println("player"+(i+1)+" number:"+player.getHands().get(i).getNumber()+", suit:"+player.getHands().get(i).getSuit());
			System.out.println("dealer"+(i+1)+" number:"+dealer.getHands().get(i).getNumber()+", suit:"+dealer.getHands().get(i).getSuit());
		}
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

	public Card drawCard(List<Card> deck) {
		Card card=deck.get(0);
		deck.remove(0);
		return card;
	}
}
