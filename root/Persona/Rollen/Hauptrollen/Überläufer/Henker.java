package root.Persona.Rollen.Hauptrollen.Überläufer;

import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Überläufer_Fraktion;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Constants.Zeigekarten.Tot;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

public class Henker extends Hauptrolle {
    public static final String STATEMENT_IDENTIFIER = "Henker";
    public static final String STATEMENT_TITLE = "Person hängen";
    public static final String STATEMENT_BESCHREIBUNG = "Henker erwacht und entscheidet wen er hängen möchte";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_CHOOSE_ONE;

    public static final String SECOND_STATEMENT_IDENTIFIER = "Henker_raten";
    public static final String SECOND_STATEMENT_TITLE = "Rollen raten";
    public static final String SECOND_STATEMENT_BESCHREIBUNG = "Henker ratet die Rollen der Person";
    public static final StatementType SECOND_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String NAME = "Henker";
    public static final String IMAGE_PATH = ImagePath.HENKER_KARTE;
    public static final Fraktion FRAKTION = new Überläufer_Fraktion();

    public Henker() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementIdentifier = STATEMENT_IDENTIFIER;
        this.statementTitle = STATEMENT_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.secondStatementIdentifier = SECOND_STATEMENT_IDENTIFIER;
        this.secondStatementTitle = SECOND_STATEMENT_TITLE;
        this.secondStatementBeschreibung = SECOND_STATEMENT_BESCHREIBUNG;
        this.secondStatementType = SECOND_STATEMENT_TYPE;

        this.spammable = false;
        this.killing = true;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        return game.getMitspielerCheckSpammableFrontendControl(this);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = game.findSpieler(chosenOption);

        if (chosenSpieler != null) {
            besucht = chosenSpieler;
        }
    }

    @Override
    public FrontendControl processChosenOptionsGetInfo(String chosenOption1, String chosenOption2) {
        if (besucht != null && chosenOption1 != null && !chosenOption1.isEmpty() && chosenOption2 != null && !chosenOption2.isEmpty()) {
            Hauptrolle hauptrolle = game.findHauptrolle(chosenOption1);
            Bonusrolle bonusrolle = game.findBonusrolle(chosenOption2);

            int correctGuesses = 0;
            Spieler chosenSpieler = besucht;

            if (chosenSpieler.hauptrolle.equals(hauptrolle)) {
                correctGuesses++;
            }

            if (chosenSpieler.bonusrolle.equals(bonusrolle)) {
                correctGuesses++;
            }

            System.out.println(correctGuesses);

            switch (correctGuesses) {
                case 0:
                    //kill henker
                    return new FrontendControl(new Tot());
                case 1:
                    //schütze henker
                    //return geschützt zeigekarte
                    break;
                case 2:
                    //kill spieler
                    //schütze henker
                    //return geschützt + kill frontencontrol
                    break;
            }
        }

        return new FrontendControl();
    }
}