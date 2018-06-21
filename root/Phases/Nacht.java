package root.Phases;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.SpielerFrame;
import root.Frontend.FrontendControl;
import root.Frontend.Page.Page;
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
    public static final String GEFÄNGNISWÄRTER = "Gefängniswärter erwacht und stellt einen Spieler  unter Schutzhaft";
    public static final String ÜBERLÄUFER = "Überläufer erwacht und entscheidet ob er seine Hauptrollenkarte tauschen möchte";
    public static final String NACHBAR = "Nachbar erwacht und entscheidet welchen Spieler er beobachten möchte";
    public static final String WACHHUND = "Wachhund erwacht und entscheidet welchen Mitspieler er schützen möchte";
    public static final String HOLDE_MAID = "Holde Maid erwacht und offenbart sich einem Mitspieler";
    public static final String GUTE_HEXE_SCHÜTZEN = "Gute Hexe erwacht und entscheidet ob sie einen Spieler schützen will";
    public static final String LADY_ALEERA = "Lady Aleera erwacht und sieht alle geschützten Spieler";
    public static final String PROSTITUIERTE = "Prostituierte legt sich zu einem Mitspieler ins Bett";
    public static final String RIESE = "Riese erwacht und entscheidet sich ob einen Mitspieler töten möchte";
    public static final String VAMPIRE = "Die Vampire erwachen und wählen ein Opfer aus";
    public static final String WERWÖLFE = "Die Werwölfe erwachen und wählen ein Opfer aus";
    public static final String WÖLFIN = "Wölfin erwacht und wählt ein Opfer aus";
    public static final String BÖSE_HEXE = "Böse Hexe erwacht und entscheidet ob sie diese Nacht einen Mitspieler töten möchte";
    public static final String SCHATTENPRIESTER = "Die Schattenpriester erwachen und entscheiden ob sie einen der Verstorbenen dieser Nacht wiederbeleben und zum Kult hinzufügen möchten";
    public static final String NEUER_SCHATTENPRIESTER = "Der Wiederbelebte erwacht und tauscht seine Karten gegen Schattenkarten";
    public static final String CHEMIKER = "Chemiker erwacht und entscheidet ob er das Werwolf Opfer dieser Nacht wiederbeleben und zum Wolfsrudel hinzufügen möchte";
    public static final String NEUER_WERWOLF = "Der Wiederbelebte erwacht und tauscht seine Hauptrollen- gegen eine Werwolfkarte";
    public static final String GUTE_HEXE_WIEDERBELEBEN = "Gute Hexe erwacht und entscheidet sich ob sie ein Opfer der Nacht wiederbeleben will";
    public static final String MISS_VERONA = "Miss Verona erwacht und lässt sich Auskunft über die Spieler geben, die angegriffen wurden";
    public static final String SPION = "Spion erwacht und fragt den Erzähler nach der Anzahl der verbleibenden Spieler einer Fraktion";
    public static final String ANALYTIKER = "Analytiker erwacht und wählt zwei Spieler, der Erzähler sagt ihm ob diese in einer Fraktion sind";
    public static final String ARCHIVAR = "Archivar erwacht und lässt sich Auskunft über einen Mitspieler geben";
    public static final String SEHERIN = "Seherin erwacht und lässt sich Auskunft über einen Mitspieler geben";
    public static final String ORAKEL = "Orakel erwacht und lässt sich vom Erzähler die Bonusrollenkarte eines zufälligen Bürgers zeigen";
    public static final String SPÄHER = "Späher erwacht und lässt sich Auskunft über einen Mitspieler geben";
    public static final String BUCHHALTER = "Buchhalter erwacht und entscheidet ob er die verbleibenden Rollen erfahren möchte";
    public static final String WAHRSAGER = "Wahrsager erwacht und gibt seinen Tipp ab welche Fraktion bei der Dorfabstimmung sterben wird";
    public static final String BESCHWÖRER = "Beschwörer erwacht und wählt einen Mitspieler der verstummt";
    public static final String FRISÖR = "Frisör erwacht und wählt einen Mitspieler den er verschönert";
    public static final String NACHBAR_INFORMATION = "Nachbar erwacht und erfährt wer die Besucher seines gewählten Spielers waren";
    public static final String KONDITOR = "Falls es in dieser Nacht keine Opfer gab, wacht der Konditor auf und entscheidet sich ob es eine gute oder schlechte Torte gibt";
    public static final String KONDITOR_LEHRLING = "Falls es in dieser Nacht keine Opfer gab, wacht der Konditor und Konditorlehrling auf und entscheidet sich ob es eine gute oder schlechte Torte gibt";
    public static final String OPFER = "Alle Opfer inklusive Liebespaaropfer werden bekannt gegeben";
    public static final String VERSTUMMT = "Der verstummte Spieler wird bekannt gegeben";
    public static final String SCHÖNLINGE = "Die Schönlinge werden bekannt gegeben";
    public static final String WÖLFIN_BONUSROLLE = "Das Dorf erfährt die Bonusrolle der Wölfin";

    public static final String PROGRAMM_SCHÜTZE = "[Programm] Schütze";
    public static final String PROGRAMM_OPFER = "[Programm] Opfer";
    public static final String PROGRAMM_TORTE = "[Programm] Torte";

    public static final String ALLE_SCHLAFEN_EIN_TITEL = "Alle schlafen ein";
    public static final String ALLE_WACHEN_AUF_TITEL = "Alle wachen auf";

    public static final String WIRT_TITEL = "Freibier ausgeben";
    public static final String TOTENGRÄBER_TITEL = "Karte tauschen";
    public static final String ANÄSTHESIST_TITEL = "Mitspieler deaktivieren";
    public static final String GEFÄNGNISWÄRTER_TITEL = "Schutzhaft";
    public static final String ÜBERLÄUFER_TITEL = "Karte tauschen";
    public static final String NACHBAR_TITEL = "Spieler beobachten";
    public static final String HOLDE_MAID_TITEL = "Mitspieler offenbaren";
    public static final String GUTE_HEXE_SCHÜTZEN_TITEL = "Spieler schützen";
    public static final String LADY_ALEERA_TITEL = "Geschützte Spieler";
    public static final String PROSTITUIERTE_TITEL = "Bett legen";
    public static final String RIESE_TITEL = "Mitspieler töten";
    public static final String VAMPIRE_TITEL = "Opfer wählen";
    public static final String WERWÖLFE_TITEL = "Opfer wählen";
    public static final String WÖLFIN_TITEL = "Opfer wählen";
    public static final String BÖSE_HEXE_TITEL = "Mitspieler töten";
    public static final String SCHATTENPRIESTER_TITEL = "Opfer wiederbeleben";
    public static final String NEUER_SCHATTENPRIESTER_TITEL = "Neuer Schattenpriester";
    public static final String CHEMIKER_TITEL = "Opfer wiederbeleben";
    public static final String NEUER_WERWOLF_TITEL = "Neuer Werwolf";
    public static final String GUTE_HEXE_WIEDERBELEBEN_TITEL = "Opfer wiederbeleben";
    public static final String MISS_VERONA_TITEL = "Angegriffene Opfer";
    public static final String SPION_TITEL = "Fraktion wählen";
    public static final String ANALYTIKER_TITEL = "Spieler wählen";
    public static final String ARCHIVAR_TITEL = "Spieler wählen";
    public static final String SEHERIN_TITEL = "Spieler wählen";
    public static final String ORAKEL_TITEL = "Bonusrolle";
    public static final String ORAKEL_VERBRAUCHT_TITEL = "Bonusrollen";
    public static final String SPÄHER_TITEL = "Spieler wählen";
    public static final String BUCHHALTER_TITEL = "Fähigkeit verbrauchen";
    public static final String WAHRSAGER_TITEL = "Fraktion wählen";
    public static final String BESCHWÖRER_TITEL = "Mitspieler verstummen";
    public static final String FRISÖR_TITEL = "Mitspieler verschönern";
    public static final String NACHBAR_INFORMATION_TITEL = "Besucher";
    public static final String KONDITOR_TITEL = "Torte";
    public static final String KONDITOR_LEHRLING_TITEL = KONDITOR_TITEL;
    public static final String OPFER_TITEL = "Opfer der Nacht";
    public static final String VERSTUMMT_TITEL = "Verstummt";
    public static final String SCHÖNLINGE_TITEL = "Schönlinge";
    public static final String WÖLFIN_BONUSROLLE_TITEL = "Wölfin";

    public static final String TORTE_TITEL = "";
    public static final String PASSIV_TITEL = "Passiv";
    public static final String AKTIV_TITEL = "Aktiv";
    public static final String INFORMATIV_TITEL = "Informativ";
    public static final String TARNUMHANG_TITEL = "Tarnumhang";
    public static final String TOT_TITEL = "Tot";
    public static final String DEAKTIVIERT_TITEL = "Deaktiviert";
    public static final String AUFGEBRAUCHT_TITEL = "Aufgebraucht";
    public static final String TÖTEND_TITEL = "Tötend";
    public static final String NICHT_TÖTEND_TITEL = "Nicht Tötend";

    ErzählerFrame erzählerFrame;
    public static SpielerFrame spielerFrame;
    public static ArrayList<Statement> statements;
    public static Object lock;

    ArrayList<Spieler> schönlinge = new ArrayList<>();

    public Nacht(ErzählerFrame erzählerFrame, SpielerFrame spielerFrame) {
        this.erzählerFrame = erzählerFrame;
        this.spielerFrame = spielerFrame;
    }

    public void run() {
        boolean freibier = false;

        lock = new Object();
        synchronized (lock) {
            FrontendControl dropdownOtions;
            FrontendControl info;
            String feedback;
            String feedbackLastStatement = null;

            Rolle rolle = null;
            Fraktion fraktion;

            String imagePath;

            String chosenOption;
            Opfer chosenOpfer;
            Spieler chosenPlayer;
            boolean wölfinKilled = false;
            Spieler wölfinSpieler = null;
            schönlinge = new ArrayList<>();
            Spieler beschworenerSpieler = null;

            ArrayList<String> spielerOrNon = Spieler.getLivigPlayerOrNoneStrings();

            beginNight();

            normaleNachtBuildStatements();

            for (Statement statement : statements) {
                feedback = null;

                switch (statement.type) {
                    case Statement.SHOW_TITLE:
                        erzählerDefaultNightPage(statement);
                        spielerTitlePage(statement.titel);

                        waitForAnswer();
                        break;

                    case Statement.ROLLE_CHOOSE_ONE:
                    case Statement.ROLLE_CHOOSE_ONE_INFO:
                        rolle = ((StatementRolle)statement).getRolle();

                        if (rolle.abilityCharges > 0) {
                            dropdownOtions = rolle.getDropdownOtions();
                            showDropdownPage(statement, dropdownOtions);
                            feedback = rolle.aktion(erzählerFrame.chosenOption1);
                        } else {
                            showAufgebrauchtPages(statement); //TODO deaktiv/tot beachten
                        }
                        break;

                    case Statement.ROLLE_INFO:
                        rolle = ((StatementRolle)statement).getRolle();
                        info = rolle.getInfo();
                        showInfo(statement, info);
                        break;

                    case Statement.ROLLE_SPECAL:
                        rolle = ((StatementRolle)statement).getRolle();
                        break;

                    case Statement.FRAKTION_CHOOSE_ONE:
                        fraktion = ((StatementFraktion)statement).getFraktion();

                        dropdownOtions = fraktion.getDropdownOtions();
                        showDropdownPage(statement, dropdownOtions);
                        feedback = fraktion.aktion(erzählerFrame.chosenOption1);
                        break;
                }

                switch (statement.beschreibung) {
                    case WIRT:
                        if (feedback != null && feedback.equals(Wirt.JA)) {
                            freibier = true;
                        }
                        break;

                    case GUTE_HEXE_SCHÜTZEN:
                        if(((GuteHexe)rolle).schutzCharges > 0) {
                            chosenOption = choosePlayerOrNonCheckSpammable(statement, rolle);
                            if (chosenOption != null) {
                                ((GuteHexe) rolle).schützen(chosenOption);
                            }
                        } else {
                            showAufgebrauchtPages(statement);
                        }
                        break;

                    case PROGRAMM_SCHÜTZE:
                        setSchütze();
                        break;

                    case WÖLFIN:
                        if(feedback.equals(Wölfin.KILL)) {
                            wölfinKilled = true;
                            wölfinSpieler = Spieler.findSpielerPerRolle(Wölfin.name);
                        }
                        break;

                    case NEUER_SCHATTENPRIESTER:
                        chosenPlayer = Spieler.findSpieler(feedbackLastStatement);
                        String neuerSchattenpriester = "";
                        imagePath = "";
                        if (chosenPlayer != null) {
                            neuerSchattenpriester = chosenPlayer.name;

                            if(chosenPlayer.nebenrolle.getName().equals(Schattenkutte.name)) {
                                imagePath = Schattenkutte.imagePath;
                            }
                        }
                        showListShowTitle(statement, neuerSchattenpriester, imagePath);
                        break;

                    case NEUER_WERWOLF:
                        chosenPlayer = Spieler.findSpieler(feedbackLastStatement);
                        String neuerWerwolf = "";
                        if (chosenPlayer != null) {
                            neuerWerwolf = chosenPlayer.name;
                        }

                        showListShowTitle(statement, neuerWerwolf);

                        break;

                    case GUTE_HEXE_WIEDERBELEBEN:
                        if (rolle.abilityCharges > 0) {
                            ArrayList<String> erweckbareOpferOrNon = Opfer.getErweckbareStringsOrNon();

                            showAfterDeathDropdownListPage(statement, erweckbareOpferOrNon);

                            chosenOpfer = Opfer.findOpfer(erzählerFrame.chosenOption1);

                            if (chosenOpfer != null) {
                                ((GuteHexe) rolle).wiederbeleben(chosenOpfer);
                            }
                        } else {
                            showAufgebrauchtPages(statement);
                        }
                        break;

                    case MISS_VERONA:
                        ArrayList<String> untote = ((MissVerona)rolle).findUntote();

                        showListOnBothScreens(statement, untote);
                        break;

                    case SEHERIN:
                        fraktion = Fraktion.findFraktion(feedback);
                        if (fraktion!=null || feedback!=null && feedback.equals(Nebenrolle.TARNUMHANG)) {
                            if(feedback.equals(Nebenrolle.TARNUMHANG)) {
                                imagePath = ResourcePath.TARNUMHANG;
                                statement.titel = TARNUMHANG_TITEL;
                            } else {
                                imagePath = fraktion.getImagePath();
                                statement.titel = erzählerFrame.chosenOption1;
                            }

                            showImageOnBothScreens(statement, imagePath);

                            statement.titel = SEHERIN_TITEL;
                        }
                        break;

                    case ORAKEL:
                        Nebenrolle randemNebenrolle;

                        if(statement.getState() == Statement.NORMAL) {
                            randemNebenrolle = ((Orakel) rolle).generateRandomNebenrolle();
                            if (randemNebenrolle != null) {
                                displayCard(statement, randemNebenrolle.getImagePath());
                            } else {
                                statement.titel = ORAKEL_VERBRAUCHT_TITEL;
                                showListOnBothScreens(statement, Orakel.geseheneNebenrollen);
                            }
                        } else {
                            displayCard(statement, "");
                        }
                        break;

                    case SPÄHER:
                        if (feedback != null) {
                            imagePath = "";
                            switch(feedback){
                                case Späher.TÖTEND:
                                    imagePath  = ResourcePath.TÖTEND;
                                    statement.titel = TÖTEND_TITEL;
                                    break;

                                case Späher.NICHT_TÖTEND:
                                    imagePath  = ResourcePath.NICHT_TÖTEND;
                                    statement.titel = NICHT_TÖTEND_TITEL;
                                    break;

                                case Späher.TARNUMHANG:
                                    imagePath  = ResourcePath.TARNUMHANG;
                                    statement.titel = TARNUMHANG_TITEL;
                                    break;
                            }

                            showImageOnBothScreens(statement, imagePath);

                            statement.titel = SPÄHER_TITEL;
                        }
                        break;

                    case BUCHHALTER:
                        if (feedback != null && feedback.equals(Buchhalter.JA)) {
                            ArrayList<String> hauptrollenImSpiel = Hauptrolle.getMainRolesAlive();
                            showListOnBothScreens(statement, hauptrollenImSpiel);
                        }
                        break;

                    case WAHRSAGER:
                        Spieler wahrsagerSpieler = Spieler.findSpielerPerRolle(rolle.getName());
                        if(wahrsagerSpieler!=null) {
                            Wahrsager wahrsager = (Wahrsager) wahrsagerSpieler.nebenrolle;
                            if(Wahrsager.allowedToTakeGuesses) {
                                if(wahrsager.guessedRight()) {
                                    schönlinge.add(wahrsagerSpieler);
                                }
                            } else {
                                Wahrsager.allowedToTakeGuesses = true;
                            }

                            wahrsager.tipp = Fraktion.findFraktion(feedback);
                        }
                        break;

                    case ANALYTIKER:
                        if(Rolle.rolleLebend(Analytiker.name)) {
                            Spieler analytikerSpieler = Spieler.findSpielerPerRolle(rolle.getName());
                            spielerOrNon.remove(analytikerSpieler.name);
                            showDropdownPage(statement, spielerOrNon, spielerOrNon);
                            spielerOrNon.add(analytikerSpieler.name);
                        } else {
                            showDropdownPage(statement, spielerOrNon, spielerOrNon);
                        }

                        Spieler chosenSpieler1 = Spieler.findSpieler(erzählerFrame.chosenOption1);
                        Spieler chosenSpieler2 = Spieler.findSpieler(erzählerFrame.chosenOption2);

                        if (chosenSpieler1 != null && chosenSpieler2 != null) {
                            if(((Analytiker)rolle).showTarnumhang(chosenSpieler1, chosenSpieler2)) {
                                imagePath = ResourcePath.TARNUMHANG;
                                statement.titel = TARNUMHANG_TITEL;
                                showImageOnBothScreens(statement, imagePath);
                            } else {
                                String answer = ((Analytiker)rolle).analysiere(chosenSpieler1, chosenSpieler2);
                                showListOnBothScreens(statement, answer);
                            }
                        }
                        break;

                    case ARCHIVAR:
                        if(feedback!=null) {
                            imagePath = "";
                            switch(feedback) {
                                case Nebenrolle.AKTIV:
                                    statement.titel = AKTIV_TITEL;
                                    imagePath =  ResourcePath.AKTIV;
                                    break;

                                case Nebenrolle.PASSIV:
                                    statement.titel = PASSIV_TITEL;
                                    imagePath =  ResourcePath.PASSIV;
                                    break;

                                case Nebenrolle.INFORMATIV:
                                    statement.titel = INFORMATIV_TITEL;
                                    imagePath =  ResourcePath.INFORMATIV;
                                    break;

                                case Nebenrolle.TARNUMHANG:
                                    statement.titel = TARNUMHANG_TITEL;
                                    imagePath =  ResourcePath.TARNUMHANG;
                                    break;
                            }

                            showImageOnBothScreens(statement, imagePath);

                            statement.titel = ARCHIVAR_TITEL;
                        }
                        break;

                    case SPION:
                        fraktion = Fraktion.findFraktion(feedback);
                        if (fraktion != null) {
                            statement.titel = fraktion.getName();

                            int fraktionAnzahl = fraktion.getFraktionsMembers().size();
                            String answer = Integer.toString(fraktionAnzahl);

                            showListOnBothScreens(statement, answer);

                            statement.titel = SPION_TITEL;
                        }
                        break;

                    case BESCHWÖRER:
                        if(feedback!=null) {
                            beschworenerSpieler = Spieler.findSpieler(feedback);
                        }
                        break;

                    case FRISÖR:
                        if(feedback!=null) {
                            schönlinge.add(Spieler.findSpieler(feedback));
                        }
                        break;

                    case NACHBAR_INFORMATION:
                        Spieler nachbarSpieler = Spieler.findSpielerPerRolle(rolle.getName());
                        Nachbar nachbar = (Nachbar)nachbarSpieler.nebenrolle;
                        ArrayList<String> besucher = nachbar.getBesucherStrings();
                        showListOnBothScreens(statement, besucher);
                        break;

                    case KONDITOR:
                    case KONDITOR_LEHRLING:
                        if (Opfer.deadVictims.size() == 0) {
                            if(Rolle.rolleLebend(Konditor.name) && Rolle.rolleAktiv(Konditor.name) || Rolle.rolleLebend(Konditorlehrling.name) && Rolle.rolleAktiv(Konditorlehrling.name)) {
                                Torte.torte = true;
                            }

                            dropdownOtions = rolle.getDropdownOtions();
                            showKonditorDropdownPage(statement, dropdownOtions);
                            feedback = rolle.aktion(erzählerFrame.chosenOption1);

                            Torte.gut = feedback.equals(Konditor.GUT);
                            feedback = null;
                        }
                        break;

                    case PROGRAMM_OPFER:
                        setOpfer();
                        break;

                    case OPFER:
                        ArrayList<String> opferDerNacht = new ArrayList<>();

                        for (Opfer currentOpfer : Opfer.deadVictims) {
                            if(!opferDerNacht.contains(currentOpfer.opfer.name)) {
                                Rolle.mitteHauptrollen.add(currentOpfer.opfer.hauptrolle);
                                Rolle.mitteNebenrollen.add(currentOpfer.opfer.nebenrolle);
                                opferDerNacht.add(currentOpfer.opfer.name);
                            }
                        }

                        showListOnBothScreens(statement, opferDerNacht);

                        if(Rolle.rolleLebend(GuteHexe.name))
                        {
                            refreshHexenSchutz();
                        }

                        String victory = Spieler.checkVictory();

                        if(victory != null) {
                            showEndScreenPage(victory);
                        }
                        break;

                    case WÖLFIN_BONUSROLLE:
                        if(wölfinKilled) {
                            if(wölfinSpieler!=null) {
                                imagePath = wölfinSpieler.nebenrolle.getImagePath();
                                if (wölfinSpieler.nebenrolle.getName().equals(Tarnumhang.name)) {
                                    imagePath = ResourcePath.TARNUMHANG;
                                    statement.titel = TARNUMHANG_TITEL;
                                }
                                showImageOnBothScreens(statement, imagePath);
                            }
                        }
                        break;

                    case VERSTUMMT:
                        if(beschworenerSpieler!=null) {
                            erzählerListPage(statement, beschworenerSpieler.name);
                            spielerIconPicturePage(beschworenerSpieler.name, ResourcePath.VERSTUMMT);
                            waitForAnswer();
                        }
                        break;

                    case SCHÖNLINGE:
                        if(schönlinge!=null) {
                            ArrayList<String> schönlingeStringList = new ArrayList<>();
                            for(Spieler spieler : schönlinge) {
                                if(!schönlingeStringList.contains(spieler.name)){
                                    schönlingeStringList.add(spieler.name);
                                }
                            }

                            if(schönlinge.size()==1) {
                                Spieler schönling = schönlinge.get(0);
                                erzählerListPage(statement, schönling.name);
                                spielerIconPicturePage(schönling.name, ResourcePath.SCHÖNLING);
                                waitForAnswer();
                            } else if(schönlinge.size()>1){
                                showListOnBothScreens(statement, schönlingeStringList);
                            }
                        }
                        break;

                    case PROGRAMM_TORTE:
                        if (Torte.torte) {
                            erzählerTortenPage();
                            spielerIconPicturePage(TORTE_TITEL, ResourcePath.TORTE);

                            waitForAnswer();
                        }
                        break;
                }

                feedbackLastStatement = feedback;

                if (freibier) {
                    break;
                }
            }
        }

        cleanUp();

        if (freibier) {
            PhaseManager.freibierDay(erzählerFrame, spielerFrame);
        }
        else {
            PhaseManager.day(erzählerFrame, spielerFrame);
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
        }
        for (Nebenrolle currentNebenrolle : Nebenrolle.secondaryRoles) {
            currentNebenrolle.besuchtLetzteNacht = currentNebenrolle.besucht;
            currentNebenrolle.besucht = null;
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
                    currentSpieler.hauptrolle.aktiv = false;
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

            if(currentSpieler.hauptrolle.aktiv) {
                if (hauptrolleCurrentSpieler.equals(GrafVladimir.name) && GrafVladimir.fraktion.getFraktionsMembers().size()>1) {
                    currentSpieler.geschützt = true;
                }
            }
        }
    }

    public void setOpfer() {
        if (Liebespaar.spieler1 != null) {
            checkLiebespaar();
        }

        killVictims();
    }

    private void cleanUp() {
        for (Spieler currentSpieler : Spieler.spieler) {
            currentSpieler.aktiv = true;
            currentSpieler.geschützt = false;
            currentSpieler.ressurectable = true;

            currentSpieler.hauptrolle.aktiv = true;
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

            if(Rolle.rolleLebend(Wölfin.name) && Wölfin.modus==Wölfin.WARTEND) {
                if(currentVictim.opfer.hauptrolle.getFraktion().getName().equals(Werwölfe.name)) {
                    Wölfin.modus = Wölfin.TÖTEND;
                }
            }
            killSpieler(currentVictim.opfer);
        }
    }

    public void checkLiebespaar() {
        boolean spieler1Lebend = true;
        boolean spieler2Lebend = true;

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

    public void killSpieler(Spieler spieler) {
        spieler.lebend = false;
    }

    public void refreshHexenSchutz() {
        GuteHexe guteHexe = (GuteHexe)Spieler.findSpielerPerRolle(GuteHexe.name).hauptrolle;
        if(guteHexe.besucht!=null) {
            String hexenSchutzSpieler = guteHexe.besucht.name;
            boolean refreshed = false;

            for (Opfer opfer : Opfer.possibleVictims) {
                if (opfer.opfer.name.equals(hexenSchutzSpieler)) {
                    guteHexe.schutzCharges++;
                    refreshed = true;
                    break;
                }
            }

            if (!refreshed) {
                for (Opfer opfer : Opfer.deadVictims) {
                    if (opfer.opfer.name.equals(hexenSchutzSpieler)) {
                        guteHexe.schutzCharges++;
                        refreshed = true;
                        break;
                    }
                }
            }
        }
    }

    public void showInfo(Statement statement, FrontendControl info) {
        switch (info.typeOfContent) {
            case FrontendControl.STATIC_IMAGE:
                showImageOnBothScreens(statement, info.imagePath);
                break;

            case FrontendControl.STATIC_LIST:
                showListOnBothScreens(statement, info.content);
                break;
        }
    }

    public void displayCard(Statement statement, String imagePath) {
        switch (statement.getState())
        {
            case Statement.NORMAL:
                erzählerCardPicturePage(statement, imagePath);
                spielerCardPicturePage(statement.titel, imagePath);
                break;

            case Statement.DEAKTIV:
                erzählerIconPicturePage(statement, ResourcePath.DEAKTIVIERT);
                spielerFrame.deactivatedPage();
                break;

            case Statement.DEAD:
                erzählerIconPicturePage(statement, ResourcePath.TOT);
                spielerFrame.deadPage();
                break;

            case Statement.NOT_IN_GAME:
                erzählerIconPicturePage(statement, ResourcePath.AUS_DEM_SPIEL);
                spielerCardPicturePage(statement.titel, "");
                break;
        }

        waitForAnswer();
    }

    public void showDeadPages(Statement statement) {
        Statement tot = new StatementIndie(statement.beschreibung, TOT_TITEL, Statement.INDIE, true);

        //TODO generateDeadPage()
        erzählerIconPicturePage(tot, ResourcePath.TOT);
        spielerFrame.deadPage();

        waitForAnswer();
    }

    public void showDeactivatedPages(Statement statement) {
        Statement deaktiviert = new StatementIndie(statement.beschreibung,DEAKTIVIERT_TITEL, Statement.INDIE, true);

        Page nightPage = erzählerFrame.pageFactory.generateDeactivatedPage(deaktiviert);
        erzählerFrame.buildScreenFromPage(nightPage);
        spielerFrame.deactivatedPage();

        waitForAnswer();
    }

    public void showAufgebrauchtPages(Statement statement) {
        Statement aufgebraucht = new StatementIndie(statement.beschreibung, AUFGEBRAUCHT_TITEL, Statement.INDIE, true);

        Page nightPage = erzählerFrame.pageFactory.generateAufgebrauchtPage(aufgebraucht);
        erzählerFrame.buildScreenFromPage(nightPage);
        spielerFrame.aufgebrauchtPage();

        waitForAnswer();
    }

    public void showListShowTitle(Statement statement, String string) {
        showListShowTitle(statement, string, "");
    }

    public void showListShowTitle(Statement statement, ArrayList<String> strings) {
        showListShowTitle(statement, strings, "");
    }

    public void showListShowTitle(Statement statement, String string, String imagePath) {
        ArrayList<String> list = new ArrayList<>();
        list.add(string);
        showListShowTitle(statement, list, imagePath);
    }

    public void showListShowTitle(Statement statement, ArrayList<String> strings, String imagePath) {
        erzählerListPage(statement, strings, imagePath);
        spielerTitlePage(statement.titel);

        waitForAnswer();
    }

    public void showListOnBothScreens(Statement statement, String string) {
        ArrayList<String> list = new ArrayList<>();
        list.add(string);
        showListOnBothScreens(statement, list);
    }

    public void showListOnBothScreens(Statement statement, ArrayList<String> strings) {
        switch (statement.getState())
        {
            case Statement.NORMAL:
                erzählerListPage(statement, strings);
                spielerListPage(statement.titel, strings);
                break;

            case Statement.DEAKTIV:
                erzählerListPage(statement, getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                spielerFrame.deactivatedPage();
                break;

            case Statement.DEAD:
                erzählerListPage(statement, getEmptyStringList(), ResourcePath.TOT);
                spielerFrame.deadPage();
                break;

            case Statement.NOT_IN_GAME:
                erzählerListPage(statement, getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
                spielerListPage(statement.titel, getEmptyStringList());
                break;
        }

        waitForAnswer();
    }

    public void showImageOnBothScreens(Statement statement, String imagePath) {
        erzählerIconPicturePage(statement, imagePath);
        spielerIconPicturePage(statement.titel, imagePath);

        waitForAnswer();
    }

    public void showEndScreenPage(String victory) {
        erzählerEndScreenPage(victory);
        spielerEndScreenPage(victory);

        waitForAnswer();
    }

    public String choosePlayer(Statement statement) {
        ArrayList<String> spieler = Spieler.getLivigPlayerStrings();

        showDropdownPage(statement, spieler);

        return erzählerFrame.chosenOption1;
    }

    public String choosePlayerOrNon(Statement statement) {
        ArrayList<String> spielerOrNon = Spieler.getLivigPlayerOrNoneStrings();

        showDropdownPage(statement, spielerOrNon);

        return erzählerFrame.chosenOption1;
    }

    public String choosePlayerOrNonCheckSpammable(Statement statement, Rolle rolle) {
        ArrayList<String> spielerOrNon = Spieler.getPlayerCheckSpammableStrings(rolle);

        showDropdownPage(statement, spielerOrNon);

        return erzählerFrame.chosenOption1;
    }

    public void showDropdownPage(Statement statement, ArrayList<String> dropdownOptions) {
        FrontendControl frontendControl = new FrontendControl(FrontendControl.DROPDOWN_WITHOUT_SUGGESTIONS, dropdownOptions);
        showDropdownPage(statement, frontendControl);
    }

    public void showDropdownListPage(Statement statement, ArrayList<String> dropdownOptions) {
        FrontendControl frontendControl = new FrontendControl(FrontendControl.DROPDOWN_WITH_SUGGESTIONS, dropdownOptions);
        showDropdownPage(statement, frontendControl);
    }

    public void showDropdownPage(Statement statement, FrontendControl frontendControl) {
        switch (statement.getState())
        {
            case Statement.NORMAL:
                erzählerDropdownPage(statement, frontendControl.content);

                switch (frontendControl.typeOfContent)
                {
                    case FrontendControl.DROPDOWN_WITHOUT_SUGGESTIONS:
                        spielerDropdownPage(statement.titel, 1);
                        break;

                    case FrontendControl.DROPDOWN_WITH_SUGGESTIONS:
                        spielerListMirrorPage(statement.titel, frontendControl.content);
                        break;
                }
                break;

            case Statement.DEAKTIV:
                erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                spielerFrame.deactivatedPage();
                break;

            case Statement.DEAD:
                erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.TOT);
                spielerFrame.deadPage();
                break;

            case Statement.NOT_IN_GAME:
                erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
                spielerDropdownPage(statement.titel,1);
                break;
        }

        waitForAnswer();
    }

    public void showDropdownPage(Statement statement, ArrayList<String> dropdownOptions1, ArrayList<String> dropdownOptions2) {
        switch (statement.getState())
        {
            case Statement.NORMAL:
                erzählerDropdownPage(statement, dropdownOptions1, dropdownOptions2);
                spielerDropdownPage(statement.titel, 2);
                break;

            case Statement.DEAKTIV:
                erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                spielerFrame.deactivatedPage();
                break;

            case Statement.DEAD:
                erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), ResourcePath.TOT);
                spielerFrame.deadPage();
                break;

            case Statement.NOT_IN_GAME:
                erzählerDropdownPage(statement, getEmptyStringList(), getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
                spielerDropdownPage(statement.titel, 2);
                break;
        }

        waitForAnswer();
    }

    public void showAfterDeathDropdownListPage(Statement statement, ArrayList<String> dropdownOptions) {
        if (statement.isLebend()) {
            if (statement.isAktiv()) {
                erzählerDropdownPage(statement, dropdownOptions);
                spielerListMirrorPage(statement.titel, dropdownOptions);
            } else {
                erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                spielerFrame.deactivatedPage();
            }
        } else {
            erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
            spielerDropdownPage(statement.titel, 1);
        }

        waitForAnswer();
    }

    public void showKonditorDropdownPage(Statement statement, FrontendControl frontendControl) {
        if (Rolle.rolleLebend(Konditor.name) || Rolle.rolleLebend(Konditorlehrling.name)) {
            if (!Opfer.isOpferPerRolle(Konditor.name) || !Opfer.isOpferPerRolle(Konditorlehrling.name)) {
                if (Rolle.rolleAktiv(Konditor.name) || Rolle.rolleAktiv(Konditorlehrling.name)) {
                    erzählerDropdownPage(statement, frontendControl.content);

                    switch (frontendControl.typeOfContent)
                    {
                        case FrontendControl.DROPDOWN_WITHOUT_SUGGESTIONS:
                            spielerDropdownPage(statement.titel, 1);
                            break;

                        case FrontendControl.DROPDOWN_WITH_SUGGESTIONS:
                            spielerListMirrorPage(statement.titel, frontendControl.content);
                            break;
                    }
                } else {
                    erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.DEAKTIVIERT);
                    spielerFrame.deactivatedPage();
                }
            } else {
                erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.TOT);
                spielerFrame.deadPage();
            }
        } else {
            erzählerDropdownPage(statement, getEmptyStringList(), ResourcePath.AUS_DEM_SPIEL);
            spielerDropdownPage(statement.titel, 1);
        }

        waitForAnswer();
    }

    public static ArrayList<String> getEmptyStringList() {
        ArrayList<String> emptyContent = new ArrayList<>();
        emptyContent.add("");
        return emptyContent;
    }

    public void erzählerDefaultNightPage(Statement statement) {
        Page nightPage;
        nightPage = erzählerFrame.pageFactory.generateDefaultNightPage(statement);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public void erzählerDropdownPage(Statement statement, ArrayList<String> dropdownOptions) {
        Page nightPage = erzählerFrame.pageFactory.generateDropdownPage(statement, dropdownOptions);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public void erzählerDropdownPage(Statement statement, ArrayList<String> dropdownOptions1, ArrayList<String> dropdownOptions2) {
        Page nightPage = erzählerFrame.pageFactory.generateDropdownPage(statement, dropdownOptions1, dropdownOptions2);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public void erzählerDropdownPage(Statement statement, ArrayList<String> dropdownOptions, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateDropdownPage(statement, dropdownOptions, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public void erzählerDropdownPage(Statement statement, ArrayList<String> dropdownOptions1, ArrayList<String> dropdownOptions2, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateDropdownPage(statement, dropdownOptions1, dropdownOptions2, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public void spielerDropdownPage(String titel, int numberOfDropdowns) {
        spielerFrame.dropDownPage = spielerFrame.pageFactory.generateDropdownPage(titel, numberOfDropdowns);
        spielerFrame.buildScreenFromPage(spielerFrame.dropDownPage);
    }

    public void spielerListMirrorPage(String titel, ArrayList<String> dropdownOptions) {
        spielerFrame.dropDownPage = spielerFrame.pageFactory.generateListMirrorPage(titel, dropdownOptions);
        spielerFrame.buildScreenFromPage(spielerFrame.dropDownPage);
    }

    public void erzählerListPage(Statement statement, String string) {
        ArrayList<String> list = new ArrayList<>();
        list.add(string);
        erzählerListPage(statement, list);
    }

    public void erzählerListPage(Statement statement, ArrayList<String> strings) {
        Page nightPage = erzählerFrame.pageFactory.generateListPage(statement, strings);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public void erzählerListPage(Statement statement, ArrayList<String> strings, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateListPage(statement, strings, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public void spielerListPage(String titel, ArrayList<String> strings) {
        Page nightPage = spielerFrame.pageFactory.generateListPage(titel, strings);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public void erzählerIconPicturePage(Statement statement, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateIconPicturePage(statement, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public void spielerIconPicturePage(String titel, String imagePath) {
        Page nightPage = spielerFrame.pageFactory.generateStaticImagePage(titel, imagePath, true);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public void erzählerCardPicturePage(Statement statement, String imagePath) {
        Page nightPage = erzählerFrame.pageFactory.generateCardPicturePage(statement, imagePath);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public void spielerCardPicturePage(String titel, String imagePath) {
        Page nightPage = spielerFrame.pageFactory.generateStaticImagePage(titel, imagePath, false);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public void spielerTitlePage(String titel) {
        Page nightPage = spielerFrame.pageFactory.generateTitlePage(titel);
        spielerFrame.buildScreenFromPage(nightPage);
    }

    public void erzählerTortenPage() {
        erzählerFrame.tortenPage = erzählerFrame.pageFactory.generateTortenPage();
        erzählerFrame.buildScreenFromPage(erzählerFrame.tortenPage);
    }

    public void erzählerEndScreenPage(String victory) {
        Page nightPage = spielerFrame.pageFactory.generateEndScreenPage(victory);
        erzählerFrame.buildScreenFromPage(nightPage);
    }

    public void spielerEndScreenPage(String victory) {
        Page nightPage = spielerFrame.pageFactory.generateEndScreenPage(victory);
        spielerFrame.buildScreenFromPage(nightPage);
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

        addStatementIndie(ALLE_SCHLAFEN_EIN, ALLE_SCHLAFEN_EIN_TITEL, Statement.SHOW_TITLE);

        if(Wirt.freibierCharges > 0) {
            addStatementRolle(WIRT, WIRT_TITEL, Wirt.name, Statement.ROLLE_CHOOSE_ONE);
        }
        addStatementRolle(TOTENGRÄBER, TOTENGRÄBER_TITEL, Totengräber.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(ANÄSTHESIST, ANÄSTHESIST_TITEL, Anästhesist.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(GEFÄNGNISWÄRTER, GEFÄNGNISWÄRTER_TITEL, Gefängniswärter.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(ÜBERLÄUFER, ÜBERLÄUFER_TITEL, Überläufer.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(NACHBAR, NACHBAR_TITEL, Nachbar.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(HOLDE_MAID, HOLDE_MAID_TITEL, HoldeMaid.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(GUTE_HEXE_SCHÜTZEN, GUTE_HEXE_SCHÜTZEN_TITEL, GuteHexe.name, Statement.ROLLE_SPECAL); //TODO choose one

        addInvisibleProgrammStatement(PROGRAMM_SCHÜTZE);

        addStatementRolle(LADY_ALEERA, LADY_ALEERA_TITEL, LadyAleera.name, Statement.ROLLE_INFO);
        addStatementRolle(PROSTITUIERTE, PROSTITUIERTE_TITEL, Prostituierte.name, Statement.ROLLE_CHOOSE_ONE);

        addStatementRolle(RIESE, RIESE_TITEL, Riese.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementFraktion(VAMPIRE, VAMPIRE_TITEL, Vampire.name, Statement.FRAKTION_CHOOSE_ONE);
        addStatementFraktion(WERWÖLFE, WERWÖLFE_TITEL, Werwölfe.name, Statement.FRAKTION_CHOOSE_ONE);
        if(Wölfin.modus == Wölfin.TÖTEND) {
            addStatementRolle(WÖLFIN, WÖLFIN_TITEL, Wölfin.name, Statement.ROLLE_CHOOSE_ONE);
        }
        addStatementRolle(BÖSE_HEXE, BÖSE_HEXE_TITEL, BöseHexe.name, Statement.ROLLE_CHOOSE_ONE);

        addStatementFraktion(SCHATTENPRIESTER, SCHATTENPRIESTER_TITEL, Schattenpriester_Fraktion.name, Statement.FRAKTION_CHOOSE_ONE);
        addStatementFraktion(NEUER_SCHATTENPRIESTER, NEUER_SCHATTENPRIESTER_TITEL, Schattenpriester_Fraktion.name, Statement.FRAKTION_SPECAL);
        addStatementRolle(CHEMIKER, CHEMIKER_TITEL, Chemiker.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(NEUER_WERWOLF, NEUER_WERWOLF_TITEL, Chemiker.name, Statement.ROLLE_INFO);
        addStatementRolle(GUTE_HEXE_WIEDERBELEBEN, GUTE_HEXE_WIEDERBELEBEN_TITEL, GuteHexe.name, Statement.ROLLE_SPECAL);

        addStatementRolle(MISS_VERONA, MISS_VERONA_TITEL, MissVerona.name, Statement.ROLLE_INFO);
        addStatementRolle(SEHERIN, SEHERIN_TITEL, Seherin.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(ORAKEL, ORAKEL_TITEL, Orakel.name, Statement.ROLLE_INFO);
        addStatementRolle(SPÄHER, SPÄHER_TITEL, Späher.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(BUCHHALTER, BUCHHALTER_TITEL, Buchhalter.name, Statement.ROLLE_CHOOSE_ONE);
        if(Spieler.getLivigPlayer().size()>4) {
            addStatementRolle(WAHRSAGER, WAHRSAGER_TITEL, Wahrsager.name, Statement.ROLLE_CHOOSE_ONE);
        } else {
            //TODO eigenes Statement für Logik
            if(Wahrsager.allowedToTakeGuesses) {
                Spieler wahrsagerSpieler = Spieler.findSpielerPerRolle(Wahrsager.name);
                if(wahrsagerSpieler!=null) {
                    Wahrsager wahrsager = (Wahrsager) wahrsagerSpieler.nebenrolle;
                    if(wahrsager.guessedRight()) {
                        schönlinge.add(wahrsagerSpieler);
                    }
                }
            }
            Wahrsager.allowedToTakeGuesses = false;
        }
        addStatementRolle(ANALYTIKER, ANALYTIKER_TITEL, Analytiker.name, Statement.ROLLE_SPECAL);
        addStatementRolle(ARCHIVAR, ARCHIVAR_TITEL, Archivar.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(SPION, SPION_TITEL, Spion.name, Statement.ROLLE_CHOOSE_ONE);

        addStatementRolle(BESCHWÖRER, BESCHWÖRER_TITEL, Beschwörer.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(FRISÖR, FRISÖR_TITEL, Frisör.name, Statement.ROLLE_CHOOSE_ONE);
        addStatementRolle(NACHBAR_INFORMATION, NACHBAR_INFORMATION_TITEL, Nachbar.name, Statement.ROLLE_INFO);
        if (Rolle.rolleInNachtEnthalten(Konditorlehrling.name)) {
            addStatementRolle(KONDITOR_LEHRLING, KONDITOR_LEHRLING_TITEL, Konditorlehrling.name, Statement.ROLLE_SPECAL);
        } else {
            addStatementRolle(KONDITOR, KONDITOR_TITEL, Konditor.name, Statement.ROLLE_SPECAL);
        }

        addStatementIndie(ALLE_WACHEN_AUF, ALLE_WACHEN_AUF_TITEL, Statement.SHOW_TITLE);

        addInvisibleProgrammStatement(PROGRAMM_OPFER);

        addStatementIndie(OPFER, OPFER_TITEL, Statement.INDIE);
        if(Wölfin.modus == Wölfin.TÖTEND) {
            addStatementRolle(WÖLFIN_BONUSROLLE, WÖLFIN_BONUSROLLE_TITEL, Wölfin.name, Statement.ROLLE_INFO);
        }
        if(Rolle.rolleExists(Beschwörer.name)) {
            addStatementRolle(VERSTUMMT, VERSTUMMT_TITEL, Beschwörer.name, Statement.ROLLE_INFO);
        }
        if(Rolle.rolleExists(Frisör.name) || Rolle.rolleExists(Wahrsager.name)) {
            addStatementIndie(SCHÖNLINGE, SCHÖNLINGE_TITEL, Statement.INDIE);
        }

        addInvisibleProgrammStatement(PROGRAMM_TORTE);
    }

    public void addStatementIndie(String statement, String titel, int type) {
        statements.add(new StatementIndie(statement, titel, type, true));
    }

    public void addStatementRolle(String statement, String titel, String rolle, int type) {
        if (Rolle.rolleInNachtEnthalten(rolle)) {
            statements.add(new StatementRolle(statement, titel, rolle, type, true));
        }
    }

    public void addStatementFraktion(String statement, String titel, String fraktion, int type) {
        if (Fraktion.fraktionInNachtEnthalten(fraktion)) {
            statements.add(new StatementFraktion(statement, titel, fraktion, type, true));
        }
    }

    public void addInvisibleProgrammStatement(String statement) {
        statements.add(new StatementIndie(statement, "", Statement.PROGRAMM, false));
    }
}
