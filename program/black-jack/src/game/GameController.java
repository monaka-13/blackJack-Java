package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class GameController {

	public void loop() {//Game Loop 本体
		Game game = new Game();
		game.setGamers(init());
		List<Gamer> gamers = game.getGamers();
		Integer gameTime = inputNumber("プレイ回数を入力してください");
		for (int i = 0; i < gameTime; i++) {
			System.out.println("★Game(" + (i + 1) + "/" + gameTime + ")Start!");
			betChip(gamers);
			game.setDeck(initGame());
			turnPlayersFirst(game);
			turnDealerFirst(game);
			turnPlayers(game);
			turnDealer(game);
			showResult(game);
			resetGameParameter(game);
		}
		gameResult(game);
	}

	private List<Gamer> init() {//最初の1回のみ行う処理
		//参加者セット
		List<Gamer> gamers = new ArrayList<Gamer>();
		//参加人数を入力
		int playersAmount = inputNumber("参加人数を入力してください");
		for (int i = 0; i < playersAmount; i++) {
			System.out.println((i + 1) + "人目のプレイヤーの名前を入力してください");
			gamers.add(new Gamer(inputName()));
		}
		gamers.add(new Gamer("dealer"));
		return gamers;
	}

	private void betChip(List<Gamer> gamers) {
		for (int i = 0; i < gamers.size() - 1; i++) {
			Gamer player = gamers.get(i);
			player.setBet(inputNumber(player.getGamerName() + "、チップを賭けてください(" + player.getScore() + ")まで"));
		}
	}

	List<Card> initGame() { // ゲーム初期化時に行う処理
		//山札用意
		return prepareDeck();
	}

	void turnPlayersFirst(Game game) {// players初回処理
		List<Gamer> players = game.getGamers();
		List<Card> deck = game.getDeck();
		for (int i = 0; i < players.size() - 1; i++) {
			Gamer player = players.get(i);
			System.out.println("★---" + player.getGamerName() + " カード配布---");
			//手札配布
			for (int j = 0; j < 2; j++) {//カード枚数分ループ
				Card drawCard = getCard(player, deck);
				plusPoint(player, drawCard);
				plusAceAmount(player, drawCard);
			}
			openDealAll(player);
			pointAmount(player);
			isBlackJack(player);
		}

	}

	void turnDealerFirst(Game game) { // dealer初回処理
		Gamer dealer = game.getGamers().get(game.getGamers().size()-1);
		List<Card> deck = game.getDeck();

		//ディーラーが準備する
		System.out.println("★---" + dealer.getGamerName() + " カード配布---");
		for (int j = 0; j < 2; j++) {//カード枚数分ループ
			Card drawCard = getCard(dealer, deck);
			plusPoint(dealer, drawCard);
			plusAceAmount(dealer, drawCard);
			openDealDealer(dealer, drawCard);
		}
		pointAmount(dealer);
		isBlackJack(dealer);
	}

	void turnPlayers(Game game) { // プレイヤーのターン
		List<Gamer> players = game.getGamers();
		List<Card> deck = game.getDeck();
		//プレイヤーカード追加
		for (int i = 0; i < players.size() - 1; i++) {
			Gamer player = players.get(i);
			System.out.println("★---" + player.getGamerName() + " カード追加---");

			while (player.isTurnEnd() == false) {
				if (player.getPoints() > 21) {//21を超える→例外がない限り終了
					int aceAmount = player.getAceAmount();
					if (aceAmount > 0) {
						int minusAce = player.getPoints() - 10;
						player.setPoints(minusAce);
					} else {
						player.setTurnEnd(true);
					}
				} else {
					String choose = inputYN();//入力
					if (choose.equals("Y")) {
						Card drawCard = getCard(player, deck);
						plusPoint(player, drawCard);
						plusAceAmount(player, drawCard);

						if (player.getPoints() > 21 && player.getAceAmount() > 0) {
							int minusAce = player.getPoints() - 10;
							int aceAmount = player.getAceAmount() - 1;
							player.setPoints(minusAce);
							player.setAceAmount(aceAmount);
						}

						openDealAll(player);
						pointAmount(player);
					} else if (choose.equals("N")) {
						player.setTurnEnd(true);
					}
				}
			}
		}

	}

	void turnDealer(Game game) { // ディーラーのターン
		Gamer dealer = game.getGamers().get(game.getGamers().size()-1);
		List<Card> deck = game.getDeck();
		//ディーラーは17になるまでカードを引き続ける
		System.out.println("★---" + dealer.getGamerName() + " カード追加---");
		while (dealer.getPoints() < 17) {
			Card drawCard = getCard(dealer, deck);
			plusPoint(dealer, drawCard);
			plusAceAmount(dealer, drawCard);
		}
		openDealAll(dealer);

	}

	void showResult(Game game) { // 結果表示
		List<Gamer> gamers = game.getGamers();
		//プレイヤーとディーラーの値を比べて、勝敗を決定する
		System.out.println("★---ショーダウン---");
		Gamer player = null;
		Gamer dealer = gamers.get(gamers.size() - 1);

		for (int i = 0; i < gamers.size(); i++) {
			Gamer gamer = gamers.get(i);
			addStatusMessage(gamer);
			if (gamer.getPoints() > 21) {
				gamer.setPoints(0);
			}
		}

		for (int i = 0; i < gamers.size() - 1; i++) {
			player = gamers.get(i);

			int playerPoints = player.getPoints();
			int dealerPoints = dealer.getPoints();
			int score = player.getScore();
			int bet = player.getBet();

			if (playerPoints > dealerPoints) {
				if (player.isBlackJack()) {
					score += bet * 1.5;
				} else {
					score += player.getBet();
				}
				System.out.println(player.getGamerName() + " wins!" + " (score:" + score + ")");
			} else if (playerPoints < dealerPoints) {
				score -= player.getBet();
				System.out.println(player.getGamerName() + " lose" + " (score:" + score + ")");
			} else {
				System.out.println(player.getGamerName() + " is draw" + " (score:" + score + ")");
			}
			player.setScore(score);
		}
	}

	private void resetGameParameter(Game game) {
		List<Gamer> gamers = game.getGamers();
		for (int i = 0; i < gamers.size(); i++) {
			Gamer gamer = gamers.get(i);
			gamer.setHands(new ArrayList<Card>());
			gamer.setPoints(0);
			gamer.setAceAmount(0);
			gamer.setTurnEnd(false);
			gamer.setBlackJack(false);
			gamer.setBet(0);
		}
	}

	private void gameResult(Game game) {
		List<Gamer> gamers = game.getGamers();
		if (gamers.size() > 2) {
			int maxScore = 0;
			for (int i = 0; i < gamers.size() - 1; i++) {
				if (maxScore < gamers.get(i).getScore()) {
					maxScore = gamers.get(i).getScore();
				}
			}
			String winner = "";
			for (int i = 0; i < gamers.size() - 1; i++) {
				if (gamers.get(i).getScore() == maxScore) {
					if (winner.equals("")) {
						winner += gamers.get(i).getGamerName();
					} else {
						winner += " ・ " + gamers.get(i).getGamerName();
					}
				}
			}

			System.out.print("☆★☆The winner is ... " + winner + "(score:" + maxScore + ")☆★☆");
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

	private Card getCard(Gamer gamer, List<Card> deck) {
		//カードを引く
		Card drawCard = drawCard(deck);
		//行動者の手札に加える
		gamer.addHands(drawCard);
		return drawCard;
	}

	public Card drawCard(List<Card> deck) {//カードを引く
		Card card = deck.get(0);
		deck.remove(0);
		return card;
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

	private void plusAceAmount(Gamer gamer, Card drawCard) {
		int aceAmount = gamer.getAceAmount();
		if (drawCard.getNumber() == 11) {
			aceAmount++;
		}
		gamer.setAceAmount(aceAmount);
	}

	private void isBlackJack(Gamer player) {
		if (player.getPoints() == 21) {
			player.setBlackJack(true);
		}
	}

	private void addStatusMessage(Gamer gamer) {
		String msg = "";
		if (gamer.isBlackJack()) {
			msg = " BlackJack!";
		} else if (gamer.getPoints() > 21) {
			msg = " Burst";
		}
		System.out.println(gamer.getGamerName() + "の合計:" + gamer.getPoints() + msg);
	}

	public String inputYN() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String a = "";
		while (true) {
			System.out.println("カードを引きますか？(Y/N)");

			// キーボード入力を受け付ける
			a = scanner.next();
			if (a.equals("Y") || a.equals("N")) {
				break;
			}
			System.out.println("認識できませんでした。");
		}
		return a;
	}

	public String inputName() {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		String a = "";

		// キーボード入力を受け付ける
		a = scanner.next();
		return a;
	}

	public Integer inputNumber(String msg) {
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println(msg);
		// キーボード入力を受け付ける
		return Integer.parseInt(scanner.next());
	}

}
