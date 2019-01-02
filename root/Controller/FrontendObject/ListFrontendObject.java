package root.Controller.FrontendObject;

import java.util.ArrayList;
import java.util.List;

public class ListFrontendObject extends FrontendObject {
    public ListFrontendObject(List<String> strings) {
        this.typeOfContent = FrontendObjectType.LIST;
        this.displayedStrings = strings;
    }

    public ListFrontendObject(String title, String listString) {
        this.typeOfContent = FrontendObjectType.LIST;
        this.title = title;
        this.displayedStrings = new ArrayList<>();
        this.displayedStrings.add(listString);
    }
}
