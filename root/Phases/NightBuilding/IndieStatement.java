package root.Phases.NightBuilding;

import root.Phases.NightBuilding.Constants.StatementType;
import root.Phases.NightBuilding.StatementDependancy.StatementDependency;

public class IndieStatement extends Statement {
    public IndieStatement(String id, String title, String beschreibung, StatementType type) {
        this.id = id;
        this.title = title;
        this.beschreibung = beschreibung;
        this.type = type;
        this.dependency = new StatementDependency();
    }
}
