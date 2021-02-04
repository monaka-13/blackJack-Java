package game;

public class Card {
	private Integer number;
	private String suit;
	private String numberMark;

	public Card() {
	}

	public Card(int _number, String _suit) {
		this.number = _number;
		this.suit = _suit;
		switch (_number) {
		case 1:
			numberMark = "A";
			break;
		case 11:
			numberMark = "J";
			break;
		case 12:
			numberMark = "Q";
			break;
		case 13:
			numberMark = "K";
			break;
		default:
			numberMark = number.toString();
		}
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public String getNumberMark() {
		return numberMark;
	}

	public void setNumberMark(String numberMark) {
		this.numberMark = numberMark;
	}
}
