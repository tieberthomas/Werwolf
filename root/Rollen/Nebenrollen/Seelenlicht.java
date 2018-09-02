package root.Rollen.Nebenrollen;

import root.ResourceManagement.ImagePath;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Nebenrolle;
import root.Rollen.Constants.NebenrollenTyp;
import root.Spieler;

/**
 * Created by Steve on 12.11.2017.
 */
public class Seelenlicht extends Nebenrolle
{
    public static final String name = "Seelenlicht";
    public static final String imagePath = ImagePath.SEELENLICHT_KARTE;
    public static boolean spammable = false;
    public NebenrollenTyp type = NebenrollenTyp.PASSIV;

    public void tauschen(Nebenrolle nebenrolle) {
        try {
            Spieler spieler = game.findSpielerPerRolle(name);
            spieler.nebenrolle = nebenrolle;
        }catch (NullPointerException e) {
            System.out.println(name + " nicht gefunden");
        }
    }

    public Nebenrolle getTauschErgebnis() {
        Spieler spieler = game.findSpielerPerRolle(name);

        if(spieler!=null) {
            Nebenrolle nebenrolle;

            if(spieler.hauptrolle.getFraktion().getName().equals(Bürger.name)) {
                nebenrolle = new ReineSeele();
            } else {
                nebenrolle = new SchwarzeSeele();
            }

            return nebenrolle;
        } else {
            return new ReineSeele();
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
    public NebenrollenTyp getType() { return type; }
}
