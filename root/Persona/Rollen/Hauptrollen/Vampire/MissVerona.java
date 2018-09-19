package root.Persona.Rollen.Hauptrollen.Vampire;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Hauptrolle;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.mechanics.Opfer;

import java.util.ArrayList;

public class MissVerona extends Hauptrolle {
    public static final String STATEMENT_TITLE = "Angegriffene Opfer";
    public static final String STATEMENT_BESCHREIBUNG = "Miss Verona erwacht und lässt sich Auskunft über die Spieler geben, die angegriffen wurden";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_INFO;

    public static final String NAME = "Miss Verona";
    public static Fraktion fraktion = new Vampire();
    public static final String IMAGE_PATH = ImagePath.MISS_VERONA_KARTE;
    public static boolean spammable = false;
    public static boolean killing = true;

    public MissVerona() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getInfo() {
        return new FrontendControl(FrontendControlType.LIST, findUntote());
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

    public ArrayList<String> findUntote() {
        ArrayList<String> untote = new ArrayList<>();

        for (Opfer possibleOpfer : Opfer.possibleVictims) {
            boolean überlebt = true;
            String currentPossibleOpferName = possibleOpfer.opfer.name;

            for (Opfer deadOpfer : Opfer.deadVictims) {
                if (currentPossibleOpferName.equals(deadOpfer.opfer.name)) {
                    überlebt = false;
                }
            }

            if (überlebt) {
                if (!untote.contains(currentPossibleOpferName))
                    untote.add(currentPossibleOpferName);
            }
        }

        return untote;
    }
}