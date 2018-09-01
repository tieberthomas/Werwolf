package root.ResourceManagement;

public class ResourcePath {
    public static String SAVE_FILE_EXTENSION = ".txt";
    public static String SAVE_FILE_PATH = System.getenv("APPDATA") + "\\Werwolf\\";
    public static String LAST_GAME_FILE = SAVE_FILE_PATH + "lastGame" + SAVE_FILE_EXTENSION;
    public static String LAST_GAME_COMPOSITION_FILE = SAVE_FILE_PATH + "lastGameComposition" + SAVE_FILE_EXTENSION;
}
