package blackjack;

import static java.util.stream.Collectors.*;

import blackjack.domain.Card;
import blackjack.domain.Dealer;
import blackjack.domain.Deck;
import blackjack.domain.Player;
import blackjack.domain.Score;
import blackjack.view.InputView;
import blackjack.view.OutputView;
import blackjack.view.PlayCommand;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {

    public static void main(String[] args) {
        Deck deck = new Deck(Card.VALUES);
        Dealer dealer = new Dealer(deck.getInitCards());

        List<String> names = InputView.getNames();
        List<Player> players = names.stream()
                .map(name -> new Player(name, deck.getInitCards()))
                .collect(toList());

        OutputView.printStartInfo(dealer, players);
        System.out.println();

        for (Player player : players) {
            playing(deck, player);
        }

        playing(deck, dealer);

        OutputView.printResultInfo(dealer, players);

        OutputView.printDealerScore(createDealerResult(players, dealer));
        OutputView.printPlayerScore(createPlayerResult(players, dealer));
    }

    public static void playing(Deck deck, Player player) {
        while (player.isPlaying()) {
            PlayCommand playCommand = InputView.getPlayCommand(player);

            if (playCommand == PlayCommand.YES) {
                player.combine(deck.draw());
                OutputView.printPlayerCardInfo(player);
            }

            if (playCommand == PlayCommand.NO) {
                OutputView.printPlayerCardInfo(player);
                break;
            }
        }
    }

    public static void playing(Deck deck, Dealer dealer) {
        while (dealer.isDrawable()) {
            OutputView.printDealerDrawableInfo();
            dealer.combine(deck.draw());
        }

        System.out.println("\n딜러는 17이상이라 카드를 더 이상 받지 않습니다.");
    }


    public static Map<String, Score> createPlayerResult(List<Player> players, Dealer dealer) {
        return players.stream()
                .collect(toMap(player -> player.getName(),
                        player -> player.createResult(dealer.getTotalScore())));
    }


    public static Map<Score, Long> createDealerResult(List<Player> players, Dealer dealer) {
        return players.stream()
                .collect(groupingBy(player -> dealer.createResult(player.getTotalScore()), counting()));
    }
}
