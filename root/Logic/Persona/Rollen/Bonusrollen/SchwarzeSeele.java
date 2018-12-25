package root.Logic.Persona.Rollen.Bonusrollen;

import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.List;

public class SchwarzeSeele extends Bonusrolle {
    public static final String ID = "ID_Schwarze_Seele";
    public static final String NAME = "Schwarze Seele";
    public static final String IMAGE_PATH = ImagePath.SCHWARZE_SEELE_KARTE;
    public static final BonusrollenType TYPE = new Passiv();

    public List<String> geseheneIrrlichter = new ArrayList<>();

    public SchwarzeSeele() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;
    }
}
