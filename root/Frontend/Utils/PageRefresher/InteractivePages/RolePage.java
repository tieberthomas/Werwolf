package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Frontend.Page.PageTable;
import root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos.RolePageElementsDto;
import root.Frontend.Utils.PageRefresher.Models.ButtonTable;
import root.Frontend.Utils.PageRefresher.Models.DeleteButtonTable;
import root.Frontend.Utils.PageRefresher.Models.Label;
import root.Frontend.Utils.PageRefresher.Models.LabelTable;
import root.Frontend.Utils.PageRefresher.Models.RefreshedPage;
import root.Frontend.Utils.PageRefresher.PageRefresher;
import root.Logic.Game;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class RolePage extends RefreshedPage {
    RolePageElementsDto interactiveElementsDto;

    private JLabel counterLabel;
    private PageTable roleButtonTable;
    private List<JButton> roleButtons;
    private PageTable labelTable;
    private PageTable deleteTable;
    private List<JButton> deleteButtons = new ArrayList<>();
    private JButton addAllRolesButton;
    private JButton deleteAllRolesButton;
    private JButton next;
    private JButton back;
    List<String> roles;

    @Override
    protected void setupObjects() {
        roleButtons = new ArrayList<>();
        roleButtonTable = new PageTable();
        counterLabel = new JLabel();
        labelTable = new PageTable();
        deleteTable = new PageTable();
        addAllRolesButton = new JButton();
        deleteAllRolesButton = new JButton();
        next = new JButton();
        back = new JButton();
        roles = new ArrayList<>();
    }

    @Override
    public void setupPageRefresher() {
        pageRefresher = new PageRefresher(page);
        pageRefresher.add(new ButtonTable(roleButtons, roles));
        pageRefresher.add(new LabelTable(labelTable, this::getRoles));
        pageRefresher.add(new DeleteButtonTable(deleteTable, deleteButtons, roles::size));
        pageRefresher.add(new Label(counterLabel, this::getRoleCounterLabel));
    }

    private List<String> getRoles() {
        return roles;
    }

    private String getRoleCounterLabel() {
        return getCounterLabelText(Game.game.spieler.size(), roles.size());
    }

    @Override
    public void processActionEvent(ActionEvent ae) {
        JButton source = (JButton) ae.getSource();
        if (next.equals(source)) {
            storeRollenInGame();
            next();
        } else if (back.equals(source)) {
            erzählerFrame.prevPage();
        } else {
            if (roleButtons.contains(source)) {
                addRolle(source);
            } else if (addAllRolesButton.equals(source)) {
                addAllRollen();
            } else if (deleteAllRolesButton.equals(source)) {
                deleteAllRollen();
            } else if (deleteButtons.contains(source)) {
                deleteRolle(source);
            }
            refresh();
        }
    }

    protected void next() {
        erzählerFrame.nextPage();
    }

    @Override
    protected void setupPageElementsDtos() {
        interactiveElementsDto = new RolePageElementsDto(counterLabel, roleButtonTable, roleButtons, labelTable, deleteTable,
                addAllRolesButton, deleteAllRolesButton, next, back);
    }

    private String getCounterLabelText(int numberOfSpieler, int numberOfRollen) {
        return pageFactory.pageElementFactory.generateCounterLabelString(numberOfSpieler, numberOfRollen);
    }

    public void refresh() {
        pageRefresher.refreshPage();
        if (spielerFrame != null) {
            refreshSpielerFrame();
        }
    }

    protected void refreshSpielerFrame() {
    }

    private void addRolle(JButton button) {
        roles.add(button.getText());
        Collections.sort(roles);
    }

    private void deleteRolle(JButton button) {
        int index = deleteButtons.indexOf(button);
        deleteRolle(index);
    }

    private void deleteRolle(int index) {
        deleteButtons.remove(index);
        roles.remove(index);
    }

    protected abstract void addAllRollen();

    private void deleteAllRollen() {
        int numberOfButtons = deleteButtons.size();
        for (int i = 0; i < numberOfButtons; i++) {
            deleteRolle(0);
        }
    }

    @Override
    public void showPage() {
        getRollenFromGame();
        super.showPage();
    }

    protected abstract void getRollenFromGame();

    protected abstract void storeRollenInGame();
}
