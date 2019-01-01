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

    public FrontendObject(List<SchnüfflerInformation> informationen, String title) {
        this.typeOfContent = FrontendObjectType.SCHNÜFFLER_INFO;
        this.title = title;
        this.informationen = informationen;
    }

    public FrontendObject(String title, String imagePath1, String imagePath2, List<String> displayedStrings) {
        this.title = title;
        this.imagePath = imagePath1;
        this.imagePath2 = imagePath2;
        this.displayedStrings = displayedStrings;
        typeOfContent = FrontendObjectType.TWO_IMAGES;
    }
}
