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
    public static boolean spammable = true;
    public Spieler bewachterSpieler = null;
    public String type = Nebenrolle.INFORMATIV;

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);
        besucht = chosenPlayer;
        bewachterSpieler = chosenPlayer;
        if(chosenPlayer!=null) {
            chosenPlayer.geschützt = true;
        }
    }

    @Override
    public FrontendControl getInfo() {
        Spieler wachhundSpieler = game.findSpielerPerRolle(Wachhund.name);
        FrontendControl info = new FrontendControl(FrontendControl.LIST, Nachbar.getBesucherStrings(bewachterSpieler, wachhundSpieler));
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
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public String getType() { return type; }
}
