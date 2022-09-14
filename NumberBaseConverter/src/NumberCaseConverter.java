import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NumberCaseConverter {

    protected class Messages {
        static final String enterTwoNumber = "Enter two numbers in format: {source base} {target base} (To quit type /exit) ";
        static final String mainMenu = "Enter number in base %s to convert to base %s (To go back type /back) ";
        static final String incorrectVariantOfMenu = "Don't understand answer.";
        static final String conversionResult = "Conversion result: ";

    }

    protected class Variants {
        static final String EXIT = "/exit";
        static final String BACK = "/back";
    }

    static final String abc = "0123456789abcdefghijklmnopqrstuvwxyz" ;

    static List<Character> list = new ArrayList<>();

    public static void main(String[] args) {
        enterTwoNumbers();
    }

    private static void enterTwoNumbers() {
        System.out.print(Messages.enterTwoNumber);
        Scanner sc = new Scanner(System.in);

        String[] message = sc.nextLine().trim().split(" ");
        if (message[0].equals(Variants.EXIT)) {
        } else {
            try {
                int sourceBase = Integer.valueOf(message[0]);
                int targetBase = Integer.valueOf(message[1]);
                start(sourceBase, targetBase);
            } catch (Exception e) {
                System.out.println(Messages.incorrectVariantOfMenu);
                enterTwoNumbers();
            }
        }
    }

    private static void start(int sourceBase, int targetBase) {

        Scanner sc = new Scanner(System.in);
        System.out.printf(Messages.mainMenu, sourceBase, targetBase);
        String str = sc.nextLine().trim();
        switch (str) {
            case Variants.BACK:
                enterTwoNumbers();
                break;
            default:
                try {
                    BigDecimal number;
                    if (sourceBase != 10) {
                        number = convertToDec(str, sourceBase);
                    } else {
                        number = new BigDecimal(str);
                    }

                    System.out.print(Messages.conversionResult);
                    if (targetBase != 10) {

                        if (str.contains(".")) {
                            BigInteger numWholePart = number.toBigInteger();
                            BigDecimal numFractionalPart = number.subtract(new BigDecimal(numWholePart));

                            convertWholePartFromDec(numWholePart, targetBase);
                            convertFractionalPartFromDec(numFractionalPart, targetBase);
                        } else {
                            convertWholePartFromDec(number.toBigInteger(), targetBase);
                        }

                        printList();

                    } else {
                        int scale = 0;
                        if (str.contains(".")) {
                            scale = 5;
                        }
                        System.out.println(number.setScale(scale, RoundingMode.HALF_DOWN));
                    }

                } catch (Exception e) {
                    System.out.println(Messages.incorrectVariantOfMenu);
                } finally {
                    start(sourceBase, targetBase);
                    break;
                }
        }
    }

    private static void printList() {
        for (int i = 0; i < list.size(); i++) {
            System.out.print(list.get(i));
        }
        list.clear();
        System.out.println();
    }

    //TODO: check that input is correct
    private static Boolean isNumberCorrect(String number, int base) {
        return false;
    }

    private static BigDecimal convertToDec(String number, int base) {
        int len = number.length();
        int i = 0;
        int exponent;
        if (number.contains(".")) {
            exponent = number.indexOf('.') - 1;
        } else {
            exponent = len - 1;
        }

        BigDecimal result = new BigDecimal("0");
        while (i < len) {
            if (number.charAt(i) != '.') {
                BigDecimal pow = BigDecimal.valueOf(Math.pow(base, exponent));
                BigDecimal num = BigDecimal.valueOf(abc.indexOf(number.charAt(i))).multiply(pow);
                result = result.add(num);
                exponent -= 1;
            }
            i++;
        }

        return result;
    }

    private static void convertWholePartFromDec(BigInteger number, int base) {
        BigInteger biBase = BigInteger.valueOf(base);
        if (number.max(biBase).equals(number) || number.equals(biBase)) {
            int index = number.mod(biBase).intValue();
            convertWholePartFromDec(number.divide(biBase), base);
            list.add(abc.charAt(index));
        } else {
            list.add(abc.charAt(number.intValue()));
        }
    }

    private static void convertFractionalPartFromDec(BigDecimal number, int targetBase) {
        list.add('.');
        BigDecimal bdBase = BigDecimal.valueOf(targetBase);
        for (int i = 0; i < 5; i++) {
            BigDecimal nextNum = number.multiply(bdBase);
            BigDecimal natural = nextNum.setScale(0, RoundingMode.FLOOR);
            list.add(abc.charAt(natural.intValue()));
            number = nextNum.subtract(natural);
        }
    }
}