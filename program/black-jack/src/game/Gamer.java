package game;

import java.util.ArrayList;
import java.util.List;

public class Gamer {
	private String gamerName = "";
	private int score = 0;
	private int bet = 0;

	private List<Card> hands = new ArrayList<Card>();
	private int points = 0;
	private int aceAmount = 0;
	private boolean turnEnd = false;
	private boolean blackJack = false;

	//コンストラクタ
	public Gamer() {
		hands = new ArrayList<Card>();
	}

	public Gamer(String _gamerName) {
		this();
		this.gamerName = _gamerName;
		this.score=500;
	}

	//getter,setter
	public String getGamerName() {
		return gamerName;
	}

	public void setGamerName(String gamerName) {
		this.gamerName = gamerName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getBet() {
		return bet;
	}

	public void setBet(int bet) {
		this.bet = bet;
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

	public int getAceAmount() {
		return aceAmount;
	}

	public void setAceAmount(int aceAmount) {
		this.aceAmount = aceAmount;
	}

	public boolean isTurnEnd() {
		return turnEnd;
	}

	public void setTurnEnd(boolean turnEnd) {
		this.turnEnd = turnEnd;
	}

	public boolean isBlackJack() {
		return blackJack;
	}

	public void setBlackJack(boolean blackJack) {
		this.blackJack = blackJack;
	}

	//メソッド
	public void addHands(Card card) {
		this.hands.add(card);
	}

}