package root.Phases;

import root.Frontend.FrontendControl;
import root.Phases.NightBuilding.Constants.StatementType;
import root.Phases.NightBuilding.*;
import root.ResourceManagement.ImagePath;
import root.Rollen.Constants.DropdownConstants;
import root.Rollen.Constants.WölfinState;
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
import root.Rollen.Hauptrollen.Werwölfe.Chemiker;
import root.Rollen.Hauptrollen.Werwölfe.Schreckenswolf;
import root.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Rollen.Hauptrollen.Überläufer.Überläufer;
import root.Rollen.Nebenrolle;
import root.Rollen.Nebenrollen.*;
import root.Rollen.Rolle;
import root.Spieler;
import root.mechanics.Game;
import root.mechanics.Liebespaar;
import root.mechanics.Opfer;
import root.mechanics.Torte;

import java.util.ArrayList;

public class Nacht extends Thread {
    Game game;

    public static final String ALLE_SCHLAFEN_EIN = "Alle schlafen ein";
    public static final String ALLE_WACHEN_AUF = "Alle wachen auf";

    public static final String WIRT = "Wirt erwacht und entscheidet sich ob er ein Freibier ausgeben will";
    public static final String TOTENGRÄBER = "Totengräber erwacht und entscheidet ob er seine Bonusrollenkarte tauschen möchte";
    public static final String GEFÄNGNISWÄRTER = "Gefängniswärter erwacht und stellt einen Spieler  unter Schutzhaft";
    public static final String ÜBERLÄUFER = "Überläufer erwacht und entscheidet ob er seine Hauptrollenkarte tauschen möchte";
    public static final String HOLDE_MAID = "Holde Maid erwacht und offenbart sich einem Mitspieler";
    public static final String NACHBAR = "Nachbar erwacht und entscheidet welchen Spieler er beobachten möchte";
    public static final String SPURENLESER = "Spurenleser erwacht und entscheidet welchen Spieler er beobachten möchte";
    public static final String LADY_ALEERA = "Lady Aleera erwacht und sieht alle geschützten Spieler";
    public static final String PROSTITUIERTE = "Prostituierte legt sich zu einem Mitspieler ins Bett";
    public static final String RIESE = "Riese erwacht und entscheidet sich ob einen Mitspieler töten möchte";
    public static final String VAMPIRE = "Die Vampire erwachen und wählen ein Opfer aus";
    public static final String GRAF_VLADIMIR = "Graf Vladimir erwacht und macht einen Spieler unerkennbar";
    public static final String WERWÖLFE = "Die Werwölfe erwachen und die Wölfe wählen ein Opfer aus";
    public static final String WÖLFIN = "Wölfin erwacht und wählt ein Opfer aus, wenn sie das tut, erfährt das dorf ihre Bonusrolle";
    public static final String SCHRECKENSWOLF = "Schreckenswolf erwacht und verstummt einen Spieler der am folgenden Tag nichtmehr reden darf";
    public static final String SCHATTENPRIESTER = "Die Schattenpriester erwachen und entscheiden welchen Verstorbenen dieser Nacht sie wiederbeleben und zum Kult hinzufügen möchten";
    public static final String NEUER_SCHATTENPRIESTER = "Der Wiederbelebte erwacht und tauscht seine Karten gegen Schattenkarten";
    public static final String CHEMIKER = "Chemiker erwacht und kann ein Wolfsopfer dieser Nacht wiederbeleben und zum Wolfsrudel hinzufügen";
    public static final String NEUER_WERWOLF = "Der Wiederbelebte erwacht und tauscht seine Hauptrollen- gegen eine Werwolfkarte";
    public static final String MISS_VERONA = "Miss Verona erwacht und lässt sich Auskunft über die Spieler geben, die angegriffen wurden";
    public static final String SPION = "Spion erwacht und fragt den Erzähler nach der Anzahl der verbleibenden Spieler einer Fraktion";
    public static final String ANALYTIKER = "Analytiker erwacht und wählt zwei Spieler, der Erzähler sagt ihm ob diese in derselben Fraktion sind";
    public static final String ARCHIVAR = "Archivar erwacht und lässt sich Auskunft über die Bonusrolle eines Mitspielers geben";
    public static final String SEHERIN = "Seherin erwacht und lässt sich Auskunft über die Fraktion eines Mitspielers geben";
    public static final String ORAKEL = "Orakel erwacht und lässt sich vom Erzähler die Bonusrollenkarte eines zufälligen Bürgers zeigen";
    public static final String SPÄHER = "Späher erwacht und lässt sich Auskunft über einen Mitspieler geben";
    public static final String NACHBAR_INFORMATION = "Nachbar erwacht und erfährt wer die Besucher seines gewählten Spielers waren";
    public static final String SPURENLESER_INFORMATION = "Spurenleser erwacht und erfährt wen der gewählte Spieler besucht hat";
    public static final String WAHRSAGER = "Wahrsager erwacht und gibt seinen Tipp ab welche Fraktion bei der Dorfabstimmung sterben wird";
    public static final String KONDITOR = "Konditor erwacht und entscheidet sich ob es eine gute oder schlechte Torte gibt";
    public static final String KONDITOR_LEHRLING = "Konditor und Konditorlehrling erwachen und entscheiden sich ob es eine gute oder schlechte Torte gibt";
    public static final String OPFER = "Alle Opfer inklusive Liebespaaropfer werden bekannt gegeben";
    public static final String VERSTUMMT = "Der verstummte Spieler wird bekannt gegeben";
    public static final String WÖLFIN_NEBENROLLE = "Das Dorf erfährt die Bonusrolle der Wölfin";

    public static final String PROGRAMM_SCHÜTZE = "[Programm] Schütze";
    public static final String PROGRAMM_OPFER = "[Programm] Opfer";
    public static final String PROGRAMM_TORTE = "[Programm] Torte";
    public static final String PROGRAMM_WAHRSAGER = "[Programm] Wahrsager";

    public static final String ALLE_SCHLAFEN_EIN_TITLE = "Alle schlafen ein";
    public static final String ALLE_WACHEN_AUF_TITLE = "Alle wachen auf";

    public static final String WIRT_TITLE = "Freibier ausgeben";
    public static final String TOTENGRÄBER_TITLE = "Karte tauschen";
    public static final String GEFÄNGNISWÄRTER_TITLE = "Schutzhaft";
    public static final String ÜBERLÄUFER_TITLE = "Karte tauschen";
    public static final String HOLDE_MAID_TITLE = "Mitspieler offenbaren";
    public static final String NACHBAR_TITLE = "Spieler beobachten";
    public static final String SPURENLESER_TITLE = "Spuren lesen von";
    public static final String LADY_ALEERA_TITLE = "Geschützte Spieler";
    public static final String PROSTITUIERTE_TITLE = "Bett legen";
    public static final String RIESE_TITLE = "Mitspieler töten";
    public static final String VAMPIRE_TITLE = "Opfer wählen";
    public static final String GRAF_VLADIMIR_TITLE = "Spieler unerkennbar machen";
    public static final String WERWÖLFE_TITLE = "Opfer wählen";
    public static final String WÖLFIN_TITLE = "Opfer wählen";
    public static final String SCHRECKENSWOLF_TITLE = "Mitspieler verstummen";
    public static final String SCHATTENPRIESTER_TITLE = "Opfer wiederbeleben";
    public static final String NEUER_SCHATTENPRIESTER_TITLE = "Neuer Schattenpriester";
    public static final String CHEMIKER_TITLE = "Opfer wiederbeleben";
    public static final String NEUER_WERWOLF_TITLE = "Neuer Werwolf";
    public static final String MISS_VERONA_TITLE = "Angegriffene Opfer";
    public static final String SPION_TITLE = "Fraktion wählen";
    public static final String ANALYTIKER_TITLE = "Spieler wählen";
    public static final String ARCHIVAR_TITLE = "Spieler wählen";
    public static final String SEHERIN_TITLE = "Spieler wählen";
    public static final String ORAKEL_TITLE = "Bonusrolle";
    public static final String ORAKEL_VERBRAUCHT_TITLE = "Bonusrollen";
    public static final String SPÄHER_TITLE = "Spieler wählen";
    public static final String NACHBAR_INFORMATION_TITLE = "Besucher von ";
    public static final String SPURENLESER_INFORMATION_TITLE = "Besuchte Spieler von ";
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

    public static ArrayList<Spieler> playersAwake = new ArrayList<>();
    public static boolean wölfinKilled;
    public static Spieler wölfinSpieler;
    public static Spieler beschworenerSpieler;

    public Nacht(Game game) {
        this.game = game;
    }

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
            beschworenerSpieler = null;

            ArrayList<String> spielerOrNon = game.getLivingPlayerOrNoneStrings();

            beginNight();

            normaleNachtBuildStatements();

            for (Statement statement : statements) {
                chosenOption = null;

                if (statement.isVisible() || statement.type == StatementType.PROGRAMM) {
                    setPlayersAwake(statement);

                    switch (statement.type) {
                        case SHOW_TITLE:
                            showTitle(statement);
                            break;

                        case ROLLE_CHOOSE_ONE:
                            rolle = ((StatementRolle) statement).getRolle();

                            if (rolle.abilityCharges > 0) {
                                dropdownOtions = rolle.getDropdownOptions();
                                chosenOption = showFrontendControl(statement, dropdownOtions);
                                rolle.processChosenOption(chosenOption);
                            } else {
                                showAufgebrauchtPages(statement); //TODO deaktiv/tot beachten
                            }
                            break;

                        case ROLLE_CHOOSE_ONE_INFO:
                            rolle = ((StatementRolle) statement).getRolle();

                            if (rolle.abilityCharges > 0) {
                                dropdownOtions = rolle.getDropdownOptions();
                                chosenOption = showFrontendControl(statement, dropdownOtions);
                                info = rolle.processChosenOptionGetInfo(chosenOption);
                                showFrontendControl(statement, info);
                            } else {
                                showAufgebrauchtPages(statement); //TODO deaktiv/tot beachten
                            }
                            break;

                        case ROLLE_INFO:
                            rolle = ((StatementRolle) statement).getRolle();

                            info = rolle.getInfo();
                            showFrontendControl(statement, info);
                            break;

                        case ROLLE_SPECAL:
                            rolle = ((StatementRolle) statement).getRolle();
                            break;

                        case FRAKTION_CHOOSE_ONE:
                            Fraktion fraktion = ((StatementFraktion) statement).getFraktion();

                            dropdownOtions = fraktion.getDropdownOptions();
                            chosenOption = showFrontendControl(statement, dropdownOtions);
                            fraktion.processChosenOption(chosenOption);
                            break;
                    }

                    switch (statement.beschreibung) {
                        case WIRT:
                            if (DropdownConstants.JA.name.equals(chosenOption)) {
                                freibier = true;
                            }
                            break;

                        case PROGRAMM_SCHÜTZE:
                            setSchütze();
                            break;

                        case SCHRECKENSWOLF:
                            Schreckenswolf schreckenswolf = (Schreckenswolf) rolle;
                            if (schreckenswolf.werwölfeKilledOnSchutz()) {
                                dropdownOtions = schreckenswolf.getDropdownOptions();
                                chosenOption = showFrontendControl(statement, dropdownOtions);
                                schreckenswolf.processChosenOption(chosenOption);
                            } else {
                                showImage(statement, ImagePath.PASSIV);
                            }
                            break;

                        case WÖLFIN:
                            if (!"".equals(chosenOption)) {
                                wölfinKilled = true;
                                wölfinSpieler = game.findSpielerPerRolle(Wölfin.name);
                            }
                            break;

                        case NEUER_SCHATTENPRIESTER:
                            chosenPlayer = game.findSpieler(chosenOptionLastStatement);
                            String neuerSchattenpriester = "";
                            imagePath = "";
                            if (chosenPlayer != null) {
                                neuerSchattenpriester = chosenPlayer.name;

                                if (!chosenPlayer.hauptrolle.getFraktion().getName().equals(Schattenpriester_Fraktion.name)) {
                                    imagePath = Schattenkutte.imagePath;
                                }
                            }
                            showListShowImage(statement, neuerSchattenpriester, ImagePath.SCHATTENPRIESTER_ICON, imagePath);
                            break;

                        case NEUER_WERWOLF:
                            chosenPlayer = game.findSpieler(chosenOptionLastStatement);
                            String neuerWerwolf = "";
                            if (chosenPlayer != null) {
                                neuerWerwolf = chosenPlayer.name;
                            }

                            showListShowImage(statement, neuerWerwolf, ImagePath.WÖLFE_ICON);
                            break;

                        case ANALYTIKER:
                            Spieler analytikerSpieler = game.findSpielerPerRolle(rolle.getName());
                            if (Rolle.rolleLebend(Analytiker.name)) {
                                ArrayList<String> spielerOrNonWithoutAnalytiker = (ArrayList<String>) spielerOrNon.clone();
                                spielerOrNonWithoutAnalytiker.remove(analytikerSpieler.name);
                                showDropdownPage(statement, spielerOrNonWithoutAnalytiker, spielerOrNonWithoutAnalytiker);
                            } else {
                                showDropdownPage(statement, spielerOrNon, spielerOrNon);
                            }

                            Spieler chosenSpieler1 = game.findSpieler(FrontendControl.erzählerFrame.chosenOption1);
                            Spieler chosenSpieler2 = game.findSpieler(FrontendControl.erzählerFrame.chosenOption2);

                            if (chosenSpieler1 != null && chosenSpieler2 != null) {
                                if (((Analytiker) rolle).showTarnumhang(chosenSpieler1, chosenSpieler2)) {
                                    imagePath = ImagePath.TARNUMHANG;
                                    statement.title = TARNUMHANG_TITLE;
                                    showImage(statement, imagePath);
                                } else {
                                    String answer = ((Analytiker) rolle).analysiere(chosenSpieler1, chosenSpieler2);
                                    showList(statement, answer);//TODO generisch machen
                                }
                            }
                            break;

                        case PROGRAMM_WAHRSAGER:
                            if (Wahrsager.isGuessing) {
                                Spieler wahrsagerSpieler2 = game.findSpielerPerRolle(Wahrsager.name);
                                Spieler deadWahrsagerSpieler = game.findSpielerOrDeadPerRolle(Wahrsager.name);
                                if (wahrsagerSpieler2 != null) {
                                    Wahrsager wahrsager = (Wahrsager) deadWahrsagerSpieler.nebenrolle;
                                    if (wahrsager.guessedRight()) {
                                        //schönlinge.add(wahrsagerSpieler2);
                                    }
                                }
                            } else {
                                Wahrsager.isGuessing = true;
                            }

                            if (!(game.getLivingPlayer().size() > 4)) {
                                Wahrsager.isGuessing = false;
                            }
                            break;

                        case WAHRSAGER:
                            Spieler wahrsagerSpieler1 = game.findSpielerOrDeadPerRolle(Wahrsager.name);
                            if (wahrsagerSpieler1 != null) {
                                Wahrsager wahrsager = (Wahrsager) wahrsagerSpieler1.nebenrolle;

                                wahrsager.tipp = Fraktion.findFraktion(chosenOption);
                            }
                            break;

                        case KONDITOR:
                        case KONDITOR_LEHRLING:
                            //TODO evaluieren ob Page angezeigt werden soll wenn gibtEsTorte();
                            if (Opfer.deadVictims.size() == 0) {
                                if (gibtEsTorte()) {
                                    Torte.torte = true;

                                    dropdownOtions = rolle.getDropdownOptions();
                                    chosenOption = showKonditorDropdownPage(statement, dropdownOtions);
                                    rolle.processChosenOption(chosenOption);

                                    Torte.gut = chosenOption.equals(DropdownConstants.GUT.name);
                                }
                            }
                            break;

                        case PROGRAMM_OPFER:
                            setOpfer();
                            break;

                        case OPFER:
                            ArrayList<String> opferDerNacht = new ArrayList<>();

                            for (Opfer currentOpfer : Opfer.deadVictims) {
                                if (!opferDerNacht.contains(currentOpfer.opfer.name)) {
                                    if (currentOpfer.opfer.nebenrolle.getName().equals(Wahrsager.name)) {
                                        Wahrsager.isGuessing = false;
                                    }
                                    opferDerNacht.add(currentOpfer.opfer.name);
                                }
                            }

                            //showList(statement, opferDerNacht);
                            FrontendControl.erzählerListPage(statement, OPFER_TITLE, opferDerNacht);
                            for (String opfer : opferDerNacht) {
                                FrontendControl.spielerAnnounceVictimPage(game.findSpieler(opfer));
                                waitForAnswer();
                            }


                            refreshHexenSchutz();

                            checkVictory();
                            break;

                        case VERSTUMMT:
                            if (beschworenerSpieler != null) {
                                FrontendControl.erzählerListPage(statement, beschworenerSpieler.name);
                                FrontendControl.spielerIconPicturePage(beschworenerSpieler.name, ImagePath.VERSTUMMT);

                                waitForAnswer();
                            }
                            break;

                        case PROGRAMM_TORTE:
                            if (Torte.torte) {
                                FrontendControl.erzählerTortenPage();
                                FrontendControl.spielerIconPicturePage(TORTE_TITLE, ImagePath.TORTE);

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

        cleanUp();

        if (freibier) {
            game.freibierDay();
        } else {
            game.day();
        }
    }

    public void beginNight() {
        for (Spieler currentSpieler : game.spieler) {
            String fraktionSpieler = currentSpieler.hauptrolle.getFraktion().getName();

            currentSpieler.ressurectable = !fraktionSpieler.equals(Vampire.name);
        }

        Opfer.possibleVictims = new ArrayList<>();
        Opfer.deadVictims = new ArrayList<>();

        for (Hauptrolle currentHauptrolle : game.mainRoles) {
            currentHauptrolle.besuchtLetzteNacht = currentHauptrolle.besucht;
            currentHauptrolle.besucht = null;
        }

        for (Nebenrolle currentNebenrolle : game.secondaryRoles) {
            currentNebenrolle.besuchtLetzteNacht = currentNebenrolle.besucht;
            currentNebenrolle.besucht = null;

            if (currentNebenrolle.getName().equals(Analytiker.name)) {
                ((Analytiker) currentNebenrolle).besuchtAnalysieren = null;
            }
        }

        if (Rolle.rolleLebend(Prostituierte.name)) {
            Spieler prostituierte = game.findSpielerPerRolle(Prostituierte.name);
            Prostituierte.host = prostituierte;
        }

        for (Spieler currentSpieler : game.spieler) {
            Hauptrolle hauptrolleSpieler = currentSpieler.hauptrolle;

            if (hauptrolleSpieler.getName().equals(Schattenpriester.name)) {
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

        GrafVladimir.unerkennbarerSpieler = null;
    }

    public void setSchütze() {
        for (Spieler currentSpieler : game.spieler) {
            String nebenrolleCurrentSpieler = currentSpieler.nebenrolle.getName();

            if (nebenrolleCurrentSpieler.equals(SchwarzeSeele.name)) {
                currentSpieler.geschützt = true;
            }
        }
    }

    public void setOpfer() {
        checkLiebespaar();
        killVictims();
    }

    public void setPlayersAwake(Statement statement) {
        playersAwake.clear();
        if (statement.getClass() == StatementFraktion.class) {
            StatementFraktion statementFraktion = (StatementFraktion) statement;
            playersAwake.addAll(Fraktion.getFraktionsMembers(statementFraktion.fraktion));
        } else if (statement.getClass() == StatementRolle.class) {
            StatementRolle statementRolle = (StatementRolle) statement;
            playersAwake.add(game.findSpielerPerRolle(statementRolle.rolle));
        }
    }

    private void cleanUp() {
        for (Spieler currentSpieler : game.spieler) {
            currentSpieler.aktiv = true;
            currentSpieler.geschützt = false;
            currentSpieler.ressurectable = true;
        }
    }

    public void killVictims() {
        for (Opfer currentVictim : Opfer.deadVictims) {
            if (Rolle.rolleLebend(Blutwolf.name)) {
                if (currentVictim.fraktionsTäter && currentVictim.täter.hauptrolle.getFraktion().getName().equals(Werwölfe.name)) {
                    Blutwolf.deadStacks++;
                    if (Blutwolf.deadStacks >= 2) {
                        Blutwolf.deadly = true;
                    }
                }
            }

            game.killSpieler(currentVictim.opfer);
        }
    }

    public void checkLiebespaar() {
        boolean spieler1Lebend = true;
        boolean spieler2Lebend = true;

        Liebespaar liebespaar = game.liebespaar;

        if (liebespaar != null && liebespaar.spieler1 != null && liebespaar.spieler2 != null) {

            for (Opfer currentVictim : Opfer.deadVictims) {
                if (currentVictim.opfer.name.equals(liebespaar.spieler1.name)) {
                    spieler1Lebend = false;
                }
                if (currentVictim.opfer.name.equals(liebespaar.spieler2.name)) {
                    spieler2Lebend = false;
                }
            }

            if (spieler1Lebend && !spieler2Lebend) {
                Opfer.deadVictims.add(new Opfer(liebespaar.spieler1, liebespaar.spieler2, false));
            }

            if (!spieler1Lebend && spieler2Lebend) {
                Opfer.deadVictims.add(new Opfer(liebespaar.spieler2, liebespaar.spieler1, false));
            }
        }
    }

    public void refreshHexenSchutz() {
        /*if (Rolle.rolleLebend(GuteHexe.name)) {
            GuteHexe guteHexe = (GuteHexe) game.findSpielerPerRolle(GuteHexe.name).hauptrolle;
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
        }*/
    }

    public boolean gibtEsTorte() {
        if (Rolle.rolleLebend(Konditor.name) && !Opfer.isOpferPerRolle(Konditor.name) && Rolle.rolleAktiv(Konditor.name)) {
            return true;
        }

        if (Rolle.rolleLebend(Konditorlehrling.name) && !Opfer.isOpferPerRolle(Konditorlehrling.name) && Rolle.rolleAktiv(Konditorlehrling.name)) {
            return true;
        }

        if (Sammler.isSammlerRolle(Konditor.name) || Sammler.isSammlerRolle(Konditorlehrling.name)) {
            if (Rolle.rolleLebend(Sammler.name) && !Opfer.isOpferPerRolle(Sammler.name) && Rolle.rolleAktiv(Sammler.name)) {
                return true;
            }
        }

        return false;
    }

    public void checkVictory() {
        String victory = game.checkVictory();

        if (victory != null) {
            showEndScreenPage(victory);
        }
    }

    public String showFrontendControl(Statement statement, FrontendControl frontendControl) {
        if (frontendControl.title == null) {
            frontendControl.title = statement.title;
        }

        switch (statement.getState()) {
            case NORMAL:
                switch (frontendControl.typeOfContent) {
                    case TITLE:
                        showTitle(statement, frontendControl.title);
                        break;

                    case DROPDOWN:
                        showDropdown(statement, frontendControl.title, frontendControl.strings);
                        return FrontendControl.erzählerFrame.chosenOption1;

                    case DROPDOWN_LIST:
                        showDropdownList(statement, frontendControl.title, frontendControl.strings);
                        return FrontendControl.erzählerFrame.chosenOption1;

                    case LIST:
                        showList(statement, frontendControl.title, frontendControl.strings);
                        break;

                    case IMAGE:
                        showImage(statement, frontendControl.title, frontendControl.imagePath);
                        break;

                    case CARD:
                        showCard(statement, frontendControl.title, frontendControl.imagePath);
                        break;

                    case LIST_IMAGE:
                        showListShowImage(statement, frontendControl.title, frontendControl.strings, frontendControl.imagePath);
                }
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

    public void showDropdownPage(Statement statement, ArrayList<String> dropdownOptions1, ArrayList<String> dropdownOptions2) {
        switch (statement.getState()) {
            case NORMAL:
                FrontendControl.erzählerDropdownPage(statement, dropdownOptions1, dropdownOptions2);
                FrontendControl.spielerDropdownPage(statement.title, 2);
                break;

            case DEAKTIV:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), ImagePath.DEAKTIVIERT);
                FrontendControl.spielerIconPicturePage(DEAKTIVIERT_TITLE, ImagePath.DEAKTIVIERT);
                break;

            case DEAD:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), ImagePath.TOT);
                FrontendControl.spielerIconPicturePage(TOT_TITLE, ImagePath.TOT);
                break;

            case NOT_IN_GAME:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), ImagePath.AUS_DEM_SPIEL);
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
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), ImagePath.DEAKTIVIERT);
                FrontendControl.spielerIconPicturePage(DEAKTIVIERT_TITLE, ImagePath.DEAKTIVIERT);
            }
        } else {
            FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), ImagePath.AUS_DEM_SPIEL);
            FrontendControl.spielerDropdownPage(statement.title, 1);
        }

        waitForAnswer();

        return FrontendControl.erzählerFrame.chosenOption1;
    }

    public String showKonditorDropdownPage(Statement statement, FrontendControl frontendControl) {
        /*if (Rolle.rolleLebend(Konditor.name) || Rolle.rolleLebend(Konditorlehrling.name)) {
            if (!Opfer.isOpferPerRolle(Konditor.name) || !Opfer.isOpferPerRolle(Konditorlehrling.name)) {
                if (Rolle.rolleAktiv(Konditor.name) || Rolle.rolleAktiv(Konditorlehrling.name)) {*/
        FrontendControl.erzählerDropdownPage(statement, frontendControl.strings);
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

    public void showEndScreenPage(String victory) {
        FrontendControl.erzählerEndScreenPage(victory);
        FrontendControl.spielerEndScreenPage(victory);

        waitForAnswer();
    }

    public void showAufgebrauchtPages(Statement statement) {
        FrontendControl.erzählerIconPicturePage(statement, ImagePath.AUFGEBRAUCHT);
        FrontendControl.spielerIconPicturePage(AUFGEBRAUCHT_TITLE, ImagePath.AUFGEBRAUCHT);

        waitForAnswer();
    }

    //TODO Cases die sowieso gleich aussehen zusammenfassen
    public void showDeaktivPages(Statement statement, FrontendControl frontendControl) {
        String erzählerDeaktiviertIconPath = ImagePath.DEAKTIVIERT;

        switch (frontendControl.typeOfContent) {
            case DROPDOWN:
            case DROPDOWN_LIST:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), erzählerDeaktiviertIconPath);
                FrontendControl.spielerIconPicturePage(DEAKTIVIERT_TITLE, ImagePath.DEAKTIVIERT);

                waitForAnswer();
                break;

            case LIST:
            case LIST_IMAGE:
                FrontendControl.erzählerListPage(statement, getEmptyStringList(), erzählerDeaktiviertIconPath);
                FrontendControl.spielerIconPicturePage(DEAKTIVIERT_TITLE, ImagePath.DEAKTIVIERT);

                waitForAnswer();
                break;

            case TITLE:
            case IMAGE:
            case CARD:
                FrontendControl.erzählerIconPicturePage(statement, erzählerDeaktiviertIconPath);
                FrontendControl.spielerIconPicturePage(DEAKTIVIERT_TITLE, ImagePath.DEAKTIVIERT);

                waitForAnswer();
                break;
        }
    }

    public void showTotPages(Statement statement, FrontendControl frontendControl) {
        switch (frontendControl.typeOfContent) {
            case DROPDOWN:
            case DROPDOWN_LIST:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), ImagePath.TOT);
                FrontendControl.spielerIconPicturePage(TOT_TITLE, ImagePath.TOT);

                waitForAnswer();
                break;

            case LIST:
            case LIST_IMAGE:
                FrontendControl.erzählerListPage(statement, getEmptyStringList(), ImagePath.TOT);
                FrontendControl.spielerIconPicturePage(TOT_TITLE, ImagePath.TOT);

                waitForAnswer();
                break;

            case TITLE:
            case IMAGE:
            case CARD:
                FrontendControl.erzählerIconPicturePage(statement, ImagePath.TOT);
                FrontendControl.spielerIconPicturePage(TOT_TITLE, ImagePath.TOT);

                waitForAnswer();
                break;
        }
    }

    public void showAusDemSpielPages(Statement statement, FrontendControl frontendControl) {
        switch (frontendControl.typeOfContent) {
            case DROPDOWN:
            case DROPDOWN_LIST:
                FrontendControl.erzählerDropdownPage(statement, getEmptyStringList(), ImagePath.AUS_DEM_SPIEL);
                FrontendControl.spielerDropdownPage(statement.title, 1);

                waitForAnswer();
                break;

            case LIST:
            case LIST_IMAGE:
                FrontendControl.erzählerListPage(statement, getEmptyStringList(), ImagePath.AUS_DEM_SPIEL);
                FrontendControl.spielerListPage(statement.title, getEmptyStringList());

                waitForAnswer();
                break;

            case TITLE:
            case IMAGE:
            case CARD:
                FrontendControl.erzählerIconPicturePage(statement, ImagePath.AUS_DEM_SPIEL);
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

    public void showList(Statement statement, ArrayList<String> strings) {
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
        FrontendControl.refreshÜbersichtsFrame();
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void normaleNachtBuildStatements() {
        statements = new ArrayList<>();

        addStatementIndie(ALLE_SCHLAFEN_EIN, ALLE_SCHLAFEN_EIN_TITLE, StatementType.SHOW_TITLE);

        if (Wirt.freibierCharges > 0) {
            addStatementRolle(WIRT, WIRT_TITLE, Wirt.name, StatementType.ROLLE_CHOOSE_ONE);
        }

        if (Totengräber.getNehmbareNebenrollen().size() > 0) {
            addStatementRolle(TOTENGRÄBER, TOTENGRÄBER_TITLE, Totengräber.name, StatementType.ROLLE_CHOOSE_ONE);
        }
        addStatementRolle(GEFÄNGNISWÄRTER, GEFÄNGNISWÄRTER_TITLE, Gefängniswärter.name, StatementType.ROLLE_CHOOSE_ONE);

        if (game.mitteHauptrollen.size() > 0) {
            addStatementRolle(ÜBERLÄUFER, ÜBERLÄUFER_TITLE, Überläufer.name, StatementType.ROLLE_CHOOSE_ONE);
        }
        addStatementRolle(HOLDE_MAID, HOLDE_MAID_TITLE, HoldeMaid.name, StatementType.ROLLE_CHOOSE_ONE);
        addStatementRolle(NACHBAR, NACHBAR_TITLE, Nachbar.name, StatementType.ROLLE_CHOOSE_ONE);
        addStatementRolle(SPURENLESER, SPURENLESER_TITLE, Spurenleser.name, StatementType.ROLLE_CHOOSE_ONE);

        addProgrammStatement(PROGRAMM_SCHÜTZE);

        addStatementRolle(LADY_ALEERA, LADY_ALEERA_TITLE, LadyAleera.name, StatementType.ROLLE_INFO);
        addStatementRolle(PROSTITUIERTE, PROSTITUIERTE_TITLE, Prostituierte.name, StatementType.ROLLE_CHOOSE_ONE);

        addStatementRolle(RIESE, RIESE_TITLE, Riese.name, StatementType.ROLLE_CHOOSE_ONE);
        addStatementFraktion(VAMPIRE, VAMPIRE_TITLE, Vampire.name, StatementType.FRAKTION_CHOOSE_ONE);
        addStatementRolle(GRAF_VLADIMIR, GRAF_VLADIMIR_TITLE, GrafVladimir.name, StatementType.ROLLE_CHOOSE_ONE);
        addStatementFraktion(WERWÖLFE, WERWÖLFE_TITLE, Werwölfe.name, StatementType.FRAKTION_CHOOSE_ONE);
        if (Wölfin.state == WölfinState.TÖTEND) {
            addStatementRolle(WÖLFIN, WÖLFIN_TITLE, Wölfin.name, StatementType.ROLLE_CHOOSE_ONE);
        }
        addStatementRolle(SCHRECKENSWOLF, SCHRECKENSWOLF_TITLE, Schreckenswolf.name, StatementType.ROLLE_SPECAL);

        addStatementFraktion(SCHATTENPRIESTER, SCHATTENPRIESTER_TITLE, Schattenpriester_Fraktion.name, StatementType.FRAKTION_CHOOSE_ONE);
        addStatementFraktion(NEUER_SCHATTENPRIESTER, NEUER_SCHATTENPRIESTER_TITLE, Schattenpriester_Fraktion.name, StatementType.FRAKTION_SPECAL);
        addStatementRolle(CHEMIKER, CHEMIKER_TITLE, Chemiker.name, StatementType.ROLLE_CHOOSE_ONE);
        addStatementRolle(NEUER_WERWOLF, NEUER_WERWOLF_TITLE, Chemiker.name, StatementType.ROLLE_SPECAL); //vll. rolle_info

        addStatementRolle(MISS_VERONA, MISS_VERONA_TITLE, MissVerona.name, StatementType.ROLLE_INFO);
        addStatementRolle(SPION, SPION_TITLE, Spion.name, StatementType.ROLLE_CHOOSE_ONE_INFO);
        addStatementRolle(ANALYTIKER, ANALYTIKER_TITLE, Analytiker.name, StatementType.ROLLE_SPECAL);
        addStatementRolle(ARCHIVAR, ARCHIVAR_TITLE, Archivar.name, StatementType.ROLLE_CHOOSE_ONE_INFO);
        addStatementRolle(SEHERIN, SEHERIN_TITLE, Seherin.name, StatementType.ROLLE_CHOOSE_ONE_INFO);
        addStatementRolle(ORAKEL, ORAKEL_TITLE, Orakel.name, StatementType.ROLLE_INFO);
        addStatementRolle(SPÄHER, SPÄHER_TITLE, Späher.name, StatementType.ROLLE_CHOOSE_ONE_INFO);

        addStatementRolle(NACHBAR_INFORMATION, NACHBAR_INFORMATION_TITLE, Nachbar.name, StatementType.ROLLE_INFO);
        addStatementRolle(SPURENLESER_INFORMATION, SPURENLESER_INFORMATION_TITLE, Spurenleser.name, StatementType.ROLLE_INFO);

        addProgrammStatement(PROGRAMM_WAHRSAGER);
        if (game.getLivingPlayer().size() > 4) {
            addStatementRolle(WAHRSAGER, WAHRSAGER_TITLE, Wahrsager.name, StatementType.ROLLE_CHOOSE_ONE);
        }

        if (game.getSecondaryRoleInGameNames().contains(Konditorlehrling.name)) {
            addStatementRolle(KONDITOR_LEHRLING, KONDITOR_LEHRLING_TITLE, Konditorlehrling.name, StatementType.ROLLE_SPECAL);
        } else {
            addStatementRolle(KONDITOR, KONDITOR_TITLE, Konditor.name, StatementType.ROLLE_SPECAL);
        }

        addStatementIndie(ALLE_WACHEN_AUF, ALLE_WACHEN_AUF_TITLE, StatementType.SHOW_TITLE);

        addProgrammStatement(PROGRAMM_OPFER);
        addStatementIndie(OPFER, OPFER_TITLE, StatementType.INDIE);

        if (Rolle.rolleInNachtEnthalten(Schreckenswolf.name)) { //TODO useless
            addStatementIndie(VERSTUMMT, VERSTUMMT_TITLE, StatementType.INDIE);
        }
        if (Wölfin.state == WölfinState.TÖTEND) {
            addStatementRolle(WÖLFIN_NEBENROLLE, WÖLFIN_NEBENROLLE_TITLE, Wölfin.name, StatementType.ROLLE_INFO);
        }

        addProgrammStatement(PROGRAMM_TORTE);
    }

    public void addProgrammStatement(String statement) {
        statements.add(new StatementProgramm(statement));
    }

    public void addStatementIndie(String statement, String title, StatementType type) {
        statements.add(new StatementIndie(statement, title, type));
    }

    public void addStatementRolle(String statement, String title, String rolle, StatementType type) {
        statements.add(new StatementRolle(statement, title, rolle, type));
    }

    public void addStatementRolle(Rolle rolle, StatementType type) {
        statements.add(new StatementRolle(rolle.statement, rolle.title, rolle.getName(), type));
    }

    public void addStatementFraktion(String statement, String title, String fraktion, StatementType type) {
        statements.add(new StatementFraktion(statement, title, fraktion, type));
    }
}
