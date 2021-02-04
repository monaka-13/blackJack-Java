package game;

import java.util.ArrayList;
import java.util.List;

public class Gamer {
	private List<Card> hands;
	private int points;

	public Gamer() {
		hands=new ArrayList<Card>();
	}

	public List<Card> getHands() {
		return hands;
	}
	public void setHands(List<Card> hands) {
		this.hands = hands;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}

	public void addHands(Card card) {
		this.hands.add(card);
	}


}