package root.Controller.FrontendObject;

import root.Logic.Persona.Rollen.Constants.Zeigekarten.Zeigekarte;

public class ImageFrontendObject extends FrontendObject {
    public ImageFrontendObject(FrontendObjectType typeOfContent, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.imagePath = imagePath;
    }

    public ImageFrontendObject(FrontendObjectType typeOfContent, String title, String imagePath) {
        this.typeOfContent = typeOfContent;
        this.title = title;
        this.imagePath = imagePath;
    }

    public ImageFrontendObject(Zeigekarte zeigekarte) {
        this.typeOfContent = FrontendObjectType.IMAGE;
        this.title = zeigekarte.title;
        this.imagePath = zeigekarte.imagePath;
    }
}
