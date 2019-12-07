package root.Logic.Persona.Rollen.Bonusrollen;

import root.Controller.FrontendObject.FrontendObject;
import root.Controller.FrontendObject.ImageFrontendObject;
import root.Controller.FrontendObject.ListFrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Tötend;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

public class Späher extends Bonusrolle {
    public static final String ID = "ID_Späher";
    public static final String NAME = "Späher";
    public static final String IMAGE_PATH = ImagePath.SPÄHER_KARTE;
    public static final BonusrollenType TYPE = new Informativ();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Späher erwacht und lässt sich Auskunft über einen Spieler geben";
    public static final StatementType STATEMENT_TYPE = StatementType.PERSONA_CHOOSE_ONE_INFO;

    public static final String INFO_TITLE = " ist";
    public static final String TÖTEND_OR_GESCHÜTZT = "tötend oder geschützt";
    public static final String NEGATIVE_MESSAGE = "weder tötend noch geschützt";

    public Späher() {
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
        Spieler späherSpieler = Game.game.findSpielerPerRolle(this.id);

        if (chosenSpieler != null && späherSpieler != null) {
            besucht = chosenSpieler;

            Zeigekarte info = chosenSpieler.isTötendInfo(späherSpieler);

            if (info instanceof Tarnumhang_BonusrollenType) {
                return new ImageFrontendObject(info);
            }

            if (info instanceof Tötend || isSpielerGeschützt(chosenSpieler)) {
                return new ListFrontendObject(chosenSpieler.name + INFO_TITLE, TÖTEND_OR_GESCHÜTZT);
            } else {
                return new ListFrontendObject(chosenSpieler.name + INFO_TITLE, NEGATIVE_MESSAGE);
            }
        }

        return new FrontendObject();
    }

    private boolean isSpielerGeschützt(Spieler chosenSpieler) {
        //TODO make framework for one-time-Schutz
        if (chosenSpieler.bonusrolle.equals(DunklesLicht.ID) && ((DunklesLicht) chosenSpieler.bonusrolle).schutzAktiv) {
            return true;
        }
        if (chosenSpieler.bonusrolle.equals(Tarnumhang.ID) && ((Tarnumhang) chosenSpieler.bonusrolle).schutzAktiv) {
            return true;
        }
        if (chosenSpieler.bonusrolle.equals(Wolfspelz.ID) && ((Wolfspelz) chosenSpieler.bonusrolle).schutzAktiv) {
            return true;
        }
        if (chosenSpieler.bonusrolle.equals(Vampirumhang.ID) && ((Vampirumhang) chosenSpieler.bonusrolle).schutzAktiv) {
            return true;
        }

        return chosenSpieler.geschützt;
    }
}
