package root.Logic.Persona.Rollen.Bonusrollen;

import root.Controller.FrontendObjectType;
import root.Controller.FrontendObject;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;
import root.Utils.Rand;

import java.util.List;

public class Dieb extends Bonusrolle {
    public static final String ID = "ID_Dieb";
    public static final String NAME = "Dieb";
    public static final String IMAGE_PATH = ImagePath.DIEB_KARTE;
    public static final BonusrollenType TYPE = new Aktiv();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Bonusrolle stehlen";
    public static final String STATEMENT_BESCHREIBUNG = "Dieb erwacht und entscheidet, ob er jemandes Bonusrolle stehlen möchte";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NEUE_BONUSROLLE = "Dieb_Neue_Bonusrolle";
    public static final String SECOND_STATEMENT_TITLE = "Neue Bonusrolle";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Der Bestohlene erwacht und erhält eine neue Bonusrolle";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.ROLLE_INFO;

    public Dieb() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementID = NEUE_BONUSROLLE;
        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;

        this.selfuseable = false;
    }

    @Override
    public FrontendObject getFrontendObject() {
        return Game.game.getSpielerFrontendObject(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);

        if (chosenSpieler != null) {
            try {
                besucht = chosenSpieler;

                Spieler spielerDieb = Game.game.findSpielerPerRolle(this.id);

                spielerDieb.bonusrolle = chosenSpieler.bonusrolle;
                Bonusrolle randomBonusrolle = getNewRandomBonusrolle();
                chosenSpieler.bonusrolle = randomBonusrolle;
                randomBonusrolle.tauschen(randomBonusrolle.getTauschErgebnis());
            } catch (NullPointerException e) {
                System.out.println(NAME + " nicht gefunden");
            }
        }
    }

    @Override
    public FrontendObject getInfo() {
        if (besucht != null) {
            return new FrontendObject(FrontendObjectType.CARD, besucht.bonusrolle.imagePath);
        }

        return new FrontendObject();
    }

    private Bonusrolle getNewRandomBonusrolle() {
        List<Bonusrolle> bonusrollen = Game.game.getStillAvailableBonusrollen();
        if (bonusrollen.isEmpty()) {
            return new Schatten();
        }

        Bonusrolle randomBonusrolle = Rand.getRandomElement(bonusrollen);

        Game.game.stillAvailableBonusrollen.remove(randomBonusrolle);

        return randomBonusrolle;
    }
}
