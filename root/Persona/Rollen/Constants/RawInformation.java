package root.Persona.Rollen.Constants;

import java.util.ArrayList;

public class RawInformation {
    public String spieler;
    public boolean isTarnumhang;
    public ArrayList<String> imagePaths = new ArrayList<>();

    public RawInformation(String spieler, boolean isTarnumhang, ArrayList<String> imagePaths) {
        this.spieler = spieler;
        this.isTarnumhang = isTarnumhang;
        this.imagePaths = imagePaths;
    }

    public static RawInformation convertToRawInformation(SchnüfflerInformation information) {
        ArrayList<String> imagePaths = new ArrayList<>();
        if (!information.isTarnumhang) {
            imagePaths.add(information.fraktion.getImagePath());
            imagePaths.add(information.tötend.imagePath);
            imagePaths.add(information.nebenrollenType.imagePath);
        }

        return new RawInformation(information.spielerName, information.isTarnumhang, imagePaths);
    }
}
