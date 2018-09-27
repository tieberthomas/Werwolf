package root.Persona.Rollen.Nebenrollen;

import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.NebenrollenType.Informativ;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Liebespaar;

import java.util.Objects;

public class Analytiker extends Bonusrolle {
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Analytiker erwacht und wählt zwei Spieler, der Erzähler sagt ihm ob diese in derselben Fraktion sind";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String NAME = "Analytiker";
    public static final String IMAGE_PATH = ImagePath.ANALYTIKER_KARTE;
    public static final NebenrollenType TYPE = new Informativ();
    public static final String GLEICH = "gleich";
    public static final String UNGLEICH = "ungleich";
    public Spieler besuchtAnalysieren = null;

    public Analytiker() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = true;
    }

    public boolean showTarnumhang(Spieler spieler1, Spieler spieler2) {
        return showTarnumhang(this, spieler1) || showTarnumhang(this, spieler2);
    }

    public String analysiere(Spieler spieler1, Spieler spieler2) {
        besucht = spieler1;
        besuchtAnalysieren = spieler2;
        String name1 = spieler1.name;
        String name2 = spieler2.name;

        String fraktion1 = spieler1.hauptrolle.fraktion.name;
        String fraktion2 = spieler2.hauptrolle.fraktion.name;

        if (Objects.equals(fraktion1, fraktion2)) {
            return GLEICH;
        } else {
            Liebespaar liebespaar = game.liebespaar;
            if (liebespaar != null && liebespaar.spieler1 != null) {
                String liebespartner1 = liebespaar.spieler1.name;
                String liebespartner2 = liebespaar.spieler2.name;

                if (Objects.equals(name1, liebespartner1) && Objects.equals(name2, liebespartner2) ||
                        Objects.equals(name2, liebespartner1) && Objects.equals(name1, liebespartner2)) {
                    return GLEICH;
                } else {
                    return UNGLEICH;
                }
            } else {
                return UNGLEICH;
            }
        }
    }
}
