package blackjack.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlayerTest {

    @DisplayName("이름과 카드 리스트가 주어지면 정상적으로 생성된다.")
    @Test
    void 플레이어_생성_정상() {
        String name = "mat";
        List<Card> cards = List.of(Card.of(Denomination.ACE, Suit.CLOVER), Card.of(Denomination.KING, Suit.DIAMOND));

        assertDoesNotThrow(() -> new Player(name, cards));
    }

    @DisplayName("플레이어가 가진 카드의 총점을 구한다.")
    @Test
    void 플레이어_카드_총점_11() {
        String name = "mat";
        List<Card> cards = List.of(Card.of(Denomination.ACE, Suit.CLOVER), Card.of(Denomination.KING, Suit.DIAMOND));
        Player player = new Player(name, cards);

        assertThat(player.getTotalScore()).isEqualTo(11);
    }

    @DisplayName("플레이어가 가진 카드의 총점을 구한다.")
    @Test
    void 플레이어_카드_총점_20() {
        String name = "sudal";
        List<Card> cards = List.of(Card.of(Denomination.JACK, Suit.CLOVER), Card.of(Denomination.KING, Suit.DIAMOND));
        Player player = new Player(name, cards);

        assertThat(player.getTotalScore()).isEqualTo(20);
    }

    @DisplayName("플레이어의 총 점수가 21점 이하인 경우 hit가 가능하다.")
    @Test
    void 플레이어_게임_지속_가능() {
        String name = "mat";
        List<Card> cards = List.of(Card.of(Denomination.ACE, Suit.CLOVER), Card.of(Denomination.KING, Suit.DIAMOND));
        Player player = new Player(name, cards);

        assertThat(player.isPlaying()).isTrue();
    }

    @DisplayName("플레이어의 총 점수가 21점을 초과하는 경우 hit가 불가능하다.")
    @Test
    void 플레이어_게임_지속_불가능() {
        String name = "mat";
        List<Card> cards = List.of(Card.of(Denomination.JACK, Suit.CLOVER), Card.of(Denomination.KING, Suit.DIAMOND),
                Card.of(Denomination.KING, Suit.HEART));
        Player player = new Player(name, cards);

        assertThat(player.isPlaying()).isFalse();
    }

    @DisplayName("카드를 받아서 합칠 수 있다.")
    @Test
    void 카드_합침() {
        String name = "mat";
        List<Card> cards = List.of(Card.of(Denomination.JACK, Suit.CLOVER), Card.of(Denomination.KING, Suit.DIAMOND),
                Card.of(Denomination.KING, Suit.HEART));
        Player player = new Player(name, cards);
        Card card = Card.of(Denomination.FIVE, Suit.SPADE);

        player.combine(card);

        assertThat(player.getCards().size()).isEqualTo(4);
    }

    @DisplayName("딜러가 버스트 일 경우 플레이어가 승리한다.")
    @Test
    void 플레이어_승패_여부_버스트_승() {
        //30점
        List<Card> bustCards = List.of(Card.of(Denomination.KING, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND),
                Card.of(Denomination.JACK, Suit.DIAMOND));
        //18점
        List<Card> normalCards = List.of(Card.of(Denomination.EIGHT, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));

        Dealer dealer = new Dealer(bustCards);
        Player player = new Player("sudal", normalCards);

        Score score = player.createResult(dealer.getTotalScore());

        assertThat(score).isEqualTo(Score.WIN);
    }

    @DisplayName("플레이어와 딜러 모두 버스트일 경우 패배한다.")
    @Test
    void 플레이어_승패_여부_둘다_버스트_패() {
        //30점
        List<Card> bustValueByDealer = List.of(Card.of(Denomination.KING, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND),
                Card.of(Denomination.JACK, Suit.DIAMOND));
        //28점
        List<Card> bustValueByPlayer = List.of(Card.of(Denomination.EIGHT, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.HEART), Card.of(Denomination.TEN, Suit.HEART));

        Dealer dealer = new Dealer(bustValueByDealer);
        Player player = new Player("sudal", bustValueByPlayer);

        Score score = player.createResult(dealer.getTotalScore());

        assertThat(score).isEqualTo(Score.LOSE);
    }

    @DisplayName("플레이어만 버스트이면 패배한다.")
    @Test
    void 플레이어_승패_여부_버스트_패() {
        //12점
        List<Card> minValueCards = List.of(Card.of(Denomination.FOUR, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));
        //22점
        List<Card> maxValueCards = List.of(Card.of(Denomination.KING, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND), Card.of(Denomination.TWO, Suit.HEART));

        Dealer dealer = new Dealer(minValueCards);
        Player player = new Player("sudal", maxValueCards);

        Score score = player.createResult(dealer.getTotalScore());

        assertThat(score).isEqualTo(Score.LOSE);
    }

    @DisplayName("플레이어가 딜러보다 점수가 높으면 승리한다.")
    @Test
    void 플레이어_승패_여부_점수_승() {
        //12점
        List<Card> minValueCards = List.of(Card.of(Denomination.FOUR, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));
        //18점
        List<Card> maxValueCards = List.of(Card.of(Denomination.EIGHT, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));

        Dealer dealer = new Dealer(minValueCards);
        Player player = new Player("sudal", maxValueCards);

        Score score = player.createResult(dealer.getTotalScore());

        assertThat(score).isEqualTo(Score.WIN);
    }

    @DisplayName("플레이어가 딜러보다 점수가 낮으면 패배.")
    @Test
    void 플레이어_승패_여부_점수_패() {
        //12점
        List<Card> minValueCards = List.of(Card.of(Denomination.FOUR, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));
        //18점
        List<Card> maxValueCards = List.of(Card.of(Denomination.EIGHT, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));

        Dealer dealer = new Dealer(maxValueCards);
        Player player = new Player("sudal", minValueCards);

        Score score = player.createResult(dealer.getTotalScore());

        assertThat(score).isEqualTo(Score.LOSE);
    }

    @DisplayName("플레이어와 딜러가 점수가 같으면 무.")
    @Test
    void 플레이어_승패_여부_점수_무() {
        //12점
        List<Card> tieValueByPlayer = List.of(Card.of(Denomination.FOUR, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));
        //12점
        List<Card> tieValueByDealer = List.of(Card.of(Denomination.FOUR, Suit.CLOVER),
                Card.of(Denomination.KING, Suit.DIAMOND));

        Dealer dealer = new Dealer(tieValueByDealer);
        Player player = new Player("sudal", tieValueByPlayer);

        Score score = player.createResult(dealer.getTotalScore());

        assertThat(score).isEqualTo(Score.TIE);
    }
}
