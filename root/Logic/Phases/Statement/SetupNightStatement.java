package root.Logic.Phases.Statement;

import root.Logic.Persona.Persona;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyPersona;

public class SetupNightStatement extends Statement {
    public SetupNightStatement(Persona persona) {
        this.id = persona.setupNightStatementID;
        this.title = persona.setupNightStatementTitle;
        this.beschreibung = persona.setupNightStatementBeschreibung;
        this.type = persona.setupNightStatementType;
        this.dependency = new StatementDependencyPersona(persona);
    }
}
