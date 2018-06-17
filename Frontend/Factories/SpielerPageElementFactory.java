package root.Frontend.Factories;

import root.Frontend.Page.PageElement;
import root.Frontend.Page.Predecessor;
import root.Frontend.Frame.SpielerFrame;

import javax.swing.*;
import java.awt.*;

public class SpielerPageElementFactory {
    SpielerFrame spielerFrame;
    public SpielerPageElementFactory(SpielerFrame spielerFrame)
    {
        this.spielerFrame = spielerFrame;
    }

    public PageElement generateBierLabel() {
        JLabel bierJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon("D:\\tmp\\bier.png");

        int bierLabelWidth = iconLogo.getIconWidth();
        int bierLabelHeight = iconLogo.getIconHeight();

        bierJLabel.setIcon(iconLogo);

        PageElement bierLabel = new PageElement(bierJLabel, bierLabelWidth, bierLabelHeight, null, null, 0, 0);

        int xCoord = spielerFrame.PANEL_WIDTH/2-(bierLabelWidth/2);
        int yCoord = spielerFrame.PANEL_HEIGHT/2-(bierLabelHeight/2);

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

        int xCoord = spielerFrame.PANEL_WIDTH/2-(imageJLabelWidth/2);

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

        int xCoord = spielerFrame.PANEL_WIDTH/2-(imageJLabelWidth/2);
        int yCoord = spielerFrame.PANEL_HEIGHT/2-(imageJLabelHeight/2);

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

        int xCoord = spielerFrame.PANEL_WIDTH/2-imageJLabelWidth;

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

        int imageJLabelWidth = 500;
        int imageJLabelHeight = 50;

        PageElement centeredLabel = new PageElement(label, imageJLabelWidth, imageJLabelHeight, null, predecessorY, 0, spaceToPredecessorY);

        int xCoord = spielerFrame.PANEL_WIDTH/2-(imageJLabelWidth/2);

        centeredLabel.setCoordX(xCoord);

        return centeredLabel;
    }

    public PageElement generateCenteredLabel(JLabel label, Predecessor predecessorY, int spaceToPredecessorY, boolean left) {
        label = generateBigJLabel(label);

        int imageJLabelWidth = 500;
        int imageJLabelHeight = 50;

        PageElement centeredLabel = new PageElement(label, imageJLabelWidth, imageJLabelHeight, null, predecessorY, 0, spaceToPredecessorY);

        int xCoord;

        if(left)
            xCoord = spielerFrame.PANEL_WIDTH/3-(imageJLabelWidth/2);
        else
            xCoord = (spielerFrame.PANEL_WIDTH/3)*2-(imageJLabelWidth/2);

        centeredLabel.setCoordX(xCoord);

        return centeredLabel;
    }

    public PageElement generateTitleLabel(String titel) {
        JLabel titleJLabel = generateTitleJLabel(titel);

        int sideFrameWidth = 8;
        int numberOfPlayersLabelWidth = spielerFrame.PANEL_WIDTH - sideFrameWidth*2;
        int numberOfPlayersLabelHeight = 60;

        PageElement titleLabel = new PageElement(titleJLabel, numberOfPlayersLabelWidth, numberOfPlayersLabelHeight, null, null);

        return titleLabel;
    }

    public JLabel generateBigJLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial",Font.PLAIN, 36));

        return label;
    }

    public JLabel generateBigJLabel(JLabel label) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial",Font.PLAIN, 36));

        return label;
    }

    public JLabel generateTitleJLabel(String text) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial",Font.PLAIN, 48));

        return label;
    }

    public JLabel generateTitleJLabel(JLabel label) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial",Font.PLAIN, 48));

        return label;
    }
}
