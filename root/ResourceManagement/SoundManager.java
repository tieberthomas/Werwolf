package root.ResourceManagement;

import root.Logic.Phases.PhaseManager;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundManager {

    public static void playCannon() {
        playSound(SoundPath.CANNON_PATH);
    }

    public static void playFallAsleep() {
        if (PhaseManager.isVollmondNacht()) {
            playSound(SoundPath.FALL_ASLEEP_VOLLMOND_PATH);
        } else {
            playSound(SoundPath.FALL_ASLEEP_PATH);
        }
    }

    public static void playWakeUp() {
        playSound(SoundPath.WAKE_UP_PATH);
    }

    private static void playSound(String path) {
        File file = new File(path);

        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e.getMessage());
            System.out.println("could not play sound at path: " + path);
        }
    }

}
