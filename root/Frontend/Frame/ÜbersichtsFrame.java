package root.Frontend.Frame;

import root.Frontend.Factories.ÜbersichtsPageFactory;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageTable;
import root.Persona.Rolle;
import root.Phases.FirstNight;
import root.Phases.NormalNight;
import root.Phases.PhaseManager;
import root.Phases.PhaseMode;
import root.Spieler;
import root.mechanics.Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ÜbersichtsFrame extends MyFrame implements ActionListener {
    private Color defaultBorderColor = Color.BLUE;

    private Color deactivatedColor = Color.RED;
    private Color shieldedColor = Color.GREEN;

    public PageTable playerTable;
    public PageTable hauptrolleTable;
    public PageTable bonusrolleTable;
    public PageTable aliveTable;
    public PageTable activeTable;
    public PageTable protectedTable;

    public JButton refreshJButton;

    private Page übersichtsPage;

    public ÜbersichtsFrame(int spawnHeight) {
        WINDOW_TITLE = "Übersichts Fenster";

        refreshJButton = new JButton();

        this.setLocation(0, spawnHeight);

        frameJpanel = generateDefaultPanel();

        Collections.sort(Game.game.spieler, new Comparator<Spieler>() {
            @Override
            public int compare(Spieler spieler1, Spieler spieler2) {
                return spieler1.name.compareTo(spieler2.name);
            }
        });

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
        refreshHauptrolleTable();
        refreshBonusrolleTable();
        refreshAliveTable();
        refreshActiveTable();
        refreshProtectedTable();

        buildScreenFromPage(übersichtsPage);
    }

    private void refreshPlayerTable() {
        playerTable.tableElements.clear();

        playerTable.add(new JLabel("Spieler"));

        for (Spieler spieler : Game.game.spieler) {
            JLabel label = new JLabel(spieler.name);
            if (!spieler.lebend) {
                label.setBackground(Spieler.DEAD_BACKGROUND_COLOR);
            } else {
                label.setBackground(Spieler.ALIVE_BACKGROUND_COLOR);
            }
            label.setOpaque(true);
            if ((PhaseManager.phaseMode == PhaseMode.FIRST_NIGHT && FirstNight.spielerAwake.contains(spieler)) ||
                    (PhaseManager.phaseMode == PhaseMode.NORMAL_NIGHT && NormalNight.spielerAwake.contains(spieler))) {
                Color borderColor = defaultBorderColor;
                //hier kann die rahmen farbe geändert werden wenn notwendig
                label.setBorder(BorderFactory.createLineBorder(borderColor, 2));
            }

            if (playerTable.tableElements.size() < Game.game.spieler.size() + 1) {
                playerTable.add(label);
            }
        }
    }

    private void refreshHauptrolleTable() {
        hauptrolleTable.tableElements.clear();

        hauptrolleTable.add(new JLabel("Hauptrolle"));

        for (Spieler spieler : Game.game.spieler) {
            Rolle rolle = spieler.hauptrolle;

            if (hauptrolleTable.tableElements.size() < Game.game.spieler.size() + 1) {
                hauptrolleTable.add(generateColorLabel(spieler, rolle));
            }
        }
    }

    private void refreshBonusrolleTable() {
        bonusrolleTable.tableElements.clear();

        bonusrolleTable.add(new JLabel("Bonusrolle"));

        for (Spieler spieler : Game.game.spieler) {
            Rolle rolle = spieler.bonusrolle;
            if (bonusrolleTable.tableElements.size() < Game.game.spieler.size() + 1) {
                bonusrolleTable.add(generateColorLabel(spieler, rolle));
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

        for (Spieler spieler : Game.game.spieler) {
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

        for (Spieler spieler : Game.game.spieler) {
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

            if (activeTable.tableElements.size() < Game.game.spieler.size() + 1) {
                activeTable.add(label);
            }
        }
    }

    private void refreshProtectedTable() {
        protectedTable.tableElements.clear();

        protectedTable.add(new JLabel("Geschützt"));

        for (Spieler spieler : Game.game.spieler) {
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

            if (protectedTable.tableElements.size() < Game.game.spieler.size() + 1) {
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
