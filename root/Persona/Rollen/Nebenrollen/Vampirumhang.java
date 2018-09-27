package root.Persona.Rollen.Nebenrollen;

import root.Persona.Fraktionen.Vampire;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.awt.*;

public class Vampirumhang extends Nebenrolle {
    public static final String NAME = "Vampirumhang";
    public static final String IMAGE_PATH = ImagePath.VAMPIRUMHANG_KARTE;
    public static final NebenrollenType TYPE = new Passiv();
    public static final Color COLOR = Vampire.COLOR;

    public Vampirumhang() {
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
        Spieler spieler = game.findSpielerPerRolle(NAME);

        if (spieler != null) {
            Nebenrolle nebenrolle;

            if (spieler.hauptrolle.fraktion.name.equals(Vampire.NAME)) {
                nebenrolle = new SchwarzeSeele();
            } else {
                nebenrolle = spieler.nebenrolle;
            }

            return nebenrolle;
        } else {
            return this;
        }
    }
}
