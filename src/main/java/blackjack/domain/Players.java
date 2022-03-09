package blackjack.domain;

import blackjack.view.InputView;
import java.util.Collections;
import java.util.List;

public class Players {
    private final List<Player> values;

    public Players(List<Player> values) {
        this.values = values;
    }

    public List<Player> getValues() {
        return Collections.unmodifiableList(values);
    }

    public void startRound(InputView inputView) {
//        for (Player player : values) {
//            player.isBust()
//        }
    }

    private void playSinglePlayer(Player player, InputView inputView) {
        Answer answer = Answer.y;
        while (!answer.getValue() && !player.isBust()) {
            answer = inputView.insertAnswer(player.getName());
        }


    }

    public boolean canPlay() {
        return values.stream()
                .anyMatch(Player::isBlackJack);
    }
}
