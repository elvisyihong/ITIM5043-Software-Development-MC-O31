
import java.awt.*;
import java.util.*;

public class NinjaCat extends Critter {
    private int moves;
    private boolean hasInfected;
    private final Random random;

    public NinjaCat (){
        hasInfected=false;
        moves = 0;
        random = new Random();
    }

    public Color getColor() {
        if (hasInfected){
            return Color.orange;
        } else {
            return Color.MAGENTA;
        }

    }

    public String toString() {
        if (hasInfected){
            return "X";
        } else {
            return "x";
        }

    }

    public Action getMove(CritterInfo info) {
        moves++;
        if (info.getFront() == Neighbor.OTHER) {
            hasInfected=true;
            return Action.INFECT;
        } else if (info.getFront() == Neighbor.EMPTY) {
            return Action.HOP;
        } else {
            return random.nextBoolean() ? Action.LEFT : Action.RIGHT;
        }
    }
}

