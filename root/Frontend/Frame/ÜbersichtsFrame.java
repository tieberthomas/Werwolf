package root.Frontend.Frame;

import root.Frontend.Factories.ÜbersichtsPageElementFactory;
import root.Frontend.Factories.ÜbersichtsPageFactory;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageTable;
import root.Phases.ErsteNacht;
import root.Phases.Nacht;
import root.Phases.PhaseMode;
import root.Rollen.Hauptrolle;
import root.Rollen.Nebenrolle;
import root.Rollen.Rolle;
import root.Spieler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ÜbersichtsFrame extends MyFrame implements ActionListener{
    ErzählerFrame erzählerFrame;

    public ÜbersichtsPageFactory pageFactory;
    public ÜbersichtsPageElementFactory pageElementFactory;

    public PageTable playerTable;
    public PageTable mainRoleTable;
    public PageTable secondaryRoleTable;
    public PageTable aliveTable;
    public PageTable activeTable;
    public PageTable protectedTable;

    public JButton refreshJButton;

    public Page übersichtsPage;

    public ÜbersichtsFrame(ErzählerFrame erzählerFrame) {
        WINDOW_TITLE = "Übersichts Fenster";

        this.erzählerFrame = erzählerFrame;

        pageFactory = new ÜbersichtsPageFactory(this);
        pageElementFactory = new ÜbersichtsPageElementFactory(this);

        refreshJButton = new JButton();

        this.setLocation(0,erzählerFrame.frameJpanel.getHeight() + 50);

        frameJpanel = generateDefaultPanel();

        übersichtsPage = pageFactory.generateÜbersichtsPage();
        refreshÜbersichtsPage();

        showFrame();
    }

    public void refreshÜbersichtsPage() {
        refreshPlayerTable();
        refreshMainRoleTable();
        refreshSecondaryRoleTable();
        refreshAliveTable();
        refreshActiveTable();
        refreshProtectedTable();

        buildScreenFromPage(übersichtsPage);
    }

    public void refreshPlayerTable() {
        playerTable.tableElements.clear();

        playerTable.add(new JLabel("Spieler"));

        for(Spieler spieler : Spieler.spieler) {
            JLabel label = new JLabel(spieler.name);
            if(!spieler.lebend) {
                label.setBackground(Color.lightGray);
            } else {
                label.setBackground(Color.white);
            }
            label.setOpaque(true);
            if((PhaseMode.phase == PhaseMode.ersteNacht && ErsteNacht.playersAwake.contains(spieler)) ||
                (PhaseMode.phase == PhaseMode.nacht && Nacht.playersAwake.contains(spieler))) {
                label.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            }
            playerTable.add(label);
        }
    }

    public void refreshMainRoleTable() {
        mainRoleTable.tableElements.clear();

        mainRoleTable.add(new JLabel("Hauptrolle"));

        for(Spieler spieler : Spieler.spieler) {
            Rolle rolle;
            if (spieler.hauptrolle == null)
                rolle = Hauptrolle.defaultHauptrolle;
            else
                rolle = spieler.hauptrolle;

            mainRoleTable.add(generateColorLabel(spieler, rolle));
        }
    }

    public void refreshSecondaryRoleTable() {
        secondaryRoleTable.tableElements.clear();

        secondaryRoleTable.add(new JLabel("Nebenrolle"));

        for(Spieler spieler : Spieler.spieler) {
            Rolle rolle;
            if (spieler.nebenrolle == null)
                rolle = Nebenrolle.defaultNebenrolle;
            else
                rolle = spieler.nebenrolle;

            secondaryRoleTable.add(generateColorLabel(spieler, rolle));
        }
    }

    public JLabel generateColorLabel(Spieler spieler, Rolle rolle) {
        JLabel label = new JLabel(rolle.getName());

        if(spieler.lebend) {
            label.setBackground(rolle.getFarbe());
        } else {
            label.setBackground(Color.lightGray);
        }

        label.setOpaque(true);

        return label;
    }

    public void refreshAliveTable() {
        aliveTable.tableElements.clear();

        aliveTable.add(new JLabel("Lebend"));

        for(Spieler spieler : Spieler.spieler) {
            String text="nein";
            if(spieler.lebend) {
                text="ja";
            }

            JLabel label = new JLabel(text);
            if(spieler.lebend) {
                label.setBackground(Color.white);
            } else {
                label.setBackground(Color.lightGray);
            }
            label.setOpaque(true);
            aliveTable.add(label);
        }
    }

    public void refreshActiveTable() {
        activeTable.tableElements.clear();

        activeTable.add(new JLabel("Aktiv"));

        for(Spieler spieler : Spieler.spieler) {
            String text="nein";
            if(spieler.aktiv) {
                text="ja";
            }

            JLabel label = new JLabel(text);
            if(!spieler.aktiv) {
                label.setBackground(Color.red);
            } else {
                label.setBackground(Color.white);
            }
            label.setOpaque(true);
            activeTable.add(label);
        }
    }

    public void refreshProtectedTable() {
        protectedTable.tableElements.clear();

        protectedTable.add(new JLabel("Geschützt"));

        for(Spieler spieler : Spieler.spieler) {
            String text="nein";
            if(spieler.geschützt) {
                text="ja";
            }

            JLabel label = new JLabel(text);
            if(spieler.geschützt) {
                label.setBackground(Color.green);
            } else {
                label.setBackground(Color.white);
            }
            label.setOpaque(true);
            protectedTable.add(label);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == refreshJButton) {
            refreshÜbersichtsPage();
        }
    }
}
