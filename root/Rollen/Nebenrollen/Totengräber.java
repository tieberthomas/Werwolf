package root.Rollen.Nebenrollen;

import root.Frontend.FrontendControl;
import root.Phases.Nacht;
import root.Phases.Statement;
import root.Phases.StatementRolle;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Nebenrolle;
import root.Rollen.Rolle;
import root.Spieler;

import java.util.ArrayList;

/**
 * Created by Steve on 12.11.2017.
 */
public class Totengräber extends Nebenrolle
{
    public static final String name = "Totengräber";
    public static final String imagePath = ResourcePath.TOTENGRÄBER_KARTE;
    public static boolean spammable = false;

    @Override
    public FrontendControl getDropdownOptions() {
        return new FrontendControl(FrontendControl.DROPDOWN_LIST, getNehmbareNebenrollen());
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Nebenrolle chosenNebenrolle = Nebenrolle.findNebenrolle(chosenOption);
        if (chosenNebenrolle != null) {
            try {
                Spieler deadSpieler = Spieler.findSpielerOrDeadPerRolle(chosenNebenrolle.getName());
                chosenNebenrolle = (Nebenrolle)Rolle.findRolle(deadSpieler.nebenrolle.getName());

                Spieler spielerTotengräber = Spieler.findSpielerPerRolle(name);
                spielerTotengräber.nebenrolle = chosenNebenrolle;
                deadSpieler.nebenrolle = new Schatten();

                Rolle.mitteNebenrollen.remove(chosenNebenrolle);
                Rolle.mitteNebenrollen.add(this);

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

        for(Nebenrolle nebenrolle : Rolle.mitteNebenrollen) {
            if(!nebenrolle.getType().equals(Nebenrolle.PASSIV)) {
                nehmbareNebenrollen.add(nebenrolle.getName());
            }
        }

        nehmbareNebenrollen.add("");

        return nehmbareNebenrollen;
    }
}
