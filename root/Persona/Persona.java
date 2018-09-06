package root.Persona;

import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.mechanics.Game;

import java.awt.*;

public class Persona {
    public static Game game;

    public String getName() {
        return "";
    }

    public String getTitle() { return ""; }

    public String getBeschreibung() { return ""; }

    public StatementType getStatementType() { return StatementType.EMPTY_STATEMENT; }

    public String getSecondTitle() { return ""; }

    public String getSecondBeschreibung() { return ""; }

    public StatementType getSecondStatementType() { return StatementType.EMPTY_STATEMENT; }

    public void processChosenOption(String chosenOption) {
    }

    public String getImagePath() {
        return ImagePath.DEAKTIVIERT;
    }

    public Color getFarbe() { return Color.WHITE; }

    public boolean equals(Persona persona) {
        return persona!=null && this.getName().equals(persona.getName());
    }

    public boolean equals(String personaName) {
        return personaName!=null && this.getName().equals(personaName);
    }
}
