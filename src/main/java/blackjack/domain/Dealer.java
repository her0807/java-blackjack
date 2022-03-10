package blackjack.domain;

import java.util.List;

public class Dealer {
    public static final int DRAWABLE_LIMIT_VALUE = 16;
    private final Cards cards;

    public Dealer(List<Card> cards) {
        this.cards = new Cards(cards);
    }

    public int getTotalScore() {
        return cards.calculateTotalScore();
    }

    public boolean isDrawable() {
        return cards.calculateTotalScore() <= DRAWABLE_LIMIT_VALUE;
    }

    public void combine(Card card) {
        cards.combine(card);
    }

    public Score createResult(int playScore) {
        if (playScore > 21) {
            return Score.WIN;
        }

        if (getTotalScore() > 21) {
            return Score.LOSE;
        }

        return Score.of(getTotalScore() - playScore);
    }


    public List<Card> getCards() {
        return cards.getValue();
    }

    @Override
    public String toString() {
        return "Dealer{" +
                "cards=" + cards +
                '}';
    }
}
