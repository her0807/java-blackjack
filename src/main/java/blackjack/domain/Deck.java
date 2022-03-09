package blackjack.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Deck {
    public static final Deck instance = new Deck();
    private final Stack<Card> values = new Stack<>();

    {
        List<Card> values = new ArrayList<>(Card.VALUES);
        Collections.shuffle(values);
        this.values.addAll(values);
    }

    private Deck(){}

    public static Deck getInstance() {
        return instance;
    }

    public Card giveCard() {
        if (values.empty()) {
            throw new IllegalArgumentException("카드가 모두 소진되었습니다.");
        }
        return values.pop();
    }

    public List<Card> getInitCard() {
        return List.of(giveCard(), giveCard());
    }
}
