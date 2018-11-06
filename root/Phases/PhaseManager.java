package root.Phases;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.ErzählerFrameMode;
import root.Frontend.FrontendControl;
import root.mechanics.Game;

public class PhaseManager extends Thread {
    public static Game game;

    public static PhaseMode phaseMode;

    public static void firstnight(ErzählerFrame erzählerFrame) {
        erzählerFrame.mode = ErzählerFrameMode.FIRST_NIGHT;
        phaseMode = PhaseMode.FIRST_NIGHT;
        FirstNight firstNight = new FirstNight(game);
        firstNight.start();
    }

    public static void night() {
        FrontendControl.erzählerFrame.mode = ErzählerFrameMode.NORMAL_NIGHT;
        phaseMode = PhaseMode.NORMAL_NIGHT;
        NormalNight normalNight = new NormalNight(game);
        normalNight.start();
    }

    public static void day() {
        FrontendControl.erzählerFrame.mode = ErzählerFrameMode.DAY;
        phaseMode = PhaseMode.DAY;
        Day day = new Day(game);
        game.day = day;
        day.start();
    }

    public static void freibierDay() {
        FrontendControl.erzählerFrame.mode = ErzählerFrameMode.FREIBIER_DAY;
        phaseMode = PhaseMode.FREIBIER_DAY;
        Day day = new Day(game);
        game.day = day;
        day.start();
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
}
