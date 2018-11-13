package root.Persona.Rollen.Constants;

import java.util.ArrayList;
import java.util.List;

public class RawInformation {
    public String spieler;
    public boolean isTarnumhang;
    public List<String> imagePaths = new ArrayList<>();

    public RawInformation(String spieler, boolean isTarnumhang, List<String> imagePaths) {
        this.spieler = spieler;
        this.isTarnumhang = isTarnumhang;
        this.imagePaths = imagePaths;
    }

    public static RawInformation convertToRawInformation(SchnüfflerInformation information) {
        List<String> imagePaths = new ArrayList<>();
        if (!information.isTarnumhang) {
            imagePaths.add(information.fraktion.imagePath);
            imagePaths.add(information.tötend.imagePath);
            imagePaths.add(information.bonusrollenType.imagePath);
        }

        return new RawInformation(information.spielerName, information.isTarnumhang, imagePaths);
    }
}
