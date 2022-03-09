package blackjack.domain;

import java.util.Arrays;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Card {

    public static final Set<Card> VALUES;

    static {
        VALUES = Arrays.stream(Suit.values())
                .flatMap(suit -> Arrays.stream(Denomination.values())
                        .map(number -> new Card(number, suit)))
                .collect(Collectors.toUnmodifiableSet());
    }

    private final Denomination denomination;
    private final Suit suit;

    private Card(Denomination denomination, Suit suit) {
        this.denomination = denomination;
        this.suit = suit;
    }

    public static Card of(Denomination denomination, Suit suit) {
        Card targetCard = new Card(denomination, suit);

        return VALUES.stream()
                .filter(card -> card.equals(targetCard))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카드입니다."));
    }

    public int getScore() {
        return denomination.getScore();
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public Suit getSuit() {
        return suit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Card card = (Card) o;
        return denomination == card.denomination && suit == card.suit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(denomination, suit);
    }
}
