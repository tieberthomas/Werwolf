package root.Frontend.Constants;

public enum FrontendControlType {
    SKIP,
    TITLE,
    DROPDOWN,
    DROPDOWN_LIST,
    DROPDOWN_SEPARATED_LIST,
    DROPDOWN_IMAGE,
    LIST,
    IMAGE,
    CARD,
    LIST_IMAGE,
    SCHNÜFFLER_INFO,
    IRRLICHT_DROPDOWN,
    TWO_IMAGES;

    public boolean isDropdown() {
        return this.equals(DROPDOWN) || this.equals(DROPDOWN_LIST) || this.equals(DROPDOWN_SEPARATED_LIST)
                || this.equals(DROPDOWN_IMAGE) || this.equals(IRRLICHT_DROPDOWN);
    }
}
