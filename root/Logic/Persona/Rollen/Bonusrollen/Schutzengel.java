package root.Logic.Persona.Rollen.Bonusrollen;

import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.ResourceManagement.ImagePath;

import java.awt.*;

public class Schutzengel extends Bonusrolle {
    public static final String ID = "ID_Schutzengel";
    public static final String NAME = "Schutzengel";
    public static final String IMAGE_PATH = ImagePath.SCHUTZENGEL_KARTE;
    public static final BonusrollenType TYPE = new Passiv();
    public boolean dayInvincibility = true;
    public static final Color COLOR = Color.MAGENTA;

    public Schutzengel() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.color = COLOR;
    }
}
