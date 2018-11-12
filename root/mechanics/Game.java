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
        return spieler.stream()
                .filter(spieler -> spieler.equals(name))
                .findAny().orElse(null);
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
        return spieler.stream()
                .filter(spieler -> spieler.hauptrolle.equals(name) || spieler.bonusrolle.equals(name))
                .findAny().orElse(null);
    }

    public List<Spieler> getLivingSpieler() {
        return spieler.stream()
                .filter(spieler -> spieler.lebend)
                .collect(Collectors.toList());
    }

    public List<String> getLivingSpielerStrings() {
        return spieler.stream()
                .filter(spieler -> spieler.lebend)
                .map(spieler -> spieler.name)
                .collect(Collectors.toList());
    }

    public List<String> getLivingSpielerOrNoneStrings() {
        List<String> allSpieler = getLivingSpielerStrings();
        allSpieler.add("");

        return allSpieler;
    }

    public List<String> getSpielerCheckSpammableStrings(Rolle rolle) {
        List<String> allSpieler = getLivingSpielerOrNoneStrings();
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

    public List<String> getMitspielerCheckSpammableStrings(Rolle rolle) {
        Spieler spieler = findSpielerPerRolle(rolle.name);

        List<String> mitspieler = getSpielerCheckSpammableStrings(rolle);
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

    public List<String> getHauptrolleNames() {
        return hauptrollen.stream()
                .map(rolle -> rolle.name)
                .collect(Collectors.toList());
    }

    public List<String> getHauptrolleInGameNames() {
        return hauptrollenInGame.stream()
                .map(rolle -> rolle.name)
                .collect(Collectors.toList());
    }

    public List<Hauptrolle> getPossibleInGameHauptrollen() {
        List<String> hauptrollenInGameNames = getHauptrolleInGameNames();

        ArrayList<Hauptrolle> hauptrolleInGame = new ArrayList<>();
        hauptrollenInGameNames.forEach(rolle -> hauptrolleInGame.add(this.findHauptrolle(rolle)));
        return hauptrolleInGame;
    }

    public List<String> getPossibleInGameHauptrolleNames() {
        List<String> hauptrollenInGame = getHauptrolleInGameNames();

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

    public List<Hauptrolle> getStillAvailableHauptrollen() {
        List<Hauptrolle> stilleAvalableHauptrollen = new ArrayList<>();

        stilleAvalableHauptrollen.addAll(hauptrollenInGame);

        for (Spieler spieler : spieler) {
            stilleAvalableHauptrollen.remove(spieler.hauptrolle);
        }

        return stilleAvalableHauptrollen;
    }

    public List<Hauptrolle> getStillAvailableBürger() {
        return getStillAvailableHauptrollen().stream()
                .filter(hauptrolle -> hauptrolle.fraktion.equals(Bürger.NAME))
                .collect(Collectors.toList());
    }

    public List<String> getStillAvailableHauptrolleNames() {
        return getStillAvailableHauptrollen().stream()
                .map(rolle -> rolle.name)
                .collect(Collectors.toList());
    }

    public Hauptrolle findHauptrolle(String wantedName) {
        return hauptrollen.stream()
                .filter(rolle -> rolle.equals(wantedName))
                .findAny().orElse(null);
    }

    public int numberOfOccurencesOfHauptrolleInGame(Hauptrolle hauptrolle) {
        return (int) hauptrollenInGame.stream()
                .filter(rolle -> rolle.equals(hauptrolle))
                .count();
    }

    public void addAllHauptrollenToGame() {
        hauptrollenInGame.addAll(hauptrollen);
        hauptrollenInGame.remove(findHauptrolle(Dorfbewohner.NAME));
        hauptrollenInGame.remove(findHauptrolle(Werwolf.NAME));
    }

    public List<String> getBonusrolleNames() {
        return bonusrollen.stream()
                .map(rolle -> rolle.name)
                .collect(Collectors.toList());
    }

    public List<String> getBonusrolleInGameNames() {
        return bonusrollenInGame.stream()
                .map(rolle -> rolle.name)
                .collect(Collectors.toList());
    }

    public List<Bonusrolle> getPossibleInGameBonusrollen() {
        List<String> bonusrollenInGameNames = getBonusrolleInGameNames();

        List<Bonusrolle> bonusrollenInGame = new ArrayList<>();
        bonusrollenInGameNames.forEach(rolle -> bonusrollenInGame.add(this.findBonusrolle(rolle)));
        return bonusrollenInGame;
    }

    public List<String> getPossibleInGameBonusrolleNames() {
        List<String> bonusrolleInGameNames = getBonusrolleInGameNames();

        for (Bonusrolle bonusrolle : mitteBonusrollen) {
            bonusrolleInGameNames.remove(bonusrolle.name);
        }

        return bonusrolleInGameNames;
    }

    public List<Bonusrolle> getStillAvailableBonusrollen() {
        List<Bonusrolle> stilleAvalableBonusrollen = new ArrayList<>();

        stilleAvalableBonusrollen.addAll(bonusrollenInGame);

        for (Spieler spieler : spieler) {
            stilleAvalableBonusrollen.remove(spieler.bonusrolle);
        }

        stilleAvalableBonusrollen.removeAll(FirstNight.swappedRoles);

        return stilleAvalableBonusrollen;
    }

    public List<String> getStillAvailableBonusrollenNames() {
        return getStillAvailableBonusrollen().stream()
                .map(rolle -> rolle.name)
                .collect(Collectors.toList());
    }

    public Bonusrolle findBonusrolle(String wantedName) {
        return bonusrollen.stream()
                .filter(rolle -> rolle.equals(wantedName))
                .findAny().orElse(null);
    }

    public int numberOfOccurencesOfBonusrolleInGame(Bonusrolle bonusrolle) {
        return (int) bonusrollenInGame.stream()
                .filter(rolle -> rolle.equals(bonusrolle))
                .count();
    }

    public void addAllBonusrollen() {
        bonusrollenInGame.addAll(bonusrollen);
        bonusrollenInGame.remove(findBonusrolle(Schatten.NAME));
    }

    public List<Spieler> getSpielerUnspecified() {
        List<Spieler> spielerUnspecified = (ArrayList) spieler.clone();
        spielerUnspecified.removeAll(spielerSpecified);
        return spielerUnspecified;
    }

    public List<String> getSpielerUnspecifiedStrings() {
        return getSpielerUnspecified().stream()
                .map(spieler -> spieler.name)
                .collect(Collectors.toList());
    }

    public List<Hauptrolle> getHauptrollenSpecified() {
        return spielerSpecified.stream()
                .map(spieler -> spieler.hauptrolle)
                .collect(Collectors.toList());
    }

    public List<String> getHauptrollenSpecifiedStrings() {
        return spielerSpecified.stream()
                .map(spieler -> spieler.hauptrolle.name)
                .collect(Collectors.toList());
    }

    public List<Hauptrolle> getHauptrollenUnspecified() {
        List<Hauptrolle> hauptrollenUnspecified = (ArrayList) hauptrollenInGame.clone();

        hauptrollenUnspecified.removeAll(getHauptrollenSpecified());

        return hauptrollenUnspecified;
    }

    public List<String> getHauptrollenUnspecifiedStrings() {
        return getHauptrollenUnspecified().stream()
                .map(rolle -> rolle.name)
                .collect(Collectors.toList());
    }

    public List<Bonusrolle> getBonusrollenSpecified() {
        return spielerSpecified.stream()
                .map(spieler -> spieler.bonusrolle)
                .collect(Collectors.toList());
    }

    public List<String> getBonusrolleSpecifiedStrings() {
        return spielerSpecified.stream()
                .map(spieler -> spieler.bonusrolle.name)
                .collect(Collectors.toList());
    }

    public List<Bonusrolle> getBonusrollenUnspecified() {
        List<Bonusrolle> bonusrollenUnspecified = (ArrayList) bonusrollenInGame.clone();

        bonusrollenUnspecified.removeAll(getBonusrollenSpecified());

        return bonusrollenUnspecified;
    }

    public List<String> getBonusrollenUnspecifiedStrings() {
        return getBonusrollenUnspecified().stream()
                .map(rolle -> rolle.name)
                .collect(Collectors.toList());
    }

    public List<Spieler> getIrrlichter() {
        List<Spieler> livingSpieler = getLivingSpieler();
        return livingSpieler.stream()
                .filter(p -> p.hauptrolle.equals(Irrlicht.NAME))
                .collect(Collectors.toList());
    }

    public List<String> getIrrlichterStrings() {
        return getIrrlichter().stream()
                .map(spieler -> spieler.name)
                .collect(Collectors.toList());
    }
}
