package root.Rollen.Nebenrollen;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Nebenrolle;
import root.Spieler;

import java.awt.*;

/**
 * Created by Steve on 12.11.2017.
 */
public class Vampirumhang extends Nebenrolle
{
    public static final String name = "Vampirumhang";
    public static final String imagePath = ResourcePath.VAMPIRUMHANG_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;
    public String type = Nebenrolle.PASSIV;
    public Color farbe = Vampire.farbe;

    public void tauschen(Nebenrolle nebenrolle) {
        try {
            Spieler spieler = Spieler.findSpielerPerRolle(name);
            spieler.nebenrolle = nebenrolle;
        }catch (NullPointerException e) {
            System.out.println(name + " nicht gefunden");
        }
    }

    public Nebenrolle getTauschErgebnis() {
        Spieler spieler = Spieler.findSpielerPerRolle(name);

        if(spieler!=null) {
            Nebenrolle nebenrolle;

            if (spieler.hauptrolle.getFraktion().getName().equals(Vampire.name)) {
                nebenrolle = new SchwarzeSeele();
            } else {
                nebenrolle = spieler.nebenrolle;
            }

            return nebenrolle;
        } else {
            return  this;
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
    public String getType() { return type; }

    @Override
    public Color getFarbe() {
        return farbe;
    }
}
