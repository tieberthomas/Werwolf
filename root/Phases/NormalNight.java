package root.Phases;

import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Persona.Fraktionen.Vampire;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.Persona.Rolle;
import root.Persona.Rollen.Bonusrollen.*;
import root.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Persona.Rollen.Constants.DropdownConstants;
import root.Persona.Rollen.Constants.SchnüfflerInformation;
import root.Persona.Rollen.Constants.Zeigekarten.*;
import root.Persona.Rollen.Hauptrollen.Bürger.Irrlicht;
import root.Persona.Rollen.Hauptrollen.Bürger.Sammler;
import root.Persona.Rollen.Hauptrollen.Bürger.Wirt;
import root.Persona.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Persona.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Blutwolf;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Chemiker;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Schreckenswolf;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Phases.NightBuilding.Constants.IndieStatements;
import root.Phases.NightBuilding.Constants.ProgrammStatements;
import root.Phases.NightBuilding.Constants.StatementState;
import root.Phases.NightBuilding.NormalNightStatementBuilder;
import root.Phases.NightBuilding.Statement;
import root.Phases.NightBuilding.StatementDependancy.StatementDependency;
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyFraktion;
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyRolle;
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyStatement;
import root.Spieler;
import root.Utils.ListHelper;
import root.mechanics.Game;
import root.mechanics.KillLogik.Angriff;
import root.mechanics.KillLogik.Opfer;
import root.mechanics.KillLogik.Selbstmord;
import root.mechanics.Liebespaar;
import root.mechanics.Torte;

import java.util.ArrayList;
import java.util.List;

public class NormalNight extends Thread {
    Game game;

    public static ArrayList<Statement> statements;
    public static Object lock;

    public static ArrayList<Angriff> angriffe = new ArrayList<>();
    public static ArrayList<Opfer> opfer = new ArrayList<>();

    public static ArrayList<Spieler> spielerAwake = new ArrayList<>();
    public static boolean wölfinKilled;
    public static Spieler wölfinSpieler;
    public static Spieler beschworenerSpieler;
    public static Spieler gefälschterSpieler;
    public static Spieler getarnterSpieler;

    public NormalNight(Game game) {
        this.game = game;
    }

    public void run() {
        lock = new Object();
        synchronized (lock) {
            FrontendControl dropdownOptions;
            FrontendControl info;

            String chosenOption;
            String chosenOptionLastStatement = null;
            Spieler chosenSpieler;

            Rolle rolle = null;
            Fraktion fraktion = null;

            String erzählerInfoIconImagePath;

            wölfinKilled = false;
            wölfinSpieler = null;
            beschworenerSpieler = null;

            List<String> spielerOrNon = game.getLivingSpielerOrNoneStrings();

            beginNight();

            statements = NormalNightStatementBuilder.normalNightBuildStatements();

            for (Statement statement : statements) {
                refreshStatementStates();

                chosenOption = null;

                if (statement.state != StatementState.INVISIBLE_NOT_IN_GAME) {
                    setSpielerAwake(statement);

                    StatementDependency dependency = statement.dependency;
                    while (dependency instanceof StatementDependencyStatement) {
                        dependency = ((StatementDependencyStatement) dependency).statement.dependency;
                    }

                    if (dependency instanceof StatementDependencyRolle) {
                        rolle = ((StatementDependencyRolle) dependency).rolle;
                    }
                    if (dependency instanceof StatementDependencyFraktion) {
                        fraktion = ((StatementDependencyFraktion) dependency).fraktion;
                    }

                    switch (statement.type) {
                        case SHOW_TITLE:
                            showTitle(statement);
                            break;

                        case ROLLE_CHOOSE_ONE:
                            dropdownOptions = rolle.getDropdownOptionsFrontendControl();
                            chosenOption = showFrontendControl(statement, dropdownOptions);
                            rolle.processChosenOption(chosenOption);
                            break;

                        case ROLLE_CHOOSE_ONE_INFO:
                            dropdownOptions = rolle.getDropdownOptionsFrontendControl();
                            chosenOption = showFrontendControl(statement, dropdownOptions);
                            info = rolle.processChosenOptionGetInfo(chosenOption);
                            showFrontendControl(statement, info);
                            break;

                        case ROLLE_INFO:
                            info = rolle.getInfo();
                            showFrontendControl(statement, info);
                            break;

                        case FRAKTION_CHOOSE_ONE:
                            dropdownOptions = fraktion.getDropdownOptionsFrontendControl();
                            chosenOption = showFrontendControl(statement, dropdownOptions);
                            fraktion.processChosenOption(chosenOption);
                            break;
                    }

                    switch (statement.id) {
                        case Wirt.STATEMENT_ID:
                            if (DropdownConstants.JA.equals(chosenOption)) {
                                game.freibier = true;
                            }
                            break;

                        case ProgrammStatements.SCHÜTZE_ID:
                            setSchütze();
                            break;

                        case Henker.STATEMENT_ID:
                            dropdownOptions = rolle.getDropdownOptionsFrontendControl();
                            chosenOption = showFrontendControl(statement, dropdownOptions);
                            rolle.processChosenOption(chosenOption);

                            if (rolle.besucht != null) {
                                while (Henker.pagecounter < Henker.numberOfPages) {
                                    if (FrontendControl.erzählerFrame.next) {
                                        henkerNächsteSeite();
                                    } else {
                                        henkerSeiteZurück();
                                    }
                                }

                                Henker henker = ((Henker) rolle);
                                info = henker.processChosenOptionsGetInfo(Henker.chosenHauptrolle.name, Henker.chosenBonusrolle.name);
                                showFrontendControl(statement, info);
                            }
                            break;

                        case Schreckenswolf.STATEMENT_ID:
                            Schreckenswolf schreckenswolf = (Schreckenswolf) rolle;
                            if (schreckenswolf != null && schreckenswolf.werwölfeKilledOnSchutz()) {
                                dropdownOptions = schreckenswolf.getDropdownOptionsFrontendControl();
                                chosenOption = showFrontendControl(statement, dropdownOptions);
                                schreckenswolf.processChosenOption(chosenOption);
                            } else {
                                showZeigekarte(statement, new Nicht_Aktiv());
                            }
                            break;

                        case Wölfin.STATEMENT_ID:
                            if (!"".equals(chosenOption)) {
                                wölfinKilled = true;
                                wölfinSpieler = game.findSpielerPerRolle(Wölfin.NAME);
                            }
                            break;

                        case SchattenpriesterFraktion.NEUER_SCHATTENPRIESTER:
                            chosenSpieler = game.findSpieler(chosenOptionLastStatement);
                            String neuerSchattenpriester = "";
                            erzählerInfoIconImagePath = ""; //TODO causes problem "1 Image could not be found at location: "
                            if (chosenSpieler != null) {
                                neuerSchattenpriester = chosenSpieler.name;

                                if (!chosenSpieler.hauptrolle.fraktion.equals(SchattenpriesterFraktion.NAME)) {
                                    erzählerInfoIconImagePath = Schattenkutte.IMAGE_PATH;
                                }
                            }
                            showListShowImage(statement, neuerSchattenpriester, SchattenpriesterFraktion.IMAGE_PATH, erzählerInfoIconImagePath);
                            break;

                        case Chemiker.NEUER_WERWOLF:
                            chosenSpieler = game.findSpieler(chosenOptionLastStatement);
                            String neuerWerwolf = "";
                            if (chosenSpieler != null) {
                                neuerWerwolf = chosenSpieler.name;
                            }

                            showListShowImage(statement, neuerWerwolf, Werwölfe.IMAGE_PATH); //TODO evalueren obs schönere lösung gibt
                            break;

                        case Irrlicht.STATEMENT_ID:
                            dropdownOptions = rolle.getDropdownOptionsFrontendControl();
                            showFrontendControl(statement, dropdownOptions);
                            break;

                        case Irrlicht.INFO:
                            info = Irrlicht.processFlackerndeIrrlichter(FrontendControl.getFlackerndeIrrlichter());
                            showFrontendControl(statement, info);
                            break;

                        case Analytiker.STATEMENT_ID:
                            Spieler analytikerSpieler = game.findSpielerPerRolle(rolle.name);

                            List<String> spielerOrNonWithoutAnalytiker = ListHelper.cloneList(spielerOrNon);
                            if (analytikerSpieler != null) {
                                spielerOrNonWithoutAnalytiker.remove(analytikerSpieler.name);
                            }
                            showDropdownPage(statement, spielerOrNonWithoutAnalytiker, spielerOrNonWithoutAnalytiker);

                            Spieler chosenSpieler1 = game.findSpieler(FrontendControl.erzählerFrame.chosenOption1);
                            Spieler chosenSpieler2 = game.findSpieler(FrontendControl.erzählerFrame.chosenOption2);

                            if (chosenSpieler1 != null && chosenSpieler2 != null) {
                                if (((Analytiker) rolle).showTarnumhang(chosenSpieler1, chosenSpieler2)) {
                                    showZeigekarte(statement, new Tarnumhang_BonusrollenType());
                                } else {
                                    String answer = ((Analytiker) rolle).analysiere(chosenSpieler1, chosenSpieler2);
                                    showList(statement, answer);//TODO generisch machen
                                }
                            }
                            break;

                        case Wahrsager.STATEMENT_ID:
                            Spieler wahrsagerSpieler2 = game.findSpielerPerRolle(Wahrsager.NAME);
                            Spieler deadWahrsagerSpieler = game.findSpielerOrDeadPerRolle(Wahrsager.NAME);
                            if (wahrsagerSpieler2 != null) {
                                Wahrsager wahrsager = (Wahrsager) deadWahrsagerSpieler.bonusrolle; //TODO Rolle rolle ?
                                ArrayList<String> rewardInformation = new ArrayList<>();
                                if (wahrsager.guessedRight() && !game.secondNight) {
                                    statement.title = Wahrsager.REWARD_TITLE;
                                    rewardInformation = wahrsager.rewardInformation();
                                }
                                FrontendControl dropdownShowReward = wahrsager.getDropdownOptionsFrontendControl();
                                dropdownShowReward.displayedStrings = rewardInformation;
                                chosenOption = showFrontendControl(statement, dropdownShowReward);
                                wahrsager.tipp = Fraktion.findFraktion(chosenOption);
                            }
                            break;

                        case Konditor.STATEMENT_ID:
                        case Konditorlehrling.STATEMENT_ID:
                            //TODO evaluieren ob Page angezeigt werden soll wenn gibtEsTorte();
                            if (opfer.size() == 0) {
                                if (gibtEsTorte()) {
                                    Torte.torte = true;

                                    dropdownOptions = rolle.getDropdownOptionsFrontendControl();
                                    chosenOption = showKonditorDropdownPage(statement, dropdownOptions);
                                    rolle.processChosenOption(chosenOption);

                                    Torte.gut = chosenOption.equals(DropdownConstants.GUT.name);
                                } else {
                                    Torte.torte = false;
                                    Torte.gut = false;
                                }
                            }
                            break;

                        case IndieStatements.OPFER_ID:
                            setOpfer();

                            ArrayList<String> opferDerNacht = new ArrayList<>();

                            for (Opfer currentOpfer : opfer) {
                                if (!opferDerNacht.contains(currentOpfer.spieler.name)) {
                                    opferDerNacht.add(currentOpfer.spieler.name);
                                }
                            }

                            FrontendControl.erzählerListPage(statement, IndieStatements.OPFER_TITLE, opferDerNacht);
                            for (String opfer : opferDerNacht) {
                                FrontendControl.spielerAnnounceOpferPage(game.findSpieler(opfer));
                                waitForAnswer();
                            }

                            checkVictory();
                            break;

                        case Schreckenswolf.VERSTUMMT:
                            if (beschworenerSpieler != null) {
                                FrontendControl.erzählerListPage(statement, beschworenerSpieler.name);
                                FrontendControl.spielerIconPicturePage(beschworenerSpieler.name, new Verstummt().imagePath);

                                waitForAnswer();
                            }
                            break;

                        case ProgrammStatements.TORTE_ID:
                            if (Torte.torte) {
                                FrontendControl.erzählerTortenPage();
                                FrontendControl.showZeigekarteOnSpielerScreen(new Torten_Zeigekarte());

                                waitForAnswer();
                            }
                            break;
                    }

                    chosenOptionLastStatement = chosenOption;

                    if (game.freibier) {
                        break;
                    }
                }

                statement.alreadyOver = true;
            }
        }

        cleanUpNight();

        PhaseManager.nextPhase();
    }

    public void beginNight() {
        for (Spieler currentSpieler : game.spieler) {
            String fraktionSpieler = currentSpieler.hauptrolle.fraktion.name;

            currentSpieler.ressurectable = !fraktionSpieler.equals(Vampire.NAME);
        }

        angriffe = new ArrayList<>();
        opfer = new ArrayList<>();

        for (Hauptrolle currentHauptrolle : game.hauptrollen) {
            currentHauptrolle.besuchtLastNight = currentHauptrolle.besucht;
            currentHauptrolle.besucht = null;
        }

        for (Bonusrolle currentBonusrolle : game.bonusrollen) {
            currentBonusrolle.besuchtLastNight = currentBonusrolle.besucht;
            currentBonusrolle.besucht = null;

            if (currentBonusrolle.equals(Analytiker.NAME)) {
                ((Analytiker) currentBonusrolle).besuchtAnalysieren = null;
            }
        }

        if (Rolle.rolleLebend(Prostituierte.NAME)) {
            Spieler prostituierte = game.findSpielerPerRolle(Prostituierte.NAME);
            Prostituierte.host = prostituierte;
        }

        for (Spieler currentSpieler : game.spieler) {
            Hauptrolle hauptrolleSpieler = currentSpieler.hauptrolle;

            if (hauptrolleSpieler.equals(Schattenpriester.NAME)) {
                if (((Schattenpriester) hauptrolleSpieler).neuster) {
                    currentSpieler.geschützt = true;
                    ((Schattenpriester) hauptrolleSpieler).neuster = false;
                }
            }
        }

        if (Torte.torte) {
            for (Spieler currentSpieler : Torte.tortenEsser) {
                if (Torte.gut) {
                    currentSpieler.geschützt = true;
                } else {
                    currentSpieler.aktiv = false;
                }
            }
        }

        Torte.torte = false;

        GrafVladimir.verschleierterSpieler = null;
        getarnterSpieler = null;
        gefälschterSpieler = null;

        Henker.pagecounter = 0;

        FrontendControl.resetFlackerndeIrrlichter();
    }

    private void refreshStatementStates() {
        for (Statement statement : statements) {
            if (!statement.alreadyOver) {
                statement.refreshState();
            }
        }
    }

    public void setSchütze() {
        for (Spieler currentSpieler : game.spieler) {
            String bonusrolleCurrentSpieler = currentSpieler.bonusrolle.name;

            if (bonusrolleCurrentSpieler.equals(SchwarzeSeele.NAME)) {
                currentSpieler.geschützt = true;
            }
        }
    }

    public void setOpfer() {
        checkLiebespaar();
        killOpfer();
    }

    public void setSpielerAwake(Statement statement) {
        spielerAwake.clear();
        if (statement.dependency instanceof StatementDependencyFraktion) {
            StatementDependencyFraktion statementDependencyFraktion = (StatementDependencyFraktion) statement.dependency;
            spielerAwake.addAll(Fraktion.getFraktionsMembers(statementDependencyFraktion.fraktion.name));
        } else if (statement.dependency instanceof StatementDependencyRolle) {
            StatementDependencyRolle statementDependencyRolle = (StatementDependencyRolle) statement.dependency;
            spielerAwake.add(game.findSpielerPerRolle(statementDependencyRolle.rolle.name));
        }
    }

    private void cleanUpNight() {
        game.secondNight = false;

        for (Spieler currentSpieler : game.spieler) {
            currentSpieler.aktiv = true;
            currentSpieler.geschützt = false;
            currentSpieler.ressurectable = true;
        }
    }

    public void killOpfer() {
        for (Opfer currentOpfer : opfer) {
            if (Rolle.rolleLebend(Blutwolf.NAME)) {
                if (currentOpfer.fraktionsTäter && currentOpfer.täterFraktion.equals(Werwölfe.NAME)) {
                    Blutwolf.deadStacks++;
                    if (Blutwolf.deadStacks >= 2) {
                        Blutwolf.deadly = true;
                    }
                }
            }

            game.killSpieler(currentOpfer.spieler);
        }
    }

    public void checkLiebespaar() {
        boolean spieler1Lebend = true;
        boolean spieler2Lebend = true;

        Liebespaar liebespaar = game.liebespaar;

        if (liebespaar != null && liebespaar.spieler1 != null && liebespaar.spieler2 != null) {

            for (Opfer currentOpfer : opfer) {
                if (currentOpfer.spieler.equals(liebespaar.spieler1)) {
                    spieler1Lebend = false;
                }
                if (currentOpfer.spieler.equals(liebespaar.spieler2)) {
                    spieler2Lebend = false;
                }
            }

            if (spieler1Lebend && !spieler2Lebend) {
                Selbstmord.execute(liebespaar.spieler1);
            }

            if (!spieler1Lebend && spieler2Lebend) {
                Selbstmord.execute(liebespaar.spieler2);
            }
        }
    }

    public boolean gibtEsTorte() {
        if (Torte.gut) {
            return false;
        }

        if (Rolle.rolleLebend(Konditor.NAME) && !Opfer.isOpferPerRolle(Konditor.NAME) && Rolle.rolleAktiv(Konditor.NAME)) {
            return true;
        }

        if (Rolle.rolleLebend(Konditorlehrling.NAME) && !Opfer.isOpferPerRolle(Konditorlehrling.NAME) && Rolle.rolleAktiv(Konditorlehrling.NAME)) {
            return true;
        }

        if (Sammler.isSammlerRolle(Konditor.NAME) || Sammler.isSammlerRolle(Konditorlehrling.NAME)) {
            if (Rolle.rolleLebend(Sammler.NAME) && !Opfer.isOpferPerRolle(Sammler.NAME) && Rolle.rolleAktiv(Sammler.NAME)) { //TODO kann man durch nur rolleAktiv ersetzen?
                return true;
            }
        }

        return false;
    }

    private void checkVictory() {
        Winner winner = game.checkVictory();

        if (winner != Winner.NO_WINNER) {
            showEndScreenPage(winner);
        }
    }

    public String showFrontendControl(Statement statement, FrontendControl frontendControl) {
        if (frontendControl.title == null) {
            frontendControl.title = statement.title;
        }

        switch (statement.state) {
            case NORMAL:
                switch (frontendControl.typeOfContent) {
                    case TITLE:
                        showTitle(statement, frontendControl.title);
                        break;

                    case DROPDOWN:
                        showDropdown(statement, frontendControl.title, frontendControl.dropdownStrings);
                        return FrontendControl.erzählerFrame.chosenOption1;

                    case DROPDOWN_LIST:
                        showDropdownList(statement, frontendControl.title, frontendControl.dropdownStrings, frontendControl.hatZurückButton);
                        return FrontendControl.erzählerFrame.chosenOption1;

                    case DROPDOWN_SEPARATED_LIST:
                        showDropdownSeperatedList(statement, frontendControl.title, frontendControl.dropdownStrings, frontendControl.displayedStrings, frontendControl.hatZurückButton);
                        return FrontendControl.erzählerFrame.chosenOption1;

                    case DROPDOWN_IMAGE:
                        showDropdownShowImage(statement, frontendControl.title, frontendControl.dropdownStrings, frontendControl.imagePath);
                        return FrontendControl.erzählerFrame.chosenOption1;

                    case LIST:
                        showList(statement, frontendControl.title, frontendControl.dropdownStrings, frontendControl.hatZurückButton);
                        break;

                    case IMAGE:
                        showImage(statement, frontendControl.title, frontendControl.imagePath);
                        break;

                    case CARD:
                        showCard(statement, frontendControl.title, frontendControl.imagePath);
                        break;

                    case LIST_IMAGE:
                        showListShowImage(statement, frontendControl.title, frontendControl.dropdownStrings, frontendControl.imagePath);
                        break;

                    case SCHNÜFFLER_INFO:
                        showSchnüfflerInfo(statement, frontendControl.informationen);
                        break;

                    case IRRLICHT_DROPDOWN:
                        showIrrlichtDropdown(statement, frontendControl.title, frontendControl.dropdownStrings);
                        break;

                    case TWO_IMAGES:
                        showTwoImages(statement, frontendControl.title, frontendControl.imagePath, frontendControl.imagePath2, frontendControl.displayedStrings);
                        break;

                }
                break;

            case AUFGEBRAUCHT:
                showAufgebrauchtPages(statement, frontendControl);
                break;

            case DEAKTIV:
                showDeaktivPages(statement, frontendControl);
                break;

            case DEAD:
                showTotPages(statement, frontendControl);
                break;

            case NOT_IN_GAME:
                showAusDemSpielPages(statement, frontendControl);
                break;
        }

        return null;
    }

    private void showTwoImages(Statement statement, String title, String imagePath, String imagePath2, List<String> displayedStrings) {
        FrontendControl.erzählerListPage(statement, title, displayedStrings);
        FrontendControl.spielerTwoImagePage(title, imagePath, imagePath2);

        waitForAnswer();
    }

    private void showIrrlichtDropdown(Statement statement, String title, List<String> dropdownStrings) {
        FrontendControl.irrlichtDropdownPage(statement, dropdownStrings);
        FrontendControl.spielerTitlePage(title);

        waitForAnswer();
    }

    private void showDropdownShowImage(Statement statement, String title, List<String> strings, String imagePath) {
        FrontendControl.erzählerDropdownPage(statement, strings);
        FrontendControl.spielerDropdownMirrorImagePage(title, imagePath);

        waitForAnswer();
    }

    public void showDropdownPage(Statement statement, List<String> dropdownOptions1, List<String> dropdownOptions2) {
        switch (statement.state) {
            case NORMAL:
                FrontendControl.erzählerDropdownPage(statement, dropdownOptions1, dropdownOptions2);
                FrontendControl.spielerDropdownPage(statement.title, 2);
                break;

            case DEAKTIV:
                Deaktiviert deaktiviert = new Deaktiviert();
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), deaktiviert.imagePath);
                FrontendControl.showZeigekarteOnSpielerScreen(deaktiviert);
                break;

            case DEAD:
                Tot tot = new Tot();
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), tot.imagePath);
                FrontendControl.showZeigekarteOnSpielerScreen(tot);
                break;

            case NOT_IN_GAME:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), new AusDemSpiel().imagePath);
                FrontendControl.spielerDropdownPage(statement.title, 2);
                break;
        }

        waitForAnswer();
    }

    public String showKonditorDropdownPage(Statement statement, FrontendControl frontendControl) {
        /*if (Rolle.rolleLebend(Konditor.NAME) || Rolle.rolleLebend(Konditorlehrling.NAME)) {
            if (!Opfer.isOpferPerRolle(Konditor.NAME) || !Opfer.isOpferPerRolle(Konditorlehrling.NAME)) {
                if (Rolle.rolleAktiv(Konditor.NAME) || Rolle.rolleAktiv(Konditorlehrling.NAME)) {*/
        FrontendControl.erzählerDropdownPage(statement, frontendControl.dropdownStrings);
        FrontendControl.spielerDropdownPage(statement.title, 1);
                /*} else {
                    FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                    FrontendControl.spielerIconPicturePage(DEAKTIVIERT_TITLE, ResourcePath.DEAKTIVIERT);
                }
            } else {
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.TOT);
                FrontendControl.spielerIconPicturePage(TOT_TITLE, ResourcePath.TOT);
            }
        } else {
            FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
            FrontendControl.spielerDropdownPage(statement.title, 1);
        }*/

        waitForAnswer();

        return FrontendControl.erzählerFrame.chosenOption1;
    }

    private void showEndScreenPage(Winner winner) {
        FrontendControl.erzählerEndScreenPage(winner);
        FrontendControl.spielerEndScreenPage(winner);

        waitForAnswer();
    }

    private void showZeigekarte(Statement statement, Zeigekarte zeigekarte) {
        FrontendControl.erzählerIconPicturePage(statement, zeigekarte.imagePath);
        FrontendControl.spielerIconPicturePage(zeigekarte.title, zeigekarte.imagePath);

        waitForAnswer();
    }

    //TODO Cases die sowieso gleich aussehen zusammenfassen
    public void showAufgebrauchtPages(Statement statement, FrontendControl frontendControl) {
        Zeigekarte aufgebraucht = new Aufgebraucht();

        switch (frontendControl.typeOfContent) {
            case DROPDOWN:
            case DROPDOWN_LIST:
            case DROPDOWN_SEPARATED_LIST:
            case DROPDOWN_IMAGE:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), aufgebraucht.imagePath);
                break;

            case LIST:
            case LIST_IMAGE:
                FrontendControl.erzählerListPage(statement, getEmptyStringList(), aufgebraucht.imagePath);
                break;

            case TITLE:
            case IMAGE:
            case CARD:
            case SCHNÜFFLER_INFO:
            default:
                FrontendControl.erzählerIconPicturePage(statement, aufgebraucht.imagePath);
                break;
        }

        FrontendControl.showZeigekarteOnSpielerScreen(aufgebraucht);
        waitForAnswer();
    }

    public void showDeaktivPages(Statement statement, FrontendControl frontendControl) {
        Zeigekarte deaktiviert = new Deaktiviert();

        switch (frontendControl.typeOfContent) {
            case DROPDOWN:
            case DROPDOWN_LIST:
            case DROPDOWN_SEPARATED_LIST:
            case DROPDOWN_IMAGE:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), deaktiviert.imagePath);
                break;

            case LIST:
            case LIST_IMAGE:
                FrontendControl.erzählerListPage(statement, getEmptyStringList(), deaktiviert.imagePath);
                break;

            case TITLE:
            case IMAGE:
            case CARD:
            case SCHNÜFFLER_INFO:
            default:
                FrontendControl.erzählerIconPicturePage(statement, deaktiviert.imagePath);
                break;
        }

        FrontendControl.showZeigekarteOnSpielerScreen(deaktiviert);
        waitForAnswer();
    }

    public void showTotPages(Statement statement, FrontendControl frontendControl) {
        Zeigekarte tot = new Tot();

        switch (frontendControl.typeOfContent) {
            case DROPDOWN:
            case DROPDOWN_LIST:
            case DROPDOWN_SEPARATED_LIST:
            case DROPDOWN_IMAGE:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), tot.imagePath);
                break;
            case LIST:
            case LIST_IMAGE:
                FrontendControl.erzählerListPage(statement, getEmptyStringList(), tot.imagePath);
                break;

            case TITLE:
            case IMAGE:
            case CARD:
            case SCHNÜFFLER_INFO:
            default:
                FrontendControl.erzählerIconPicturePage(statement, tot.imagePath);
                break;
        }

        FrontendControl.showZeigekarteOnSpielerScreen(tot);
        waitForAnswer();
    }

    public void showAusDemSpielPages(Statement statement, FrontendControl frontendControl) {
        Zeigekarte ausDemSpiel = new AusDemSpiel();

        switch (frontendControl.typeOfContent) {
            case DROPDOWN:
            case DROPDOWN_LIST:
            case DROPDOWN_SEPARATED_LIST:
            case DROPDOWN_IMAGE:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), ausDemSpiel.imagePath);
                FrontendControl.spielerDropdownPage(statement.title, 1);
                break;

            case LIST:
            case LIST_IMAGE:
                FrontendControl.erzählerListPage(statement, getEmptyStringList(), ausDemSpiel.imagePath);
                FrontendControl.spielerListPage(statement.title, getEmptyStringList());
                break;

            case TITLE:
            case IMAGE:
            case CARD:
            case SCHNÜFFLER_INFO:
            default:
                FrontendControl.erzählerIconPicturePage(statement, ausDemSpiel.imagePath);
                FrontendControl.spielerIconPicturePage(statement.title, "");
                break;
        }

        waitForAnswer();
    }

    public void showTitle(Statement statement) {
        showTitle(statement, statement.title);
    }

    public void showTitle(Statement statement, String title) {
        FrontendControl.erzählerDefaultNightPage(statement);
        FrontendControl.spielerTitlePage(title);

        waitForAnswer();
    }

    public void showDropdown(Statement statement, String title, List<String> dropdownOptions) {
        FrontendControl.erzählerDropdownPage(statement, dropdownOptions);
        FrontendControl.spielerDropdownPage(title, 1);

        waitForAnswer();
    }

    public void showDropdownList(Statement statement, String title, List<String> strings, boolean hatZurückButton) {
        FrontendControl.erzählerDropdownPage(statement, strings, hatZurückButton);
        FrontendControl.spielerDropdownListPage(title, strings);

        waitForAnswer();
    }

    public void showDropdownSeperatedList(Statement statement, String title, List<String> dropdownStrings, List<String> listStrings, boolean hatZurückButton) {
        FrontendControl.erzählerDropdownPage(statement, dropdownStrings, hatZurückButton);
        FrontendControl.spielerDropdownListPage(title, listStrings);

        waitForAnswer();
    }

    public void showList(Statement statement, String string) {
        ArrayList<String> list = new ArrayList<>();
        list.add(string);
        showList(statement, list);
    }

    public void showList(Statement statement, List<String> strings) {
        showList(statement, statement.title, strings, false);
    }

    public void showList(Statement statement, String title, List<String> strings, boolean hatZurückButton) {
        FrontendControl.erzählerListPage(statement, title, strings, hatZurückButton);
        FrontendControl.spielerListPage(title, strings);

        waitForAnswer();
    }

    public void showImage(Statement statement, String imagePath) {
        showImage(statement, statement.title, imagePath);
    }

    public void showImage(Statement statement, String title, String imagePath) {
        FrontendControl.erzählerIconPicturePage(statement, title, imagePath);
        FrontendControl.spielerIconPicturePage(title, imagePath);

        waitForAnswer();
    }

    public void showCard(Statement statement, String title, String imagePath) {
        FrontendControl.erzählerCardPicturePage(statement, title, imagePath);
        FrontendControl.spielerCardPicturePage(title, imagePath);

        waitForAnswer();
    }

    public void showListShowImage(Statement statement, String string, String spielerImagePath) {
        ArrayList<String> list = new ArrayList<>();
        list.add(string);
        showListShowImage(statement, statement.title, list, spielerImagePath);
    }

    public void showListShowImage(Statement statement, String string, String spielerImagePath, String erzählerImagePath) {
        ArrayList<String> list = new ArrayList<>();
        list.add(string);
        showListShowImage(statement, statement.title, list, spielerImagePath, erzählerImagePath);
    }

    public void showListShowImage(Statement statement, String title, List<String> strings, String spielerImagePath) {
        FrontendControl.erzählerListPage(statement, strings);
        FrontendControl.spielerIconPicturePage(title, spielerImagePath);

        waitForAnswer();
    }

    public void showListShowImage(Statement statement, String title, List<String> strings, String spielerImagePath, String erzählerImagePath) {
        FrontendControl.erzählerListPage(statement, strings, erzählerImagePath);
        FrontendControl.spielerIconPicturePage(title, spielerImagePath);

        waitForAnswer();
    }

    public void showSchnüfflerInfo(Statement statement, List<SchnüfflerInformation> informationen) {
        FrontendControl.erzählerDefaultNightPage(statement);
        FrontendControl.spielerSchnüfflerInfoPage(informationen);

        waitForAnswer();
    }

    public static List<String> getEmptyStringList() {
        ArrayList<String> emptyContent = new ArrayList<>();
        emptyContent.add("");
        return emptyContent;
    }

    public void waitForAnswer() {
        FrontendControl.refreshÜbersichtsFrame();
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void henkerNächsteSeite() {
        Henker henker = (Henker) game.findHauptrolle(Henker.NAME);
        Henker.pagecounter++;
        if (Henker.pagecounter < Henker.numberOfPages) {
            FrontendControl frontendControl = henker.getPage(FrontendControl.erzählerFrame.chosenOption1);
            showHenkerPage(frontendControl);
        }
    }

    public void henkerSeiteZurück() {
        Henker henker = (Henker) game.findHauptrolle(Henker.NAME);
        if (Henker.pagecounter > 0) {
            Henker.pagecounter--;
            FrontendControl frontendControl = henker.getPage();
            showHenkerPage(frontendControl);
        }
    }

    public void showHenkerPage(FrontendControl frontendControl) {
        Statement henkerStatement = statements.stream()
                .filter(statement -> statement.id.equals(Henker.STATEMENT_ID))
                .findAny().orElse(null);

        showFrontendControl(henkerStatement, frontendControl);
    }
}
