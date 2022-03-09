package blackjack;

import blackjack.domain.Dealer;
import blackjack.domain.Deck;
import blackjack.domain.GameMachine;
import blackjack.domain.Player;
import blackjack.domain.Players;
import blackjack.view.InputView;
import blackjack.view.OutputView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Application {

    public static void main(String[] args) {
        InputView inputView = new InputView();
        Deck deck = Deck.getInstance();

        Players players = createPlayers(inputView.insertPlayers(), deck);
        Dealer dealer = createDealer(deck);

        OutputView.printInitCard(players, dealer);

        GameMachine gameMachine = new GameMachine(players, dealer);

    }

    private static Players createPlayers(String[] names, Deck deck) {
        List<Player> players = Arrays.stream(names)
                .map((name) -> new Player(name.trim(), deck.getInitCard()))
                .collect(Collectors.toList());
        System.out.println(players.size());
        return new Players(players);
    }

    private static Dealer createDealer(Deck deck) {
        return new Dealer("딜러", deck.getInitCard());
    }
}
