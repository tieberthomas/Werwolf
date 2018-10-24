package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Bonusrollen.Tarnumhang;
import root.Persona.Rollen.Constants.WölfinState;
import root.Phases.NightBuilding.Constants.StatementType;
import root.Phases.NormalNight;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Opfer;

public class Wölfin extends Hauptrolle {
    public static final String STATEMENT_IDENTIFIER = "Wölfin";
    public static final String STATEMENT_TITLE = "Opfer wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Wölfin erwacht und wählt ein Opfer aus, wenn sie das tut, erfährt das Dorf ihre Bonusrolle";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String WÖLFIN_BONUSROLLE = "Wölfin_Bonusrolle";
    public static final String SECOND_STATEMENT_TITLE = "Wölfin";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Das Dorf erfährt die Bonusrolle der Wölfin";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.ROLLE_INFO;

    public static final String NAME = "Wölfin";
    public static final String IMAGE_PATH = ImagePath.WÖLFIN_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();
    public static WölfinState state = WölfinState.WARTEND;

    public Wölfin() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementIdentifier = STATEMENT_IDENTIFIER;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementIdentifier = WÖLFIN_BONUSROLLE;
        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;

        this.killing = true;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        return game.getSpielerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = game.findSpieler(chosenOption);
        state = WölfinState.FERTIG;
        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            Spieler täter = game.findSpielerPerRolle(NAME);
            Opfer.addOpfer(chosenSpieler, täter);
        }
    }

    @Override
    public FrontendControl getInfo() {
        if (NormalNight.wölfinKilled) {
            Spieler wölfinSpieler = NormalNight.wölfinSpieler;
            if (wölfinSpieler != null) {
                String imagePath = wölfinSpieler.bonusrolle.imagePath;
                if (wölfinSpieler.bonusrolle.name.equals(Tarnumhang.NAME)) {
                    imagePath = ImagePath.TARNUMHANG;
                }
                return new FrontendControl(FrontendControlType.IMAGE, imagePath);
            }
        }

        return new FrontendControl();
    }
}