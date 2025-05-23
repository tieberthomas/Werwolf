package root.Logic.Persona.Rollen.Bonusrollen;

import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.WerwölfeZeigekarte;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

import java.awt.*;

import static root.Logic.Persona.Constants.TitleConstants.NEUE_KARTE_TITLE;

public class Wolfspelz extends Bonusrolle {
    public static final String ID = "ID_Wolfspelz";
    public static final String NAME = "Wolfspelz";
    public static final String IMAGE_PATH = ImagePath.WOLFSPELZ_KARTE;
    public static final BonusrollenType TYPE = new Passiv();
    public static final Color COLOR = Werwölfe.COLOR;

    public static final String SETUP_NIGHT_STATEMENT_ID = ID;
    public static final String SETUP_NIGHT_STATEMENT_TITLE = NEUE_KARTE_TITLE;
    public static final String SETUP_NIGHT_STATEMENT_BESCHREIBUNG = "Träger des Wolfspelzes erwacht und tauscht ggf. seine Karte aus";
    public static final StatementType SETUP_NIGHT_STATEMENT_TYPE = StatementType.PERSONA_SPECAL;

    public boolean schutzAktiv = true;

    public Wolfspelz() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;
        this.color = COLOR;

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

    public Bonusrolle getTauschErgebnis() { //TODO make generic with other swap roles
        Spieler spieler = Game.game.findSpielerPerRolle(this.id);

        if (spieler != null) {
            Bonusrolle bonusrolle;

            if (spieler.hauptrolle.fraktion.equals(Werwölfe.ID)) {
                bonusrolle = new DunklesLicht();
            } else {
                bonusrolle = spieler.bonusrolle;
            }

            return bonusrolle;
        } else {
            return this;
        }
    }

    public Zeigekarte getFraktionInfo() {
        return new WerwölfeZeigekarte();
    }

    public void consumeSchutz() { //TODO make überklasse for wolfspelz and vampirumhang
        schutzAktiv = false;
    }
}
