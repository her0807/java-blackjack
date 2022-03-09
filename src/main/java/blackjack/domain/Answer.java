package blackjack.domain;

import java.util.Arrays;

public enum Answer {
    y("y", true),
    n("n", false),
    ;

    String name;
    boolean value;

    Answer(String answer, boolean value) {
        this.name = answer;
        this.value = value;
    }

    public static Answer of(String value) {
        return Arrays.stream(values()).filter(answer -> answer.getName() == value)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("y, n 으로만 답변이 가능합니다."));
    }

    public String getName() {
        return name;
    }

    public boolean getValue() {
        return value;
    }
}
