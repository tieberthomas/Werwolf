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
    public String identifier;
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
        this.identifier = rolle.statementIdentifier;
        this.title = rolle.statementTitle;
        this.beschreibung = rolle.statementBeschreibung;
        this.type = rolle.statementType;
        this.dependency = new StatementDependencyRolle(rolle);

        if (!this.identifier.equals(Konditorlehrling.STATEMENT_IDENTIFIER)) {
            this.sammlerBeschreibung = Sammler.beschreibungAddiditon + this.beschreibung;
        } else {
            this.sammlerBeschreibung = this.beschreibung.replace(Sammler.konditorlehrlingSearchString, Sammler.beschreibungAddiditonLowerCase + Sammler.konditorlehrlingSearchString);
        }
    }

    public Statement(Rolle rolle, Statement dependency) {
        this.identifier = rolle.secondStatementIdentifier;
        this.title = rolle.secondStatementTitle;
        this.beschreibung = rolle.secondStatementBeschreibung;
        this.type = rolle.secondStatementType;
        this.dependency = new StatementDependencyStatement(dependency);
    }

    public Statement(Fraktion fraktion) {
        this.identifier = fraktion.statementIdentifier;
        this.title = fraktion.statementTitle;
        this.beschreibung = fraktion.statementBeschreibung;
        this.type = fraktion.statementType;
        this.dependency = new StatementDependencyFraktion(fraktion);
    }

    public Statement(Fraktion fraktion, Statement dependency) {
        this.identifier = fraktion.secondStatementIdentifier;
        this.title = fraktion.secondStatementTitle;
        this.beschreibung = fraktion.secondStatementBeschreibung;
        this.type = fraktion.secondStatementType;
        this.dependency = new StatementDependencyStatement(dependency);
    }

    public Statement(String identifier) {
        this.identifier = identifier;
        this.type = StatementType.PROGRAMM;
        this.dependency = new StatementDependency();
    }

    public Statement(String identifier, String title, String beschreibung, StatementType type) {
        this.identifier = identifier;
        this.title = title;
        this.beschreibung = beschreibung;
        this.type = type;
        this.dependency = new StatementDependency();
    }

    public Statement(String identifier, String title, String beschreibung, StatementType type, StatementDependency dependency) {
        this.identifier = identifier;
        this.title = title;
        this.beschreibung = beschreibung;
        this.type = type;
        this.dependency = dependency;
    }

    public static Statement newFirstNightStatement(Rolle rolle)
    {
        String identifier = rolle.firstNightStatementIdentifier;
        String title = rolle.firstNightStatementTitle;
        String beschreibung = rolle.firstNightStatementBeschreibung;
        StatementType type = rolle.firstNightStatementType;
        StatementDependency dependency = new StatementDependencyRolle(rolle);

        return new Statement(identifier, title, beschreibung, type, dependency);
    }

    public static Statement newFirstNightStatement(Fraktion fraktion)
    {
        String identifier = fraktion.firstNightStatementIdentifier;
        String title = fraktion.firstNightStatementTitle;
        String beschreibung = fraktion.firstNightStatementBeschreibung;
        StatementType type = fraktion.firstNightStatementType;
        StatementDependency dependency = new StatementDependencyFraktion(fraktion);

        return new Statement(identifier, title, beschreibung, type, dependency);
    }

    public void refreshState() {
        state = dependency.getState();
    }
}
