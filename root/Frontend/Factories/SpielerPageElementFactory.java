package root.Frontend.Factories;

import root.Frontend.Frame.SpielerFrame;
import root.Frontend.Page.PageElement;
import root.Frontend.Page.Predecessor;
import root.Logic.Persona.Rollen.Constants.RawInformation;
import root.ResourceManagement.ImagePath;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SpielerPageElementFactory {
    public static final int defaultTextSize = 36;
    public static final int defaultTitleSize = 48;

    private SpielerFrame spielerFrame;

    public SpielerPageElementFactory(SpielerFrame spielerFrame) {
        this.spielerFrame = spielerFrame;
    }


    public PageElement generateBierImage(Corner corner, int heigth) {
        int xSpace = 10;
        int ySpace = 20;

        return generateCornerImage(ImagePath.FREIBIER, corner, heigth, xSpace, ySpace);
    }

    public PageElement generateCornerImage(String imagePath, Corner corner, int height, int xSpace, int ySpace) {
        PageElement pageImage = generateFixedHeightPageImage(imagePath, height);

        Point point = generateCornerCoordinates(pageImage.width, height, xSpace, ySpace, corner);

        pageImage.setCoords(point);

        return pageImage;
    }

    private Point generateCornerCoordinates(int width, int heigth, int xSpace, int ySpace, Corner corner) {
        Point startpoint = generateCornerStarpoint(corner);
        int xOffsetDirection = corner.xDirection;
        int yOffsetDirection = corner.yDirection;

        int xImageOffset = xOffsetDirection == Direction.POSITIVE.modificatior ? 0 : width * xOffsetDirection;
        int yImageOffset = yOffsetDirection == Direction.POSITIVE.modificatior ? 0 : heigth * yOffsetDirection;

        int xCoord = startpoint.x + xImageOffset + xSpace * xOffsetDirection;
        int yCoord = startpoint.y + yImageOffset + ySpace * yOffsetDirection;

        return new Point(xCoord, yCoord);
    }

    private Point generateCornerStarpoint(Corner corner) {
        int x = corner.xDirection == Direction.POSITIVE.modificatior ? 0 : getSpielerFrameWidth();
        int y = corner.yDirection == Direction.POSITIVE.modificatior ? 0 : getSpielerFrameHeight();

        return new Point(x, y);
    }

    private PageElement generateFixedHeightPageImage(String imagePath, int height) {
        JLabel bierJLabel = generateFixedHeightImage(imagePath, height);

        int imageWidth = bierJLabel.getIcon().getIconWidth();
        int imageHeight = bierJLabel.getIcon().getIconHeight();

        return new PageElement(bierJLabel, imageWidth, imageHeight, null, null, 0, 0);
    }


    public PageElement generateHorizontallyCenteredImageLabel(String imagePath, Predecessor predecessorY) {
        JLabel imageJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(imagePath);

        int imageJLabelWidth = iconLogo.getIconWidth();
        int imageJLabelHeight = iconLogo.getIconHeight();

        imageJLabel.setIcon(iconLogo);

        PageElement imageLabel = new PageElement(imageJLabel, imageJLabelWidth, imageJLabelHeight, null,
                predecessorY, 0, 0);

        int xCoord = getSpielerFrameWidth() / 2 - (imageJLabelWidth / 2);

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

        int xCoord = getSpielerFrameWidth() / 2 - (imageJLabelWidth / 2);
        int yCoord = getSpielerFrameHeight() / 2 - (imageJLabelHeight / 2);

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

        int xCoord = getSpielerFrameWidth() / 2 - imageJLabelWidth;

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

        int imageJLabelWidth = getSpielerFrameWidth();
        int imageJLabelHeight = getJLabelStandardHeight();

        PageElement centeredLabel = new PageElement(label, imageJLabelWidth, imageJLabelHeight, null, predecessorY, 0, spaceToPredecessorY);

        int xCoord = getSpielerFrameWidth() / 2 - (imageJLabelWidth / 2);

        centeredLabel.setCoordX(xCoord);

        return centeredLabel;
    }

    public PageElement generateColumnCenteredLabel(JLabel label, Predecessor predecessorY, int spaceToPredecessorY, int numberOfColumns, int indexOfColumn) {
        return generateColumnCenteredLabel(label, predecessorY, spaceToPredecessorY, numberOfColumns, indexOfColumn, defaultTextSize);
    }

    public PageElement generateColumnCenteredLabel(JLabel label, Predecessor predecessorY, int spaceToPredecessorY, int numberOfColumns, int indexOfColumn, int size) {
        label = formatLabel(label, size);

        int jLabelWidth = getSpielerFrameWidth() / numberOfColumns;
        int jLabelHeight = getJLabelHeight(size);

        PageElement centeredLabel = new PageElement(label, jLabelWidth, jLabelHeight, null, predecessorY, 0, spaceToPredecessorY);

        int spacePerColumn = getSpielerFrameWidth() / numberOfColumns;
        int xCoord = (int) (spacePerColumn * (indexOfColumn + 0.5) - (jLabelWidth / 2));

        centeredLabel.setCoordX(xCoord);

        return centeredLabel;
    }

    public PageElement generateColumnCenteredComponent(JComponent component, Predecessor predecessorY, int spaceToPredecessorY, int numberOfColumns, int indexOfColumn) {
        int componentWidth = (int) component.getPreferredSize().getWidth();
        int componentHeight = (int) component.getPreferredSize().getHeight();

        PageElement centeredElement = new PageElement(component, componentWidth, componentHeight, null, predecessorY, 0, spaceToPredecessorY);

        int spacePerColumn = getSpielerFrameWidth() / numberOfColumns;
        int xCoord = (int) (spacePerColumn * (indexOfColumn + 0.5) - (componentWidth / 2));

        centeredElement.setCoordX(xCoord);

        return centeredElement;
    }

    public PageElement generateTitleLabel(String title) {
        return generateColumnTitleLabel(title, 1, 0);
    }

    public PageElement generateColumnTitleLabel(String title, int numberOfColumns, int indexOfColumn) {
        return generateColumnTitleLabel(title, numberOfColumns, indexOfColumn, defaultTitleSize);
    }

    public PageElement generateColumnTitleLabel(String title, int numberOfColumns, int indexOfColumn, int spaceToUse) {
        JLabel titleJLabel = generateTitleJLabel(title);

        int numberOfPlayersLabelWidth = getSpielerFrameWidth();
        int numberOfPlayersLabelHeight = spaceToUse + 10;

        PageElement titleLabel = new PageElement(titleJLabel, numberOfPlayersLabelWidth, numberOfPlayersLabelHeight, null, null);

        int spacePerColumn = getSpielerFrameWidth() / numberOfColumns;
        int xCoord = (int) (spacePerColumn * (indexOfColumn + 0.5) - (numberOfPlayersLabelWidth / 2));

        titleLabel.setCoordX(xCoord);

        return titleLabel;
    }

    public PageElement generateClockLabel(JLabel label, int spaceToUse) {
        JLabel clockJLabel = formatLabel(label, 100);

        int clockLabelWidth = getSpielerFrameWidth();
        int clockLabelHeight = spaceToUse;

        PageElement clockLabel = new PageElement(clockJLabel, clockLabelWidth, clockLabelHeight, null, null);

        int yCoord = getSpielerFrameHeight() - spaceToUse;
        clockLabel.setCoordY(yCoord);

        return clockLabel;
    }

    public JLabel formatLabel(JLabel label, int size) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, size));

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

    public JLabel generateFixedHeightImage(String imagepath, int wantedHeight) {
        JLabel iconJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(imagepath);

        int actualHeight = iconLogo.getIconHeight();
        int actualWidth = iconLogo.getIconWidth();
        double skalierungsFaktor = ((double) wantedHeight) / actualHeight;
        int scaledIconWidth = (int) ((double) actualWidth * skalierungsFaktor);

        scaleImage(iconLogo, scaledIconWidth, wantedHeight);
        iconJLabel.setIcon(iconLogo);

        return iconJLabel;
    }

    private void scaleImage(ImageIcon imageIcon, int width, int height) {
        Image img = imageIcon.getImage();
        if (img != null) {
            Image newimg = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            imageIcon.setImage(newimg);
        }
    }

    public List<JComponent> generateFixedSizedImages(RawInformation rawInformation, int width, int height) {
        List<JComponent> images = new ArrayList<>();

        for (String imagepath : rawInformation.imagePaths) {
            images.add(generateFixedSizeImage(imagepath, width, height));
        }

        return images;
    }

    public JLabel generateFixedSizeImage(String imagepath, int wantedWidth, int wantedHeight) {
        JLabel iconJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(imagepath);

        int iconWidth = iconLogo.getIconWidth();
        int iconHeight = iconLogo.getIconHeight();
        if (iconWidth > wantedWidth || iconHeight > wantedHeight) {
            double verkleinerunsFaktor = getVerkleinerungsFaktor(iconWidth, iconHeight, wantedWidth, wantedHeight);

            iconWidth = (int) ((double) iconLogo.getIconWidth() * verkleinerunsFaktor);
            iconHeight = (int) ((double) iconLogo.getIconHeight() * verkleinerunsFaktor);

            if (!imagepath.isEmpty()) {
                Image img = iconLogo.getImage();
                if (img != null) {
                    Image newimg = img.getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH);
                    iconLogo = new ImageIcon(newimg);

                    iconJLabel.setIcon(iconLogo);
                }
            }
        }

        iconJLabel.setIcon(iconLogo);

        return iconJLabel;
    }

    private double getVerkleinerungsFaktor(int iconWidth, int iconHeight, int wantedWidth, int wantedHeight) {
        int bigNumber = 100;
        double verkleinerungsFaktorWidth = bigNumber;
        if (iconWidth > wantedWidth) {
            verkleinerungsFaktorWidth = ((double) wantedWidth) / iconWidth;
        }

        double verkleinerungsFaktorHeight = bigNumber;
        if (iconHeight > wantedHeight) {
            verkleinerungsFaktorHeight = ((double) wantedHeight) / iconHeight;
        }

        return getSmallerNumber(verkleinerungsFaktorWidth, verkleinerungsFaktorHeight);
    }

    private double getSmallerNumber(double number1, double number2) {
        if (number1 < number2) {
            return number1;
        } else {
            return number2;
        }
    }

    public int getJLabelStandardHeight() {
        JLabel jLabel = new JLabel("test");
        jLabel = generateBigJLabel(jLabel);

        return (int) jLabel.getPreferredSize().getHeight();
    }

    public int getJLabelHeight(int size) {
        JLabel jLabel = new JLabel("test");
        jLabel = formatLabel(jLabel, size);

        return (int) jLabel.getPreferredSize().getHeight();
    }

    private int getSpielerFrameWidth() {
        return spielerFrame.frameJpanel.getWidth();
    }

    private int getSpielerFrameHeight() {
        return spielerFrame.frameJpanel.getHeight();
    }
}
