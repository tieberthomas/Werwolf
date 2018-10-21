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

public class Day extends Thread {
    Game game;

    public static Object lock;
    public static boolean umbringenButton = false;
    public static Spieler umbringenSpieler;
    public static Spieler priester;
    public static ArrayList<Spieler> gebürgteSpieler;
    public static Spieler richterin;
    public static ArrayList<Spieler> verurteilteSpieler;

    public static String dayTitle = "Opfer der Dorfabstimmung";

    public Day(Game game) {
        this.game = game;
    }

    public void run() {
        gebürgteSpieler = new ArrayList<>();
        verurteilteSpieler = new ArrayList<>();
        day();

        if (game.freibier) {
            day();
            game.freibier = false;
        }

        game.night();//TODO remove
    }

    public void day() {
        lock = new Object();
        synchronized (lock) {
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
                        (gebürgteSpieler.contains(chosenSpieler) && hauptrolleSpieler.fraktion.name.equals(Bürger.NAME))) {
                    FrontendControl.erzählerAnnounceOpferPage(chosenSpieler, ImagePath.REINE_SEELE_OPEN_KARTE);
                    FrontendControl.spielerCardPicturePage(chosenSpieler.name, ImagePath.REINE_SEELE_OPEN_KARTE);
                    if (chosenSpieler.bonusrolle.name.equals(ReineSeele.NAME)) {
                        ((ReineSeele) chosenSpieler.bonusrolle).dayInvincibility = false;
                    }

                    waitForAnswer();

                    checkRichterin(chosenSpieler);
                } else {
                    killSpielerCheckLiebespaar(chosenSpieler);
                    Wahrsager.opferFraktion = chosenSpieler.hauptrolle.fraktion;
                    if (!hauptrolleSpieler.fraktion.name.equals(Bürger.NAME)) {
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

    public void checkVictory() {
        Winner winner = game.checkVictory();

        if (winner != Winner.NO_WINNER) {
            showEndScreenPage(winner);
        }
    }

    public void checkRichterin(Spieler spieler) {
        if (spieler.hauptrolle.fraktion.name.equals(Bürger.NAME)) {
            if (richterin != null && richterin.lebend && verurteilteSpieler.contains(spieler)) {
                killSpielerCheckLiebespaar(richterin);
            }
        }
    }

    public void killSpieler(Spieler spieler) {
        if (spieler != null) {
            game.killSpieler(spieler);

            FrontendControl.erzählerAnnounceOpferPage(spieler);
            FrontendControl.spielerAnnounceOpferPage(spieler);
        }
    }

    public void killSpielerCheckLiebespaar(Spieler spieler) {
        killSpieler(spieler);

        if (game.liebespaar.getPlayerToDie() != null) {
            JButtonStyler.disableButton(FrontendControl.erzählerFrame.umbringenJButton);
            waitForAnswer();
            killSpieler(game.liebespaar.getPlayerToDie());
        }

        waitForAnswer();

        checkVictory();
    }

    private void showEndScreenPage(Winner winner) {
        FrontendControl.erzählerEndScreenPage(winner);
        FrontendControl.spielerEndScreenPage(winner);

        waitForAnswer();
    }

    public void bürgen(String priester, String spieler) {
        Spieler priesterSpieler = game.findSpieler(priester);
        Spieler verbürgerSpieler = game.findSpieler(spieler);

        if (priesterSpieler != null && spieler != null) {
            Day.priester = priesterSpieler;
            Day.gebürgteSpieler.add(verbürgerSpieler);
        }
    }

    public void verurteilen(String richterin, String spieler) {
        Spieler richterinSpieler = game.findSpieler(richterin);
        Spieler verurteilterSpieler = game.findSpieler(spieler);

        if (richterinSpieler != null && spieler != null) {
            Day.richterin = richterinSpieler;
            Day.verurteilteSpieler.add(verurteilterSpieler);
        }
    }

    public static void waitForAnswer() {
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
