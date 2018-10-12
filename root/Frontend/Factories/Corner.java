package root.Frontend.Factories;

import static root.Frontend.Factories.Direction.NEGATIVE;
import static root.Frontend.Factories.Direction.POSITIVE;

public enum Corner {
    UPPERLEFT(POSITIVE, POSITIVE),
    UPPERRIGHT(NEGATIVE, POSITIVE),
    LOWERLEFT(POSITIVE, NEGATIVE),
    LOWERRIGHT(NEGATIVE, NEGATIVE);

    public int xDirection;
    public int yDirection;

    Corner(Direction xDirection, Direction yDirection) {
        this.xDirection = xDirection.modificatior;
        this.yDirection = yDirection.modificatior;
    }
}
