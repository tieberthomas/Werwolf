package root.Frontend.Factories;

import root.Frontend.Frame.FrameMode;
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

public class SpielerPageFactory {
    SpielerFrame spielerFrame;
    public SpielerPageElementFactory pageElementFactory;

    public SpielerPageFactory(SpielerFrame frame) {
        spielerFrame = frame;
        pageElementFactory = new SpielerPageElementFactory(spielerFrame);
    }

    public Page generateStaticImagePage(String title, String imagePath) {
        return generateStaticImagePage(title, imagePath, false);
    }

    public Page generateStaticImagePage(String title, String imagePath, boolean fullCentered) {
        spielerFrame.mode = SpielerFrameMode.staticImage;

        return generateImagePage(title, imagePath, fullCentered);
    }

    private Page generateImagePage(String title, String imagePath, boolean fullCentered) {
        spielerFrame.title = title;
        PageElement titleLabel = pageElementFactory.generateTitleLabel(title);
        PageElement imageLabel;
        if (fullCentered) {
            imageLabel = pageElementFactory.generateFullCenteredImageLabel(imagePath);
        } else {
            imageLabel = pageElementFactory.generateHorizontallyCenteredImageLabel(imagePath, titleLabel);
        }

        Page imagePage = new Page(0, 10);

        imagePage.add(titleLabel);
        imagePage.add(imageLabel);

        return imagePage;
    }

    public Page generateEndScreenPage(String firstLine, String imagePath, String secondLine) {
        spielerFrame.title = "";
        spielerFrame.mode = SpielerFrameMode.staticImage;

        PageElement imageLabel = spielerFrame.pageElementFactory.generateFullCenteredImageLabel(imagePath);
        JLabel secondeLineJLabel = pageElementFactory.generateBigJLabel(secondLine);
        PageElement secondLineLabel = pageElementFactory.generateCenteredLabel(secondeLineJLabel, imageLabel, 20);
        JLabel firstLineJLabel = pageElementFactory.generateBigJLabel(firstLine);
        int ySpaceToPredecessor = (imageLabel.height + 20 + secondLineLabel.height) * (-1);
        PageElement firstLineLabel = pageElementFactory.generateCenteredLabel(firstLineJLabel, imageLabel, ySpaceToPredecessor);

        Page endScreenPage = new Page(0, 10);

        endScreenPage.add(firstLineLabel);
        endScreenPage.add(imageLabel);
        endScreenPage.add(secondLineLabel);

        return endScreenPage;
    }

    public Page generateEndScreenPage(Winner winner) {
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

    public Page generateDeadPage() {
        Tot tot = new Tot();
        return generateStaticImagePage(tot.title, tot.imagePath, true);
    }

    public Page generateDayPage(List<String> hauptrollen, List<String> bonusrollen, boolean freibier) {
        int titleSpace = 80;
        int clockSpace = 150;
        Page listPage = generateDoubleListPage(hauptrollen, bonusrollen, titleSpace, clockSpace - 30);

        PageElement title1Element = pageElementFactory.generateColumnTitleLabel("Hauptrollen", 2, 0, titleSpace);
        PageElement title2Element = pageElementFactory.generateColumnTitleLabel("Bonusrollen", 2, 1, titleSpace);

        spielerFrame.clockLabel = new JLabel();
        PageElement counterLabel = pageElementFactory.generateClockLabel(spielerFrame.clockLabel, clockSpace);

        listPage.add(title1Element);
        listPage.add(title2Element);
        listPage.add(counterLabel);

        if (freibier) { //TODO implementieren dass nurnoch eines angezeigt wird nach erstem kill (?)
            PageElement freibierWatermarkRight = pageElementFactory.generateBierImage(Corner.LOWERRIGHT, clockSpace);
            PageElement freibierWatermarkLeft = pageElementFactory.generateBierImage(Corner.LOWERLEFT, clockSpace);

            listPage.add(freibierWatermarkRight);
            listPage.add(freibierWatermarkLeft);
        }

        return listPage;
    }

    public Page generateDropdownPage(String title, int numberOfDropdowns) {
        spielerFrame.title = title;
        spielerFrame.mode = SpielerFrameMode.dropDownText;

        PageElement titleLabel = pageElementFactory.generateTitleLabel(title);

        Page dropdownPage = new Page(0, 10);

        dropdownPage.add(titleLabel);

        PageElement centeredLabel1;
        PageElement centeredLabel2;
        PageElement centeredLabel3;

        int frameOffset = MyFrame.yOffset;
        int titleHeight = titleLabel.height;
        int stringHeight = pageElementFactory.getJLabelStandardHeight();
        int spaceToUse = spielerFrame.frameJpanel.getHeight() - frameOffset - titleHeight - stringHeight;
        int spacePerString;
        int startpoint;

        switch (numberOfDropdowns) {
            case 1:
                spacePerString = spaceToUse;
                startpoint = titleHeight + (spacePerString / 2) - (stringHeight / 2);
                centeredLabel1 = pageElementFactory.generateCenteredLabel(spielerFrame.comboBox1Label, titleLabel, startpoint);
                dropdownPage.add(centeredLabel1);
                break;
            case 2:
                spacePerString = spaceToUse / 2;
                startpoint = titleHeight + (spacePerString / 2) - (stringHeight / 2);

                centeredLabel1 = pageElementFactory.generateCenteredLabel(spielerFrame.comboBox1Label, titleLabel, startpoint);
                dropdownPage.add(centeredLabel1);

                centeredLabel2 = pageElementFactory.generateCenteredLabel(spielerFrame.comboBox2Label, centeredLabel1, spacePerString);
                dropdownPage.add(centeredLabel2);
                break;
            case 3:
                spacePerString = spaceToUse / 3;
                startpoint = titleHeight + (spacePerString / 2) - (stringHeight / 2);

                centeredLabel1 = pageElementFactory.generateCenteredLabel(spielerFrame.comboBox1Label, titleLabel, startpoint);
                dropdownPage.add(centeredLabel1);

                centeredLabel2 = pageElementFactory.generateCenteredLabel(spielerFrame.comboBox2Label, centeredLabel1, spacePerString);
                dropdownPage.add(centeredLabel2);

                centeredLabel3 = pageElementFactory.generateCenteredLabel(spielerFrame.comboBox3Label, centeredLabel2, spacePerString);
                dropdownPage.add(centeredLabel3);
                break;
        }

        spielerFrame.comboBox1Label.setText("");
        spielerFrame.comboBox2Label.setText("");
        spielerFrame.comboBox3Label.setText("");

        return dropdownPage;
    }

    public Page generateMirrorImagePage(String title, String imagePath1, String imagePath2) {
        spielerFrame.title = title;
        spielerFrame.mode = SpielerFrameMode.dropDownImage;

        PageElement titleLabel = pageElementFactory.generateTitleLabel(title);
        PageElement imageLabel1 = spielerFrame.pageElementFactory.generateLeftCenteredImageLabel(imagePath1, titleLabel);
        PageElement imageLabel2 = spielerFrame.pageElementFactory.generateImageLabel(imagePath2, imageLabel1, titleLabel);


        Page imagePage = new Page(0, 10);

        imagePage.add(titleLabel);
        imagePage.add(imageLabel1);
        imagePage.add(imageLabel2);

        return imagePage;
    }

    public Page generateTwoImagePage(String title, String imagePath1, String imagePath2) {
        spielerFrame.title = title;

        PageElement titleLabel = pageElementFactory.generateTitleLabel(title);
        PageElement imageLabel1 = spielerFrame.pageElementFactory.generateLeftCenteredImageLabel(imagePath1, titleLabel);
        PageElement imageLabel2 = spielerFrame.pageElementFactory.generateImageLabel(imagePath2, imageLabel1, titleLabel);

        imageLabel1.setCoordX(imageLabel1.coordX - 10);
        imageLabel2.setCoordX(imageLabel2.coordX + 10);


        Page imagePage = new Page(0, 10);

        imagePage.add(titleLabel);
        imagePage.add(imageLabel1);
        imagePage.add(imageLabel2);

        return imagePage;
    }

    private Page generateListPage(String title, List<String> stringsToDisplay, int frameHeight) {
        spielerFrame.title = title;
        spielerFrame.mode = SpielerFrameMode.staticListPage;

        Page listPage = new Page(0, 10);

        PageElement titleLabel = pageElementFactory.generateTitleLabel(title);
        listPage.add(titleLabel);

        List<String> realStringsToDisplay = new ArrayList<>(stringsToDisplay);
        realStringsToDisplay.remove("");

        if (realStringsToDisplay.size() > 0) {
            int frameOffset = MyFrame.yOffset;
            int titleHeight = titleLabel.height;
            int stringHeight = pageElementFactory.getJLabelStandardHeight();
            int spaceToUse = frameHeight - frameOffset - titleHeight - stringHeight;
            int spacePerString = spaceToUse / realStringsToDisplay.size();
            int spacingBetweenStrings = spacePerString - stringHeight;
            int startpoint = titleHeight + ((spacePerString / 2) - (stringHeight / 2));

            PageElement label = pageElementFactory.generateCenteredLabel(new JLabel(realStringsToDisplay.get(0)), titleLabel, startpoint);
            listPage.add(label);

            int i = 0;
            for (String string : realStringsToDisplay) {
                if (i != 0) {
                    label = pageElementFactory.generateCenteredLabel(new JLabel(string), label, spacingBetweenStrings);
                    listPage.add(label);
                }

                i++;
            }
        }

        return listPage;
    }

    public Page generateListPageWithNote(String title, List<String> stringsToDisplay, String note) {
        int noteHeight = 120;
        int listSize = spielerFrame.frameJpanel.getHeight() - noteHeight;
        Page page = generateListPage(title, stringsToDisplay, listSize);

        PageElement noteLabel = pageElementFactory.generateNoteLabel(note, noteHeight, 50);

        page.add(noteLabel);

        return page;
    }

    public Page generateListMirrorPage(String title, List<String> stringsToDisplay) {
        Page listPage = generateListPage(title, stringsToDisplay);

        spielerFrame.mode = SpielerFrameMode.listMirrorPage;

        return listPage;
    }

    public Page generateTitlePage(String title) {
        spielerFrame.title = title;
        spielerFrame.mode = SpielerFrameMode.titlePage;

        Page titlePage = new Page(0, 10);

        PageElement titleLabel = pageElementFactory.generateTitleLabel(title);
        titlePage.add(titleLabel);

        return titlePage;
    }

    public Page generateTitlePage() {
        return generateTitlePage(spielerFrame.title);
    }

    public Page generateDoubleListPage(List<String> stringsToDisplay1, List<String> stringsToDisplay2, String title1, String title2) {
        int titleSpace = 80;
        stringsToDisplay1.sort(String.CASE_INSENSITIVE_ORDER);
        stringsToDisplay2.sort(String.CASE_INSENSITIVE_ORDER);
        Page listPage = generateDoubleListPage(stringsToDisplay1, stringsToDisplay2, titleSpace, 0);

        PageElement title1Element = pageElementFactory.generateColumnTitleLabel(title1, 2, 0, titleSpace);
        PageElement title2Element = pageElementFactory.generateColumnTitleLabel(title2, 2, 1, titleSpace);

        listPage.add(title1Element);
        listPage.add(title2Element);

        return listPage;
    }

    public Page generateDoubleListPage(List<String> stringsToDisplay1, List<String> stringsToDisplay2, int offsetAbove, int offsetBelow) {
        List<String> realStringsToDisplay1 = new ArrayList<>(stringsToDisplay1);
        realStringsToDisplay1.remove("");
        realStringsToDisplay1.sort(String.CASE_INSENSITIVE_ORDER);
        List<String> realStringsToDisplay2 = new ArrayList<>(stringsToDisplay2);
        realStringsToDisplay2.remove("");
        realStringsToDisplay2.sort(String.CASE_INSENSITIVE_ORDER);

        int columns = getNumberOfColumns(realStringsToDisplay1, realStringsToDisplay2);

        return generateListPage(realStringsToDisplay1, realStringsToDisplay2, columns, offsetAbove, offsetBelow);
    }

    private int getNumberOfColumns(List<String> realStringsToDisplay1, List<String> realStringsToDisplay2) {
        int maxLinesPerColumnBig = 12;
        int maxLinesPerColumnSmall = 10;
        int maxLinesPerColumn = maxLinesPerColumnBig;

        if (spielerFrame.frameMode == FrameMode.big) {
            maxLinesPerColumn = maxLinesPerColumnBig;
        } else if (spielerFrame.frameMode == FrameMode.small) {
            maxLinesPerColumn = maxLinesPerColumnSmall;
        } else {
            System.out.println("Unknown FrameSizemode");
        }

        if ((realStringsToDisplay1.size() > maxLinesPerColumn || realStringsToDisplay2.size() > maxLinesPerColumn)) {
            return 2;
        } else {
            return 1;
        }
    }

    public Page generateListPage(String title, List<String> stringsToDisplay) {
        stringsToDisplay.sort(String.CASE_INSENSITIVE_ORDER);
        List<String> realStringsToDisplay = new ArrayList<>(stringsToDisplay);
        realStringsToDisplay.remove("");

        PageElement titleLabel = pageElementFactory.generateTitleLabel(title);

        int titleHeight = titleLabel.height;
        int numberOfColumns = getNumberOfColumns(realStringsToDisplay.size());
        Page listPage = generateListPage(realStringsToDisplay, numberOfColumns, titleHeight);

        listPage.add(titleLabel);

        return listPage;
    }

    private int getNumberOfColumns(int numberOfStrings) {
        if (numberOfStrings < 8) {
            return 1;
        } else if (numberOfStrings < 16) {
            return 2;
        } else {
            return 3;
        }
    }

    public Page generateListPage(List<String> stringsToDisplay, int numberOfColumns, int titleHeight) {
        Page listPage = new Page(0, 10);
        stringsToDisplay.sort(String.CASE_INSENSITIVE_ORDER);
        float dividingPoint = ((float) stringsToDisplay.size()) / numberOfColumns;

        for (int i = 0; i < numberOfColumns; i++) {
            int start = Math.round(dividingPoint * i);
            int end = Math.round(dividingPoint * (i + 1));

            Page pageToAdd = generateListPage(new ArrayList<>(stringsToDisplay.subList(start, end)), numberOfColumns, i, titleHeight);

            for (PageElement element : pageToAdd.pageElements) {
                listPage.add(element);
            }
        }

        return listPage;
    }

    public Page generateListPage(List<String> stringsToDisplay, List<String> stringsToDisplay2, int numberOfColumnsPerList) {
        return generateListPage(stringsToDisplay, stringsToDisplay2, numberOfColumnsPerList, 0, 0);
    }

    public Page generateListPage(List<String> stringsToDisplay, List<String> stringsToDisplay2, int numberOfColumnsPerList, int offsetAbove, int offsetBelow) {
        Page listPage = new Page(5, 10);
        stringsToDisplay.sort(String.CASE_INSENSITIVE_ORDER);
        float dividingPoint1 = ((float) stringsToDisplay.size()) / numberOfColumnsPerList;
        float dividingPoint2 = ((float) stringsToDisplay2.size()) / numberOfColumnsPerList;
        int textSize = 36;

        if (numberOfColumnsPerList > 1) {
            if (spielerFrame.frameMode == FrameMode.big) {
                textSize = 30;
            } else {
                textSize = 25;
            }
        }

        for (int i = 0; i < numberOfColumnsPerList; i++) {
            int start1 = Math.round(dividingPoint1 * i);
            int end1 = Math.round(dividingPoint1 * (i + 1));

            int start2 = Math.round(dividingPoint2 * i);
            int end2 = Math.round(dividingPoint2 * (i + 1));

            Page pageToAdd = generateListPage(new ArrayList<>(stringsToDisplay.subList(start1, end1)), numberOfColumnsPerList * 2, i, offsetAbove, offsetBelow, textSize);
            Page pageToAdd2 = generateListPage(new ArrayList<>(stringsToDisplay2.subList(start2, end2)), numberOfColumnsPerList * 2, i + numberOfColumnsPerList, offsetAbove, offsetBelow, textSize);

            for (PageElement element : pageToAdd.pageElements) {
                listPage.add(element);
            }

            for (PageElement element : pageToAdd2.pageElements) {
                listPage.add(element);
            }
        }

        return listPage;
    }

    public Page generateListPage(List<String> stringsToDisplay, int numberOfColumns, int indexOfColumn, int offsetAbove) {
        return generateListPage(stringsToDisplay, numberOfColumns, indexOfColumn, offsetAbove, 0, SpielerPageElementFactory.defaultTextSize);
    }

    private Page generateListPage(List<String> stringsToDisplay, int numberOfColumns, int indexOfColumn, int offsetAbove,
                                  int offsetBelow, int textSize) {
        Page listPage = new Page(0, 10);
        List<String> realStringsToDisplay = new ArrayList<>(stringsToDisplay);
        realStringsToDisplay.remove("");
        realStringsToDisplay.sort(String.CASE_INSENSITIVE_ORDER);

        if (realStringsToDisplay.size() > 0) {
            int frameOffset = MyFrame.yOffset;
            int stringHeight = pageElementFactory.getJLabelHeight(textSize);
            int spaceToUse = spielerFrame.frameJpanel.getHeight() - frameOffset - offsetAbove - offsetBelow;
            int spacePerString = spaceToUse / realStringsToDisplay.size();
            int spacingBetweenStrings = spacePerString - stringHeight;
            int startpoint = ((spacePerString / 2) - (stringHeight / 2)) + offsetAbove;

            if (numberOfColumns <= 0) {
                numberOfColumns = 1;
            }

            PageElement label;
            label = pageElementFactory.generateColumnCenteredLabel(new JLabel(realStringsToDisplay.get(0)), null, startpoint, numberOfColumns, indexOfColumn, textSize);
            listPage.add(label);

            int i = 0;
            for (String string : realStringsToDisplay) {
                if (i != 0) {
                    label = pageElementFactory.generateColumnCenteredLabel(new JLabel(string), label, spacingBetweenStrings, numberOfColumns, indexOfColumn, textSize);
                    listPage.add(label);
                }

                i++;
            }
        }

        return listPage;
    }

    public List<PageElement> generateColumnElements(List<JComponent> elementsToDisplay, int numberOfColumns, int indexOfColumn, int offsetAbove, int offsetBelow) {
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

                label = pageElementFactory.generateColumnCenteredComponent(component, label, spaceToKeepFromLastBox, numberOfColumns, indexOfColumn);
                listPage.add(label);
            }
        }

        return listPage.pageElements;
    }

    public Page generateSchnüfflerInformationPage(List<RawInformation> informationen, int maxColumns) {
        Page listPage = new Page(0, 10);

        int columns = informationen.size();
        int indexOfColumn = 0;
        for (RawInformation information : informationen) {
            PageElement spielerTitle = pageElementFactory.generateColumnCenteredLabel(new JLabel(information.spieler), null, 0, columns, indexOfColumn);
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

    private List<PageElement> generateSchnüfflerInformationsColumn(RawInformation rawInformation, int maxColumns, int numberOfColumns, int indexOfColumn, int offsetAbove) {
        List<JComponent> elementsToDisplay = new ArrayList<>();
        if (rawInformation.isTarnumhang) {
            elementsToDisplay.add(pageElementFactory.generateIcon(new Tarnumhang_BonusrollenType().imagePath));
        } else {
            int frameOffset = MyFrame.yOffset;
            int spaceToUse = spielerFrame.frameJpanel.getHeight() - frameOffset - offsetAbove;
            int columnHeight = spaceToUse / rawInformation.imagePaths.size();
            int columnWidth = (int) ((double) spielerFrame.frameJpanel.getWidth()) / numberOfColumns;
            elementsToDisplay = pageElementFactory.generateFixedSizedImages(rawInformation, columnWidth, columnHeight);
        }

        return generateColumnElements(elementsToDisplay, numberOfColumns, indexOfColumn, offsetAbove, 0);
    }

    public Page generateDropdownMirrorImagePage(String title, String imagepath) {
        Page listPage = generateImagePage(title, imagepath, true);

        spielerFrame.mode = SpielerFrameMode.listMirrorPage;

        return listPage;
    }
}
