package root.Frontend.Factories;

public enum Direction {
    POSITIVE(1),
    NEGATIVE((-1));

    int modificatior;

    Direction(int directionModificatior) {
        this.modificatior = directionModificatior;
    }
}
