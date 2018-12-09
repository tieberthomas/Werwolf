package root.Logic.Phases.Statement;

import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Phases.Statement.StatementDependency.StatementDependency;

public class ProgramStatement extends Statement {
    public ProgramStatement(String id) {
        this.id = id;
        this.type = StatementType.PROGRAM;
        this.dependency = new StatementDependency();
    }
}
