package blackjack.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StringTest {
    @Test
    void 스플릿_공백_에러() {
        String text = " ";
        String[] arr = text.split(",");
        assertThat(arr.length).isEqualTo(0);
    }
}
