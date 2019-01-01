package root.Logic.Phases;

import root.Controller.FrontendControl;
import root.Controller.FrontendObject.FrontendObject;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.KillLogic.Angriff;
import root.Logic.KillLogic.Opfer;
import root.Logic.KillLogic.Selbstmord;
import root.Logic.Liebespaar;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Logic.Persona.Fraktionen.Werwölfe;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rolle;
import root.Logic.Persona.Rollen.Bonusrollen.*;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Torten_Zeigekarte;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Irrlicht;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Wirt;
import root.Logic.Persona.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Blutwolf;
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
import java.util.stream.Collectors;

public class NormalNight extends Thread {
    public static List<Statement> statements;
    public static Object lock;

    public static List<Angriff> angriffe = new ArrayList<>();
    public static List<Opfer> opfer = new ArrayList<>();
    public List<String> opferDerNacht;

    public static List<Spieler> spielerAwake = new ArrayList<>();

    public void run() {
        lock = new Object();
        synchronized (lock) {
            FrontendControl.lock = lock;

            FrontendObject dropdownOptions;
            FrontendObject info;

            String chosenOption;
            String chosenOptionLastStatement = null;
            Spieler chosenSpieler;

            Rolle rolle = null;
            Fraktion fraktion = null;

            String erzählerInfoIconImagePath;

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
                            FrontendControl.showTitle(statement);
                            break;

                        case ROLLE_CHOOSE_ONE:
                            dropdownOptions = rolle.getFrontendObject();
                            chosenOption = FrontendControl.showFrontendObject(statement, dropdownOptions);
                            rolle.processChosenOption(chosenOption);
                            break;

                        case ROLLE_CHOOSE_ONE_INFO:
                            dropdownOptions = rolle.getFrontendObject();
                            chosenOption = FrontendControl.showFrontendObject(statement, dropdownOptions);
                            info = rolle.processChosenOptionGetInfo(chosenOption);
                            FrontendControl.showFrontendObject(statement, info);
                            break;

                        case ROLLE_INFO:
                            info = rolle.getInfo();
                            FrontendControl.showFrontendObject(statement, info);
                            break;

                        case FRAKTION_CHOOSE_ONE:
                            dropdownOptions = fraktion.getFrontendObject();
                            chosenOption = FrontendControl.showFrontendObject(statement, dropdownOptions);
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
                            dropdownOptions = rolle.getFrontendObject();
                            chosenOption = FrontendControl.showFrontendObject(statement, dropdownOptions);
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
                                FrontendControl.showFrontendObject(statement, info);
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
                            FrontendControl.showListShowImage(statement, neueSchattenpriester, SchattenpriesterFraktion.IMAGE_PATH, erzählerInfoIconImagePath);
                            break;

                        case Nachtfürst.TÖTEN_ID:
                            Nachtfürst nachtfürst = (Nachtfürst) rolle;
                            dropdownOptions = nachtfürst.getSecondFrontendObject();
                            chosenOption = FrontendControl.showFrontendObject(statement, dropdownOptions);
                            nachtfürst.processSecondChosenOption(chosenOption);
                            break;

                        case Irrlicht.STATEMENT_ID:
                            dropdownOptions = rolle.getFrontendObject();
                            FrontendControl.showFrontendObject(statement, dropdownOptions);
                            break;

                        case Irrlicht.INFO:
                            info = Irrlicht.processFlackerndeIrrlichter(FrontendControl.getFlackerndeIrrlichter());
                            FrontendControl.showFrontendObject(statement, info);
                            break;

                        case Analytiker.STATEMENT_ID:
                            Analytiker analytiker = (Analytiker) rolle;

                            DropdownOptions analytikerDropdownOptions = analytiker.getDropdownOptions();
                            FrontendControl.showDropdownPage(statement, analytikerDropdownOptions, analytikerDropdownOptions);

                            Spieler chosenSpieler1 = Game.game.findSpieler(FrontendControl.erzählerFrame.chosenOption1);
                            Spieler chosenSpieler2 = Game.game.findSpieler(FrontendControl.erzählerFrame.chosenOption2);

                            if (chosenSpieler1 != null && chosenSpieler2 != null) {
                                if (analytiker.showTarnumhang(chosenSpieler1, chosenSpieler2)) {
                                    FrontendControl.showZeigekarte(statement, new Tarnumhang_BonusrollenType());
                                } else {
                                    String answer = analytiker.analysiere(chosenSpieler1, chosenSpieler2);
                                    FrontendControl.showList(statement, answer);
                                }
                            }
                            break;

                        case Konditor.STATEMENT_ID:
                            if (gibtEsTorte()) {
                                Torte.torte = true;

                                dropdownOptions = rolle.getFrontendObject();
                                chosenOption = FrontendControl.showKonditorDropdownPage(statement, dropdownOptions);
                                rolle.processChosenOption(chosenOption);

                                Torte.gut = chosenOption.equals(Konditor.GUT);
                            }
                            break;

                        case Konditorlehrling.STATEMENT_ID:
                            if (!gibtEsTorte()) {
                                Konditorlehrling konditorlehrling = (Konditorlehrling) rolle;

                                DropdownOptions dropdownOptionsSpieler = konditorlehrling.getDropdownOptionsSpieler();
                                DropdownOptions dropdownOptionsTorte = Konditor.getTortenOptions();
                                FrontendControl.showDropdownPage(statement, dropdownOptionsSpieler, dropdownOptionsTorte);

                                konditorlehrling.processChosenOption(FrontendControl.erzählerFrame.chosenOption1);
                            }
                            break;

                        case IndieStatements.OPFER_ID:
                            setOpfer();

                            opferDerNacht = opfer.stream()
                                    .map(opfer -> opfer.spieler.name).distinct()
                                    .collect(Collectors.toList());

                            FrontendControl.erzählerListPage(statement, IndieStatements.OPFER_TITLE, opferDerNacht);
                            for (String opfer : opferDerNacht) {
                                FrontendControl.spielerAnnounceOpferPage(Game.game.findSpieler(opfer));
                                FrontendControl.waitForAnswer();
                            }

                            checkVictory();
                            break;

                        case ProgramStatements.TORTE_ID:
                            if (Torte.torte) {
                                FrontendControl.erzählerTortenPage();
                                FrontendControl.showZeigekarteOnSpielerScreen(new Torten_Zeigekarte());

                                FrontendControl.waitForAnswer();
                                Torte.setTortenEsser(FrontendControl.getTortenesser());
                            } else {
                                if (Torte.tortenStück) {
                                    dropdownOptions = Torte.getFrontendObject();
                                    statement.title = Torte.TORTENSTUECK_TITLE;
                                    chosenOption = FrontendControl.showFrontendObject(statement, dropdownOptions);

                                    if (chosenOption.equals(Torte.TORTE_NEHMEN)) {
                                        Torte.stückEssen = true;
                                    }
                                }
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

    private void beginNight() {
        setDefaultPlayerStates();

        for (Fraktion fraktion : Fraktion.getFraktionen()) {
            fraktion.beginNight();
        }

        for (Hauptrolle hauptrolle : Game.game.hauptrollenInGame) {
            hauptrolle.beginNight();
        }

        for (Bonusrolle bonusrolle : Game.game.bonusrollenInGame) {
            bonusrolle.beginNight();
        }

        angriffe = new ArrayList<>();
        opfer = new ArrayList<>();

        for (Spieler currentSpieler : Game.game.spieler) {
            Hauptrolle hauptrolleSpieler = currentSpieler.hauptrolle;

            if (hauptrolleSpieler.equals(Schattenpriester.ID)) {
                if (((Schattenpriester) hauptrolleSpieler).neuster) {
                    currentSpieler.geschützt = true;
                    ((Schattenpriester) hauptrolleSpieler).neuster = false;
                }
            }
        }

        Torte.beginNight();

        Henker.pagecounter = 0;
    }

    private void refreshStatementStates() {
        for (Statement statement : statements) {
            if (!statement.alreadyOver) {
                statement.refreshState();
            }
        }
    }

    private void setSchütze() {
        for (Spieler currentSpieler : Game.game.spieler) {
            if (currentSpieler.bonusrolle.equals(DunklesLicht.ID)) {
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

    private void setOpfer() {
        checkLiebespaar();
        killOpfer();
    }

    private void setSpielerAwake(Statement statement) {
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
        setDefaultPlayerStates();
    }

    private void setDefaultPlayerStates() {
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

    private void killOpfer() {
        for (Opfer currentOpfer : opfer) {
            if (Rolle.rolleLebend(Blutwolf.ID)) {
                if (currentOpfer.täterFraktion != null && currentOpfer.täterFraktion.equals(Werwölfe.ID)) {
                    Blutwolf.deadStacks++;
                    if (Blutwolf.deadStacks >= 2) {
                        Blutwolf.deadly = true;
                    }
                }
            }

            Game.game.killSpieler(currentOpfer.spieler);
        }
    }

    private void checkLiebespaar() {
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

    public static boolean gibtEsTorte() {
        return opfer.size() == 0 &&
                !Torte.letzteTorteGut &&
                Rolle.rolleLebend(Konditor.ID) &&
                !Opfer.isOpferPerRolle(Konditor.ID) &&
                Rolle.rolleAktiv(Konditor.ID);
    }

    private void checkVictory() {
        Winner winner = Game.game.checkVictory();

        if (winner != Winner.NO_WINNER) {
            FrontendControl.showEndScreenPage(winner);
        }
    }

    private void henkerNächsteSeite() {
        Henker henker = (Henker) Game.game.findHauptrolle(Henker.ID);
        Henker.pagecounter++;
        if (Henker.pagecounter < Henker.numberOfPages) {
            FrontendObject frontendObject = henker.getPage(FrontendControl.erzählerFrame.chosenOption1);
            showHenkerPage(frontendObject);
        }
    }

    private void henkerSeiteZurück() {
        Henker henker = (Henker) Game.game.findHauptrolle(Henker.ID);
        if (Henker.pagecounter > 0) {
            Henker.pagecounter--;
            FrontendObject frontendObject = henker.getPage();
            showHenkerPage(frontendObject);
        }
    }

    private void showHenkerPage(FrontendObject frontendObject) {
        Statement henkerStatement = statements.stream()
                .filter(statement -> statement.id.equals(Henker.STATEMENT_ID))
                .findAny().orElse(null);

        FrontendControl.showFrontendObject(henkerStatement, frontendObject);
    }
}
