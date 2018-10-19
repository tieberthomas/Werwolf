package root.Persona.Rollen.Bonusrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;
import java.util.Random;

public class Dieb extends Bonusrolle {
    public static final String STATEMENT_IDENTIFIER = "Dieb";
    public static final String STATEMENT_TITLE = "Bonusrolle stehlen";
    public static final String STATEMENT_BESCHREIBUNG = "Dieb erwacht und entscheidet ob er jemandes Bonusrolle stehlen möchte";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NEUE_BONUSROLLE = "Dieb_Neue_Bonusrolle";
    public static final String SECOND_STATEMENT_TITLE = "Neue Bonusrolle";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Der Bestohlene erwacht und erhält eine neue Bonusrolle";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.ROLLE_INFO;//TODO Problem: Statement ist von Dieb abhängig, Dieb ist zu diesem Zeitpunkt bereits aus dem Spiel

    public static final String NAME = "Dieb";
    public static final String IMAGE_PATH = ImagePath.DIEB_KARTE;
    public static final BonusrollenType TYPE = new Aktiv();

    public Dieb() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementIdentifier = STATEMENT_IDENTIFIER;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementIdentifier = NEUE_BONUSROLLE;
        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getMitspielerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);

        if (chosenPlayer != null) {
            try {
                besucht = chosenPlayer;

                Spieler spielerDieb = game.findSpielerPerRolle(NAME);

                spielerDieb.bonusrolle = chosenPlayer.bonusrolle;
                chosenPlayer.bonusrolle = getNewRandomBonusrolle();
            } catch (NullPointerException e) {
                System.out.println(NAME + " nicht gefunden");
            }
        }
    }

    @Override
    public FrontendControl getInfo() {
        if (besucht != null) {
            return new FrontendControl(FrontendControlType.CARD, besucht.bonusrolle.imagePath);
        }

        return new FrontendControl();
    }

    private Bonusrolle getNewRandomBonusrolle() {
        ArrayList<Bonusrolle> bonusrollen = game.getStillAvailableBonusRoles();

        int numberOfUnassignedBonusrollen = bonusrollen.size();
        Random random = new Random();
        int index = random.nextInt(numberOfUnassignedBonusrollen);

        return bonusrollen.get(index);
    }
}
