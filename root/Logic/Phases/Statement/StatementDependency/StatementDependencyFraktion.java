package root.Logic.Phases.Statement.StatementDependency;

import root.Logic.Persona.Fraktion;
import root.Logic.Phases.Statement.Constants.StatementState;

public class StatementDependencyFraktion extends StatementDependency {
    public Fraktion fraktion;

    public StatementDependencyFraktion(Fraktion fraktion) {
        this.fraktion = fraktion;
    }

    @Override
    public StatementState getState() {
        return Fraktion.getState(fraktion);
    }
}
