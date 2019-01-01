package root.Controller.FrontendObject;

import java.util.List;

public class TwoImageFrontendObject extends FrontendObject {
    public TwoImageFrontendObject(String title, String imagePath1, String imagePath2, List<String> displayedStrings) {
        this.typeOfContent = FrontendObjectType.TWO_IMAGES;
        this.title = title;
        this.imagePath = imagePath1;
        this.imagePath2 = imagePath2;
        this.displayedStrings = displayedStrings;
    }
}
