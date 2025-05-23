package root.Logic.Phases.Statement.Constants;

import root.Logic.Phases.Statement.ProgramStatement;

public class ProgramStatements {
    public static final String SCHÜTZE_ID = "Program_Schütze";
    public static final String TORTE_ID = "Program_Torte";

    public static ProgramStatement getSchützeStatement() {
        return new ProgramStatement(SCHÜTZE_ID);
    }

    public static ProgramStatement getTortenStatement() {
        return new ProgramStatement(TORTE_ID);
    }
}
