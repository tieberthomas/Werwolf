package root.Logic.Persona.Rollen.Hauptrollen.Bürger;

import root.Controller.FrontendObject.FrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

public class Schamanin extends Hauptrolle {
    public static final String ID = "ID_Schamanin";
    public static final String NAME = "Schamanin";
    public static final String IMAGE_PATH = ImagePath.SCHAMANIN_KARTE;
    public static final Fraktion FRAKTION = new Bürger();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Mitspieler schützen";
    public static final String STATEMENT_BESCHREIBUNG = "Schamanin erwacht und entscheidet ob sie einen Spieler schützen möchte";
    public static final StatementType STATEMENT_TYPE = StatementType.PERSONA_CHOOSE_ONE;

    public Schamanin() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = false;
        this.selfuseable = true;
        this.killing = true;
    }

    @Override
    public FrontendObject getFrontendObject() {
        return Game.game.getSpielerFrontendObject(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            besucht = chosenSpieler;
            chosenSpieler.geschützt = true;
            abilityCharges--;
        }
    }
}