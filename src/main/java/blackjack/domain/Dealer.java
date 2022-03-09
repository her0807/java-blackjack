package blackjack.domain;

import java.util.List;

public class Dealer {

    public static final int BUST_VALUE = 21;
    public static final int DEALER_GET_LIMIT_VALUE = 17;

    private final String name;
    private final Cards cards;

    public Dealer(String name, List<Card> cards) {
        this.name = name;
        this.cards = new Cards(cards);
    }

    public int getTotalScore() {
        return cards.calculateTotalScore();
    }

    public boolean isBust() {
        return cards.calculateTotalScore() > BUST_VALUE;
    }

    public boolean isBlackJack() {
        return cards.calculateTotalScore() == BUST_VALUE;
    }

    public boolean canHit() {
        return cards.calculateTotalScore() < DEALER_GET_LIMIT_VALUE;
    }

    public String getName() {
        return name;
    }

    public Cards getCards() {
        return cards;
    }
}
