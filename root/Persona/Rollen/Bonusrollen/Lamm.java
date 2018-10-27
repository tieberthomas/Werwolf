package root.Persona.Rollen.Bonusrollen;

import root.Persona.Bonusrolle;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.awt.*;

public class Lamm extends Bonusrolle {
    public static final String FIRST_NIGHT_STATEMENT_ID = "Lamm";
    public static final String FIRST_NIGHT_STATEMENT_TITLE = "Neue Karte";
    public static final String FIRST_NIGHT_STATEMENT_BESCHREIBUNG = "Lamm erwacht und tauscht ggf. seine Karte aus";
    public static final StatementType FIRST_NIGHT_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String NAME = "Lamm";
    public static final String IMAGE_PATH = ImagePath.LAMM_KARTE;
    public static final BonusrollenType TYPE = new Passiv();
    public static final Color COLOR = Bürger.COLOR;

    public Lamm() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.color = COLOR;

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
        try {
            Spieler spieler = game.findSpielerPerRolle(NAME);
            Bonusrolle bonusrolle;

            if (spieler.hauptrolle.fraktion.name.equals(Bürger.NAME)) {
                bonusrolle = new ReineSeele();
            } else {
                bonusrolle = spieler.bonusrolle;
            }

            return bonusrolle;
        } catch (NullPointerException e) {
            System.out.println(NAME + " nicht gefunden");
        }

        return this;
    }

    public Zeigekarte getFraktionInfo() {
        return new Bürger().getZeigeKarte();
    }
}
