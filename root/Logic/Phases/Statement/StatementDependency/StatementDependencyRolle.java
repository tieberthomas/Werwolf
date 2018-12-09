package root.Logic.Phases.Statement.StatementDependency;

import root.Logic.Persona.Rolle;
import root.Logic.Phases.Statement.Constants.StatementState;

public class StatementDependencyRolle extends StatementDependency {
    public Rolle rolle;

    public StatementDependencyRolle(Rolle rolle) {
        this.rolle = rolle;
    }

    @Override
    public StatementState getState() {
        return Rolle.getState(rolle);
    }
}
