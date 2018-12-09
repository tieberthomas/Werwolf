package root.Logic.Phases.NightBuilding.Statement;

import root.Logic.Phases.NightBuilding.Constants.StatementType;
import root.Logic.Phases.NightBuilding.Statement.StatementDependency.StatementDependency;

public class ProgramStatement extends Statement {
    public ProgramStatement(String id) {
        this.id = id;
        this.type = StatementType.PROGRAM;
        this.dependency = new StatementDependency();
    }
}
