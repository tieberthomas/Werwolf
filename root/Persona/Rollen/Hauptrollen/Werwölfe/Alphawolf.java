package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Alphawolf extends Hauptrolle {
    public static final String NAME = "Alphawolf";
    public static final String IMAGE_PATH = ImagePath.ALPHAWOLF_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public Alphawolf() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.killing = true;
    }
}