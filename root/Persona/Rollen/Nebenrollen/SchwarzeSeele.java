package root.Persona.Rollen.Nebenrollen;

import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.NebenrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;

public class SchwarzeSeele extends Bonusrolle {
    public static final String NAME = "Schwarze Seele";
    public static final String IMAGE_PATH = ImagePath.SCHWARZE_SEELE_KARTE;
    public static final BonusrollenType TYPE = new Passiv();

    public SchwarzeSeele() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;
    }
}
