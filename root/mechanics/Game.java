package root.mechanics;

import root.Frontend.Constants.FrontendControlType;
import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.ÜbersichtsFrame;
import root.Frontend.FrontendControl;
import root.Persona.Bonusrolle;
import root.Persona.Fraktion;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Fraktionen.SchattenpriesterFraktion;
import root.Persona.Fraktionen.Werwölfe;
import root.Persona.Hauptrolle;
import root.Persona.Persona;
import root.Persona.Rolle;
import root.Persona.Rollen.Bonusrollen.Analytiker;
import root.Persona.Rollen.Bonusrollen.Archivar;
import root.Persona.Rollen.Bonusrollen.Dieb;
import root.Persona.Rollen.Bonusrollen.Gefängniswärter;
import root.Persona.Rollen.Bonusrollen.Konditor;
import root.Persona.Rollen.Bonusrollen.Konditorlehrling;
import root.Persona.Rollen.Bonusrollen.Lamm;
import root.Persona.Rollen.Bonusrollen.Medium;
import root.Persona.Rollen.Bonusrollen.Nachbar;
import root.Persona.Rollen.Bonusrollen.Prostituierte;
import root.Persona.Rollen.Bonusrollen.ReineSeele;
import root.Persona.Rollen.Bonusrollen.Schatten;
import root.Persona.Rollen.Bonusrollen.Schattenkutte;
import root.Persona.Rollen.Bonusrollen.Schnüffler;
import root.Persona.Rollen.Bonusrollen.SchwarzeSeele;
import root.Persona.Rollen.Bonusrollen.Seelenlicht;
import root.Persona.Rollen.Bonusrollen.Spurenleser;
import root.Persona.Rollen.Bonusrollen.Tarnumhang;
import root.Persona.Rollen.Bonusrollen.Totengräber;
import root.Persona.Rollen.Bonusrollen.Vampirumhang;
import root.Persona.Rollen.Bonusrollen.Wahrsager;
import root.Persona.Rollen.Bonusrollen.Wolfspelz;
import root.Persona.Rollen.Constants.WölfinState;
import root.Persona.Rollen.Hauptrollen.Bürger.Dorfbewohner;
import root.Persona.Rollen.Hauptrollen.Bürger.HoldeMaid;
import root.Persona.Rollen.Hauptrollen.Bürger.Irrlicht;
import root.Persona.Rollen.Hauptrollen.Bürger.Orakel;
import root.Persona.Rollen.Hauptrollen.Bürger.Riese;
import root.Persona.Rollen.Hauptrollen.Bürger.Sammler;
import root.Persona.Rollen.Hauptrollen.Bürger.Schamanin;
import root.Persona.Rollen.Hauptrollen.Bürger.Seherin;
import root.Persona.Rollen.Hauptrollen.Bürger.Späher;
import root.Persona.Rollen.Hauptrollen.Bürger.Wirt;
import root.Persona.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Persona.Rollen.Hauptrollen.Vampire.GrafVladimir;
import root.Persona.Rollen.Hauptrollen.Vampire.LadyAleera;
import root.Persona.Rollen.Hauptrollen.Vampire.MissVerona;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Blutwolf;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Chemiker;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Geisterwolf;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Schreckenswolf;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Werwolf;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Wolfsmensch;
import root.Persona.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Persona.Rollen.Hauptrollen.Überläufer.Henker;
import root.Persona.Rollen.Hauptrollen.Überläufer.Überläufer;
import root.Phases.Day;
import root.Phases.FirstNight;
import root.Phases.NightBuilding.NormalNightStatementBuilder;
import root.Phases.PhaseManager;
import root.Phases.PhaseMode;
import root.Phases.Winner;
import root.Spieler;
import root.mechanics.KillLogik.Angriff;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Game {
    public Day day;

    public boolean freibier = false;

    public Liebespaar liebespaar;

    public ArrayList<Spieler> spieler = new ArrayList<>();
    public ArrayList<Hauptrolle> hauptrollen = new ArrayList<>();
    public ArrayList<Hauptrolle> hauptrollenInGame = new ArrayList<>();
    public ArrayList<Bonusrolle> bonusrollen = new ArrayList<>();
    public ArrayList<Bonusrolle> bonusrollenInGame = new ArrayList<>();
    public ArrayList<Hauptrolle> mitteHauptrollen = new ArrayList<>();
    public ArrayList<Bonusrolle> mitteBonusrollen = new ArrayList<>();
    public ArrayList<Spieler> spielerSpecified = new ArrayList<>();

    public boolean secondNight = true;
    private boolean started = false;

    public Game() {
        Persona.game = this;
        Spieler.game = this;
        FrontendControl.game = this;
        Angriff.game = this;
        NormalNightStatementBuilder.game = this;
        PhaseManager.game = this;

        PhaseManager.phaseMode = PhaseMode.SETUP;

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

    public void startGame(ErzählerFrame erzählerFrame) {
        erzählerFrame.übersichtsFrame = new ÜbersichtsFrame(erzählerFrame.frameJpanel.getHeight() + 50, this);
        erzählerFrame.toFront();

        FrontendControl.erzählerFrame = erzählerFrame;
        FrontendControl.spielerFrame = erzählerFrame.spielerFrame;
        FrontendControl.übersichtsFrame = erzählerFrame.übersichtsFrame;

        PhaseManager phaseManager = new PhaseManager(this);
        phaseManager.start();
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
        bonusrollen.add(new ReineSeele());
        bonusrollen.add(new Schatten());
        bonusrollen.add(new Schattenkutte());
        bonusrollen.add(new Schnüffler());
        bonusrollen.add(new SchwarzeSeele());
        bonusrollen.add(new Seelenlicht());
        bonusrollen.add(new Spurenleser());
        bonusrollen.add(new Tarnumhang());
        bonusrollen.add(new Totengräber());
        bonusrollen.add(new Vampirumhang());
        bonusrollen.add(new Wahrsager());
        bonusrollen.add(new Wolfspelz());
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
            mitteBonusrollen.add(bonusrolle); //TODO methode auslagern?

            if (hauptrolle.equals(Schattenpriester.NAME) && !bonusrolle.equals(Schatten.NAME)) {
                SchattenpriesterFraktion.deadSchattenPriester++;
            }

            if (Rolle.rolleLebend(Wölfin.NAME) && Wölfin.state == WölfinState.WARTEND) {
                if (hauptrolle.fraktion.equals(Werwölfe.NAME)) {
                    Wölfin.state = WölfinState.TÖTEND;
                }
            }

            //TODO rolle.cleanUp()

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
            if (currentSpieler.equals(name)) {
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
            if (bonusrolle.equals(name)) {
                return findSpielerPerRolle(Sammler.NAME);
            }
        }

        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.hauptrolle.equals(name) || currentSpieler.bonusrolle.equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }

    public Spieler findSpielerOrDeadPerRolle(String name) {
        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.hauptrolle.equals(name) || currentSpieler.bonusrolle.equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }

    public ArrayList<Spieler> findSpielersPerRolle(String name) {
        ArrayList<Spieler> spielers = new ArrayList<>();

        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.hauptrolle.equals(name) || currentSpieler.bonusrolle.equals(name)) {
                spielers.add(currentSpieler);
            }
        }

        return spielers;
    }

    public ArrayList<String> findSpielersStringsPerRolle(String name) {
        ArrayList<String> spielers = new ArrayList<>();

        for (Spieler currentSpieler : spieler) {
            if (currentSpieler.hauptrolle.equals(name) || currentSpieler.bonusrolle.equals(name)) {
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

    public List<Hauptrolle> getPossibleInGameHauptrollen() {
        ArrayList<String> hauptrollenInGameNames = getHauptrolleInGameNames();

        ArrayList<Hauptrolle> hauptrolleInGame = new ArrayList<>();
        hauptrollenInGameNames.forEach(rolle -> hauptrolleInGame.add(this.findHauptrolle(rolle)));
        return hauptrolleInGame;
    }

    public ArrayList<String> getPossibleInGameHauptrolleNames() {
        ArrayList<String> hauptrollenInGame = getHauptrolleInGameNames();

        for (Hauptrolle hauptrolle : mitteHauptrollen) {
            if (!hauptrolle.equals(Schattenpriester.NAME)) {
                hauptrollenInGame.remove(hauptrolle.name);
            }
        }

        for (int i = 0; i < SchattenpriesterFraktion.deadSchattenPriester; i++) {
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
            if (hauptrolle.equals(wantedName))
                return hauptrolle;
        }

        return null;
    }

    public int numberOfOccurencesOfHauptrolleInGame(Hauptrolle hauptrolle) {
        int occurences = 0;
        for (Hauptrolle currentHauptrolle : hauptrollenInGame) {
            if (currentHauptrolle.equals(hauptrolle)) {
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

    public List<Bonusrolle> getPossibleInGameBonusrollen() {
        ArrayList<String> bonusrollenInGameNames = getBonusrolleInGameNames();

        ArrayList<Bonusrolle> bonusrollenInGame = new ArrayList<>();
        bonusrollenInGameNames.forEach(rolle -> bonusrollenInGame.add(this.findBonusrolle(rolle)));
        return bonusrollenInGame;
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

        stilleAvalableBonusrollen.removeAll(FirstNight.swappedRoles);

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
            if (bonusrolle.equals(wantedName))
                return bonusrolle;
        }

        return null;
    }

    public int numberOfOccurencesOfBonusrolleInGame(Bonusrolle bonusrolle) {
        int occurences = 0;
        for (Bonusrolle currentBonusrolle : bonusrollenInGame) {
            if (currentBonusrolle.equals(bonusrolle)) {
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
        ArrayList<Spieler> spielerUnspecified = (ArrayList) spieler.clone();
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
