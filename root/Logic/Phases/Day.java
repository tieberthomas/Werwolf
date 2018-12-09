package root.Logic.Phases;

import root.Frontend.FrontendControl;
import root.Frontend.Utils.JButtonStyler;
import root.Frontend.Utils.TimeUpdater;
import root.Logic.Game;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Fraktionen.Bürger;
import root.Logic.Persona.Hauptrolle;
import root.Logic.Persona.Rollen.Bonusrollen.ReineSeele;
import root.Logic.Persona.Rollen.Bonusrollen.Wahrsager;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Schattenmensch;
import root.Logic.Spieler;
import root.ResourceManagement.ImagePath;

import java.util.ArrayList;
import java.util.List;

public class Day extends Thread {
    public static Object lock;
    public static boolean umbringenButton = false;
    public static Spieler umbringenSpieler;
    private Spieler priester;
    private List<Spieler> gebürgteSpieler;
    private Spieler richterin;
    private List<Spieler> verurteilteSpieler;

    public static final String dayTitle = "Opfer der Dorfabstimmung";

    @Override
    public void run() {
        day();

        if (Game.game.freibier) {
            day();
            Game.game.freibier = false;
        }

        PhaseManager.nextPhase();
    }

    private void day() {
        lock = new Object();
        synchronized (lock) {
            gebürgteSpieler = new ArrayList<>();
            verurteilteSpieler = new ArrayList<>();
            TimeUpdater.time = 0;

            FrontendControl.erzählerDefaultDayPage();
            FrontendControl.spielerDayPage();

            waitForAnswer();

            while (umbringenButton) {
                umbringenButton = false;
                killSpielerCheckLiebespaar(umbringenSpieler);

                FrontendControl.erzählerDefaultDayPage();
                FrontendControl.spielerDayPage();

                waitForAnswer();
            }

            Spieler chosenSpieler = Game.game.findSpieler(FrontendControl.erzählerFrame.chosenOption1);

            if (chosenSpieler != null) {
                Hauptrolle hauptrolleSpieler = chosenSpieler.hauptrolle;
                Bonusrolle bonusrolleSpieler = chosenSpieler.bonusrolle;

                //TODO cleanup and move into dayInvincibilityHandling Method
                if (bonusrolleSpieler.equals(ReineSeele.ID) && ((ReineSeele) chosenSpieler.bonusrolle).dayInvincibility ||
                        (gebürgteSpieler.contains(chosenSpieler) && hauptrolleSpieler.fraktion.equals(Bürger.ID)) ||
                        chosenSpieler.hauptrolle.equals(Schattenmensch.ID)) {
                    FrontendControl.erzählerAnnounceOpferPage(chosenSpieler, ImagePath.REINE_SEELE_OPEN_KARTE);
                    FrontendControl.spielerCardPicturePage(chosenSpieler.name, ImagePath.REINE_SEELE_OPEN_KARTE);
                    if (chosenSpieler.hauptrolle.equals(Schattenmensch.ID)) {
                        Schattenmensch.shallBeTransformed = true;
                        if (!(bonusrolleSpieler.equals(ReineSeele.ID) && ((ReineSeele) chosenSpieler.bonusrolle).dayInvincibility ||
                                (gebürgteSpieler.contains(chosenSpieler) && hauptrolleSpieler.fraktion.equals(Bürger.ID)))) {
                            Schattenmensch.changeReineSeele = true;
                        }
                    } else {
                        //TODO implementieren dass jeder nur einmal erfolgreich gepriestert werden kann
                        if (chosenSpieler.bonusrolle.equals(ReineSeele.ID) &&
                                !(gebürgteSpieler.contains(chosenSpieler) && hauptrolleSpieler.fraktion.equals(Bürger.ID))) {
                            ((ReineSeele) chosenSpieler.bonusrolle).dayInvincibility = false;
                        }
                    }

                    waitForAnswer();

                    checkRichterin(chosenSpieler);
                } else {
                    killSpielerCheckLiebespaar(chosenSpieler);
                    Wahrsager.opferFraktion = chosenSpieler.hauptrolle.fraktion;
                    if (!hauptrolleSpieler.fraktion.equals(Bürger.ID)) {
                        if (priester != null && priester.lebend && gebürgteSpieler.contains(chosenSpieler)) {
                            killSpielerCheckLiebespaar(priester);
                        }
                    }

                    checkRichterin(chosenSpieler);
                }
            } else {
                Wahrsager.opferFraktion = null;
            }

            while (umbringenButton) {
                umbringenButton = false;
                killSpielerCheckLiebespaar(umbringenSpieler);
            }
        }
    }

    private void checkVictory() {
        Winner winner = Game.game.checkVictory();

        if (winner != Winner.NO_WINNER) {
            showEndScreenPage(winner);
        }
    }

    private void checkRichterin(Spieler spieler) {
        if (spieler.hauptrolle.fraktion.equals(Bürger.ID)) {
            if (richterin != null && richterin.lebend && verurteilteSpieler.contains(spieler)) {
                killSpielerCheckLiebespaar(richterin);
            }
        }
    }

    private void killSpieler(Spieler spieler) {
        if (spieler != null) {
            Game.game.killSpieler(spieler);

            FrontendControl.erzählerAnnounceOpferPage(spieler);
            FrontendControl.spielerAnnounceOpferPage(spieler);
            FrontendControl.refreshÜbersichtsFrame();
        }
    }

    private void killSpielerCheckLiebespaar(Spieler spieler) {
        killSpieler(spieler);

        if (Game.game.liebespaar.getSpielerToDie() != null) {
            JButtonStyler.disableButton(FrontendControl.erzählerFrame.umbringenJButton);
            waitForAnswer();
            killSpieler(Game.game.liebespaar.getSpielerToDie());
        }

        waitForAnswer();

        checkVictory();
    }

    private void showEndScreenPage(Winner winner) {
        FrontendControl.erzählerEndScreenPage(winner);
        FrontendControl.spielerEndScreenPage(winner);

        waitForAnswer();
    }

    public void bürgen(Spieler priesterSpieler, Spieler verbürgerSpieler) {
        priester = priesterSpieler;
        gebürgteSpieler.add(verbürgerSpieler);
    }

    public void verurteilen(Spieler richterinSpieler, Spieler verurteilterSpieler) {
        richterin = richterinSpieler;
        verurteilteSpieler.add(verurteilterSpieler);
    }

    private static void waitForAnswer() {
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
