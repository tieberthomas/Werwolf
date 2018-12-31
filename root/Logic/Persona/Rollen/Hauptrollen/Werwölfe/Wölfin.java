package root.Logic.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Controller.FrontendObject;
import root.Logic.Game;
import root.Logic.KillLogic.NormalKill;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

import static root.Logic.Persona.Constants.TitleConstants.CHOSE_OPFER_TITLE;

public class Wölfin extends Hauptrolle {
    public static final String ID = "ID_Wölfin";
    public static final String NAME = "Wölfin";
    public static final String IMAGE_PATH = ImagePath.WÖLFIN_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = CHOSE_OPFER_TITLE;
    public static final String STATEMENT_BESCHREIBUNG = "Wölfin erwacht und wählt ein Opfer aus";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static boolean stateKilling = false;

    public Wölfin() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = true;
        this.selfuseable = true;
        this.killing = true;
    }

    @Override
    public FrontendObject getFrontendObject() {
        return Game.game.getSpielerFrontendObject(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        stateKilling = false;
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            besucht = chosenSpieler;

            Spieler täter = Game.game.findSpielerPerRolle(this.id);
            NormalKill.execute(chosenSpieler, täter);
        }
    }
}