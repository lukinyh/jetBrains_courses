import java.util.*;

public class Main {
    static boolean isXOrder = true;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        char[] field = new char[9];
        initializeField(field);

        while (resultOfWin(field).equals(Text.GAME_NOT_FINISHED)) {
            enterCoordinates(sc, field);
        }
    }

    private static void initializeField(char[] field) {
        for (int i = 0; i < 9; i++) {
            field[i] = Text.BLANK;
        }
        printState(field);
    }

    private static void enterCoordinates(Scanner sc, char[] field) {
        try {
            if (isXOrder) {
                System.out.println(Text.TURN_X);
            } else {
                System.out.println(Text.TURN_O);
            }
            System.out.print(Text.ENTER_COORDINATES);
            int num1 = sc.nextInt();
            int num2 = sc.nextInt();

            if (num1 > 3 || num1 < 1 || num2 > 3 || num2 < 1) {
                System.out.println(Text.ERROR_OUT_OF_BOUND);
                enterCoordinates(sc, field);
            } else if (isCellOccuped(num1, num2, field)) {
                System.out.println(Text.ERROR_CELL_IS_OCCUPIED);
                enterCoordinates(sc, field);
            } else {
                if (isXOrder) {
                    addElementAndPrint(num1, num2, field, Text.X);
                    isXOrder = false;
                } else {
                    addElementAndPrint(num1, num2, field, Text.O);
                    isXOrder = true;
                }
            }
        } catch (Exception e) {
            System.out.println(Text.ERROR_NOT_NUMBERS);
            enterCoordinates(sc, field);
        }
    }

    private static void addElementAndPrint(int num1, int num2, char[] field, char elem) {
        field[(num1 - 1) * 3 + num2 - 1] = elem;
        printState(field);
    }

    private static boolean isCellOccuped(int num1, int num2, char[] field) {
        if (field[(num1 - 1) * 3 + num2 - 1] != Text.BLANK) {
            return true;
        } else {
            return false;
        }
    }


    private static String resultOfWin(char[] field) {

        boolean winX = isWin(field, Text.X);
        boolean winO = isWin(field, Text.O);

        if (winX) {
            System.out.println(Text.X_WINS);
            return Text.X_WINS;
        } else if (winO) {
            System.out.println(Text.O_WINS);
            return Text.O_WINS;
        } else if (isGameNotFinished(field)) {
            return Text.GAME_NOT_FINISHED;
        } else {
            System.out.println(Text.DRAW);
            return Text.DRAW;
        }
    }

    private static boolean isGameNotFinished(char[] field) {
        return frequency(field, Text.BLANK) > 0;
    }

    private static boolean isWin(char[] field, char element) {
        // horizontal lines
        for (int i = 0; i < 9; i++) {
            if (i % 3 == 0) {
                if (field[i] == element && field[i + 1] == element && field[i + 2] == element) {
                    return true;
                }
            }
        }

        // vertical lines
        for (int i = 0; i < 3; i++) {
            if (field[i] == element && field[i + 3] == element && field[i + 6] == element) {
                return true;
            }
        }

        if (field[0] == element && field[4] == element && field[8] == element) {
            return true;
        }

        if (field[2] == element && field[4] == element && field[6] == element) {
            return true;
        }

        return false;
    }

    private static int frequency(char[] field, char element) {
        int count = 0;
        for (char ch : field) {
            if (ch == element) {
                count++;
            }
        }
        return count;
    }

    private static void printState(char[] field) {
        System.out.println("---------");
        System.out.print("| ");
        for (int i = 1; i <= 9; i++) {
            System.out.print(field[i - 1] + " ");
            if (i % 3 == 0) {
                System.out.print("|");
                System.out.println();
                if (i != 9) {
                    System.out.print("| ");
                }
            }
        }
        System.out.println("---------");
    }
}
