package root.Phases;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.ErzählerFrameMode;
import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Page.Page;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Bürger;
import root.Rollen.Fraktionen.Werwölfe;
import root.Rollen.Hauptrolle;
import root.Rollen.Hauptrollen.Werwölfe.Wölfin;
import root.Rollen.Nebenrollen.ReineSeele;
import root.Rollen.Nebenrollen.ReinesLicht;
import root.Rollen.Nebenrollen.Schatten;
import root.Rollen.Nebenrollen.Wahrsager;
import root.Rollen.Rolle;
import root.Spieler;
import root.mechanics.Liebespaar;

import java.util.ArrayList;

public class Tag extends Thread {
    public static ErzählerFrame erzählerFrame;
    public static SpielerFrame spielerFrame;
    public static Object lock;
    public static boolean umbringenButton = false;
    public static Spieler umbringenSpieler;
    public static Spieler priester;
    public static ArrayList<Spieler> gebürgteSpieler;
    public static Spieler richterin;
    public static ArrayList<Spieler> verurteilteSpieler;

    public static String dayTitle = "Opfer der Dorfabstimmung";

    public Tag(ErzählerFrame erzählerFrame, SpielerFrame spielerFrame) {
        this.erzählerFrame = erzählerFrame;
        this.spielerFrame = spielerFrame;
    }

    public void run() {
        gebürgteSpieler = new ArrayList<>();
        verurteilteSpieler = new ArrayList<>();
        day();

        if(erzählerFrame.mode == ErzählerFrameMode.freibierTag) {
            day();
        }

        PhaseManager.night(erzählerFrame, spielerFrame);
    }

    public void day() {
        lock = new Object();
        synchronized (lock) {
            Page dayPage = erzählerFrame.pageFactory.generateDefaultDayPage();
            erzählerFrame.buildScreenFromPage(dayPage);

            spielerFrame.generateDayPage();
            spielerFrame.time = 0;

            waitForAnswer();

            while(umbringenButton) {
                umbringenButton = false;
                killSpielerCheckLiebespaar(umbringenSpieler);
            }

            Spieler chosenSpieler = Spieler.findSpieler(erzählerFrame.chosenOption1);

            if (chosenSpieler != null) {
                String nebenrolleSpieler = chosenSpieler.nebenrolle.getName();
                Hauptrolle hauptrolleSpieler = chosenSpieler.hauptrolle;

                if (nebenrolleSpieler.equals(ReineSeele.name) && ((ReineSeele)chosenSpieler.nebenrolle).dayInvincibility ||
                        (gebürgteSpieler.contains(chosenSpieler) && hauptrolleSpieler.getFraktion().getName().equals(Bürger.name))) {
                    dayPage = spielerFrame.pageFactory.generateStaticImagePage(ReinesLicht.name, ReineSeele.imagePath);
                    spielerFrame.buildScreenFromPage(dayPage);
                    if(chosenSpieler.nebenrolle.getName().equals(ReineSeele.name)) {
                        ((ReineSeele) chosenSpieler.nebenrolle).dayInvincibility = false;
                    }

                    waitForAnswer();

                    checkRichterin(hauptrolleSpieler);
                }
                else {
                    killSpielerCheckLiebespaar(chosenSpieler);
                    Wahrsager.opferFraktion = chosenSpieler.hauptrolle.getFraktion();
                    if(!hauptrolleSpieler.getFraktion().getName().equals(Bürger.name)) {
                        if(priester.lebend) {
                            killSpielerCheckLiebespaar(priester);
                        }
                    }

                    checkRichterin(hauptrolleSpieler);
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
            spielerFrame.buildScreenFromPage(spielerFrame.pageFactory.generateEndScreenPage(victory));
            erzählerFrame.buildScreenFromPage(spielerFrame.pageFactory.generateEndScreenPage(victory));

            waitForAnswer();
        }
    }

    public static void checkRichterin(Hauptrolle hauptrolleSpieler) {
        if(hauptrolleSpieler.getFraktion().getName().equals(Bürger.name)) {
            if(richterin.lebend) {
                killSpielerCheckLiebespaar(richterin);
            }
        }
    }

    public static void killSpieler(Spieler spieler) {
        if(spieler!=null) {
            spieler.lebend = false;

            if(Rolle.rolleLebend(Wölfin.name) && Wölfin.modus==Wölfin.WARTEND) {
                if(spieler.hauptrolle.getFraktion().getName().equals(Werwölfe.name)) {
                    Wölfin.modus = Wölfin.TÖTEND;
                }
            }

            Rolle.mitteHauptrollen.add(spieler.hauptrolle);
            Rolle.mitteNebenrollen.add(spieler.nebenrolle);

            Page dayPage = spielerFrame.pageFactory.generateTwoImagePage(spieler.name, spieler.hauptrolle.getImagePath(), spieler.nebenrolle.getImagePath());
            spielerFrame.buildScreenFromPage(dayPage);

            dayPage = erzählerFrame.pageFactory.generateAnnounceVictimsDayPage(spieler.name);
            erzählerFrame.buildScreenFromPage(dayPage);
        }
    }

    public static void killSpielerCheckLiebespaar(Spieler spieler){
        killSpieler(spieler);

        if(Liebespaar.getPlayerToDie()!=null) {
            erzählerFrame.disableButton(erzählerFrame.umbringenJButton);
            waitForAnswer();
            killSpieler(Liebespaar.getPlayerToDie());
        }

        waitForAnswer();

        checkVictory();
    }

    public static void waitForAnswer() {
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
