package root.Phases.NightBuilding;

import root.Phases.NightBuilding.Constants.StatementType;
import root.Phases.NightBuilding.StatementDependancy.StatementDependency;

public class ProgramStatement extends Statement{
    public ProgramStatement(String id) {
        this.id = id;
        this.type = StatementType.PROGRAMM;
        this.dependency = new StatementDependency();
    }
}
