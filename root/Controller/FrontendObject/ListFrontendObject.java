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
}
