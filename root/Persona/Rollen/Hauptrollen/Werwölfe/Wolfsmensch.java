package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Wolfsmensch extends Hauptrolle {
    public static final String NAME = "Wolfsmensch";
    public static final String IMAGE_PATH = ImagePath.WOLFSMENSCH_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public Wolfsmensch() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;
    }
}
