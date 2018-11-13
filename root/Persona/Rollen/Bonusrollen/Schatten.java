package root.Persona.Rollen.Bonusrollen;

import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.ResourceManagement.ImagePath;

public class Schatten extends Bonusrolle {
    public static final String ID = "ID_Schatten";
    public static final String NAME = "Schatten";
    public static final String IMAGE_PATH = ImagePath.SCHATTEN_KARTE;
    public static final BonusrollenType TYPE = new Passiv();

    public Schatten() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.numberOfPossibleInstances = 100;
    }
}