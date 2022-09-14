import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Field fieldForPlayer1 = new Field();

        Player player1 = new Player(Text.PLAYER_1);
        preparation(fieldForPlayer1, player1);

        Field fieldForPlayer2 = new Field();
        Player player2 = new Player(Text.PLAYER_2);
        preparation(fieldForPlayer2, player2);

        System.out.println(Text.START);

        boolean finish = false;
        boolean isOrder1 = true;
        do {
            if (isOrder1) {

                play(sc, player1, fieldForPlayer1, player2, fieldForPlayer2);
                if (fieldForPlayer2.getNumOfShips() == 0) {
                    finish = true;
                } else {
                    isOrder1 = false;
                    promptEnterKey();
                }
            } else {
                play(sc, player2, fieldForPlayer2, player1, fieldForPlayer1);
                if (fieldForPlayer1.getNumOfShips() == 0) {
                    finish = true;
                } else {
                    isOrder1 = true;
                    promptEnterKey();
                }
            }

        } while (!finish);
        System.out.println(Text.WIN);
    }

    private static void play(Scanner sc, Player player1, Field fieldForPlayer1, Player player2, Field fieldForPlayer2) {
        System.out.printf(Text.TURN, player1.getName());
        fieldForPlayer2.printField(false);
        System.out.println(Text.SEPARATOR);
        fieldForPlayer1.printField(true);

        player1.yourTurn(sc, fieldForPlayer2, player2);
    }

    private static void preparation(Field fieldForPlayer, Player player) {
        System.out.printf(Text.PLACE_SHIP, player.getName());
        fieldForPlayer.printField(true);
        fieldForPlayer.fillField(player);
        fieldForPlayer.printField(true);

        promptEnterKey();
    }
    public static void promptEnterKey() {
        System.out.println(Text.PRESS_ENTER);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}