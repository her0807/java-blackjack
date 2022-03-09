package blackjack.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DealerTest {

    @DisplayName("카드 리스트가 주어지면 정상적으로 생성된다.")
    @Test
    void 딜러_생성_정상() {
        List<Card> cards = List.of(Card.of(Denomination.ACE, Suit.CLOVER), Card.of(Denomination.KING, Suit.DIAMOND));

        assertDoesNotThrow(() -> new Dealer(cards));
    }

    @DisplayName("딜러 가진 카드의 총점을 구한다.")
    @Test
    void 딜러_카드_총점_11() {
        List<Card> cards = List.of(Card.of(Denomination.ACE, Suit.CLOVER), Card.of(Denomination.KING, Suit.DIAMOND));
        Dealer dealer = new Dealer(cards);

        assertThat(dealer.getTotalScore()).isEqualTo(11);
    }

    @DisplayName("딜러의 점수가 16점 이하인 경우 카드를 받을 수 있다.")
    @Test
    void 카드_발급_유무_확인() {
        List<Card> cards = List.of(Card.of(Denomination.ACE, Suit.CLOVER), Card.of(Denomination.KING, Suit.DIAMOND));
        Dealer dealer = new Dealer(cards);

        assertThat(dealer.isDrawable()).isTrue();
    }

    @DisplayName("카드를 받아서 합칠 수 있다.")
    @Test
    void 카드_합침() {
        List<Card> cards = List.of(Card.of(Denomination.ACE, Suit.CLOVER), Card.of(Denomination.KING, Suit.DIAMOND));
        Dealer dealer = new Dealer(cards);
        Card card = Card.of(Denomination.FIVE, Suit.SPADE);

        dealer.combine(card);

        assertThat(dealer.getCards().size()).isEqualTo(3);
    }

    @DisplayName("player 최종 승패 여부를 계산한다.")
    @Test
    void 플레이어_승패_여부() {
        // given
        List<Card> cards = List.of(Card.of(Denomination.ACE, Suit.CLOVER), Card.of(Denomination.KING, Suit.DIAMOND));
        List<Card> playerCards = List.of(Card.of(Denomination.EIGHT, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));
        Dealer dealer = new Dealer(cards);
        List<Player> players = List.of(new Player("sudal", cards), new Player("mat", playerCards));
        // when

        Map<String, Score> result = dealer.createResult(players);
        Map<Score, Long> dealerResult = dealer.createDealerResult(players);

        System.out.println(result);
        System.out.println(dealerResult);

    }


}
