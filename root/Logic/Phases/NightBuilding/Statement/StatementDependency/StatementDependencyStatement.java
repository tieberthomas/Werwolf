package root.Logic.Phases.NightBuilding.Statement.StatementDependency;

import root.Logic.Phases.NightBuilding.Constants.StatementState;
import root.Logic.Phases.NightBuilding.Statement.Statement;

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