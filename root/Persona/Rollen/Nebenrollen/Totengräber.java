package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Persona.Rolle;
import root.Persona.Rollen.Constants.NebenrollenType.Aktiv;
import root.Persona.Rollen.Constants.NebenrollenType.NebenrollenType;
import root.Phases.Nacht;
import root.Phases.NightBuilding.Constants.StatementType;
import root.Phases.NightBuilding.Statement;
import root.Phases.NightBuilding.StatementRolle;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Totengräber extends Nebenrolle {
    public static final String STATEMENT_TITLE = "Karte tauschen";
    public static final String STATEMENT_BESCHREIBUNG = "Totengräber erwacht und entscheidet ob er seine Bonusrollenkarte tauschen möchte";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NAME = "Totengräber";
    public static final String IMAGE_PATH = ImagePath.TOTENGRÄBER_KARTE;
    public static final NebenrollenType TYPE = new Aktiv();

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
        ArrayList<String> nehmbareNebenrollen = getNehmbareNebenrollen();
        nehmbareNebenrollen.add("");
        return new FrontendControl(FrontendControlType.DROPDOWN_LIST, nehmbareNebenrollen);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Nebenrolle chosenNebenrolle = game.findNebenrolle(chosenOption);
        if (chosenNebenrolle != null) {
            try {
                Spieler deadSpieler = game.findSpielerOrDeadPerRolle(chosenNebenrolle.name);
                chosenNebenrolle = (Nebenrolle) Rolle.findRolle(deadSpieler.nebenrolle.name);

                Spieler spielerTotengräber = game.findSpielerPerRolle(NAME);
                spielerTotengräber.nebenrolle = chosenNebenrolle;
                deadSpieler.nebenrolle = new Schatten();

                game.mitteNebenrollen.remove(chosenNebenrolle);
                game.mitteNebenrollen.add(this);

                removeSammlerFlag(chosenNebenrolle.name);
            } catch (NullPointerException e) {
                System.out.println(NAME + " nicht gefunden");
            }
        }
    }

    public void removeSammlerFlag(String nebenRolle) {
        for (Statement statement : Nacht.statements) {
            if (statement.getClass() == StatementRolle.class) {
                StatementRolle statementRolle = (StatementRolle) statement;
                if (statementRolle.getRolle().name.equals(nebenRolle)) {
                    statementRolle.sammler = false;
                }
            }
        }
    }

    public static ArrayList<String> getNehmbareNebenrollen() {
        ArrayList<String> nehmbareNebenrollen = new ArrayList<>();

        for (Nebenrolle nebenrolle : game.mitteNebenrollen) {
            nehmbareNebenrollen.add(nebenrolle.name);
        }

        return nehmbareNebenrollen;
    }
}
