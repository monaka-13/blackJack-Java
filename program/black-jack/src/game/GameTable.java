package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameTable {
	public void facilitateGame() {
		//参加者セット
		List<Gamer> players = new ArrayList<Gamer>();
		players.add(new Gamer(inputName()));
		Gamer dealer = new Gamer("dealer");

		boolean turnEnd = false;

		//山札用意
		List<Card> deck = prepareDeck();
		System.out.println("★GameStart!");
		for (int i = 0; i < players.size(); i++) {
			Gamer player = players.get(i);
			System.out.println("★---" + player.getGamerName() + " カード配布---");
			//手札配布
			for (int j = 0; j < 2; j++) {//カード枚数分ループ

				Card drawCard = getCard(player, deck);
				plusPoint(player, drawCard);

			}
			openDealAll(player);
			pointAmount(player);
		}

		//ディーラーが準備する
		System.out.println("★---" + dealer.getGamerName() + " カード配布---");
		for (int j = 0; j < 2; j++) {//カード枚数分ループ
			Card drawCard = getCard(dealer, deck);

			plusPoint(dealer, drawCard);

			openDealDealer(dealer, drawCard);
		}
		pointAmount(dealer);

		//プレイヤーカード追加
		for (int i = 0; i < players.size(); i++) {
			Gamer player = players.get(i);
			System.out.println("★---" + player.getGamerName() + " カード追加---");

			while (turnEnd == false) {//21を超える→終了
				if (player.getPoints() > 21) {
					turnEnd = true;
				} else {
					String choose = inputYN();//入力
					if (choose.equals("Y")) {
						Card drawCard = getCard(player, deck);
						plusPoint(player, drawCard);
						openDealAll(player);
						pointAmount(player);
					} else if (choose.equals("N")) {
						turnEnd = true;
					}
				}
			}
		}

		//ディーラーは17になるまでカードを引き続ける
		System.out.println("★---" + dealer.getGamerName() + " カード追加---");
		while (dealer.getPoints() < 17) {
			Card drawCard = getCard(dealer, deck);
			plusPoint(dealer, drawCard);
		}
		openDealAll(dealer);

		//プレイヤーとディーラーの値を比べて、勝敗を決定する
		System.out.println("★---ショーダウン---");
		for (int i = 0; i < players.size(); i++) {
			Gamer player = players.get(i);
			message(player);
		}
		message(dealer);

		for (int i = 0; i < players.size(); i++) {
			Gamer player = players.get(i);

			int ply0 = player.getPoints();
			int ply1 = dealer.getPoints();
			if (ply0 > 21) {
				ply0 = 0;
			}
			if (ply1 > 21) {
				ply1 = 0;
			}
			if (ply0 > ply1) {
				System.out.println(player.getGamerName() + " wins!");
			} else if (ply0 < ply1) {
				System.out.println(player.getGamerName() + " lose");
			} else {
				System.out.println("draw");
			}
		}
	}

	private void message(Gamer gamer) {
		String msg = "";
		if (gamer.getPoints() == 21) {
			msg = " BlackJack!";
		} else if (gamer.getPoints() > 21) {
			msg = " Burst";
		}
		System.out.println(gamer.getGamerName() + "の合計:" + gamer.getPoints() + msg);
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

	private void openDealAll(Gamer gamer) {
		for (int i = 0; i < gamer.getHands().size(); i++) {
			System.out.println(gamer.getGamerName() + ":" + (i + 1) + "枚目");
			System.out.println("┏━┓");
			System.out.println("┃" + gamer.getHands().get(i).getSuit() + " ┃");
			System.out.println("┃" + gamer.getHands().get(i).getNumberMark() + "┃");
			System.out.println("┗━┛");
		}
	}

	private void openDealDealer(Gamer gamer, Card drawCard) {
		//行動者の手札を表示する
		System.out.println(gamer.getGamerName() + ":" + gamer.getHands().size() + "枚目");
		System.out.println("┏━┓");
		if (gamer.getHands().size() == 2) {
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

	private static String inputYN() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String a = "";
		boolean b = false;
		while (true) {
			System.out.println("カードを引きますか？(Y/N)");

			// キーボード入力を受け付ける
			a = scanner.next();
			b = a.equals("Y") || a.equals("N");
			if (b == true) {
				break;
			}
			System.out.println("認識できませんでした。");
		}
		return a;
	}

	private String inputName() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String a = "";
		System.out.println("プレイヤー名を入力してください");

		// キーボード入力を受け付ける
		a = scanner.next();
		return a;
	}

}
