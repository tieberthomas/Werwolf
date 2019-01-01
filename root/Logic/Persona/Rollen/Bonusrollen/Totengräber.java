package root.Logic.Persona.Rollen.Bonusrollen;

import root.Controller.FrontendObject.FrontendObjectType;
import root.Controller.FrontendObject.FrontendObject;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Logic.Persona.Rollen.Constants.DropdownConstants;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

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
    public FrontendObject getFrontendObject() {
        return new FrontendObject(FrontendObjectType.DROPDOWN_LIST, new DropdownOptions(getNehmbareBonusrollen(), DropdownConstants.EMPTY));
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Bonusrolle chosenBonusrolle = Game.game.findBonusrollePerName(chosenOption);
        if (chosenBonusrolle != null) {
            try {
                Spieler deadSpieler = Game.game.findSpielerPerRolle(chosenBonusrolle.id);
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

        for (Bonusrolle bonusrolle : Game.game.mitteBonusrollen) {
            nehmbareBonusrollen.add(bonusrolle.name);
        }

        Spieler spielerTotengräber = Game.game.findSpielerPerRolle(ID);
        if (spielerTotengräber != null) {
            if (spielerTotengräber.getFraktion().equals(Bürger.ID)) {
                nehmbareBonusrollen.remove(DunklesLicht.NAME);
            } else {
                nehmbareBonusrollen.remove(Schutzengel.NAME);
            }
        }

        return nehmbareBonusrollen;
    }
}
