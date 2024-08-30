
import java.awt.*;

public class WhiteTiger extends Tiger {
    private boolean hasInfected;

    public WhiteTiger(){
        hasInfected = false;
    }

    public Color getColor() {
        return Color.WHITE;
    }

    public String toString() {
        if (hasInfected){
            return super.toString(); // Returns "TGR" from the Tiger class.
        } else {
            return "tgr"; // Returns "tgr" in lowercase if it hasn't infected yet.
        }
    }

    public Action getMove(CritterInfo info) {
        if (info.getFront() == Neighbor.OTHER){
            hasInfected = true;
        }
        return super.getMove(info); // Calls the Tiger's getMove method.
    }
}

