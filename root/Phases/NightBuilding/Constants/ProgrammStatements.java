package root.Phases.NightBuilding.Constants;

import root.Phases.NightBuilding.Statement;

public class ProgrammStatements {
    public static final String SCHÜTZE_ID = "Programm_Schütze";
    public static final String TORTE_ID = "Programm_Torte";

    public static Statement getSchützeStatement() {
        return new Statement(SCHÜTZE_ID);
    }

    public static Statement getTortenProgrammStatement() {
        return new Statement(TORTE_ID);
    }
}
