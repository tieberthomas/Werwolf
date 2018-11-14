package root.Persona.Rollen.Bonusrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Utils.Rand;

import java.util.ArrayList;
import java.util.List;

public class Medium extends Bonusrolle {
    public static final String ID = "ID_Medium";
    public static final String NAME = "Medium";
    public static final String IMAGE_PATH = ImagePath.MEDIUM_KARTE;
    public static final BonusrollenType TYPE = new Informativ();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Bonusrolle";
    public static final String STATEMENT_BESCHREIBUNG = "Medium erwacht und erfährt eine zufällige Bonusrolle, die nicht im Spiel ist";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_INFO;

    private static final String NO_BONUSROLES_OUT_OF_GAME = "Es sind alle Bonusrollen im Spiel";

    private static List<String> geseheneBonusrollen = new ArrayList<>();

    public Medium() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getInfo() {
        Bonusrolle randomBonusrolle = getRandomUnseenBonusrolle();

        if (randomBonusrolle != null) {
            return new FrontendControl(FrontendControlType.CARD, randomBonusrolle.imagePath);
        } else {
            return new FrontendControl(statementTitle, NO_BONUSROLES_OUT_OF_GAME);
        }
    }

    private Bonusrolle getRandomUnseenBonusrolle() {
        List<Bonusrolle> unseenBonusrollen = getAllUnseenBonusrollen();

        Bonusrolle bonusrolle;

        if (unseenBonusrollen.size() > 0) {
            bonusrolle = Rand.getRandomElement(unseenBonusrollen);
            geseheneBonusrollen.add(bonusrolle.id);
        } else {
            if (geseheneBonusrollen.size() == 0) {
                //das heißt dass alle bonusrollen im spiel sind
                return null;
            } else {
                geseheneBonusrollen.clear();
                return getRandomUnseenBonusrolle();
            }
        }

        return bonusrolle;
    }

    private List<Bonusrolle> getAllUnseenBonusrollen() {
        List<Bonusrolle> bonusrollenNotInGame = game.getStillAvailableBonusrollen();
        bonusrollenNotInGame.removeIf(bonusrolle -> geseheneBonusrollen.contains(bonusrolle.id));
        return bonusrollenNotInGame;
    }
}
