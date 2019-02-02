package root.Frontend.Factories;

import root.Frontend.Frame.MyFrame;
import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Frame.SpielerFrameMode;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageElement;
import root.Logic.Liebespaar;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Rollen.Constants.BonusrollenType.Tarnumhang_BonusrollenType;
import root.Logic.Persona.Rollen.Constants.RawInformation;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Tot;
import root.Logic.Phases.Winner;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SpielerPageFactory {
    public static SpielerFrame spielerFrame;

    static int DEFAULT_BIG_TEXT_SIZE = 36;

    public static Page generateStaticImagePage(String title, String imagePath) {
        return generateStaticImagePage(title, imagePath, false);
    }

    public static Page generateStaticImagePage(String title, String imagePath, boolean fullCentered) {
        spielerFrame.mode = SpielerFrameMode.staticImage;

        return generateImagePage(title, imagePath, fullCentered);
    }

    private static Page generateImagePage(String title, String imagePath, boolean fullCentered) {
        spielerFrame.title = title;
        PageElement titleLabel = SpielerPageElementFactory.generateTitleLabel(title);
        PageElement imageLabel;
        if (fullCentered) {
            imageLabel = SpielerPageElementFactory.generateFullCenteredImageLabel(imagePath);
        } else {
            imageLabel = SpielerPageElementFactory.generateHorizontallyCenteredImageLabel(imagePath, titleLabel);
        }

        Page imagePage = new Page(0, 10);

        imagePage.add(titleLabel);
        imagePage.add(imageLabel);

        return imagePage;
    }

    public static Page generateEndScreenPage(String firstLine, String imagePath, String secondLine) {
        spielerFrame.title = "";
        spielerFrame.mode = SpielerFrameMode.staticImage;

        PageElement imageLabel = SpielerPageElementFactory.generateFullCenteredImageLabel(imagePath);
        JLabel secondeLineJLabel = SpielerPageElementFactory.generateBigJLabel(secondLine);
        PageElement secondLineLabel = SpielerPageElementFactory.generateCenteredLabel(secondeLineJLabel, imageLabel, 20);
        JLabel firstLineJLabel = SpielerPageElementFactory.generateBigJLabel(firstLine);
        int ySpaceToPredecessor = (imageLabel.height + 20 + secondLineLabel.height) * (-1);
        PageElement firstLineLabel = SpielerPageElementFactory.generateCenteredLabel(firstLineJLabel, imageLabel, ySpaceToPredecessor);

        Page endScreenPage = new Page(0, 10);

        endScreenPage.add(firstLineLabel);
        endScreenPage.add(imageLabel);
        endScreenPage.add(secondLineLabel);

        return endScreenPage;
    }

    public static Page generateEndScreenPage(Winner winner) {
        Page endScreenPage = null;

        switch (winner) {
            case ALL_DEAD:
                endScreenPage = generateDeadPage();
                break;

            case LIEBESPAAR:
                endScreenPage = generateEndScreenPage("Liebespaar", Liebespaar.IMAGE_PATH, "gewinnt!");
                break;

            case FRAKTION:
                Fraktion fraktion = winner.fraktion;
                endScreenPage = generateEndScreenPage(fraktion.name, fraktion.imagePath, "gewinnen!");
                break;
        }

        return endScreenPage;
    }

    public static Page generateDeadPage() {
        Tot tot = new Tot();
        return generateStaticImagePage(tot.title, tot.imagePath, true);
    }

    public static Page generateDayPage(List<String> hauptrollen, List<String> bonusrollen, boolean freibier) {
        int titleSpace = 80;
        int clockSpace = 150;
        Page listPage = SpielerListPageFactory.generateDoubleListPage(hauptrollen, bonusrollen, titleSpace, clockSpace - 30);

        PageElement title1Element = SpielerPageElementFactory.generateColumnTitleLabel("Hauptrollen", 2, 0, titleSpace);
        PageElement title2Element = SpielerPageElementFactory.generateColumnTitleLabel("Bonusrollen", 2, 1, titleSpace);

        spielerFrame.clockLabel = new JLabel();
        PageElement counterLabel = SpielerPageElementFactory.generateClockLabel(spielerFrame.clockLabel, clockSpace);

        listPage.add(title1Element);
        listPage.add(title2Element);
        listPage.add(counterLabel);

        if (freibier) { //TODO implementieren dass nurnoch eines angezeigt wird nach erstem kill (?)
            PageElement freibierWatermarkRight = SpielerPageElementFactory.generateBierImage(Corner.LOWERRIGHT, clockSpace);
            PageElement freibierWatermarkLeft = SpielerPageElementFactory.generateBierImage(Corner.LOWERLEFT, clockSpace);

            listPage.add(freibierWatermarkRight);
            listPage.add(freibierWatermarkLeft);
        }

        return listPage;
    }

    public static Page generateDropdownPage(String title, int numberOfDropdowns) {
        spielerFrame.title = title;
        spielerFrame.mode = SpielerFrameMode.dropDownText;

        PageElement titleLabel = SpielerPageElementFactory.generateTitleLabel(title);

        Page dropdownPage = new Page(0, 10);

        dropdownPage.add(titleLabel);

        PageElement centeredLabel1;
        PageElement centeredLabel2;
        PageElement centeredLabel3;

        int frameOffset = MyFrame.yOffset;
        int titleHeight = titleLabel.height;
        int stringHeight = SpielerPageElementFactory.getJLabelStandardHeight();
        int spaceToUse = spielerFrame.frameJpanel.getHeight() - frameOffset - titleHeight - stringHeight;
        int spacePerString;
        int startpoint;

        switch (numberOfDropdowns) {
            case 1:
                spacePerString = spaceToUse;
                startpoint = titleHeight + (spacePerString / 2) - (stringHeight / 2);
                centeredLabel1 = SpielerPageElementFactory.generateCenteredLabel(spielerFrame.comboBox1Label, titleLabel, startpoint);
                dropdownPage.add(centeredLabel1);
                break;
            case 2:
                spacePerString = spaceToUse / 2;
                startpoint = titleHeight + (spacePerString / 2) - (stringHeight / 2);

                centeredLabel1 = SpielerPageElementFactory.generateCenteredLabel(spielerFrame.comboBox1Label, titleLabel, startpoint);
                dropdownPage.add(centeredLabel1);

                centeredLabel2 = SpielerPageElementFactory.generateCenteredLabel(spielerFrame.comboBox2Label, centeredLabel1, spacePerString);
                dropdownPage.add(centeredLabel2);
                break;
            case 3:
                spacePerString = spaceToUse / 3;
                startpoint = titleHeight + (spacePerString / 2) - (stringHeight / 2);

                centeredLabel1 = SpielerPageElementFactory.generateCenteredLabel(spielerFrame.comboBox1Label, titleLabel, startpoint);
                dropdownPage.add(centeredLabel1);

                centeredLabel2 = SpielerPageElementFactory.generateCenteredLabel(spielerFrame.comboBox2Label, centeredLabel1, spacePerString);
                dropdownPage.add(centeredLabel2);

                centeredLabel3 = SpielerPageElementFactory.generateCenteredLabel(spielerFrame.comboBox3Label, centeredLabel2, spacePerString);
                dropdownPage.add(centeredLabel3);
                break;
        }

        spielerFrame.comboBox1Label.setText("");
        spielerFrame.comboBox2Label.setText("");
        spielerFrame.comboBox3Label.setText("");

        return dropdownPage;
    }

    public static Page generateMirrorImagePage(String title, String imagePath1, String imagePath2) {
        spielerFrame.title = title;
        spielerFrame.mode = SpielerFrameMode.dropDownImage;

        PageElement titleLabel = SpielerPageElementFactory.generateTitleLabel(title);
        PageElement imageLabel1 = SpielerPageElementFactory.generateLeftCenteredImageLabel(imagePath1, titleLabel);
        PageElement imageLabel2 = SpielerPageElementFactory.generateImageLabel(imagePath2, imageLabel1, titleLabel);


        Page imagePage = new Page(0, 10);

        imagePage.add(titleLabel);
        imagePage.add(imageLabel1);
        imagePage.add(imageLabel2);

        return imagePage;
    }

    public static Page generateTwoImagePage(String title, String imagePath1, String imagePath2) {
        spielerFrame.title = title;

        PageElement titleLabel = SpielerPageElementFactory.generateTitleLabel(title);
        PageElement imageLabel1 = SpielerPageElementFactory.generateLeftCenteredImageLabel(imagePath1, titleLabel);
        PageElement imageLabel2 = SpielerPageElementFactory.generateImageLabel(imagePath2, imageLabel1, titleLabel);

        imageLabel1.setCoordX(imageLabel1.coordX - 10);
        imageLabel2.setCoordX(imageLabel2.coordX + 10);


        Page imagePage = new Page(0, 10);

        imagePage.add(titleLabel);
        imagePage.add(imageLabel1);
        imagePage.add(imageLabel2);

        return imagePage;
    }

    public static Page generateListMirrorPage(String title, List<String> stringsToDisplay) {
        Page listPage = SpielerListPageFactory.generateListPage(title, stringsToDisplay);

        spielerFrame.mode = SpielerFrameMode.listMirrorPage;

        return listPage;
    }

    public static Page generateTitlePage(String title) {
        spielerFrame.title = title;
        spielerFrame.mode = SpielerFrameMode.titlePage;

        Page titlePage = new Page(0, 10);

        PageElement titleLabel = SpielerPageElementFactory.generateTitleLabel(title);
        titlePage.add(titleLabel);

        return titlePage;
    }

    public static Page generateTitlePage() {
        return generateTitlePage(spielerFrame.title);
    }


    public static Page generateSchnüfflerInformationPage(List<RawInformation> informationen, int maxColumns) {
        Page listPage = new Page(0, 10);

        int columns = informationen.size();
        int indexOfColumn = 0;
        for (RawInformation information : informationen) {
            PageElement spielerTitle = SpielerPageElementFactory.generateColumnCenteredLabel(new JLabel(information.spieler), null, 0, columns, indexOfColumn);
            listPage.add(spielerTitle);
            int offsetAbove = spielerTitle.height;
            List<PageElement> columnToAdd = generateSchnüfflerInformationsColumn(information, maxColumns, columns, indexOfColumn, offsetAbove);

            for (PageElement element : columnToAdd) {
                listPage.add(element);
            }

            indexOfColumn++;
        }


        return listPage;
    }

    private static List<PageElement> generateSchnüfflerInformationsColumn(RawInformation rawInformation, int maxColumns, int numberOfColumns, int indexOfColumn, int offsetAbove) {
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

    public static Page generateDropdownMirrorImagePage(String title, String imagepath) {
        Page listPage = generateImagePage(title, imagepath, true);

        spielerFrame.mode = SpielerFrameMode.listMirrorPage;

        return listPage;
    }
}
