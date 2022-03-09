package blackjack.view;

import blackjack.domain.Cards;
import blackjack.domain.Dealer;
import blackjack.domain.Player;
import blackjack.domain.Players;
import java.util.stream.Collectors;

public class OutputView {

    private OutputView() {
    }

    public static void printPlayerGuideMessage() {
        System.out.println("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)");
    }

    public static void printAnswerGuideMessage(String name) {
        System.out.println(String.format("%s는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)", name));
    }


    public static void printInitCard(Players players, Dealer dealer) {
        String names = extractNames(players);
        System.out.println(String.format("\n딜러와 %s에게 각각 2장의 카드를 나누었습니다.", names));
        printDealerCard(dealer);
        printPlayersCard(players);
    }

    private static String extractNames(Players players) {
        return players.getValues().stream().map(Player::getName).collect(Collectors.joining(", "));
    }

    private static void printDealerCard(Dealer dealer) {
        String text = String.format("%s: %s", dealer.getName(), extractDealerCards(dealer.getCards()));
        System.out.println(text);
    }

    private static void printPlayersCard(Players players) {
        players.getValues().stream().forEach(OutputView::printPlayerCard);
    }

    private static void printPlayerCard(Player player) {
        String text = String.format("%s카드: %s", player.getName(), extractCards(player.getCards()));
        System.out.println(text);
    }

    private static String extractCards(Cards cards) {
        return cards.getValue().stream()
                .map(card -> String.format("%s%s", card.getDenomination().getName(), card.getSuit().getName()))
                .collect(Collectors.joining(", "));
    }

    private static String extractDealerCards(Cards cards) {
        return cards.getValue().stream()
                .map(card -> String.format("%s%s", card.getDenomination().getName(), card.getSuit().getName()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("딜러의 카드를 찾을 수 없습니다."));
    }
}
