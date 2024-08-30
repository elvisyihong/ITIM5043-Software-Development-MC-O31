
import java.awt.*;

public class Bear extends Critter {
    private final boolean polar;
    private boolean slash;

    public Bear(boolean polar){
        this.polar = polar;
        this.slash = true; // Initially, the bear displays a slash ("/").
    }

    public Color getColor() {
        // Color.WHITE if the bear is polar, Color.BLACK otherwise
        return polar ? Color.WHITE : Color.BLACK;
    }

    public String toString(){
        // Returns "/" if slash is true, "\\" if slash is false.
        if (slash) {
            return "/";
        } else {
            return "\\";
        }
    }

    public Action getMove(CritterInfo info) {
        slash = !slash;
        if (info.getFront() == Neighbor.OTHER) {
            return Action.INFECT;
        } else if (info.getFront() == Neighbor.EMPTY) {
            return Action.HOP;
        } else {
            return Action.LEFT;
        }
    }
}

