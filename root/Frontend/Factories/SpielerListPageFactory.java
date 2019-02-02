package root.Frontend.Factories;

import root.Frontend.Frame.FrameMode;
import root.Frontend.Frame.MyFrame;
import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageElement;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public abstract class SpielerListPageFactory {
    public static SpielerFrame spielerFrame;

    public static Page generateListPageWithNote(String title, List<String> stringsToDisplay, String note) {
        int noteHeight = 120;

        PageElement titleLabel = SpielerPageElementFactory.generateTitleLabel(title);
        Page page = generateListPage(stringsToDisplay, 1, 0, titleLabel.height,
                noteHeight, SpielerPageFactory.DEFAULT_BIG_TEXT_SIZE, false);
        PageElement noteLabel = SpielerPageElementFactory.generateNoteLabel(note, noteHeight, 50);

        page.add(titleLabel);
        page.add(noteLabel);

        return page;
    }

    public static Page generateListPage(String title, List<String> stringsToDisplay) {
        stringsToDisplay.sort(String.CASE_INSENSITIVE_ORDER);
        List<String> realStringsToDisplay = new ArrayList<>(stringsToDisplay);
        realStringsToDisplay.remove("");

        PageElement titleLabel = SpielerPageElementFactory.generateTitleLabel(title);

        int titleHeight = titleLabel.height;
        int numberOfColumns = getNumberOfColumns(realStringsToDisplay.size());
        Page listPage = generateListPage(realStringsToDisplay, numberOfColumns, titleHeight);

        listPage.add(titleLabel);

        return listPage;
    }

    private static int getNumberOfColumns(int numberOfStrings) {
        if (numberOfStrings < 8) {
            return 1;
        } else if (numberOfStrings < 16) {
            return 2;
        } else {
            return 3;
        }
    }

    private static Page generateListPage(List<String> stringsToDisplay, int numberOfColumns, int titleHeight) {
        Page listPage = new Page(0, 10);
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

    private static Page generateListPage(List<String> stringsToDisplay, int numberOfColumns, int indexOfColumn, int offsetAbove) {
        return generateListPage(stringsToDisplay, numberOfColumns, indexOfColumn, offsetAbove, 0, SpielerPageElementFactory.defaultTextSize, false);
    }

    private static Page generateListPage(List<String> stringsToDisplay, int numberOfColumns, int indexOfColumn, int offsetAbove,
                                         int offsetBelow, int textSize, boolean mainRoleMarkup) {
        Page listPage = new Page(0, 10);
        List<String> realStringsToDisplay = new ArrayList<>(stringsToDisplay);
        realStringsToDisplay.remove("");

        if (realStringsToDisplay.size() > 0) {
            int frameOffset = MyFrame.yOffset;
            int stringHeight = SpielerPageElementFactory.getJLabelHeight(textSize);
            int spaceToUse = spielerFrame.frameJpanel.getHeight() - frameOffset - offsetAbove - offsetBelow;
            int spacePerString = spaceToUse / realStringsToDisplay.size();
            int spacingBetweenStrings = spacePerString - stringHeight;
            int startpoint = ((spacePerString / 2) - (stringHeight / 2)) + offsetAbove;

            if (numberOfColumns <= 0) {
                numberOfColumns = 1;
            }

            PageElement label;
            JLabel jLabel = new JLabel(realStringsToDisplay.get(0));
            if (mainRoleMarkup) {
                SpielerPageElementFactory.mainroleMarkup(jLabel);
            }
            label = SpielerPageElementFactory.generateColumnCenteredLabel(jLabel, null, startpoint, numberOfColumns, indexOfColumn, textSize);
            listPage.add(label);

            int i = 0;
            for (String string : realStringsToDisplay) {
                if (i != 0) {
                    jLabel = new JLabel(string);
                    if (mainRoleMarkup) {
                        SpielerPageElementFactory.mainroleMarkup(jLabel);
                    }
                    label = SpielerPageElementFactory.generateColumnCenteredLabel(jLabel, label, spacingBetweenStrings, numberOfColumns, indexOfColumn, textSize);
                    listPage.add(label);
                }

                i++;
            }
        }

        return listPage;
    }

    public static Page generateDoubleListPage(List<String> stringsToDisplay1, List<String> stringsToDisplay2, String title1, String title2) {
        int titleSpace = 80;
        Page listPage = generateDoubleListPage(stringsToDisplay1, stringsToDisplay2, titleSpace, 0);

        PageElement title1Element = SpielerPageElementFactory.generateColumnTitleLabel(title1, 2, 0, titleSpace);
        PageElement title2Element = SpielerPageElementFactory.generateColumnTitleLabel(title2, 2, 1, titleSpace);

        listPage.add(title1Element);
        listPage.add(title2Element);

        return listPage;
    }

    static Page generateDoubleListPage(List<String> stringsToDisplay1, List<String> stringsToDisplay2, int offsetAbove, int offsetBelow) {
        List<String> realStringsToDisplay1 = new ArrayList<>(stringsToDisplay1);
        realStringsToDisplay1.remove("");
        List<String> realStringsToDisplay2 = new ArrayList<>(stringsToDisplay2);
        realStringsToDisplay2.remove("");

        int columns = getNumberOfColumns(realStringsToDisplay1, realStringsToDisplay2);

        return generateListPage(realStringsToDisplay1, realStringsToDisplay2, columns, offsetAbove, offsetBelow);
    }

    private static int getNumberOfColumns(List<String> realStringsToDisplay1, List<String> realStringsToDisplay2) {
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

    private static Page generateListPage(List<String> stringsToDisplay, List<String> stringsToDisplay2, int numberOfColumnsPerList, int offsetAbove, int offsetBelow) {
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

            Page pageToAdd = generateListPage(new ArrayList<>(stringsToDisplay.subList(start1, end1)), numberOfColumnsPerList * 2, i, offsetAbove, offsetBelow, textSize, true);
            Page pageToAdd2 = generateListPage(new ArrayList<>(stringsToDisplay2.subList(start2, end2)), numberOfColumnsPerList * 2, i + numberOfColumnsPerList, offsetAbove, offsetBelow, textSize, true);

            for (PageElement element : pageToAdd.pageElements) {
                listPage.add(element);
            }

            for (PageElement element : pageToAdd2.pageElements) {
                listPage.add(element);
            }
        }

        return listPage;
    }
}
