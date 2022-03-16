package blackjack.view;

import static java.util.stream.Collectors.toList;

import blackjack.domain.Player;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final static Scanner SCANNER = new Scanner(System.in);

    private InputView() {
    }

    public static List<String> getNames() {
        OutputView.printGuideMessage("게임에 참여할 사람의 이름을 입력하세요.(쉼표 기준으로 분리)");
        String input = SCANNER.nextLine();

        String[] splitNames = input.split(",", -1);

        return Arrays.stream(splitNames)
                .collect(toList());
    }

    public static int insertBattingMoney(String playerName) {
        OutputView.printGuideMessage(playerName + "의 배팅 금액은?");
        return Integer.parseInt(SCANNER.nextLine());
    }

    public static PlayCommand getPlayCommand(Player player) {
        OutputView.printGuideMessage(player.getName() + "는 한장의 카드를 더 받겠습니까?(예는 y, 아니오는 n)");
        return PlayCommand.of(SCANNER.nextLine());
    }
}
