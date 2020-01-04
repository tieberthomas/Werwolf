package root.Logic.Persona;

import root.Controller.FrontendObject.FrontendObject;
import root.Logic.Phases.Statement.Constants.StatementState;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

import java.awt.*;

public class Persona {
    public String id = "";
    public String name = "";
    public String imagePath = ImagePath.AUS_DEM_SPIEL;

    public Color color = Spieler.ALIVE_BACKGROUND_COLOR;

    public String statementID = "";
    public String statementTitle = "";
    public String statementBeschreibung = "";
    public StatementType statementType = StatementType.EMPTY_STATEMENT;

    public String secondStatementID = "";
    public String secondStatementTitle = "";
    public String secondStatementBeschreibung = "";
    public StatementType secondStatementType = StatementType.EMPTY_STATEMENT;

    public String setupNightStatementID = "";
    public String setupNightStatementTitle = "";
    public String setupNightStatementBeschreibung = "";
    public StatementType setupNightStatementType = StatementType.EMPTY_STATEMENT;

    public String dynamicStatementID = "";
    public String dynamicStatementTitle = "";
    public String dynamicStatementBeschreibung = "";
    public StatementType dynamicStatementType = StatementType.EMPTY_STATEMENT;

    public StatementState getState() {
        return StatementState.NORMAL;
    }

    public StatementState getDynamicState() {
        return StatementState.NORMAL;
    }

    public FrontendObject getFrontendObject() {
        return new FrontendObject();
    }

    public void processChosenOption(String chosenOption) {
    }

    public FrontendObject processChosenOptionGetInfo(String chosenOption) {
        return new FrontendObject();
    }

    public FrontendObject getInfo() {
        return new FrontendObject();
    }

    public Color getColor() {
        return color;
    }

    public boolean equals(Persona persona) {
        return persona != null && this.id.equals(persona.id);
    }

    public boolean equals(String personaID) {
        return this.id.equals(personaID);
    }

    public void beginNight() {
    }

    public void cleanUpAfterNight() {
    }
}
