package root.Phases.NightBuilding.Constants;

import root.Phases.NightBuilding.Statement;

public class ProgrammStatements {
    public static final String SCHÜTZE = "Programm_Schütze";
    public static final String OPFER = "Programm_Opfer";
    public static final String TORTE = "Programm_Torte";

    public static Statement getSchützeStatement() {
        return new Statement(SCHÜTZE);
    }

    public static Statement getOferStatement() {
        return new Statement(OPFER);
    }

    public static Statement getTortenProgrammStatement() {
        return new Statement(TORTE);
    }
}
