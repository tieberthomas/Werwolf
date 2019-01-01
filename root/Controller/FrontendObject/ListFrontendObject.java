package root.Controller.FrontendObject;

import java.util.ArrayList;
import java.util.List;

public class ListFrontendObject extends FrontendObject {
    public ListFrontendObject(FrontendObjectType typeOfContent, List<String> strings) {
        this.typeOfContent = typeOfContent;
        this.displayedStrings = strings;
    }

    public ListFrontendObject(FrontendObjectType typeOfContent, String title, List<String> strings) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.displayedStrings = strings;
    }

    public ListFrontendObject(String title, String listString) {
        this.typeOfContent = FrontendObjectType.LIST;
        this.title = title;
        this.displayedStrings = new ArrayList<>();
        this.displayedStrings.add(listString);
    }

    public ListFrontendObject(FrontendObjectType typeOfContent, String title, List<String> displayedStrings, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.displayedStrings = displayedStrings;
        this.imagePath = imagePath;
    }

    public ListFrontendObject(FrontendObjectType typeOfContent, String title, String note, List<String> displayedStrings) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.note = note;
        this.displayedStrings = displayedStrings;
    }
}
