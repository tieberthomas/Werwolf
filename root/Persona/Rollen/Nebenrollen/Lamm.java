package root.Persona.Rollen.Nebenrollen;

import root.Persona.Fraktionen.Bürger;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.awt.*;

public class Lamm extends Nebenrolle {
    public static final String NAME = "Lamm";
    public static final String IMAGE_PATH = ImagePath.LAMM_KARTE;
    public static final NebenrollenType TYPE = new Passiv();
    public static final Color COLOR = Bürger.COLOR;

    public Lamm() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.color = COLOR;
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
        try {
            Spieler spieler = game.findSpielerPerRolle(NAME);
            Nebenrolle nebenrolle;

            if (spieler.hauptrolle.fraktion.name.equals(Bürger.NAME)) {
                nebenrolle = new ReineSeele();
            } else {
                nebenrolle = spieler.nebenrolle;
            }

            return nebenrolle;
        } catch (NullPointerException e) {
            System.out.println(NAME + " nicht gefunden");
        }

        return this;
    }
}
