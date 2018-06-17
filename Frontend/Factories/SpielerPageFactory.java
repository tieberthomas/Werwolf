package root.Frontend.Factories;

import root.Frontend.Frame.SpielerFrameMode;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageElement;
import root.Frontend.Frame.SpielerFrame;
import root.ResourceManagement.ResourcePath;
import root.Rollen.Fraktion;
import root.Rollen.Fraktionen.Werwölfe;
import root.mechanics.Liebespaar;

import javax.swing.*;
import java.util.ArrayList;

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
        if(fullCentered) {
            imageLabel = spielerFrame.pageElementFactory.generateFullCenteredImageLabel(imagePath);
        } else {
            imageLabel = spielerFrame.pageElementFactory.generateHorizontallyCenteredImageLabel(imagePath, titleLabel);
        }

        Page imagePage = new Page(0,10);

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
        int ySpaceToPredecessor = (imageLabel.height+20+secondLineLabel.height)*(-1);
        PageElement firstLineLabel = pageElementFactory.generateCenteredLabel(firstLineJLabel, imageLabel, ySpaceToPredecessor);

        Page endScreenPage = new Page(0,10);

        endScreenPage.add(firstLineLabel);
        endScreenPage.add(imageLabel);
        endScreenPage.add(secondLineLabel);

        return endScreenPage;
    }

    public Page generateEndScreenPage(String string) {
        Page endScreenPage;
        if(string == null) {
            endScreenPage = spielerFrame.deadPage;
        } else if(string == "Liebespaar") {
            endScreenPage = generateEndScreenPage("Liebespaar", Liebespaar.getImagePath(), "gewinnt!");
        } else {
            Fraktion fraktion = Fraktion.findFraktion(string);
            endScreenPage = generateEndScreenPage(fraktion.getName(), fraktion.getImagePath(), "gewinnen!");
        }

        return endScreenPage;
    }

    public Page generateDropdownPage(String title, int numberOfDropdowns) {
        spielerFrame.title = title;
        spielerFrame.mode = SpielerFrameMode.dropDownText;

        PageElement titleLabel = pageElementFactory.generateTitleLabel(title);

        Page dropdownPage = new Page(0,10);

        dropdownPage.add(titleLabel);

        PageElement centeredLabel1;
        PageElement centeredLabel2;
        PageElement centeredLabel3;

        int frameOffset = 50;
        int titleHeight = titleLabel.height;
        int stringHeight = 50;
        int spaceToUse = spielerFrame.PANEL_HEIGHT - frameOffset - titleHeight - stringHeight;
        int spacePerString;
        int startpoint;

        switch(numberOfDropdowns){
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


        Page imagePage = new Page(0,10);

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

        imageLabel1.setCoordX(imageLabel1.coordX-10);
        imageLabel2.setCoordX(imageLabel2.coordX+10);


        Page imagePage = new Page(0,10);

        imagePage.add(titleLabel);
        imagePage.add(imageLabel1);
        imagePage.add(imageLabel2);

        return imagePage;
    }

    public Page generateListPage(String title, ArrayList<String> stringsToDisplay){
        spielerFrame.title = title;
        spielerFrame.mode = SpielerFrameMode.staticListPage;

        Page listPage = new Page(0,10);

        PageElement titleLabel = pageElementFactory.generateTitleLabel(title);
        listPage.add(titleLabel);

        ArrayList<String> realStringsToDisplay = new ArrayList<String>(stringsToDisplay);
        realStringsToDisplay.remove("");

        if(realStringsToDisplay.size() > 0) {
            int frameOffset = 50;
            int titleHeight = titleLabel.height;
            int stringHeight = 50;
            int spaceToUse = spielerFrame.PANEL_HEIGHT - frameOffset - titleHeight - stringHeight;
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

    public Page generateListMirrorPage(String title, ArrayList<String> stringsToDisplay){
        Page listPage = generateListPage(title, stringsToDisplay);

        spielerFrame.mode = SpielerFrameMode.listMirrorPage;

        return listPage;
    }

    public Page generateTitlePage(String title) {
        spielerFrame.title = title;
        spielerFrame.mode = SpielerFrameMode.titlePage;

        Page titlePage = new Page(0,10);

        PageElement titleLabel = pageElementFactory.generateTitleLabel(title);
        titlePage.add(titleLabel);

        return titlePage;
    }

    public Page generateTitlePage() {
        return generateTitlePage(spielerFrame.title);
    }

    public Page generateDoubleListPage(ArrayList<String> stringsToDisplay1, ArrayList<String> stringsToDisplay2){
        Page listPage = new Page(0,10);
        ArrayList<String> realStringsToDisplay = new ArrayList<String>(stringsToDisplay1);
        realStringsToDisplay.remove("");

        listPage = generateListPage(stringsToDisplay1, true);
        Page listPage2 = generateListPage(stringsToDisplay2, false);
        for(PageElement element : listPage2.pageElements){
            listPage.add(element);
        }

        return listPage;
    }

    public Page generateListPage(ArrayList<String> stringsToDisplay){
        Page listPage = new Page(0,10);
        ArrayList<String> realStringsToDisplay = new ArrayList<String>(stringsToDisplay);
        realStringsToDisplay.remove("");

        if(realStringsToDisplay.size() > 0) {
            if(realStringsToDisplay.size() < 10) {
                int frameOffset = 50;
                int stringHeight = 50;
                int spaceToUse = spielerFrame.PANEL_HEIGHT - frameOffset;
                int spacePerString = spaceToUse / realStringsToDisplay.size();
                int spacingBetweenStrings = spacePerString - stringHeight;
                int startpoint = ((spacePerString / 2) - (stringHeight / 2));

                PageElement label = pageElementFactory.generateCenteredLabel(new JLabel(realStringsToDisplay.get(0)), null, startpoint);
                listPage.add(label);

                int i = 0;
                for (String string : realStringsToDisplay) {
                    if (i != 0) {
                        label = pageElementFactory.generateCenteredLabel(new JLabel(string), label, spacingBetweenStrings);
                        listPage.add(label);
                    }

                    i++;
                }
            } else {
                int halfpoint = realStringsToDisplay.size()/2;
                listPage = generateListPage(new ArrayList<String>(stringsToDisplay.subList(0,halfpoint)), true);
                Page listPage2 = generateListPage(new ArrayList<String>(stringsToDisplay.subList(halfpoint, stringsToDisplay.size())), false);
                for(PageElement element : listPage2.pageElements){
                    listPage.add(element);
                }
            }
        }

        return listPage;
    }

    public Page generateListPage(ArrayList<String> stringsToDisplay, boolean left){
        Page listPage = new Page(0,10);
        ArrayList<String> realStringsToDisplay = new ArrayList<String>(stringsToDisplay);
        realStringsToDisplay.remove("");

        if(realStringsToDisplay.size() > 0) {
            int frameOffset = 50;
            int stringHeight = 50;
            int spaceToUse = spielerFrame.PANEL_HEIGHT - frameOffset;
            int spacePerString = spaceToUse / realStringsToDisplay.size();
            int spacingBetweenStrings = spacePerString - stringHeight;
            int startpoint = ((spacePerString / 2) - (stringHeight / 2));

            PageElement label;
            label = pageElementFactory.generateCenteredLabel(new JLabel(realStringsToDisplay.get(0)), null, startpoint, left);
            listPage.add(label);

            int i = 0;
            for (String string : realStringsToDisplay) {
                if (i != 0) {
                    label = pageElementFactory.generateCenteredLabel(new JLabel(string), label, spacingBetweenStrings, left);
                    listPage.add(label);
                }

                i++;
            }
        }

        return listPage;
    }
}
