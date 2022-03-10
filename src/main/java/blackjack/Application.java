package blackjack;

import static java.util.stream.Collectors.*;

import blackjack.domain.Card;
import blackjack.domain.Dealer;
import blackjack.domain.Deck;
import blackjack.domain.GameResult;
import blackjack.domain.Player;
import blackjack.view.InputView;
import blackjack.view.OutputView;
import blackjack.view.PlayCommand;
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

        players.forEach(player -> playing(deck, player));
        drawDealer(deck, dealer);

        OutputView.printResultInfo(dealer, players);

        OutputView.printDealerGameResult(createDealerResult(players, dealer));
        OutputView.printPlayerGameResult(createPlayerResult(players, dealer));
    }

    public static void playing(Deck deck, Player player) {
        PlayCommand playCommand = PlayCommand.YES;
        while (player.isPlaying() && playCommand == PlayCommand.YES) {
            playCommand = InputView.getPlayCommand(player);
            drawCard(deck, player, playCommand);
        }
    }

    private static void drawCard(Deck deck, Player player, PlayCommand playCommand) {
        if (playCommand == PlayCommand.YES) {
            player.combine(deck.draw());
            OutputView.printPlayerCardInfo(player);
        }
    }

    public static void drawDealer(Deck deck, Dealer dealer) {
        while (dealer.isDrawable()) {
            OutputView.printDealerDrawableInfo();
            dealer.combine(deck.draw());
        }
    }

    public static Map<String, GameResult> createPlayerResult(List<Player> players, Dealer dealer) {
        return players.stream()
                .collect(toMap(player -> player.getName(),
                        player -> player.createResult(dealer.getTotalScore())));
    }


    public static Map<GameResult, Long> createDealerResult(List<Player> players, Dealer dealer) {
        return players.stream()
                .collect(groupingBy(player -> dealer.createResult(player.getTotalScore()), counting()));
    }
}
