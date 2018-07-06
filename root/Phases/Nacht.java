package root.Phases;

import root.Frontend.FrontendControl;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Schattenpriester_Fraktion;
import root.Rollen.Fraktionen.Vampire;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;
import root.Rollen.Hauptrollen.Bürger.*;
import root.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Rollen.Hauptrollen.Vampire.LadyAleera;
import root.Rollen.Hauptrollen.Vampire.MissVerona;
import root.Rollen.Hauptrollen.Werwölfe.Blutwolf;
import root.Rollen.Hauptrollen.Werwölfe.BöseHexe;
import root.Rollen.Hauptrollen.Werwölfe.Chemiker;
import root.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Rollen.Hauptrollen.Überläufer.Überläufer;
import root.Rollen.Nebenrolle;
import root.Rollen.Nebenrollen.*;
import root.Rollen.Rolle;
import root.Spieler;
import root.mechanics.Liebespaar;
import root.mechanics.Opfer;
import root.mechanics.Torte;

import java.util.ArrayList;

public class Nacht extends Thread
{
    public static final String ALLE_SCHLAFEN_EIN = "Alle schlafen ein";
    public static final String ALLE_WACHEN_AUF = "Alle wachen auf";

    public static final String WIRT = "Wirt erwacht und entscheidet sich ob er ein Freibier ausgeben will";
    public static final String TOTENGRÄBER = "Totengräber erwacht und entscheidet ob er seine Bonusrollenkarte tauschen möchte";
    public static final String ANÄSTHESIST = "Anästhesist erwacht und deaktiviert einen Spieler";
    public static final String ANÄSTHESIERTE_SPIELER = "Der anästhesierte Spieler erwacht und erfährt, dass er diese Nacht nicht mehr erwacht";
    public static final String GEFÄNGNISWÄRTER = "Gefängniswärter erwacht und stellt einen Spieler  unter Schutzhaft";
    public static final String ÜBERLÄUFER = "Überläufer erwacht und entscheidet ob er seine Hauptrollenkarte tauschen möchte";
    public static final String HOLDE_MAID = "Holde Maid erwacht und offenbart sich einem Mitspieler";
    public static final String NACHBAR = "Nachbar erwacht und entscheidet welchen Spieler er beobachten möchte";
    public static final String WACHHUND = "Wachhund erwacht und entscheidet welchen Mitspieler er schützen möchte";
    public static final String GUTE_HEXE_SCHÜTZEN = "Gute Hexe erwacht und entscheidet ob sie einen Spieler schützen will";
    public static final String LADY_ALEERA = "Lady Aleera erwacht und sieht alle geschützten Spieler";
    public static final String PROSTITUIERTE = "Prostituierte legt sich zu einem Mitspieler ins Bett";
    public static final String RIESE = "Riese erwacht und entscheidet sich ob einen Mitspieler töten möchte";
    public static final String VAMPIRE = "Die Vampire erwachen und wählen ein Opfer aus";
    public static final String WERWÖLFE = "Die Werwölfe erwachen und die Wölfe wählen ein Opfer aus";
    public static final String WÖLFIN = "Wölfin erwacht und wählt ein Opfer aus, wenn sie das tut, erfährt das dorf ihre Bonusrolle";
    public static final String BÖSE_HEXE = "Böse Hexe erwacht und entscheidet ob sie diese Nacht einen Mitspieler töten möchte";
    public static final String SCHATTENPRIESTER = "Die Schattenpriester erwachen und entscheiden welchen Verstorbenen dieser Nacht sie wiederbeleben und zum Kult hinzufügen möchten";
    public static final String NEUER_SCHATTENPRIESTER = "Der Wiederbelebte erwacht und tauscht seine Karten gegen Schattenkarten";
    public static final String CHEMIKER = "Chemiker erwacht und kann ein Wolfsopfer dieser Nacht wiederbeleben und zum Wolfsrudel hinzufügen";
    public static final String NEUER_WERWOLF = "Der Wiederbelebte erwacht und tauscht seine Hauptrollen- gegen eine Werwolfkarte";
    public static final String GUTE_HEXE_WIEDERBELEBEN = "Gute Hexe erwacht und entscheidet sich ob sie ein Opfer der Nacht wiederbeleben will";
    public static final String MISS_VERONA = "Miss Verona erwacht und lässt sich Auskunft über die Spieler geben, die angegriffen wurden";
    public static final String SPION = "Spion erwacht und fragt den Erzähler nach der Anzahl der verbleibenden Spieler einer Fraktion";
    public static final String ANALYTIKER = "Analytiker erwacht und wählt zwei Spieler, der Erzähler sagt ihm ob diese in derselben Fraktion sind";
    public static final String ARCHIVAR = "Archivar erwacht und lässt sich Auskunft über die Bonusrolle eines Mitspielers geben";
    public static final String SEHERIN = "Seherin erwacht und lässt sich Auskunft über die Fraktion eines Mitspielers geben";
    public static final String ORAKEL = "Orakel erwacht und lässt sich vom Erzähler die Bonusrollenkarte eines zufälligen Bürgers zeigen";
    public static final String SPÄHER = "Späher erwacht und lässt sich Auskunft über einen Mitspieler geben";
    public static final String BUCHHALTER = "Buchhalter erwacht und entscheidet ob er die verbleibenden Hauptrollen erfahren möchte";
    public static final String BESCHWÖRER = "Beschwörer erwacht und wählt einen Mitspieler der verstummt";
    public static final String FRISÖR = "Frisör erwacht und wählt einen Mitspieler den er verschönert";
    public static final String NACHBAR_INFORMATION = "Nachbar erwacht und erfährt wer die Besucher seines gewählten Spielers waren";
    public static final String WACHHUND_INFORMATION = "Wachhund erwacht und erfährt wer die Besucher seines gewählten Spielers waren";
    public static final String WAHRSAGER = "Wahrsager erwacht und gibt seinen Tipp ab welche Fraktion bei der Dorfabstimmung sterben wird";
    public static final String KONDITOR = "Konditor erwacht und entscheidet sich ob es eine gute oder schlechte Torte gibt";
    public static final String KONDITOR_LEHRLING = "Konditor und Konditorlehrling erwachen und entscheiden sich ob es eine gute oder schlechte Torte gibt";
    public static final String OPFER = "Alle Opfer inklusive Liebespaaropfer werden bekannt gegeben";
    public static final String VERSTUMMT = "Der verstummte Spieler wird bekannt gegeben";
    public static final String SCHÖNLINGE = "Die Schönlinge werden bekannt gegeben";
    public static final String WÖLFIN_NEBENROLLE = "Das Dorf erfährt die Bonusrolle der Wölfin";

    public static final String PROGRAMM_SCHÜTZE = "[Programm] Schütze";
    public static final String PROGRAMM_OPFER = "[Programm] Opfer";
    public static final String PROGRAMM_TORTE = "[Programm] Torte";
    public static final String PROGRAMM_WAHRSAGER = "[Programm] Wahrsager";

    public static final String ALLE_SCHLAFEN_EIN_TITLE = "Alle schlafen ein";
    public static final String ALLE_WACHEN_AUF_TITLE = "Alle wachen auf";

    public static final String WIRT_TITLE = "Freibier ausgeben";
    public static final String TOTENGRÄBER_TITLE = "Karte tauschen";
    public static final String ANÄSTHESIST_TITLE = "Mitspieler deaktivieren";
    public static final String ANÄSTHESIERTE_SPIELER_TITLE = "Anästhesiert";
    public static final String GEFÄNGNISWÄRTER_TITLE = "Schutzhaft";
    public static final String ÜBERLÄUFER_TITLE = "Karte tauschen";
    public static final String HOLDE_MAID_TITLE = "Mitspieler offenbaren";
    public static final String NACHBAR_TITLE = "Spieler beobachten";
    public static final String WACHHUND_TITLE = "Spieler bewachen";
    public static final String GUTE_HEXE_SCHÜTZEN_TITLE = "Spieler schützen";
    public static final String LADY_ALEERA_TITLE = "Geschützte Spieler";
    public static final String PROSTITUIERTE_TITLE = "Bett legen";
    public static final String RIESE_TITLE = "Mitspieler töten";
    public static final String VAMPIRE_TITLE = "Opfer wählen";
    public static final String WERWÖLFE_TITLE = "Opfer wählen";
    public static final String WÖLFIN_TITLE = "Opfer wählen";
    public static final String BÖSE_HEXE_TITLE = "Mitspieler töten";
    public static final String SCHATTENPRIESTER_TITLE = "Opfer wiederbeleben";
    public static final String NEUER_SCHATTENPRIESTER_TITLE = "Neuer Schattenpriester";
    public static final String CHEMIKER_TITLE = "Opfer wiederbeleben";
    public static final String NEUER_WERWOLF_TITLE = "Neuer Werwolf";
    public static final String GUTE_HEXE_WIEDERBELEBEN_TITLE = "Opfer wiederbeleben";
    public static final String MISS_VERONA_TITLE = "Angegriffene Opfer";
    public static final String SPION_TITLE = "Fraktion wählen";
    public static final String ANALYTIKER_TITLE = "Spieler wählen";
    public static final String ARCHIVAR_TITLE = "Spieler wählen";
    public static final String SEHERIN_TITLE = "Spieler wählen";
    public static final String ORAKEL_TITLE = "Bonusrolle";
    public static final String ORAKEL_VERBRAUCHT_TITLE = "Bonusrollen";
    public static final String SPÄHER_TITLE = "Spieler wählen";
    public static final String BUCHHALTER_TITLE = "Fähigkeit verbrauchen";
    public static final String BESCHWÖRER_TITLE = "Mitspieler verstummen";
    public static final String FRISÖR_TITLE = "Mitspieler verschönern";
    public static final String NACHBAR_INFORMATION_TITLE = "Besucher von ";
    public static final String WACHHUND_INFORMATION_TITLE = "Besucher von ";
    public static final String WAHRSAGER_TITLE = "Fraktion wählen";
    public static final String KONDITOR_TITLE = "Torte";
    public static final String KONDITOR_LEHRLING_TITLE = KONDITOR_TITLE;
    public static final String OPFER_TITLE = "Opfer der Nacht";
    public static final String VERSTUMMT_TITLE = "Verstummt";
    public static final String SCHÖNLINGE_TITLE = "Schönlinge";
    public static final String WÖLFIN_NEBENROLLE_TITLE = "Wölfin";

    public static final String TORTE_TITLE = "";
    public static final String TARNUMHANG_TITLE = "Tarnumhang";
    public static final String TOT_TITLE = "Tot";
    public static final String DEAKTIVIERT_TITLE = "Deaktiviert";
    public static final String AUFGEBRAUCHT_TITLE = "Aufgebraucht";

    public static ArrayList<Statement> statements;
    public static Object lock;

    ArrayList<Spieler> schönlinge = new ArrayList<>();
    public static boolean wölfinKilled;
    public static Spieler wölfinSpieler;
    public static Spieler beschworenerSpieler;

    public void run() {
        boolean freibier = false;

        lock = new Object();
        synchronized (lock) {
            FrontendControl dropdownOtions;
            FrontendControl info;
            String chosenOption;
            String chosenOptionLastStatement = null;

            Rolle rolle = null;

            String imagePath;

            Opfer chosenOpfer;
            Spieler chosenPlayer;
            wölfinKilled = false;
            wölfinSpieler = null;
            schönlinge = new ArrayList<>();
            beschworenerSpieler = null;

            ArrayList<String> spielerOrNon = Spieler.getLivigPlayerOrNoneStrings();

            beginNight();

            normaleNachtBuildStatements();

            for (Statement statement : statements) {
                chosenOption = null;

                if (statement.visible || statement.type == Statement.PROGRAMM) { //TODO useless?
                    switch (statement.type) {
                        case Statement.SHOW_TITLE:
                            showTitle(statement);
                            break;

                        case Statement.ROLLE_CHOOSE_ONE:
                            rolle = ((StatementRolle) statement).getRolle();

                            if (rolle.abilityCharges > 0) {
                                dropdownOtions = rolle.getDropdownOptions();
                                chosenOption = showFrontendControl(statement, dropdownOtions);
                                rolle.processChosenOption(chosenOption);
                            } else {
                                showAufgebrauchtPages(statement); //TODO deaktiv/tot beachten
                            }
                            break;

                        case Statement.ROLLE_CHOOSE_ONE_INFO:
                            rolle = ((StatementRolle) statement).getRolle();

                            if (rolle.abilityCharges > 0) {
                                dropdownOtions = rolle.getDropdownOptions();
                                chosenOption = showFrontendControl(statement, dropdownOtions);
                                info = rolle.processChosenOptionGetInfo(chosenOption);
                                showFrontendControl(statement, info);
                            } else {
                                if (rolle.getName().equals(Buchhalter.name)) {
                                    info = ((Buchhalter) rolle).getAufgebrauchtPage();
                                    showFrontendControl(statement, info);
                                } else {
                                    showAufgebrauchtPages(statement); //TODO deaktiv/tot beachten
                                }
                            }
                            break;

                        case Statement.ROLLE_INFO:
                            rolle = ((StatementRolle) statement).getRolle();

                            info = rolle.getInfo();
                            showFrontendControl(statement, info);
                            break;

                        case Statement.ROLLE_SPECAL:
                            rolle = ((StatementRolle) statement).getRolle();
                            break;

                        case Statement.FRAKTION_CHOOSE_ONE:
                            Fraktion fraktion = ((StatementFraktion) statement).getFraktion();

                            dropdownOtions = fraktion.getDropdownOptions();
                            chosenOption = showFrontendControl(statement, dropdownOtions);
                            fraktion.processChosenOption(chosenOption);
                            break;
                    }

                    switch (statement.beschreibung) {
                        case WIRT:
                            if (chosenOption != null && chosenOption.equals(Wirt.JA)) {
                                freibier = true;
                            }
                            break;

                        case ANÄSTHESIERTE_SPIELER:
                            chosenPlayer = Spieler.findSpieler(chosenOptionLastStatement);
                            String anästhesierteSpieler = "";
                            if (chosenPlayer != null) {
                                anästhesierteSpieler = chosenPlayer.name;
                            }

                            showListShowImage(statement, anästhesierteSpieler, ResourcePath.DEAKTIVIERT); //TODO anästhesiert Symbol?
                            break;

                        case PROGRAMM_SCHÜTZE:
                            setSchütze();
                            break;

                        case WÖLFIN:
                            if (chosenOption.equals(Wölfin.KILL)) {
                                wölfinKilled = true;
                                wölfinSpieler = Spieler.findSpielerPerRolle(Wölfin.name);
                            }
                            break;

                        case NEUER_SCHATTENPRIESTER:
                            chosenPlayer = Spieler.findSpieler(chosenOptionLastStatement);
                            String neuerSchattenpriester = "";
                            imagePath = "";
                            if (chosenPlayer != null) {
                                neuerSchattenpriester = chosenPlayer.name;

                                if (!chosenPlayer.hauptrolle.getFraktion().getName().equals(Schattenpriester_Fraktion.name)) {
                                    System.out.println("schattenkutte");
                                    imagePath = Schattenkutte.imagePath;
                                }
                            }
                            showListShowImage(statement, neuerSchattenpriester, ResourcePath.SCHATTENPRIESTER_ICON, imagePath);
                            break;

                        case NEUER_WERWOLF:
                            chosenPlayer = Spieler.findSpieler(chosenOptionLastStatement);
                            String neuerWerwolf = "";
                            if (chosenPlayer != null) {
                                neuerWerwolf = chosenPlayer.name;
                            }

                            showListShowImage(statement, neuerWerwolf, ResourcePath.WÖLFE_ICON);
                            break;

                        case GUTE_HEXE_WIEDERBELEBEN:
                            GuteHexe guteHexe = (GuteHexe) Spieler.findSpielerPerRolle(GuteHexe.name).hauptrolle;
                            if (guteHexe.wiederbelebenCharges > 0) {
                                ArrayList<String> erweckbareOpferOrNon = Opfer.getErweckbareStringsOrNon();

                                chosenOption = showAfterDeathDropdownListPage(statement, erweckbareOpferOrNon);

                                chosenOpfer = Opfer.findOpfer(chosenOption);

                                if (chosenOpfer != null) {
                                    guteHexe.wiederbeleben(chosenOpfer);
                                }
                            } else {
                                showAufgebrauchtPages(statement);
                            }
                            break;

                        case ANALYTIKER:
                            Spieler analytikerSpieler = Spieler.findSpielerPerRolle(rolle.getName());
                            if (Rolle.rolleLebend(Analytiker.name)) {
                                ArrayList<String> spielerOrNonWithoutAnalytiker = (ArrayList<String>) spielerOrNon.clone();
                                spielerOrNonWithoutAnalytiker.remove(analytikerSpieler.name);
                                showDropdownPage(statement, spielerOrNonWithoutAnalytiker, spielerOrNonWithoutAnalytiker);
                            } else {
                                showDropdownPage(statement, spielerOrNon, spielerOrNon);
                            }

                            Spieler chosenSpieler1 = Spieler.findSpieler(FrontendControl.erzählerFrame.chosenOption1);
                            Spieler chosenSpieler2 = Spieler.findSpieler(FrontendControl.erzählerFrame.chosenOption2);

                            if (chosenSpieler1 != null && chosenSpieler2 != null) {
                                Analytiker analytiker = (Analytiker) analytikerSpieler.nebenrolle;
                                if (analytiker.showTarnumhang(chosenSpieler1, chosenSpieler2)) {
                                    imagePath = ResourcePath.TARNUMHANG;
                                    statement.title = TARNUMHANG_TITLE;
                                    showImage(statement, imagePath);
                                } else {
                                    String answer = analytiker.analysiere(chosenSpieler1, chosenSpieler2);
                                    showList(statement, answer);//TODO generisch machen
                                }
                            }
                            break;

                        case BESCHWÖRER:
                            chosenPlayer = Spieler.findSpieler(chosenOption);
                            if (chosenPlayer != null) {
                                beschworenerSpieler = chosenPlayer;
                            }
                            break;

                        case FRISÖR:
                            chosenPlayer = Spieler.findSpieler(chosenOption);
                            if (chosenPlayer != null) {
                                schönlinge.add(chosenPlayer);
                            }
                            break;

                        case PROGRAMM_WAHRSAGER:
                            if (Wahrsager.isGuessing) {
                                Spieler wahrsagerSpieler2 = Spieler.findSpielerPerRolle(Wahrsager.name);
                                Spieler deadWahrsagerSpieler = Spieler.findSpielerOrDeadPerRolle(Wahrsager.name);
                                if (wahrsagerSpieler2 != null) {
                                    Wahrsager wahrsager = (Wahrsager) deadWahrsagerSpieler.nebenrolle;
                                    if (wahrsager.guessedRight()) {
                                        schönlinge.add(wahrsagerSpieler2);
                                    }
                                }
                            } else {
                                Wahrsager.isGuessing = true;
                            }

                            if (!(Spieler.getLivigPlayer().size() > 4)) {
                                Wahrsager.isGuessing = false;
                            }
                            break;

                        case WAHRSAGER:
                            Spieler wahrsagerSpieler1 = Spieler.findSpielerOrDeadPerRolle(Wahrsager.name);
                            if (wahrsagerSpieler1 != null) {
                                Wahrsager wahrsager = (Wahrsager) wahrsagerSpieler1.nebenrolle;

                                wahrsager.tipp = Fraktion.findFraktion(chosenOption);
                            }
                            break;

                        case KONDITOR:
                        case KONDITOR_LEHRLING:
                            if (Opfer.deadVictims.size() == 0) {
                                if (Rolle.rolleLebend(Konditor.name) && Rolle.rolleAktiv(Konditor.name) || Rolle.rolleLebend(Konditorlehrling.name) && Rolle.rolleAktiv(Konditorlehrling.name)) {
                                    Torte.torte = true;
                                }

                                dropdownOtions = rolle.getDropdownOptions();
                                chosenOption = showKonditorDropdownPage(statement, dropdownOtions);
                                rolle.processChosenOption(chosenOption);

                                Torte.gut = chosenOption.equals(Konditor.GUT);
                            }
                            break;

                        case PROGRAMM_OPFER:
                            setOpfer();
                            break;

                        case OPFER:
                            ArrayList<String> opferDerNacht = new ArrayList<>();

                            for (Opfer currentOpfer : Opfer.deadVictims) {
                                if (!opferDerNacht.contains(currentOpfer.opfer.name)) {
                                    if(currentOpfer.opfer.nebenrolle.getName().equals(Wahrsager.name)){
                                        Wahrsager.isGuessing = false;
                                    }
                                    opferDerNacht.add(currentOpfer.opfer.name);
                                }
                            }

                            showList(statement, opferDerNacht);

                            refreshHexenSchutz();

                            checkVictory();
                            break;

                        case VERSTUMMT:
                            if (beschworenerSpieler != null) {
                                FrontendControl.erzählerListPage(statement, beschworenerSpieler.name);
                                FrontendControl.spielerIconPicturePage(beschworenerSpieler.name, ResourcePath.VERSTUMMT);

                                waitForAnswer();
                            }
                            break;

                        case SCHÖNLINGE:
                            if (schönlinge != null) {
                                ArrayList<String> schönlingeStringList = new ArrayList<>();
                                for (Spieler spieler : schönlinge) {
                                    if (!schönlingeStringList.contains(spieler.name)) {
                                        schönlingeStringList.add(spieler.name);
                                    }
                                }

                                if (schönlinge.size() == 1) {
                                    Spieler schönling = schönlinge.get(0);
                                    FrontendControl.erzählerListPage(statement, schönling.name);
                                    FrontendControl.spielerIconPicturePage(schönling.name, ResourcePath.SCHÖNLING);
                                    waitForAnswer();
                                } else if (schönlinge.size() > 1) {
                                    showList(statement, schönlingeStringList);
                                }
                            }
                            break;

                        case PROGRAMM_TORTE:
                            if (Torte.torte) {
                                FrontendControl.erzählerTortenPage();
                                FrontendControl.spielerIconPicturePage(TORTE_TITLE, ResourcePath.TORTE);

                                waitForAnswer();
                            }
                            break;
                    }

                    chosenOptionLastStatement = chosenOption;

                    if (freibier) {
                        break;
                    }
                }
            }
        }

        for(String fraktionsName : Fraktion.getFraktionStrings()) {
            if (Fraktion.fraktionOffenkundigTot(fraktionsName)) {
                System.out.println(fraktionsName + " tot");
            } else {
                System.out.println(fraktionsName + " lebendig");
            }
        }
        System.out.println("fertig für diese Nacht");
        System.out.println();
        System.out.println();

        cleanUp();

        if (freibier) {
            PhaseManager.freibierDay();
        }
        else {
            PhaseManager.day();
        }
    }

    public void beginNight() {
        for (Spieler currentSpieler : Spieler.spieler) {
            String fraktionSpieler = currentSpieler.hauptrolle.getFraktion().getName();

            currentSpieler.ressurectable = !fraktionSpieler.equals(Vampire.name);
        }

        Opfer.possibleVictims = new ArrayList<>();
        Opfer.deadVictims = new ArrayList<>();

        for (Hauptrolle currentHauptrolle : Hauptrolle.mainRoles) {
            currentHauptrolle.besuchtLetzteNacht = currentHauptrolle.besucht;
            currentHauptrolle.besucht = null;
            if(currentHauptrolle.getName().equals(GuteHexe.name)) {
                ((GuteHexe)currentHauptrolle).besuchtWiederbeleben = null;
            }
        }
        for (Nebenrolle currentNebenrolle : Nebenrolle.secondaryRoles) {
            currentNebenrolle.besuchtLetzteNacht = currentNebenrolle.besucht;
            currentNebenrolle.besucht = null;

            if(currentNebenrolle.getName().equals(Analytiker.name)) {
                ((Analytiker)currentNebenrolle).besuchtAnalysieren = null;
            }
        }

        if(Rolle.rolleLebend(Prostituierte.name)) {
            Spieler prostituierte = Spieler.findSpielerPerRolle(Prostituierte.name);
            Prostituierte.host = prostituierte;
        }

        for (Spieler currentSpieler : Spieler.spieler) {
            Hauptrolle hauptrolleSpieler = currentSpieler.hauptrolle;

            if (hauptrolleSpieler.getName().equals(Schattenpriester.name)) {
                if (((Schattenpriester) hauptrolleSpieler).neuster) {
                    currentSpieler.geschützt = true;
                    ((Schattenpriester) hauptrolleSpieler).neuster = false;
                }
            }
        }

        if(Torte.torte) {
            for (Spieler currentSpieler : Torte.tortenEsser) {
                if (Torte.gut) {
                    currentSpieler.geschützt = true;
                } else {
                    currentSpieler.aktiv = false;
                }
            }
        }

        Torte.torte = false;
    }

    public void setSchütze() {
        for (Spieler currentSpieler : Spieler.spieler) {
            String hauptrolleCurrentSpieler = currentSpieler.hauptrolle.getName();
            String nebenrolleCurrentSpieler = new Schatten().getName();
            if(currentSpieler.nebenrolle!=null) {
                nebenrolleCurrentSpieler = currentSpieler.nebenrolle.getName();
            }

            if (nebenrolleCurrentSpieler.equals(SchwarzeSeele.name)) {
                currentSpieler.geschützt = true;
            }

            if (hauptrolleCurrentSpieler.equals(GrafVladimir.name) && GrafVladimir.fraktion.getFraktionsMembers().size()>1) {
                currentSpieler.geschützt = true;
            }
        }
    }

    public void setOpfer() {
        checkLiebespaar();
        checkWachhund();
        killVictims();
    }

    private void cleanUp() {
        for (Spieler currentSpieler : Spieler.spieler) {
            currentSpieler.aktiv = true;
            currentSpieler.geschützt = false;
            currentSpieler.ressurectable = true;
        }
    }

    public void killVictims() {
        for (Opfer currentVictim : Opfer.deadVictims) {
            if(Rolle.rolleLebend(Blutwolf.name)) {
                if(currentVictim.fraktionsTäter && currentVictim.täter.hauptrolle.getFraktion().getName().equals(Werwölfe.name)) {
                    Blutwolf.deadStacks++;
                    if(Blutwolf.deadStacks>=2) {
                        Blutwolf.deadly = true;
                    }
                }
            }

            Spieler.killSpieler(currentVictim.opfer);
        }
    }

    public void checkLiebespaar() {
        boolean spieler1Lebend = true;
        boolean spieler2Lebend = true;

        if (Liebespaar.spieler1 != null && Liebespaar.spieler2!=null) {

            for (Opfer currentVictim : Opfer.deadVictims) {
                if (currentVictim.opfer.name.equals(Liebespaar.spieler1.name)) {
                    spieler1Lebend = false;
                }
                if (currentVictim.opfer.name.equals(Liebespaar.spieler2.name)) {
                    spieler2Lebend = false;
                }
            }

            if (spieler1Lebend && !spieler2Lebend) {
                Opfer.deadVictims.add(new Opfer(Liebespaar.spieler1, Liebespaar.spieler2, false));
            }

            if (!spieler1Lebend && spieler2Lebend) {
                Opfer.deadVictims.add(new Opfer(Liebespaar.spieler2, Liebespaar.spieler1, false));
            }
        }
    }

    public void checkWachhund() {
        boolean bewachterSpielerDied = false;
        Spieler wachhundSpieler = Spieler.findSpielerOrDeadPerRolle(Wachhund.name);
        if(wachhundSpieler!=null) {
            Wachhund wachhund = (Wachhund)wachhundSpieler.nebenrolle;
            for (Opfer currentVictim : Opfer.deadVictims) {
                if (wachhund.bewachterSpieler!=null && currentVictim.opfer.name.equals(wachhund.bewachterSpieler.name)) {
                    bewachterSpielerDied = true;
                }
            }

            if(bewachterSpielerDied) {
                Opfer.deadVictims.add(new Opfer(wachhundSpieler, wachhund.bewachterSpieler, false));
            }
        }
    }

    public void refreshHexenSchutz() {
        if (Rolle.rolleLebend(GuteHexe.name)) {
            GuteHexe guteHexe = (GuteHexe) Spieler.findSpielerPerRolle(GuteHexe.name).hauptrolle;
            if (guteHexe.besucht != null) {
                String hexenSchutzSpieler = guteHexe.besucht.name;
                boolean refreshed = false;

                for (Opfer opfer : Opfer.possibleVictims) {
                    if (opfer.opfer.name.equals(hexenSchutzSpieler)) {
                        guteHexe.abilityCharges++;
                        refreshed = true;
                        break;
                    }
                }

                if (!refreshed) {
                    for (Opfer opfer : Opfer.deadVictims) {
                        if (opfer.opfer.name.equals(hexenSchutzSpieler)) {
                            guteHexe.abilityCharges++;
                            break;
                        }
                    }
                }
            }
        }
    }

    public void checkVictory() {
        String victory = Spieler.checkVictory();

        if (victory != null) {
            showEndScreenPage(victory);
        }
    }

    public String showFrontendControl(Statement statement, FrontendControl frontendControl) {
        if (frontendControl.title == null) {
            frontendControl.title = statement.title;
        }

        switch (statement.getState())
        {
            case Statement.NORMAL:
                switch (frontendControl.typeOfContent)
                {
                    case FrontendControl.TITLE:
                        showTitle(statement, frontendControl.title);
                        break;

                    case FrontendControl.DROPDOWN:
                        showDropdown(statement, frontendControl.title, frontendControl.strings);
                        return FrontendControl.erzählerFrame.chosenOption1;

                    case FrontendControl.DROPDOWN_LIST:
                        showDropdownList(statement, frontendControl.title, frontendControl.strings);
                        return FrontendControl.erzählerFrame.chosenOption1;

                    case FrontendControl.LIST:
                        showList(statement, frontendControl.title, frontendControl.strings);
                        break;

                    case FrontendControl.IMAGE:
                        showImage(statement, frontendControl.title, frontendControl.imagePath);
                        break;

                    case FrontendControl.CARD:
                        showCard(statement, frontendControl.title, frontendControl.imagePath);
                        break;

                    case FrontendControl.LIST_IMAGE:
                        showListShowImage(statement, frontendControl.title, frontendControl.strings, frontendControl.imagePath);
                }
                break;

            case Statement.DEAKTIV:
                showDeaktivPages(statement, frontendControl);
                break;

            case Statement.DEAD:
                showTotPages(statement, frontendControl);
                break;

            case Statement.NOT_IN_GAME:
                showAusDemSpielPages(statement, frontendControl);
                break;
        }

        return null;
    }

    public void showDropdownPage(Statement statement, ArrayList<String> dropdownOptions1, ArrayList<String> dropdownOptions2) {
        switch (statement.getState())
        {
            case Statement.NORMAL:
                FrontendControl.erzählerDropdownPage(statement, dropdownOptions1, dropdownOptions2);
                FrontendControl.spielerDropdownPage(statement.title, 2);
                break;

            case Statement.DEAKTIV:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                FrontendControl.spielerIconPicturePage(DEAKTIVIERT_TITLE, ResourcePath.DEAKTIVIERT);
                break;

            case Statement.DEAD:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), ResourcePath.TOT);
                FrontendControl.spielerIconPicturePage(TOT_TITLE, ResourcePath.TOT);
                break;

            case Statement.NOT_IN_GAME:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
                FrontendControl.spielerDropdownPage(statement.title, 2);
                break;
        }

        waitForAnswer();
    }

    public String showAfterDeathDropdownListPage(Statement statement, ArrayList<String> dropdownOptions) {
        if (statement.isLebend()) {
            if (statement.isAktiv()) {
                FrontendControl.erzählerDropdownPage(statement, dropdownOptions);
                FrontendControl.spielerDropdownListPage(statement.title, dropdownOptions);
            } else {
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                FrontendControl.spielerIconPicturePage(DEAKTIVIERT_TITLE, ResourcePath.DEAKTIVIERT);
            }
        } else {
            FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
            FrontendControl.spielerDropdownPage(statement.title, 1);
        }

        waitForAnswer();

        return FrontendControl.erzählerFrame.chosenOption1;
    }

    public String showKonditorDropdownPage(Statement statement, FrontendControl frontendControl) {
        if (Rolle.rolleLebend(Konditor.name) || Rolle.rolleLebend(Konditorlehrling.name)) {
            if (!Opfer.isOpferPerRolle(Konditor.name) || !Opfer.isOpferPerRolle(Konditorlehrling.name)) {
                if (Rolle.rolleAktiv(Konditor.name) || Rolle.rolleAktiv(Konditorlehrling.name)) {
                    FrontendControl.erzählerDropdownPage(statement, frontendControl.strings);

                    switch (frontendControl.typeOfContent)
                    {
                        case FrontendControl.DROPDOWN:
                            FrontendControl.spielerDropdownPage(statement.title, 1);
                            break;

                        case FrontendControl.DROPDOWN_LIST:
                            FrontendControl.spielerDropdownListPage(statement.title, frontendControl.strings);
                            break;
                    }
                } else {
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
        }

        waitForAnswer();

        return FrontendControl.erzählerFrame.chosenOption1;
    }

    public void showEndScreenPage(String victory) {
        FrontendControl.erzählerEndScreenPage(victory);
        FrontendControl.spielerEndScreenPage(victory);

        waitForAnswer();
    }

    public void showAufgebrauchtPages(Statement statement) {
        FrontendControl.erzählerIconPicturePage(statement, ResourcePath.AUFGEBRAUCHT);
        FrontendControl.spielerIconPicturePage(AUFGEBRAUCHT_TITLE, ResourcePath.AUFGEBRAUCHT);

        waitForAnswer();
    }

    //TODO Cases die sowieso gleich aussehen zusammenfassen
    public void showDeaktivPages(Statement statement, FrontendControl frontendControl) {
        switch (frontendControl.typeOfContent) {
            case FrontendControl.DROPDOWN:
            case FrontendControl.DROPDOWN_LIST:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                FrontendControl.spielerIconPicturePage(DEAKTIVIERT_TITLE, ResourcePath.DEAKTIVIERT);

                waitForAnswer();
                break;
                
            case FrontendControl.LIST:
            case FrontendControl.LIST_IMAGE:
                FrontendControl.erzählerListPage(statement, getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                FrontendControl.spielerIconPicturePage(DEAKTIVIERT_TITLE, ResourcePath.DEAKTIVIERT);

                waitForAnswer();
                break;

            case FrontendControl.TITLE:
            case FrontendControl.IMAGE:
            case FrontendControl.CARD:
                FrontendControl.erzählerIconPicturePage(statement, ResourcePath.DEAKTIVIERT);
                FrontendControl.spielerIconPicturePage(DEAKTIVIERT_TITLE, ResourcePath.DEAKTIVIERT);

                waitForAnswer();
                break;
        }
    }
    
    public void showTotPages(Statement statement, FrontendControl frontendControl) {
        switch (frontendControl.typeOfContent) {
            case FrontendControl.DROPDOWN:
            case FrontendControl.DROPDOWN_LIST:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.TOT);
                FrontendControl.spielerIconPicturePage(TOT_TITLE, ResourcePath.TOT);

                waitForAnswer();
                break;
                
            case FrontendControl.LIST:
            case FrontendControl.LIST_IMAGE:
                FrontendControl.erzählerListPage(statement, getEmptyStringList(), ResourcePath.TOT);
                FrontendControl.spielerIconPicturePage(TOT_TITLE, ResourcePath.TOT);

                waitForAnswer();
                break;

            case FrontendControl.TITLE:
            case FrontendControl.IMAGE:
            case FrontendControl.CARD:
                FrontendControl.erzählerIconPicturePage(statement, ResourcePath.TOT);
                FrontendControl.spielerIconPicturePage(TOT_TITLE, ResourcePath.TOT);

                waitForAnswer();
                break;
        }
    }

    public void showAusDemSpielPages(Statement statement, FrontendControl frontendControl) {
        switch (frontendControl.typeOfContent) {
            case FrontendControl.DROPDOWN:
            case FrontendControl.DROPDOWN_LIST:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
                FrontendControl.spielerDropdownPage(statement.title,1);

                waitForAnswer();
                break;

            case FrontendControl.LIST:
            case FrontendControl.LIST_IMAGE:
                FrontendControl.erzählerListPage(statement, getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
                FrontendControl.spielerListPage(statement.title, getEmptyStringList());

                waitForAnswer();
                break;

            case FrontendControl.TITLE:
            case FrontendControl.IMAGE:
            case FrontendControl.CARD:
                FrontendControl.erzählerIconPicturePage(statement, ResourcePath.AUS_DEM_SPIEL);
                FrontendControl.spielerIconPicturePage(statement.title, "");

                waitForAnswer();
                break;
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

    public void showDropdown(Statement statement, String title, ArrayList<String> dropdownOptions) {
        FrontendControl.erzählerDropdownPage(statement, dropdownOptions);
        FrontendControl.spielerDropdownPage(title, 1);

        waitForAnswer();
    }

    public void showDropdownList(Statement statement, String title, ArrayList<String> strings) {
        FrontendControl.erzählerDropdownPage(statement, strings);
        FrontendControl.spielerDropdownListPage(title, strings);

        waitForAnswer();
    }

    public void showList(Statement statement, String string) {
        ArrayList<String> list = new ArrayList<>();
        list.add(string);
        showList(statement, list);
    }

    public void showList(Statement statement, ArrayList<String> strings){
        showList(statement, statement.title, strings);
    }

    public void showList(Statement statement, String title, ArrayList<String> strings) {
        FrontendControl.erzählerListPage(statement, title, strings);
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
        showListShowImage(statement, string, spielerImagePath, "");
    }

    public void showListShowImage(Statement statement, String string, String spielerImagePath, String erzählerImagePath) {
        ArrayList<String> list = new ArrayList<>();
        list.add(string);
        showListShowImage(statement, statement.title, list, spielerImagePath, erzählerImagePath);
    }

    public void showListShowImage(Statement statement, String title, ArrayList<String> strings, String spielerImagePath) {
        showListShowImage(statement, title, strings, spielerImagePath, "");
    }

    public void showListShowImage(Statement statement, String title, ArrayList<String> strings, String spielerImagePath, String erzählerImagePath) {
        FrontendControl.erzählerListPage(statement, strings, erzählerImagePath);
        FrontendControl.spielerIconPicturePage(title, spielerImagePath);

        waitForAnswer();
    }

    public static ArrayList<String> getEmptyStringList() {
        ArrayList<String> emptyContent = new ArrayList<>();
        emptyContent.add("");
        return emptyContent;
    }

    public void waitForAnswer() {
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void normaleNachtBuildStatements() {
        statements = new ArrayList<>();

        addStatementIndie(ALLE_SCHLAFEN_EIN, ALLE_SCHLAFEN_EIN_TITLE, Statement.SHOW_TITLE);

        if(Wirt.freibierCharges > 0) {
            addStatementRolle(WIRT, WIRT_TITLE, Wirt.name, Statement.ROLLE_CHOOSE_ONE);
        }

        addStatementRolle(TOTENGRÄBER, TOTENGRÄBER_TITLE, Totengräber.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(ANÄSTHESIST, ANÄSTHESIST_TITLE, Anästhesist.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(ANÄSTHESIERTE_SPIELER, ANÄSTHESIERTE_SPIELER_TITLE, Anästhesist.name, Statement.ROLLE_SPECAL);
        addStatementRolle(GEFÄNGNISWÄRTER, GEFÄNGNISWÄRTER_TITLE, Gefängniswärter.name, Statement.ROLLE_CHOOSE_ONE);

        addStatementRolle(ÜBERLÄUFER, ÜBERLÄUFER_TITLE, Überläufer.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(HOLDE_MAID, HOLDE_MAID_TITLE, HoldeMaid.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(NACHBAR, NACHBAR_TITLE, Nachbar.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(WACHHUND, WACHHUND_TITLE, Wachhund.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(GUTE_HEXE_SCHÜTZEN, GUTE_HEXE_SCHÜTZEN_TITLE, GuteHexe.name, Statement.ROLLE_CHOOSE_ONE);

        addInvisibleProgrammStatement(PROGRAMM_SCHÜTZE);

        addStatementRolle(LADY_ALEERA, LADY_ALEERA_TITLE, LadyAleera.name, Statement.ROLLE_INFO);
        addStatementRolle(PROSTITUIERTE, PROSTITUIERTE_TITLE, Prostituierte.name, Statement.ROLLE_CHOOSE_ONE);

        addStatementRolle(RIESE, RIESE_TITLE, Riese.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementFraktion(VAMPIRE, VAMPIRE_TITLE, Vampire.name, Statement.FRAKTION_CHOOSE_ONE);
        addStatementFraktion(WERWÖLFE, WERWÖLFE_TITLE, Werwölfe.name, Statement.FRAKTION_CHOOSE_ONE);
        if(Wölfin.modus == Wölfin.TÖTEND) {
            addStatementRolle(WÖLFIN, WÖLFIN_TITLE, Wölfin.name, Statement.ROLLE_CHOOSE_ONE);
        }
        addStatementRolle(BÖSE_HEXE, BÖSE_HEXE_TITLE, BöseHexe.name, Statement.ROLLE_CHOOSE_ONE);

        addStatementFraktion(SCHATTENPRIESTER, SCHATTENPRIESTER_TITLE, Schattenpriester_Fraktion.name, Statement.FRAKTION_CHOOSE_ONE);
        addStatementFraktion(NEUER_SCHATTENPRIESTER, NEUER_SCHATTENPRIESTER_TITLE, Schattenpriester_Fraktion.name, Statement.FRAKTION_SPECAL);
        addStatementRolle(CHEMIKER, CHEMIKER_TITLE, Chemiker.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(NEUER_WERWOLF, NEUER_WERWOLF_TITLE, Chemiker.name, Statement.ROLLE_SPECAL); //vll. rolle_info
        addStatementRolle(GUTE_HEXE_WIEDERBELEBEN, GUTE_HEXE_WIEDERBELEBEN_TITLE, GuteHexe.name, Statement.ROLLE_SPECAL);

        addStatementRolle(MISS_VERONA, MISS_VERONA_TITLE, MissVerona.name, Statement.ROLLE_INFO);
        addStatementRolle(SPION, SPION_TITLE, Spion.name, Statement.ROLLE_CHOOSE_ONE_INFO);
        addStatementRolle(ANALYTIKER, ANALYTIKER_TITLE, Analytiker.name, Statement.ROLLE_SPECAL);
        addStatementRolle(ARCHIVAR, ARCHIVAR_TITLE, Archivar.name, Statement.ROLLE_CHOOSE_ONE_INFO);
        addStatementRolle(SEHERIN, SEHERIN_TITLE, Seherin.name, Statement.ROLLE_CHOOSE_ONE_INFO);
        addStatementRolle(ORAKEL, ORAKEL_TITLE, Orakel.name, Statement.ROLLE_INFO);
        addStatementRolle(SPÄHER, SPÄHER_TITLE, Späher.name, Statement.ROLLE_CHOOSE_ONE_INFO);
        addStatementRolle(BUCHHALTER, BUCHHALTER_TITLE, Buchhalter.name, Statement.ROLLE_CHOOSE_ONE_INFO);

        addStatementRolle(BESCHWÖRER, BESCHWÖRER_TITLE, Beschwörer.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(FRISÖR, FRISÖR_TITLE, Frisör.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(NACHBAR_INFORMATION, NACHBAR_INFORMATION_TITLE, Nachbar.name, Statement.ROLLE_INFO);
        addStatementRolle(WACHHUND_INFORMATION, WACHHUND_INFORMATION_TITLE, Wachhund.name, Statement.ROLLE_INFO);

        addInvisibleProgrammStatement(PROGRAMM_WAHRSAGER);
        if(Spieler.getLivigPlayer().size()>4) {
            addStatementRolle(WAHRSAGER, WAHRSAGER_TITLE, Wahrsager.name, Statement.ROLLE_CHOOSE_ONE);
        }

        if (Rolle.rolleInNachtEnthalten(Konditorlehrling.name)) {
            addStatementRolle(KONDITOR_LEHRLING, KONDITOR_LEHRLING_TITLE, Konditorlehrling.name, Statement.ROLLE_SPECAL);
        } else {
            addStatementRolle(KONDITOR, KONDITOR_TITLE, Konditor.name, Statement.ROLLE_SPECAL);
        }

        addStatementIndie(ALLE_WACHEN_AUF, ALLE_WACHEN_AUF_TITLE, Statement.SHOW_TITLE);

        addInvisibleProgrammStatement(PROGRAMM_OPFER);
        addStatementIndie(OPFER, OPFER_TITLE, Statement.INDIE);

        if(Rolle.rolleInNachtEnthalten(Beschwörer.name)) {
            addStatementIndie(VERSTUMMT, VERSTUMMT_TITLE, Statement.INDIE);
        }
        if(Rolle.rolleInNachtEnthalten(Frisör.name) || Rolle.rolleInNachtEnthalten(Wahrsager.name)) {
            addStatementIndie(SCHÖNLINGE, SCHÖNLINGE_TITLE, Statement.INDIE);
        }
        if(Wölfin.modus == Wölfin.TÖTEND) {
            addStatementRolle(WÖLFIN_NEBENROLLE, WÖLFIN_NEBENROLLE_TITLE, Wölfin.name, Statement.ROLLE_INFO);
        }

        addInvisibleProgrammStatement(PROGRAMM_TORTE);
    }

    public void addStatementIndie(String statement, String title, int type) {
        statements.add(new StatementIndie(statement, title, type, true));
    }

    public void addStatementRolle(String statement, String title, String rolle, int type) {
        if (Rolle.rolleInNachtEnthalten(rolle)) {
            boolean isSammlerStatement = Sammler.isSammlerRolle(rolle);
            statements.add(new StatementRolle(statement, title, rolle, type, true, isSammlerStatement));
        } else {
            statements.add(new StatementRolle(statement, title, rolle, type, false));
        }
    }

    public void addStatementFraktion(String statement, String title, String fraktion, int type) {
        if (Fraktion.fraktionInNachtEnthalten(fraktion)) {
            statements.add(new StatementFraktion(statement, title, fraktion, type, true));
        }
    }

    public void addInvisibleProgrammStatement(String statement) {
        statements.add(new StatementIndie(statement, "", Statement.PROGRAMM, false));
    }
}
