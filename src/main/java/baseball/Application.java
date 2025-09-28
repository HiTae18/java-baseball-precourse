package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Application {

    private static final int NUMBER_LENGTH = 3;
    private static final int START_RANGE = 1;
    private static final int END_RANGE = 9;

    public static void main(String[] args) {
        do {
            playBaseBall();
        } while (isRestart());
    }

    public static void playBaseBall() {
        List<Integer> answerNumbers = generateNumbers();

        while (true) {
            System.out.print("숫자를 입력해주세요 : ");
            String userInput = getUserNumber();
            List<Integer> userNumbers = stringToList(userInput);

            int strikes = 0;
            int balls = 0;

            for (int i = 0; i < NUMBER_LENGTH; i++) {
                if (answerNumbers.get(i).equals(userNumbers.get(i)))
                    strikes++;
                else if (answerNumbers.contains(userNumbers.get(i)))
                    balls++;
            }

            printResult(strikes, balls);

            if (strikes == NUMBER_LENGTH) {
                System.out.println(NUMBER_LENGTH + "개의 숫자를 모두 맞히셨습니다! 게임 종료");
                break;
            }
        }
    }

    public static List<Integer> generateNumbers() {
        List<Integer> answer = new ArrayList<>();
        while (answer.size() < NUMBER_LENGTH) {
            int randomNumber = Randoms.pickNumberInRange(START_RANGE, END_RANGE);

            if (!answer.contains(randomNumber))
                answer.add(randomNumber);

        }
        return answer;
    }

    public static String getUserNumber() {
        String input = Console.readLine();

        if (input.length() != NUMBER_LENGTH)
            throw new InvalidInputException("입력은 " + NUMBER_LENGTH + "자리 숫자여야 합니다.");

        for (char c : input.toCharArray())
            if (!Character.isDigit(c) || c == '0')
                throw new InvalidInputException("입력은 1부터 9까지만 가능합니다.");

        Set<Character> uniqueChars = new HashSet<>();

        for (char c : input.toCharArray())
            uniqueChars.add(c);

        if (uniqueChars.size() != NUMBER_LENGTH)
            throw new InvalidInputException("입력된 숫자는 서로 다른 수여야 합니다.");

        return input;
    }

    public static void printResult(int strikes, int balls) {
        if (strikes > 0 && balls > 0)
            System.out.println(balls + "볼 " + strikes + "스트라이크");
        else if (strikes > 0)
            System.out.println(strikes + "스트라이크");
        else if (balls > 0)
            System.out.println(balls + "볼");
        else
            System.out.println("낫싱");
    }

    public static boolean isRestart() {
        System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
        String input = Console.readLine();
        if ("1".equals(input))
            return true;
        if ("2".equals(input))
            return false;

        throw new InvalidInputException ("잘못된 입력입니다. 1(재시작) 또는 2(종료)를 입력하세요.");
    }

    private static List<Integer> stringToList(String str) {
        List<Integer> list = new ArrayList<>();
        for (char c : str.toCharArray())
            list.add(c - '0');
        return list;
    }
}