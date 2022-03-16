package blackjack.domain;


import java.util.List;

public class Dealer extends Gamer {

    private static final int DRAWABLE_LIMIT_VALUE = 16;
    public static final String DEALER_NAME = "딜러";

    public Dealer(List<Card> cards,  int battingMoney ) {
        super(DEALER_NAME, battingMoney, cards);
    }

    @Override
    public boolean canHit() {
        return getTotalScore() <= DRAWABLE_LIMIT_VALUE;
    }

    @Override
    public GameResult createResult(Gamer player) {
        if (player.isBust()) {
            return GameResult.WIN;
        }

        if (isBust()) {
            return GameResult.LOSE;
        }

        return GameResult.of(getTotalScore(), player.getTotalScore());
    }
}

