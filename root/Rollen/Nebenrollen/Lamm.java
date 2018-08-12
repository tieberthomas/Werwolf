package root.Rollen.Nebenrollen;

import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Nebenrolle;
import root.Spieler;

/**
 * Created by Steve on 12.11.2017.
 */
public class Lamm extends Nebenrolle
{
    public static final String name = "Lamm";
    public static final String imagePath = ResourcePath.LAMM_KARTE;
    public static boolean spammable = false;
    public String type = Nebenrolle.PASSIV;

    public void tauschen(Nebenrolle nebenrolle) {
        try {
            Spieler spieler = Spieler.findSpielerPerRolle(name);
            spieler.nebenrolle = nebenrolle;
        }catch (NullPointerException e) {
            System.out.println(name + " nicht gefunden");
        }
    }

    public Nebenrolle getTauschErgebnis() {
        try {
            Spieler spieler = Spieler.findSpielerPerRolle(name);
            Nebenrolle nebenrolle;

            if(spieler.hauptrolle.getFraktion().getName().equals(Bürger.name)) {
                nebenrolle = new ReineSeele();
            } else {
                nebenrolle = spieler.nebenrolle;
            }

            return nebenrolle;
        }catch (NullPointerException e) {
            System.out.println(name + " nicht gefunden");
        }

        return this;
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
}
