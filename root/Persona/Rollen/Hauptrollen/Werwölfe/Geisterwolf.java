package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Constants.Zeigekarten.Nicht_Tötend;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Geisterwolf extends Hauptrolle {
    public static final String ID = "Geisterwolf";
    public static final String NAME = "Geisterwolf";
    public static final String IMAGE_PATH = ImagePath.GEISTERWOLF_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public Geisterwolf() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.killing = true;
    }

    public Zeigekarte isTötendInfo(Spieler requester) {
        return new Nicht_Tötend();
    }
}