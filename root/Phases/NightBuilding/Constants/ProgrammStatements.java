package root.Phases.NightBuilding.Constants;

import root.Phases.NightBuilding.Statement;
import root.Phases.NightBuilding.StatementProgramm;

public class ProgrammStatements {
    public static final String SCHÜTZE = "[Programm] Schütze";
    public static final String OPFER = "[Programm] Opfer";
    public static final String TORTE = "[Programm] Torte";
    public static final String WAHRSAGER = "[Programm] Wahrsager";

    public static Statement getSchützeStatement() {
        return new StatementProgramm(SCHÜTZE);
    }

    public static Statement getOferStatement() {
        return new StatementProgramm(OPFER);
    }

    public static Statement getTortenProgrammStatement() {
        return new StatementProgramm(TORTE);
    }

    public static Statement getWahrsagerProgrammStatement() {
        return new StatementProgramm(WAHRSAGER);
    }
}
