package root.Persona;

import root.Frontend.FrontendControl;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Game;

import java.awt.*;

public class Persona {
    public static Game game;

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

    public String firstNightStatementID = "";
    public String firstNightStatementTitle = "";
    public String firstNightStatementBeschreibung = "";
    public StatementType firstNightStatementType = StatementType.EMPTY_STATEMENT;

    public FrontendControl getDropdownOptionsFrontendControl() {
        return new FrontendControl();
    }

    public void processChosenOption(String chosenOption) {
    }

    public FrontendControl processChosenOptionGetInfo(String chosenOption) {
        return new FrontendControl();
    }

    public FrontendControl getInfo() {
        return new FrontendControl();
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
}
