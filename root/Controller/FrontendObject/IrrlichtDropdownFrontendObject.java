package root.Controller.FrontendObject;

import root.Frontend.Utils.DropdownOptions;

import java.util.List;

public class IrrlichtDropdownFrontendObject extends FrontendObject {
    public IrrlichtDropdownFrontendObject(List<String> strings) {
        this.typeOfContent = FrontendObjectType.IRRLICHT_DROPDOWN;
        this.dropdownOptions = (DropdownOptions) strings;
    }
}
