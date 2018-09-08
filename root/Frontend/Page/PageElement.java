package root.Frontend.Page;

import javax.swing.*;

public class PageElement extends Predecessor {
    public JComponent component;

    public PageElement(JComponent element, int width, int height, Predecessor predecessorX, Predecessor predecessorY, int space_to_predecessor_x, int space_to_predecessor_y) {
        super(width, height, predecessorX, predecessorY, space_to_predecessor_x, space_to_predecessor_y);
        this.component = element;
        element.setBounds(coordX, coordY, width, height);
    }

    public PageElement(JComponent element, int width, int height) {
        this(element, width, height, null, null, DEFAULT_SPACE, DEFAULT_SPACE);
    }

    public PageElement(JComponent element, int width, int height, Predecessor predecessorX) {
        this(element, width, height, predecessorX, null, DEFAULT_SPACE, DEFAULT_SPACE);
    }

    public PageElement(JComponent element, int width, int height, Predecessor predecessorX, Predecessor predecessorY) {
        this(element, width, height, predecessorX, predecessorY, DEFAULT_SPACE, DEFAULT_SPACE);
    }

    public PageElement(JComponent element, int width, int height, Predecessor predecessorX, int space_to_predecessor_x) {
        this(element, width, height, predecessorX, null, space_to_predecessor_x, DEFAULT_SPACE);
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
        pageCoordX = this.coordX + pageOffsetX;
        component.setBounds(pageCoordX, pageCoordY, width, height);
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
        pageCoordY = this.coordY + pageOffsetY;
        component.setBounds(pageCoordX, pageCoordY, width, height);
    }

    public void setCoords(int coordX, int coordY) {
        this.coordX = coordX;
        this.coordY = coordY;
        pageCoordX = this.coordX + pageOffsetX;
        pageCoordY = this.coordY + pageOffsetY;
        component.setBounds(pageCoordX, pageCoordY, width, height);
    }

    public void addYSpace(int space) {
        setCoordY(coordY + space);
    }
}
