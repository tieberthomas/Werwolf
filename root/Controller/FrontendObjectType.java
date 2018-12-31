package root.Controller;

public enum FrontendObjectType {
    SKIP,
    TITLE,
    DROPDOWN,
    DROPDOWN_LIST,
    DROPDOWN_SEPARATED_LIST,
    DROPDOWN_IMAGE,
    LIST,
    LIST_WITH_NOTE,
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
