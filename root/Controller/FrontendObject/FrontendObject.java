package root.Controller.FrontendObject;

import root.Frontend.Utils.DropdownOptions;
import root.Logic.Persona.Rollen.Constants.SchnüfflerInformation;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;

import java.util.ArrayList;
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

    public FrontendObject(String title) {
        this.typeOfContent = FrontendObjectType.TITLE;
        this.title = title;
    }

    public FrontendObject(String title, String listString) {
        this.typeOfContent = FrontendObjectType.LIST;
        this.title = title;
        displayedStrings = new ArrayList<>();
        displayedStrings.add(listString);
    }

    public FrontendObject(FrontendObjectType typeOfContent, String title, List<String> strings) {
        this(typeOfContent, strings);
        this.title = title;
    }

    public FrontendObject(DropdownOptions dropdownOptions) {
        this.typeOfContent = FrontendObjectType.DROPDOWN;
        this.dropdownOptions = dropdownOptions;
    }

    public FrontendObject(FrontendObjectType typeOfContent, List<String> strings) {
        this.typeOfContent = typeOfContent;
        if (typeOfContent.isDropdown()) {
            dropdownOptions = (DropdownOptions) strings;
        } else {
            displayedStrings = strings;
        }
    }

    public FrontendObject(String title, DropdownOptions dropdownOptions) {
        this.typeOfContent = FrontendObjectType.DROPDOWN;
        this.title = title;
        this.dropdownOptions = dropdownOptions;
    }

    public FrontendObject(FrontendObjectType typeOfContent, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.imagePath = imagePath;
    }

    public FrontendObject(FrontendObjectType typeOfContent, String title, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.imagePath = imagePath;
    }

    public FrontendObject(Zeigekarte zeigekarte) {
        this.typeOfContent = FrontendObjectType.IMAGE;
        this.title = zeigekarte.title;
        this.imagePath = zeigekarte.imagePath;
    }

    public FrontendObject(FrontendObjectType typeOfContent, DropdownOptions dropdownOptions, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.dropdownOptions = dropdownOptions;
        this.imagePath = imagePath;
    }

    public FrontendObject(FrontendObjectType typeOfContent, String title, List<String> displayedStrings, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.displayedStrings = displayedStrings;
        this.imagePath = imagePath;
    }

    public FrontendObject(FrontendObjectType typeOfContent, String title, String note, List<String> displayedStrings) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.note = note;
        this.displayedStrings = displayedStrings;
    }

    public FrontendObject(List<SchnüfflerInformation> informationen, String title) {
        this.typeOfContent = FrontendObjectType.SCHNÜFFLER_INFO;
        this.title = title;
        this.informationen = informationen;
    }

    public FrontendObject(String title, DropdownOptions dropdownOptions, List<String> displayedStrings) {
        this.typeOfContent = FrontendObjectType.DROPDOWN_SEPARATED_LIST;
        this.title = title;
        this.dropdownOptions = dropdownOptions;
        this.displayedStrings = displayedStrings;
    }

    public FrontendObject(String title, String imagePath1, String imagePath2, List<String> displayedStrings) {
        this.title = title;
        this.imagePath = imagePath1;
        this.imagePath2 = imagePath2;
        this.displayedStrings = displayedStrings;
        typeOfContent = FrontendObjectType.TWO_IMAGES;
    }
}
