import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class BullsAncCows {
    //TODO: check length of input
    //TODO: check correct symbols in input
    static int bulls = 0; //бык - совпадающее число стоит на правильном месте
    static int cows = 0; // корова - совпадающее число стоит на неправильном месте

    final static String abc = "0123456789abcdefjhijklmnopqrstuvwxyz";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Input the length of the secret code:");
        String lengthStr = sc.nextLine();
        if (isNotValidNumber(lengthStr)) {
            System.out.printf("Error: \"%s\" isn't a valid number.\n", lengthStr);
            return;
        }
        int length = Integer.parseInt(lengthStr);

        System.out.println("Input the number of possible symbols in the code:");
        String numPossibleStr = sc.nextLine();
        if (isNotValidNumber(numPossibleStr)) {
            System.out.printf("Error: \"%s\" isn't a valid number.\n", numPossibleStr);
            return;
        }
        int numPossible = Integer.parseInt(numPossibleStr);

        if (numPossible > 36) {
            System.out.println("Error: maximum number of possible symbols in the code is 36 (0-9, a-z).");
            return;
        }

        if (numPossible < length) {
            System.out.printf("Error: it's not possible to generate code with a length of %d with %d unique symbols.\n", length, numPossible);
            return;
        }

        startGame(length, numPossible, sc);


    }

    private static boolean isNotValidNumber(String potentialNumber) {
        return !potentialNumber.matches("[-+]?\\d+") || Integer.parseInt(potentialNumber) < 1;
    }

    private static void startGame(int length, int numPossible, Scanner sc) {
        System.out.println("Okay, let's start a game!");
        String secretCode;
        secretCode = generatePseudoNum(length, numPossible).reverse().toString();
        System.out.printf("The secret is prepared: %s.\n", shifre(length, numPossible));
        int turn = 1;
        //String number = sc.nextLine();

        boolean isContinue = true;
        do {
            System.out.printf("Turn %d:\n", turn);
            grader(secretCode, sc.nextLine(), length);
            printResult(cows, bulls, secretCode);
            if (bulls == length) {
                isContinue = false;
                System.out.println("Congratulations! You guessed the secret code.");

            }
            turn++;
        } while (isContinue);
    }

    private static String shifre(int length, int numPossible) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < length; i++) {
            str.append("*");
        }

        if (numPossible < 10) {
            str.append(" (0-");
            str.append(numPossible);
        } else {
            str.append(" (0-9, a");
            if (numPossible != 10) {
                str.append("-");
                str.append(abc.charAt(numPossible - 1));
            }
        }
        str.append(")");

        return str.toString();
    }

    private static StringBuilder generatePseudoNum(int length, int numPossible) {
        Random rand = new Random();
        Set<Integer> collection = new HashSet<Integer>();

        while (collection.size() != length) {
            int pseudoRandomNumber = rand.nextInt(numPossible);
            if (!collection.contains(pseudoRandomNumber)) {
                collection.add(pseudoRandomNumber);
            }
        }
        StringBuilder strPseudoNum = new StringBuilder();

        for (Integer i : collection) {
            strPseudoNum.append(abc.charAt(i));
        }
        return strPseudoNum;
    }

    private static void grader(String secretCode, String number, int length) {
        if (secretCode.length() < length) {
            System.out.println("Number should include 4 symbols");
            return;
        }
        bulls = 0;
        cows = 0;

        for (int i = 0; i < secretCode.length(); i++) {
            if (number.contains(secretCode.subSequence(i, i+1))) {
                if (number.charAt(i) == secretCode.charAt(i)) {
                    bulls++;
                } else {
                    cows++;
                }
            }
        }
    }

    private static void printResult(int cows, int bulls, String secretCode){
        System.out.print("Grade: ");

        if (cows == 0 && bulls == 0) {
            System.out.print("None");
        }

        if (cows != 0 && bulls == 0) {
            System.out.printf("%d cow(s)", cows);
        }

        if (cows == 0 && bulls != 0) {
            System.out.printf("%d bull(s)", bulls);
        }

        if (cows != 0 && bulls != 0) {
            System.out.printf("%d bull(s) and %d cow(s)", bulls, cows);
        }
        System.out.println();
    }
}
