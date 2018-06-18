package root.Frontend.Factories;

import root.*;
import root.Frontend.Frame.ErzählerFrame;
import root.Frontend.Frame.ErzählerFrameMode;
import root.Frontend.Page.PageElement;
import root.Frontend.Page.PageTable;
import root.Frontend.Page.Predecessor;
import root.Phases.ErsteNacht;
import root.Phases.Nacht;
import root.Phases.Statement;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrolle;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ErzählerPageElementFactory {
    ErzählerFrame erzählerFrame;

    public ErzählerPageElementFactory(ErzählerFrame erzählerFrame) {
        this.erzählerFrame = erzählerFrame;
    }

    public PageElement generateLabel(Predecessor predecessorY, String titel) {
        JLabel titleJLabel = new JLabel(titel);

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

        int xCoord = erzählerFrame.PANEL_WIDTH / 2 - (startButtonWidth / 2);
        int yCoord = erzählerFrame.PANEL_HEIGHT / 2 - startButtonHeight;

        startButton.setCoords(xCoord, yCoord);

        return startButton;
    }

    public PageElement generateCenteredBigButton(JButton button, Predecessor predecessorY) {
        int bigButtonWidth = 300;
        int bigButtonHeight = 50;

        button.addActionListener(erzählerFrame);
        button.setBackground(erzählerFrame.DEFAULT_BUTTON_COLOR);

        PageElement bigButton = new PageElement(button, bigButtonWidth, bigButtonHeight, null, predecessorY, 0, 10);

        int xCoord = erzählerFrame.PANEL_WIDTH / 2 - (bigButtonWidth / 2);

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

        if(predecessorX==null)
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
        return generateLowestButton(button, title, right,0);
    }

    public PageElement generateLowestButton(JButton button, String title, boolean right, int howManyButtonsBefore) {
        int elementWidth = 120;
        int elementHeight = 50;
        int xSpace = 10;
        int xOffset = (elementWidth + xSpace) * howManyButtonsBefore;
        int spaceToPredecessorX = xOffset;
        int spaceToPredecessorY = erzählerFrame.PANEL_HEIGHT - elementHeight - 20;

        if(right) {
            spaceToPredecessorX = (erzählerFrame.PANEL_WIDTH - elementWidth - 20) - xOffset;
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

    public String generateNumberOfPLayersLabelTitle() {
        int numberOfPlayers = Spieler.spieler.size();
        String numberOfPlayersLabelTitle = "Spieleranzahl: " + Integer.toString(numberOfPlayers);

        return numberOfPlayersLabelTitle;
    }

    public String generateMainRoleCounterLabelTitle() {
        int numberOfMainRoles = Hauptrolle.mainRolesInGame.size();

        return generateCounterLabelTitle(numberOfMainRoles);
    }

    public String generateSecondaryRoleCounterLabelTitle() {
        int numberOfSecondaryRoles = Nebenrolle.secondaryRolesInGame.size();

        return generateCounterLabelTitle(numberOfSecondaryRoles);
    }

    public String generateCounterLabelTitle(int numberOfRoles) {
        int numberOfPlayers = Spieler.spieler.size();
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
            button.setBackground(Nebenrolle.defaultFarbe);
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

        if(erzählerFrame.mode == ErzählerFrameMode.ersteNacht){
            statements = ErsteNacht.statements;
        } else if (erzählerFrame.mode == ErzählerFrameMode.nacht) {
            statements = Nacht.statements;
        }

        for (Statement statement : statements) {
            if(statement.visible) {
                nachtPunkte.add(statement.beschreibung);
                if(/*(erzählerFrame.mode != ErzählerFrameMode.ersteNacht) && */!statement.isLebend()) {
                    if (statement.beschreibung.equals(currentStatement)) {
                        found = true;
                        nachtPunkteFarben.add(HTMLStringBuilder.blue);
                    } else {
                        nachtPunkteFarben.add(HTMLStringBuilder.gray);
                    }
                } else {
                    if (statement.beschreibung.equals(currentStatement)) {
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

        PageElement nightLabel = new PageElement(nightJLabel, 400, erzählerFrame.getHeight()+300);

        return nightLabel;
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

    public PageElement generateTitleLabel(Predecessor predecessorX, String titel) {
        int titleLabelWidth = 500;
        int titleLabelHeight = 50;
        int spaceToPredecessorY = 0;
        int spaceToPredecessorX = 20;

        JLabel titleJLabel = generateTitleJLabel(titel);

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

        int xCoord = erzählerFrame.PANEL_WIDTH/2-(imageJLabelWidth/2);

        imageLabel.setCoordX(xCoord);

        return imageLabel;
    }

    public PageElement generateCenteredImageLabel(String imagePath, int numberOfBigButtons, int bigButtonHeightPlusSpace) {
        JLabel imageJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(imagePath);

        int imageJLabelWidth = iconLogo.getIconWidth();
        int imageJLabelHeight = iconLogo.getIconHeight();

        imageJLabel.setIcon(iconLogo);

        int yOffset = erzählerFrame.PANEL_HEIGHT/2 - (imageJLabelHeight+numberOfBigButtons*bigButtonHeightPlusSpace)/2;

        PageElement imageLabel = new PageElement(imageJLabel, imageJLabelWidth, imageJLabelHeight, null, null, 0, yOffset);

        int xCoord = erzählerFrame.PANEL_WIDTH/2-(imageJLabelWidth/2);

        imageLabel.setCoordX(xCoord);

        return imageLabel;
    }

    public PageElement generateSmallIcon(Predecessor predecessorY, String filePath) {
        JLabel iconJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(filePath);

        int iconWidth = (int)(iconLogo.getIconWidth()*0.7);
        int iconHeight = (int) (iconLogo.getIconHeight()*0.7);

        if(filePath!="") {
            Image img = iconLogo.getImage();
            Image newimg = img.getScaledInstance(iconWidth, iconHeight, java.awt.Image.SCALE_SMOOTH);
            iconLogo = new ImageIcon(newimg);

            iconJLabel.setIcon(iconLogo);
        }

        PageElement iconLabel = new PageElement(iconJLabel, iconWidth, iconHeight, null, predecessorY, PageElement.DEFAULT_SPACE, 20);

        return iconLabel;
    }

    public JLabel generateTitleJLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial",Font.PLAIN, 36));

        return label;
    }

    public PageElement generateLeftCenteredLabel(JLabel label) {
        label = generateBigJLabel(label);

        int imageJLabelWidth = (int)label.getPreferredSize().getWidth();
        int imageJLabelHeight = (int)label.getPreferredSize().getHeight();

        int spaceToPredecessorY = erzählerFrame.PANEL_HEIGHT/2-imageJLabelHeight/2;

        PageElement centeredLabel = new PageElement(label, imageJLabelWidth, imageJLabelHeight, null, null, 0, spaceToPredecessorY);

        int xCoord = erzählerFrame.PANEL_WIDTH/3-(imageJLabelWidth/2);

        centeredLabel.setCoordX(xCoord);

        return centeredLabel;
    }

    public PageElement generateRightCenteredImage(String imagePath) {
        JLabel imageJLabel = new JLabel();

        ImageIcon iconLogo = new ImageIcon(imagePath);

        int imageJLabelWidth = iconLogo.getIconWidth();
        int imageJLabelHeight = iconLogo.getIconHeight();

        int spaceToPredecessorY = erzählerFrame.PANEL_HEIGHT/2-imageJLabelHeight/2;

        imageJLabel.setIcon(iconLogo);

        PageElement imageLabel = new PageElement(imageJLabel, imageJLabelWidth, imageJLabelHeight, null, null, 0, spaceToPredecessorY);

        int xCoord = (erzählerFrame.PANEL_WIDTH/3)*2-(imageJLabelWidth/2);

        imageLabel.setCoordX(xCoord);

        return imageLabel;
    }

    public JLabel generateBigJLabel(JLabel label) {
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Arial",Font.PLAIN, 36));

        return label;
    }

    public int calculateColumns(int columnWidth){
        return (int)Math.floor((double)erzählerFrame.PANEL_WIDTH/(double)columnWidth);
    }
}
