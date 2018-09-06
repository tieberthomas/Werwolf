package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Wolfsmensch extends Hauptrolle {
    public static final String name = "Wolfsmensch";
    public static Fraktion fraktion = new Werwölfe();
    public static final String imagePath = ImagePath.WOLFSMENSCH_KARTE;

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
}
