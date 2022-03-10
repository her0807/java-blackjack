package blackjack.domain;

import java.util.Arrays;
import java.util.function.Predicate;

public enum Score {

    WIN("승", (score) -> score > 0),
    TIE("무", (score) -> score == 0),
    LOSE("패", (score) -> score < 0);

    private final String value;
    private final Predicate<Integer> condition;

    Score(String value, Predicate<Integer> condition) {
        this.value = value;
        this.condition = condition;
    }

    public static Score of(int targetScore) {
        return Arrays.stream(values())
                .filter(score -> score.condition.test(targetScore))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("결과가 정상적으로 나오지 않았습니다."));
    }

    private static boolean checkBust(int score) {
        return score > 21;
    }

    public String getValue() {
        return value;
    }
}
