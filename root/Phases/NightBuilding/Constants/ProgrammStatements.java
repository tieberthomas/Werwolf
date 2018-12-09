package root.Phases.NightBuilding.Constants;

import root.Phases.NightBuilding.ProgramStatement;

public class ProgrammStatements {
    public static final String SCHÜTZE_ID = "Programm_Schütze";
    public static final String TORTE_ID = "Programm_Torte";

    public static ProgramStatement getSchützeStatement() {
        return new ProgramStatement(SCHÜTZE_ID);
    }

    public static ProgramStatement getTortenStatement() {
        return new ProgramStatement(TORTE_ID);
    }
}
