package root.Persona.Rollen.Bonusrollen;

import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.ResourceManagement.ImagePath;

import java.awt.*;

public class ReineSeele extends Bonusrolle {
    public static final String ID = "ID_Reine_Seele";
    public static final String NAME = "Reine Seele";
    public static final String IMAGE_PATH = ImagePath.REINE_SEELE_KARTE;
    public static final BonusrollenType TYPE = new Passiv();
    public boolean dayInvincibility = true;
    public static final Color COLOR = Color.MAGENTA;

    public ReineSeele() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.color = COLOR;
    }
}
