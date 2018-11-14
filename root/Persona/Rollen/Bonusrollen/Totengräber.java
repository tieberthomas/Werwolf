package root.Persona.Rollen.Bonusrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Game;

import java.util.ArrayList;
import java.util.List;

public class Totengräber extends Bonusrolle {
    public static final String ID = "ID_Totengräber";
    public static final String NAME = "Totengräber";
    public static final String IMAGE_PATH = ImagePath.TOTENGRÄBER_KARTE;
    public static final BonusrollenType TYPE = new Aktiv();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Karte tauschen";
    public static final String STATEMENT_BESCHREIBUNG = "Totengräber erwacht und entscheidet, ob er seine Bonusrollenkarte tauschen möchte";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public Totengräber() {
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
    public FrontendControl getDropdownOptionsFrontendControl() {
        List<String> nehmbareBonusrollen = getNehmbareBonusrollen();
        nehmbareBonusrollen.add("");
        return new FrontendControl(FrontendControlType.DROPDOWN_LIST, nehmbareBonusrollen);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Bonusrolle chosenBonusrolle = Game.game.findBonusrollePerName(chosenOption);
        if (chosenBonusrolle != null) {
            try {
                Spieler deadSpieler = Game.game.findSpielerOrDeadPerRolle(chosenBonusrolle.id);
                Spieler spielerTotengräber = Game.game.findSpielerPerRolle(this.id);

                spielerTotengräber.bonusrolle = chosenBonusrolle;
                deadSpieler.bonusrolle = new Schatten();

                Game.game.mitteBonusrollen.remove(chosenBonusrolle);
                Game.game.mitteBonusrollen.add(this);
            } catch (NullPointerException e) {
                System.out.println(NAME + " nicht gefunden");
            }
        }
    }

    public static List<String> getNehmbareBonusrollen() {
        List<String> nehmbareBonusrollen = new ArrayList<>();

        //TODO michael fragen, welche rollen darf Totengräber nehmen
        for (Bonusrolle bonusrolle : Game.game.mitteBonusrollen) {
            nehmbareBonusrollen.add(bonusrolle.name);
        }

        return nehmbareBonusrollen;
    }
}
