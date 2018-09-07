package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Geisterwolf extends Hauptrolle {
    public static final String name = "Geisterwolf";
    public static Fraktion fraktion = new Werwölfe();
    public static final String imagePath = ImagePath.GEISTERWOLF_KARTE;
    public static boolean killing = true;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean isKilling() {
        return killing;
    }
}