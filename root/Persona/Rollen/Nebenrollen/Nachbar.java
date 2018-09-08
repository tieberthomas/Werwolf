package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.Informativ;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Nachbar extends Nebenrolle {
    public static String title = "Spieler beobachten";
    public static final String beschreibung = "Nachbar erwacht und entscheidet welchen Spieler er beobachten möchte";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;

    public static String secondTitle = "Besucher von ";
    public static final String NACHBAR_INFORMATION = "Nachbar erwacht und erfährt wer die Besucher seines gewählten Spielers waren";
    public static final String secondBeschreibung = NACHBAR_INFORMATION;
    public static StatementType secondStatementType = StatementType.ROLLE_INFO;

    public static final String name = "Nachbar";
    public static final String imagePath = ImagePath.NACHBAR_KARTE;
    public static boolean spammable = true;
    public Spieler beobachteterSpieler = null;
    public NebenrollenType type = new Informativ();

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
        if(showTarnumhang(this, beobachteterSpieler)) {
            return new FrontendControl(new Tarnumhang_NebenrollenType());
        }

        Spieler nachbarSpieler = game.findSpielerPerRolle(Nachbar.name);
        FrontendControl info = new FrontendControl(FrontendControlType.LIST, getBesucherStrings(beobachteterSpieler, nachbarSpieler));
        if (beobachteterSpieler != null) {
            info.title = secondTitle + beobachteterSpieler.name;
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
    public String getSecondTitle() { return secondTitle; }

    @Override
    public String getSecondBeschreibung() { return secondBeschreibung; }

    @Override
    public StatementType getSecondStatementType() { return secondStatementType; }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public NebenrollenType getType() {
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
