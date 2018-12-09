package root.Phases.NightBuilding;

import root.Persona.Fraktion;
import root.Persona.Rolle;
import root.Persona.Rollen.Bonusrollen.Konditorlehrling;
import root.Persona.Rollen.Hauptrollen.Bürger.Sammler;
import root.Phases.NightBuilding.Constants.StatementState;
import root.Phases.NightBuilding.Constants.StatementType;
import root.Phases.NightBuilding.StatementDependancy.StatementDependency;
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyFraktion;
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyRolle;
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyStatement;

public class Statement {
    public String id;
    public String title;
    public String beschreibung;
    public StatementType type;
    public StatementDependency dependency;

    public String sammlerBeschreibung;

    public StatementState state = StatementState.INVISIBLE_NOT_IN_GAME;
    public boolean alreadyOver = false;

    public Statement() {
        type = StatementType.EMPTY_STATEMENT;
    }

    public Statement(Rolle rolle) {
        this.id = rolle.statementID;
        this.title = rolle.statementTitle;
        this.beschreibung = rolle.statementBeschreibung;
        this.type = rolle.statementType;
        this.dependency = new StatementDependencyRolle(rolle);

        if (!this.id.equals(Konditorlehrling.STATEMENT_ID)) {
            this.sammlerBeschreibung = Sammler.beschreibungAddiditon + this.beschreibung;
        } else {
            this.sammlerBeschreibung = this.beschreibung.replace(Sammler.konditorlehrlingSearchString, Sammler.beschreibungAddiditonLowerCase + Sammler.konditorlehrlingSearchString);
        }
    }

    //TODO schöne konstruktorn lösung finden
    public Statement(Rolle rolle, boolean secondStatement) {
        this.id = rolle.secondStatementID;
        this.title = rolle.secondStatementTitle;
        this.beschreibung = rolle.secondStatementBeschreibung;
        this.type = rolle.secondStatementType;
        this.dependency = new StatementDependencyRolle(rolle);
    }

    public Statement(Rolle rolle, Statement dependency) {
        this.id = rolle.secondStatementID;
        this.title = rolle.secondStatementTitle;
        this.beschreibung = rolle.secondStatementBeschreibung;
        this.type = rolle.secondStatementType;
        this.dependency = new StatementDependencyStatement(dependency);
    }

    public Statement(Fraktion fraktion) {
        this.id = fraktion.statementID;
        this.title = fraktion.statementTitle;
        this.beschreibung = fraktion.statementBeschreibung;
        this.type = fraktion.statementType;
        this.dependency = new StatementDependencyFraktion(fraktion);
    }

    public Statement(Fraktion fraktion, Statement dependency) {
        this.id = fraktion.secondStatementID;
        this.title = fraktion.secondStatementTitle;
        this.beschreibung = fraktion.secondStatementBeschreibung;
        this.type = fraktion.secondStatementType;
        this.dependency = new StatementDependencyStatement(dependency);
    }

    public void refreshState() {
        state = dependency.getState();
    }
}
