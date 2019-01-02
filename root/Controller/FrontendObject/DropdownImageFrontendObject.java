package root.Controller.FrontendObject;

import root.Frontend.Utils.DropdownOptions;

public class DropdownImageFrontendObject extends FrontendObject {
    public DropdownImageFrontendObject(DropdownOptions dropdownOptions, String imagePath) {
        this.typeOfContent = FrontendObjectType.DROPDOWN_IMAGE;
        this.dropdownOptions = dropdownOptions;
        this.imagePath = imagePath;
    }
}
