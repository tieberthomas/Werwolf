package root.Persona.Rollen.Bonusrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;

public class Medium extends Bonusrolle {
    public static final String STATEMENT_IDENTIFIER = "Medium";
    public static final String STATEMENT_TITLE = "Bonusrolle";
    public static final String STATEMENT_BESCHREIBUNG = "Medium erwacht und lässt sich vom Erzähler eine Bonusrolle, die nicht im Spiel ist, zeigen";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_INFO;

    public static final String NO_BONUSROLES_OUT_OF_GAME = "Es sind alle Bonusrollen im Spiel";

    public static final String NAME = "Medium";
    public static final String IMAGE_PATH = ImagePath.MEDIUM_KARTE;
    public static final BonusrollenType TYPE = new Informativ();


    public static ArrayList<String> geseheneBonusrollen = new ArrayList<>();

    public Medium() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementIdentifier = STATEMENT_IDENTIFIER;
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
        ArrayList<Bonusrolle> unseenBonusrollen = getAllUnseenBonusrollen();

        Bonusrolle bonusrolle;

        if (unseenBonusrollen.size() > 0) {
            int randIndex = (int) (Math.random() * unseenBonusrollen.size());

            bonusrolle = unseenBonusrollen.get(randIndex);
            geseheneBonusrollen.add(bonusrolle.name);
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

    private ArrayList<Bonusrolle> getAllUnseenBonusrollen() {
        ArrayList<Bonusrolle> bonusrollenNotInGame = game.getStillAvailableBonusrollen();
        bonusrollenNotInGame.removeIf(bonusrolle -> geseheneBonusrollen.contains(bonusrolle.name));
        return bonusrollenNotInGame;
    }
}
