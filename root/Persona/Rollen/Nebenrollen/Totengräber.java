package root.Persona.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Nebenrolle;
import root.Persona.Rolle;
import root.Persona.Rollen.Constants.NebenrollenType.Passiv;
import root.Phases.Nacht;
import root.Phases.NightBuilding.Constants.StatementType;
import root.Phases.NightBuilding.Statement;
import root.Phases.NightBuilding.StatementRolle;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Totengräber extends Nebenrolle {
    public static String title = "Karte tauschen";
    public static final String beschreibung = "Totengräber erwacht und entscheidet ob er seine Bonusrollenkarte tauschen möchte";
    public static StatementType statementType = StatementType.ROLLE_CHOOSE_ONE;
    public static final String name = "Totengräber";
    public static final String imagePath = ImagePath.TOTENGRÄBER_KARTE;
    public static boolean spammable = false;

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
                Spieler deadSpieler = game.findSpielerOrDeadPerRolle(chosenNebenrolle.getName());
                chosenNebenrolle = (Nebenrolle) Rolle.findRolle(deadSpieler.nebenrolle.getName());

                Spieler spielerTotengräber = game.findSpielerPerRolle(name);
                spielerTotengräber.nebenrolle = chosenNebenrolle;
                deadSpieler.nebenrolle = new Schatten();

                game.mitteNebenrollen.remove(chosenNebenrolle);
                game.mitteNebenrollen.add(this);

                removeSammlerFlag(chosenNebenrolle.getName());
            } catch (NullPointerException e) {
                System.out.println(name + " nicht gefunden");
            }
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public boolean isSpammable() {
        return spammable;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getBeschreibung() {
        return beschreibung;
    }

    @Override
    public StatementType getStatementType() {
        return statementType;
    }

    public void removeSammlerFlag(String nebenRolle) {
        for (Statement statement : Nacht.statements) {
            if (statement.getClass() == StatementRolle.class) {
                StatementRolle statementRolle = (StatementRolle) statement;
                if (statementRolle.getRolle().getName().equals(nebenRolle)) {
                    statementRolle.sammler = false;
                }
            }
        }
    }

    public static ArrayList<String> getNehmbareNebenrollen() {
        ArrayList<String> nehmbareNebenrollen = new ArrayList<>();

        for (Nebenrolle nebenrolle : game.mitteNebenrollen) {
            if (!nebenrolle.getType().equals(new Passiv())) {
                nehmbareNebenrollen.add(nebenrolle.getName());
            }
        }

        return nehmbareNebenrollen;
    }
}
