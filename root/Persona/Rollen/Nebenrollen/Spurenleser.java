package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Persona.Rollen.Constants.NebenrollenType.Informativ;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Spurenleser extends Nebenrolle {
    public static String title = "Spuren lesen von";
    public static final String beschreibung = "Spurenleser erwacht und entscheidet welchen Spieler er beobachten möchte";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;

    public static String secondTitle = "Besuchte Spieler von ";
    public static final String SPURENLESER_INFORMATION = "Spurenleser erwacht und erfährt wen der gewählte Spieler besucht hat";
    public static final String secondBeschreibung = SPURENLESER_INFORMATION;
    public static StatementType secondStatementType = StatementType.ROLLE_INFO;

    public static final String name = "Spurenleser";
    public static final String imagePath = ImagePath.SPURENLESER_KARTE;
    public static boolean spammable = true;
    private Spieler beobachteterSpieler = null;
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
        FrontendControl info = new FrontendControl(FrontendControlType.LIST, getBesuchteSpielerStrings(beobachteterSpieler));
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