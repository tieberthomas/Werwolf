package root.mechanics;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.ErzählerFrameMode;
import root.Frontend.Frame.ÜbersichtsFrame;
import root.Frontend.FrontendControl;
import root.Persona.*;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Fraktionen.Schattenpriester_Fraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Rollen.Bonusrollen.*;
import root.Persona.Rollen.Constants.WölfinState;
import root.Persona.Rollen.Hauptrollen.Bürger.*;
import root.Persona.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Persona.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Persona.Rollen.Hauptrollen.Vampire.LadyAleera;
import root.Persona.Rollen.Hauptrollen.Vampire.MissVerona;
import root.Persona.Rollen.Hauptrollen.Werwölfe.*;
import root.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Persona.Rollen.Hauptrollen.Überläufer.Überläufer;
import root.Phases.*;
import root.Phases.NightBuilding.NormalNightStatementBuilder;
import root.Spieler;

import java.util.ArrayList;

public class Game {
    public PhaseMode phaseMode;
    public Tag tag;

    public boolean freibier = false;

    public Liebespaar liebespaar;

    public ArrayList<Spieler> spieler = new ArrayList<>();
    public ArrayList<Hauptrolle> mainRoles = new ArrayList<>();
    public ArrayList<Hauptrolle> mainRolesInGame = new ArrayList<>();
    public ArrayList<Bonusrolle> secondaryRoles = new ArrayList<>();
    public ArrayList<Bonusrolle> secondaryRolesInGame = new ArrayList<>();
    public ArrayList<Hauptrolle> mitteHauptrollen = new ArrayList<>();
    public ArrayList<Bonusrolle> mitteBonusrollen = new ArrayList<>();
    public ArrayList<Spieler> playersSpecified = new ArrayList<>();

    public boolean zweiteNacht = true;

    public Game() {
        Persona.game = this;
        Spieler.game = this;
        FrontendControl.game = this;
        Opfer.game = this;
        NormalNightStatementBuilder.game = this;

        phaseMode = PhaseMode.setup;

        spieler = new ArrayList<>();
        mainRolesInGame = new ArrayList<>();
        generateAllAvailableMainRoles();
        secondaryRolesInGame = new ArrayList<>();
        generateAllAvailableBonusRoles();
        mitteHauptrollen = new ArrayList<>();
        mitteBonusrollen = new ArrayList<>();
        playersSpecified = new ArrayList<>();

        liebespaar = new Liebespaar(this);
        Torte.tortenEsser = new ArrayList<>();
    }

    private void generateAllAvailableMainRoles() {
        mainRoles.add(new Bruder());
        mainRoles.add(new Dorfbewohner());
        mainRoles.add(new HoldeMaid());
        mainRoles.add(new Orakel());
        mainRoles.add(new Riese());
        mainRoles.add(new Sammler());
        mainRoles.add(new Schamanin());
        mainRoles.add(new Seherin());
        mainRoles.add(new Späher());
        mainRoles.add(new Wirt());
        mainRoles.add(new Schattenpriester());
        mainRoles.add(new GrafVladimir());
        mainRoles.add(new LadyAleera());
        mainRoles.add(new MissVerona());
        mainRoles.add(new Alphawolf());
        mainRoles.add(new Blutwolf());
        mainRoles.add(new Chemiker());
        mainRoles.add(new Geisterwolf());
        mainRoles.add(new Schreckenswolf());
        mainRoles.add(new Werwolf());
        mainRoles.add(new Wolfsmensch());
        mainRoles.add(new Wölfin());
        mainRoles.add(new Henker());
        mainRoles.add(new Überläufer());
    }

    private void generateAllAvailableBonusRoles() {
        secondaryRoles.add(new Analytiker());
        secondaryRoles.add(new Archivar());
        secondaryRoles.add(new Dieb());
        secondaryRoles.add(new Gefängniswärter());
        //Imitator
        secondaryRoles.add(new Konditor());
        secondaryRoles.add(new Konditorlehrling());
        secondaryRoles.add(new Lamm());
        secondaryRoles.add(new Medium());
        secondaryRoles.add(new Nachbar());
        secondaryRoles.add(new Prostituierte());
        secondaryRoles.add(new Schatten());
        secondaryRoles.add(new Schattenkutte());
        secondaryRoles.add(new Schnüffler());
        secondaryRoles.add(new Seelenlicht());
        secondaryRoles.add(new Spurenleser());
        secondaryRoles.add(new Tarnumhang());
        secondaryRoles.add(new Totengräber());
        secondaryRoles.add(new Vampirumhang());
        secondaryRoles.add(new Wahrsager());
        secondaryRoles.add(new Wolfspelz());
    }

    public ErzählerFrameMode parsePhaseMode() { //TODO automapper?
        if (phaseMode == PhaseMode.tag) {
            return ErzählerFrameMode.tag;
        } else if (phaseMode == PhaseMode.freibierTag) {
            return ErzählerFrameMode.freibierTag;
        } else if (phaseMode == PhaseMode.ersteNacht) {
            return ErzählerFrameMode.ersteNacht;
        } else if (phaseMode == PhaseMode.nacht) {
            return ErzählerFrameMode.nacht;
        } else {
            return ErzählerFrameMode.setup;
        }
    }

    public void startGame(ErzählerFrame erzählerFrame) {
        erzählerFrame.übersichtsFrame = new ÜbersichtsFrame(erzählerFrame, this);
        erzählerFrame.toFront();

        FrontendControl.erzählerFrame = erzählerFrame;
        FrontendControl.spielerFrame = erzählerFrame.spielerFrame;
        FrontendControl.übersichtsFrame = erzählerFrame.übersichtsFrame;

        firstnight(erzählerFrame);

        //TODO structure below doesn't work because of multiThreading
//        while (true) {
//            if(freibier) {
//                freibierDay();
//                freibier = false;
//            } else {
//                day();
//            }
//
//            night();
//        }
    }

    public void firstnight(ErzählerFrame erzählerFrame) {
        erzählerFrame.mode = ErzählerFrameMode.ersteNacht;
        phaseMode = PhaseMode.ersteNacht;
        ErsteNacht ersteNacht = new ErsteNacht(this);
        ersteNacht.start();
    }

    public void night() {
        FrontendControl.erzählerFrame.mode = ErzählerFrameMode.nacht;
        phaseMode = PhaseMode.nacht;
        Nacht nacht = new Nacht(this);
        nacht.start();
    }

    public void day() {
        FrontendControl.erzählerFrame.mode = ErzählerFrameMode.tag;
        phaseMode = PhaseMode.tag;
        tag = new Tag(this);
        tag.start();
    }

    public void freibierDay() {
        FrontendControl.erzählerFrame.mode = ErzählerFrameMode.freibierTag;
        phaseMode = PhaseMode.freibierTag;
        tag = new Tag(this);
        tag.start();
    }

    public Winner checkVictory() {
        ArrayList<Fraktion> fraktionen = Fraktion.getLivingFraktionen();

        switch (fraktionen.size()) {
            case 0:
                return Winner.ALL_DEAD;
            case 1:
                Winner winner = Winner.FRAKTION;
                winner.fraktion = fraktionen.get(0);
                return winner;
            case 2:
                if (getLivingPlayerStrings().size() == 2) {
                    Spieler spieler1 = findSpieler(getLivingPlayerStrings().get(0));
                    Spieler spieler2 = findSpieler(getLivingPlayerStrings().get(1));
                    if (liebespaar != null && ((liebespaar.spieler1 == spieler1 && liebespaar.spieler2 == spieler2) ||
                            (liebespaar.spieler1 == spieler2 && liebespaar.spieler2 == spieler1))) {
                        return Winner.LIEBESPAAR;
                    } else {
                        return Winner.NO_WINNER;
                    }
                } else {
                    return Winner.NO_WINNER;
                }
            default:
                return Winner.NO_WINNER;
        }
    }

    public void killSpieler(Spieler spieler) {
        if (spieler != null && spieler.lebend) {
            spieler.lebend = false;
            Hauptrolle hauptrolle = spieler.hauptrolle;
            Bonusrolle bonusrolle = spieler.bonusrolle;
            mitteHauptrollen.add(hauptrolle);
            mitteBonusrollen.add(bonusrolle);

            if (hauptrolle.name.equals(Schattenpriester.NAME) && !bonusrolle.name.equals(Schatten.NAME)) {
                Schattenpriester_Fraktion.deadSchattenPriester++;
            }

            if (Rolle.rolleLebend(Wölfin.NAME) && Wölfin.state == WölfinState.WARTEND) {
                if (hauptrolle.fraktion.name.equals(Werwölfe.NAME)) {
                    Wölfin.state = WölfinState.TÖTEND;
                }
            }

            if (bonusrolle.equals(Schnüffler.NAME)) {
                ((Schnüffler) bonusrolle).informationen = new ArrayList<>();
            }

            if (bonusrolle.equals(Tarnumhang.NAME)) {
                ((Tarnumhang) bonusrolle).seenPlayers = new ArrayList<>();
            }
        }
    }

    public Spieler findSpieler(String name) {
        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.name.equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }

    public boolean spielerExists(String name) {
        return findSpieler(name) != null;
    }

    public Spieler findSpielerPerRolle(String name) {
        for (Bonusrolle bonusrolle : mitteBonusrollen) {
            if (bonusrolle.name.equals(name)) {
                return findSpielerPerRolle(Sammler.NAME);
            }
        }

        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.hauptrolle.name.equals(name) || currentSpieler.bonusrolle.name.equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }

    public Spieler findSpielerOrDeadPerRolle(String name) {
        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.hauptrolle.name.equals(name) || currentSpieler.bonusrolle.name.equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }

    public ArrayList<Spieler> findSpielersPerRolle(String name) {
        ArrayList<Spieler> spielers = new ArrayList<>();

        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.hauptrolle.name.equals(name) || currentSpieler.bonusrolle.name.equals(name)) {
                spielers.add(currentSpieler);
            }
        }

        return spielers;
    }

    public ArrayList<String> findSpielersStringsPerRolle(String name) {
        ArrayList<String> spielers = new ArrayList<>();

        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.hauptrolle.name.equals(name) || currentSpieler.bonusrolle.name.equals(name)) {
                spielers.add(currentSpieler.name);
            }
        }

        return spielers;
    }

    public ArrayList<Spieler> getLivingPlayer() {
        ArrayList<Spieler> allSpieler = new ArrayList<>();

        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.lebend) {
                allSpieler.add(currentSpieler);
            }
        }

        return allSpieler;
    }

    public ArrayList<String> getLivingPlayerStrings() {
        ArrayList<String> allSpieler = new ArrayList<>();

        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.lebend) {
                allSpieler.add(currentSpieler.name);
            }
        }

        return allSpieler;
    }

    public ArrayList<String> getLivingPlayerOrNoneStrings() {
        ArrayList<String> allSpieler = getLivingPlayerStrings();
        allSpieler.add("");

        return allSpieler;
    }

    public FrontendControl getPlayerFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN;
        frontendControl.dropdownStrings = getLivingPlayerOrNoneStrings();

        return frontendControl;
    }

    public ArrayList<String> getPlayerCheckSpammableStrings(Rolle rolle) {
        ArrayList<String> allSpieler = getLivingPlayerOrNoneStrings();
        if (!rolle.spammable && rolle.besuchtLetzteNacht != null) {
            allSpieler.remove(rolle.besuchtLetzteNacht.name);
        }

        return allSpieler;
    }

    public FrontendControl getPlayerCheckSpammableFrontendControl(Rolle rolle) {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN;
        frontendControl.dropdownStrings = getPlayerCheckSpammableStrings(rolle);

        return frontendControl;
    }

    public ArrayList<String> getMitspielerCheckSpammableStrings(Rolle rolle) {
        Spieler spieler = findSpielerPerRolle(rolle.name);

        ArrayList<String> mitspieler = getPlayerCheckSpammableStrings(rolle);
        if (spieler != null) {
            mitspieler.remove(spieler.name);
        }

        return mitspieler;
    }

    public FrontendControl getMitspielerCheckSpammableFrontendControl(Rolle rolle) {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN;
        frontendControl.dropdownStrings = getMitspielerCheckSpammableStrings(rolle);

        return frontendControl;
    }

    public ArrayList<String> getMainRoleNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (Hauptrolle hauptrolle : mainRoles) {
            names.add(hauptrolle.name);
        }

        return names;
    }

    public ArrayList<String> getMainRoleInGameNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (Hauptrolle hauptrolle : mainRolesInGame) {
            names.add(hauptrolle.name);
        }

        return names;
    }

    public ArrayList<String> getPossibleInGameMainRoleNames() {
        ArrayList<String> mainRolesInGame = getMainRoleInGameNames();

        for (Hauptrolle hauptrolle : mitteHauptrollen) {
            if (!hauptrolle.name.equals(Schattenpriester.NAME)) {
                mainRolesInGame.remove(hauptrolle.name);
            }
        }

        for (int i = 0; i < Schattenpriester_Fraktion.deadSchattenPriester; i++) {
            mainRolesInGame.remove(Schattenpriester.NAME);
        }

        return mainRolesInGame;
    }

    public ArrayList<Hauptrolle> getStillAvailableMainRoles() {
        ArrayList<Hauptrolle> stilleAvalableMainRoles = new ArrayList<>();

        stilleAvalableMainRoles.addAll(mainRolesInGame);

        for (Spieler spieler : spieler) {
            stilleAvalableMainRoles.remove(spieler.hauptrolle);
        }

        return stilleAvalableMainRoles;
    }

    public ArrayList<Hauptrolle> getStillAvailableBürger() {
        ArrayList<Hauptrolle> mainroles = getStillAvailableMainRoles();
        ArrayList<Hauptrolle> bürger = new ArrayList<>();

        for (Hauptrolle hauptrolle : mainroles) {
            if (hauptrolle.fraktion.equals(Bürger.NAME)) {
                bürger.add(hauptrolle);
            }
        }

        return bürger;
    }

    public ArrayList<String> getStillAvailableMainRoleNames() {
        ArrayList<Hauptrolle> stilleAvalableMainRoles = getStillAvailableMainRoles();
        ArrayList<String> names = new ArrayList<>();

        for (Hauptrolle hauptrolle : stilleAvalableMainRoles) {
            names.add(hauptrolle.name);
        }

        return names;
    }

    public Hauptrolle findHauptrolle(String wantedName) {
        for (Hauptrolle hauptrolle : mainRoles) {
            if (hauptrolle.name.equals(wantedName))
                return hauptrolle;
        }

        return null;
    }

    public int numberOfOccurencesOfMainRoleInGame(Hauptrolle hauptrolle) {
        int occurences = 0;
        for (Hauptrolle currentHauptrolle : mainRolesInGame) {
            if (currentHauptrolle.name.equals(hauptrolle.name)) {
                occurences++;
            }
        }

        return occurences;
    }

    public void addAllMainRolesToGame() {
        mainRolesInGame.addAll(mainRoles);
        mainRolesInGame.remove(findHauptrolle(Bruder.NAME));
        mainRolesInGame.add(new Bruder());
        mainRolesInGame.add(new Bruder()); //zum sortieren der liste
        mainRolesInGame.remove(findHauptrolle(Dorfbewohner.NAME));
    }

    public ArrayList<String> getSecondaryRoleNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (Bonusrolle bonusrolle : secondaryRoles) {
            names.add(bonusrolle.name);
        }

        return names;
    }

    public ArrayList<String> getSecondaryRoleInGameNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (Bonusrolle bonusrolle : secondaryRolesInGame) {
            names.add(bonusrolle.name);
        }

        return names;
    }

    public ArrayList<String> getPossibleInGameSecondaryRoleNames() {
        ArrayList<String> secondaryRoleInGameNames = getSecondaryRoleInGameNames();

        for (Bonusrolle bonusrolle : mitteBonusrollen) {
            secondaryRoleInGameNames.remove(bonusrolle.name);
        }

        return secondaryRoleInGameNames;
    }

    public ArrayList<Bonusrolle> getStillAvailableBonusRoles() {
        ArrayList<Bonusrolle> stilleAvalableSecondaryRoles = new ArrayList<>();

        stilleAvalableSecondaryRoles.addAll(secondaryRolesInGame);

        for (Spieler spieler : spieler) {
            stilleAvalableSecondaryRoles.remove(spieler.bonusrolle);
        }


        return stilleAvalableSecondaryRoles;
    }

    public ArrayList<String> getStillAvailableBonusRoleNames() {
        ArrayList<Bonusrolle> stilleAvalableSecondaryRoles = getStillAvailableBonusRoles();
        ArrayList<String> names = new ArrayList<>();

        for (Bonusrolle bonusrolle : stilleAvalableSecondaryRoles) {
            names.add(bonusrolle.name);
        }

        return names;
    }

    public Bonusrolle findBonusrolle(String wantedName) {
        for (Bonusrolle bonusrolle : secondaryRoles) {
            if (bonusrolle.name.equals(wantedName))
                return bonusrolle;
        }

        return null;
    }

    public int numberOfOccurencesOfSecondaryRoleInGame(Bonusrolle bonusrolle) {
        int occurences = 0;
        for (Bonusrolle currentBonusrolle : secondaryRolesInGame) {
            if (currentBonusrolle.name.equals(bonusrolle.name)) {
                occurences++;
            }
        }

        return occurences;
    }

    public void addAllSecondaryRoles() {
        secondaryRolesInGame.addAll(secondaryRoles);
        secondaryRolesInGame.remove(findBonusrolle(Schatten.NAME));
    }

    public ArrayList<Spieler> getPlayersUnspecified() {
        ArrayList<Spieler> playersUnspecified = new ArrayList<Spieler>();
        playersUnspecified = (ArrayList) spieler.clone();
        playersUnspecified.removeAll(playersSpecified);
        return playersUnspecified;
    }

    public ArrayList<String> getPlayersUnspecifiedStrings() {
        ArrayList<String> playersUnspecifiedStrings = new ArrayList<>();

        for (Spieler spieler : getPlayersUnspecified()) {
            playersUnspecifiedStrings.add(spieler.name);
        }

        return playersUnspecifiedStrings;
    }

    public ArrayList<Hauptrolle> getMainRolesSpecified() {
        ArrayList<Hauptrolle> mainRolesSpecified = new ArrayList<>();

        for (Spieler spieler : playersSpecified) {
            mainRolesSpecified.add(spieler.hauptrolle);
        }

        return mainRolesSpecified;
    }

    public ArrayList<String> getMainRolesSpecifiedStrings() {
        ArrayList<String> mainRolesSpecifiedStrings = new ArrayList<>();

        for (Hauptrolle hauptrolle : getMainRolesSpecified()) {
            if (hauptrolle != null) {
                mainRolesSpecifiedStrings.add(hauptrolle.name);
            }
        }

        return mainRolesSpecifiedStrings;
    }

    public ArrayList<Hauptrolle> getMainRolesUnspecified() {
        ArrayList<Hauptrolle> mainRolesUnspecified = (ArrayList) mainRolesInGame.clone();

        MyCollectionHelper.removeAllHauptrollen(mainRolesUnspecified, getMainRolesSpecified());

        return mainRolesUnspecified;
    }

    public ArrayList<String> getMainRolesUnspecifiedStrings() {
        ArrayList<String> mainRolesUnspecifiedStrings = new ArrayList<>();

        for (Hauptrolle hauptrolle : getMainRolesUnspecified()) {
            mainRolesUnspecifiedStrings.add(hauptrolle.name);
        }

        return mainRolesUnspecifiedStrings;
    }

    public ArrayList<Bonusrolle> getSecondaryRolesSpecified() {
        ArrayList<Bonusrolle> secondaryRolesSpecified = new ArrayList<>();

        for (Spieler spieler : playersSpecified) {
            secondaryRolesSpecified.add(spieler.bonusrolle);
        }

        return secondaryRolesSpecified;
    }

    public ArrayList<String> getSecondaryRoleSpecifiedStrings() {
        ArrayList<String> secondaryRolesSpecifiedStrings = new ArrayList<>();

        for (Bonusrolle bonusrolle : getSecondaryRolesSpecified()) {
            if (bonusrolle != null) {
                secondaryRolesSpecifiedStrings.add(bonusrolle.name);
            }
        }

        return secondaryRolesSpecifiedStrings;
    }

    public ArrayList<Bonusrolle> getSecondaryRolesUnspecified() {
        ArrayList<Bonusrolle> secondaryRolesUnspecified = new ArrayList<Bonusrolle>();
        secondaryRolesUnspecified = (ArrayList) secondaryRolesInGame.clone();

        MyCollectionHelper.removeAllNebenrollen(secondaryRolesUnspecified, getSecondaryRolesSpecified());

        return secondaryRolesUnspecified;
    }

    public ArrayList<String> getSecondaryRolesUnspecifiedStrings() {
        ArrayList<String> secondaryRolesUnspecifiedStrings = new ArrayList<>();

        for (Bonusrolle bonusrolle : getSecondaryRolesUnspecified()) {
            secondaryRolesUnspecifiedStrings.add(bonusrolle.name);
        }

        return secondaryRolesUnspecifiedStrings;
    }
}
