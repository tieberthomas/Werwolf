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
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    public PhaseMode phaseMode;
    public Day day;

    public boolean freibier = false;

    public Liebespaar liebespaar;

    public ArrayList<Spieler> spieler = new ArrayList<>(); //TODO überflüssige initialisierung an manchen stellen, generisch machen
    public ArrayList<Hauptrolle> hauptrollen = new ArrayList<>();
    public ArrayList<Hauptrolle> hauptrollenInGame = new ArrayList<>();
    public ArrayList<Bonusrolle> bonusrollen = new ArrayList<>();
    public ArrayList<Bonusrolle> bonusrollenInGame = new ArrayList<>();
    public ArrayList<Hauptrolle> mitteHauptrollen = new ArrayList<>();
    public ArrayList<Bonusrolle> mitteBonusrollen = new ArrayList<>();
    public ArrayList<Spieler> spielerSpecified = new ArrayList<>();

    public boolean secondNight = true;

    public Game() {
        Persona.game = this;
        Spieler.game = this;
        FrontendControl.game = this;
        Opfer.game = this;
        NormalNightStatementBuilder.game = this;

        phaseMode = PhaseMode.SETUP;

        spieler = new ArrayList<>();
        hauptrollenInGame = new ArrayList<>();
        generateAllAvailableHauptrollen();
        bonusrollenInGame = new ArrayList<>();
        generateAllAvailableBonusrollen();
        mitteHauptrollen = new ArrayList<>();
        mitteBonusrollen = new ArrayList<>();
        spielerSpecified = new ArrayList<>();

        liebespaar = new Liebespaar(this);
        Torte.tortenEsser = new ArrayList<>();
    }

    private void generateAllAvailableHauptrollen() {
        hauptrollen.add(new Dorfbewohner());
        hauptrollen.add(new HoldeMaid());
        hauptrollen.add(new Irrlicht());
        hauptrollen.add(new Orakel());
        hauptrollen.add(new Riese());
        hauptrollen.add(new Sammler());
        hauptrollen.add(new Schamanin());
        hauptrollen.add(new Seherin());
        hauptrollen.add(new Späher());
        hauptrollen.add(new Wirt());
        hauptrollen.add(new Schattenpriester());
        hauptrollen.add(new GrafVladimir());
        hauptrollen.add(new LadyAleera());
        hauptrollen.add(new MissVerona());
        hauptrollen.add(new Alphawolf());
        hauptrollen.add(new Blutwolf());
        hauptrollen.add(new Chemiker());
        hauptrollen.add(new Geisterwolf());
        hauptrollen.add(new Schreckenswolf());
        hauptrollen.add(new Werwolf());
        hauptrollen.add(new Wolfsmensch());
        hauptrollen.add(new Wölfin());
        hauptrollen.add(new Henker());
        hauptrollen.add(new Überläufer());
    }

    private void generateAllAvailableBonusrollen() {
        bonusrollen.add(new Analytiker());
        bonusrollen.add(new Archivar());
        bonusrollen.add(new Dieb());
        bonusrollen.add(new Gefängniswärter());
        //Imitator
        bonusrollen.add(new Konditor());
        bonusrollen.add(new Konditorlehrling());
        bonusrollen.add(new Lamm());
        bonusrollen.add(new Medium());
        bonusrollen.add(new Nachbar());
        bonusrollen.add(new Prostituierte());
        bonusrollen.add(new Schatten());
        bonusrollen.add(new Schattenkutte());
        bonusrollen.add(new Schnüffler());
        bonusrollen.add(new Seelenlicht());
        bonusrollen.add(new Spurenleser());
        bonusrollen.add(new Tarnumhang());
        bonusrollen.add(new Totengräber());
        bonusrollen.add(new Vampirumhang());
        bonusrollen.add(new Wahrsager());
        bonusrollen.add(new Wolfspelz());
    }

    public ErzählerFrameMode parsePhaseMode() { //TODO automapper?
        if (phaseMode == PhaseMode.DAY) {
            return ErzählerFrameMode.DAY;
        } else if (phaseMode == PhaseMode.FREIBIER_DAY) {
            return ErzählerFrameMode.FREIBIER_DAY;
        } else if (phaseMode == PhaseMode.FIRST_NIGHT) {
            return ErzählerFrameMode.FIRST_NIGHT;
        } else if (phaseMode == PhaseMode.NORMAL_NIGHT) {
            return ErzählerFrameMode.NORMAL_NIGHT;
        } else {
            return ErzählerFrameMode.SETUP;
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
        erzählerFrame.mode = ErzählerFrameMode.FIRST_NIGHT;
        phaseMode = PhaseMode.FIRST_NIGHT;
        FirstNight firstNight = new FirstNight(this);
        firstNight.start();
    }

    public void night() {
        FrontendControl.erzählerFrame.mode = ErzählerFrameMode.NORMAL_NIGHT;
        phaseMode = PhaseMode.NORMAL_NIGHT;
        NormalNight normalNight = new NormalNight(this);
        normalNight.start();
    }

    public void day() {
        FrontendControl.erzählerFrame.mode = ErzählerFrameMode.DAY;
        phaseMode = PhaseMode.DAY;
        day = new Day(this);
        day.start();
    }

    public void freibierDay() {
        FrontendControl.erzählerFrame.mode = ErzählerFrameMode.FREIBIER_DAY;
        phaseMode = PhaseMode.FREIBIER_DAY;
        day = new Day(this);
        day.start();
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
                if (getLivingSpielerStrings().size() == 2) {
                    Spieler spieler1 = findSpieler(getLivingSpielerStrings().get(0));
                    Spieler spieler2 = findSpieler(getLivingSpielerStrings().get(1));
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
                ((Tarnumhang) bonusrolle).seenSpieler = new ArrayList<>();
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

    public ArrayList<Spieler> getLivingSpieler() {
        ArrayList<Spieler> allSpieler = new ArrayList<>();

        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.lebend) {
                allSpieler.add(currentSpieler);
            }
        }

        return allSpieler;
    }

    public ArrayList<String> getLivingSpielerStrings() {
        ArrayList<String> allSpieler = new ArrayList<>();

        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.lebend) {
                allSpieler.add(currentSpieler.name);
            }
        }

        return allSpieler;
    }

    public ArrayList<String> getLivingSpielerOrNoneStrings() {
        ArrayList<String> allSpieler = getLivingSpielerStrings();
        allSpieler.add("");

        return allSpieler;
    }

    public FrontendControl getSpielerFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN;
        frontendControl.dropdownStrings = getLivingSpielerOrNoneStrings();

        return frontendControl;
    }

    public ArrayList<String> getSpielerCheckSpammableStrings(Rolle rolle) {
        ArrayList<String> allSpieler = getLivingSpielerOrNoneStrings();
        if (!rolle.spammable && rolle.besuchtLastNight != null) {
            allSpieler.remove(rolle.besuchtLastNight.name);
        }

        return allSpieler;
    }

    public FrontendControl getSpielerCheckSpammableFrontendControl(Rolle rolle) {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControlType.DROPDOWN;
        frontendControl.dropdownStrings = getSpielerCheckSpammableStrings(rolle);

        return frontendControl;
    }

    public ArrayList<String> getMitspielerCheckSpammableStrings(Rolle rolle) {
        Spieler spieler = findSpielerPerRolle(rolle.name);

        ArrayList<String> mitspieler = getSpielerCheckSpammableStrings(rolle);
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

    public ArrayList<String> getHauptrolleNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (Hauptrolle hauptrolle : hauptrollen) {
            names.add(hauptrolle.name);
        }

        return names;
    }

    public ArrayList<String> getHauptrolleInGameNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (Hauptrolle hauptrolle : hauptrollenInGame) {
            names.add(hauptrolle.name);
        }

        return names;
    }

    public ArrayList<String> getPossibleInGameHauptrolleNames() {
        ArrayList<String> hauptrollenInGame = getHauptrolleInGameNames();

        for (Hauptrolle hauptrolle : mitteHauptrollen) {
            if (!hauptrolle.name.equals(Schattenpriester.NAME)) {
                hauptrollenInGame.remove(hauptrolle.name);
            }
        }

        for (int i = 0; i < Schattenpriester_Fraktion.deadSchattenPriester; i++) {
            hauptrollenInGame.remove(Schattenpriester.NAME);
        }

        return hauptrollenInGame;
    }

    public ArrayList<Hauptrolle> getStillAvailableHauptrollen() {
        ArrayList<Hauptrolle> stilleAvalableHauptrollen = new ArrayList<>();

        stilleAvalableHauptrollen.addAll(hauptrollenInGame);

        for (Spieler spieler : spieler) {
            stilleAvalableHauptrollen.remove(spieler.hauptrolle);
        }

        return stilleAvalableHauptrollen;
    }

    public ArrayList<Hauptrolle> getStillAvailableBürger() {
        ArrayList<Hauptrolle> hauptrollen = getStillAvailableHauptrollen();
        ArrayList<Hauptrolle> bürger = new ArrayList<>();

        for (Hauptrolle hauptrolle : hauptrollen) {
            if (hauptrolle.fraktion.equals(Bürger.NAME)) {
                bürger.add(hauptrolle);
            }
        }

        return bürger;
    }

    public ArrayList<String> getStillAvailableHauptrolleNames() {
        ArrayList<Hauptrolle> stilleAvalableHauptrollen = getStillAvailableHauptrollen();
        ArrayList<String> names = new ArrayList<>();

        for (Hauptrolle hauptrolle : stilleAvalableHauptrollen) {
            names.add(hauptrolle.name);
        }

        return names;
    }

    public Hauptrolle findHauptrolle(String wantedName) {
        for (Hauptrolle hauptrolle : hauptrollen) {
            if (hauptrolle.name.equals(wantedName))
                return hauptrolle;
        }

        return null;
    }

    public int numberOfOccurencesOfHauptrolleInGame(Hauptrolle hauptrolle) {
        int occurences = 0;
        for (Hauptrolle currentHauptrolle : hauptrollenInGame) {
            if (currentHauptrolle.name.equals(hauptrolle.name)) {
                occurences++;
            }
        }

        return occurences;
    }

    public void addAllHauptrollenToGame() {
        hauptrollenInGame.addAll(hauptrollen);
        hauptrollenInGame.remove(findHauptrolle(Dorfbewohner.NAME));
        hauptrollenInGame.remove(findHauptrolle(Werwolf.NAME));
    }

    public ArrayList<String> getBonusrolleNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (Bonusrolle bonusrolle : bonusrollen) {
            names.add(bonusrolle.name);
        }

        return names;
    }

    public ArrayList<String> getBonusrolleInGameNames() {
        ArrayList<String> names = new ArrayList<String>();

        for (Bonusrolle bonusrolle : bonusrollenInGame) {
            names.add(bonusrolle.name);
        }

        return names;
    }

    public ArrayList<String> getPossibleInGameBonusrolleNames() {
        ArrayList<String> bonusrolleInGameNames = getBonusrolleInGameNames();

        for (Bonusrolle bonusrolle : mitteBonusrollen) {
            bonusrolleInGameNames.remove(bonusrolle.name);
        }

        return bonusrolleInGameNames;
    }

    public ArrayList<Bonusrolle> getStillAvailableBonusrollen() {
        ArrayList<Bonusrolle> stilleAvalableBonusrollen = new ArrayList<>();

        stilleAvalableBonusrollen.addAll(bonusrollenInGame);

        for (Spieler spieler : spieler) {
            stilleAvalableBonusrollen.remove(spieler.bonusrolle);
        }


        return stilleAvalableBonusrollen;
    }

    public ArrayList<String> getStillAvailableBonusrollenNames() {
        ArrayList<Bonusrolle> stilleAvalableBonusrollen = getStillAvailableBonusrollen();
        ArrayList<String> names = new ArrayList<>();

        for (Bonusrolle bonusrolle : stilleAvalableBonusrollen) {
            names.add(bonusrolle.name);
        }

        return names;
    }

    public Bonusrolle findBonusrolle(String wantedName) {
        for (Bonusrolle bonusrolle : bonusrollen) {
            if (bonusrolle.name.equals(wantedName))
                return bonusrolle;
        }

        return null;
    }

    public int numberOfOccurencesOfBonusrolleInGame(Bonusrolle bonusrolle) {
        int occurences = 0;
        for (Bonusrolle currentBonusrolle : bonusrollenInGame) {
            if (currentBonusrolle.name.equals(bonusrolle.name)) {
                occurences++;
            }
        }

        return occurences;
    }

    public void addAllBonusrollen() {
        bonusrollenInGame.addAll(bonusrollen);
        bonusrollenInGame.remove(findBonusrolle(Schatten.NAME));
    }

    public ArrayList<Spieler> getSpielerUnspecified() {
        ArrayList<Spieler> spielerUnspecified = (ArrayList)spieler.clone();
        spielerUnspecified.removeAll(spielerSpecified);
        return spielerUnspecified;
    }

    public ArrayList<String> getSpielerUnspecifiedStrings() {
        ArrayList<String> spielerUnspecifiedStrings = new ArrayList<>();

        for (Spieler spieler : getSpielerUnspecified()) {
            spielerUnspecifiedStrings.add(spieler.name);
        }

        return spielerUnspecifiedStrings;
    }

    public ArrayList<Hauptrolle> getHauptrollenSpecified() {
        ArrayList<Hauptrolle> hauptrollenSpecified = new ArrayList<>();

        for (Spieler spieler : spielerSpecified) {
            hauptrollenSpecified.add(spieler.hauptrolle);
        }

        return hauptrollenSpecified;
    }

    public ArrayList<String> getHauptrollenSpecifiedStrings() {
        ArrayList<String> hauptrollenSpecifiedStrings = new ArrayList<>();

        for (Hauptrolle hauptrolle : getHauptrollenSpecified()) {
            if (hauptrolle != null) {
                hauptrollenSpecifiedStrings.add(hauptrolle.name);
            }
        }

        return hauptrollenSpecifiedStrings;
    }

    public ArrayList<Hauptrolle> getHauptrollenUnspecified() {
        ArrayList<Hauptrolle> hauptrollenUnspecified = (ArrayList) hauptrollenInGame.clone();

        MyCollectionHelper.removeAllHauptrollen(hauptrollenUnspecified, getHauptrollenSpecified());

        return hauptrollenUnspecified;
    }

    public ArrayList<String> getHauptrollenUnspecifiedStrings() {
        ArrayList<String> hauptrollenUnspecifiedStrings = new ArrayList<>();

        for (Hauptrolle hauptrolle : getHauptrollenUnspecified()) {
            hauptrollenUnspecifiedStrings.add(hauptrolle.name);
        }

        return hauptrollenUnspecifiedStrings;
    }

    public ArrayList<Bonusrolle> getBonusrollenSpecified() {
        ArrayList<Bonusrolle> bonusrollenSpecified = new ArrayList<>();

        for (Spieler spieler : spielerSpecified) {
            bonusrollenSpecified.add(spieler.bonusrolle);
        }

        return bonusrollenSpecified;
    }

    public ArrayList<String> getBonusrolleSpecifiedStrings() {
        ArrayList<String> bonusrollenSpecifiedStrings = new ArrayList<>();

        for (Bonusrolle bonusrolle : getBonusrollenSpecified()) {
            if (bonusrolle != null) {
                bonusrollenSpecifiedStrings.add(bonusrolle.name);
            }
        }

        return bonusrollenSpecifiedStrings;
    }

    public ArrayList<Bonusrolle> getBonusrollenUnspecified() {
        ArrayList<Bonusrolle> bonusrollenUnspecified = new ArrayList<Bonusrolle>();
        bonusrollenUnspecified = (ArrayList) bonusrollenInGame.clone();

        MyCollectionHelper.removeAllBonusrollen(bonusrollenUnspecified, getBonusrollenSpecified());

        return bonusrollenUnspecified;
    }

    public ArrayList<String> getBonusrollenUnspecifiedStrings() {
        ArrayList<String> bonusrollenUnspecifiedStrings = new ArrayList<>();

        for (Bonusrolle bonusrolle : getBonusrollenUnspecified()) {
            bonusrollenUnspecifiedStrings.add(bonusrolle.name);
        }

        return bonusrollenUnspecifiedStrings;
    }

    public List<Spieler> getIrrlichter() {
        ArrayList<Spieler> livingSpieler = getLivingSpieler();
        return livingSpieler.stream().filter(p -> p.hauptrolle.equals(Irrlicht.NAME)).collect(Collectors.toList());
    }

    public List<String> getIrrlichterStrings() {
        return getIrrlichter().stream().map(Spieler::getName).collect(Collectors.toList());
    }
}
