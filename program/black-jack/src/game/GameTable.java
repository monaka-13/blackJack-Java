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
		List<Gamer> gamers = new ArrayList<Gamer>();
		gamers.add(new Gamer("player"));
		gamers.add(new Gamer("dealer"));

		//山札用意
		List<Card> deck = prepareDeck();

		//手札配布
		for (int j = 0; j < 2; j++) {//カード枚数分ループ
			//カードを引く
			Card drawCard = drawCard(deck);

			//行動者の手札に加える
			gamers.get(0).addHands(drawCard);

			//手札の点数を出す
			int point = gamers.get(0).getPoints() + drawCard.getNumber();
			//行動者の点数に加算する
			gamers.get(0).setPoints(point);

			//行動者の手札を表示する
			System.out.println(gamers.get(0).getGamerName() + ":" + gamers.get(0).getHands().size() + "枚目");
			System.out.println("┏━┓");
			//			if (i == 1 && j == 1) {
			//				System.out.println("┃" + "?" + " ┃");
			//				System.out.println("┃" + " ?" + "┃");
			//			} else {
			System.out.println("┃" + drawCard.getSuit() + " ┃");
			System.out.println("┃" + drawCard.getNumberMark() + "┃");
			//			}
			System.out.println("┗━┛");
		}
		//		if (i == 1) {
		//			System.out.println(gamers.get(i).getGamerName() + "の合計:" + "?");
		//		} else {
		System.out.println(gamers.get(0).getGamerName() + "の合計:" + gamers.get(0).getPoints());
		//		}
		System.out.println("----------");

		boolean turnEnd = false;
		//21を超える→終了
		while (turnEnd == false) {
			if (gamers.get(0).getPoints() > 21) {
				turnEnd = true;
			} else {
				//TODO プレイヤーはカードを追加できる
				String choose = "Y";//入力
				if (choose.equals("Y")) {
					//カードを引く
					Card drawCard = drawCard(deck);

					//行動者の手札に加える
					gamers.get(0).addHands(drawCard);

					//手札の点数を出す
					int point = gamers.get(0).getPoints() + drawCard.getNumber();
					//行動者の点数に加算する
					gamers.get(0).setPoints(point);

					//行動者の手札を表示する
					System.out
							.println(gamers.get(0).getGamerName() + ":" + gamers.get(0).getHands().size() + "枚目");
					System.out.println("┏━┓");
					//			if (i == 1 && j == 1) {
					//				System.out.println("┃" + "?" + " ┃");
					//				System.out.println("┃" + " ?" + "┃");
					//			} else {
					System.out.println("┃" + drawCard.getSuit() + " ┃");
					System.out.println("┃" + drawCard.getNumberMark() + "┃");
					//			}
					System.out.println("┗━┛");

					//		if (i == 1) {
					//			System.out.println(gamers.get(i).getGamerName() + "の合計:" + "?");
					//		} else {
					System.out.println(gamers.get(0).getGamerName() + "の合計:" + gamers.get(0).getPoints());
					//		}
					System.out.println("----------");
					turnEnd = true;//一回だけ実行(削除)
				} else if (choose.equals("N")) {
					turnEnd = true;
				}
				System.out.println(gamers.get(0).getGamerName() + "'s turn end");
			}
		}

		//TODO ディーラーが準備する
		for (int j = 0; j < 2; j++) {//カード枚数分ループ
			//カードを引く
			Card drawCard = drawCard(deck);

			//行動者の手札に加える
			gamers.get(1).addHands(drawCard);

			//手札の点数を出す
			int point = gamers.get(1).getPoints() + drawCard.getNumber();
			//行動者の点数に加算する
			gamers.get(1).setPoints(point);

			//行動者の手札を表示する
			System.out.println(gamers.get(1).getGamerName() + ":" + gamers.get(1).getHands().size() + "枚目");
			System.out.println("┏━┓");
			if (gamers.get(1).getGamerName().equals("dealer") && j == 1) {
				System.out.println("┃" + "?" + " ┃");
				System.out.println("┃" + " ?" + "┃");
			} else {
				System.out.println("┃" + drawCard.getSuit() + " ┃");
				System.out.println("┃" + drawCard.getNumberMark() + "┃");
			}
			System.out.println("┗━┛");
		}
		if (gamers.get(1).getGamerName().equals("dealer")) {
			System.out.println(gamers.get(1).getGamerName() + "の合計:" + "?");
		} else {
			System.out.println(gamers.get(0).getGamerName() + "の合計:" + gamers.get(0).getPoints());
		}
		System.out.println("----------");

		//TODO ディーラーは17になるまでカードを引き続ける
		for (int i = 0; gamers.get(1).getPoints() < 17;) {
			//カードを引く
			Card drawCard = drawCard(deck);

			//行動者の手札に加える
			gamers.get(1).addHands(drawCard);

			//手札の点数を出す
			int point = gamers.get(1).getPoints() + drawCard.getNumber();
			//行動者の点数に加算する
			gamers.get(1).setPoints(point);
		}
		System.out.println(gamers.get(1).getGamerName() + "の合計:" + gamers.get(1).getPoints());

		//TODO プレイヤーとディーラーの値を比べて、勝敗を決定する
		int ply0 = gamers.get(0).getPoints();
		int ply1 = gamers.get(1).getPoints();
		if (ply0 > 21) {
			ply0 = 0;
		}
		if (ply1 > 21) {
			ply1 = 0;
		}
		if (ply0 > ply1) {
			System.out.println(gamers.get(0).getGamerName() + " wins");
		} else if (ply0 < ply1) {
			System.out.println(gamers.get(0).getGamerName() + " lose");
		} else {
			System.out.println("draw");
		}
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
