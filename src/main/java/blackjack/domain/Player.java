package blackjack.domain;

import java.util.List;

public class Player {

    private static final int PLAYING_STANDARD = 21;

    private final String name;
    private final Cards cards;

    public Player(String name, List<Card> cards) {
        this.name = name;
        this.cards = new Cards(cards);
    }

    public int getTotalScore() {
        return cards.calculateTotalScore();
    }

    public boolean isPlaying() {
        return cards.calculateTotalScore() <= PLAYING_STANDARD;
    }

    public void combine(Card card) {
        cards.combine(card);
    }

    public GameResult createResult(int dealerScore) {
        if (getTotalScore() > PLAYING_STANDARD) {
            return GameResult.LOSE;
        }

        if (dealerScore > PLAYING_STANDARD) {
            return GameResult.WIN;
        }
        return GameResult.of(getTotalScore() - dealerScore);
    }

    public String getName() {
        return name;
    }

    public List<Card> getCards() {
        return cards.getValue();
    }
}
