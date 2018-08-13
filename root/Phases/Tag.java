package root.Phases;

import root.Frontend.Frame.ErzählerFrameMode;
import root.Frontend.Frame.SpielerFrame;
import root.Frontend.FrontendControl;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrollen.ReineSeele;
import root.Rollen.Nebenrollen.Wahrsager;
import root.Spieler;
import root.mechanics.Game;
import root.mechanics.Liebespaar;

import java.util.ArrayList;

public class Tag extends Thread {
    Game game;

    public static Object lock;
    public static boolean umbringenButton = false;
    public static Spieler umbringenSpieler;
    public static Spieler priester;
    public static ArrayList<Spieler> gebürgteSpieler;
    public static Spieler richterin;
    public static ArrayList<Spieler> verurteilteSpieler;

    public static String dayTitle = "Opfer der Dorfabstimmung";

    public Tag(Game game) {
        this.game = game;
    }

    public void run() {
        gebürgteSpieler = new ArrayList<>();
        verurteilteSpieler = new ArrayList<>();
        day();

        if(FrontendControl.erzählerFrame.mode == ErzählerFrameMode.freibierTag) {
            day();
        }

        game.night();
    }

    public void day() {
        lock = new Object();
        synchronized (lock) {
            SpielerFrame.time = 0;

            FrontendControl.erzählerDefaultDayPage();
            FrontendControl.spielerDayPage();

            waitForAnswer();

            while(umbringenButton) {
                umbringenButton = false;
                killSpielerCheckLiebespaar(umbringenSpieler);
            }

            Spieler chosenSpieler = Spieler.findSpieler(FrontendControl.erzählerFrame.chosenOption1);

            if (chosenSpieler != null) {
                String nebenrolleSpieler = chosenSpieler.nebenrolle.getName();
                Hauptrolle hauptrolleSpieler = chosenSpieler.hauptrolle;

                if (nebenrolleSpieler.equals(ReineSeele.name) && ((ReineSeele)chosenSpieler.nebenrolle).dayInvincibility ||
                        (gebürgteSpieler.contains(chosenSpieler) && hauptrolleSpieler.getFraktion().getName().equals(Bürger.name))) {
                    FrontendControl.spielerCardPicturePage(chosenSpieler.name, ReineSeele.imagePath);
                    if(chosenSpieler.nebenrolle.getName().equals(ReineSeele.name)) {
                        ((ReineSeele) chosenSpieler.nebenrolle).dayInvincibility = false;
                    }

                    waitForAnswer();

                    checkRichterin(chosenSpieler);
                }
                else {
                    killSpielerCheckLiebespaar(chosenSpieler);
                    Wahrsager.opferFraktion = chosenSpieler.hauptrolle.getFraktion();
                    if(!hauptrolleSpieler.getFraktion().getName().equals(Bürger.name)) {
                        if(priester != null && priester.lebend && gebürgteSpieler.contains(chosenSpieler)) {
                            killSpielerCheckLiebespaar(priester);
                        }
                    }

                    checkRichterin(chosenSpieler);
                }
            } else {
                Wahrsager.opferFraktion = null;
            }

            while(umbringenButton) {
                umbringenButton = false;
                killSpielerCheckLiebespaar(umbringenSpieler);
            }
        }
    }

    public static void checkVictory() {
        String victory = Spieler.checkVictory();

        if (victory != null) {
            showEndScreenPage(victory);
        }
    }

    public static void checkRichterin(Spieler spieler) {
        if(spieler.hauptrolle.getFraktion().getName().equals(Bürger.name)) {
            if(richterin != null && richterin.lebend && verurteilteSpieler.contains(spieler)) {
                killSpielerCheckLiebespaar(richterin);
            }
        }
    }

    public static void killSpieler(Spieler spieler) {
        if(spieler!=null) {
            Spieler.killSpieler(spieler);

            FrontendControl.erzählerAnnounceVictimPage(spieler);
            FrontendControl.spielerAnnounceVictimPage(spieler);
        }
    }

    public static void killSpielerCheckLiebespaar(Spieler spieler){
        killSpieler(spieler);

        if(Liebespaar.getPlayerToDie()!=null) {
            FrontendControl.erzählerFrame.disableButton(FrontendControl.erzählerFrame.umbringenJButton);
            waitForAnswer();
            killSpieler(Liebespaar.getPlayerToDie());
        }

        waitForAnswer();

        checkVictory();
    }

    public static void showEndScreenPage(String victory) {
        FrontendControl.erzählerEndScreenPage(victory);
        FrontendControl.spielerEndScreenPage(victory);

        waitForAnswer();
    }

    public static void bürgen(String priester, String spieler) {
        Spieler priesterSpieler = Spieler.findSpieler(priester);
        Spieler verbürgerSpieler = Spieler.findSpieler(spieler);

        if(priesterSpieler!=null && spieler!=null) {
            Tag.priester = priesterSpieler;
            Tag.gebürgteSpieler.add(verbürgerSpieler);
        }
    }

    public static void verurteilen(String richterin, String spieler) {
        Spieler richterinSpieler = Spieler.findSpieler(richterin);
        Spieler verurteilterSpieler = Spieler.findSpieler(spieler);

        if(richterinSpieler!=null && spieler!=null) {
            Tag.richterin = richterinSpieler;
            Tag.verurteilteSpieler.add(verurteilterSpieler);
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
