import java.util.Scanner;

public class Player {
    private final String name;

    public Ship aircraftCarrier = new Ship("Aircraft Carrier", 5);
    public Ship battleship = new Ship("Battleship", 4);
    public Ship submarine = new Ship("Submarine", 3);
    public Ship cruiser = new Ship("Cruiser", 3);
    public Ship destroyer = new Ship("Destroyer", 2);

    Ship[] ships = {aircraftCarrier, battleship, submarine, cruiser, destroyer};

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void yourTurn(Scanner sc, Field field, Player toWhom) {
        field.takeAShot(toWhom, sc);
    }
}
