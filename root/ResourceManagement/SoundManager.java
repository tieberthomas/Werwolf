package root.ResourceManagement;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class SoundManager {
    public static final JFXPanel fxPanel = new JFXPanel();

    public static void playCannon() {
        playSound(SoundPath.CANNON_PATH);
    }

    private static void playSound(String path) {
        Media hit = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }
}
