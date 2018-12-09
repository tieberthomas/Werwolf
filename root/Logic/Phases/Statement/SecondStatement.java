package root.Logic.Phases.Statement;

import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Rolle;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyRolle;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyStatement;

public class SecondStatement extends Statement {
    public SecondStatement(Rolle rolle, Statement dependency) {
        this.id = rolle.secondStatementID;
        this.title = rolle.secondStatementTitle;
        this.beschreibung = rolle.secondStatementBeschreibung;
        this.type = rolle.secondStatementType;
        this.dependency = new StatementDependencyStatement(dependency);
    }

    public SecondStatement(Fraktion fraktion, Statement dependency) {
        this.id = fraktion.secondStatementID;
        this.title = fraktion.secondStatementTitle;
        this.beschreibung = fraktion.secondStatementBeschreibung;
        this.type = fraktion.secondStatementType;
        this.dependency = new StatementDependencyStatement(dependency);
    }

    //TODO schöne konstruktorn lösung finden
    public SecondStatement(Rolle rolle, boolean secondStatement) {
        this.id = rolle.secondStatementID;
        this.title = rolle.secondStatementTitle;
        this.beschreibung = rolle.secondStatementBeschreibung;
        this.type = rolle.secondStatementType;
        this.dependency = new StatementDependencyRolle(rolle);
    }
}
