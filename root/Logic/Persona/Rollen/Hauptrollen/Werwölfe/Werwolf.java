package root.Logic.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Hauptrolle;
import root.ResourceManagement.ImagePath;

public class Werwolf extends Hauptrolle {
    public static final String ID = "ID_Werwolf";
    public static final String NAME = "Werwolf";
    public static final String IMAGE_PATH = ImagePath.WERWOLF_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public Werwolf() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.numberOfPossibleInstances = 100;
        this.killing = true;
    }
}