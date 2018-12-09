package root.Phases.NightBuilding.Constants;

import root.Phases.NightBuilding.IndieStatement;

public class IndieStatements {
    public static final String ALLE_SCHLAFEN_EIN_ID = "Alle_schlafen_ein";
    public static final String ALLE_SCHLAFEN_EIN_TITLE = "Alle schlafen ein";
    public static final String ALLE_SCHLAFEN_EIN_BESCHREIBUNG = "Alle schlafen ein";
    public static final StatementType ALLE_SCHLAFEN_EIN_TYPE = StatementType.SHOW_TITLE;

    public static final String ALLE_WACHEN_AUF_ID = "Alle_wachen_auf";
    public static final String ALLE_WACHEN_AUF_TITLE = "Alle wachen auf";
    public static final String ALLE_WACHEN_AUF_BESCHREIBUNG = "Alle wachen auf";
    public static final StatementType ALLE_WACHEN_AUF_TYPE = StatementType.SHOW_TITLE;

    public static final String LIEBESPAAR_ID = "Liebespaar";
    public static final String LIEBESPAAR_TITLE = "Liebespaar wählen";
    public static final String LIEBESPAAR_BESCHREIBUNG = "Der Erzähler wählt ein Liebespaar aus";
    public static final StatementType LIEBESPAAR_TYPE = StatementType.INDIE;

    public static final String LIEBESPAAR_FINDEN_ID = "Liebespaar_finden";
    public static final String LIEBESPAAR_FINDEN_TITLE = "Liebespaar";
    public static final String LIEBESPAAR_FINDEN_BESCHREIBUNG = "Das Liebespaar erwacht, findet und verliebt sich";
    public static final StatementType LIEBESPAAR_FINDEN_TYPE = StatementType.INDIE;

    public static final String OPFER_ID = "Opfer_der_Nacht";
    public static final String OPFER_TITLE = "Opfer der Nacht";
    public static final String OPFER_BESCHREIBUNG = "Alle Opfer inklusive Liebespaaropfer werden bekannt gegeben";
    public static final StatementType OPFER_TYPE = StatementType.INDIE;

    public static IndieStatement getAlleSchlafenEinStatement() {
        return new IndieStatement(ALLE_SCHLAFEN_EIN_ID, ALLE_SCHLAFEN_EIN_TITLE, ALLE_SCHLAFEN_EIN_BESCHREIBUNG, ALLE_SCHLAFEN_EIN_TYPE);
    }

    public static IndieStatement getAlleWachenAufStatement() {
        return new IndieStatement(ALLE_WACHEN_AUF_ID, ALLE_WACHEN_AUF_TITLE, ALLE_WACHEN_AUF_BESCHREIBUNG, ALLE_WACHEN_AUF_TYPE);
    }

    public static IndieStatement getLiebespaarStatement() {
        return new IndieStatement(LIEBESPAAR_ID, LIEBESPAAR_TITLE, LIEBESPAAR_BESCHREIBUNG, LIEBESPAAR_TYPE);
    }

    public static IndieStatement getLiebespaarFindenStatement() {
        return new IndieStatement(LIEBESPAAR_FINDEN_ID, LIEBESPAAR_FINDEN_TITLE, LIEBESPAAR_FINDEN_BESCHREIBUNG, LIEBESPAAR_FINDEN_TYPE);
    }

    public static IndieStatement getOpferStatement() {
        return new IndieStatement(OPFER_ID, OPFER_TITLE, OPFER_BESCHREIBUNG, OPFER_TYPE);
    }
}
