package root.Logic.Phases.Statement;

import root.Logic.Persona.Persona;
import root.Logic.Phases.Statement.Constants.StatementState;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Phases.Statement.StatementDependency.StatementDependency;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyPersona;

public class Statement {
    public String id;
    public String title;
    public String beschreibung;
    public StatementType type;
    public StatementDependency dependency;

    public StatementState state = StatementState.INVISIBLE_NOT_IN_GAME;
    public boolean alreadyOver = false;

    public Statement() {
        type = StatementType.EMPTY_STATEMENT;
    }

    public Statement(Persona persona) {
        this.id = persona.statementID;
        this.title = persona.statementTitle;
        this.beschreibung = persona.statementBeschreibung;
        this.type = persona.statementType;
        this.dependency = new StatementDependencyPersona(persona);
    }

    public void refreshState() {
        state = dependency.getState();
    }
}
