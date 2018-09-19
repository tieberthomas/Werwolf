package root.Persona.Rollen.Hauptrollen.Überläufer;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Überläufer_Fraktion;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Überläufer extends Hauptrolle {
    public static final String STATEMENT_TITLE = "Karte tauschen";
    public static final String STATEMENT_BESCHREIBUNG = "Überläufer erwacht und entscheidet ob er seine Hauptrollenkarte tauschen möchte";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String NAME = "Überläufer";
    public static Fraktion fraktion = new Überläufer_Fraktion();
    public static final String IMAGE_PATH = ImagePath.ÜBERLÄUFER_KARTE;
    public static boolean spammable = false;

    public Überläufer() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;

        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;
    }

    @Override
    public FrontendControl getDropdownOptions() {
        ArrayList<String> nehmbareHauptrollen = getMitteHauptrollenStrings();
        nehmbareHauptrollen.add("");
        return new FrontendControl(FrontendControlType.DROPDOWN_LIST, nehmbareHauptrollen);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Hauptrolle chosenHauptrolle = game.findHauptrolle(chosenOption);
        if (chosenHauptrolle != null) {
            try {
                Spieler spielerHauptrolle = game.findSpielerPerRolle(chosenHauptrolle.name);
                chosenHauptrolle = spielerHauptrolle.hauptrolle;

                Spieler spielerÜberläufer = game.findSpielerPerRolle(NAME);
                spielerÜberläufer.hauptrolle = chosenHauptrolle;
                spielerHauptrolle.hauptrolle = new Dorfbewohner();

                game.mitteHauptrollen.remove(chosenHauptrolle);
                game.mitteHauptrollen.add(this);
            } catch (NullPointerException e) {
                System.out.println(NAME + " nicht gefunden");
            }
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
}