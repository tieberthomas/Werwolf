package root.Logic.Phases.NightBuilding.Statement;

import root.Logic.Phases.NightBuilding.Constants.StatementType;
import root.Logic.Phases.NightBuilding.Statement.StatementDependency.StatementDependency;

public class IndieStatement extends Statement {
    public IndieStatement(String id, String title, String beschreibung, StatementType type) {
        this.id = id;
        this.title = title;
        this.beschreibung = beschreibung;
        this.type = type;
        this.dependency = new StatementDependency();
    }
}
