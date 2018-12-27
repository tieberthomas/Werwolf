package root;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.GameMode;
import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Frame.ÜbersichtsFrame;
import root.Frontend.FrontendControl;
import root.Frontend.Page.Page;
import root.Frontend.Utils.PageRefresher.Models.LoadMode;
import root.Logic.Game;
import root.Logic.Phases.*;
import root.ResourceManagement.DataManager;

import javax.swing.*;
import java.awt.event.WindowEvent;

public class GameController {
    private static DataManager dataManager;

    public static ErzählerFrame erzählerFrame;
    public static SpielerFrame spielerFrame;
    public static ÜbersichtsFrame übersichtsFrame;

    public static GameMode mode = GameMode.SETUP;

    static void startProgram() {
        erzählerFrame = new ErzählerFrame();
        dataManager = new DataManager();
    }

    public static void setupGame(LoadMode loadMode) {
        new Game();
        erzählerFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        spielerFrame = new SpielerFrame(erzählerFrame);

        if (loadMode == LoadMode.COMPOSITION) {
            dataManager.loadLastComposition(); //TODO load with a thread so playersetuppage can be displayed as soon as all the players are loaded
        } else if (loadMode == LoadMode.GAME) {
            dataManager.loadLastGame();
        }

        erzählerFrame.generateAllPageRefreshers();
    }

    public static void startGame() {
        übersichtsFrame = new ÜbersichtsFrame(erzählerFrame.frameJpanel.getHeight() + ÜbersichtsFrame.spaceFromErzählerFrame);
        erzählerFrame.toFront();
        Game.game.startGame(erzählerFrame, spielerFrame, übersichtsFrame);
        writeGame();
    }

    public static void writeComposition() {
        new Thread(() -> dataManager.writeComposition()).start();
    }

    public static void writeGame() {
        new Thread(() -> dataManager.writeGame()).start();
    }

    public static void respawnFrames() {
        spielerFrame.dispatchEvent(new WindowEvent(spielerFrame, WindowEvent.WINDOW_CLOSING));
        übersichtsFrame.dispatchEvent(new WindowEvent(übersichtsFrame, WindowEvent.WINDOW_CLOSING));

        int spielerFrameMode = spielerFrame.mode;
        Page savePage = spielerFrame.currentPage;
        spielerFrame = new SpielerFrame(erzählerFrame);
        spielerFrame.mode = spielerFrameMode;
        spielerFrame.buildScreenFromPage(savePage);

        übersichtsFrame = new ÜbersichtsFrame(erzählerFrame.frameJpanel.getHeight() + ÜbersichtsFrame.spaceFromErzählerFrame);

        FrontendControl.spielerFrame = spielerFrame;
        if (PhaseManager.phaseMode == PhaseMode.DAY || PhaseManager.phaseMode == PhaseMode.FREIBIER_DAY) {
            spielerFrame.generateDayPage();
        } else if (PhaseManager.phaseMode == PhaseMode.NORMAL_NIGHT || PhaseManager.phaseMode == PhaseMode.SETUP_NIGHT) {
            spielerFrame.buildScreenFromPage(savePage);
        }
    }


    public static void continueThreads() {
        try {
            if (mode == GameMode.SETUP_NIGHT) {
                synchronized (SetupNight.lock) {
                    SetupNight.lock.notify();
                }
            } else if (mode == GameMode.DAY || mode == GameMode.FREIBIER_DAY) {
                synchronized (Day.lock) {
                    Day.lock.notify();
                }
            } else if (mode == GameMode.NORMAL_NIGHT) {
                synchronized (NormalNight.lock) {
                    NormalNight.lock.notify();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Something went wrong with the Phases. (phasemode might be set wrong)");
        }
    }
}
