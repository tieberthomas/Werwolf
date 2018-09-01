package root.Rollen.Nebenrollen;

import root.ResourceManagement.ImagePath;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Nebenrolle;
import root.Rollen.NebenrollenTyp;
import root.Spieler;

import java.awt.*;

public class Vampirumhang extends Nebenrolle {
    public static final String name = "Vampirumhang";
    public static final String imagePath = ImagePath.VAMPIRUMHANG_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;
    public NebenrollenTyp type = NebenrollenTyp.PASSIV;
    public Color farbe = Vampire.farbe;

    public void tauschen(Nebenrolle nebenrolle) {
        try {
            Spieler spieler = game.findSpielerPerRolle(name);
            spieler.nebenrolle = nebenrolle;
        } catch (NullPointerException e) {
            System.out.println(name + " nicht gefunden");
        }
    }

    public Nebenrolle getTauschErgebnis() {
        Spieler spieler = game.findSpielerPerRolle(name);

        if (spieler != null) {
            Nebenrolle nebenrolle;

            if (spieler.hauptrolle.getFraktion().getName().equals(Vampire.name)) {
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
    public String getName() {
        return name;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public NebenrollenTyp getType() {
        return type;
    }

    @Override
    public Color getFarbe() {
        return farbe;
    }
}
