package root.Phases.NightBuilding;

import root.Persona.Fraktion;
import root.Persona.Rolle;
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyFraktion;
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyRolle;

public class SetupNightStatement extends Statement{
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
