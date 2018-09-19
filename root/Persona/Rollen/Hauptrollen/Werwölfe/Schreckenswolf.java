package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Nebenrollen.Wolfspelz;
import root.Phases.Nacht;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Opfer;

import java.util.ArrayList;

public class Schreckenswolf extends Hauptrolle {
    public static String STATEMENT_TITLE = "Mitspieler verstummen";
    public static final String STATEMENT_BESCHREIBUNG = "Schreckenswolf erwacht und verstummt ggf. einen Spieler der am folgenden Tag nichtmehr reden darf";
    public static StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String SECOND_STATEMENT_TITLE = "Verstummt";
    public static final String VERSTUMMT = "Der verstummte Spieler wird bekannt gegeben";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = VERSTUMMT;
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.ROLLE_INFO;

    public static final String NAME = "Schreckenswolf";
    public static Fraktion fraktion = new Werwölfe();
    public static final String IMAGE_PATH = ImagePath.SCHRECKENSWOLF_KARTE;
    public static boolean spammable = false;
    public static boolean killing = true;

    public Schreckenswolf() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        return game.getPlayerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenPlayer = game.findSpieler(chosenOption);
        if (chosenPlayer != null) {
            besucht = chosenPlayer;
            Nacht.beschworenerSpieler = chosenPlayer;
        }
    }

    @Override
    public Fraktion getFraktion() {
        return fraktion;
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
        return didSomeoneHaveSchutz(possibleWerwolfOpfer());
    }

    private ArrayList<Opfer> possibleWerwolfOpfer() {
        ArrayList<Opfer> possibleWerwolfOpfer = new ArrayList<>();
        for (Opfer opfer : Opfer.possibleVictims) {
            if (opfer.täterFraktion.name.equals(Werwölfe.NAME)) {
                possibleWerwolfOpfer.add(opfer);
            }
        }

        return possibleWerwolfOpfer;
    }

    private boolean didSomeoneHaveSchutz(ArrayList<Opfer> possibleOpfer) {
        boolean someoneHadSchutz = false;

        for (Opfer opfer : possibleOpfer) {
            if (opfer.opfer.geschützt || opfer.opfer.nebenrolle.equals(Wolfspelz.NAME)) {
                someoneHadSchutz = true;
            }
        }

        return someoneHadSchutz;
    }
}