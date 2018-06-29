package root.Phases;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Schattenpriester_Fraktion;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;
import root.Rollen.Hauptrollen.Bürger.Bruder;
import root.Rollen.Hauptrollen.Bürger.Seherin;
import root.Rollen.Hauptrollen.Werwölfe.Alphawolf;
import root.Rollen.Nebenrolle;
import root.Rollen.Nebenrollen.*;
import root.Rollen.Rolle;
import root.Spieler;
import root.mechanics.Liebespaar;

import java.util.ArrayList;
import java.util.Objects;

public class ErsteNacht extends Thread {
    public static final String ALLE_SCHLAFEN_EIN = "Alle schlafen ein";
    public static final String ALLE_WACHEN_AUF = "Alle wachen auf";

    public static final String LIEBESPAAR = "Der Erzähler wählt ein Liebespaar aus";
    public static final String LIEBESPAAR_FINDEN = "Das Liebespaar erwacht, findet und verliebt sich";
    public static final String REINES_LICHT = "Reines Licht erwacht und tauscht seine Karte je nach Hauptrolle aus";
    public static final String LAMM = "Lamm erwacht und tauscht ggf. seine Karte aus";
    public static final String VAMPIRUMHANG = "Träger des Vampirumhangs erwacht und tauscht ggf. seine Karte aus";
    public static final String WOLFSPELZ = "Träger des Wolfspelzes erwacht und tauscht ggf. seine Karte aus";
    public static final String IMITATOR = "Imitator erwacht und entscheidet welche Rolle er imitieren möchte";
    public static final String VAMPIRE = "Die Vampire erwachen und sehen einander";
    public static final String WERWÖLFE = "Die Werwölfe erwachen und sehen einander";
    public static final String ALPHAWOLF = "Alpha Wolf erwacht und erfährt die Rollen der Wolfsfraktion";
    public static final String SCHATTENPRIESTER = "Die Schattenpriester erwachen und sehen einander";
    public static final String BRÜDER = "Brüder erwachen und sehen einander";
    public static final String SEHERIN = "Seherin erwacht und lässt sich Auskunft über einen Mitspieler geben";

    public static final String ALLE_SCHLAFEN_EIN_TITLE = "Alle schlafen ein";
    public static final String ALLE_WACHEN_AUF_TITLE = "Alle wachen auf";

    public static final String LIEBESPAAR_TITLE = "Liebespaar wählen";
    public static final String LIEBESPAAR_FINDEN_TITLE = "Liebespaar";
    public static final String REINES_LICHT_TITLE = "neue Karte";
    public static final String LAMM_TITLE = "neue Karte";
    public static final String VAMPIRUMHANG_TITLE = "neue Karte";
    public static final String WOLFSPELZ_TITLE = "neue Karte";
    public static final String IMITATOR_TITLE = "Imitieren";
    public static final String VAMPIRE_TITLE = "Vampire";
    public static final String WERWÖLFE_TITLE = "Werwölfe";
    public static final String ALPHAWOLF_TITLE = "Werwölfe";
    public static final String ALPHAWOLF_FERTIG_TITLE = "Fertig";
    public static final String SCHATTENPRIESTER_TITLE = "Schattenpriester";
    public static final String BRÜDER_TITLE = "Brüder";
    public static final String SEHERIN_TITLE = "Spieler wählen";

    public static final String TARNUMHANG_TITLE = "Tarnumhang";

    public static ArrayList<Statement> statements;
    public static Object lock;

    public void run() {
        lock = new Object();
        synchronized (lock) {
            FrontendControl dropdownOtions;
            FrontendControl info;
            String chosenOption;

            Nebenrolle newNebenrolle;
            String cardToDisplay;
            String imagePath;

            beginNight();

            ersteNachtBuildStatements();

            for (Statement statement : statements) {
                Rolle rolle = null;
                if (statement.getClass() == StatementRolle.class) {
                    rolle = ((StatementRolle) statement).getRolle();
                }

                if (rolle != null && rolle instanceof Nebenrolle && ((Nebenrolle) rolle).getType().equals(Nebenrolle.PASSIV)) {
                    Nebenrolle nebenrolle = ((Nebenrolle) rolle);
                    newNebenrolle = nebenrolle.getTauschErgebnis();
                    cardToDisplay = newNebenrolle.getImagePath();
                    displayCard(statement, statement.title, cardToDisplay); //TODO title
                    if(Rolle.rolleLebend(rolle.getName())) {
                        nebenrolle.tauschen(newNebenrolle);
                        Nebenrolle.secondaryRolesInGame.remove(nebenrolle);
                    }

                } else if(statement.getClass() == StatementFraktion.class){
                    Fraktion fraktion = Fraktion.findFraktion(((StatementFraktion) statement).fraktion);
                    showFraktionMembers(statement, fraktion.getName());
                }
                else{
                    switch (statement.beschreibung) {
                        case LIEBESPAAR:
                            ArrayList<String> spielerOrZufällig = Spieler.getLivigPlayerStrings();
                            spielerOrZufällig.add(Liebespaar.ZUFÄLLIG);

                            showDropdownPage(statement, spielerOrZufällig, spielerOrZufällig);

                            Liebespaar.neuesLiebespaar(FrontendControl.erzählerFrame.chosenOption1, FrontendControl.erzählerFrame.chosenOption2);
                            break;

                        case LIEBESPAAR_FINDEN:
                            if(Liebespaar.spieler1!=null) {
                                ArrayList<String> liebespaarStrings = new ArrayList<>();

                                liebespaarStrings.add(Liebespaar.spieler1.name);
                                liebespaarStrings.add(Liebespaar.spieler2.name);

                                imagePath = Liebespaar.getImagePath();

                                FrontendControl.erzählerListPage(statement, liebespaarStrings);
                                FrontendControl.spielerIconPicturePage(statement.title, imagePath);

                                waitForAnswer();
                            }
                            break;

                        case ALPHAWOLF:
                            ArrayList<Spieler> werwölfe = Fraktion.findFraktion(Werwölfe.name).getFraktionsMembers();
                            werwölfe.remove(Spieler.findSpielerPerRolle(Alphawolf.name));
                            for (Spieler currentSpieler : werwölfe) {
                                showHauptrolle(statement, currentSpieler);
                            }
                            statement.title = ALPHAWOLF_FERTIG_TITLE;
                            showImageOnBothScreens(statement, ResourcePath.WÖLFE_ICON);
                            break;

                        case BRÜDER:
                            ArrayList<String> brüder = Spieler.findSpielersPerRolle(Bruder.name);

                            FrontendControl.erzählerListPage(statement, brüder);
                            FrontendControl.spielerCardPicturePage(statement.title, ResourcePath.BRÜDER_KARTE);

                            waitForAnswer();
                            break;

                        case SEHERIN:
                            dropdownOtions = rolle.getDropdownOptions();
                            chosenOption = showDropdownPage(statement, dropdownOtions);
                            info = rolle.processChosenOptionGetInfo(chosenOption);
                            showInfo(statement, info);
                            break;

                        default:
                            FrontendControl.erzählerDefaultNightPage(statement);
                            FrontendControl.spielerTitlePage(statement.title);

                            waitForAnswer();
                            break;
                    }
                }
            }
        }

        cleanUp();
        PhaseManager.day();
    }

    public void beginNight() {
        for (Hauptrolle currentHauptrolle : Hauptrolle.mainRoles) {
            currentHauptrolle.besuchtLetzteNacht = null;
            currentHauptrolle.besucht = null;
        }
        for (Nebenrolle currentNebenrolle : Nebenrolle.secondaryRoles) {
            currentNebenrolle.besuchtLetzteNacht = null;
            currentNebenrolle.besucht = null;
        }
    }

    private ArrayList<String> getSecondaryRolesLeft() {
        ArrayList<String> freieNebenrollen = new ArrayList<>();

        for (Nebenrolle currentNebenrolle: Nebenrolle.secondaryRolesInGame) {
            String nebenrolle = currentNebenrolle.getName();

            boolean frei = true;
            for (Spieler currentSpieler: Spieler.spieler) {
                String nebenrolleSpieler = currentSpieler.nebenrolle.getName();

                if (Objects.equals(nebenrolle, nebenrolleSpieler)) {
                    frei = false;
                }
            }

            if(frei) {
                freieNebenrollen.add(nebenrolle);
            }
        }

        return freieNebenrollen;
    }

    private void cleanUp() {
        for (Spieler currentSpieler : Spieler.spieler) {
            currentSpieler.aktiv = true;
            currentSpieler.geschützt = false;
            currentSpieler.ressurectable = true;

            currentSpieler.hauptrolle.aktiv = true;
        }
    }

    public void showFraktionMembers(Statement statement, String fraktionName) {
        Fraktion fraktion = Fraktion.findFraktion(fraktionName);
        ArrayList<String> fraktionMembers = fraktion.getFraktionsMemberStrings();

        FrontendControl.erzählerListPage(statement, fraktionMembers);

        try {
            String fraktionsLogoImagePath = fraktion.getImagePath();

            FrontendControl.spielerIconPicturePage(statement.title, fraktionsLogoImagePath);
        } catch (NullPointerException e) {
            System.out.println(fraktionName + " nicht gefunden");
        }

        waitForAnswer();
    }

    public void showInfo(Statement statement, FrontendControl info) {
        if (info.title == null) {
            info.title = statement.title;
        }

        switch (info.typeOfContent) {
            case FrontendControl.STATIC_IMAGE:
                showImageOnBothScreens(statement, info.title, info.imagePath);
                break;

            case FrontendControl.STATIC_LIST:
                showListOnBothScreens(statement, info.title, info.content);
                break;

            case FrontendControl.STATIC_CARD:
                displayCard(statement, info.title, info.imagePath);
                break;
        }
    }

    public void showNebenrolle(Statement statement, Spieler spieler) {
        if (spieler != null) {
            statement.title = spieler.name;

            String imagePath = spieler.nebenrolle.getImagePath();
            if(spieler.nebenrolle.getName().equals(Tarnumhang.name)) {
                imagePath = ResourcePath.TARNUMHANG;
                statement.title = TARNUMHANG_TITLE;
            }
            displayCard(statement, statement.title, imagePath);
        }
    }

    public void showHauptrolle(Statement statement, Spieler spieler) {
        if (spieler != null) {
            displayCard(statement, spieler.name, spieler.hauptrolle.getImagePath());
        }
    }

    public void displayCard(Statement statement, String title, String imagePath) {
        FrontendControl.erzählerCardPicturePage(statement, title, imagePath);
        FrontendControl.spielerCardPicturePage(title, imagePath);

        waitForAnswer();
    }

    public void showListOnBothScreens(Statement statement, String title, ArrayList<String> strings) {
        FrontendControl.erzählerListPage(statement, title, strings);
        FrontendControl.spielerListPage(title, strings);

        waitForAnswer();
    }

    public void showImageOnBothScreens(Statement statement, String imagePath) {
        showImageOnBothScreens(statement, statement.title, imagePath);
    }

    public void showImageOnBothScreens(Statement statement, String title, String imagePath) {
        FrontendControl.erzählerIconPicturePage(statement, title, imagePath);
        FrontendControl.spielerIconPicturePage(title, imagePath);

        waitForAnswer();
    }

    public String showDropdownPage(Statement statement, FrontendControl frontendControl) {
        FrontendControl.erzählerDropdownPage(statement, frontendControl.content);

        switch (frontendControl.typeOfContent)
        {
            case FrontendControl.DROPDOWN_WITHOUT_SUGGESTIONS:
                FrontendControl.spielerDropdownPage(statement.title, 1);
                break;

            case FrontendControl.DROPDOWN_WITH_SUGGESTIONS:
                FrontendControl.spielerDropdownListPage(statement.title, frontendControl.content);
                break;
        }

        waitForAnswer();

        return FrontendControl.erzählerFrame.chosenOption1;
    }

    public void showDropdownPage(Statement statement, ArrayList<String> dropdownOptions1, ArrayList<String> dropdownOptions2) {
        FrontendControl.erzählerDropdownPage(statement, dropdownOptions1, dropdownOptions2);
        FrontendControl.spielerDropdownPage(statement.title, 2);

        waitForAnswer();
    }

    public void waitForAnswer() {
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void ersteNachtBuildStatements() {
        statements = new ArrayList<>();

        addStatement(ALLE_SCHLAFEN_EIN, ALLE_SCHLAFEN_EIN_TITLE);

        addStatement(LIEBESPAAR, LIEBESPAAR_TITLE);
        addStatement(LIEBESPAAR_FINDEN, LIEBESPAAR_FINDEN_TITLE);

        addStatementRolle(REINES_LICHT, REINES_LICHT_TITLE, ReinesLicht.name);
        addStatementRolle(LAMM, LAMM_TITLE, Lamm.name);
        addStatementRolle(VAMPIRUMHANG, VAMPIRUMHANG_TITLE, Vampirumhang.name);
        addStatementRolle(WOLFSPELZ, WOLFSPELZ_TITLE, Wolfspelz.name);

        //addStatementRolle(IMITATOR, IMITATOR_TITLE, Imitator.name);

        addStatementFraktion(VAMPIRE, VAMPIRE_TITLE, Vampire.name);
        addStatementFraktion(WERWÖLFE, WERWÖLFE_TITLE, Werwölfe.name);
        addStatementRolle(ALPHAWOLF, ALPHAWOLF_TITLE, Alphawolf.name);
        addStatementFraktion(SCHATTENPRIESTER, SCHATTENPRIESTER_TITLE, Schattenpriester_Fraktion.name);
        addStatementRolle(BRÜDER, BRÜDER_TITLE, Bruder.name);

        addStatementRolle(SEHERIN, SEHERIN_TITLE, Seherin.name);

        addStatement(ALLE_WACHEN_AUF, ALLE_WACHEN_AUF_TITLE);
    }

    public void addStatement(String statement, String title) {
        statements.add(new StatementIndie(statement, title, Statement.INDIE, true));
    }

    //TODO Statement Type implementieren

    public void addStatementRolle(String statement, String title, String rolle) {
        if (Rolle.rolleInNachtEnthalten(rolle)) {
            statements.add(new StatementRolle(statement, title, rolle, Statement.INDIE, true));
        }
    }

    public void addStatementFraktion(String statement, String title, String fraktionsName) {
        Fraktion fraktion = Fraktion.findFraktion(fraktionsName);

        if (Fraktion.fraktionInNachtEnthalten(fraktionsName)) {
            if(fraktion.getNumberOfFraktionsMembersInGame()>1) {
                statements.add(new StatementFraktion(statement, title, fraktionsName, Statement.INDIE, true));
            }
        }
    }
}
