package root.Frontend.Utils;

import root.Frontend.Frame.SpielerFrame;

import java.awt.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimeController {
    private static final int deadLine = 20; //minutes
    private static int currentDeadLine = 20; //minutes
    private static final int countdownStart = 18; //minutes
    private static int deadLineSeconds = currentDeadLine * 60;
    private static final int countdownStartSeconds = countdownStart * 60;

    private static Color countdownColor = Color.RED;

    public static SpielerFrame spielerframe;
    private static boolean timeThreadStarted = false;
    private static int time = 0;
    private static String timestring = "00:00";

    public static void startTimeUpdateThread() {
        if (!timeThreadStarted) {
            timeThreadStarted = true;
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

            Runnable periodicTask = () -> {
                time++;
                generateTimeString();
                if (spielerframe != null && spielerframe.clockLabel != null) {
                    spielerframe.clockLabel.setText(timestring);
                    if(countDownMode()) {
                        spielerframe.clockLabel.setForeground(countdownColor);
                    }
                }
            };

            executor.scheduleAtFixedRate(periodicTask, 0, 1, TimeUnit.SECONDS);
        }
    }

    public static void reset() {
        time = 0;
        currentDeadLine = deadLine;
    }

    private static boolean countDownMode() {
        return time>countdownStartSeconds;
    }

    private static void generateTimeString() {
        int tmpTime = time;
        int firstDigit = 0;
        int secondDigit = 0;
        int thirdDigit = 0;
        int fourthDigit = 0;

        if(countDownMode()) {
            tmpTime = deadLineSeconds - time;
        }

        firstDigit = tmpTime % 10;
        tmpTime -= firstDigit;
        tmpTime = tmpTime / 10;

        secondDigit = tmpTime % 6;
        tmpTime -= secondDigit;
        tmpTime = tmpTime / 6;

        thirdDigit = tmpTime % 10;
        tmpTime -= thirdDigit;
        tmpTime = tmpTime / 10;

        fourthDigit = tmpTime % 6;

        timestring = fourthDigit + "" + thirdDigit + ":" + secondDigit + "" + firstDigit;
    }
}
