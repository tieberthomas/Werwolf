package root.mechanics;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.ErzählerFrameMode;
import root.Frontend.Frame.ÜbersichtsFrame;
import root.Frontend.FrontendControl;
import root.Phases.ErsteNacht;
import root.Phases.Nacht;
import root.Phases.PhaseMode;
import root.Phases.Tag;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Schattenpriester_Fraktion;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;
import root.Rollen.Hauptrollen.Bürger.Sammler;
import root.Rollen.Hauptrollen.Schattenpriester.Schattenpriester;
import root.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Rollen.Nebenrolle;
import root.Rollen.Nebenrollen.Schatten;
import root.Rollen.Rolle;
import root.Spieler;

import java.util.ArrayList;

public class Game {
    public PhaseMode phaseMode;
    public Tag tag;

    public Liebespaar liebespaar;

    public ArrayList<Spieler> spieler = new ArrayList<>();
    public ArrayList<Hauptrolle> mitteHauptrollen = new ArrayList<>();
    public ArrayList<Nebenrolle> mitteNebenrollen = new ArrayList<>();

    public Game(){
        phaseMode = PhaseMode.setup;
        Rolle.game = this;
        Fraktion.game = this;
        Spieler.game = this;
        FrontendControl.game = this;
        Opfer.game = this;
    }

    public ErzählerFrameMode parsePhaseMode() {
        if(phaseMode == PhaseMode.tag) {
            return ErzählerFrameMode.tag;
        } else if(phaseMode == PhaseMode.freibierTag) {
            return ErzählerFrameMode.freibierTag;
        } else if(phaseMode == PhaseMode.ersteNacht) {
            return ErzählerFrameMode.ersteNacht;
        } else if(phaseMode == PhaseMode.nacht) {
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
        FrontendControl.spielerFrame.startTimeUpdateThread();
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

        switch(fraktionen.size()){
            case 0:
                return "Tod";
            case 1:
                return fraktionen.get(0).getName();
            case 2:
                if(getLivingPlayerStrings().size() == 2) {
                    Spieler spieler1 = findSpieler(getLivingPlayerStrings().get(0));
                    Spieler spieler2 = findSpieler(getLivingPlayerStrings().get(1));
                    if(liebespaar!=null && ((liebespaar.spieler1 == spieler1 && liebespaar.spieler2 == spieler2) ||
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

        for(Spieler currentSpieler : spieler) {
            if(currentSpieler.lebend) {
                allSpieler.add(currentSpieler.name);
            }
        }

        return allSpieler;
    }

    public Spieler findSpieler(String name) {
        for(Spieler currentSpieler : spieler)
        {
            if(currentSpieler.name.equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }

    public ArrayList<Spieler> getLivingPlayer() {
        ArrayList<Spieler> allSpieler = new ArrayList<>();

        for(Spieler currentSpieler : spieler) {
            if(currentSpieler.lebend) {
                allSpieler.add(currentSpieler);
            }
        }

        return allSpieler;
    }

    public Spieler findSpielerPerRolle(String name) {
        for(Nebenrolle nebenrolle : mitteNebenrollen)
        {
            if(nebenrolle.getName().equals(name)) {
                return findSpielerPerRolle(Sammler.name);
            }
        }

        for(Spieler currentSpieler : spieler)
        {
            if(currentSpieler.hauptrolle.getName().equals(name) || currentSpieler.nebenrolle.getName().equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }

    public FrontendControl getPlayerFrontendControl() {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN;
        frontendControl.strings = getLivingPlayerOrNoneStrings();

        return frontendControl;
    }

    public ArrayList<String> getLivingPlayerOrNoneStrings() {
        ArrayList<String> allSpieler = getLivingPlayerStrings();
        allSpieler.add("");

        return allSpieler;
    }

    public void killSpieler(Spieler spieler) {
        if(spieler!=null) {
            spieler.lebend = false;
            mitteHauptrollen.add(spieler.hauptrolle);
            mitteNebenrollen.add(spieler.nebenrolle);
            if(spieler.hauptrolle.getName().equals(Schattenpriester.name) && !spieler.nebenrolle.getName().equals(Schatten.name)) {
                Schattenpriester_Fraktion.deadSchattenPriester++;
            }

            if(Rolle.rolleLebend(Wölfin.name) && Wölfin.modus==Wölfin.WARTEND) {
                if(spieler.hauptrolle.getFraktion().getName().equals(Werwölfe.name)) {
                    Wölfin.modus = Wölfin.TÖTEND;
                }
            }
        }
    }

    public FrontendControl getPlayerCheckSpammableFrontendControl(Rolle rolle) {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN;
        frontendControl.strings = getPlayerCheckSpammableStrings(rolle);

        return frontendControl;
    }

    public ArrayList<String> getMitspielerCheckSpammableStrings(Rolle rolle) {
        Spieler spieler = findSpielerPerRolle(rolle.getName());

        ArrayList<String> mitspieler = getPlayerCheckSpammableStrings(rolle);
        if(spieler!=null) {
            mitspieler.remove(spieler.name);
        }

        return mitspieler;
    }

    public ArrayList<String> getPlayerCheckSpammableStrings(Rolle rolle) {
        ArrayList<String> allSpieler  = getLivingPlayerOrNoneStrings();
        if (!rolle.isSpammable() && rolle.besuchtLetzteNacht != null) {
            allSpieler.remove(rolle.besuchtLetzteNacht.name);
        }

        return allSpieler;
    }

    public Spieler findSpielerOrDeadPerRolle(String name) {
        for(Spieler currentSpieler : spieler)
        {
            if(currentSpieler.hauptrolle.getName().equals(name) || currentSpieler.nebenrolle.getName().equals(name)) {
                return currentSpieler;
            }
        }

        return null;
    }

    public FrontendControl getMitspielerCheckSpammableFrontendControl(Rolle rolle) {
        FrontendControl frontendControl = new FrontendControl();

        frontendControl.typeOfContent = FrontendControl.DROPDOWN;
        frontendControl.strings = getMitspielerCheckSpammableStrings(rolle);

        return frontendControl;
    }

    public ArrayList<Spieler> findSpielersPerRolle(String name) {
        ArrayList<Spieler> spielers = new ArrayList<>();

        for(Spieler currentSpieler : spieler)
        {
            if(currentSpieler.hauptrolle.getName().equals(name) || currentSpieler.nebenrolle.getName().equals(name)) {
                spielers.add(currentSpieler);
            }
        }

        return spielers;
    }

    public ArrayList<String> findSpielersStringsPerRolle(String name) {
        ArrayList<String> spielers = new ArrayList<>();

        for(Spieler currentSpieler : spieler)
        {
            if(currentSpieler.hauptrolle.getName().equals(name) || currentSpieler.nebenrolle.getName().equals(name)) {
                spielers.add(currentSpieler.name);
            }
        }

        return spielers;
    }

    public boolean spielerExists(String name) {
        return findSpieler(name) != null;
    }
}
