package root.Frontend.Factories;

import root.Frontend.Frame.FrameMode;
import root.Frontend.Frame.MyFrame;
import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Frame.SpielerFrameMode;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageElement;
import root.Persona.Fraktion;
import root.Persona.Rollen.Constants.NebenrollenType.Tarnumhang_NebenrollenType;
import root.Persona.Rollen.Constants.RawInformation;
import root.mechanics.Liebespaar;

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
        spielerFrame.title = title;
        spielerFrame.mode = SpielerFrameMode.staticImage;

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

    public Page generateEndScreenPage(String string) {
        Page endScreenPage;
        if (string == null) {
            endScreenPage = spielerFrame.deadPage;
        } else if (string == "Liebespaar") {
            endScreenPage = generateEndScreenPage("Liebespaar", Liebespaar.getImagePath(), "gewinnt!");
        } else {
            Fraktion fraktion = Fraktion.findFraktion(string);
            endScreenPage = generateEndScreenPage(fraktion.getName(), fraktion.getImagePath(), "gewinnen!");
        }

        return endScreenPage;
    }

    public Page generateDayPage(ArrayList<String> hauptrollen, ArrayList<String> nebenrollen) {
        int titleSpace = 80;
        int clockSpace = 150;
        Page listPage = generateDoubleListPage(hauptrollen, nebenrollen, titleSpace, clockSpace - 30);

        PageElement title1Element = pageElementFactory.generateColumnTitleLabel("Hauptrollen", 2, 0, titleSpace);
        PageElement title2Element = pageElementFactory.generateColumnTitleLabel("Nebenrollen", 2, 1, titleSpace);

        spielerFrame.clockLabel = new JLabel("00:00:00");
        PageElement counterLabel = pageElementFactory.generateClockLabel(spielerFrame.clockLabel, clockSpace);

        listPage.add(title1Element);
        listPage.add(title2Element);
        listPage.add(counterLabel);

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
        int stringHeight = pageElementFactory.getJLabelHeight();
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

    public Page generateListPage(String title, ArrayList<String> stringsToDisplay) {
        spielerFrame.title = title;
        spielerFrame.mode = SpielerFrameMode.staticListPage;

        Page listPage = new Page(0, 10);

        PageElement titleLabel = pageElementFactory.generateTitleLabel(title);
        listPage.add(titleLabel);

        ArrayList<String> realStringsToDisplay = new ArrayList<String>(stringsToDisplay);
        realStringsToDisplay.remove("");

        if (realStringsToDisplay.size() > 0) {
            int frameOffset = MyFrame.yOffset;
            int titleHeight = titleLabel.height;
            int stringHeight = pageElementFactory.getJLabelHeight();
            int spaceToUse = spielerFrame.frameJpanel.getHeight() - frameOffset - titleHeight - stringHeight;
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

    public Page generateListMirrorPage(String title, ArrayList<String> stringsToDisplay) {
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

    public Page generateDoubleListPage(ArrayList<String> stringsToDisplay1, ArrayList<String> stringsToDisplay2, String title1, String title2) {
        int titleSpace = 80;
        Page listPage = generateDoubleListPage(stringsToDisplay1, stringsToDisplay2, titleSpace, 0);

        PageElement title1Element = pageElementFactory.generateColumnTitleLabel(title1, 2, 0, titleSpace);
        PageElement title2Element = pageElementFactory.generateColumnTitleLabel(title2, 2, 1, titleSpace);

        listPage.add(title1Element);
        listPage.add(title2Element);

        return listPage;
    }

    public Page generateDoubleListPage(ArrayList<String> stringsToDisplay1, ArrayList<String> stringsToDisplay2, int offsetAbove, int offsetBelow) {
        ArrayList<String> realStringsToDisplay1 = new ArrayList<String>(stringsToDisplay1);
        realStringsToDisplay1.remove("");
        ArrayList<String> realStringsToDisplay2 = new ArrayList<String>(stringsToDisplay2);
        realStringsToDisplay2.remove("");

        int maxLinesPerCollumnBig = 12;
        int maxLinesPerCollumnSmall = 10;

        if ((spielerFrame.frameMode == FrameMode.big && (realStringsToDisplay1.size() > maxLinesPerCollumnBig || realStringsToDisplay2.size() > maxLinesPerCollumnBig)) ||
                (spielerFrame.frameMode == FrameMode.small && (realStringsToDisplay1.size() > maxLinesPerCollumnSmall || realStringsToDisplay2.size() > maxLinesPerCollumnSmall))) {
            return generateListPage(realStringsToDisplay1, realStringsToDisplay2, 2, offsetAbove, offsetBelow);
        } else {
            return generateListPage(realStringsToDisplay1, realStringsToDisplay2, 1, offsetAbove, offsetBelow);
        }
    }

    public Page generateListPage(ArrayList<String> stringsToDisplay) {
        ArrayList<String> realStringsToDisplay = new ArrayList<String>(stringsToDisplay);
        realStringsToDisplay.remove("");

        if (realStringsToDisplay.size() < 8) {
            return generateListPage(realStringsToDisplay, 1);
        } else if (stringsToDisplay.size() < 16) {
            return generateListPage(realStringsToDisplay, 2);
        } else {
            return generateListPage(realStringsToDisplay, 3);
        }
    }

    public Page generateListPage(ArrayList<String> stringsToDisplay, int numberOfColumns) {
        Page listPage = new Page(0, 10);
        float dividingPoint = ((float) stringsToDisplay.size()) / numberOfColumns;

        for (int i = 0; i < numberOfColumns; i++) {
            int start = Math.round(dividingPoint * i);
            int end = Math.round(dividingPoint * (i + 1));

            Page pageToAdd = generateListPage(new ArrayList<>(stringsToDisplay.subList(start, end)), numberOfColumns, i);

            for (PageElement element : pageToAdd.pageElements) {
                listPage.add(element);
            }
        }

        return listPage;
    }

    public Page generateListPage(ArrayList<String> stringsToDisplay, ArrayList<String> stringsToDisplay2, int numberOfColumnsPerList) {
        return generateListPage(stringsToDisplay, stringsToDisplay2, numberOfColumnsPerList, 0, 0);
    }

    public Page generateListPage(ArrayList<String> stringsToDisplay, ArrayList<String> stringsToDisplay2, int numberOfColumnsPerList, int offsetAbove, int offsetBelow) {
        Page listPage = new Page(5, 10);
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

    public Page generateListPage(ArrayList<String> stringsToDisplay, int numberOfColumns, int indexOfColumn) {
        return generateListPage(stringsToDisplay, numberOfColumns, indexOfColumn, 0, 0, pageElementFactory.defaultTextSize);
    }

    public Page generateListPage(ArrayList<String> stringsToDisplay, int numberOfColumns, int indexOfColumn, int textSize) {
        return generateListPage(stringsToDisplay, numberOfColumns, indexOfColumn, 0, 0, textSize);
    }

    public Page generateListPage(ArrayList<String> stringsToDisplay, int numberOfColumns, int indexOfColumn, int offsetAbove, int offsetBelow, int textSize) {
        Page listPage = new Page(0, 10);
        ArrayList<String> realStringsToDisplay = new ArrayList<String>(stringsToDisplay);
        realStringsToDisplay.remove("");

        if (realStringsToDisplay.size() > 0) {
            int frameOffset = 38;
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

    public ArrayList<PageElement> generateColumnElements(ArrayList<JComponent> elementsToDisplay, int numberOfColumns, int indexOfColumn, int offsetAbove, int offsetBelow) {
        Page listPage = new Page(0, 10);

        if (elementsToDisplay.size() > 0) {
            int frameOffset = 38;
            int elementHeight = (int)elementsToDisplay.get(0).getPreferredSize().getHeight();
            int spaceToUse = spielerFrame.frameJpanel.getHeight() - frameOffset - offsetAbove - offsetBelow;
            int spacePerElement = spaceToUse / elementsToDisplay.size();
            int spacingBetweenElements = spacePerElement - elementHeight;
            int startpoint = ((spacePerElement / 2) - (elementHeight / 2)) + offsetAbove;

            if (numberOfColumns <= 0) {
                numberOfColumns = 1;
            }

            PageElement label;
            label = pageElementFactory.generateColumnCenteredComponent(elementsToDisplay.get(0), null, startpoint, numberOfColumns, indexOfColumn);
            listPage.add(label);

            int i = 0;
            for (JComponent component : elementsToDisplay) {
                if (i != 0) {
                    label = pageElementFactory.generateColumnCenteredComponent(component, label, spacingBetweenElements, numberOfColumns, indexOfColumn);
                    listPage.add(label);
                }

                i++;
            }
        }

        return listPage.pageElements;
    }

    public Page generateSchnüfflerInformationPage(List<RawInformation> informationen) {
        Page listPage = new Page(0, 10);

        int columns = informationen.size();
        int indexOfColumn = 0;
        for(RawInformation information : informationen) {
            PageElement spielerTitle = pageElementFactory.generateColumnCenteredLabel(new JLabel(information.spieler), null, 0, columns, indexOfColumn);
            listPage.add(spielerTitle);
            int offsetAbove = spielerTitle.height;
            ArrayList<PageElement> columnToAdd = generateSchnüfflerInformationsColumn(information, columns, indexOfColumn, offsetAbove);

            for (PageElement element : columnToAdd) {
                listPage.add(element);
            }

            indexOfColumn++;
        }


        return listPage;
    }

    private ArrayList<PageElement> generateSchnüfflerInformationsColumn(RawInformation rawInformation, int numberOfColumns, int indexOfColumn, int offsetAbove) {
        ArrayList<JComponent> elementsToDisplay = new ArrayList<>();
        if(rawInformation.isTarnumhang) {
            elementsToDisplay.add(pageElementFactory.generateIcon(new Tarnumhang_NebenrollenType().imagePath));
        } else {
            elementsToDisplay = pageElementFactory.generateImages(rawInformation);
        }

        return generateColumnElements(elementsToDisplay, numberOfColumns, indexOfColumn, offsetAbove, 0);
    }
}
