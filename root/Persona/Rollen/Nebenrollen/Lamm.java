package root.Persona.Rollen.Nebenrollen;

import root.Persona.Bonusrolle;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.awt.*;

public class Lamm extends Bonusrolle {
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

    public void tauschen(Bonusrolle bonusrolle) {
        try {
            Spieler spieler = game.findSpielerPerRolle(NAME);
            spieler.bonusrolle = bonusrolle;
        } catch (NullPointerException e) {
            System.out.println(NAME + " nicht gefunden");
        }
    }

    public Bonusrolle getTauschErgebnis() {
        try {
            Spieler spieler = game.findSpielerPerRolle(NAME);
            Bonusrolle bonusrolle;

            if (spieler.hauptrolle.fraktion.name.equals(Bürger.NAME)) {
                bonusrolle = new ReineSeele();
            } else {
                bonusrolle = spieler.bonusrolle;
            }

            return bonusrolle;
        } catch (NullPointerException e) {
            System.out.println(NAME + " nicht gefunden");
        }

        return this;
    }
}
