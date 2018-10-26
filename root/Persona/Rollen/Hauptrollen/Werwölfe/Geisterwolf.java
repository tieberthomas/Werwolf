package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Constants.TötendInformationType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import static root.Persona.Rollen.Constants.TötendInformationType.NICHT_TÖTEND;

public class Geisterwolf extends Hauptrolle {
    public static final String NAME = "Geisterwolf";
    public static final String IMAGE_PATH = ImagePath.GEISTERWOLF_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public Geisterwolf() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.killing = true;
    }

    public TötendInformationType isTötendInfo(Spieler requester) {
        return NICHT_TÖTEND;
    }
}