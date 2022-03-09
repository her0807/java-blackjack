package blackjack.domain;

import blackjack.view.InputView;

public class GameMachine {
    private static final Deck DECK = Deck.getInstance();
    private final Players players;
    private final Dealer dealer;

    public GameMachine(Players players, Dealer dealer) {
        this.players = players;
        this.dealer = dealer;
    }

    public void play(InputView inputView) {
        while (players.canPlay() && !dealer.isBlackJack()) {
            players.startRound(inputView);
        }

