package root.Logic.Persona.Rollen.Bonusrollen;

import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.Liebespaar;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.LadyAleera;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.MissVerona;
import root.Logic.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

import java.util.Objects;

public class Analytiker extends Bonusrolle {
    public static final String ID = "ID_Analytiker";
    public static final String NAME = "Analytiker";
    public static final String IMAGE_PATH = ImagePath.ANALYTIKER_KARTE;
    public static final BonusrollenType TYPE = new Informativ();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Spieler wählen";
    public static final String STATEMENT_BESCHREIBUNG = "Analytiker erwacht und wählt zwei Spieler, der Erzähler sagt ihm ob diese in derselben Fraktion sind";
    public static final StatementType STATEMENT_TYPE = StatementType.PERSONA_SPECAL;

    private static final String GLEICH = "gleich";
    private static final String UNGLEICH = "ungleich";

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

        this.selfuseable = false;
        this.spammable = true;
    }

    public DropdownOptions getDropdownOptions() {
        return Game.game.getSpielerDropdownOptions(this);
    }

    public boolean showTarnumhang(Spieler spieler1, Spieler spieler2) {
        Spieler getarnterSpieler = MissVerona.getarnterSpieler;
        Spieler analytikerSpieler = Game.game.findSpielerPerRolle(this.id);
        return showTarnumhang(this, spieler1) || showTarnumhang(this, spieler2) ||
                analytikerSpieler.equals(getarnterSpieler) ||
                spieler1.equals(getarnterSpieler) || spieler2.equals(getarnterSpieler);
    }

    public String analysiere(Spieler spieler1, Spieler spieler2) {
        besucht = spieler1;
        besuchtAnalysieren = spieler2;
        String name1 = spieler1.name;
        String name2 = spieler2.name;

        String fraktion1 = spieler1.hauptrolle.fraktion.id;
        String fraktion2 = spieler2.hauptrolle.fraktion.id;

        if (spieler1.hauptrolle.equals(Henker.ID)) {
            fraktion1 = Bürger.ID;
        }

        if (spieler2.hauptrolle.equals(Henker.ID)) {
            fraktion2 = Bürger.ID;
        }

        String information;

        if (Objects.equals(fraktion1, fraktion2)) {
            information = GLEICH;
        } else {
            Liebespaar liebespaar = Game.game.liebespaar;
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

        Spieler analytikerSpieler = Game.game.findSpielerPerRolle(this.id);

        if (analytikerSpieler != null && (spieler1.equals(LadyAleera.gefälschterSpieler) || spieler2.equals(LadyAleera.gefälschterSpieler))) {
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

    @Override
    public void beginNight() {
        super.beginNight();
        besuchtAnalysieren = null;
    }
}
