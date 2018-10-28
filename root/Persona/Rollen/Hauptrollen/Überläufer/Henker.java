package root.Persona.Rollen.Hauptrollen.Überläufer;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Überläufer_Fraktion;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Constants.Zeigekarten.Tot;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;

import java.util.ArrayList;

public class Henker extends Hauptrolle {
    public static final String STATEMENT_ID = "Henker";
    public static final String SPIELER_TITLE = "Person hängen";
    public static final String FRAKTION_TITLE = "Person hängen";
    public static final String HAUPTROLLEN_TITLE = "Hauptrolle wählen";
    public static final String BONUSROLLENTYP_TITLE = "Bonusrollentyp wählen";
    public static final String BONUSROLLEN_TITLE = "Bonusrolle wählen";
    public static final String AUSWAHL_BESTÄTIGUNG_TITLE = "Person hängen";
    public static final String STATEMENT_BESCHREIBUNG = "Henker erwcht und versucht die Rollen eines Mitspielers zu erraten";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String NAME = "Henker";
    public static final String IMAGE_PATH = ImagePath.HENKER_KARTE;
    public static final Fraktion FRAKTION = new Überläufer_Fraktion();

    public Henker() {
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementID = STATEMENT_ID;
        this.statementTitle = SPIELER_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.spammable = false;
        this.killing = true;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        FrontendControl spielerAuswahl = game.getMitspielerCheckSpammableFrontendControl(this);
        FrontendControl fraktionsAuswahl = new FrontendControl(FrontendControlType.DROPDOWN_LIST, FRAKTION_TITLE, Fraktion.getLivingFraktionStrings());
        fraktionsAuswahl.hatZurückButton = true;
        FrontendControl hauptrollenAuswahl = game.getMitspielerCheckSpammableFrontendControl(this);
        hauptrollenAuswahl.hatZurückButton = true;
        FrontendControl bonusrollenTypAuswahl = game.getMitspielerCheckSpammableFrontendControl(this);
        bonusrollenTypAuswahl.hatZurückButton = true;
        FrontendControl bonusrollenAuswahl = game.getMitspielerCheckSpammableFrontendControl(this);
        bonusrollenAuswahl.hatZurückButton = true;
        FrontendControl auswahlBestätigung = game.getMitspielerCheckSpammableFrontendControl(this);
        auswahlBestätigung.hatZurückButton = true;

        ArrayList<FrontendControl> pages = new ArrayList<>();
        pages.add(spielerAuswahl);
        pages.add(fraktionsAuswahl);
        pages.add(hauptrollenAuswahl);
        pages.add(bonusrollenTypAuswahl);
        pages.add(bonusrollenAuswahl);
        pages.add(auswahlBestätigung);

        return new FrontendControl(pages, FrontendControlType.PAGES);

        //return game.getMitspielerCheckSpammableFrontendControl(this);
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