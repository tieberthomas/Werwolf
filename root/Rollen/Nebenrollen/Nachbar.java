package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Nebenrolle;
import root.Spieler;

import java.util.ArrayList;

/**
 * Created by Steve on 12.11.2017.
 */
public class Nachbar extends Nebenrolle
{
    public static final String name = "Nachbar";
    public static final String imagePath = ResourcePath.NACHBAR_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;
    public Spieler beobachteterSpieler = null;

    @Override
    public String aktion(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        besucht = chosenPlayer;
        beobachteterSpieler = chosenPlayer;

        return null;
    }

    public ArrayList<String> getBesucherStrings() {
        ArrayList<String> besucher = new ArrayList<>();

        if(beobachteterSpieler!=null) {

            for (Spieler spieler : Spieler.getLivigPlayer()) {
                if (spieler.hauptrolle.besucht != null && spieler.hauptrolle.besucht.name.equals(beobachteterSpieler.name) ||
                        (spieler.nebenrolle.besucht != null && spieler.nebenrolle.besucht.name.equals(beobachteterSpieler.name))) {
                    besucher.add(spieler.name);
                }
            }

            Spieler nachbarSpieler = Spieler.findSpielerPerRolle(Nachbar.name);
            besucher.remove(nachbarSpieler.name);
        }

        return besucher;
    }

    @Override
    public FrontendControl getDropdownOtions() {
        return Spieler.getPlayerCheckSpammableFrontendControl(this);
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
    public boolean isUnique() {
        return unique;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

}
