package root.Logic.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Blutwolf extends Hauptrolle {
    public static final String ID = "ID_Blutwolf";
    public static final String NAME = "Blutwolf";
    public static final String IMAGE_PATH = ImagePath.BLUTWOLF_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();
    public static int deadStacks = 0;
    public static boolean deadly = false;

    public Blutwolf() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.killing = true;
    }
}