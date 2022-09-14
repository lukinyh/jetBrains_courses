import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Ship {
    private String name;
    private int length;
    private char status;
    private int[][] coordinates;
    private Collection<String> location = new ArrayList<>();
    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
        this.status = Text.STATUS_ALIVE;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int[][] getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Field field) {
        Scanner sc = new Scanner(System.in);

        try {
            String[] coordinate = sc.nextLine().split(" ");

            char startSymbol = coordinate[0].charAt(0);
            int startNumber = Integer.parseInt(coordinate[0].substring(1));

            char endSymbol = coordinate[1].charAt(0);
            int endNumber = Integer.parseInt(coordinate[1].substring(1));

            if (!isCoordinateCorrect(startSymbol, startNumber, endSymbol, endNumber)) {
                System.out.println(Errors.ERROR_NOT_EXISTED_COORDINATES);
                this.setCoordinates(field);
                return ;
            }
            if (!isLengthCorrect(this.getLength(), startSymbol, startNumber, endSymbol, endNumber)) {
                System.out.printf(Errors.ERROR_INCORRECT_LINE, this.getLength());
                this.setCoordinates(field);
                return ;
            }

            if (isShipTouched(field, this.getLength(), startSymbol, startNumber, endSymbol, endNumber)) {
                System.out.println(Errors.ERROR_TOO_CLOSE);
                this.setCoordinates(field);
                return ;
            }

            if (startSymbol < endSymbol || startNumber < endNumber) {
                this.coordinates = new int[][]{{startSymbol, startNumber}, {endSymbol, endNumber}};
                setLocation(startSymbol, startNumber, endSymbol, endNumber);
            } else {
                this.coordinates = new int[][]{{endSymbol, endNumber}, {startSymbol, startNumber}};
                setLocation(endSymbol, endNumber, startSymbol, startNumber);
            }


        } catch (Exception e) {
            System.out.println(Errors.ERROR_UNEXPECTED);
            this.setCoordinates(field);
            return;
        }
    }

    public Collection<String> getLocation() {
        return this.location;
    }

    private void setLocation(int startSymbol, int startNumber, int endSymbol, int endNumber) {
        int lengthHor = length;
        int lengthVert = 1;
        if (startSymbol != endSymbol) {
            //vertical
            lengthHor = 1;
            lengthVert = length;
        }
        int symbol;
        int number;
        for (int i = 0; i < lengthVert; i++) {
            for (int j = 0; j < lengthHor; j++) {
                symbol = startSymbol + i;
                number = startNumber + j;
                location.add("" + (char)symbol + number);
            }
        }
    }

    private boolean isShipTouched(Field field, int length, int startSymbol, int startNumber, int endSymbol, int endNumber) {
        int lengthHor = length;
        int lengthVert = 1;
        if (startSymbol != endSymbol) {
            //vertical
            lengthHor = 1;
            lengthVert = length;
            if (startSymbol > endSymbol) {
                int temp = startSymbol;
                startSymbol = endSymbol;
                endSymbol = temp;
            }
        } else if (startNumber > endNumber) {
            // horizontal
            int temp = startNumber;
            startNumber = endNumber;
            endNumber = temp;
        }

        startSymbol -= 65;
        startNumber -= 1;
        endSymbol -= 65;
        endNumber -= 1;

        int symbols;
        int numbers;
        for (int i = 0; i < lengthVert; i++) {
            for (int j = 0; j < lengthHor; j++) {
                symbols = startSymbol + i;
                numbers = startNumber + j;
                if (field.getField()[symbols][numbers] == Text.SHIP) {
                    return true;
                }
                if (symbols > 0 && field.getField()[symbols - 1][numbers] == Text.SHIP) {
                    return true;
                }
                if (symbols < field.SIZE - 1 && field.getField()[symbols + 1][numbers] == Text.SHIP) {
                    return true;
                }
                if (numbers > 0 && field.getField()[symbols][numbers - 1] == Text.SHIP) {
                    return true;
                }
                if (numbers < field.SIZE - 1 && field.getField()[symbols][numbers + 1] == Text.SHIP) {
                    return true;
                }
            }
        }

        if (startSymbol > 0 && startNumber > 0 && field.getField()[startSymbol - 1][startNumber - 1] == Text.SHIP) {
            return true;
        }
        if (startSymbol > 0 &&endNumber < field.SIZE - 1 && field.getField()[startSymbol - 1][endNumber + 1] == Text.SHIP) {
            return true;
        }
        if (endSymbol < field.SIZE - 1 && startNumber > 0 && field.getField()[endSymbol + 1][startNumber - 1] == Text.SHIP) {
            return true;
        }
        if (endSymbol < field.SIZE - 1 && endNumber < field.SIZE - 1 && field.getField()[endSymbol + 1][endNumber + 1] == Text.SHIP) {
            return true;
        }

        return false;
    }

    private boolean isLengthCorrect(int length, char startSymbol, int startNumber, char endSymbol, int endNumber) {
        int lengthHor = Math.abs(startNumber - endNumber) + 1;

        int lengthVert = Math.abs(Text.SYMBOLS.indexOf(startSymbol) - Text.SYMBOLS.indexOf(endSymbol)) + 1;

        if (lengthHor > 1 && lengthVert > 1) {
            return false;
        }
        if (lengthHor == lengthVert) {
            return false;
        }
        if (lengthHor != length && lengthVert != length) {
            return false;
        }
        return true;
    }

    private boolean isCoordinateCorrect(char startSymbol, int startNumber, char endSymbol, int endNumber) {
        if (Text.SYMBOLS.indexOf(startSymbol) == -1|| Text.SYMBOLS.indexOf(endSymbol) == -1) {
            return false;
        }

        if ((startNumber < 1 && startNumber > Field.SIZE) || (endNumber < 1 || endNumber > Field.SIZE)) {
            return false;
        }
        return true;
    }
}
