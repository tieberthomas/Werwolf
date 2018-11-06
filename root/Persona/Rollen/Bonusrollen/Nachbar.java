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

public class Nachbar extends Bonusrolle {
    public static final String ID = "Nachbar";
    public static final String NAME = "Nachbar";
    public static final String IMAGE_PATH = ImagePath.NACHBAR_KARTE;
    public static final BonusrollenType TYPE = new Informativ();

    public static final String STATEMENT_ID = "Nachbar";
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Nachbar erwacht, wählt einen Spieler und erfährt wer diesen Spieler besucht hat";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE_INFO;

    public static final String INFO_TITLE = "Besucher von ";

    public Nachbar() {
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
        return game.getSpielerCheckSpammableFrontendControl(this);
    }

    @Override
    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenSpieler = game.findSpieler(chosenOption);

        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            if (showTarnumhang(this, chosenSpieler)) {
                return new FrontendControl(new Tarnumhang_BonusrollenType());
            }

            Spieler nachbarSpieler = game.findSpielerPerRolle(Nachbar.NAME);
            FrontendControl info = new FrontendControl(FrontendControlType.LIST, getBesucherStrings(chosenSpieler, nachbarSpieler));
            info.title = INFO_TITLE + chosenSpieler.name;

            return info;
        }

        return new FrontendControl();
    }

    public static ArrayList<String> getBesucherStrings(Spieler beobachteterSpieler, Spieler beobachter) {
        ArrayList<String> besucher = new ArrayList<>();

        if (beobachteterSpieler != null) {
            for (Spieler spieler : game.getLivingSpieler()) {
                if (spieler.hauptrolle.besucht != null && spieler.hauptrolle.besucht.name.equals(beobachteterSpieler.name) ||
                        (spieler.bonusrolle.besucht != null && spieler.bonusrolle.besucht.name.equals(beobachteterSpieler.name))) {
                    besucher.add(spieler.name);
                }

                if (!besucher.contains(spieler.name) && spieler.bonusrolle.name.equals(Analytiker.NAME)) {
                    Analytiker analytiker = (Analytiker) spieler.bonusrolle;
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
