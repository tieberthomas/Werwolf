package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.NebenrollenType.Informativ;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Spurenleser extends Bonusrolle {
    public static final String STATEMENT_TITLE = "Spuren lesen von";
    public static final String STATEMENT_BESCHREIBUNG = "Spurenleser erwacht, wählt einen Mitspieler und erfährt wen dieser Spieler besucht hat";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE_INFO;
    public static final String INFO_TITLE = "Besuchte Spieler von ";

    public static final String NAME = "Spurenleser";
    public static final String IMAGE_PATH = ImagePath.SPURENLESER_KARTE;
    public static final NebenrollenType TYPE = new Informativ();

    public Spurenleser() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = true;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getMitspielerCheckSpammableFrontendControl(this);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);

        if (chosenPlayer != null) {
            besucht = chosenPlayer;

            if (showTarnumhang(this, chosenPlayer)) {
                return new FrontendControl(new Tarnumhang_NebenrollenType());
            }

            FrontendControl info = new FrontendControl(FrontendControlType.LIST, getBesuchteSpielerStrings(chosenPlayer));
            info.title = INFO_TITLE + chosenPlayer.name;

            return info;
        }

        return new FrontendControl();
    }

    public static ArrayList<String> getBesuchteSpielerStrings(Spieler beobachteterSpieler) {
        ArrayList<String> besucher = new ArrayList<>();

        if (beobachteterSpieler != null) {
            if (beobachteterSpieler.hauptrolle.besucht != null) {
                String besuchterSpielerDerHauptrolle = beobachteterSpieler.hauptrolle.besucht.name;
                besucher.add(besuchterSpielerDerHauptrolle);
            }

            if (beobachteterSpieler.bonusrolle.besucht != null) {
                String besuchterSpielerDerNebenrolle = beobachteterSpieler.bonusrolle.besucht.name;
                besucher.add(besuchterSpielerDerNebenrolle);
            }

            if (beobachteterSpieler.bonusrolle.name.equals(Analytiker.NAME)) {
                Analytiker analytiker = (Analytiker) beobachteterSpieler.bonusrolle;
                besucher.add(analytiker.besuchtAnalysieren.name);
            }
        }

        return besucher;
    }
}