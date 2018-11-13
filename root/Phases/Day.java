package root.Phases;

import root.Frontend.FrontendControl;
import root.Frontend.Utils.JButtonStyler;
import root.Frontend.Utils.TimeUpdater;
import root.Persona.Fraktionen.Bürger;
import root.Persona.Hauptrolle;
import root.Persona.Rollen.Bonusrollen.ReineSeele;
import root.Persona.Rollen.Bonusrollen.Wahrsager;
import root.ResourceManagement.ImagePath;
import root.Spieler;
import root.mechanics.Game;

import java.util.ArrayList;
import java.util.List;

public class Day extends Thread {
    Game game;

    public static Object lock;
    public static boolean umbringenButton = false;
    public static Spieler umbringenSpieler;
    private Spieler priester;
    private List<Spieler> gebürgteSpieler;
    private Spieler richterin;
    private List<Spieler> verurteilteSpieler;

    public static final String dayTitle = "Opfer der Dorfabstimmung";

    public Day(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        day();

        if (game.freibier) {
            day();
            game.freibier = false;
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
            }

            Spieler chosenSpieler = game.findSpieler(FrontendControl.erzählerFrame.chosenOption1);

            if (chosenSpieler != null) {
                String bonusrolleSpieler = chosenSpieler.bonusrolle.name;
                Hauptrolle hauptrolleSpieler = chosenSpieler.hauptrolle;

                if (bonusrolleSpieler.equals(ReineSeele.NAME) && ((ReineSeele) chosenSpieler.bonusrolle).dayInvincibility ||
                        (gebürgteSpieler.contains(chosenSpieler) && hauptrolleSpieler.fraktion.equals(Bürger.NAME))) {
                    FrontendControl.erzählerAnnounceOpferPage(chosenSpieler, ImagePath.REINE_SEELE_OPEN_KARTE);
                    FrontendControl.spielerCardPicturePage(chosenSpieler.name, ImagePath.REINE_SEELE_OPEN_KARTE);
                    if (chosenSpieler.bonusrolle.equals(ReineSeele.NAME)) {
                        ((ReineSeele) chosenSpieler.bonusrolle).dayInvincibility = false;
                    }

                    waitForAnswer();

                    checkRichterin(chosenSpieler);
                } else {
                    killSpielerCheckLiebespaar(chosenSpieler);
                    Wahrsager.opferFraktion = chosenSpieler.hauptrolle.fraktion;
                    if (!hauptrolleSpieler.fraktion.equals(Bürger.NAME)) {
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
        Winner winner = game.checkVictory();

        if (winner != Winner.NO_WINNER) {
            showEndScreenPage(winner);
        }
    }

    private void checkRichterin(Spieler spieler) {
        if (spieler.hauptrolle.fraktion.equals(Bürger.NAME)) {
            if (richterin != null && richterin.lebend && verurteilteSpieler.contains(spieler)) {
                killSpielerCheckLiebespaar(richterin);
            }
        }
    }

    private void killSpieler(Spieler spieler) {
        if (spieler != null) {
            game.killSpieler(spieler);

            FrontendControl.erzählerAnnounceOpferPage(spieler);
            FrontendControl.spielerAnnounceOpferPage(spieler);
        }
    }

    private void killSpielerCheckLiebespaar(Spieler spieler) {
        killSpieler(spieler);

        if (game.liebespaar.getSpielerToDie() != null) {
            JButtonStyler.disableButton(FrontendControl.erzählerFrame.umbringenJButton);
            waitForAnswer();
            killSpieler(game.liebespaar.getSpielerToDie());
        }

        waitForAnswer();

        checkVictory();
    }

    private void showEndScreenPage(Winner winner) {
        FrontendControl.erzählerEndScreenPage(winner);
        FrontendControl.spielerEndScreenPage(winner);

        waitForAnswer();
    }

    public void bürgen(String priesterName, String spielerName) {
        Spieler priesterSpieler = game.findSpieler(priesterName);
        Spieler verbürgerSpieler = game.findSpieler(spielerName);

        if (priesterSpieler != null && spielerName != null) {
            priester = priesterSpieler;
            gebürgteSpieler.add(verbürgerSpieler);
        }
    }

    public void verurteilen(String richterinName, String spielerName) {
        Spieler richterinSpieler = game.findSpieler(richterinName);
        Spieler verurteilterSpieler = game.findSpieler(spielerName);

        if (richterinSpieler != null && spielerName != null) {
            richterin = richterinSpieler;
            verurteilteSpieler.add(verurteilterSpieler);
        }
    }

    private static void waitForAnswer() {
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
