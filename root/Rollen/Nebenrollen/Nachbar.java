package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
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
    public static boolean unique = true;
    public static boolean spammable = true;
    public Spieler beobachteterSpieler = null;

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
        return new FrontendControl(FrontendControl.STATIC_LIST, getBesucherStrings());
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

    public ArrayList<String> getBesucherStrings() {
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

            Spieler nachbarSpieler = Spieler.findSpielerPerRolle(Nachbar.name);
            besucher.remove(nachbarSpieler.name);
        }

        return besucher;
    }
}
