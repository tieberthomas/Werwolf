package root.Phases.NightBuilding;

import root.Phases.NightBuilding.Constants.StatementType;
import root.Phases.NightBuilding.StatementDependancy.StatementDependency;

public class ProgramStatement extends Statement{
    public ProgramStatement(String id) {
        this.id = id;
        this.type = StatementType.PROGRAM;
        this.dependency = new StatementDependency();
    }
}
