package root.Phases.NightBuilding.Constants;

import root.Phases.NightBuilding.Statement;
import root.Phases.NightBuilding.StatementIndie;

public class IndieStatements {
    public static final String ALLE_SCHLAFEN_EIN_TITLE = "Alle schlafen ein";
    public static final String ALLE_SCHLAFEN_EIN = "Alle schlafen ein";
    public static final StatementType alleSchlafenEinStatementType = StatementType.SHOW_TITLE;

    public static final String ALLE_WACHEN_AUF_TITLE = "Alle wachen auf";
    public static final String ALLE_WACHEN_AUF = "Alle wachen auf";
    public static final StatementType alleWachenAufStatementType = StatementType.SHOW_TITLE;

    public static final String OPFER_TITLE = "Opfer der Nacht";
    public static final String OPFER = "Alle Opfer inklusive Liebespaaropfer werden bekannt gegeben";
    public static final StatementType opferStatementType = StatementType.INDIE;


    public static Statement getAlleSchlafenEinStatement() {
        return new StatementIndie(ALLE_SCHLAFEN_EIN, ALLE_SCHLAFEN_EIN_TITLE, alleSchlafenEinStatementType);
    }

    public static Statement getAlleWachenAufStatement() {
        return new StatementIndie(ALLE_WACHEN_AUF, ALLE_WACHEN_AUF_TITLE, alleWachenAufStatementType);
    }

    public static Statement getOpferStatement() {
        return new StatementIndie(OPFER, OPFER_TITLE, opferStatementType);
    }
}
