package root.Persona.Rollen.Bonusrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.BonusrollenType.Informativ;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Medium extends Bonusrolle {
    public static final String STATEMENT_IDENTIFIER = "Medium";
    public static final String STATEMENT_TITLE = "Bonusrolle";
    public static final String STATEMENT_BESCHREIBUNG = "Medium erwacht und lässt sich vom Erzähler eine Bonusrolle, die nicht im Spiel ist, zeigen";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_INFO;
    public static final String VERBRAUCHT_TITLE = "Bonusrollen";

    public static final String NAME = "Medium";
    public static final String IMAGE_PATH = ""; //TODO replace imagepath
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
            return new FrontendControl(FrontendControlType.LIST, VERBRAUCHT_TITLE, geseheneBonusrollen);
        }
    }

    private Bonusrolle getRandomUnseenBonusrolle() {
        ArrayList<Bonusrolle> unseenBonusroles = getAllUnseenBonusrollen();

        Bonusrolle bonusrolle;

        if (unseenBonusroles.size() > 0) {
            int randIndex = (int) (Math.random() * unseenBonusroles.size());

            bonusrolle = unseenBonusroles.get(randIndex);
            geseheneBonusrollen.add(bonusrolle.name);
        } else {
            bonusrolle = null;
        }

        return bonusrolle;
    }

    private ArrayList<Bonusrolle> getAllUnseenBonusrollen() {
        ArrayList<Bonusrolle> bonusrolesNotInGame = game.getStillAvailableBonusRoles();
        bonusrolesNotInGame.removeIf(bonusrolle -> geseheneBonusrollen.contains(bonusrolle.name));
        return bonusrolesNotInGame;
    }
}
