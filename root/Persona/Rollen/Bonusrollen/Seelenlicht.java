package root.Persona.Rollen.Bonusrollen;

import root.Persona.Bonusrolle;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Seelenlicht extends Bonusrolle {
    public static final String FIRST_NIGHT_STATEMENT_ID = "Seelenlicht";
    public static final String FIRST_NIGHT_STATEMENT_TITLE = "Neue Karte";
    public static final String FIRST_NIGHT_STATEMENT_BESCHREIBUNG = "Seelenlicht erwacht und tauscht seine Karte je nach Hauptrolle aus";
    public static final StatementType FIRST_NIGHT_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String NAME = "Seelenlicht";
    public static final String IMAGE_PATH = ImagePath.SEELENLICHT_KARTE;
    public static final BonusrollenType TYPE = new Passiv();

    public Seelenlicht() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.firstNightStatementID = FIRST_NIGHT_STATEMENT_ID;
        this.firstNightStatementTitle = FIRST_NIGHT_STATEMENT_TITLE;
        this.firstNightStatementBeschreibung = FIRST_NIGHT_STATEMENT_BESCHREIBUNG;
        this.firstNightStatementType = FIRST_NIGHT_STATEMENT_TYPE;
    }

    public void tauschen(Bonusrolle bonusrolle) {
        try {
            Spieler spieler = game.findSpielerPerRolle(NAME);
            spieler.bonusrolle = bonusrolle;
        } catch (NullPointerException e) {
            System.out.println(NAME + " nicht gefunden");
        }
    }

    public Bonusrolle getTauschErgebnis() {
        Spieler spieler = game.findSpielerPerRolle(NAME);

        if (spieler != null) {
            Bonusrolle bonusrolle;

            if (spieler.hauptrolle.fraktion.name.equals(Bürger.NAME)) {
                bonusrolle = new ReineSeele();
            } else {
                bonusrolle = new SchwarzeSeele();
            }

            return bonusrolle;
        } else {
            return new ReineSeele();
        }
    }
}
