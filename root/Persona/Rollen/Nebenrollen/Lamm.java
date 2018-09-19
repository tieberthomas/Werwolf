package root.Persona.Rollen.Nebenrollen;

import root.Persona.Fraktionen.Bürger;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Lamm extends Nebenrolle {
    public static final String NAME = "Lamm";
    public static final String IMAGE_PATH = ImagePath.LAMM_KARTE;
    public static boolean spammable = false;
    public NebenrollenType type = new Passiv();

    public Lamm() {
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
        try {
            Spieler spieler = game.findSpielerPerRolle(NAME);
            Nebenrolle nebenrolle;

            if (spieler.hauptrolle.getFraktion().name.equals(Bürger.NAME)) {
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

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public NebenrollenType getType() {
        return type;
    }
}
