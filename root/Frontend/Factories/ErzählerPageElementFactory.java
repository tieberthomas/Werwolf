package root.Frontend.Factories;

import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.ErzählerFrameMode;
import root.Frontend.Frame.FrameMode;
import root.Frontend.Page.PageElement;
import root.Frontend.Page.PageTable;
import root.Frontend.Page.Predecessor;
import root.Persona.Bonusrolle;
import root.Persona.Rollen.Bonusrollen.Konditorlehrling;
import root.Persona.Rollen.Hauptrollen.Bürger.Sammler;
import root.Phases.ErsteNacht;
import root.Phases.Nacht;
import root.Phases.NightBuilding.Statement;
import root.Phases.NightBuilding.StatementRolle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ErzählerPageElementFactory {
    ErzählerFrame erzählerFrame;
    public static final int bigTextSize = 36;
    public static final int smallTextSize = 28;

    public ErzählerPageElementFactory(ErzählerFrame erzählerFrame) {
        this.erzählerFrame = erzählerFrame;
    }

    public PageElement generateLabel(Predecessor predecessorY, String title) {
        JLabel titleJLabel = new JLabel(title);

        return generateInteractiveLabel(predecessorY, titleJLabel);
    }

    public PageElement generateInteractiveLabel(Predecessor predecessorY, JLabel label) {
        int numberOfPlayersLabeWidth = 300;
        int numberOfPlayersLabeHeight = 25;

        PageElement titleLabel = new PageElement(label, numberOfPlayersLabeWidth, numberOfPlayersLabeHeight, null, predecessorY);

        return titleLabel;
    }

    public PageElement generateUpperCenterStartButton(JButton button) {
        String startButtonTitle = "Neues Spiel";
        int startButtonWidth = 300;
        int startButtonHeight = 50;

        button.setText(startButtonTitle);
        button.addActionListener(erzählerFrame);
        button.setBackground(erzählerFrame.DEFAULT_BUTTON_COLOR);

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
        button.setBackground(erzählerFrame.DEFAULT_BUTTON_COLOR);

        PageElement bigButton = new PageElement(button, bigButtonWidth, bigButtonHeight, null, predecessorY, 0, 10);

        int xCoord = erzählerFrame.frameJpanel.getWidth() / 2 - (bigButtonWidth / 2);

        bigButton.setCoordX(xCoord);

        return bigButton;
    }

    public PageElement generateSmallButton(JButton jbutton) {
        return generateSmallButton(jbutton, null);
    }

    public PageElement generateSmallButton(JButton jbutton, Predecessor predecessorX) {
        int buttonWidth = 120;
        int buttonHeight = 25;
        int spaceToPredecessorX = 10;

        if (predecessorX == null)
            spaceToPredecessorX = 0;

        PageElement button = new PageElement(jbutton, buttonWidth, buttonHeight, predecessorX, spaceToPredecessorX);

        jbutton.setBackground(erzählerFrame.DEFAULT_BUTTON_COLOR);
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
        button.addActionListener(erzählerFrame);
        button.setBackground(erzählerFrame.DEFAULT_BUTTON_COLOR);

        return goNextButton;
    }

    public PageElement generateAddPlayerTxtField(Predecessor predecessorY) {
        int addPlayerTxtFieldWidth = 120;
        int addPlayerTxtFieldHeight = 25;
        int spaceToPredecessorX = Predecessor.DEFAULT_SPACE;
        int spaceToPredecessorY = 10;
        JTextField addPlayerJTxtField = new JTextField("");

        PageElement addPlayerTxtField = new PageElement(addPlayerJTxtField, addPlayerTxtFieldWidth, addPlayerTxtFieldHeight, null, predecessorY, spaceToPredecessorX, spaceToPredecessorY);

        erzählerFrame.addPlayerTxtField = addPlayerJTxtField;
        erzählerFrame.addPlayerTxtField.addActionListener(erzählerFrame);

        return addPlayerTxtField;
    }

    public PageElement generateCounterLabel(JLabel label, Predecessor predecessorY) {
        int labelWidth = 150;
        int labelHeight = 25;
        int spaceToPredecessorY = 10;

        PageElement numberOfPlayersLabel = new PageElement(label, labelWidth, labelHeight, null, predecessorY, Predecessor.DEFAULT_SPACE, spaceToPredecessorY);

        return numberOfPlayersLabel;
    }

    public String generateNumberOfPLayersLabelTitle(int numberOfPlayers) {
        String numberOfPlayersLabelTitle = "Spieleranzahl: " + Integer.toString(numberOfPlayers);

        return numberOfPlayersLabelTitle;
    }

    public String generateCounterLabelTitle(int numberOfPlayers, int numberOfRoles) {
        String numberOfPlayersLabelTitle = Integer.toString(numberOfRoles) + " / " + Integer.toString(numberOfPlayers);

        return numberOfPlayersLabelTitle;
    }

    public PageTable generateButtonTable(Predecessor predecessorY) {
        int tableElementWidth = 120;
        int tableElementHeight = 30;
        int tableElementsXDistance = 10;
        int tableElementsYDistance = 10;
        int spaceToPredecessorX = 0;
        int spaceToPredecessorY = 10;

        int columns = calculateColumns(120 + 10);

        return generatePageTable(predecessorY, columns, tableElementWidth, tableElementHeight, tableElementsXDistance, tableElementsYDistance, spaceToPredecessorX, spaceToPredecessorY);

    }

    public void generateTableButtons(ArrayList<String> stringsToFillIn, ArrayList<JButton> tableButtons, PageTable pageTable) {
        for (String role : stringsToFillIn) {
            JButton button = new JButton(role);
            button.addActionListener(erzählerFrame);
            button.setMargin(new Insets(0, 0, 0, 0));
            button.setBackground(Bonusrolle.DEFAULT_COLOR);
            tableButtons.add(button);
            pageTable.add(button);
        }
    }

    public PageTable generatePageTable(Predecessor predecessorY, int columns, int tableElementWidth, int tableElementHeight,
                                       int tableElementsXDistance, int tableElementsYDistance, int spaceToPredecessorX, int spaceToPredecessorY) {

        PageTable labelTable = new PageTable(columns, tableElementWidth, tableElementHeight, null, predecessorY, spaceToPredecessorX, spaceToPredecessorY);
        labelTable.setTable_elements_x_distance(tableElementsXDistance);
        labelTable.setTable_elements_y_distance(tableElementsYDistance);

        return labelTable;
    }

    public PageTable generatePageTable(Predecessor predecessorX, Predecessor predecessorY, int columns, int tableElementWidth, int tableElementHeight,
                                       int tableElementsXDistance, int tableElementsYDistance, int spaceToPredecessorX, int spaceToPredecessorY) {

        PageTable labelTable = new PageTable(columns, tableElementWidth, tableElementHeight, predecessorX, predecessorY, spaceToPredecessorX, spaceToPredecessorY);
        labelTable.setTable_elements_x_distance(tableElementsXDistance);
        labelTable.setTable_elements_y_distance(tableElementsYDistance);

        return labelTable;
    }

    public JButton generateDeleteButton() {
        JButton deleteButton = new JButton("X");
        deleteButton.setMargin(new Insets(0, 0, 0, 0));
        deleteButton.addActionListener(erzählerFrame);
        deleteButton.setBackground(erzählerFrame.DEFAULT_BUTTON_COLOR);

        return deleteButton;
    }

    public PageElement generateNightLabel(String currentStatement) {
        ArrayList<String> nachtPunkte = new ArrayList<>();
        ArrayList<String> nachtPunkteFarben = new ArrayList<>();

        Boolean found = false;

        ArrayList<Statement> statements = new ArrayList<>();

        if (erzählerFrame.mode == ErzählerFrameMode.ersteNacht) {
            statements = ErsteNacht.statements;
        } else if (erzählerFrame.mode == ErzählerFrameMode.nacht) {
            statements = changeStatementsToFitSammler(Nacht.statements);
        }

        for (Statement statement : statements) {
            if (statement.isVisible()) {
                nachtPunkte.add(statement.beschreibung);
                if (!statement.isLebend()) {
                    if (statement.beschreibung.contains(currentStatement) || (statement.title.equals(Konditorlehrling.STATEMENT_TITLE) && currentStatement.equals(Konditorlehrling.STATEMENT_BESCHREIBUNG))) {
                        found = true;
                        nachtPunkteFarben.add(HTMLStringBuilder.blue);
                    } else {
                        nachtPunkteFarben.add(HTMLStringBuilder.gray);
                    }
                } else {
                    if (statement.beschreibung.contains(currentStatement) || (statement.title.equals(Konditorlehrling.STATEMENT_TITLE) && currentStatement.equals(Konditorlehrling.STATEMENT_BESCHREIBUNG))) {
                        nachtPunkteFarben.add(HTMLStringBuilder.yellow);
                        found = true;
                    } else {
                        if (!found) {
                            nachtPunkteFarben.add(HTMLStringBuilder.gray);
                        } else {
                            nachtPunkteFarben.add(HTMLStringBuilder.white);
                        }
                    }
                }
            }
        }

        String nightText = HTMLStringBuilder.buildHTMLText(nachtPunkte, nachtPunkteFarben);

        JLabel nightJLabel = new JLabel(nightText);
        nightJLabel.setVerticalAlignment(SwingConstants.TOP);
        int width = nightJLabel.getPreferredSize().width;
        if (width > 400) {
            width = 400;
        }

        PageElement nightLabel = new PageElement(nightJLabel, width, erzählerFrame.frameJpanel.getHeight() + 300);

        return nightLabel;
    }

    public ArrayList<Statement> changeStatementsToFitSammler(ArrayList<Statement> statements) {
        ArrayList<Statement> newStatements = new ArrayList<Statement>();

        for (Statement statement : statements) {
            if (statement.getClass() == StatementRolle.class) {
                StatementRolle statementRolle = (StatementRolle) statement;
                if (statementRolle.sammler) {
                    StatementRolle newRolleStatement = new StatementRolle(statementRolle.beschreibung, statementRolle.title, statementRolle.getRolle().name, statementRolle.type);
                    if (!statementRolle.beschreibung.equals(Konditorlehrling.STATEMENT_BESCHREIBUNG)) {
                        newRolleStatement.beschreibung = Sammler.beschreibungAddiditon + statement.beschreibung;
                    } else {
                        String searchString = "Konditorlehrling erwachen ";
                        newRolleStatement.beschreibung = statement.beschreibung.replace(searchString, Sammler.beschreibungAddiditonLowerCase + searchString);
                    }
                    statement = newRolleStatement;
                }
            }
            newStatements.add(statement);
        }

        return newStatements;
    }

    public PageElement generateDropdown(JComboBox jComboBox, Predecessor predecessorX, Predecessor predecessorY,
                                        int width, int height, int spaceToPredecessorX, int spaceToPredecessorY) {
        jComboBox.addActionListener(erzählerFrame);
        jComboBox.setSelectedIndex(jComboBox.getItemCount() - 1);
        jComboBox.setBackground(erzählerFrame.DEFAULT_BUTTON_COLOR);

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

        PageElement titleLabel = new PageElement(titleJLabel, titleLabelWidth, titleLabelHeight, predecessorX, null, spaceToPredecessorX, spaceToPredecessorY);

        return titleLabel;
    }

    public PageElement generateIcon(Predecessor predecessorY, String filePath) {
        JLabel iconJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(filePath);

        int iconWidth = iconLogo.getIconWidth();
        int iconHeight = iconLogo.getIconHeight();

        iconJLabel.setIcon(iconLogo);

        PageElement iconLabel = new PageElement(iconJLabel, iconWidth, iconHeight, null, predecessorY, PageElement.DEFAULT_SPACE, 20);

        return iconLabel;
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

        if (filePath != "") {
            Image img = iconLogo.getImage();
            if (img != null) {
                Image newimg = img.getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH);
                iconLogo = new ImageIcon(newimg);

                iconJLabel.setIcon(iconLogo);
            }
        }

        PageElement iconLabel = new PageElement(iconJLabel, iconWidth, iconHeight, null, predecessorY, PageElement.DEFAULT_SPACE, 20);

        return iconLabel;
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
