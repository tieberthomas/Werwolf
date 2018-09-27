package root.Persona.Rollen.Nebenrollen;

import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.awt.*;

public class Wolfspelz extends Nebenrolle {
    public static final String NAME = "Wolfspelz";
    public static final String IMAGE_PATH = ImagePath.WOLFSPELZ_KARTE;
    public NebenrollenType type = new Passiv();
    public Color farbe = Werwölfe.farbe;

    public Wolfspelz() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
    }

    public void tauschen(Nebenrolle nebenrolle) {
        try {
            Spieler spieler = game.findSpielerPerRolle(NAME);
            spieler.nebenrolle = nebenrolle;
        } catch (NullPointerException e) {
            System.out.println(NAME + " nicht gefunden");
        }
    }

    public Nebenrolle getTauschErgebnis() {
        Spieler spieler = game.findSpielerPerRolle(NAME);

        if (spieler != null) {
            Nebenrolle nebenrolle;

            if (spieler.hauptrolle.fraktion.name.equals(Werwölfe.NAME)) {
                nebenrolle = new SchwarzeSeele();
            } else {
                nebenrolle = spieler.nebenrolle;
            }

            return nebenrolle;
        } else {
            return this;
        }
    }

    @Override
    public NebenrollenType getType() {
        return type;
    }

    @Override
    public Color getFarbe() {
        return farbe;
    }
}
