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
    public static boolean unique = true;
    public NebenrollenType type = new Passiv();
    public Color farbe = Vampire.farbe;

    public Vampirumhang() {
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

            if (spieler.hauptrolle.getFraktion().name.equals(Vampire.NAME)) {
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
