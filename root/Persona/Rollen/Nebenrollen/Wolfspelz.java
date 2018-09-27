package root.Persona.Rollen.Nebenrollen;

import root.Persona.Bonusrolle;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.awt.*;

public class Wolfspelz extends Bonusrolle {
    public static final String NAME = "Wolfspelz";
    public static final String IMAGE_PATH = ImagePath.WOLFSPELZ_KARTE;
    public static final NebenrollenType TYPE = new Passiv();
    public static final Color COLOR = Werwölfe.COLOR;

    public Wolfspelz() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.color = COLOR;
    }

    public void tauschen(Bonusrolle bonusrolle) {
        try {
            Spieler spieler = game.findSpielerPerRolle(NAME);
            spieler.bonusrolle = bonusrolle;
        } catch (NullPointerException e) {
            System.out.println(NAME + " nicht gefunden");
        }
    }

    public Bonusrolle getTauschErgebnis() {
        Spieler spieler = game.findSpielerPerRolle(NAME);

        if (spieler != null) {
            Bonusrolle bonusrolle;

            if (spieler.hauptrolle.fraktion.name.equals(Werwölfe.NAME)) {
                bonusrolle = new SchwarzeSeele();
            } else {
                bonusrolle = spieler.bonusrolle;
            }

            return bonusrolle;
        } else {
            return this;
        }
    }
}
