package root.Persona.Rollen.Nebenrollen;

import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.awt.*;

public class Wolfspelz extends Nebenrolle {
    public static final String name = "Wolfspelz";
    public static final String imagePath = ImagePath.WOLFSPELZ_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;
    public NebenrollenType type = new Passiv();
    public Color farbe = Werwölfe.farbe;

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

            if (spieler.hauptrolle.getFraktion().getName().equals(Werwölfe.name)) {
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
    public NebenrollenType getType() {
        return type;
    }

    @Override
    public Color getFarbe() {
        return farbe;
    }
}
