
import java.awt.*;
import java.util.*;

public class Tiger extends Critter {
    private int moves;
    private Color tigerColor;
    private final Random random;

    public Tiger(){
        random = new Random();
        getColor(); // Initializes the tigerColor based on the initial conditions.
    }

    public Color getColor() {
        if (moves % 3 == 0) { // Change color every three moves.
            int choice = random.nextInt(3);
            if (choice == 0) {
                tigerColor = Color.RED;
            } else if (choice == 1) {
                tigerColor = Color.GREEN;
            } else {
                tigerColor = Color.BLUE;
            }
        }
        return tigerColor;
    }

    public String toString() {
        return "TGR";
    }

    public Action getMove(CritterInfo info) {
        moves++;
        if (info.getFront() == Neighbor.OTHER) {
            return Action.INFECT;
        } else if (info.getFront() == Neighbor.WALL || info.getRight() == Neighbor.WALL) {
            return Action.LEFT;
        } else if (info.getFront() == Neighbor.SAME) {
            return Action.RIGHT;
        } else {
            return Action.HOP;
        }
    }
}

