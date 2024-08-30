
import java.awt.*;

public class Giant extends Critter{
    private int moves;
    private final String[] phrases = {"fee", "fie", "foe", "fum"};

    public Giant(){
        moves = 0;
    }

    public Color getColor (){
        return Color.GRAY;
    }

    public String toString() {
        // The phrase is determined by dividing the number of moves by 6 and taking the modulus
        // with the length of the phrases array to ensure cycling through the array.
        return phrases[moves / 6 % phrases.length];
    }

    public Action getMove(CritterInfo info) {
        moves++;
        if (info.getFront() == Neighbor.OTHER) {
            return Action.INFECT;
        } else if (info.getFront() == Neighbor.EMPTY) {
            return Action.HOP;
        } else {
            return Action.RIGHT;
        }
    }
}


