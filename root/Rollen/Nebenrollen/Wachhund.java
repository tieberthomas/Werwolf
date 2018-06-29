package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.Phases.Nacht;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Nebenrolle;
import root.Spieler;

/**
 * Created by Steve on 12.11.2017.
 */
public class Wachhund extends Nebenrolle
{
    public static final String name = "Wachhund";
    public static final String imagePath = ResourcePath.WACHHUND_KARTE;
    public static boolean unique = true;
    public static boolean spammable = true;
    public Spieler bewachterSpieler = null;

    @Override
    public FrontendControl getDropdownOptions() {
        return Spieler.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        besucht = chosenPlayer;
        bewachterSpieler = chosenPlayer;
        if(chosenPlayer!=null) {
            chosenPlayer.geschützt = true;
        }
    }

    @Override
    public FrontendControl getInfo() {
        Spieler wachhundSpieler = Spieler.findSpielerPerRolle(Wachhund.name);
        FrontendControl info = new FrontendControl(FrontendControl.STATIC_LIST, Nachbar.getBesucherStrings(bewachterSpieler, wachhundSpieler));
        if(bewachterSpieler!=null) {
            info.title = Nacht.WACHHUND_INFORMATION_TITLE + bewachterSpieler.name;
        }
        return info;
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
