package root.Logic.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Logic.Game;
import root.Logic.KillLogic.NormalKill;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rollen.Constants.WölfinState;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Logic.Phases.NormalNight;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

public class Wölfin extends Hauptrolle {
    public static final String ID = "ID_Wölfin";
    public static final String NAME = "Wölfin";
    public static final String IMAGE_PATH = ImagePath.WÖLFIN_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Opfer wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Wölfin erwacht und wählt ein Opfer aus, wenn sie das tut, erfährt das Dorf ihre Bonusrolle";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String WÖLFIN_BONUSROLLE = "Wölfin_Bonusrolle";
    public static final String SECOND_STATEMENT_TITLE = "Wölfin";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Das Dorf erfährt die Bonusrolle der Wölfin";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.ROLLE_INFO;

    public static WölfinState state = WölfinState.WARTEND;

    public Wölfin() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementID = WÖLFIN_BONUSROLLE;
        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;

        this.selfuseable = true;
        this.killing = true;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        return Game.game.getSpielerFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        state = WölfinState.FERTIG;
        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            Spieler täter = Game.game.findSpielerPerRolle(this.id);
            NormalKill.execute(chosenSpieler, täter);
        }
    }

    @Override
    public FrontendControl getInfo() {
        if (NormalNight.wölfinKilled) {
            Spieler wölfinSpieler = NormalNight.wölfinSpieler;
            if (wölfinSpieler != null) {
                String imagePath = wölfinSpieler.bonusrolle.imagePath;

                if (wölfinSpieler.equals(GrafVladimir.verschleierterSpieler)) {
                    imagePath = ImagePath.AUS_DEM_SPIEL;
                }

                return new FrontendControl(FrontendControlType.IMAGE, imagePath);
            }
        }

        return new FrontendControl();
    }
}