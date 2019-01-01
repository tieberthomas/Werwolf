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

    public DropdownFrontendObject(FrontendObjectType typeOfContent, DropdownOptions dropdownOptions, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.dropdownOptions = dropdownOptions;
        this.imagePath = imagePath;
    }

    public DropdownFrontendObject(String title, DropdownOptions dropdownOptions, List<String> displayedStrings) {
        this.typeOfContent = FrontendObjectType.DROPDOWN_SEPARATED_LIST;
        this.title = title;
        this.dropdownOptions = dropdownOptions;
        this.displayedStrings = displayedStrings;
    }
}
