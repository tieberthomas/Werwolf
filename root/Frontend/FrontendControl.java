package root.Frontend;

import java.util.ArrayList;

/**
 * Created by Steve on 16.05.2018.
 */
public class FrontendControl {
    public static final int DROPDOWN_WITHOUT_SUGGESTIONS = 0;
    public static final int DROPDOWN_WITH_SUGGESTIONS = 1;
    public static final int STATIC_LIST = 2;
    public static final int STATIC_IMAGE = 3;

    public int typeOfContent;
    public ArrayList<String> content;
    public String imagePath;
    public String title;

    public FrontendControl() {
        this.typeOfContent = DROPDOWN_WITHOUT_SUGGESTIONS;
        this.content = new ArrayList<>();
    }

    public FrontendControl(ArrayList<String> content) {
        this.typeOfContent = DROPDOWN_WITHOUT_SUGGESTIONS;
        this.content = content;
    }

    public FrontendControl(ArrayList<String> content, String title) {
        this.typeOfContent = DROPDOWN_WITHOUT_SUGGESTIONS;
        this.content = content;
        this.title = title;
    }

    public FrontendControl(String imagePath) {
        this.typeOfContent = STATIC_IMAGE;
        this.imagePath = imagePath;
    }

    public FrontendControl(String imagePath, String title) {
        this.typeOfContent = STATIC_IMAGE;
        this.imagePath = imagePath;
        this.title = title;
    }

    public FrontendControl(int typeOfContent, ArrayList<String> content) {
        this.typeOfContent = typeOfContent;
        this.content = content;
    }

    public FrontendControl(int typeOfContent, ArrayList<String> content, String title) {
        this.typeOfContent = typeOfContent;
        this.content = content;
        this.title = title;
    }
}
