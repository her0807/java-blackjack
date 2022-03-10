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


    @DisplayName("딜러만 버스트 일 경우 패배한다.")
    @Test
    void 딜러_승패_여부_버스트_패() {
        //30점
        List<Card> bustCards = List.of(Card.of(Denomination.KING, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND),
                Card.of(Denomination.JACK, Suit.DIAMOND));
        //18점
        List<Card> normalCards = List.of(Card.of(Denomination.EIGHT, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));

        Dealer dealer = new Dealer(bustCards);
        Player player = new Player("sudal", normalCards);

        Score score = dealer.createResult(player.getTotalScore());

        assertThat(score).isEqualTo(Score.LOSE);
    }

    @DisplayName("딜러, 플레이어 모두 버스트일 경우 승리한다.")
    @Test
    void 딜러_승패_여부_둘다_버스트_승() {
        //30점
        List<Card> bustValueByDealer = List.of(Card.of(Denomination.KING, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND),
                Card.of(Denomination.JACK, Suit.DIAMOND));
        //28점
        List<Card> bustValueByPlayer = List.of(Card.of(Denomination.EIGHT, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.HEART), Card.of(Denomination.TEN, Suit.HEART));

        Dealer dealer = new Dealer(bustValueByDealer);
        Player player = new Player("sudal", bustValueByPlayer);

        Score score = dealer.createResult(player.getTotalScore());

        assertThat(score).isEqualTo(Score.WIN);
    }

    @DisplayName("플레이어만 버스트이면 승리한다.")
    @Test
    void 딜러_승패_여부_버스트_승() {
        //12점
        List<Card> minValueCards = List.of(Card.of(Denomination.FOUR, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));
        //22점
        List<Card> maxValueCards = List.of(Card.of(Denomination.KING, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND), Card.of(Denomination.TWO, Suit.HEART));

        Dealer dealer = new Dealer(minValueCards);
        Player player = new Player("sudal", maxValueCards);

        Score score = dealer.createResult(player.getTotalScore());

        assertThat(score).isEqualTo(Score.WIN);
    }

    @DisplayName("딜러보다 플레이어의 점수가 높으면 패배한다.")
    @Test
    void 딜러_승패_여부_점수_패() {
        //12점
        List<Card> minValueCards = List.of(Card.of(Denomination.FOUR, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));
        //18점
        List<Card> maxValueCards = List.of(Card.of(Denomination.EIGHT, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));

        Dealer dealer = new Dealer(minValueCards);
        Player player = new Player("sudal", maxValueCards);

        Score score = dealer.createResult(player.getTotalScore());

        assertThat(score).isEqualTo(Score.LOSE);
    }

    @DisplayName("딜러가 플레이어보다 점수가 높으면 승리한다.")
    @Test
    void 플레이어_승패_여부_점수_승() {
        //12점
        List<Card> minValueCards = List.of(Card.of(Denomination.FOUR, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));
        //18점
        List<Card> maxValueCards = List.of(Card.of(Denomination.EIGHT, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));

        Dealer dealer = new Dealer(maxValueCards);
        Player player = new Player("sudal", minValueCards);

        Score score = dealer.createResult(player.getTotalScore());

        assertThat(score).isEqualTo(Score.WIN);
    }

    @DisplayName("딜러와 플레이어 점수가 같으면 무.")
    @Test
    void 딜러_승패_여부_점수_무() {
        //12점
        List<Card> tieValueByPlayer = List.of(Card.of(Denomination.FOUR, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));
        //12점
        List<Card> tieValueByDealer = List.of(Card.of(Denomination.FOUR, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));

        Dealer dealer = new Dealer(tieValueByDealer);
        Player player = new Player("sudal", tieValueByPlayer);

        Score score = dealer.createResult(player.getTotalScore());

        assertThat(score).isEqualTo(Score.TIE);
    }

}
