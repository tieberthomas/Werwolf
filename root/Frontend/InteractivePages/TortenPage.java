package root.Frontend.InteractivePages;

import root.Frontend.Page.PageTable;
import root.Frontend.Utils.DropdownOptions;
import root.Frontend.InteractivePages.InteractiveElementsDtos.OneDropdownDeletePageDto;
import root.Frontend.Utils.PageRefresherFramework.Models.Combobox;
import root.Frontend.Utils.PageRefresherFramework.Models.DeleteButtonTable;
import root.Frontend.Utils.PageRefresherFramework.Models.LabelTable;
import root.Frontend.Utils.PageRefresherFramework.Models.RefreshedPage;
import root.Frontend.Utils.PageRefresherFramework.PageRefresher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class TortenPage extends RefreshedPage {
    private OneDropdownDeletePageDto interactiveElementsDto;

    private JButton addPlayer;
    private PageTable labelTable;
    private PageTable deleteTable;
    private List<JButton> deleteButtons = new ArrayList<>();
    private JComboBox nameComboBox;

    public static ArrayList<String> tortenesser;

    private DropdownOptions allPlayers;

    private static String TITLE;

    public TortenPage(DropdownOptions allPlayers) {
        TITLE = "Torte";
        tortenesser = new ArrayList<>();
        this.allPlayers = allPlayers;
        generatePage();
        setupPageRefresher();
        refresh();
    }

    @Override
    protected void setupObjects() {
        addPlayer = new JButton("Hinzufügen");
        labelTable = new PageTable();
        deleteTable = new PageTable();
        nameComboBox = new JComboBox();
    }

    @Override
    public void generatePage() {
        erzählerFrame.pageFactory.generateTortenPage(page, interactiveElementsDto, allPlayers, TITLE);
    }

    @Override
    public void setupPageRefresher() {
        pageRefresher = new PageRefresher(page);
        pageRefresher.add(new LabelTable(labelTable, TortenPage::getTortenesser));
        pageRefresher.add(new DeleteButtonTable(deleteTable, deleteButtons, TortenPage.tortenesser::size));
        pageRefresher.add(new Combobox(nameComboBox, this::getNichtTortenesser));
    }

    private static List<String> getTortenesser() {
        return tortenesser;
    }

    private List<String> getNichtTortenesser() {
        List<String> nichtFlackernde = new ArrayList<>(allPlayers);
        nichtFlackernde.removeAll(tortenesser);
        return nichtFlackernde;
    }

    @Override
    protected void setupPageElementsDtos() {
        interactiveElementsDto = new OneDropdownDeletePageDto(addPlayer, labelTable, deleteTable, nameComboBox);
    }

    @Override
    public void processActionEvent(ActionEvent ae) {
        if (addPlayer.equals(ae.getSource())) {
            addPlayer();
        } else if (deleteButtons.contains(ae.getSource())) {
            deletePlayer(ae);
        }
    }

    private void addPlayer() {
        if (nameComboBox.getSelectedItem() != null) {
            String spielerName = nameComboBox.getSelectedItem().toString();
            tortenesser.add(spielerName);
            refresh();
        }
    }

    private void deletePlayer(ActionEvent ae) {
        int index = findIndex(ae);

        if (index > 0 && index < deleteButtons.size()) {
            deleteButtons.remove(index);
        }

        tortenesser.remove(index);

        refresh();
    }

    private int findIndex(ActionEvent ae) {
        int keinTortenesserGefunden = -1;

        for (int i = 0; i < deleteButtons.size(); i++) {
            if (ae.getSource() == deleteButtons.get(i)) {
                return i;
            }
        }

        return keinTortenesserGefunden;
    }
}
