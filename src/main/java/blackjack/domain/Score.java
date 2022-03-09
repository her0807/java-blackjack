package blackjack.domain;

import java.util.Arrays;
import java.util.function.BiPredicate;

public enum Score {

    WIN("승", (totalScore, targetScore) -> !checkBust(totalScore) && totalScore > targetScore),
    TIE("무", (totalScore, targetScore) -> totalScore == targetScore),
    LOSE("패", (totalScore, targetScore)
            -> checkBust(totalScore) || (totalScore < targetScore && !checkBust(targetScore)));

    private final String value;
    private final BiPredicate<Integer, Integer> condition;

    Score(String value, BiPredicate<Integer, Integer> condition) {
        this.value = value;
        this.condition = condition;
    }

    public static Score of(int totalScore, int targetTotalScore) {
        if (checkBust(totalScore)) {
            return Score.LOSE;
        }
        return Arrays.stream(values())
                .filter(score -> score.condition.test(totalScore, targetTotalScore))
                .findAny()
                .get();
    }

    public static Score ofDealer(int totalScore, int targetTotalScore) {
        if (checkBust(targetTotalScore)) {
            return Score.WIN;
        }

        return Arrays.stream(values())
                .filter(score -> score.condition.test(totalScore, targetTotalScore))
                .findAny()
                .get();
    }

    private static boolean checkBust(int score) {
        return score > 21;
    }

    public String getValue() {
        return value;
    }
}
