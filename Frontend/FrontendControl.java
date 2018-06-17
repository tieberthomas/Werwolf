package root.Frontend;

import root.Phases.Statement;

import java.util.ArrayList;

/**
 * Created by Steve on 16.05.2018.
 */
public class FrontendControl {
    public static final int LIST_WITHOUT_DISPLAY = 0;
    public static final int LIST_DISPLAY_AS_TEXT = 1;
    public static final int LIST_DISPLAY_AS_IMAGE = 2;

    public int typeOfContent;
    public ArrayList<String> content;

    public FrontendControl() {
        this.typeOfContent = LIST_WITHOUT_DISPLAY;
        this.content = new ArrayList<>();
    }

    public FrontendControl(ArrayList<String> content) {
        this.typeOfContent = LIST_WITHOUT_DISPLAY;
        this.content = content;
    }

    public FrontendControl(int typeOfContent, ArrayList<String> content) {
        this.typeOfContent = typeOfContent;
        this.content = content;
    }
}
