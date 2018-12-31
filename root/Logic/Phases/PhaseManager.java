package root.Logic.Phases;

import root.Controller.FrontendControl;
import root.Frontend.Factories.ErzählerPageFactory;
import root.Frontend.Frame.GameMode;
import root.GameController;
import root.Logic.Game;

public class PhaseManager extends Thread {
    public static Object lock = new Object();
    public static int nightCount = 0;

    public static PhaseMode phaseMode;

    public void run() {
        lock = new Object();
        synchronized (lock) {
            setupNight();
            for (nightCount = 1; true; nightCount++) {
                day();
                night();
            }
        }
    }

    private void setupNight() {
        ErzählerPageFactory.nightGenerationModeSetupNight = true;
        GameController.mode = GameMode.SETUP_NIGHT;
        phaseMode = PhaseMode.SETUP_NIGHT;
        SetupNight setupNight = new SetupNight();
        setupNight.start();
        waitForAnswer();
        ErzählerPageFactory.nightGenerationModeSetupNight = false;
    }

    private void night() {
        GameController.mode = GameMode.NORMAL_NIGHT;
        phaseMode = PhaseMode.NORMAL_NIGHT;
        NormalNight normalNight = new NormalNight();
        normalNight.start();
        waitForAnswer();
    }

    private void day() {
        if (Game.game.freibier) {
            GameController.mode = GameMode.FREIBIER_DAY;
            phaseMode = PhaseMode.FREIBIER_DAY;
        } else {
            GameController.mode = GameMode.DAY;
            phaseMode = PhaseMode.DAY;
        }
        Day day = new Day();
        Game.game.day = day;
        day.start();
        waitForAnswer();
    }

    public static GameMode parsePhaseMode() { //TODO automapper?
        if (phaseMode == PhaseMode.DAY) {
            return GameMode.DAY;
        } else if (phaseMode == PhaseMode.FREIBIER_DAY) {
            return GameMode.FREIBIER_DAY;
        } else if (phaseMode == PhaseMode.SETUP_NIGHT) {
            return GameMode.SETUP_NIGHT;
        } else if (phaseMode == PhaseMode.NORMAL_NIGHT) {
            return GameMode.NORMAL_NIGHT;
        } else {
            return GameMode.SETUP;
        }
    }

    public void waitForAnswer() {
        FrontendControl.refreshÜbersichtsFrame();
        try {
            lock.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void nextPhase() {
        synchronized (lock) {
            lock.notify();
        }
    }
}
