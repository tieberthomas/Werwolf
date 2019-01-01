package root.Logic.Persona.Rollen.Bonusrollen;

import root.Controller.FrontendObject.FrontendObjectType;
import root.Controller.FrontendObject.FrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.List;

public class Spurenleser extends Bonusrolle {
    public static final String ID = "ID_Spurenleser";
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

        this.selfuseable = true;
        this.spammable = true;
    }

    @Override
    public FrontendObject getFrontendObject() {
        return Game.game.getSpielerFrontendObject(this);
    }

    @Override
    public FrontendObject processChosenOptionGetInfo(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);

        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            if (showTarnumhang(this, chosenSpieler)) {
                return new FrontendObject(new Tarnumhang_BonusrollenType());
            }

            FrontendObject info = new FrontendObject(FrontendObjectType.LIST, getBesuchteSpielerStrings(chosenSpieler));
            info.title = INFO_TITLE + chosenSpieler.name;

            return info;
        }

        return new FrontendObject();
    }

    private static List<String> getBesuchteSpielerStrings(Spieler beobachteterSpieler) {
        List<String> besucher = new ArrayList<>();

        if (beobachteterSpieler != null) {
            if (beobachteterSpieler.hauptrolle.besucht != null) {
                String besuchterSpielerDerHauptrolle = beobachteterSpieler.hauptrolle.besucht.name;
                besucher.add(besuchterSpielerDerHauptrolle);
            }

            if (beobachteterSpieler.bonusrolle.besucht != null) {
                String besuchterSpielerDerBonusrolle = beobachteterSpieler.bonusrolle.besucht.name;
                besucher.add(besuchterSpielerDerBonusrolle);
            }

            if (beobachteterSpieler.bonusrolle.equals(Analytiker.ID)) {
                Analytiker analytiker = (Analytiker) beobachteterSpieler.bonusrolle;
                if (analytiker.besuchtAnalysieren != null) {
                    besucher.add(analytiker.besuchtAnalysieren.name);
                }
            }
        }

        return besucher;
    }
}