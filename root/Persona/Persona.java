package root.Persona;

import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.mechanics.Game;

import java.awt.*;

public class Persona {
    public static Game game;

    public String name = "";
    public String imagePath = ImagePath.AUS_DEM_SPIEL;

    public String statementTitle = "";
    public String statementBeschreibung = "";
    public StatementType statementType = StatementType.EMPTY_STATEMENT;

    public String secondStatementTitle = "";
    public String secondStatementBeschreibung = "";
    public StatementType secondStatementType = StatementType.EMPTY_STATEMENT;

    public void processChosenOption(String chosenOption) {
    }

    public Color getFarbe() {
        return Color.WHITE;
    }

    public boolean equals(Persona persona) {
        return persona != null && this.name.equals(persona.name);
    }

    public boolean equals(String personaName) {
        return personaName != null && this.name.equals(personaName);
    }
}
