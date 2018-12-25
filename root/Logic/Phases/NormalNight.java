package root.Logic.Phases;

import root.Frontend.FrontendControl;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.KillLogic.Angriff;
import root.Logic.KillLogic.Opfer;
import root.Logic.KillLogic.Selbstmord;
import root.Logic.Liebespaar;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Logic.Persona.Fraktionen.Vampire;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rolle;
import root.Logic.Persona.Rollen.Bonusrollen.*;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Logic.Persona.Rollen.Constants.SchnüfflerInformation;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.*;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Irrlicht;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Wirt;
import root.Logic.Persona.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Logic.Persona.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Blutwolf;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Chemiker;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Logic.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Logic.Phases.NightBuilding.NormalNightStatementBuilder;
import root.Logic.Phases.Statement.Constants.IndieStatements;
import root.Logic.Phases.Statement.Constants.ProgramStatements;
import root.Logic.Phases.Statement.Constants.StatementState;
import root.Logic.Phases.Statement.Statement;
import root.Logic.Phases.Statement.StatementDependency.StatementDependency;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyFraktion;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyRolle;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyStatement;
import root.Logic.Spieler;
import root.Logic.Torte;

import java.util.ArrayList;
import java.util.List;

public class NormalNight extends Thread {
    public static List<Statement> statements;
    public static Object lock;

    public static List<Angriff> angriffe = new ArrayList<>();
    public static List<Opfer> opfer = new ArrayList<>();

    public static List<Spieler> spielerAwake = new ArrayList<>();
    public static boolean wölfinKilled;
    public static Spieler wölfinSpieler;
    public static Spieler gefälschterSpieler;
    public static Spieler getarnterSpieler;
    public List<String> opferDerNacht;


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

            statements = NormalNightStatementBuilder.normalNightBuildStatements();

            beginNight();

            System.out.println(PhaseManager.nightCount + ". Nacht beginnt!");

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
                            if (Wirt.JA.equals(chosenOption)) {
                                Game.game.freibier = true;
                            }
                            break;

                        case ProgramStatements.SCHÜTZE_ID:
                            setSchütze();
                            break;

                        case Henker.STATEMENT_ID:
                            dropdownOptions = rolle.getDropdownOptionsFrontendControl();
                            chosenOption = showFrontendControl(statement, dropdownOptions);
                            rolle.processChosenOption(chosenOption);

                            while (Henker.pagecounter < Henker.numberOfPages) {
                                if (FrontendControl.erzählerFrame.next) {
                                    henkerNächsteSeite();
                                } else {
                                    henkerSeiteZurück();
                                }
                            }

                            if (rolle.besucht != null) {
                                Henker henker = ((Henker) rolle);
                                info = henker.processChosenOptionsGetInfo(Henker.chosenHauptrolle.name, Henker.chosenBonusrolle.name);
                                showFrontendControl(statement, info);
                            }
                            break;

                        case Wölfin.STATEMENT_ID:
                            if (!chosenOption.isEmpty()) {
                                wölfinKilled = true;
                                wölfinSpieler = Game.game.findSpielerPerRolle(Wölfin.ID);
                            }
                            break;

                        case SchattenpriesterFraktion.NEUER_SCHATTENPRIESTER:
                            chosenSpieler = Game.game.findSpieler(chosenOptionLastStatement);
                            List<String> neueSchattenpriester = new ArrayList<>();
                            erzählerInfoIconImagePath = ""; //TODO causes problem "1 Image could not be found at location: "
                            if (chosenSpieler != null) {
                                String neuerSchattenpriester = chosenSpieler.name;
                                neueSchattenpriester.add(neuerSchattenpriester);
                                if (SchattenpriesterFraktion.spielerToChangeCards != null) {
                                    neueSchattenpriester.add(SchattenpriesterFraktion.spielerToChangeCards.name);
                                    SchattenpriesterFraktion.spielerToChangeCards = null;
                                }

                                if (!chosenSpieler.hauptrolle.fraktion.equals(SchattenpriesterFraktion.ID)) {
                                    erzählerInfoIconImagePath = Schattenkutte.IMAGE_PATH;
                                }
                            }
                            showListShowImage(statement, neueSchattenpriester, SchattenpriesterFraktion.IMAGE_PATH, erzählerInfoIconImagePath);
                            break;

                        case Chemiker.NEUER_WERWOLF:
                            chosenSpieler = Game.game.findSpieler(chosenOptionLastStatement);
                            String neuerWerwolf = "";
                            if (chosenSpieler != null) {
                                neuerWerwolf = chosenSpieler.name;
                            }

                            showListShowImage(statement, neuerWerwolf, Chemiker.FRAKTION.imagePath);
                            break;

                        case Nachtfürst.TÖTEN_ID:
                            Nachtfürst nachtfürst = (Nachtfürst) rolle;
                            dropdownOptions = nachtfürst.getSecondDropdownOptionsFrontendControl();
                            chosenOption = showFrontendControl(statement, dropdownOptions);
                            nachtfürst.processSecondChosenOption(chosenOption);
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
                            Analytiker analytiker = (Analytiker) rolle;

                            DropdownOptions analytikerDropdownOptions = analytiker.getDropdownOptions();
                            showDropdownPage(statement, analytikerDropdownOptions, analytikerDropdownOptions);

                            Spieler chosenSpieler1 = Game.game.findSpieler(FrontendControl.erzählerFrame.chosenOption1);
                            Spieler chosenSpieler2 = Game.game.findSpieler(FrontendControl.erzählerFrame.chosenOption2);

                            if (chosenSpieler1 != null && chosenSpieler2 != null) {
                                if (analytiker.showTarnumhang(chosenSpieler1, chosenSpieler2)) {
                                    showZeigekarte(statement, new Tarnumhang_BonusrollenType());
                                } else {
                                    String answer = analytiker.analysiere(chosenSpieler1, chosenSpieler2);
                                    showList(statement, answer);
                                }
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

                                    Torte.gut = chosenOption.equals(Konditor.GUT);
                                } else {
                                    Torte.torte = false;
                                    Torte.gut = false;
                                }
                            }
                            break;

                        case IndieStatements.OPFER_ID:
                            setOpfer();

                            opferDerNacht = new ArrayList<>();

                            for (Opfer currentOpfer : opfer) {
                                if (!opferDerNacht.contains(currentOpfer.spieler.name)) {
                                    opferDerNacht.add(currentOpfer.spieler.name);
                                }
                            }

                            FrontendControl.erzählerListPage(statement, IndieStatements.OPFER_TITLE, opferDerNacht);
                            for (String opfer : opferDerNacht) {
                                FrontendControl.spielerAnnounceOpferPage(Game.game.findSpieler(opfer));
                                waitForAnswer();
                            }

                            checkVictory();
                            break;

                        case ProgramStatements.TORTE_ID:
                            if (Torte.torte) {
                                FrontendControl.erzählerTortenPage();
                                FrontendControl.showZeigekarteOnSpielerScreen(new Torten_Zeigekarte());

                                waitForAnswer();
                                Torte.setTortenEsser(FrontendControl.getTortenesser());
                            }
                            break;
                    }

                    chosenOptionLastStatement = chosenOption;

                    if (Game.game.freibier) {
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
        for (Spieler currentSpieler : Game.game.spieler) {
            currentSpieler.ressurectable = !currentSpieler.hauptrolle.fraktion.equals(Vampire.ID);
        }

        angriffe = new ArrayList<>();
        opfer = new ArrayList<>();

        for (Hauptrolle currentHauptrolle : Game.game.hauptrollen) {
            currentHauptrolle.besuchtLastNight = currentHauptrolle.besucht;
            currentHauptrolle.besucht = null;
        }

        for (Bonusrolle currentBonusrolle : Game.game.bonusrollen) {
            currentBonusrolle.besuchtLastNight = currentBonusrolle.besucht;
            currentBonusrolle.besucht = null;

            if (currentBonusrolle.equals(Analytiker.ID)) {
                ((Analytiker) currentBonusrolle).besuchtAnalysieren = null;
            }
        }

        if (Rolle.rolleLebend(Prostituierte.ID)) {
            Prostituierte.host = Game.game.findSpielerPerRolle(Prostituierte.ID);
        }

        for (Spieler currentSpieler : Game.game.spieler) {
            Hauptrolle hauptrolleSpieler = currentSpieler.hauptrolle;

            if (hauptrolleSpieler.equals(Schattenpriester.ID)) {
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
    }

    private void refreshStatementStates() {
        for (Statement statement : statements) {
            if (!statement.alreadyOver) {
                statement.refreshState();
            }
        }
    }

    public void setSchütze() {
        for (Spieler currentSpieler : Game.game.spieler) {
            if (currentSpieler.bonusrolle.equals(SchwarzeSeele.ID)) {
                currentSpieler.geschützt = true;
            }
        }

        setNachtfürstSchutz();
    }

    private void setNachtfürstSchutz() {
        Rolle rolle = Rolle.findRolle(Nachtfürst.ID);

        if (rolle != null) {
            Nachtfürst nachtfürst = (Nachtfürst) rolle;

            if (!nachtfürst.isTötendeFraktion() && nachtfürst.guessedRight) {
                Spieler nachtfürstSpieler = Game.game.findSpielerPerRolle(nachtfürst.id);
                if (nachtfürstSpieler != null) {
                    nachtfürstSpieler.geschützt = true;
                }
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
            spielerAwake.addAll(Fraktion.getFraktionsMembers(statementDependencyFraktion.fraktion.id));
        } else if (statement.dependency instanceof StatementDependencyRolle) {
            StatementDependencyRolle statementDependencyRolle = (StatementDependencyRolle) statement.dependency;
            spielerAwake.add(Game.game.findSpielerPerRolle(statementDependencyRolle.rolle.id));
        }
    }

    private void cleanUpNight() {
        checkNachtfürstGuess();

        for (Spieler currentSpieler : Game.game.spieler) {
            currentSpieler.aktiv = true;
            currentSpieler.geschützt = false;
            currentSpieler.ressurectable = true;
        }
    }

    private void checkNachtfürstGuess() {
        Rolle rolle = Rolle.findRolle(Nachtfürst.ID);

        if (rolle != null) {
            Nachtfürst nachtfürst = (Nachtfürst) rolle;

            int anzahlOpferDerNacht = getAnzahlOpferDerNacht();
            nachtfürst.checkGuess(anzahlOpferDerNacht);
        }
    }

    private int getAnzahlOpferDerNacht() {
        return opferDerNacht.size();
    }

    public void killOpfer() {
        for (Opfer currentOpfer : opfer) {
            if (Rolle.rolleLebend(Blutwolf.ID)) {
                if (currentOpfer.fraktionsTäter && currentOpfer.täterFraktion.equals(Werwölfe.ID)) {
                    Blutwolf.deadStacks++;
                    if (Blutwolf.deadStacks >= 2) {
                        Blutwolf.deadly = true;
                    }
                }
            }

            Game.game.killSpieler(currentOpfer.spieler);
        }
    }

    public void checkLiebespaar() {
        boolean spieler1Lebend = true;
        boolean spieler2Lebend = true;

        Liebespaar liebespaar = Game.game.liebespaar;

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

        if (Rolle.rolleLebend(Konditor.ID) && !Opfer.isOpferPerRolle(Konditor.ID) && Rolle.rolleAktiv(Konditor.ID)) {
            return true;
        }

        if (Rolle.rolleLebend(Konditorlehrling.ID) && !Opfer.isOpferPerRolle(Konditorlehrling.ID) && Rolle.rolleAktiv(Konditorlehrling.ID)) {
            return true;
        }

        return false;
    }

    private void checkVictory() {
        Winner winner = Game.game.checkVictory();

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
                        showDropdown(statement, frontendControl.title, frontendControl.dropdownOptions);
                        return FrontendControl.erzählerFrame.chosenOption1;

                    case DROPDOWN_LIST:
                        showDropdownList(statement, frontendControl.title, frontendControl.dropdownOptions, frontendControl.hatZurückButton);
                        return FrontendControl.erzählerFrame.chosenOption1;

                    case DROPDOWN_SEPARATED_LIST:
                        showDropdownSeperatedList(statement, frontendControl.title, frontendControl.dropdownOptions, frontendControl.displayedStrings, frontendControl.hatZurückButton);
                        return FrontendControl.erzählerFrame.chosenOption1;

                    case DROPDOWN_IMAGE:
                        showDropdownShowImage(statement, frontendControl.title, frontendControl.dropdownOptions, frontendControl.imagePath);
                        return FrontendControl.erzählerFrame.chosenOption1;

                    case LIST:
                        showList(statement, frontendControl.title, frontendControl.displayedStrings, frontendControl.hatZurückButton);
                        break;

                    case IMAGE:
                        showImage(statement, frontendControl.title, frontendControl.imagePath);
                        break;

                    case CARD:
                        showCard(statement, frontendControl.title, frontendControl.imagePath);
                        break;

                    case LIST_IMAGE:
                        showListShowImage(statement, frontendControl.title, frontendControl.displayedStrings, frontendControl.imagePath);
                        break;

                    case SCHNÜFFLER_INFO:
                        showSchnüfflerInfo(statement, frontendControl.informationen);
                        break;

                    case IRRLICHT_DROPDOWN:
                        showIrrlichtDropdown(statement, frontendControl.title, frontendControl.dropdownOptions);
                        break;

                    case TWO_IMAGES:
                        showTwoImages(statement, frontendControl.title, frontendControl.imagePath, frontendControl.imagePath2, frontendControl.displayedStrings, frontendControl.hatZurückButton);
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

    private void showTwoImages(Statement statement, String title, String imagePath, String imagePath2, List<String> displayedStrings, boolean hatZurückButton) {
        FrontendControl.erzählerListPage(statement, title, displayedStrings, hatZurückButton);
        FrontendControl.spielerTwoImagePage(title, imagePath, imagePath2);

        waitForAnswer();
    }

    private void showIrrlichtDropdown(Statement statement, String title, DropdownOptions dropdownStrings) {
        FrontendControl.irrlichtDropdownPage(statement, dropdownStrings);
        FrontendControl.spielerTitlePage(title);

        waitForAnswer();
    }

    private void showDropdownShowImage(Statement statement, String title, DropdownOptions strings, String imagePath) {
        FrontendControl.erzählerDropdownPage(statement, strings);
        FrontendControl.spielerDropdownMirrorImagePage(title, imagePath);

        waitForAnswer();
    }

    public void showDropdownPage(Statement statement, DropdownOptions dropdownOptions1, DropdownOptions dropdownOptions2) {
        switch (statement.state) {
            case NORMAL:
                FrontendControl.erzählerDropdownPage(statement, dropdownOptions1, dropdownOptions2);
                FrontendControl.spielerDropdownPage(statement.title, 2);
                break;

            case DEAKTIV:
                Deaktiviert deaktiviert = new Deaktiviert();
                FrontendControl.erzählerDropdownPage(statement, getEmptyDropdownOptions(), getEmptyDropdownOptions(), deaktiviert.imagePath);
                FrontendControl.showZeigekarteOnSpielerScreen(deaktiviert);
                break;

            case DEAD:
                Tot tot = new Tot();
                FrontendControl.erzählerDropdownPage(statement, getEmptyDropdownOptions(), getEmptyDropdownOptions(), tot.imagePath);
                FrontendControl.showZeigekarteOnSpielerScreen(tot);
                break;

            case NOT_IN_GAME:
                FrontendControl.erzählerDropdownPage(statement, getEmptyDropdownOptions(), getEmptyDropdownOptions(), new AusDemSpiel().imagePath);
                FrontendControl.spielerDropdownPage(statement.title, 2);
                break;
        }

        waitForAnswer();
    }

    public String showKonditorDropdownPage(Statement statement, FrontendControl frontendControl) {
        FrontendControl.erzählerDropdownPage(statement, frontendControl.dropdownOptions);
        FrontendControl.spielerDropdownPage(statement.title, 1);

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
                FrontendControl.erzählerDropdownPage(statement, getEmptyDropdownOptions(), aufgebraucht.imagePath);
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
                FrontendControl.erzählerDropdownPage(statement, getEmptyDropdownOptions(), deaktiviert.imagePath);
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
                FrontendControl.erzählerDropdownPage(statement, getEmptyDropdownOptions(), tot.imagePath);
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
                FrontendControl.erzählerDropdownPage(statement, getEmptyDropdownOptions(), ausDemSpiel.imagePath);
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

    public void showDropdown(Statement statement, String title, DropdownOptions dropdownOptions) {
        FrontendControl.erzählerDropdownPage(statement, dropdownOptions);
        FrontendControl.spielerDropdownPage(title, 1);

        waitForAnswer();
    }

    public void showDropdownList(Statement statement, String title, DropdownOptions dropdownOptions, boolean hatZurückButton) {
        FrontendControl.erzählerDropdownPage(statement, dropdownOptions, hatZurückButton);
        FrontendControl.spielerDropdownListPage(title, dropdownOptions);

        waitForAnswer();
    }

    public void showDropdownSeperatedList(Statement statement, String title, DropdownOptions dropdownStrings, List<String> listStrings, boolean hatZurückButton) {
        FrontendControl.erzählerDropdownPage(statement, dropdownStrings, hatZurückButton);
        FrontendControl.spielerDropdownListPage(title, listStrings);

        waitForAnswer();
    }

    public void showList(Statement statement, String string) {
        List<String> list = new ArrayList<>();
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
        List<String> list = new ArrayList<>();
        list.add(string);
        showListShowImage(statement, statement.title, list, spielerImagePath);
    }

    public void showListShowImage(Statement statement, List<String> strings, String spielerImagePath, String erzählerImagePath) {
        showListShowImage(statement, statement.title, strings, spielerImagePath, erzählerImagePath);
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
        List<String> emptyContent = new ArrayList<>();
        emptyContent.add("");
        return emptyContent;
    }

    public static DropdownOptions getEmptyDropdownOptions() {
        return new DropdownOptions(getEmptyStringList());
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
        Henker henker = (Henker) Game.game.findHauptrolle(Henker.ID);
        Henker.pagecounter++;
        if (Henker.pagecounter < Henker.numberOfPages) {
            FrontendControl frontendControl = henker.getPage(FrontendControl.erzählerFrame.chosenOption1);
            showHenkerPage(frontendControl);
        }
    }

    public void henkerSeiteZurück() {
        Henker henker = (Henker) Game.game.findHauptrolle(Henker.ID);
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
