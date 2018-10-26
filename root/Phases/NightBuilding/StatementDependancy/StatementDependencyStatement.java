package root.Phases.NightBuilding.StatementDependancy;

import root.Phases.NightBuilding.Constants.StatementState;
import root.Phases.NightBuilding.Statement;

public class StatementDependencyStatement extends StatementDependency {
    public Statement statement;

    public StatementDependencyStatement(Statement statement) {
        this.statement = statement;
    }

    @Override
    public StatementState getState() {
        return statement.state;
    }
}