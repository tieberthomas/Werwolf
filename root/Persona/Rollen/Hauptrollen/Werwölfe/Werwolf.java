package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Werwolf extends Hauptrolle {
    public static final String NAME = "Werwolf";
    public static Fraktion fraktion = new Werwölfe();
    public static final String IMAGE_PATH = ImagePath.WERWOLF_KARTE;
    public static boolean killing = true;

    public Werwolf() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.numberOfPossibleInstances = 100;
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