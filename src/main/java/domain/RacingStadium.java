package domain;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.StringJoiner;

public class RacingStadium {
    private final Car[] raceCars;
    private final int moveCount;

    public RacingStadium() {
        this.raceCars = getReady();
        this.moveCount = inputMoveCount();
    }

    private Car[] getReady() {
        String[] participants = recruitParticipants();
        Car[] startLine = new Car[participants.length];

        for (int i = 0; i < participants.length; i++) {
            startLine[i] = new Car(participants[i]);
        }
        return startLine;
    }

    private String[] recruitParticipants() {
        String[] waiterList;

        do {
            System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
            Scanner scanner = new Scanner(System.in);
            String waiters = scanner.nextLine();
            waiterList = waiters.split(",");
        } while (!isLengthValid(waiterList));

        return waiterList;
    }

    private boolean isLengthValid(String[] waiterList) {
        final int lengthLimit = 5;

        for (String waiter : waiterList) {
            if (waiter.length() > lengthLimit || waiter.length() == 0) {
                System.out.printf("이름은 없거나, %d 글자를 초과할 수 없습니다.", lengthLimit);
                return false;
            }
        }
        return true;
    }

    private int inputMoveCount() {
        String inputString;
        int moveCount;

        do {
            System.out.println("시도할 회수는 몇 회인가요?");
            Scanner scanner = new Scanner(System.in);
            inputString = scanner.nextLine();
            moveCount = getNumber(inputString);
        } while (moveCount == 0);

        return moveCount;
    }

    private int getNumber(String inputString) {
        for (int i = 0; i < inputString.length(); i++) {
            if (!Character.isDigit(inputString.charAt(i))) {
                System.out.println("1 이상의 '숫자'를 입력해주세요.");
                return 0;
            }
        }
        return Integer.parseInt(inputString);
    }

    public void startRace() {
        System.out.println("\n실행결과");
        for (int i = 0; i < moveCount; i++) {
            playRace();
            System.out.println("\n");
        }
    }

    private void playRace() {
        Random random = new Random();

        for (Car raceCar : raceCars) {
            int randomNumber = random.nextInt(9);
            raceCar.tryToGoForward(randomNumber);
            raceCar.printCurrentStatus();
        }
    }

    public void announceWinners() {
        ArrayList<String> winnerList = new ArrayList<String>();
        winnerList = update(winnerList);

        System.out.printf("%s가 최종 우승했습니다.", extractName(winnerList));
    }

    private ArrayList<String> update(ArrayList<String> winnerList) {
        int maxPosition = 0;
        for (Car raceCar : raceCars) {
            int position = raceCar.getPosition();
            if (position < maxPosition) {
                continue;
            }
            if (position > maxPosition) {
                winnerList = new ArrayList<String>();
                maxPosition = position;
            }
            winnerList.add(raceCar.getName());
        }
        return winnerList;
    }

    private String extractName(ArrayList<String> winnerList) {
        StringJoiner stringJoiner = new StringJoiner(", ");

        for (String winner : winnerList) {
            stringJoiner.add(winner);
        }
        return stringJoiner.toString();
    }
}
