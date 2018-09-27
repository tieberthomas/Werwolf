package root.Persona.Rollen.Nebenrollen;

import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.NebenrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;

public class ReineSeele extends Bonusrolle {
    public static final String NAME = "Reine Seele";
    public static final String IMAGE_PATH = ImagePath.REINE_SEELE_KARTE;
    public static final BonusrollenType TYPE = new Passiv();
    public boolean dayInvincibility = true;

    public ReineSeele() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;
    }
}
