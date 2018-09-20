package root.Persona.Rollen.Nebenrollen;

import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;

public class Schatten extends Nebenrolle {
    public static final String NAME = "Schatten";
    public static final String IMAGE_PATH = ImagePath.SCHATTEN_KARTE;
    public static int numberOfPossibleInstances = 100;
    public NebenrollenType type = new Passiv();

    public Schatten() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
    }

    @Override
    public int getNumberOfPossibleInstances() {
        return numberOfPossibleInstances;
    }

    @Override
    public NebenrollenType getType() {
        return type;
    }
}