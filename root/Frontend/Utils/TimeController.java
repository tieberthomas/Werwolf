package root.Frontend.Utils;

import root.Frontend.Frame.SpielerFrame;
import root.GameController;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeController {
    public static SpielerFrame spielerframe;
    private static boolean timeThreadStarted = false;

    private static final int deadLineMinutes = 17;
    private static int deadLineSeconds = deadLineMinutes * 60;

    private static final int colorChangeMinutes = 3;
    private static final int colorChangeSeconds = colorChangeMinutes * 60;
    private static Color urgentColor = Color.RED;

    private static int time = deadLineSeconds;
    private static boolean isCounting = false;

    public static void resetTimerAndStartCounting() {
        time = deadLineSeconds;
        isCounting = true;
    }

    public static void startTimeUpdateThread() {
        if (!timeThreadStarted) {
            timeThreadStarted = true;
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

            Runnable periodicTask = TimeController::count;

            executor.scheduleAtFixedRate(periodicTask, 0, 1, TimeUnit.SECONDS);
        }
    }

    private static void count() {
        if (isCounting) {
            time--;

            if (deadLineExceeded()) {
                startNight();
            } else {
                updateTimeString();
            }
        }
    }

    private static boolean deadLineExceeded() {
        return time < 0;
    }

    private static void startNight() {
        GameController.continueThreads();
        stopCounting();
    }

    public static void stopCounting() {
        isCounting = false;
    }

    private static void updateTimeString() {
        if (spielerframe != null && spielerframe.clockLabel != null) {
            String timestring = generateTimeString(time);
            spielerframe.clockLabel.setText(timestring);
            if (countDownIsNearTheEnd()) {
                spielerframe.clockLabel.setForeground(urgentColor);
            }
        }
    }

    private static String generateTimeString(int time) {
        int tmpTime = time;
        int firstDigit;
        int secondDigit;
        int thirdDigit;
        int fourthDigit;

        firstDigit = tmpTime % 10;
        tmpTime = tmpTime / 10;

        secondDigit = tmpTime % 6;
        tmpTime = tmpTime / 6;

        thirdDigit = tmpTime % 10;
        tmpTime = tmpTime / 10;

        fourthDigit = tmpTime % 6;

        return fourthDigit + "" + thirdDigit + ":" + secondDigit + "" + firstDigit;
    }

    private static boolean countDownIsNearTheEnd() {
        return time <= colorChangeSeconds;
    }
}
