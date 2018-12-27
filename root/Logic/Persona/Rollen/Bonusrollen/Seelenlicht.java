package root.Logic.Persona.Rollen.Bonusrollen;

import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

import static root.Logic.Persona.Constants.TitleConstants.NEUE_KARTE_TITLE;

public class Seelenlicht extends Bonusrolle {
    public static final String ID = "ID_Seelenlicht";
    public static final String NAME = "Seelenlicht";
    public static final String IMAGE_PATH = ImagePath.SEELENLICHT_KARTE;
    public static final BonusrollenType TYPE = new Passiv();

    public static final String SETUP_NIGHT_STATEMENT_ID = ID;
    public static final String SETUP_NIGHT_STATEMENT_TITLE = NEUE_KARTE_TITLE;
    public static final String SETUP_NIGHT_STATEMENT_BESCHREIBUNG = "Seelenlicht erwacht und tauscht seine Karte je nach Hauptrolle aus";
    public static final StatementType SETUP_NIGHT_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public Seelenlicht() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.setupNightStatementID = SETUP_NIGHT_STATEMENT_ID;
        this.setupNightStatementTitle = SETUP_NIGHT_STATEMENT_TITLE;
        this.setupNightStatementBeschreibung = SETUP_NIGHT_STATEMENT_BESCHREIBUNG;
        this.setupNightStatementType = SETUP_NIGHT_STATEMENT_TYPE;
    }

    public void tauschen(Bonusrolle bonusrolle) {
        try {
            Spieler spieler = Game.game.findSpielerPerRolle(this.id);
            spieler.bonusrolle = bonusrolle;
        } catch (NullPointerException e) {
            System.out.println(NAME + " nicht gefunden");
        }
    }

    public Bonusrolle getTauschErgebnis() {
        Spieler spieler = Game.game.findSpielerPerRolle(this.id);

        if (spieler != null) {
            Bonusrolle bonusrolle;

            if (spieler.hauptrolle.fraktion.equals(Bürger.ID)) {
                bonusrolle = new ReineSeele();
            } else {
                bonusrolle = new DunklesLicht();
            }

            return bonusrolle;
        } else {
            return new ReineSeele();
        }
    }
}
