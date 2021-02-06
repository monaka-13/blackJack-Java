package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameTable {
	public void facilitateGame() {
		//参加者セット
//		List<Gamer> gamers = new ArrayList<Gamer>();
//		gamers.add(new Gamer("player"));
//		gamers.add(new Gamer("dealer"));
		Gamer player=new Gamer("player");
		Gamer dealer=new Gamer("dealer");

		boolean turnEnd = false;

		//山札用意
		List<Card> deck = prepareDeck();

		//手札配布
		for (int j = 0; j < 2; j++) {//カード枚数分ループ

			Card drawCard = getCard(player, deck);
			plusPoint(player, drawCard);
			openDeal(player, drawCard);

		}
		pointAmount(player);

		//ディーラーが準備する
		for (int j = 0; j < 2; j++) {//カード枚数分ループ
			Card drawCard = getCard(dealer, deck);

			plusPoint(dealer, drawCard);

			openDeal(dealer, drawCard);
		}
		pointAmount(dealer);

		//プレイヤーカード追加

		while (turnEnd == false) {//21を超える→終了
			if (player.getPoints() > 21) {
				turnEnd = true;
			} else {
				String choose = "Y";//入力
				if (choose.equals("Y")) {
					Card drawCard = getCard(player, deck);
					plusPoint(player, drawCard);

					openDeal(player, drawCard);
					pointAmount(player);

					turnEnd = true;//一回だけ実行(削除)
				} else if (choose.equals("N")) {
					turnEnd = true;
				}
				System.out.println(player.getGamerName() + "'s turn end");
			}
		}


		//ディーラーは17になるまでカードを引き続ける
		for (int i = 0; dealer.getPoints() < 17;) {
			Card drawCard = getCard(player, deck);

			plusPoint(dealer, drawCard);
		}

		//プレイヤーとディーラーの値を比べて、勝敗を決定する
		message(player);
		message(dealer);

		int ply0 = player.getPoints();
		int ply1 = dealer.getPoints();
		if (ply0 > 21) {
			ply0 = 0;
		}
		if (ply1 > 21) {
			ply1 = 0;
		}
		if (ply0 > ply1) {
			System.out.println(player.getGamerName() + " wins");
		} else if (ply0 < ply1) {
			System.out.println(player.getGamerName() + " lose");
		} else {
			System.out.println("draw");
		}
	}

	private void message(Gamer gamer) {
		String msg="";
		if(gamer.getPoints()==21) {
			msg=" BlackJack!";
		}else if(gamer.getPoints()>21) {
			msg=" Burst";
		}
		System.out.println(gamer.getGamerName() + "の合計:" + gamer.getPoints()+msg);
	}

	private Card getCard(Gamer gamer, List<Card> deck) {
		//カードを引く
		Card drawCard = drawCard(deck);

		//行動者の手札に加える
		gamer.addHands(drawCard);

		return drawCard;
	}

	private void plusPoint(Gamer gamer, Card drawCard) {
		//手札の点数を出す
		int point = gamer.getPoints() + drawCard.getNumber();
		//行動者の点数に加算する
		gamer.setPoints(point);
	}

	private void openDeal(Gamer gamer, Card drawCard) {
		//行動者の手札を表示する
		System.out.println(gamer.getGamerName() + ":" + gamer.getHands().size() + "枚目");
		System.out.println("┏━┓");
		if (gamer.getGamerName().equals("dealer")) {
			System.out.println("┃" + "?" + " ┃");
			System.out.println("┃" + " ?" + "┃");
		} else {
			System.out.println("┃" + drawCard.getSuit() + " ┃");
			System.out.println("┃" + drawCard.getNumberMark() + "┃");
		}
		System.out.println("┗━┛");
	}

	private void pointAmount(Gamer gamer) {
		if (gamer.getGamerName().equals("dealer")) {
			System.out.println(gamer.getGamerName() + "の合計:" + "?");
		} else {
			System.out.println(gamer.getGamerName() + "の合計:" + gamer.getPoints());
		}
		System.out.println("----------");
	}

	public List<Card> prepareDeck() {//山札を用意する
		String[] suit = { "♠", "♥", "♦", "♣" };
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

	public Card drawCard(List<Card> deck) {//カードを引く
		Card card = deck.get(0);
		deck.remove(0);
		return card;
	}

	public String inputCommand(String msg) {
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(isr);
		System.out.println(msg);

		String str = null;
		try {
			str = br.readLine();
			br.close();
		} catch (IOException e) {
			return "";
		}

		return str;
	}
}
