package blackjack.view;

import blackjack.domain.Answer;
import java.util.Scanner;

public class InputView {
    private Scanner scanner = new Scanner(System.in);

    public String[] insertPlayers() {
        OutputView.printPlayerGuideMessage();
        String players = scanner.nextLine();
        return players.split(",");
    }

    public Answer insertAnswer(String name) {
        OutputView.printAnswerGuideMessage(name);
        try {
            return Answer.of(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return insertAnswer(name);
        }
    }
}
