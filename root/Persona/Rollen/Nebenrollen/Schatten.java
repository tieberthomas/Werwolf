package root.Persona.Rollen.Nebenrollen;

import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;

public class Schatten extends Bonusrolle {
    public static final String NAME = "Schatten";
    public static final String IMAGE_PATH = ImagePath.SCHATTEN_KARTE;
    public static final NebenrollenType TYPE = new Passiv();

    public Schatten() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.numberOfPossibleInstances = 100;
    }
}