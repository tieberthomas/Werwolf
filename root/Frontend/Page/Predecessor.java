package root.Frontend.Page;

public class Predecessor {
    public int coordX;
    public int coordY;
    public int pageCoordX;
    public int pageCoordY;
    public int pageOffsetX;
    public int pageOffsetY;
    public int width;
    public int height;
    public Predecessor predecessorX;
    public Predecessor predecessorY;
    public int space_to_predecessor_x;
    public int space_to_predecessor_y;

    public static int DEFAULT_SPACE = 0;

    public Predecessor(int width, int height, Predecessor predecessorX, Predecessor predecessorY, int space_to_predecessor_x, int space_to_predecessor_y) {
        this.width = width;
        this.height = height;
        pageOffsetX = 0;
        pageOffsetY = 0;
        this.predecessorX = predecessorX;
        this.predecessorY = predecessorY;
        this.space_to_predecessor_x = space_to_predecessor_x;
        this.space_to_predecessor_y = space_to_predecessor_y;

        if (predecessorX == null) {
            if (predecessorY == null) {
                coordY = space_to_predecessor_y;
                coordX = space_to_predecessor_x;
            } else {
                coordY = predecessorY.coordY + predecessorY.height + space_to_predecessor_y;
                coordX = predecessorY.coordX + space_to_predecessor_x;
            }
        } else {
            if (predecessorY == null) {
                coordY = predecessorX.coordY + space_to_predecessor_y;
                coordX = predecessorX.coordX + predecessorX.width + space_to_predecessor_x;
            } else {
                coordY = predecessorY.coordY + predecessorY.height + space_to_predecessor_y;
                coordX = predecessorX.coordX + predecessorX.width + space_to_predecessor_x;
            }
        }
    }

    public void setPageOffset(int pageOffsetX, int pageOffsetY) {
        this.pageOffsetX = pageOffsetX;
        this.pageOffsetY = pageOffsetY;
        setCoords(coordX, coordY);
    }

    public void setCoords(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
        pageCoordX = this.coordX + pageOffsetX;
        pageCoordY = this.coordY + pageOffsetY;
    }
}
