package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Geisterwolf extends Hauptrolle {
    public static final String NAME = "Geisterwolf";
    public static Fraktion fraktion = new Werwölfe();
    public static final String IMAGE_PATH = ImagePath.GEISTERWOLF_KARTE;
    public static boolean killing = true;

    public Geisterwolf() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public boolean isKilling() {
        return killing;
    }
}