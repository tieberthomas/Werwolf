package root.Controller.FrontendObject;

import root.Frontend.Utils.DropdownOptions;
import root.Logic.Persona.Rollen.Constants.SchnüfflerInformation;

import java.util.List;

public class FrontendObject {
    public FrontendObjectType typeOfContent;
    public String title;
    public DropdownOptions dropdownOptions;
    public List<String> displayedStrings;
    public List<SchnüfflerInformation> informationen;
    public String imagePath;
    public String imagePath2;
    public String note;

    public boolean hatZurückButton = false;

    public FrontendObject() {
        this.typeOfContent = FrontendObjectType.SKIP;
    }
}
