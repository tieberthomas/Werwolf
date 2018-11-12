package root.Phases;

import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Fraktion;
import root.Persona.Hauptrolle;
import root.Persona.Rolle;
import root.Persona.Rollen.Bonusrollen.Tarnumhang;
import root.Persona.Rollen.Constants.BonusrollenType.Passiv;
import root.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Wolfsmensch;
import root.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Phases.NightBuilding.Constants.IndieStatements;
import root.Phases.NightBuilding.Constants.StatementState;
import root.Phases.NightBuilding.FirstNightStatementBuilder;
import root.Phases.NightBuilding.Statement;
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyFraktion;
import root.Phases.NightBuilding.StatementDependancy.StatementDependencyRolle;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Game;
import root.mechanics.Liebespaar;
import root.Utils.Rand;

import java.util.ArrayList;
import java.util.List;

public class FirstNight extends Thread {
    Game game;

    public static final String TARNUMHANG_TITLE = "Tarnumhang";
    public static final String NEUE_KARTE_TITLE = "Neue Karte";

    public static ArrayList<Statement> statements;
    public static Object lock;
    public static ArrayList<Spieler> spielerAwake = new ArrayList<>();
    public static ArrayList<Bonusrolle> swappedRoles = new ArrayList<>();

    public FirstNight(Game game) {
        this.game = game;
    }

    public void run() {
        lock = new Object();
        synchronized (lock) {
            FrontendControl dropdownOtions;
            FrontendControl info;
            String chosenOption;

            Bonusrolle newBonusrolle;
            String cardToDisplay;
            String imagePath;

            beginNight();

            statements = FirstNightStatementBuilder.firstNightBuildStatements();

            for (Statement statement : statements) {
                refreshStatementStates();

                if (statement.state != StatementState.INVISIBLE_NOT_IN_GAME) {
                    setSpielerAwake(statement);
                    Rolle rolle = null;
                    if (statement.dependency instanceof StatementDependencyRolle) {
                        rolle = ((StatementDependencyRolle) statement.dependency).rolle;
                    }

                    if (rolle != null && rolle instanceof Bonusrolle && ((Bonusrolle) rolle).type.equals(new Passiv())) {
                        Bonusrolle bonusrolle = ((Bonusrolle) rolle);
                        newBonusrolle = bonusrolle.getTauschErgebnis();
                        cardToDisplay = newBonusrolle.imagePath;
                        String title;
                        if (newBonusrolle.equals(bonusrolle)) {
                            title = "";
                        } else {
                            swappedRoles.add(bonusrolle);
                            title = NEUE_KARTE_TITLE;
                        }
                        showCard(statement, title, cardToDisplay);
                        if (Rolle.rolleLebend(rolle.name)) {
                            bonusrolle.tauschen(newBonusrolle);
                        }

                    } else if (statement.dependency instanceof StatementDependencyFraktion) {
                        Fraktion fraktion = ((StatementDependencyFraktion) statement.dependency).fraktion;
                        showFraktionMembers(statement, fraktion);
                    } else {
                        switch (statement.id) {
                            case IndieStatements.LIEBESPAAR_ID:
                                List<String> spielerOrZufällig = game.liebespaar.getDropdownOptions();

                                showDropdown(statement, spielerOrZufällig, spielerOrZufällig);

                                game.liebespaar = new Liebespaar(FrontendControl.erzählerFrame.chosenOption1, FrontendControl.erzählerFrame.chosenOption2, game);
                                FrontendControl.regenerateAndRefreshÜbersichtsFrame();
                                break;

                            case IndieStatements.LIEBESPAAR_FINDEN_ID:
                                Liebespaar liebespaar = game.liebespaar;
                                if (liebespaar != null && liebespaar.spieler1 != null) {
                                    ArrayList<String> liebespaarStrings = new ArrayList<>();

                                    liebespaarStrings.add(liebespaar.spieler1.name);
                                    liebespaarStrings.add(liebespaar.spieler2.name);

                                    imagePath = Liebespaar.IMAGE_PATH;

                                    FrontendControl.erzählerListPage(statement, liebespaarStrings);
                                    FrontendControl.spielerIconPicturePage(statement.title, imagePath);

                                    waitForAnswer();
                                }
                                break;

                            case Henker.FIRST_NIGHT_STATEMENT_ID:
                                List<Hauptrolle> henkerHauptrollen = game.getStillAvailableBürger();
                                Hauptrolle henkerHauptrolle = pickRandomHauptrolle(henkerHauptrollen);
                                if (henkerHauptrolle == null) {
                                    henkerHauptrolle = new Dorfbewohner();
                                }
                                Henker.fakeRolle = henkerHauptrolle;
                                showCard(statement, statement.title, henkerHauptrolle.imagePath);
                                break;

                            case Wolfsmensch.FIRST_NIGHT_STATEMENT_ID:
                                List<Hauptrolle> hauptrollen = game.getStillAvailableBürger();
                                Hauptrolle hauptrolle = pickRandomHauptrolle(hauptrollen);
                                if (hauptrolle == null) {
                                    hauptrolle = new Dorfbewohner();
                                }
                                showCard(statement, statement.title, hauptrolle.imagePath);
                                break;

                            default:
                                showTitle(statement);
                                break;
                        }
                    }
                }
            }
        }

        cleanUp();
        PhaseManager.nextPhase();
    }

    public void beginNight() {
        for (Hauptrolle currentHauptrolle : game.hauptrollen) {
            currentHauptrolle.besuchtLastNight = null;
            currentHauptrolle.besucht = null;
        }
        for (Bonusrolle currentBonusrolle : game.bonusrollen) {
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
        for (Spieler currentSpieler : game.spieler) {
            currentSpieler.aktiv = true;
            currentSpieler.geschützt = false;
            currentSpieler.ressurectable = true;
        }
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

    public void showFraktionMembers(Statement statement, Fraktion fraktion) {
        ArrayList<String> fraktionMembers = Fraktion.getFraktionsMemberStrings(fraktion.name);

        String fraktionsLogoImagePath = fraktion.imagePath;

        showListShowImage(statement, fraktionMembers, fraktionsLogoImagePath);
    }

    public String showFrontendControl(Statement statement, FrontendControl frontendControl) {
        if (frontendControl.title == null) {
            frontendControl.title = statement.title;
        }

        switch (frontendControl.typeOfContent) {
            case TITLE:
                showTitle(statement, frontendControl.title);
                break;

            case DROPDOWN:
                showDropdown(statement, frontendControl.title, frontendControl.dropdownStrings);
                return FrontendControl.erzählerFrame.chosenOption1;

            case DROPDOWN_LIST:
                showDropdownList(statement, frontendControl.title, frontendControl.dropdownStrings);
                return FrontendControl.erzählerFrame.chosenOption1;

            case LIST:
                showList(statement, frontendControl.title, frontendControl.dropdownStrings);
                break;

            case IMAGE:
                showImage(statement, frontendControl.title, frontendControl.imagePath);
                break;

            case CARD:
                showCard(statement, frontendControl.title, frontendControl.imagePath);
                break;

            case LIST_IMAGE:
                showListShowImage(statement, frontendControl.title, frontendControl.dropdownStrings, frontendControl.imagePath);
        }

        return null;
    }

    public void showBonusrolle(Statement statement, Spieler spieler) {
        if (spieler != null) {
            statement.title = spieler.name;

            String imagePath = spieler.bonusrolle.imagePath;
            if (spieler.bonusrolle.equals(Tarnumhang.NAME)) {
                imagePath = ImagePath.TARNUMHANG;
                statement.title = TARNUMHANG_TITLE;
            }
            showCard(statement, statement.title, imagePath);
        }
    }

    public void showHauptrolle(Statement statement, Spieler spieler) {
        if (spieler != null) {
            showCard(statement, spieler.name, spieler.hauptrolle.imagePath);
        }
    }

    public void showTitle(Statement statement) {
        showTitle(statement, statement.title);
    }

    public void showTitle(Statement statement, String title) {
        FrontendControl.erzählerDefaultNightPage(statement);
        FrontendControl.spielerTitlePage(title);

        waitForAnswer();
    }

    public void showList(Statement statement, String title, List<String> strings) {
        FrontendControl.erzählerListPage(statement, title, strings);
        FrontendControl.spielerListPage(title, strings);

        waitForAnswer();
    }

    public void showImage(Statement statement, String title, String imagePath) {
        FrontendControl.erzählerIconPicturePage(statement, title, imagePath);
        FrontendControl.spielerIconPicturePage(title, imagePath);

        waitForAnswer();
    }

    public void showDropdown(Statement statement, String title, List<String> dropdownOptions) {
        FrontendControl.erzählerDropdownPage(statement, dropdownOptions);
        FrontendControl.spielerDropdownPage(title, 1);

        waitForAnswer();
    }

    public void showDropdown(Statement statement, List<String> dropdownOptions1, List<String> dropdownOptions2) {
        FrontendControl.erzählerDropdownPage(statement, dropdownOptions1, dropdownOptions2);
        FrontendControl.spielerDropdownPage(statement.title, 2);

        waitForAnswer();
    }

    public void showDropdownList(Statement statement, String title, List<String> strings) {
        FrontendControl.erzählerDropdownPage(statement, strings);
        FrontendControl.spielerDropdownListPage(title, strings);

        waitForAnswer();
    }

    public void showCard(Statement statement, String title, String imagePath) {
        FrontendControl.erzählerCardPicturePage(statement, title, imagePath);
        FrontendControl.spielerCardPicturePage(title, imagePath);

        waitForAnswer();
    }

    public void showListShowImage(Statement statement, List<String> strings, String spielerImagePath) {
        showListShowImage(statement, statement.title, strings, spielerImagePath);
    }

    public void showListShowImage(Statement statement, String title, List<String> strings, String spielerImagePath) {
        FrontendControl.erzählerListPage(statement, strings);
        FrontendControl.spielerIconPicturePage(title, spielerImagePath);

        waitForAnswer();
    }

    public void waitForAnswer() {
        FrontendControl.refreshÜbersichtsFrame();
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Hauptrolle pickRandomHauptrolle(List<Hauptrolle> hauptrollen) {
        int numberOfUnassignedHauptrollen = hauptrollen.size();
        if (numberOfUnassignedHauptrollen > 0) {
            return Rand.getRandomElement(hauptrollen);
        }

        return Hauptrolle.DEFAULT_HAUPTROLLE;
    }
}
