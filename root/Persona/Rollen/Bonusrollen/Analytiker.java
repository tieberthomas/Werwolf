package root.Persona.Rollen.Bonusrollen;

import root.Persona.Bonusrolle;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Phases.NightBuilding.Constants.StatementType;
import root.Phases.NormalNight;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Liebespaar;

import java.util.Objects;

public class Analytiker extends Bonusrolle {
    public static final String ID = "ID_Analytiker";
    public static final String NAME = "Analytiker";
    public static final String IMAGE_PATH = ImagePath.ANALYTIKER_KARTE;
    public static final BonusrollenType TYPE = new Informativ();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Analytiker erwacht und wählt zwei Spieler, der Erzähler sagt ihm ob diese in derselben Fraktion sind";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String GLEICH = "gleich";
    public static final String UNGLEICH = "ungleich";

    public Spieler besuchtAnalysieren = null;

    public Analytiker() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = true;
    }

    public boolean showTarnumhang(Spieler spieler1, Spieler spieler2) {
        Spieler getarnterSpieler = NormalNight.getarnterSpieler;
        Spieler analytikerSpieler = game.findSpielerPerRolle(name);
        return showTarnumhang(this, spieler1) || showTarnumhang(this, spieler2) ||
                analytikerSpieler.equals(getarnterSpieler) ||
                spieler1.equals(getarnterSpieler) || spieler2.equals(getarnterSpieler);
    }

    public String analysiere(Spieler spieler1, Spieler spieler2) {
        besucht = spieler1;
        besuchtAnalysieren = spieler2;
        String name1 = spieler1.name;
        String name2 = spieler2.name;

        String fraktion1 = spieler1.hauptrolle.fraktion.name;
        String fraktion2 = spieler2.hauptrolle.fraktion.name;

        if (spieler1.hauptrolle.equals(Henker.NAME)) {
            fraktion1 = new Bürger().name;
        }

        if (spieler2.hauptrolle.equals(Henker.NAME)) {
            fraktion2 = new Bürger().name;
        }

        String information;

        if (Objects.equals(fraktion1, fraktion2)) {
            information = GLEICH;
        } else {
            Liebespaar liebespaar = game.liebespaar;
            if (liebespaar != null && liebespaar.spieler1 != null) {
                String liebespartner1 = liebespaar.spieler1.name;
                String liebespartner2 = liebespaar.spieler2.name;

                if (Objects.equals(name1, liebespartner1) && Objects.equals(name2, liebespartner2) ||
                        Objects.equals(name2, liebespartner1) && Objects.equals(name1, liebespartner2)) {
                    information = GLEICH;
                } else {
                    information = UNGLEICH;
                }
            } else {
                information = UNGLEICH;
            }
        }

        Spieler analytikerSpieler = game.findSpielerPerRolle(name);

        if (analytikerSpieler != null && (spieler1.equals(NormalNight.gefälschterSpieler) || spieler2.equals(NormalNight.gefälschterSpieler))) {
            return getWrongInformation(information);
        } else {
            return information;
        }
    }

    private String getWrongInformation(String information) {
        if (information == null) {
            return null;
        }

        switch (information) {
            case GLEICH:
                return UNGLEICH;
            case UNGLEICH:
                return GLEICH;
            default:
                System.out.println("Analytiker.getWrongInformation was called with wrong Parameters.");
                return null;
        }
    }
}
