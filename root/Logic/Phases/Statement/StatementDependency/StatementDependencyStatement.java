package root.Logic.Phases.Statement.StatementDependency;

import root.Logic.Phases.Statement.Constants.StatementState;
import root.Logic.Phases.Statement.Statement;

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