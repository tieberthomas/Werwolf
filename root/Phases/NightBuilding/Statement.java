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

    public Statement(Rolle rolle) {
        this(rolle.statementIdentifier, rolle.statementTitle, rolle.statementBeschreibung, rolle.statementType, new StatementDependencyRolle(rolle));
    }

    public Statement(Fraktion fraktion) {
        this(fraktion.statementIdentifier, fraktion.statementTitle, fraktion.statementBeschreibung, fraktion.statementType, new StatementDependencyFraktion(fraktion));
    }

    public Statement(String identifier, String title, String beschreibung, StatementType type, StatementDependency dependency) {
        this.identifier = identifier;
        this.title = title;
        this.beschreibung = beschreibung;
        this.type = type;
        this.dependency = dependency;

        if (!this.identifier.equals(Konditorlehrling.STATEMENT_IDENTIFIER)) {
            this.sammlerBeschreibung = Sammler.beschreibungAddiditon + this.beschreibung;
        } else {
            this.sammlerBeschreibung = this.beschreibung.replace(Sammler.konditorlehrlingSearchString, Sammler.beschreibungAddiditonLowerCase + Sammler.konditorlehrlingSearchString);
        }
    }

    public void refreshState() {
        state = dependency.getState();
    }
}
