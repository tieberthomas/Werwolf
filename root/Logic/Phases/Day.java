package root.Logic.Phases;

import root.Controller.FrontendControl;
import root.Frontend.Utils.JButtonStyler;
import root.Frontend.Utils.TimeController;
import root.Logic.Game;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Rollen.Bonusrollen.Schutzengel;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Schattenmensch;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

public class Day extends Thread {
    public static Object lock;
    public static boolean umbringenButton = false;
    public static Spieler umbringenSpieler;
    private Spieler priester;
    private Spieler gebürgterSpieler;
    private Spieler richterin;
    private Spieler verurteilterSpieler;

    public static final String dayTitle = "Opfer der Dorfabstimmung";

    @Override
    public void run() {
        priester = null;
        gebürgterSpieler = null;
        verurteilterSpieler = null;

        day();

        PhaseManager.nextPhase();
    }

    private void day() {
        lock = new Object();
        synchronized (lock) {
            FrontendControl.lock = lock;

            TimeController.resetTimerAndStartCounting();

            FrontendControl.erzählerDefaultDayPage();
            FrontendControl.spielerDayPage();

            System.out.println(PhaseManager.nightCount + ". Tag beginnt!");

            FrontendControl.waitForAnswer();

            while (umbringenButton) {
                umbringenButton = false;
                killSpielerCheckLiebespaar(umbringenSpieler);

                FrontendControl.erzählerDefaultDayPage();
                FrontendControl.spielerDayPage();

                FrontendControl.waitForAnswer();
            }

            Spieler chosenSpieler = Game.game.findSpieler(FrontendControl.erzählerFrame.chosenOption1);

            if (chosenSpieler != null) {
                System.out.println("Das Dorf entscheidet sich " + chosenSpieler.name + " hinzurichten.");
                if (isDayInvincible(chosenSpieler)) {
                    handleInvincibility(chosenSpieler);

                    FrontendControl.announceOpferPage(chosenSpieler, ImagePath.REINE_SEELE_KARTE);

                    FrontendControl.waitForAnswer();
                } else {
                    killSpielerCheckLiebespaar(chosenSpieler);
                    checkPriester(chosenSpieler);
                }

                checkRichterin(chosenSpieler);
            }

            while (umbringenButton) {
                umbringenButton = false;
                killSpielerCheckLiebespaar(umbringenSpieler);
            }
        }
    }

    private void handleInvincibility(Spieler chosenSpieler) {
        if (isGebürgterSpieler(chosenSpieler)) {
            System.out.println("Der Priester " + priester.name + " hat " + chosenSpieler.name + " gerettet.");
        }

        if (chosenSpieler.bonusrolle.equals(Schutzengel.ID)) {
            ((Schutzengel) chosenSpieler.bonusrolle).dayInvincibility = false;
        }

        if (chosenSpieler.hauptrolle.equals(Schattenmensch.ID)) {
            Schattenmensch.shallBeTransformed = true;
        }
    }

    private void checkPriester(Spieler hingerichteterSpieler) {
        Fraktion fraktion = hingerichteterSpieler.getFraktion();
        if (!fraktion.equals(Bürger.ID)) {
            if (priester != null && priester.lebend && gebürgterSpieler.equals(hingerichteterSpieler)) {
                killSpielerCheckLiebespaar(priester);
                System.out.println("Der Priester " + priester.name + " hat sich in " + hingerichteterSpieler.name +
                        " getäuscht und wir ebenfalls hingerichtet.");
            }
        }
    }

    private void checkRichterin(Spieler hingerichteterSpieler) {
        if (hingerichteterSpieler.hauptrolle.fraktion.equals(Bürger.ID)) {
            if (richterin != null && richterin.lebend && verurteilterSpieler.equals(hingerichteterSpieler)) {
                killSpielerCheckLiebespaar(richterin);
                System.out.println("Die Richterin " + richterin.name + " hat sich in " + hingerichteterSpieler.name +
                        " getäuscht und wir ebenfalls hingerichtet.");
            }
        }
    }

    private boolean isDayInvincible(Spieler chosenSpieler) {
        return isInvincibleFromReineSeele(chosenSpieler) || isGebürgterSpieler(chosenSpieler) ||
                chosenSpieler.hauptrolle.equals(Schattenmensch.ID);
    }

    private boolean isInvincibleFromReineSeele(Spieler chosenSpieler) {
        return chosenSpieler.bonusrolle.equals(Schutzengel.ID) && ((Schutzengel) chosenSpieler.bonusrolle).dayInvincibility;
    }

    private boolean isGebürgterSpieler(Spieler chosenSpieler) {
        return gebürgterSpieler != null && gebürgterSpieler.equals(chosenSpieler) && chosenSpieler.getFraktion().equals(Bürger.ID);
    }

    private void checkVictory() {
        Winner winner = Game.game.checkVictory();

        if (winner != Winner.NO_WINNER) {
            FrontendControl.showEndScreenPage(winner);
        }
    }

    private void killSpieler(Spieler spieler) {
        if (spieler != null) {
            Game.game.killSpieler(spieler);
            FrontendControl.announceOpferPage(spieler);
        }
    }

    private void killSpielerCheckLiebespaar(Spieler spieler) {
        killSpieler(spieler);

        Spieler liebespartner = Game.game.liebespaar.getSpielerToDie();
        if (liebespartner != null) {
            JButtonStyler.disableButton(FrontendControl.erzählerFrame.umbringenJButton);
            FrontendControl.waitForAnswer();
            System.out.println(liebespartner.name + " sieht dass sein Liebespaartner " + spieler.name + " gestorben ist und begeht Suizid.");
            killSpieler(liebespartner);
        }

        FrontendControl.waitForAnswer();

        checkVictory();
    }

    public void bürgen(Spieler priesterSpieler, Spieler gebürgterSpieler) {
        priester = priesterSpieler;
        this.gebürgterSpieler = gebürgterSpieler;
    }

    public void verurteilen(Spieler richterinSpieler, Spieler verurteilterSpieler) {
        richterin = richterinSpieler;
        this.verurteilterSpieler = verurteilterSpieler;
    }
}
