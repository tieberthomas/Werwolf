package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Frontend.Page.PageTable;
import root.Frontend.Utils.DropdownOptions;
import root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos.OneDropdownPageElementsDto;
import root.Frontend.Utils.PageRefresher.Models.Combobox;
import root.Frontend.Utils.PageRefresher.Models.DeleteButtonTable;
import root.Frontend.Utils.PageRefresher.Models.LabelTable;
import root.Frontend.Utils.PageRefresher.Models.RefreshedPage;
import root.Frontend.Utils.PageRefresher.PageRefresher;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class TortenPage extends RefreshedPage {
    private OneDropdownPageElementsDto interactiveElementsDto;

    private JButton addSpieler;
    private PageTable labelTable;
    private PageTable deleteTable;
    private List<JButton> deleteButtons = new ArrayList<>();
    private JComboBox nameComboBox;

    public static ArrayList<String> tortenesser;

    private DropdownOptions allPlayers;

    public TortenPage(DropdownOptions allPlayers) {
        tortenesser = new ArrayList<>();
        this.allPlayers = allPlayers;
        generatePage();
        setupPageRefresher();
        refresh();
    }

    @Override
    protected void setupObjects() {
        addSpieler = new JButton("Hinzufügen");
        labelTable = new PageTable();
        deleteTable = new PageTable();
        nameComboBox = new JComboBox();
    }

    @Override
    public void generatePage() {
        erzählerFrame.pageFactory.generateTortenPage(page, interactiveElementsDto, allPlayers);
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
        List<String> nichtFlackernde = new ArrayList<>(allPlayers.strings);
        nichtFlackernde.removeAll(tortenesser);
        return nichtFlackernde;
    }

    @Override
    protected void setupPageElementsDtos() {
        interactiveElementsDto = new OneDropdownPageElementsDto(addSpieler, labelTable, deleteTable, nameComboBox);
    }

    @Override
    public void processActionEvent(ActionEvent ae) {
        if (addSpieler.equals(ae.getSource())) {
            addTortenesser();
        } else if (deleteButtons.contains(ae.getSource())) {
            deleteTortenesser(ae);
        }
    }

    private void addTortenesser() {
        if (nameComboBox.getSelectedItem() != null) {
            String spielerName = nameComboBox.getSelectedItem().toString();
            tortenesser.add(spielerName);
            refresh();
        }
    }

    private void deleteTortenesser(ActionEvent ae) {
        int index = findIndex(ae);

        if (index > 0 && index < deleteButtons.size()) {
            deleteButtons.remove(index);
        }

        tortenesser.remove(index);

        refresh();
    }

    private int findIndex(ActionEvent ae) {
        int keinIrrlicht = -1;

        for (int i = 0; i < deleteButtons.size(); i++) {
            if (ae.getSource() == deleteButtons.get(i)) {
                return i;
            }
        }

        return keinIrrlicht;
    }
}
