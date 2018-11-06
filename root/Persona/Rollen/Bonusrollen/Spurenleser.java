package root.Persona.Rollen.Bonusrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Spurenleser extends Bonusrolle {
    public static final String ID = "Spurenleser";
    public static final String NAME = "Spurenleser";
    public static final String IMAGE_PATH = ImagePath.SPURENLESER_KARTE;
    public static final BonusrollenType TYPE = new Informativ();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Spuren lesen von";
    public static final String STATEMENT_BESCHREIBUNG = "Spurenleser erwacht, wählt einen Mitspieler und erfährt wen dieser Spieler besucht hat";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public static final String INFO_TITLE = "Besuchte Spieler von ";

    public Spurenleser() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = true;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        return game.getMitspielerCheckSpammableFrontendControl(this);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenSpieler = game.findSpieler(chosenOption);

        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            if (showTarnumhang(this, chosenSpieler)) {
                return new FrontendControl(new Tarnumhang_BonusrollenType());
            }

            FrontendControl info = new FrontendControl(FrontendControlType.LIST, getBesuchteSpielerStrings(chosenSpieler));
            info.title = INFO_TITLE + chosenSpieler.name;

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
                String besuchterSpielerDerBonusrolle = beobachteterSpieler.bonusrolle.besucht.name;
                besucher.add(besuchterSpielerDerBonusrolle);
            }

            if (beobachteterSpieler.bonusrolle.equals(Analytiker.NAME)) {
                Analytiker analytiker = (Analytiker) beobachteterSpieler.bonusrolle;
                besucher.add(analytiker.besuchtAnalysieren.name);
            }
        }

        return besucher;
    }
}