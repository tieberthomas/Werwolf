package root.Controller.FrontendObject;

import root.Logic.Persona.Rollen.Constants.SchnüfflerInformation;

import java.util.List;

public class SchnüfflerInfoFrontendObject extends FrontendObject {
    public SchnüfflerInfoFrontendObject(List<SchnüfflerInformation> informationen, String title) {
        this.typeOfContent = FrontendObjectType.SCHNÜFFLER_INFO;
        this.title = title;
        this.informationen = informationen;
    }
}
