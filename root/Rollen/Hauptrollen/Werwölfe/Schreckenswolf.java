package root.Rollen.Hauptrollen.Werwölfe;

import root.Frontend.FrontendControl;
import root.Phases.Nacht;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;
import root.Spieler;
import root.mechanics.Opfer;

import java.util.ArrayList;

public class Schreckenswolf extends Hauptrolle
{
    public static final String name = "Schreckenswolf";
    public static Fraktion fraktion = new Werwölfe();
    public static final String imagePath = ResourcePath.SCHRECKENSWOLF_KARTE;
    public static boolean spammable = false;
    public static boolean killing = true;

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);
        if(chosenPlayer != null) {
            besucht = chosenPlayer;
            Nacht.beschworenerSpieler = chosenPlayer;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
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
    public boolean isKilling() {
        return killing;
    }

    public boolean werwölfeKilledOnSchutz() {
        return !didAllOpferDie(possibleWerwolfOpfer());
    }

    public ArrayList<Opfer> possibleWerwolfOpfer() {
        ArrayList<Opfer> possibleWerwolfOpfer = new ArrayList<>();
        for(Opfer opfer : Opfer.possibleVictims) {
            if(opfer.täter!=null && opfer.täter.hauptrolle.getFraktion().getName().equals(Werwölfe.name)) {
                possibleWerwolfOpfer.add(opfer);
            }
        }

        return possibleWerwolfOpfer;
    }

    public boolean didAllOpferDie(ArrayList<Opfer> possibleOpfer) {
        possibleOpfer.removeAll(Opfer.deadVictims);

        return possibleOpfer.size()==0;
    }
}