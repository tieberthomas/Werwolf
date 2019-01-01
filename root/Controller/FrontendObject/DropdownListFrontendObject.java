package root.Controller.FrontendObject;

import root.Frontend.Utils.DropdownOptions;

import java.util.List;

public class DropdownListFrontendObject extends FrontendObject {
    public DropdownListFrontendObject(List<String> strings) {
        this.typeOfContent = FrontendObjectType.DROPDOWN_LIST;
        this.dropdownOptions = (DropdownOptions) strings;
    }

    public DropdownListFrontendObject(String title, List<String> strings) {
        this.typeOfContent = FrontendObjectType.DROPDOWN_LIST;
        this.title = title;
        this.dropdownOptions = (DropdownOptions) strings;
    }
}
