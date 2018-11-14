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
import root.mechanics.KillLogik.Angriff;

import java.util.ArrayList;
import java.util.List;

public class Schreckenswolf extends Hauptrolle {
    public static final String ID = "ID_Schreckenswolf";
    public static final String NAME = "Schreckenswolf";
    public static final String IMAGE_PATH = ImagePath.SCHRECKENSWOLF_KARTE;
    public static final Fraktion FRAKTION = new Werwölfe();

    public static final String STATEMENT_ID = ID;
    public static final String STATEMENT_TITLE = "Mitspieler verstummen";
    public static final String STATEMENT_BESCHREIBUNG = "Schreckenswolf erwacht und verstummt ggf. einen Spieler der am folgenden Tag nichtmehr reden darf";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String VERSTUMMT = "Schreckenswolf_Verstummt";
    public static final String SECOND_STATEMENT_TITLE = "Verstummt";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Der verstummte Spieler wird bekannt gegeben";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public Schreckenswolf() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementID = STATEMENT_ID;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementID = VERSTUMMT;
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
        return didSomeoneHaveSchutz(werwolfAngriffe());
    }

    private List<Angriff> werwolfAngriffe() {
        List<Angriff> werwolfAngriffe = new ArrayList<>();
        for (Angriff angriff : NormalNight.angriffe) {
            if (angriff.täterFraktion != null) {
                if (angriff.täterFraktion.equals(Werwölfe.ID)) {
                    werwolfAngriffe.add(angriff);
                }
            }
        }

        return werwolfAngriffe;
    }

    private boolean didSomeoneHaveSchutz(List<Angriff> angriffe) {
        for (Angriff angriff : angriffe) {
            if (angriff.opfer.geschützt || angriff.opfer.bonusrolle.equals(Wolfspelz.ID)) {
                return true;
            }
        }

        return false;
    }
}