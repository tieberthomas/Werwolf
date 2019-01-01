package root.Controller.FrontendObject;

import root.Frontend.Utils.DropdownOptions;

import java.util.List;

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

    public DropdownFrontendObject(FrontendObjectType typeOfContent, List<String> strings) {
        this.typeOfContent = typeOfContent;
        this.dropdownOptions = (DropdownOptions) strings;
    }

    public DropdownFrontendObject(FrontendObjectType typeOfContent, String title, List<String> strings) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.dropdownOptions = (DropdownOptions) strings;
    }
}
