package root.Phases.NightBuilding;

import root.Persona.Fraktion;
import root.Persona.Rolle;
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyFraktion;
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyRolle;

public class FirstNightStatement extends Statement{
    public FirstNightStatement(Rolle rolle) {
        this.id = rolle.firstNightStatementID;
        this.title = rolle.firstNightStatementTitle;
        this.beschreibung = rolle.firstNightStatementBeschreibung;
        this.type = rolle.firstNightStatementType;
        this.dependency = new StatementDependencyRolle(rolle);
    }

    public FirstNightStatement(Fraktion fraktion) {
        this.id = fraktion.firstNightStatementID;
        this.title = fraktion.firstNightStatementTitle;
        this.beschreibung = fraktion.firstNightStatementBeschreibung;
        this.type = fraktion.firstNightStatementType;
        this.dependency = new StatementDependencyFraktion(fraktion);
    }
}
