package root.Controller.FrontendObject;

import java.util.List;

public class ListWithNoteFrontendObject extends FrontendObject {
    public ListWithNoteFrontendObject(String title, String note, List<String> displayedStrings) {
        this.typeOfContent = FrontendObjectType.LIST_WITH_NOTE;
        this.title = title;
        this.note = note;
        this.displayedStrings = displayedStrings;
    }
}
