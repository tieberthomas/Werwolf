package root.Controller.FrontendObject;

import root.Frontend.Utils.DropdownOptions;

import java.util.List;

public class NumberedDropdownListFrontendObject extends FrontendObject {
    public NumberedDropdownListFrontendObject(String title, List<String> strings) {
        this.typeOfContent = FrontendObjectType.NUMBERED_DROPDOWN_LIST;
        this.title = title;
        this.dropdownOptions = (DropdownOptions) strings;
    }
}
