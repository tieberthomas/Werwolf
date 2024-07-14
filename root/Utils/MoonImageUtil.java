package root.Utils;

import root.ResourceManagement.ImagePath;

public class MoonImageUtil {

    public static String getMoonImagePath(int daysUntilFullMoon) {
        switch (daysUntilFullMoon) {
            case 0:
                return ImagePath.FULL_MOON;
            case 1:
                return ImagePath.HALF_MOON;
            default:
                return ImagePath.NEW_MOON;
        }
    }

}
