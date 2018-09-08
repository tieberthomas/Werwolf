package root.Frontend.Factories;

import root.Frontend.Frame.ÜbersichtsFrame;
import root.Frontend.Page.PageElement;
import root.Frontend.Page.PageTable;
import root.Frontend.Page.Predecessor;

import javax.swing.*;
import java.awt.*;

public class ÜbersichtsPageElementFactory {
    ÜbersichtsFrame übersichtsFrame;

    public ÜbersichtsPageElementFactory(ÜbersichtsFrame übersichtsFrame) {
        this.übersichtsFrame = übersichtsFrame;
    }

    public PageTable generatePageTable(Predecessor predecessorY, int columns, int tableElementWidth, int tableElementHeight,
                                       int tableElementsXDistance, int tableElementsYDistance, int spaceToPredecessorX, int spaceToPredecessorY) {

        PageTable labelTable = new PageTable(columns, tableElementWidth, tableElementHeight, null, predecessorY, spaceToPredecessorX, spaceToPredecessorY);
        labelTable.setTable_elements_x_distance(tableElementsXDistance);
        labelTable.setTable_elements_y_distance(tableElementsYDistance);

        return labelTable;
    }

    public PageTable generatePageTable(Predecessor predecessorX, Predecessor predecessorY, int columns, int tableElementWidth, int tableElementHeight,
                                       int tableElementsXDistance, int tableElementsYDistance, int spaceToPredecessorX, int spaceToPredecessorY) {

        PageTable labelTable = new PageTable(columns, tableElementWidth, tableElementHeight, predecessorX, predecessorY, spaceToPredecessorX, spaceToPredecessorY);
        labelTable.setTable_elements_x_distance(tableElementsXDistance);
        labelTable.setTable_elements_y_distance(tableElementsYDistance);

        return labelTable;
    }

    public PageElement generateLowestButton(JButton button, String title) {
        return generateLowestButton(button, title, true);
    }

    public PageElement generateLowestButton(JButton button, String title, boolean right) {
        return generateLowestButton(button, title, right, 0);
    }

    public PageElement generateLowestButton(JButton button, String title, boolean right, int howManyButtonsBefore) {
        int elementWidth = 120;
        int elementHeight = 50;
        int xSpace = 10;
        int xOffset = (elementWidth + xSpace) * howManyButtonsBefore;
        int spaceToPredecessorX = xOffset;
        int spaceToPredecessorY = übersichtsFrame.frameJpanel.getHeight() - elementHeight - 20;

        if (right) {
            spaceToPredecessorX = (übersichtsFrame.frameJpanel.getWidth() - elementWidth - 20) - xOffset;
        }

        PageElement goNextButton = new PageElement(button, elementWidth, elementHeight, null, null, spaceToPredecessorX, spaceToPredecessorY);

        button.setText(title);
        button.setMargin(new Insets(0, 0, 0, 0));
        button.addActionListener(übersichtsFrame);
        button.setBackground(übersichtsFrame.DEFAULT_BUTTON_COLOR);

        return goNextButton;
    }

    public PageElement generateLabel(Predecessor predecessorY, String string) {
        int labelWidth = 400;
        int labelHeight = 25;

        PageElement titleLabel = new PageElement(new JLabel(string), labelWidth, labelHeight, null, predecessorY);

        return titleLabel;
    }
}
