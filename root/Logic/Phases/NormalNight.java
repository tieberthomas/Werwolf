package root.Logic.Phases;

import root.Controller.FrontendControl;
import root.Controller.FrontendObject.FrontendObject;
import root.Frontend.Utils.DropdownOptions;
import root.Logic.Game;
import root.Logic.KillLogic.Angriff;
import root.Logic.KillLogic.Opfer;
import root.Logic.KillLogic.Selbstmord;
import root.Logic.Liebespaar;
import root.Logic.Persona.*;
import root.Logic.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Logic.Persona.Rollen.Bonusrollen.*;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Torten_Zeigekarte;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Irrlicht;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Schattenmensch;
import root.Logic.Persona.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Logic.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Logic.Phases.NightBuilding.NormalNightStatementBuilder;
import root.Logic.Phases.Statement.Constants.IndieStatements;
import root.Logic.Phases.Statement.Constants.ProgramStatements;
import root.Logic.Phases.Statement.Constants.StatementState;
import root.Logic.Phases.Statement.Statement;
import root.Logic.Phases.Statement.StatementDependency.StatementDependency;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyPersona;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyStatement;
import root.Logic.Spieler;
import root.Logic.Torte;
import root.ResourceManagement.SoundManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NormalNight extends Thread {
    public static List<Statement> statements;
    public static Object lock;

    public static List<Angriff> angriffe = new ArrayList<>();
    public static List<Opfer> opfer = new ArrayList<>();
    public static List<String> opferDerNacht;

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

            Persona persona = null;

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

                    if (dependency instanceof StatementDependencyPersona) {
                        persona = ((StatementDependencyPersona) dependency).persona;
                    }

                    switch (statement.id) {
                        case IndieStatements.ALLE_SCHLAFEN_EIN_ID:
                            SoundManager.playFallAsleep();
                            break;

                        case IndieStatements.ALLE_WACHEN_AUF_ID:
                            SoundManager.playWakeUp();
                            break;
                    }

                    switch (statement.type) {
                        case SHOW_TITLE:
                            FrontendControl.showTitle(statement);
                            break;

                        case PERSONA_CHOOSE_ONE:
                            dropdownOptions = persona.getFrontendObject();
                            chosenOption = FrontendControl.showFrontendObject(statement, dropdownOptions);
                            persona.processChosenOption(chosenOption);
                            break;

                        case PERSONA_CHOOSE_ONE_INFO:
                            dropdownOptions = persona.getFrontendObject();
                            chosenOption = FrontendControl.showFrontendObject(statement, dropdownOptions);
                            info = persona.processChosenOptionGetInfo(chosenOption);
                            FrontendControl.showFrontendObject(statement, info);
                            break;

                        case PERSONA_INFO:
                            info = persona.getInfo();
                            FrontendControl.showFrontendObject(statement, info);
                            break;
                    }

                    switch (statement.id) {
                        case Schattenmensch.STATEMENT_ID:
                            FrontendObject frontendObject = persona.getFrontendObject();
                            Schattenmensch.transformIfShallBeTransformed();
                            FrontendControl.showFrontendObject(statement, frontendObject);
                            break;

                        case ProgramStatements.SCHÜTZE_ID:
                            setSchattenpriesterSchutz();
                            setNachtfürstSchutz();
                            break;

                        case Henker.STATEMENT_ID:
                            dropdownOptions = persona.getFrontendObject();
                            chosenOption = FrontendControl.showFrontendObject(statement, dropdownOptions);
                            persona.processChosenOption(chosenOption);

                            Henker henker = ((Henker) persona);

                            while (Henker.pagecounter < Henker.numberOfPages) {
                                if (FrontendControl.erzählerFrame.next) {
                                    henkerNächsteSeite();
                                } else {
                                    henkerSeiteZurück();
                                }
                            }

                            if (henker.besucht != null) {
                                info = henker.processChosenOptionsGetInfo(Henker.chosenHauptrolle.name, Henker.chosenBonusrolle.name);
                                FrontendControl.showFrontendObject(statement, info);
                            }
                            break;

                        case SchattenpriesterFraktion.NEUER_SCHATTENPRIESTER:
                            chosenSpieler = Game.game.findSpieler(chosenOptionLastStatement);
                            List<String> neueSchattenpriester = new ArrayList<>();
                            erzählerInfoIconImagePath = "";
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
                            Nachtfürst nachtfürst = (Nachtfürst) persona;
                            dropdownOptions = nachtfürst.getSecondFrontendObject();
                            chosenOption = FrontendControl.showFrontendObject(statement, dropdownOptions);
                            nachtfürst.processSecondChosenOption(chosenOption);
                            break;

                        case Irrlicht.STATEMENT_ID:
                            dropdownOptions = persona.getFrontendObject();
                            FrontendControl.showFrontendObject(statement, dropdownOptions);
                            break;

                        case Irrlicht.INFO:
                            info = Irrlicht.processFlackerndeIrrlichter(FrontendControl.getFlackerndeIrrlichter());
                            FrontendControl.showFrontendObject(statement, info);
                            break;

                        case Analytiker.STATEMENT_ID:
                            Analytiker analytiker = (Analytiker) persona;

                            DropdownOptions analytikerDropdownOptions = analytiker.getDropdownOptions();
                            FrontendControl.showDropdownPage(statement, analytikerDropdownOptions, analytikerDropdownOptions);

                            Spieler chosenSpieler1 = Game.game.findSpieler(FrontendControl.erzählerFrame.chosenOption1);
                            Spieler chosenSpieler2 = Game.game.findSpieler(FrontendControl.erzählerFrame.chosenOption2);

                            if (chosenSpieler1 != null && chosenSpieler2 != null) {
                                String answer = analytiker.analysiere(chosenSpieler1, chosenSpieler2);

                                if (answer.equals(Analytiker.TARNUMHANG)) {
                                    FrontendControl.showZeigekarte(statement, new Tarnumhang_BonusrollenType());
                                } else {
                                    FrontendControl.showList(statement, answer);
                                }
                            }
                            break;

                        case Konditor.STATEMENT_ID:
                            if (gibtEsTorte()) {
                                Torte.torte = true;

                                dropdownOptions = persona.getFrontendObject();
                                chosenOption = FrontendControl.showKonditorDropdownPage(statement, dropdownOptions);
                                persona.processChosenOption(chosenOption);

                                Torte.gut = chosenOption.equals(Konditor.GUT);
                            }
                            break;

                        case Konditorlehrling.STATEMENT_ID:
                            if (!gibtEsTorte()) {
                                Konditorlehrling konditorlehrling = (Konditorlehrling) persona;

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

        for (Spieler currentSpieler : Game.game.spieler) { //TODO move to Schütze statement?
            Hauptrolle hauptrolleSpieler = currentSpieler.hauptrolle;

            if (hauptrolleSpieler.equals(Schattenpriester.ID)) {
                if (((Schattenpriester) hauptrolleSpieler).neuster) {
                    currentSpieler.geschützt = true;
                    ((Schattenpriester) hauptrolleSpieler).neuster = false;
                }
            }
        }

        Torte.beginNight();

        Henker.pagecounter = 0; //TODO move to henker.beginnight
    }

    private void refreshStatementStates() {
        for (Statement statement : statements) {
            if (!statement.alreadyOver) {
                statement.refreshState();
            }
        }
    }

    private void setNachtfürstSchutz() { //TODO find a way to move out of night
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

    private void setSchattenpriesterSchutz() {
        if (SchattenpriesterFraktion.resurrectResigned) {
            List<Spieler> schattenpriesterList = Fraktion.getFraktionsMembers(SchattenpriesterFraktion.ID);
            schattenpriesterList.forEach(spieler -> spieler.geschützt = true);

            SchattenpriesterFraktion.resurrectResigned = false;
        }
    }

    private void setOpfer() {
        checkLiebespaar();
        killOpfer();
    }

    private void setSpielerAwake(Statement statement) {
        spielerAwake.clear();
        if (statement.dependency instanceof StatementDependencyPersona) {
            StatementDependencyPersona statementDependencyPersona = (StatementDependencyPersona) statement.dependency;
            if (statementDependencyPersona.persona instanceof Fraktion) {
                spielerAwake.addAll(Fraktion.getFraktionsMembers(statementDependencyPersona.persona.id));
            } else if (statementDependencyPersona.persona instanceof Rolle) {
                spielerAwake.add(Game.game.findSpielerPerRolle(statementDependencyPersona.persona.id));
            }
        }
    }

    private void cleanUpNight() {
        for (Hauptrolle hauptrolle : Game.game.hauptrollenInGame) {
            hauptrolle.cleanUpAfterNight();
        }

        for (Bonusrolle bonusrolle : Game.game.bonusrollenInGame) {
            bonusrolle.cleanUpAfterNight();
        }

        setDefaultPlayerStates();
    }

    private void setDefaultPlayerStates() {
        for (Spieler currentSpieler : Game.game.spieler) {
            currentSpieler.aktiv = true;
            currentSpieler.geschützt = false;
            currentSpieler.ressurectable = true;
        }
    }

    public static int getAnzahlOpferDerNacht() {
        if (opferDerNacht == null) {
            return 0;
        } else {
            return opferDerNacht.size();
        }
    }

    private void killOpfer() {
        for (Opfer currentOpfer : opfer) {
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
