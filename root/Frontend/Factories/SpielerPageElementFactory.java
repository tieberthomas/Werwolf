package root.Frontend.Factories;

import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Page.PageElement;
import root.Frontend.Page.Predecessor;

import javax.swing.*;
import java.awt.*;

public class SpielerPageElementFactory {
    SpielerFrame spielerFrame;
    public SpielerPageElementFactory(SpielerFrame spielerFrame)
    {
        this.spielerFrame = spielerFrame;
    }
    public static final int defaultTextSize = 36;
    public static final int defaultTitleSize = 48;

    public PageElement generateBierLabel() {
        JLabel bierJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon("D:\\tmp\\bier.png");

        int bierLabelWidth = iconLogo.getIconWidth();
        int bierLabelHeight = iconLogo.getIconHeight();

        bierJLabel.setIcon(iconLogo);

        PageElement bierLabel = new PageElement(bierJLabel, bierLabelWidth, bierLabelHeight, null, null, 0, 0);

        int xCoord = spielerFrame.frameJpanel.getWidth()/2-(bierLabelWidth/2);
        int yCoord = spielerFrame.frameJpanel.getHeight()/2-(bierLabelHeight/2);

        bierLabel.setCoords(xCoord, yCoord);

        return bierLabel;
    }

    public PageElement generateHorizontallyCenteredImageLabel(String imagePath, Predecessor predecessorY) {
        JLabel imageJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(imagePath);

        int imageJLabelWidth = iconLogo.getIconWidth();
        int imageJLabelHeight = iconLogo.getIconHeight();

        imageJLabel.setIcon(iconLogo);

        PageElement imageLabel = new PageElement(imageJLabel, imageJLabelWidth, imageJLabelHeight, null,
                predecessorY, 0, 0);

        int xCoord = spielerFrame.frameJpanel.getWidth()/2-(imageJLabelWidth/2);

        imageLabel.setCoordX(xCoord);

        return imageLabel;
    }

    public PageElement generateFullCenteredImageLabel(String imagePath) {
        JLabel imageJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(imagePath);

        int imageJLabelWidth = iconLogo.getIconWidth();
        int imageJLabelHeight = iconLogo.getIconHeight();

        imageJLabel.setIcon(iconLogo);

        PageElement imageLabel = new PageElement(imageJLabel, imageJLabelWidth, imageJLabelHeight);

        int xCoord = spielerFrame.frameJpanel.getWidth()/2-(imageJLabelWidth/2);
        int yCoord = spielerFrame.frameJpanel.getHeight()/2-(imageJLabelHeight/2);

        imageLabel.setCoords(xCoord, yCoord);

        return imageLabel;
    }

    public PageElement generateLeftCenteredImageLabel(String imagePath, Predecessor predecessorY) {
        JLabel imageJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(imagePath);

        int imageJLabelWidth = iconLogo.getIconWidth();
        int imageJLabelHeight = iconLogo.getIconHeight();

        imageJLabel.setIcon(iconLogo);

        PageElement imageLabel = new PageElement(imageJLabel, imageJLabelWidth, imageJLabelHeight, null, predecessorY, 0, 0);

        int xCoord = spielerFrame.frameJpanel.getWidth()/2-imageJLabelWidth;

        imageLabel.setCoordX(xCoord);

        return imageLabel;
    }

    public PageElement generateImageLabel(String imagePath, Predecessor predecessorX, Predecessor predecessorY) {
        JLabel imageJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(imagePath);

        int imageJLabelWidth = iconLogo.getIconWidth();
        int imageJLabelHeight = iconLogo.getIconHeight();

        imageJLabel.setIcon(iconLogo);

        PageElement imageLabel = new PageElement(imageJLabel, imageJLabelWidth, imageJLabelHeight, predecessorX, predecessorY, 0, 0);

        return imageLabel;
    }

    public JLabel generateIcon(String imagePath) {
        JLabel imageJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(imagePath);
        imageJLabel.setIcon(iconLogo);

        return imageJLabel;
    }

    public PageElement generateCenteredLabel(JLabel label, Predecessor predecessorY) {
        return generateCenteredLabel(label, predecessorY, 100);
    }

    public PageElement generateCenteredLabel(String text, Predecessor predecessorY) {
        return generateCenteredLabel(new JLabel(text), predecessorY, 100);
    }

    public PageElement generateCenteredLabel(JLabel label, Predecessor predecessorY, int spaceToPredecessorY) {
        label = generateBigJLabel(label);

        int imageJLabelWidth = (int)label.getPreferredSize().getWidth();
        int imageJLabelHeight = (int)label.getPreferredSize().getHeight();

        PageElement centeredLabel = new PageElement(label, imageJLabelWidth, imageJLabelHeight, null, predecessorY, 0, spaceToPredecessorY);

        int xCoord = spielerFrame.frameJpanel.getWidth()/2-(imageJLabelWidth/2);

        centeredLabel.setCoordX(xCoord);

        return centeredLabel;
    }

    public PageElement generateColumnCenteredLabel(JLabel label, Predecessor predecessorY, int spaceToPredecessorY, int numberOfColumns, int indexOfColumn) {
        return generateColumnCenteredLabel(label, predecessorY, spaceToPredecessorY, numberOfColumns, indexOfColumn, defaultTextSize);
    }

    public PageElement generateColumnCenteredLabel(JLabel label, Predecessor predecessorY, int spaceToPredecessorY, int numberOfColumns, int indexOfColumn, int size) {
        label = formatLabel(label, size);

        int imageJLabelWidth = (int)label.getPreferredSize().getWidth();
        int imageJLabelHeight = (int)label.getPreferredSize().getHeight();

        PageElement centeredLabel = new PageElement(label, imageJLabelWidth, imageJLabelHeight, null, predecessorY, 0, spaceToPredecessorY);

        int sideFrameWidth = spielerFrame.xOffset;
        int spacePerColumn = (spielerFrame.frameJpanel.getWidth()-sideFrameWidth*2)/numberOfColumns;
        int xCoord = (int)(spacePerColumn*(indexOfColumn+0.5)-(imageJLabelWidth/2));

        centeredLabel.setCoordX(xCoord);

        return centeredLabel;
    }

    public PageElement generateTitleLabel(String title) {
        return generateColumnTitleLabel(title, 1,0);
    }

    public PageElement generateColumnTitleLabel(String title, int numberOfColumns, int indexOfColumn) {
        return generateColumnTitleLabel(title, numberOfColumns, indexOfColumn, defaultTitleSize);
    }

    public PageElement generateColumnTitleLabel(String title, int numberOfColumns, int indexOfColumn, int spaceToUse) {
        JLabel titleJLabel = generateTitleJLabel(title);

        int numberOfPlayersLabelWidth = (int)titleJLabel.getPreferredSize().getWidth();
        int numberOfPlayersLabelHeight = spaceToUse;

        PageElement titleLabel = new PageElement(titleJLabel, numberOfPlayersLabelWidth, numberOfPlayersLabelHeight, null, null);

        int sideFrameWidth = spielerFrame.xOffset;
        int spacePerColumn = (spielerFrame.frameJpanel.getWidth()-sideFrameWidth*2)/numberOfColumns;
        int xCoord = (int)(spacePerColumn*(indexOfColumn+0.5)-(numberOfPlayersLabelWidth/2));

        titleLabel.setCoordX(xCoord);

        return titleLabel;
    }

    public PageElement generateClockLabel(JLabel label, int spaceToUse){
        JLabel clockJLabel = formatLabel(label, 100);

        int sideFrameWidth = spielerFrame.xOffset;
        int clockLabelWidth = (spielerFrame.frameJpanel.getWidth()-sideFrameWidth*2);
        int clockLabelHeight = spaceToUse;

        PageElement clockLabel = new PageElement(clockJLabel, clockLabelWidth, clockLabelHeight, null, null);

        int yCoord = spielerFrame.frameJpanel.getHeight()-spaceToUse;
        clockLabel.setCoordY(yCoord);

        return clockLabel;
    }

    public JLabel formatLabel(JLabel label, int size) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial",Font.PLAIN, size));

        return label;
    }

    public JLabel generateBigJLabel(String text) {
        JLabel label = new JLabel(text);

        return formatLabel(label, defaultTextSize);
    }

    public JLabel generateBigJLabel(JLabel label) {
        return formatLabel(label, defaultTextSize);
    }

    public JLabel generateTitleJLabel(String text) {
        JLabel label = new JLabel(text);

        return formatLabel(label, defaultTitleSize);
    }

    public JLabel generateTitleJLabel(JLabel label) {
        return formatLabel(label, defaultTitleSize);
    }

    public int getJLabelHeight(){
        JLabel jLabel = new JLabel("test");
        jLabel = generateBigJLabel(jLabel);

        return (int)jLabel.getPreferredSize().getHeight();
    }

    public int getJLabelHeight(int size){
        JLabel jLabel = new JLabel("test");
        jLabel = formatLabel(jLabel, size);

        return (int)jLabel.getPreferredSize().getHeight();
    }
}
