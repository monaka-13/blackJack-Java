package game;

import java.util.ArrayList;
import java.util.List;

public class Gamer {
	private String gamerName;
	private List<Card> hands;
	private int points = 0;
	private int score = 0;

	//コンストラクタ
	public Gamer() {
		hands = new ArrayList<Card>();
	}

	public Gamer(String _gamerName) {
		this();
		this.gamerName = _gamerName;
	}

	//getter,setter
	public String getGamerName() {
		return gamerName;
	}

	public void setGamerName(String gamerName) {
		this.gamerName = gamerName;
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

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void addHands(Card card) {
		this.hands.add(card);
	}

}