package root.Logic.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Nicht_Tötend;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

public class Geisterwolf extends Hauptrolle {
    public static final String ID = "ID_Geisterwolf";
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