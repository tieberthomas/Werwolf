package root.Frontend.Factories;

import root.Frontend.Frame.MyFrame;
import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Frame.SpielerFrameMode;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageElement;
import root.Logic.Liebespaar;
import root.Logic.Persona.Fraktion;
import root.Logic.Persona.Rollen.Constants.Zeigekarten.Tot;
import root.Logic.Phases.Winner;

import javax.swing.*;
import java.util.List;

import static root.Utils.MoonImageUtil.getMoonImagePath;

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

    public static Page generateDayPage(List<String> hauptrollen, List<String> bonusrollen, int daysUntilFullMoon) {
        int titleSpace = 80;
        int clockSpace = 150;

        Page listPage = SpielerListPageFactory.generateDoubleListPage(hauptrollen, bonusrollen, titleSpace, clockSpace - 30);

        PageElement title1Element = SpielerPageElementFactory.generateColumnTitleLabel("Hauptrollen", 2, 0, titleSpace);
        listPage.add(title1Element);

        PageElement title2Element = SpielerPageElementFactory.generateColumnTitleLabel("Bonusrollen", 2, 1, titleSpace);
        listPage.add(title2Element);

        PageElement fullMoonImage = SpielerPageElementFactory.generateFullMoonImage(getMoonImagePath(daysUntilFullMoon), 100);
        listPage.add(fullMoonImage);

        spielerFrame.clockLabel = new JLabel();
        PageElement counterLabel = SpielerPageElementFactory.generateClockLabel(spielerFrame.clockLabel, clockSpace);
        listPage.add(counterLabel);

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

    public static Page generateDropdownMirrorImagePage(String title, String imagepath) {
        Page listPage = generateImagePage(title, imagepath, true);

        spielerFrame.mode = SpielerFrameMode.listMirrorPage;

        return listPage;
    }
}
