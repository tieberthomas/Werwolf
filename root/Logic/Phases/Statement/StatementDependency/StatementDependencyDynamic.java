package root.Logic.Phases.Statement.StatementDependency;

import root.Logic.Persona.Persona;
import root.Logic.Phases.Statement.Constants.StatementState;

public class StatementDependencyDynamic extends StatementDependency {
    public Persona persona;

    public StatementDependencyDynamic(Persona persona) {
        this.persona = persona;
    }

    @Override
    public StatementState getState() {
        return persona.getDynamicState();
    }
}
