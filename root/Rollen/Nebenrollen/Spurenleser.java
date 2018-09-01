package root.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Phases.Nacht;
import root.ResourceManagement.ImagePath;
import root.Rollen.Nebenrolle;
import root.Rollen.NebenrollenTyp;
import root.Spieler;

import java.util.ArrayList;

public class Spurenleser extends Nebenrolle {
    public static final String name = "Spurenleser";
    public static final String imagePath = ImagePath.SPURENLESER_KARTE;
    public static boolean spammable = true;
    private Spieler beobachteterSpieler = null;
    public NebenrollenTyp type = NebenrollenTyp.INFORMATIV;

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);
        besucht = chosenPlayer;
        beobachteterSpieler = chosenPlayer;
    }

    @Override
    public FrontendControl getInfo() {
        FrontendControl info = new FrontendControl(FrontendControlType.LIST, getBesuchteSpielerStrings(beobachteterSpieler));
        if (beobachteterSpieler != null) {
            info.title = Nacht.SPURENLESER_INFORMATION_TITLE + beobachteterSpieler.name;
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
    public NebenrollenTyp getType() {
        return type;
    }

    public static ArrayList<String> getBesuchteSpielerStrings(Spieler beobachteterSpieler) {
        ArrayList<String> besucher = new ArrayList<>();

        if (beobachteterSpieler != null) {
            if(beobachteterSpieler.hauptrolle.besucht!=null) {
                String besuchterSpielerDerHauptrolle = beobachteterSpieler.hauptrolle.besucht.name;
                besucher.add(besuchterSpielerDerHauptrolle);
            }

            if(beobachteterSpieler.nebenrolle.besucht!=null) {
                String besuchterSpielerDerNebenrolle = beobachteterSpieler.nebenrolle.besucht.name;
                besucher.add(besuchterSpielerDerNebenrolle);
            }

            if(beobachteterSpieler.nebenrolle.getName().equals(Analytiker.name)){
                Analytiker analytiker = (Analytiker) beobachteterSpieler.nebenrolle;
                besucher.add(analytiker.besuchtAnalysieren.name);
            }
        }

        return besucher;
    }
}