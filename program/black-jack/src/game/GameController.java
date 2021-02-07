package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameController {

	public void loop() {//Game Loop 本体
		List<Gamer> gamers = init();
		Gamer dealer = gamers.get(gamers.size() - 1);
		List<Card> deck = initGame();
		turnPlayersFirst(gamers, deck);
		turnDealerFirst(dealer, deck);
		turnPlayers(gamers, deck);
		turnDealer(dealer, deck);
		showResult(gamers);
	}

	private List<Gamer> init() {//最初の1回のみ行う処理
		//参加者セット
		List<Gamer> gamers = new ArrayList<Gamer>();
		gamers.add(new Gamer("player"));//FIXME 戻す　gamers.add(new Gamer(inputName()));
		gamers.add(new Gamer("dealer"));
		return gamers;
	}

	List<Card> initGame() { // ゲーム初期化時に行う処理
		//山札用意
		List<Card> deck = prepareDeck();
		System.out.println("★GameStart!");
		return deck;
	}

	void turnPlayersFirst(List<Gamer> players, List<Card> deck) {// players初回処理
		for (int i = 0; i < players.size() - 1; i++) {
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

	}

	void turnDealerFirst(Gamer dealer, List<Card> deck) { // dealer初回処理
		//ディーラーが準備する
		System.out.println("★---" + dealer.getGamerName() + " カード配布---");
		for (int j = 0; j < 2; j++) {//カード枚数分ループ
			Card drawCard = getCard(dealer, deck);
			plusPoint(dealer, drawCard);
			openDealDealer(dealer, drawCard);
		}
		pointAmount(dealer);
	}

	void turnPlayers(List<Gamer> players, List<Card> deck) { // プレイヤーのターン
		//プレイヤーカード追加
		for (int i = 0; i < players.size() - 1; i++) {
			Gamer player = players.get(i);
			System.out.println("★---" + player.getGamerName() + " カード追加---");

			while (player.isTurnEnd() == false) {
				if (player.getPoints() > 21) {//21を超える→例外がない限り終了
					player.setTurnEnd(true);
				} else {
					String choose = inputYN();//入力
					if (choose.equals("Y")) {
						Card drawCard = getCard(player, deck);
						plusPoint(player, drawCard);
						openDealAll(player);
						pointAmount(player);
					} else if (choose.equals("N")) {
						player.setTurnEnd(true);
					}
				}
			}
		}

	}

	void turnDealer(Gamer dealer, List<Card> deck) { // ディーラーのターン
		//ディーラーは17になるまでカードを引き続ける
		System.out.println("★---" + dealer.getGamerName() + " カード追加---");
		while (dealer.getPoints() < 17) {
			Card drawCard = getCard(dealer, deck);
			plusPoint(dealer, drawCard);
		}
		openDealAll(dealer);

	}

	void showResult(List<Gamer> gamers) { // 結果表示
		//プレイヤーとディーラーの値を比べて、勝敗を決定する
		System.out.println("★---ショーダウン---");
		Gamer player = new Gamer();
		Gamer dealer = gamers.get(gamers.size() - 1);

		for (int i = 0; i < gamers.size(); i++) {
			message(gamers.get(i));
		}

		for (int i = 0; i < gamers.size() - 1; i++) {
			player = gamers.get(i);

			int playerPoints = player.getPoints();
			int dealerPoints = dealer.getPoints();
			if (playerPoints > 21) {
				playerPoints = 0;
			}
			if (dealerPoints > 21) {
				dealerPoints = 0;
			}
			if (playerPoints > dealerPoints) {
				System.out.println(player.getGamerName() + " wins!");
			} else if (playerPoints < dealerPoints) {
				System.out.println(player.getGamerName() + " lose");
			} else {
				System.out.println(player.getGamerName() + " is draw");
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