package root.Frontend.Factories;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.ErzählerFrameMode;
import root.Frontend.Frame.FrameMode;
import root.Frontend.Frame.MyFrame;
import root.Frontend.Page.PageElement;
import root.Frontend.Page.PageTable;
import root.Frontend.Page.Predecessor;
import root.Logic.Persona.Bonusrolle;
import root.Logic.Persona.Rollen.Hauptrollen.Bürger.Sammler;
import root.Logic.Phases.NormalNight;
import root.Logic.Phases.SetupNight;
import root.Logic.Phases.Statement.Constants.StatementState;
import root.Logic.Phases.Statement.Constants.StatementType;
import root.Logic.Phases.Statement.Statement;
import root.Logic.Phases.Statement.StatementDependency.StatementDependencyRolle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ErzählerPageElementFactory {
    static ErzählerFrame erzählerFrame;
    private static final int bigTextSize = 36;
    private static final int smallTextSize = 28;

    public ErzählerPageElementFactory(ErzählerFrame erzählerFrame) {
        ErzählerPageElementFactory.erzählerFrame = erzählerFrame;
    }

    PageElement generateLabel(Predecessor predecessorY, String title) {
        JLabel titleJLabel = new JLabel(title);

        return generateInteractiveLabel(predecessorY, titleJLabel);
    }

    PageElement generateXLabel(Predecessor predecessorX, JLabel label) {
        return generateXLabel(predecessorX, label, 0);
    }

    PageElement generateXLabel(Predecessor predecessorX, JLabel label, int spaceToPredecessor) {
        int numberOfPlayersLabeWidth = 300;
        int numberOfPlayersLabeHeight = 25;

        return new PageElement(label, numberOfPlayersLabeWidth, numberOfPlayersLabeHeight, predecessorX, null, spaceToPredecessor, 0);
    }

    private PageElement generateInteractiveLabel(Predecessor predecessorY, JLabel label) {
        int numberOfPlayersLabeWidth = 300;
        int numberOfPlayersLabeHeight = 25;

        return new PageElement(label, numberOfPlayersLabeWidth, numberOfPlayersLabeHeight, null, predecessorY);
    }

    public PageElement generateUpperCenterStartButton(JButton button) {
        String startButtonTitle = "Neues Spiel";
        int startButtonWidth = 300;
        int startButtonHeight = 50;

        button.setText(startButtonTitle);
        button.addActionListener(erzählerFrame);
        button.setBackground(MyFrame.DEFAULT_BUTTON_COLOR);

        PageElement startButton = new PageElement(button, startButtonWidth, startButtonHeight, null, null, 0, 0);

        int xCoord = erzählerFrame.frameJpanel.getWidth() / 2 - (startButtonWidth / 2);
        int yCoord = erzählerFrame.frameJpanel.getHeight() / 2 - startButtonHeight;

        startButton.setCoords(xCoord, yCoord);

        return startButton;
    }

    public PageElement generateCenteredBigButton(JButton button, Predecessor predecessorY) {
        int bigButtonWidth = 300;
        int bigButtonHeight = 50;

        button.addActionListener(erzählerFrame);
        button.setBackground(MyFrame.DEFAULT_BUTTON_COLOR);

        PageElement bigButton = new PageElement(button, bigButtonWidth, bigButtonHeight, null, predecessorY, 0, 10);

        int xCoord = erzählerFrame.frameJpanel.getWidth() / 2 - (bigButtonWidth / 2);

        bigButton.setCoordX(xCoord);

        return bigButton;
    }

    public PageElement generateSmallButton(JButton jbutton, Predecessor predecessorX) {
        int buttonWidth = 120;
        int buttonHeight = 25;
        int spaceToPredecessorX = 10;

        if (predecessorX == null)
            spaceToPredecessorX = 0;

        PageElement button = new PageElement(jbutton, buttonWidth, buttonHeight, predecessorX, spaceToPredecessorX);

        jbutton.setBackground(MyFrame.DEFAULT_BUTTON_COLOR);
        jbutton.addActionListener(erzählerFrame);

        return button;
    }

    public PageElement generateLowestButton(JButton button) {
        return generateLowestButton(button, "Weiter");
    }

    public PageElement generateLowestButton(JButton button, String title) {
        return generateLowestButton(button, title, true);
    }

    public PageElement generateLowestButton(JButton button, String title, boolean right) {
        return generateLowestButton(button, title, right, 0);
    }

    public PageElement generateLowestButton(JButton button, boolean right, int howManyButtonsBefore) {
        return generateLowestButton(button, button.getText(), right, howManyButtonsBefore);
    }

    public PageElement generateLowestButton(JButton button, String title, boolean right, int howManyButtonsBefore) {
        int elementWidth = 120;
        if (erzählerFrame.frameMode == FrameMode.small) {
            elementWidth = 100;
        }
        int elementHeight = 50;
        int xSpace = 10;
        int xOffset = (elementWidth + xSpace) * howManyButtonsBefore;
        int spaceToPredecessorX = xOffset;
        int spaceToPredecessorY = erzählerFrame.frameJpanel.getHeight() - elementHeight - 20;

        if (right) {
            spaceToPredecessorX = (erzählerFrame.frameJpanel.getWidth() - elementWidth - 20) - xOffset;
        }

        PageElement goNextButton = new PageElement(button, elementWidth, elementHeight, null, null, spaceToPredecessorX, spaceToPredecessorY);

        button.setText(title);
        button.setMargin(new Insets(0, 0, 0, 0));
        if (button.getActionListeners().length == 0) {
            button.addActionListener(erzählerFrame);
        }
        button.setBackground(MyFrame.DEFAULT_BUTTON_COLOR);

        return goNextButton;
    }

    public PageElement generateAddPlayerTxtField(JTextField textField, Predecessor predecessorY) {
        int addPlayerTxtFieldWidth = 120;
        int addPlayerTxtFieldHeight = 25;
        int spaceToPredecessorX = Predecessor.DEFAULT_SPACE;
        int spaceToPredecessorY = 10;

        textField.setText("");

        PageElement addPlayerTxtField = new PageElement(textField, addPlayerTxtFieldWidth, addPlayerTxtFieldHeight, null, predecessorY, spaceToPredecessorX, spaceToPredecessorY);

        textField.addActionListener(erzählerFrame);

        return addPlayerTxtField;
    }

    public PageElement generateCounterLabel(JLabel label, Predecessor predecessorY) {
        int labelWidth = 150;
        int labelHeight = 25;
        int spaceToPredecessorY = 10;

        return new PageElement(label, labelWidth, labelHeight, null, predecessorY, Predecessor.DEFAULT_SPACE, spaceToPredecessorY);
    }

    public String generateNumberOfPLayersLabelTitle(int numberOfPlayers) {
        return "Spieleranzahl: " + Integer.toString(numberOfPlayers);
    }

    public String generateCounterLabelString(int numberOfPlayers, int numberOfRollen) {
        return Integer.toString(numberOfRollen) + " / " + Integer.toString(numberOfPlayers);
    }

    public PageTable generateButtonTable(PageTable buttonTable, Predecessor predecessorY) {
        int tableElementWidth = 120;
        int tableElementHeight = 30;
        int tableElementsXDistance = 10;
        int tableElementsYDistance = 10;
        int spaceToPredecessorX = 0;
        int spaceToPredecessorY = 10;

        int columns = calculateColumns(120 + 10);

        return generatePageTable(buttonTable, predecessorY, columns, tableElementWidth, tableElementHeight, tableElementsXDistance, tableElementsYDistance, spaceToPredecessorX, spaceToPredecessorY);

    }

    public void generateTableButtons(List<String> stringsToFillIn, List<JButton> tableButtons, PageTable pageTable) {
        tableButtons.clear();
        pageTable.tableElements.clear();
        for (String rolle : stringsToFillIn) {
            JButton button = new JButton(rolle);
            button.addActionListener(erzählerFrame);
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setBackground(Bonusrolle.DEFAULT_COLOR);
            tableButtons.add(button);
            pageTable.add(button);
        }
    }

    public PageTable generatePageTable(PageTable table, Predecessor predecessorY, int columns, int tableElementWidth, int tableElementHeight,
                                       int tableElementsXDistance, int tableElementsYDistance, int spaceToPredecessorX, int spaceToPredecessorY) {

        table.setupTable(columns, tableElementWidth, tableElementHeight, null, predecessorY, spaceToPredecessorX, spaceToPredecessorY);
        table.setTable_elements_x_distance(tableElementsXDistance);
        table.setTable_elements_y_distance(tableElementsYDistance);

        return table;
    }

    public PageTable generatePageTable(PageTable table, Predecessor predecessorX, Predecessor predecessorY, int columns, int tableElementWidth, int tableElementHeight,
                                       int tableElementsXDistance, int tableElementsYDistance, int spaceToPredecessorX, int spaceToPredecessorY) {

        table.setupTable(columns, tableElementWidth, tableElementHeight, predecessorX, predecessorY, spaceToPredecessorX, spaceToPredecessorY);
        table.setTable_elements_x_distance(tableElementsXDistance);
        table.setTable_elements_y_distance(tableElementsYDistance);

        return table;
    }

    public static JButton generateDeleteButton() {
        JButton deleteButton = new JButton("X");
        deleteButton.setMargin(new Insets(0, 0, 0, 0));
        deleteButton.addActionListener(erzählerFrame);
        deleteButton.setBackground(MyFrame.DEFAULT_BUTTON_COLOR);

        return deleteButton;
    }

    public PageElement generateNightLabel(String currentStatement) {
        List<String> statementStrings = new ArrayList<>();
        List<String> statementColors = new ArrayList<>();

        boolean found = false;

        List<Statement> statements = new ArrayList<>();

        if (ErzählerFrame.mode == ErzählerFrameMode.SETUP_NIGHT) {
            statements = SetupNight.statements;
        } else if (ErzählerFrame.mode == ErzählerFrameMode.NORMAL_NIGHT) {
            statements = NormalNight.statements;
        }

        for (Statement statement : statements) {
            if (statement.state != StatementState.INVISIBLE_NOT_IN_GAME && statement.type != StatementType.PROGRAM) {
                String beschreibung = statement.beschreibung;

                if (statement.dependency instanceof StatementDependencyRolle) {
                    StatementDependencyRolle statementDependencyRolle = (StatementDependencyRolle) statement.dependency;
                    if (Sammler.isSammlerRolle(statementDependencyRolle.rolle.id)) {
                        beschreibung = statement.sammlerBeschreibung;
                    }
                }

                statementStrings.add(beschreibung);

                if ((statement.state == StatementState.NOT_IN_GAME)) {
                    if (statement.id.equals(currentStatement)) {
                        statementColors.add(HTMLStringBuilder.blue);
                        found = true;
                    } else {
                        statementColors.add(HTMLStringBuilder.gray);
                    }
                } else {
                    if (statement.id.equals(currentStatement)) {
                        statementColors.add(HTMLStringBuilder.yellow);
                        found = true;
                    } else {
                        if (!found) {
                            statementColors.add(HTMLStringBuilder.gray);
                        } else {
                            statementColors.add(HTMLStringBuilder.white);
                        }
                    }
                }
            }
        }

        String nightText = HTMLStringBuilder.buildHTMLText(statementStrings, statementColors);

        JLabel nightJLabel = new JLabel(nightText);
        nightJLabel.setVerticalAlignment(SwingConstants.TOP);
        int width = nightJLabel.getPreferredSize().width;
        if (width > 400) {
            width = 400;
        }

        PageElement nightLabel = new PageElement(nightJLabel, width, erzählerFrame.frameJpanel.getHeight() + 300);

        return nightLabel;
    }

    public PageElement generateDropdown(JComboBox jComboBox, Predecessor predecessorX, Predecessor predecessorY,
                                        int width, int height, int spaceToPredecessorX, int spaceToPredecessorY) {
        jComboBox.addActionListener(erzählerFrame);
        jComboBox.setSelectedIndex(jComboBox.getItemCount() - 1);
        jComboBox.setBackground(MyFrame.DEFAULT_BUTTON_COLOR);

        PageElement comboBox = new PageElement(jComboBox, width, height, predecessorX, predecessorY, spaceToPredecessorX, spaceToPredecessorY);

        return comboBox;
    }

    public PageElement generateDropdown(JComboBox jComboBox, Predecessor predecessorX, Predecessor predecessorY, int spaceToPredecessorX, int spaceToPredecessorY) {
        return generateDropdown(jComboBox, predecessorX, predecessorY, 200, 20, spaceToPredecessorX, spaceToPredecessorY);
    }

    public PageElement generateDropdown(JComboBox jComboBox, Predecessor predecessorX, Predecessor predecessorY) {
        return generateDropdown(jComboBox, predecessorX, predecessorY, 200, 20, 0, 20);
    }

    public PageElement generateTitleLabel(Predecessor predecessorX, String title) {
        int textSize = bigTextSize;
        if (erzählerFrame.frameMode == FrameMode.small) {
            textSize = smallTextSize;
        }

        JLabel titleJLabel = generateTitleJLabel(title, textSize);

        int titleLabelWidth = titleJLabel.getPreferredSize().width;
        int titleLabelHeight = titleJLabel.getPreferredSize().height;
        int spaceToPredecessorY = 0;
        int spaceToPredecessorX = 20;

        return new PageElement(titleJLabel, titleLabelWidth, titleLabelHeight, predecessorX, null, spaceToPredecessorX, spaceToPredecessorY);
    }

    public PageElement generateIcon(Predecessor predecessorY, String filePath) {
        JLabel iconJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(filePath);

        int iconWidth = iconLogo.getIconWidth();
        int iconHeight = iconLogo.getIconHeight();

        iconJLabel.setIcon(iconLogo);

        return new PageElement(iconJLabel, iconWidth, iconHeight, null, predecessorY, PageElement.DEFAULT_SPACE, 20);
    }

    public PageElement generateCenteredImageLabel(String imagePath, Predecessor predecessorY) {
        JLabel imageJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(imagePath);

        int imageJLabelWidth = iconLogo.getIconWidth();
        int imageJLabelHeight = iconLogo.getIconHeight();

        imageJLabel.setIcon(iconLogo);

        PageElement imageLabel = new PageElement(imageJLabel, imageJLabelWidth, imageJLabelHeight, null, predecessorY, 0, 20);

        int xCoord = erzählerFrame.frameJpanel.getWidth() / 2 - (imageJLabelWidth / 2);

        imageLabel.setCoordX(xCoord);

        return imageLabel;
    }

    public PageElement generateCenteredImageLabel(String imagePath, int numberOfBigButtons, int bigButtonHeightPlusSpace) {
        JLabel imageJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(imagePath);

        int imageJLabelWidth = iconLogo.getIconWidth();
        int imageJLabelHeight = iconLogo.getIconHeight();

        imageJLabel.setIcon(iconLogo);

        int yOffset = erzählerFrame.frameJpanel.getHeight() / 2 - (imageJLabelHeight + numberOfBigButtons * bigButtonHeightPlusSpace) / 2;

        PageElement imageLabel = new PageElement(imageJLabel, imageJLabelWidth, imageJLabelHeight, null, null, 0, yOffset);

        int xCoord = erzählerFrame.frameJpanel.getWidth() / 2 - (imageJLabelWidth / 2);

        imageLabel.setCoordX(xCoord);

        return imageLabel;
    }

    public PageElement generateSmallIcon(Predecessor predecessorY, String filePath) {
        JLabel iconJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(filePath);

        int iconWidth = (int) (iconLogo.getIconWidth() * 0.7);
        int iconHeight = (int) (iconLogo.getIconHeight() * 0.7);

        Image img = iconLogo.getImage();
        if (!(img == null || iconWidth == 0 || iconHeight == 0)) {
            Image newimg = img.getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH);
            iconLogo = new ImageIcon(newimg);

            iconJLabel.setIcon(iconLogo);
        } else {
            System.out.println("1 Image could not be found at location: " + filePath);
        }

        return new PageElement(iconJLabel, iconWidth, iconHeight, null, predecessorY, PageElement.DEFAULT_SPACE, 20);
    }

    public JLabel generateTitleJLabel(String text) {
        return generateTitleJLabel(text, bigTextSize);
    }

    public JLabel generateTitleJLabel(String text, int textSize) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, textSize));

        return label;
    }

    public PageElement generateLeftCenteredLabel(JLabel label) {
        label = generateBigJLabel(label);

        int imageJLabelWidth = (int) label.getPreferredSize().getWidth();
        int imageJLabelHeight = (int) label.getPreferredSize().getHeight();

        int spaceToPredecessorY = erzählerFrame.frameJpanel.getHeight() / 2 - imageJLabelHeight / 2;

        PageElement centeredLabel = new PageElement(label, imageJLabelWidth, imageJLabelHeight, null, null, 0, spaceToPredecessorY);

        int xCoord = erzählerFrame.frameJpanel.getWidth() / 3 - (imageJLabelWidth / 2);

        centeredLabel.setCoordX(xCoord);

        return centeredLabel;
    }

    public PageElement generateRightCenteredImage(String imagePath) {
        JLabel imageJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(imagePath);

        int imageJLabelWidth = iconLogo.getIconWidth();
        int imageJLabelHeight = iconLogo.getIconHeight();

        int spaceToPredecessorY = erzählerFrame.frameJpanel.getHeight() / 2 - imageJLabelHeight / 2;

        imageJLabel.setIcon(iconLogo);

        PageElement imageLabel = new PageElement(imageJLabel, imageJLabelWidth, imageJLabelHeight, null, null, 0, spaceToPredecessorY);

        int xCoord = (erzählerFrame.frameJpanel.getWidth() / 3) * 2 - (imageJLabelWidth / 2);

        imageLabel.setCoordX(xCoord);

        return imageLabel;
    }

    public JLabel generateBigJLabel(JLabel label) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 36));

        return label;
    }

    public int calculateColumns(int columnWidth) {
        return (int) Math.floor((double) erzählerFrame.frameJpanel.getWidth() / (double) columnWidth);
    }
}
