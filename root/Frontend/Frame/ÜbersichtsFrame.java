package root.Frontend.Frame;

import root.Frontend.Factories.ÜbersichtsPageFactory;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageTable;
import root.Persona.Rolle;
import root.Phases.ErsteNacht;
import root.Phases.Nacht;
import root.Phases.PhaseMode;
import root.Spieler;
import root.mechanics.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ÜbersichtsFrame extends MyFrame implements ActionListener {
    public Game game;

    private Color defaultBorderColor = Color.BLUE;

    private Color deactivatedColor = Color.RED;
    private Color shieldedColor = Color.GREEN;

    public PageTable playerTable;
    public PageTable mainRoleTable;
    public PageTable secondaryRoleTable;
    public PageTable aliveTable;
    public PageTable activeTable;
    public PageTable protectedTable;

    public JButton refreshJButton;

    private Page übersichtsPage;

    public ÜbersichtsFrame(ErzählerFrame erzählerFrame, Game game) {
        this.game = game;
        WINDOW_TITLE = "Übersichts Fenster";

        refreshJButton = new JButton();

        this.setLocation(0, erzählerFrame.frameJpanel.getHeight() + 50);

        frameJpanel = generateDefaultPanel();

        regenerateAndRefresh();

        showFrame();
    }

    public void regenerateAndRefresh() {
        ÜbersichtsPageFactory pageFactory = new ÜbersichtsPageFactory(this);
        übersichtsPage = pageFactory.generateÜbersichtsPage();
        refresh();
    }

    public void refresh() {
        refreshPlayerTable();
        refreshMainRoleTable();
        refreshSecondaryRoleTable();
        refreshAliveTable();
        refreshActiveTable();
        refreshProtectedTable();

        buildScreenFromPage(übersichtsPage);
    }

    private void refreshPlayerTable() {
        playerTable.tableElements.clear();

        playerTable.add(new JLabel("Spieler"));

        for (Spieler spieler : game.spieler) {
            JLabel label = new JLabel(spieler.name);
            if (!spieler.lebend) {
                label.setBackground(Spieler.DEAD_BACKGROUND_COLOR);
            } else {
                label.setBackground(Spieler.ALIVE_BACKGROUND_COLOR);
            }
            label.setOpaque(true);
            if ((game.phaseMode == PhaseMode.ersteNacht && ErsteNacht.playersAwake.contains(spieler)) ||
                    (game.phaseMode == PhaseMode.nacht && Nacht.playersAwake.contains(spieler))) {
                Color borderColor = defaultBorderColor;
                //hier kann die rahmen farbe geändert werden wenn notwendig
                label.setBorder(BorderFactory.createLineBorder(borderColor, 2));
            }

            if (playerTable.tableElements.size() < game.spieler.size() + 1) {
                playerTable.add(label);
            }
        }
    }

    private void refreshMainRoleTable() {
        mainRoleTable.tableElements.clear();

        mainRoleTable.add(new JLabel("Hauptrolle"));

        for (Spieler spieler : game.spieler) {
            Rolle rolle = spieler.hauptrolle;

            if (mainRoleTable.tableElements.size() < game.spieler.size() + 1) {
                mainRoleTable.add(generateColorLabel(spieler, rolle));
            }
        }
    }

    private void refreshSecondaryRoleTable() {
        secondaryRoleTable.tableElements.clear();

        secondaryRoleTable.add(new JLabel("Bonusrolle"));

        for (Spieler spieler : game.spieler) {
            Rolle rolle = spieler.bonusrolle;
            if (secondaryRoleTable.tableElements.size() < game.spieler.size() + 1) {
                secondaryRoleTable.add(generateColorLabel(spieler, rolle));
            }
        }
    }

    private JLabel generateColorLabel(Spieler spieler, Rolle rolle) {
        JLabel label = new JLabel(rolle.name);

        if (spieler.lebend) {
            Color farbe = rolle.getColor();
            if (farbe.equals(Color.BLACK)) {
                label.setForeground(Color.WHITE);
            }
            label.setBackground(farbe);
        } else {
            label.setBackground(Spieler.DEAD_BACKGROUND_COLOR);
        }

        label.setOpaque(true);

        return label;
    }

    private void refreshAliveTable() {
        aliveTable.tableElements.clear();

        aliveTable.add(new JLabel("Lebend"));

        for (Spieler spieler : game.spieler) {
            String text = "nein";
            if (spieler.lebend) {
                text = "ja";
            }

            JLabel label = new JLabel(text);
            if (spieler.lebend) {
                label.setBackground(Spieler.ALIVE_BACKGROUND_COLOR);
            } else {
                label.setBackground(Spieler.DEAD_BACKGROUND_COLOR);
            }
            label.setOpaque(true);
            aliveTable.add(label);
        }
    }

    private void refreshActiveTable() {
        activeTable.tableElements.clear();

        activeTable.add(new JLabel("Aktiv"));

        for (Spieler spieler : game.spieler) {
            String text = "nein";
            if (spieler.aktiv) {
                text = "ja";
            }

            JLabel label = new JLabel(text);
            if (!spieler.aktiv) {
                label.setBackground(deactivatedColor);
            } else {
                label.setBackground(Spieler.ALIVE_BACKGROUND_COLOR);
            }
            label.setOpaque(true);

            if (activeTable.tableElements.size() < game.spieler.size() + 1) {
                activeTable.add(label);
            }
        }
    }

    private void refreshProtectedTable() {
        protectedTable.tableElements.clear();

        protectedTable.add(new JLabel("Geschützt"));

        for (Spieler spieler : game.spieler) {
            String text = "nein";
            if (spieler.geschützt) {
                text = "ja";
            }

            JLabel label = new JLabel(text);
            if (spieler.geschützt) {
                label.setBackground(shieldedColor);
            } else {
                label.setBackground(Spieler.ALIVE_BACKGROUND_COLOR);
            }
            label.setOpaque(true);

            if (protectedTable.tableElements.size() < game.spieler.size() + 1) {
                protectedTable.add(label);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == refreshJButton) {
            refresh();
        }
    }
}
