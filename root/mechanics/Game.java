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
import root.Persona.Rollen.Constants.WölfinState;
import root.Persona.Rollen.Hauptrollen.Bürger.*;
import root.Persona.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Persona.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Persona.Rollen.Hauptrollen.Vampire.LadyAleera;
import root.Persona.Rollen.Hauptrollen.Vampire.MissVerona;
import root.Persona.Rollen.Hauptrollen.Werwölfe.*;
import root.Persona.Rollen.Hauptrollen.Überläufer.Überläufer;
import root.Persona.Rollen.Nebenrollen.*;
import root.Phases.ErsteNacht;
import root.Phases.Nacht;
import root.Phases.NightBuilding.NormalNightStatementBuilder;
import root.Phases.PhaseMode;
import root.Phases.Tag;
import root.Spieler;

import java.util.ArrayList;

public class Game {
    public PhaseMode phaseMode;
    public Tag tag;

    public Liebespaar liebespaar;

    public ArrayList<Spieler> spieler = new ArrayList<>();
    public ArrayList<Hauptrolle> mainRoles = new ArrayList<>();
    public ArrayList<Hauptrolle> mainRolesInGame = new ArrayList<>();
    public ArrayList<Nebenrolle> secondaryRoles = new ArrayList<>();
    public ArrayList<Nebenrolle> secondaryRolesInGame = new ArrayList<>();
    public ArrayList<Hauptrolle> mitteHauptrollen = new ArrayList<>();
    public ArrayList<Nebenrolle> mitteNebenrollen = new ArrayList<>();
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
        generateAllAvailableSecondaryRoles();
        mitteHauptrollen = new ArrayList<>();
        mitteNebenrollen = new ArrayList<>();
        playersSpecified = new ArrayList<>();

        liebespaar = new Liebespaar(this);
    }

    public void generateAllAvailableMainRoles() {
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
        mainRoles.add(new Überläufer());
    }

    public void generateAllAvailableSecondaryRoles() {
        secondaryRoles.add(new Analytiker());
        secondaryRoles.add(new Archivar());
        secondaryRoles.add(new Gefängniswärter());
        //Imitator
        secondaryRoles.add(new Konditor());
        secondaryRoles.add(new Konditorlehrling());
        secondaryRoles.add(new Lamm());
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

    public ErzählerFrameMode parsePhaseMode() {
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

    public void firstnight(ErzählerFrame erzählerFrame) {
        phaseMode = PhaseMode.ersteNacht;
        erzählerFrame.mode = ErzählerFrameMode.ersteNacht;
        erzählerFrame.übersichtsFrame = new ÜbersichtsFrame(erzählerFrame, this);
        erzählerFrame.toFront();
        FrontendControl.erzählerFrame = erzählerFrame;
        FrontendControl.spielerFrame = erzählerFrame.spielerFrame;
        FrontendControl.übersichtsFrame = erzählerFrame.übersichtsFrame;
        ErsteNacht ersteNacht = new ErsteNacht(this);
        ersteNacht.start();
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

    public void night() {
        FrontendControl.erzählerFrame.mode = ErzählerFrameMode.nacht;
        phaseMode = PhaseMode.nacht;
        Nacht nacht = new Nacht(this);
        nacht.start();
    }

    public String checkVictory() {
        ArrayList<Fraktion> fraktionen = Fraktion.getLivingFraktionen();

        switch (fraktionen.size()) {
            case 0:
                return "Tot";
            case 1:
                return fraktionen.get(0).name;
            case 2:
                if (getLivingPlayerStrings().size() == 2) {
                    Spieler spieler1 = findSpieler(getLivingPlayerStrings().get(0));
                    Spieler spieler2 = findSpieler(getLivingPlayerStrings().get(1));
                    if (liebespaar != null && ((liebespaar.spieler1 == spieler1 && liebespaar.spieler2 == spieler2) ||
                            (liebespaar.spieler1 == spieler2 && liebespaar.spieler2 == spieler1))) {
                        return "Liebespaar";
                    } else {
                        return null;
                    }
                } else {
                    return null;
                }
            default:
                return null;
        }
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

    public Spieler findSpieler(String name) {
        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.name.equals(name)) {
                return currentSpieler;
            }
        }

        return null;
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

    public Spieler findSpielerPerRolle(String name) {
        for (Nebenrolle nebenrolle : mitteNebenrollen) {
            if (nebenrolle.name.equals(name)) {
                return findSpielerPerRolle(Sammler.NAME);
            }
        }

        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.hauptrolle.name.equals(name) || currentSpieler.nebenrolle.name.equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }

    public FrontendControl getPlayerFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN;
        frontendControl.dropdownStrings = getLivingPlayerOrNoneStrings();

        return frontendControl;
    }

    public ArrayList<String> getLivingPlayerOrNoneStrings() {
        ArrayList<String> allSpieler = getLivingPlayerStrings();
        allSpieler.add("");

        return allSpieler;
    }

    public void killSpieler(Spieler spieler) {
        if (spieler != null && spieler.lebend) {
            spieler.lebend = false;
            Hauptrolle hauptrolle = spieler.hauptrolle;
            Nebenrolle nebenrolle = spieler.nebenrolle;
            mitteHauptrollen.add(hauptrolle);
            mitteNebenrollen.add(nebenrolle);

            if (hauptrolle.name.equals(Schattenpriester.NAME) && !nebenrolle.name.equals(Schatten.NAME)) {
                Schattenpriester_Fraktion.deadSchattenPriester++;
            }

            if (Rolle.rolleLebend(Wölfin.NAME) && Wölfin.state == WölfinState.WARTEND) {
                if (hauptrolle.getFraktion().name.equals(Werwölfe.NAME)) {
                    Wölfin.state = WölfinState.TÖTEND;
                }
            }

            if (nebenrolle.equals(Schnüffler.NAME)) {
                ((Schnüffler) nebenrolle).informationen = new ArrayList<>();
            }

            if (nebenrolle.equals(Tarnumhang.NAME)) {
                ((Tarnumhang) nebenrolle).seenPlayers = new ArrayList<>();
            }
        }
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

    public ArrayList<String> getPlayerCheckSpammableStrings(Rolle rolle) {
        ArrayList<String> allSpieler = getLivingPlayerOrNoneStrings();
        if (!rolle.spammable && rolle.besuchtLetzteNacht != null) {
            allSpieler.remove(rolle.besuchtLetzteNacht.name);
        }

        return allSpieler;
    }

    public Spieler findSpielerOrDeadPerRolle(String name) {
        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.hauptrolle.name.equals(name) || currentSpieler.nebenrolle.name.equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }

    public FrontendControl getMitspielerCheckSpammableFrontendControl(Rolle rolle) {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN;
        frontendControl.dropdownStrings = getMitspielerCheckSpammableStrings(rolle);

        return frontendControl;
    }

    public ArrayList<Spieler> findSpielersPerRolle(String name) {
        ArrayList<Spieler> spielers = new ArrayList<>();

        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.hauptrolle.name.equals(name) || currentSpieler.nebenrolle.name.equals(name)) {
                spielers.add(currentSpieler);
            }
        }

        return spielers;
    }

    public ArrayList<String> findSpielersStringsPerRolle(String name) {
        ArrayList<String> spielers = new ArrayList<>();

        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.hauptrolle.name.equals(name) || currentSpieler.nebenrolle.name.equals(name)) {
                spielers.add(currentSpieler.name);
            }
        }

        return spielers;
    }

    public boolean spielerExists(String name) {
        return findSpieler(name) != null;
    }

    public ArrayList<String> getMainRolesAlive() {
        ArrayList<String> names = new ArrayList<String>();

        for (Hauptrolle currentHauptrolle : mainRoles) {
            for (Spieler currentSpieler : spieler) {
                if (currentSpieler.hauptrolle.name.equals(currentHauptrolle.name) && Rolle.rolleLebend(currentSpieler.hauptrolle.name)) {
                    names.add(currentSpieler.hauptrolle.name);
                }
            }
        }

        return names;
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

    public ArrayList<Hauptrolle> getStillAvailableMainRoles() {
        ArrayList<Hauptrolle> mainroles = (ArrayList) mainRolesInGame.clone();
        ArrayList<Hauptrolle> stilleAvalableMainRoles = new ArrayList<>();

        for (Spieler spieler : spieler) {
            mainroles.remove(spieler.hauptrolle);
        }

        stilleAvalableMainRoles.addAll(mainroles);

        return stilleAvalableMainRoles;
    }

    public ArrayList<Hauptrolle> getStillAvailableBürger() {
        ArrayList<Hauptrolle> mainroles = getStillAvailableMainRoles();
        ArrayList<Hauptrolle> mainrolesToRemove = new ArrayList<>();

        for (Hauptrolle hauptrolle : mainroles) {
            if (!hauptrolle.getFraktion().equals(Bürger.NAME)) {
                mainrolesToRemove.add(hauptrolle);
            }
        }

        mainroles.removeAll(mainrolesToRemove);

        return mainroles;
    }

    public ArrayList<String> getStillAvailableMainRoleNames() {
        ArrayList<Hauptrolle> stilleAvalableMainRoles = getStillAvailableMainRoles();
        ArrayList<String> names = new ArrayList<>();

        for (Hauptrolle hauptrolle : stilleAvalableMainRoles) {
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

        for (Nebenrolle nebenrolle : secondaryRoles) {
            names.add(nebenrolle.name);
        }

        return names;
    }

    public ArrayList<String> getSecondaryRoleInGameNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (Nebenrolle nebenrolle : secondaryRolesInGame) {
            names.add(nebenrolle.name);
        }

        return names;
    }

    public ArrayList<String> getPossibleInGameSecondaryRoleNames() {
        ArrayList<String> secondaryRoleInGameNames = getSecondaryRoleInGameNames();

        for (Nebenrolle nebenrolle : mitteNebenrollen) {
            secondaryRoleInGameNames.remove(nebenrolle.name);
        }

        return secondaryRoleInGameNames;
    }

    public ArrayList<String> getStillAvailableSecondaryRoleNames() {
        ArrayList<Nebenrolle> secondaryRoles = (ArrayList) secondaryRolesInGame.clone();
        ArrayList<String> names = new ArrayList<String>();

        for (Spieler spieler : spieler) {
            secondaryRoles.remove(spieler.nebenrolle);
        }

        for (Nebenrolle nebenrolle : secondaryRoles) {
            names.add(nebenrolle.name);
        }

        return names;
    }

    public Nebenrolle findNebenrolle(String wantedName) {
        for (Nebenrolle nebenrolle : secondaryRoles) {
            if (nebenrolle.name.equals(wantedName))
                return nebenrolle;
        }

        return null;
    }

    public int numberOfOccurencesOfSecondaryRoleInGame(Nebenrolle nebenrolle) {
        int occurences = 0;
        for (Nebenrolle currentNebenrolle : secondaryRolesInGame) {
            if (currentNebenrolle.name.equals(nebenrolle.name)) {
                occurences++;
            }
        }

        return occurences;
    }

    public void addAllSecondaryRoles() {
        secondaryRolesInGame.addAll(secondaryRoles);
        secondaryRolesInGame.remove(findNebenrolle(Schatten.NAME));
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

    public ArrayList<Nebenrolle> getSecondaryRolesSpecified() {
        ArrayList<Nebenrolle> secondaryRolesSpecified = new ArrayList<>();

        for (Spieler spieler : playersSpecified) {
            secondaryRolesSpecified.add(spieler.nebenrolle);
        }

        return secondaryRolesSpecified;
    }

    public ArrayList<String> getSecondaryRoleSpecifiedStrings() {
        ArrayList<String> secondaryRolesSpecifiedStrings = new ArrayList<>();

        for (Nebenrolle nebenrolle : getSecondaryRolesSpecified()) {
            if (nebenrolle != null) {
                secondaryRolesSpecifiedStrings.add(nebenrolle.name);
            }
        }

        return secondaryRolesSpecifiedStrings;
    }

    public ArrayList<Nebenrolle> getSecondaryRolesUnspecified() {
        ArrayList<Nebenrolle> secondaryRolesUnspecified = new ArrayList<Nebenrolle>();
        secondaryRolesUnspecified = (ArrayList) secondaryRolesInGame.clone();

        MyCollectionHelper.removeAllNebenrollen(secondaryRolesUnspecified, getSecondaryRolesSpecified());

        return secondaryRolesUnspecified;
    }

    public ArrayList<String> getSecondaryRolesUnspecifiedStrings() {
        ArrayList<String> secondaryRolesUnspecifiedStrings = new ArrayList<>();

        for (Nebenrolle nebenrolle : getSecondaryRolesUnspecified()) {
            secondaryRolesUnspecifiedStrings.add(nebenrolle.name);
        }

        return secondaryRolesUnspecifiedStrings;
    }
}
