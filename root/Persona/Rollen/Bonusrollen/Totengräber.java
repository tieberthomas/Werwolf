package root.Persona.Rollen.Bonusrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Rolle;
import root.Persona.Rollen.Constants.BonusrollenType.Aktiv;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Phases.Nacht;
import root.Phases.NightBuilding.Constants.StatementType;
import root.Phases.NightBuilding.Statement;
import root.Phases.NightBuilding.StatementRolle;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Totengräber extends Bonusrolle {
    public static final String STATEMENT_TITLE = "Karte tauschen";
    public static final String STATEMENT_BESCHREIBUNG = "Totengräber erwacht und entscheidet ob er seine Bonusrollenkarte tauschen möchte";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NAME = "Totengräber";
    public static final String IMAGE_PATH = ImagePath.TOTENGRÄBER_KARTE;
    public static final BonusrollenType TYPE = new Aktiv();

    public Totengräber() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.type = TYPE;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        ArrayList<String> nehmbareBonusrollen = getNehmbareBonusrollen();
        nehmbareBonusrollen.add("");
        return new FrontendControl(FrontendControlType.DROPDOWN_LIST, nehmbareBonusrollen);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Bonusrolle chosenBonusrolle = game.findBonusrolle(chosenOption);
        if (chosenBonusrolle != null) {
            try {
                Spieler deadSpieler = game.findSpielerOrDeadPerRolle(chosenBonusrolle.name);
                chosenBonusrolle = (Bonusrolle) Rolle.findRolle(deadSpieler.bonusrolle.name);

                Spieler spielerTotengräber = game.findSpielerPerRolle(NAME);
                spielerTotengräber.bonusrolle = chosenBonusrolle;
                deadSpieler.bonusrolle = new Schatten();

                game.mitteBonusrollen.remove(chosenBonusrolle);
                game.mitteBonusrollen.add(this);

                removeSammlerFlag(chosenBonusrolle.name);
            } catch (NullPointerException e) {
                System.out.println(NAME + " nicht gefunden");
            }
        }
    }

    public void removeSammlerFlag(String bonusRolle) {
        for (Statement statement : Nacht.statements) {
            if (statement.getClass() == StatementRolle.class) {
                StatementRolle statementRolle = (StatementRolle) statement;
                if (statementRolle.getRolle().name.equals(bonusRolle)) {
                    statementRolle.sammler = false;
                }
            }
        }
    }

    public static ArrayList<String> getNehmbareBonusrollen() {
        ArrayList<String> nehmbareBonusrollen = new ArrayList<>();

        for (Bonusrolle bonusrolle : game.mitteBonusrollen) {
            nehmbareBonusrollen.add(bonusrolle.name);
        }

        return nehmbareBonusrollen;
    }
}
