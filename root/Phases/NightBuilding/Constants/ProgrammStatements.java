package root.Phases.NightBuilding.Constants;

import root.Phases.NightBuilding.Statement;
import root.Phases.NightBuilding.StatementProgramm;

public class ProgrammStatements {
    public static final String SCHÜTZE = "Programm_Schütze";
    public static final String OPFER = "Programm_Opfer";
    public static final String TORTE = "Programm_Torte";

    public static Statement getSchützeStatement() {
        return new StatementProgramm(SCHÜTZE);
    }

    public static Statement getOferStatement() {
        return new StatementProgramm(OPFER);
    }

    public static Statement getTortenProgrammStatement() {
        return new StatementProgramm(TORTE);
    }
}
