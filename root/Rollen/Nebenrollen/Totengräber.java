package root.Rollen.Nebenrollen;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Phases.Nacht;
import root.Phases.Statement;
import root.Phases.StatementRolle;
import root.ResourceManagement.ImagePath;
import root.Rollen.Nebenrolle;
import root.Rollen.NebenrollenTyp;
import root.Rollen.Rolle;
import root.Spieler;

import java.util.ArrayList;

/**
 * Created by Steve on 12.11.2017.
 */
public class Totengräber extends Nebenrolle
{
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
                chosenNebenrolle = (Nebenrolle)Rolle.findRolle(deadSpieler.nebenrolle.getName());

                Spieler spielerTotengräber = game.findSpielerPerRolle(name);
                spielerTotengräber.nebenrolle = chosenNebenrolle;
                deadSpieler.nebenrolle = new Schatten();

                game.mitteNebenrollen.remove(chosenNebenrolle);
                game.mitteNebenrollen.add(this);

                removeSammlerFlag(chosenNebenrolle.getName());
            }catch (NullPointerException e) {
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

    public void removeSammlerFlag(String nebenRolle) {
        for(Statement statement : Nacht.statements) {
            if(statement.getClass() == StatementRolle.class) {
                StatementRolle statementRolle = (StatementRolle)statement;
                if(statementRolle.getRolle().getName().equals(nebenRolle)){
                    statementRolle.sammler = false;
                }
            }
        }
    }

    public static ArrayList<String> getNehmbareNebenrollen() {
        ArrayList<String> nehmbareNebenrollen = new ArrayList<>();

        for(Nebenrolle nebenrolle : game.mitteNebenrollen) {
            if(!nebenrolle.getType().equals(NebenrollenTyp.PASSIV)) {
                nehmbareNebenrollen.add(nebenrolle.getName());
            }
        }

        return nehmbareNebenrollen;
    }
}
