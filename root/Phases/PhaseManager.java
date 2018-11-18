package root.Phases;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.ErzählerFrameMode;
import root.Frontend.FrontendControl;
import root.mechanics.Game;

public class PhaseManager extends Thread {
    public static Object lock = new Object();
    public static int nightCount = 0;

    public static PhaseMode phaseMode;

    public void run() {
        lock = new Object();
        synchronized (lock) {
            firstnight();
            while (true) {
                day();
                nightCount++;
                night();
            }
        }
    }

    private void firstnight() {
        ErzählerFrame.mode = ErzählerFrameMode.FIRST_NIGHT;
        phaseMode = PhaseMode.FIRST_NIGHT;
        FirstNight firstNight = new FirstNight();
        firstNight.start();
        waitForAnswer();
    }

    private void night() {
        ErzählerFrame.mode = ErzählerFrameMode.NORMAL_NIGHT;
        phaseMode = PhaseMode.NORMAL_NIGHT;
        NormalNight normalNight = new NormalNight();
        normalNight.start();
        waitForAnswer();
    }

    private void day() {
        if (Game.game.freibier) {
            ErzählerFrame.mode = ErzählerFrameMode.FREIBIER_DAY;
            phaseMode = PhaseMode.FREIBIER_DAY;
        } else {
            ErzählerFrame.mode = ErzählerFrameMode.DAY;
            phaseMode = PhaseMode.DAY;
        }
        Day day = new Day();
        Game.game.day = day;
        day.start();
        waitForAnswer();
    }

    public static ErzählerFrameMode parsePhaseMode() { //TODO automapper?
        if (phaseMode == PhaseMode.DAY) {
            return ErzählerFrameMode.DAY;
        } else if (phaseMode == PhaseMode.FREIBIER_DAY) {
            return ErzählerFrameMode.FREIBIER_DAY;
        } else if (phaseMode == PhaseMode.FIRST_NIGHT) {
            return ErzählerFrameMode.FIRST_NIGHT;
        } else if (phaseMode == PhaseMode.NORMAL_NIGHT) {
            return ErzählerFrameMode.NORMAL_NIGHT;
        } else {
            return ErzählerFrameMode.SETUP;
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
