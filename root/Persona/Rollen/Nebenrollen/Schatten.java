package root.Persona.Rollen.Nebenrollen;

import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;

public class Schatten extends Nebenrolle {
    public static final String NAME = "Schatten";
    public static final String IMAGE_PATH = ImagePath.SCHATTEN_KARTE;
    public NebenrollenType type = new Passiv();

    public Schatten() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.numberOfPossibleInstances = 100;
    }

    @Override
    public NebenrollenType getType() {
        return type;
    }
}