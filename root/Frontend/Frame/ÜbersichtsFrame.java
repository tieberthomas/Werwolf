package root.Frontend.Frame;

import root.Frontend.Factories.ÜbersichtsPageElementFactory;
import root.Frontend.Factories.ÜbersichtsPageFactory;
import root.Frontend.Page.Page;
import root.Frontend.Page.PageTable;
import root.Rollen.Nebenrollen.Schatten;
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
    public JButton respawnFramesJButton;

    public Page übersichtsPage;

    public ÜbersichtsFrame(ErzählerFrame erzählerFrame) {
        WINDOW_TITLE = "Übersichts Fenster";

        this.erzählerFrame = erzählerFrame;

        pageFactory = new ÜbersichtsPageFactory(this);
        pageElementFactory = new ÜbersichtsPageElementFactory(this);

        refreshJButton = new JButton();

        this.setLocation(0,ErzählerFrame.PANEL_HEIGHT + 50);

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
            playerTable.add(label);
        }
    }

    public void refreshMainRoleTable() {
        mainRoleTable.tableElements.clear();

        mainRoleTable.add(new JLabel("Hauptrolle"));

        for(Spieler spieler : Spieler.spieler) {
            JLabel label = new JLabel(spieler.hauptrolle.getName());
            if(spieler.lebend) {
                label.setBackground(spieler.hauptrolle.getFraktion().getFarbe());
            } else {
                label.setBackground(Color.lightGray);
            }

            label.setOpaque(true);
            mainRoleTable.add(label);
        }
    }

    public void refreshSecondaryRoleTable() {
        secondaryRoleTable.tableElements.clear();

        secondaryRoleTable.add(new JLabel("Nebenrolle"));

        for(Spieler spieler : Spieler.spieler) {
            String spielerNebenrolle;
            if(spieler.nebenrolle==null)
                spielerNebenrolle = new Schatten().getName();
            else
                spielerNebenrolle = spieler.nebenrolle.getName();
            secondaryRoleTable.add(new JLabel(spielerNebenrolle));
        }
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
        if(ae.getSource() == respawnFramesJButton) {
            erzählerFrame.respawnFrames();
        }
    }
}
