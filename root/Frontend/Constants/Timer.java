package root.Frontend.Constants;

import root.Frontend.Frame.SpielerFrame;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Timer {
    public static boolean timeThreadStarted = false;
    public static int time = 0;
    public static String timestring = "00:00:00";
    public SpielerFrame spielerframe;

    public void startTimeUpdateThread() {
        if (!timeThreadStarted) {
            timeThreadStarted = true;
            ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

            Runnable periodicTask = new Runnable() {
                public void run() {
                    time++;
                    generateTimeString();
                    spielerframe.clockLabel.setText(timestring);
                }
            };

            executor.scheduleAtFixedRate(periodicTask, 0, 1, TimeUnit.SECONDS);
        }
    }

    public static void generateTimeString() {
        int tmpTime = time;
        int firstDigit = 0;
        int secondDigit = 0;
        int thirdDigit = 0;
        int fourthDigit = 0;
        int fifthDigit = 0;
        int sixthDigit = 0;

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
        tmpTime -= fourthDigit;
        tmpTime = tmpTime / 6;
        fifthDigit = tmpTime % 10;
        tmpTime -= fifthDigit;
        tmpTime = tmpTime / 10;
        sixthDigit = tmpTime;

        timestring = "" + sixthDigit + "" + fifthDigit + ":" + fourthDigit + "" + thirdDigit + ":" + secondDigit + "" + firstDigit;
    }
}
