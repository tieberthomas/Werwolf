package root.Persona;

import root.Frontend.FrontendControl;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.mechanics.Game;

import java.awt.*;

public class Persona {
    public static Game game;

    public String name = "";
    public String imagePath = ImagePath.AUS_DEM_SPIEL;

    public Color color = Color.WHITE;

    public String statementIdentifier = "";
    public String statementTitle = "";
    public String statementBeschreibung = "";
    public StatementType statementType = StatementType.EMPTY_STATEMENT;

    public String secondStatementIdentifier = "";
    public String secondStatementTitle = "";
    public String secondStatementBeschreibung = "";
    public StatementType secondStatementType = StatementType.EMPTY_STATEMENT;

    public FrontendControl getDropdownOptions() {
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
        return persona != null && this.name.equals(persona.name);
    }

    public boolean equals(String personaName) {
        return personaName != null && this.name.equals(personaName);
    }
}
