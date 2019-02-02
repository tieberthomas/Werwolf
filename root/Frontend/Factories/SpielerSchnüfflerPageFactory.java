package root.Frontend.Factories;

import root.Frontend.Frame.MyFrame;
import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageElement;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Logic.Persona.Rollen.Constants.RawInformation;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SpielerSchnüfflerPageFactory {
    public static SpielerFrame spielerFrame;

    public static Page generateSchnüfflerInformationPage(List<RawInformation> informationen) {
        Page listPage = new Page(0, 10);

        int columns = informationen.size();
        int indexOfColumn = 0;
        for (RawInformation information : informationen) {
            PageElement spielerTitle = SpielerPageElementFactory.generateColumnCenteredLabel(new JLabel(information.spieler), null, 0, columns, indexOfColumn);
            listPage.add(spielerTitle);
            int offsetAbove = spielerTitle.height;
            List<PageElement> columnToAdd = generateSchnüfflerInformationsColumn(information, columns, indexOfColumn, offsetAbove);

            for (PageElement element : columnToAdd) {
                listPage.add(element);
            }

            indexOfColumn++;
        }

        return listPage;
    }

    private static List<PageElement> generateSchnüfflerInformationsColumn(RawInformation rawInformation, int numberOfColumns, int indexOfColumn, int offsetAbove) {
        List<JComponent> elementsToDisplay = new ArrayList<>();
        if (rawInformation.isTarnumhang) {
            elementsToDisplay.add(SpielerPageElementFactory.generateIcon(new Tarnumhang_BonusrollenType().imagePath));
        } else {
            int frameOffset = MyFrame.yOffset;
            int spaceToUse = spielerFrame.frameJpanel.getHeight() - frameOffset - offsetAbove;
            int columnHeight = spaceToUse / rawInformation.imagePaths.size();
            int columnWidth = (int) ((double) spielerFrame.frameJpanel.getWidth()) / numberOfColumns;
            elementsToDisplay = SpielerPageElementFactory.generateFixedSizedImages(rawInformation, columnWidth, columnHeight);
        }

        return generateColumnElements(elementsToDisplay, numberOfColumns, indexOfColumn, offsetAbove, 0);
    }

    private static List<PageElement> generateColumnElements(List<JComponent> elementsToDisplay, int numberOfColumns, int indexOfColumn, int offsetAbove, int offsetBelow) {
        Page listPage = new Page(0, 10);

        if (elementsToDisplay.size() > 0) {
            if (numberOfColumns <= 0) {
                numberOfColumns = 1;
            }

            int frameOffset = MyFrame.yOffset;
            int spaceToUse = spielerFrame.frameJpanel.getHeight() - frameOffset - offsetAbove - offsetBelow;
            int spaceLeftLastBox = 0;

            boolean isFirstElement = true;

            PageElement label = null;

            for (JComponent component : elementsToDisplay) {
                int elementHeight = (int) component.getPreferredSize().getHeight();
                int spacePerElement = spaceToUse / elementsToDisplay.size();
                int spaceLeftInBox = spacePerElement - elementHeight;
                int spaceAboveAndBelow = (int) ((double) spaceLeftInBox) / 2;
                int spaceToKeepFromLastBox = spaceLeftLastBox + spaceAboveAndBelow;
                spaceLeftLastBox = spaceAboveAndBelow;

                if (isFirstElement) {
                    spaceToKeepFromLastBox += offsetAbove;
                    isFirstElement = false;
                }

                label = SpielerPageElementFactory.generateColumnCenteredComponent(component, label, spaceToKeepFromLastBox, numberOfColumns, indexOfColumn);
                listPage.add(label);
            }
        }

        return listPage.pageElements;
    }
}
