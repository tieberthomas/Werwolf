package root.Rollen.Hauptrollen.Vampire;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrollen.Vampirumhang;
import root.Spieler;

import java.util.ArrayList;

/**
 * Created by Steve on 12.11.2017.
 */
public class LadyAleera extends Hauptrolle
{
    public static final String name = "Lady Aleera";
    public static Fraktion fraktion = new Vampire();
    public static final String imagePath = ResourcePath.LADY_ALEERA_KARTE;
    public static boolean unique = true;
    public static boolean spammable = false;
    public static boolean killing = true;

    @Override
    public FrontendControl getInfo() {
        return new FrontendControl(FrontendControl.STATIC_LIST, findGeschützeSpieler());
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean isUnique() {
        return unique;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public boolean isKilling() {
        return killing;
    }

    public ArrayList<String> findGeschützeSpieler(){
        ArrayList<String> geschützte = new ArrayList<>();

        for (Spieler currentSpieler : Spieler.spieler) {
            String nebenrolleCurrentSpieler = currentSpieler.nebenrolle.getName();

            if ((currentSpieler.geschützt || nebenrolleCurrentSpieler.equals(Vampirumhang.name)) && currentSpieler.lebend) {
                geschützte.add(currentSpieler.name);
            }
        }

        return geschützte;
    }
}