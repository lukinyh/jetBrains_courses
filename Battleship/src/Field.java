import java.util.Collection;
import java.util.Scanner;

public class Field {
    static final int SIZE = 10;


    public Field() {
        this.field = createField();
        this.numOfShips = 5;
    }

    public char[][] getField() {
        return field;
    }

    private char[][] field = new char[SIZE][SIZE];
    private int numOfShips;
    public int getNumOfShips() {
        return numOfShips;
    }

    public void fillField(Player player) {
        for (Ship thisShip : player.ships) {
            System.out.printf(Text.ENTER_COORDINATES, thisShip.getName(), thisShip.getLength());
            thisShip.setCoordinates(this);
            addShipOnField(thisShip);
            printField(true);
        }
    }

    public void takeAShot(Player player, Scanner sc) {
        String coordinate = sc.nextLine();
        try {
            int symbol = coordinate.charAt(0);
            int number = Integer.valueOf(coordinate.substring(1));
            if (number < 1 || number > SIZE || symbol < 65 && symbol > 74) {
                System.out.println(Errors.ERROR_OUT_OF_BAND);
                takeAShot(player, sc);
            } else {
                strike(player,symbol - 65, number - 1);
            }
        } catch (Exception e) {
            System.out.println(Errors.ERROR_EXCEPTION);
            takeAShot(player, sc);
        }
    }

    /*
    final static char UNKNOWN_AREA = '~';
    final static char SHIP = 'O';
    final static char HIT = 'X';
    final static char MISS = 'M';
    */
    private void strike(Player player, int symbol, int number) {
        String text;
        if (field[symbol][number] == Text.SHIP) {
            field[symbol][number] = Text.HIT;

            text = changeStatus(player,"" + (char) (symbol + 65) + (number + 1));
        } else if (field[symbol][number] == Text.UNKNOWN_AREA || field[symbol][number] == Text.MISS) {
            field[symbol][number] = Text.MISS;
            text = Text.MISSED;
        } else if (field[symbol][number] == Text.HIT) {
            text = Text.YOU_HIT;
        } else {
            text = Errors.ERROR_CHECKED_COORDINATE;
        }

        printField(false);
        System.out.println();

        if (text.equals(Text.FINISH)) {
            System.out.println(Text.CONGRATULATIONS);
        } else {
            System.out.println(text);

        }
    }


    public String changeStatus(Player player, String coordinate) {
        for (Ship ship : player.ships) {
            Collection location = ship.getLocation();
            if (location.contains(coordinate)) {
                ship.setLength(ship.getLength() - 1);
                if (ship.getStatus() == Text.STATUS_ALIVE) {
                    ship.setStatus(Text.STATUS_HIT);
                    return Text.YOU_HIT;
                } else if (ship.getStatus() == Text.STATUS_HIT) {
                    if (ship.getLength() == 0) {
                        ship.setStatus(Text.STATUS_DEAD);
                        this.numOfShips -= 1;
                        if (this.numOfShips == 0) {
                            return Text.FINISH;
                        } else {
                            return Text.YOU_SANK;
                        }
                    }
                    return Text.YOU_HIT;
                } else if (ship.getStatus() == 'D') {
                    return Text.YOU_SANK;
                }
            }
        }
        return "Something was wrong.";
    }

    public void addShipOnField(Ship ship) {

        //A  B  C  D  E  F  G  H  I  J;
        //65 66 67 68 69 70 71 72 73 74 - vertical - 65
        //1  2  3  4  5  6  7  8  9  10 - horizontal - 1
        //0  1  2  3  4  5  6  7  8  9
        int[][] coordinates = ship.getCoordinates();

        int length = ship.getLength();
        // horizontal
        int lengthHor = length;
        int lengthVert = 1;
        if (coordinates[0][0] != coordinates[1][0]) {
            //vertical
            lengthHor = 1;
            lengthVert = length;
        }
        for (int i = 0; i < lengthVert; i++) {
            for (int j = 0; j < lengthHor; j++) {
                field[coordinates[0][0] - 65 + i][coordinates[0][1] - 1 + j] = Text.SHIP;
            }
        }
    }

    public char[][] createField() {
        char[][] field = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                field[i][j] = Text.UNKNOWN_AREA;
            }
        }
        return field;
    }

    public void printField(boolean isPresentAll) {
        System.out.println(Text.NUMBERS);

        int i = 0;
        for (char[] column : field) {
            System.out.printf("%c ", Text.SYMBOLS.charAt(i));
            i++;
            for (char cell : column) {
                if (cell == Text.SHIP && !isPresentAll) {
                    System.out.print(Text.UNKNOWN_AREA + " ");
                } else {
                    System.out.print(cell + " ");
                }
            }
            System.out.println();
        }
    }
}
