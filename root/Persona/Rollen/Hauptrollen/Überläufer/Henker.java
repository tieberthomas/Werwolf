package root.Persona.Rollen.Hauptrollen.Überläufer;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.FrontendControl;
import root.Frontend.Utils.DropdownOptions;
import root.Persona.Bonusrolle;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Persona.Fraktionen.ÜberläuferFraktion;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Bonusrollen.ReineSeele;
import root.Persona.Rollen.Bonusrollen.Schatten;
import root.Persona.Rollen.Bonusrollen.SchwarzeSeele;
import root.Persona.Rollen.Constants.BonusrollenType.BonusrollenType;
import root.Persona.Rollen.Constants.InformationsCluster.BonusrollenInfo;
import root.Persona.Rollen.Constants.Zeigekarten.Geschützt;
import root.Persona.Rollen.Constants.Zeigekarten.Tot;
import root.Persona.Rollen.Constants.Zeigekarten.Tötend;
import root.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;
import root.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Phases.NightBuilding.Constants.StatementType;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.Utils.ListHelper;
import root.mechanics.Game;
import root.mechanics.KillLogik.AbsoluteKill;
import root.mechanics.KillLogik.Selbstmord;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Henker extends Hauptrolle {
    public static final String ID = "ID_Henker";
    public static final String NAME = "Henker";
    public static final String IMAGE_PATH = ImagePath.HENKER_KARTE;
    public static final Fraktion FRAKTION = new ÜberläuferFraktion();

    public static final String STATEMENT_ID = ID;
    public static final String SUCCESSFUL_KILL_TITLE = "Erfolgreiche Hängung";
    public static final String SPIELER_TITLE = "Person hängen";
    public static final String FRAKTION_TITLE = "Fraktion wählen";
    public static final String HAUPTROLLEN_TITLE = "Hauptrolle wählen";
    public static final String BONUSROLLENTYP_TITLE = "Bonusrollentyp wählen";
    public static final String BONUSROLLEN_TITLE = "Bonusrolle wählen";
    public static final String AUSWAHL_BESTÄTIGUNG_TITLE = "Person hängen";
    public static final String STATEMENT_BESCHREIBUNG = "Henker erawcht und versucht die Rollen eines Mitspielers zu erraten";
    public static final StatementType STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static final String SETUP_NIGHT_STATEMENT_ID = "Setup_Night_Henker";
    public static final String SETUP_NIGHT_STATEMENT_TITLE = "Nicht im Spiel";
    public static final String SETUP_NIGHT_STATEMENT_BESCHREIBUNG = "Henker erwacht und erfährt eine Bürgerrolle die nicht im Spiel ist";
    public static final StatementType SETUP_NIGHT_STATEMENT_TYPE = StatementType.ROLLE_SPECAL;

    public static Hauptrolle fakeRolle = new Dorfbewohner();

    public static int pagecounter = 0;
    public static int numberOfPages = 6;

    public static Fraktion chosenFraktion;
    public static Hauptrolle chosenHauptrolle;
    public static BonusrollenType chosenBonusrollenType;
    public static Bonusrolle chosenBonusrolle;

    public Henker() {
        this.id = ID;
        this.name = NAME;
        this.imagePath = IMAGE_PATH;
        this.fraktion = FRAKTION;

        this.statementID = STATEMENT_ID;
        this.statementTitle = SPIELER_TITLE;
        this.statementBeschreibung = STATEMENT_BESCHREIBUNG;
        this.statementType = STATEMENT_TYPE;

        this.setupNightStatementID = SETUP_NIGHT_STATEMENT_ID;
        this.setupNightStatementTitle = SETUP_NIGHT_STATEMENT_TITLE;
        this.setupNightStatementBeschreibung = SETUP_NIGHT_STATEMENT_BESCHREIBUNG;
        this.setupNightStatementType = SETUP_NIGHT_STATEMENT_TYPE;

        this.spammable = false;
        this.killing = true;
    }

    @Override
    public FrontendControl getDropdownOptionsFrontendControl() {
        return Game.game.getSpielerFrontendControl(this, true);
    }

    @Override
    public void processChosenOption(String chosenOption) {
        Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
        besucht = chosenSpieler;
    }

    @Override
    public FrontendControl processChosenOptionsGetInfo(String chosenOption1, String chosenOption2) {
        if (besucht != null && chosenOption1 != null && !chosenOption1.isEmpty() && chosenOption2 != null && !chosenOption2.isEmpty()) {
            Hauptrolle hauptrolle = Game.game.findHauptrollePerName(chosenOption1);
            Bonusrolle bonusrolle = Game.game.findBonusrollePerName(chosenOption2);

            int correctGuesses = 0;
            Spieler chosenSpieler = besucht;

            if (chosenSpieler.hauptrolle.equals(hauptrolle)) {
                correctGuesses++;
            }

            if (chosenSpieler.bonusrolle.equals(bonusrolle)) {
                correctGuesses++;
            }

            Spieler henkerSpieler = Game.game.findSpielerPerRolle(this.id);

            switch (correctGuesses) {
                case 0:
                    Selbstmord.execute(henkerSpieler);

                    return new FrontendControl(new Tot());
                case 1:
                    henkerSpieler.geschützt = true;

                    return new FrontendControl(new Geschützt());
                case 2:
                    henkerSpieler.geschützt = true;
                    AbsoluteKill.execute(besucht, henkerSpieler);

                    return new FrontendControl(SUCCESSFUL_KILL_TITLE, new Tötend().imagePath, new Geschützt().imagePath, new ArrayList<>());
            }
        }

        return new FrontendControl();
    }

    public FrontendControl getPage(String chosenOption) {
        switch (pagecounter) {
            case 0:
                break;
            case 1:
                Spieler chosenSpieler = Game.game.findSpieler(chosenOption);
                besucht = chosenSpieler;
                break;
            case 2:
                chosenFraktion = Fraktion.findFraktionPerName(chosenOption);
                break;
            case 3:
                chosenHauptrolle = Game.game.findHauptrollePerName(chosenOption);
                break;
            case 4:
                chosenBonusrollenType = BonusrollenType.getBonusrollenType(chosenOption);
                break;
            case 5:
                chosenBonusrolle = Game.game.findBonusrollePerName(chosenOption);
                break;
        }

        return getPage();
    }

    public FrontendControl getPage() {
        if (besucht == null) {
            pagecounter = numberOfPages;
            return new FrontendControl();
        }

        switch (pagecounter) {
            case 0:
                return Game.game.getSpielerFrontendControl(this, true);
            case 1:
                FrontendControl fraktionsAuswahl = new FrontendControl(FrontendControlType.DROPDOWN_LIST, FRAKTION_TITLE, new DropdownOptions(Fraktion.getLivingFraktionStrings()));
                fraktionsAuswahl.hatZurückButton = true;
                return fraktionsAuswahl;
            case 2:
                List<Hauptrolle> hauptrollen = ListHelper.cloneList(Game.game.hauptrollenInGame);
                List<String> hauptrollenStrings = hauptrollen.stream().
                        filter(hauptrolle -> hauptrolle.fraktion.equals(chosenFraktion)).
                        map(hauptrolle -> hauptrolle.name).
                        distinct().
                        collect(Collectors.toList());
                FrontendControl hauptrollenAuswahl = new FrontendControl(FrontendControlType.DROPDOWN_LIST, HAUPTROLLEN_TITLE, new DropdownOptions(hauptrollenStrings));
                hauptrollenAuswahl.hatZurückButton = true;
                return hauptrollenAuswahl;
            case 3:
                List<Zeigekarte> bonusrollenTypes = BonusrollenInfo.informations;
                List<String> bonusrollenTypeStrings = bonusrollenTypes.stream().
                        map(type -> type.name).
                        collect(Collectors.toList());
                FrontendControl bonusrollenTypAuswahl = new FrontendControl(FrontendControlType.DROPDOWN_LIST, BONUSROLLENTYP_TITLE, new DropdownOptions(bonusrollenTypeStrings));
                bonusrollenTypAuswahl.hatZurückButton = true;
                return bonusrollenTypAuswahl;
            case 4:
                List<Bonusrolle> bonusrollen = ListHelper.cloneList(Game.game.bonusrollenInGame);
                if (Fraktion.fraktionContainedInNight(SchattenpriesterFraktion.ID)) {
                    bonusrollen.add(new Schatten());
                }
                bonusrollen.add(new SchwarzeSeele());
                bonusrollen.add(new ReineSeele());
                List<String> bonusrollenStrings = bonusrollen.stream()
                        .filter(bonusrolle -> bonusrolle.type.equals(chosenBonusrollenType))
                        .map(bonusrolle -> bonusrolle.name)
                        .distinct()
                        .collect(Collectors.toList());
                FrontendControl bonusrollenAuswahl = new FrontendControl(FrontendControlType.DROPDOWN_LIST, BONUSROLLEN_TITLE, new DropdownOptions(bonusrollenStrings));
                bonusrollenAuswahl.hatZurückButton = true;
                return bonusrollenAuswahl;
            case 5:
                List<String> namenDerRollen = new ArrayList<>();
                namenDerRollen.add(chosenHauptrolle.name);
                namenDerRollen.add(chosenBonusrolle.name);
                FrontendControl auswahlBestätigung = new FrontendControl(besucht.name, chosenHauptrolle.imagePath, chosenBonusrolle.imagePath, namenDerRollen);
                auswahlBestätigung.hatZurückButton = true;
                return auswahlBestätigung;
            default:
                System.out.println("There is no Henker Page with this number");
                return Game.game.getSpielerFrontendControl(this, true);
        }
    }

    public Zeigekarte isTötendInfo(Spieler requester) {
        return fakeRolle.isTötendInfo(requester);
    }

    public Zeigekarte getFraktionInfo() {
        return fakeRolle.getFraktionInfo();
    }
}