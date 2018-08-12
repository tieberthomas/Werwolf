package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.Phases.Nacht;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Hauptrollen.Bürger.GuteHexe;
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
    public static boolean spammable = true;
    public Spieler beobachteterSpieler = null;
    public String type = Nebenrolle.INFORMATIV;

    @Override
    public FrontendControl getDropdownOptions() {
        return Spieler.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = Spieler.findSpieler(chosenOption);
        besucht = chosenPlayer;
        beobachteterSpieler = chosenPlayer;
    }

    @Override
    public FrontendControl getInfo() {
        Spieler nachbarSpieler = Spieler.findSpielerPerRolle(Nachbar.name);
        FrontendControl info = new FrontendControl(FrontendControl.LIST, getBesucherStrings(beobachteterSpieler, nachbarSpieler));
        if(beobachteterSpieler!=null) {
            info.title = Nacht.NACHBAR_INFORMATION_TITLE + beobachteterSpieler.name;
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

    public static ArrayList<String> getBesucherStrings(Spieler beobachteterSpieler, Spieler beobachter) {
        ArrayList<String> besucher = new ArrayList<>();

        if(beobachteterSpieler!=null) {
            for (Spieler spieler : Spieler.getLivigPlayer()) {
                if (spieler.hauptrolle.besucht != null && spieler.hauptrolle.besucht.name.equals(beobachteterSpieler.name) ||
                        (spieler.nebenrolle.besucht != null && spieler.nebenrolle.besucht.name.equals(beobachteterSpieler.name))) {
                    besucher.add(spieler.name);
                }

                if(!besucher.contains(spieler.name) && spieler.hauptrolle.getName().equals(GuteHexe.name)) {
                    GuteHexe guteHexe = (GuteHexe)spieler.hauptrolle;
                    if(guteHexe.besuchtWiederbeleben!=null && guteHexe.besuchtWiederbeleben.name.equals(beobachteterSpieler.name)) {
                        besucher.add(spieler.name);
                    }
                }

                if(!besucher.contains(spieler.name) && spieler.nebenrolle.getName().equals(Analytiker.name)) {
                    Analytiker analytiker = (Analytiker) spieler.nebenrolle;
                    if(analytiker.besuchtAnalysieren!=null && analytiker.besuchtAnalysieren.name.equals(beobachteterSpieler.name)) {
                        besucher.add(spieler.name);
                    }
                }
            }

            if(beobachter!=null) {
                besucher.remove(beobachter.name);
            }
        }

        return besucher;
    }
}
