package root.Logic.Phases.Statement;

import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Rolle;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyFraktion;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyRolle;

public class SetupNightStatement extends Statement {
    public SetupNightStatement(Rolle rolle) {
        this.id = rolle.setupNightStatementID;
        this.title = rolle.setupNightStatementTitle;
        this.beschreibung = rolle.setupNightStatementBeschreibung;
        this.type = rolle.setupNightStatementType;
        this.dependency = new StatementDependencyRolle(rolle);
    }

    public SetupNightStatement(Fraktion fraktion) {
        this.id = fraktion.setupNightStatementID;
        this.title = fraktion.setupNightStatementTitle;
        this.beschreibung = fraktion.setupNightStatementBeschreibung;
        this.type = fraktion.setupNightStatementType;
        this.dependency = new StatementDependencyFraktion(fraktion);
    }
}
