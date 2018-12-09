package root.Persona.Rollen.Bonusrollen;

import root.Persona.Bonusrolle;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Persona.Rollen.Constants.Zeigekarten.FraktionsZeigekarten.VampireZeigekarte;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Game;

import java.awt.*;

public class Vampirumhang extends Bonusrolle {
    public static final String ID = "ID_Vampirumhang";
    public static final String NAME = "Vampirumhang";
    public static final String IMAGE_PATH = ImagePath.VAMPIRUMHANG_KARTE;
    public static final BonusrollenType TYPE = new Passiv();
    public static final Color COLOR = Vampire.COLOR;

    public static final String SETUP_NIGHT_STATEMENT_ID = ID;
    public static final String SETUP_NIGHT_STATEMENT_TITLE = "Neue Karte";
    public static final String SETUP_NIGHT_STATEMENT_BESCHREIBUNG = "Träger des Vampirumhangs erwacht und tauscht ggf. seine Karte aus";
    public static final StatementType SETUP_NIGHT_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public Vampirumhang() {
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

    public Bonusrolle getTauschErgebnis() {
        Spieler spieler = Game.game.findSpielerPerRolle(this.id);

        if (spieler != null) {
            Bonusrolle bonusrolle;

            if (spieler.hauptrolle.fraktion.equals(Vampire.ID)) {
                bonusrolle = new SchwarzeSeele();
            } else {
                bonusrolle = spieler.bonusrolle;
            }

            return bonusrolle;
        } else {
            return this;
        }
    }

    public Zeigekarte getFraktionInfo() {
        return new VampireZeigekarte();
    }
}
