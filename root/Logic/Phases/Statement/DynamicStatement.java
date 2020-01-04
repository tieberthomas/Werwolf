package root.Logic.Phases.Statement;

import root.Logic.Persona.Persona;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyDynamic;

public class DynamicStatement extends Statement {
    public DynamicStatement(Persona persona) {
        this.id = persona.dynamicStatementID;
        this.title = persona.dynamicStatementTitle;
        this.beschreibung = persona.dynamicStatementBeschreibung;
        this.type = persona.dynamicStatementType;
        this.dependency = new StatementDependencyDynamic(persona);
    }
}
