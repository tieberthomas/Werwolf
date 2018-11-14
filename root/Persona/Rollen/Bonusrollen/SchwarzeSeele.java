package root.Persona.Rollen.Bonusrollen;

import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.ResourceManagement.ImagePath;

public class SchwarzeSeele extends Bonusrolle {
    public static final String ID = "ID_Schwarze_Seele";
    public static final String NAME = "Schwarze Seele";
    public static final String IMAGE_PATH = ImagePath.SCHWARZE_SEELE_KARTE;
    public static final BonusrollenType TYPE = new Passiv();

    public SchwarzeSeele() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;
    }
}
