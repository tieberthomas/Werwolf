package root.Logic.Phases;

import root.Controller.FrontendControl;
import root.Logic.Game;
import root.Logic.Liebespaar;
import root.Logic.Persona.*;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Logic.Persona.Rollen.Hauptrollen.Werwölfe.Wolfsmensch;
import root.Logic.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Logic.Phases.NightBuilding.SetupNightStatementBuilder;
import root.Logic.Phases.Statement.Constants.IndieStatements;
import root.Logic.Phases.Statement.Constants.StatementState;
import root.Logic.Phases.Statement.Statement;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyPersona;
import root.Logic.Spieler;
import root.ResourceManagement.SoundManager;
import root.Utils.Rand;

import java.util.ArrayList;
import java.util.List;

import static root.Logic.Persona.Constants.TitleConstants.NEUE_KARTE_TITLE;

public class SetupNight extends Thread {
    public static List<Statement> statements;
    public static Object lock;
    public static List<Spieler> spielerAwake = new ArrayList<>();
    public static List<Bonusrolle> swappedRoles = new ArrayList<>();

    public void run() {
        lock = new Object();
        synchronized (lock) {
            FrontendControl.lock = lock;

            Bonusrolle newBonusrolle;
            String cardToDisplay;
            String imagePath;

            beginNight();

            statements = SetupNightStatementBuilder.setupNightBuildStatements();

            for (Statement statement : statements) {
                refreshStatementStates();

                if (statement.state != StatementState.INVISIBLE_NOT_IN_GAME) {
                    setSpielerAwake(statement);
                    Persona persona = null;
                    if (statement.dependency instanceof StatementDependencyPersona) {
                        persona = ((StatementDependencyPersona) statement.dependency).persona;
                    }

                    switch (statement.id) {
                        case IndieStatements.ALLE_SCHLAFEN_EIN_ID:
                            SoundManager.playFallAsleep();
                            break;

                        case IndieStatements.ALLE_WACHEN_AUF_ID:
                            SoundManager.playWakeUp();
                            break;
                    }

                    if (persona instanceof Bonusrolle && ((Bonusrolle) persona).type.equals(new Passiv())) { //TODO nicht schön es soll ein "tauschen" flag geben
                        Bonusrolle bonusrolle = ((Bonusrolle) persona);
                        newBonusrolle = bonusrolle.getTauschErgebnis();
                        cardToDisplay = newBonusrolle.imagePath;
                        String title;
                        if (newBonusrolle.equals(bonusrolle)) {
                            title = ""; //TODO wollen wir das so ohne titel?
                        } else {
                            swappedRoles.add(bonusrolle);
                            title = NEUE_KARTE_TITLE;
                        }
                        FrontendControl.showCard(statement, title, cardToDisplay);
                        if (Rolle.rolleLebend(persona.id)) {
                            bonusrolle.tauschen(newBonusrolle);
                        }
                    } else if (persona instanceof Fraktion) {
                        Fraktion fraktion = (Fraktion) persona;
                        showFraktionMembers(statement, fraktion);
                    } else {
                        switch (statement.id) {
                            case IndieStatements.LIEBESPAAR_ID:
                                FrontendControl.showDropdown(statement, Liebespaar.getDropdownOptions(), Liebespaar.getDropdownOptions());

                                //TODO chosen options sollten direkt über frontendcontrol accessed werden
                                Game.game.liebespaar = new Liebespaar(FrontendControl.erzählerFrame.chosenOption1, FrontendControl.erzählerFrame.chosenOption2);
                                FrontendControl.regenerateAndRefreshÜbersichtsFrame();
                                break;

                            case IndieStatements.LIEBESPAAR_FINDEN_ID:
                                Liebespaar liebespaar = Game.game.liebespaar;
                                if (liebespaar != null && liebespaar.spieler1 != null) {
                                    List<String> liebespaarStrings = new ArrayList<>();

                                    liebespaarStrings.add(liebespaar.spieler1.name);
                                    liebespaarStrings.add(liebespaar.spieler2.name);

                                    imagePath = Liebespaar.IMAGE_PATH;

                                    FrontendControl.erzählerListPage(statement, liebespaarStrings);
                                    FrontendControl.spielerIconPicturePage(statement.title, imagePath);

                                    FrontendControl.waitForAnswer();
                                }
                                break;

                            case Henker.SETUP_NIGHT_STATEMENT_ID:
                                Henker.fakeRolle = getRandomStillAvailableBürgerRolle();
                                FrontendControl.showCard(statement, statement.title, Henker.fakeRolle.imagePath);
                                break;

                            case Wolfsmensch.SETUP_NIGHT_STATEMENT_ID:
                                Hauptrolle hauptrolle = getRandomStillAvailableBürgerRolle();
                                FrontendControl.showCard(statement, statement.title, hauptrolle.imagePath);
                                break;

                            default:
                                FrontendControl.showTitle(statement);
                                break;
                        }
                    }
                }
            }
        }

        cleanUp();
        PhaseManager.nextPhase();
    }

    private void beginNight() {
        for (Hauptrolle currentHauptrolle : Game.game.hauptrollen) {
            currentHauptrolle.besuchtLastNight = null;
            currentHauptrolle.besucht = null;
        }
        for (Bonusrolle currentBonusrolle : Game.game.bonusrollen) {
            currentBonusrolle.besuchtLastNight = null;
            currentBonusrolle.besucht = null;
        }
    }

    private void refreshStatementStates() {
        for (Statement statement : statements) {
            if (!statement.alreadyOver) {
                statement.refreshState();
            }
        }
    }

    private void cleanUp() {
        for (Spieler currentSpieler : Game.game.spieler) {
            currentSpieler.aktiv = true;
            currentSpieler.geschützt = false;
            currentSpieler.ressurectable = true;
        }
    }

    private void setSpielerAwake(Statement statement) { //TODO copyed code!
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

    private void showFraktionMembers(Statement statement, Fraktion fraktion) {
        List<String> fraktionMembers = Fraktion.getFraktionsMemberStrings(fraktion.id);

        String fraktionsLogoImagePath = fraktion.imagePath;

        FrontendControl.showListShowImage(statement, fraktionMembers, fraktionsLogoImagePath);
    }

    private Hauptrolle getRandomStillAvailableBürgerRolle() {
        List<Hauptrolle> henkerHauptrollen = Game.game.getStillAvailableBürger();
        Hauptrolle henkerHauptrolle = pickRandomHauptrolle(henkerHauptrollen);
        if (henkerHauptrolle == null) {
            henkerHauptrolle = new Dorfbewohner();
        }
        return henkerHauptrolle;
    }

    private Hauptrolle pickRandomHauptrolle(List<Hauptrolle> hauptrollen) {
        return hauptrollen.isEmpty() ? Hauptrolle.getDefaultHauptrolle() : Rand.getRandomElement(hauptrollen);
    }
}
