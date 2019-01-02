package root.Controller.FrontendObject;

public class CardFrontendObject extends FrontendObject {
    public CardFrontendObject(String imagePath) {
        this.typeOfContent = FrontendObjectType.CARD;
        this.imagePath = imagePath;
    }
}
