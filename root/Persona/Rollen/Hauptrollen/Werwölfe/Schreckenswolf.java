package root.Persona.Rollen.Hauptrollen.Werwölfe;

import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Bonusrollen.Wolfspelz;
import root.Phases.NightBuilding.Constants.StatementType;
import root.Phases.NormalNight;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Opfer;

import java.util.ArrayList;

public class Schreckenswolf extends Hauptrolle {
    public static final String STATEMENT_IDENTIFIER = "Schreckenswolf";
    public static final String STATEMENT_TITLE = "Mitspieler verstummen";
    public static final String STATEMENT_BESCHREIBUNG = "Schreckenswolf erwacht und verstummt ggf. einen Spieler der am folgenden Tag nichtmehr reden darf";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String VERSTUMMT = "Schreckenswolf_Verstummt";
    public static final String SECOND_STATEMENT_TITLE = "Verstummt";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Der verstummte Spieler wird bekannt gegeben";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String NAME = "Schreckenswolf";
    public static final String IMAGE_PATH = ImagePath.SCHRECKENSWOLF_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public Schreckenswolf() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementIdentifier = STATEMENT_IDENTIFIER;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementIdentifier = VERSTUMMT;
        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;

        this.spammable = false;
        this.killing = true;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        return game.getSpielerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = game.findSpieler(chosenOption);
        if (chosenSpieler != null) {
            besucht = chosenSpieler;
            NormalNight.beschworenerSpieler = chosenSpieler;
        }
    }

    public boolean werwölfeKilledOnSchutz() {
        return didSomeoneHaveSchutz(possibleWerwolfOpfer());
    }

    private ArrayList<Opfer> possibleWerwolfOpfer() {
        ArrayList<Opfer> possibleWerwolfOpfer = new ArrayList<>();
        for (Opfer opfer : Opfer.possibleOpfer) {
            if (opfer.täterFraktion.name.equals(Werwölfe.NAME)) {
                possibleWerwolfOpfer.add(opfer);
            }
        }

        return possibleWerwolfOpfer;
    }

    private boolean didSomeoneHaveSchutz(ArrayList<Opfer> possibleOpfer) {
        boolean someoneHadSchutz = false;

        for (Opfer opfer : possibleOpfer) {
            if (opfer.opfer.geschützt || opfer.opfer.bonusrolle.equals(Wolfspelz.NAME)) {
                someoneHadSchutz = true;
            }
        }

        return someoneHadSchutz;
    }
}