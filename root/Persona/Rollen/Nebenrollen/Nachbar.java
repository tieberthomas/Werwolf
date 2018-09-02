package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Phases.Nacht;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenTyp;
import root.Spieler;

import java.util.ArrayList;

public class Nachbar extends Nebenrolle {
    public static String title = "Spieler beobachten";
    public static final String beschreibung = "Nachbar erwacht und entscheidet welchen Spieler er beobachten möchte";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;
    public static final String name = "Nachbar";
    public static final String imagePath = ImagePath.NACHBAR_KARTE;
    public static boolean spammable = true;
    public Spieler beobachteterSpieler = null;
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
        Spieler nachbarSpieler = game.findSpielerPerRolle(Nachbar.name);
        FrontendControl info = new FrontendControl(FrontendControlType.LIST, getBesucherStrings(beobachteterSpieler, nachbarSpieler));
        if (beobachteterSpieler != null) {
            info.title = Nacht.NACHBAR_INFORMATION_TITLE + beobachteterSpieler.name;
        }
        return info;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public StatementType getStatementType() {
        return statementType;
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

    public static ArrayList<String> getBesucherStrings(Spieler beobachteterSpieler, Spieler beobachter) {
        ArrayList<String> besucher = new ArrayList<>();

        if (beobachteterSpieler != null) {
            for (Spieler spieler : game.getLivingPlayer()) {
                if (spieler.hauptrolle.besucht != null && spieler.hauptrolle.besucht.name.equals(beobachteterSpieler.name) ||
                        (spieler.nebenrolle.besucht != null && spieler.nebenrolle.besucht.name.equals(beobachteterSpieler.name))) {
                    besucher.add(spieler.name);
                }

                if (!besucher.contains(spieler.name) && spieler.nebenrolle.getName().equals(Analytiker.name)) {
                    Analytiker analytiker = (Analytiker) spieler.nebenrolle;
                    if (analytiker.besuchtAnalysieren != null && analytiker.besuchtAnalysieren.name.equals(beobachteterSpieler.name)) {
                        besucher.add(spieler.name);
                    }
                }
            }

            if (beobachter != null) {
                besucher.remove(beobachter.name);
            }
        }

        return besucher;
    }
}
