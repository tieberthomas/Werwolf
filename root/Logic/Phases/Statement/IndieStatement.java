package root.Logic.Phases.Statement;

import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Phases.Statement.StatementDependency.StatementDependency;

public class IndieStatement extends Statement {
    public IndieStatement(String id, String title, String beschreibung, StatementType type) {
        this.id = id;
        this.title = title;
        this.beschreibung = beschreibung;
        this.type = type;
        this.dependency = new StatementDependency();
    }
}
