package root.Controller.FrontendObject;

import root.Frontend.Utils.DropdownOptions;

public class DropdownFrontendObject extends FrontendObject {
    public DropdownFrontendObject(DropdownOptions dropdownOptions) {
        this.typeOfContent = FrontendObjectType.DROPDOWN;
        this.dropdownOptions = dropdownOptions;
    }

    public DropdownFrontendObject(String title, DropdownOptions dropdownOptions) {
        this.typeOfContent = FrontendObjectType.DROPDOWN;
        this.title = title;
        this.dropdownOptions = dropdownOptions;
    }
}
