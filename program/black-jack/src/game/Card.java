package game;

public class Card {
	private Integer number;
	private String suit;
	private String numberMark;
	private String test;
	private String test2;

	public Card() {
		this.number=0;
		this.suit="";
		this.numberMark="";
	}

	public Card(int _number, String _suit) {
		//スートの設定（設定なし）
		this.suit = _suit;

		//カード点数の設定（J,Q,Kは10点）
		switch (_number) {
		case 1:
			this.number=11;
			break;
		case 11:
		case 12:
		case 13:
			this.number=10;
			break;
		default:
			this.number = _number;
		}

		//表示の設定(J,Q,K,A)
		switch (_number) {
		case 1:
			this.numberMark = " A";
			break;
		case 11:
			this.numberMark = " J";
			break;
		case 12:
			this.numberMark = " Q";
			break;
		case 13:
			this.numberMark = " K";
			break;
		case 10:
			this.numberMark="10";
			break;
		default:
			this.numberMark = " "+number.toString();
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
