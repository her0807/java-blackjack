package blackjack.domain;

import static java.util.stream.Collectors.*;

import java.util.List;
import java.util.Map;

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

    public Map<String, Score> createResult(List<Player> players) {
        int dealerScore = getTotalScore();
        return players.stream()
                .collect(toMap(player -> player.getName(), player -> Score.of(player.getTotalScore(), dealerScore)));
    }

    public Map<Score, Long> createDealerResult(List<Player> players) {
        int dealerScore = getTotalScore();
        return players.stream()
                .collect(groupingBy(player -> Score.ofDealer(dealerScore, player.getTotalScore()), counting()));
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
