package root.Logic.Phases.Statement;

import root.Logic.Persona.Persona;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyPersona;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyStatement;

public class SecondStatement extends Statement {
    public SecondStatement(Persona persona) {
        this.id = persona.secondStatementID;
        this.title = persona.secondStatementTitle;
        this.beschreibung = persona.secondStatementBeschreibung;
        this.type = persona.secondStatementType;
        this.dependency = new StatementDependencyPersona(persona);
    }

    public SecondStatement(Persona persona, Statement dependency) {
        this.id = persona.secondStatementID;
        this.title = persona.secondStatementTitle;
        this.beschreibung = persona.secondStatementBeschreibung;
        this.type = persona.secondStatementType;
        this.dependency = new StatementDependencyStatement(dependency);
    }
}
