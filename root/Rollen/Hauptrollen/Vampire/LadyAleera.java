package root.Rollen.Hauptrollen.Vampire;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ImagePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrollen.Vampirumhang;
import root.Spieler;

import java.util.ArrayList;

public class LadyAleera extends Hauptrolle {
    public static final String name = "Lady Aleera";
    public static Fraktion fraktion = new Vampire();
    public static final String imagePath = ImagePath.LADY_ALEERA_KARTE;
    public static boolean spammable = false;
    public static boolean killing = true;

    @Override
    public FrontendControl getInfo() {
        return new FrontendControl(FrontendControl.LIST, findGeschützeSpieler());
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
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public boolean isKilling() {
        return killing;
    }

    public ArrayList<String> findGeschützeSpieler() {
        ArrayList<String> geschützte = new ArrayList<>();

        for (Spieler currentSpieler : game.spieler) {
            String nebenrolleCurrentSpieler = currentSpieler.nebenrolle.getName();

            if ((currentSpieler.geschützt || nebenrolleCurrentSpieler.equals(Vampirumhang.name)) && currentSpieler.lebend) {
                geschützte.add(currentSpieler.name);
            }
        }

        return geschützte;
    }
}