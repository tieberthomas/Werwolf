package root.Frontend.InteractivePages;

import root.Frontend.Page.PageTable;
import root.Frontend.Utils.DropdownOptions;
import root.Frontend.InteractivePages.InteractiveElementsDtos.OneDropdownDeletePageDto;
import root.Frontend.Utils.PageRefresherFramework.Models.Combobox;
import root.Frontend.Utils.PageRefresherFramework.Models.DeleteButtonTable;
import root.Frontend.Utils.PageRefresherFramework.Models.LabelTable;
import root.Frontend.Utils.PageRefresherFramework.Models.RefreshedPage;
import root.Frontend.Utils.PageRefresherFramework.PageRefresher;
import root.Logic.Phases.Statement.Statement;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class IrrlichtPage extends RefreshedPage {
    private OneDropdownDeletePageDto interactiveElementsDto;

    private JButton addIrrlichtButton;
    private PageTable labelTable;
    private PageTable deleteTable;
    private List<JButton> deleteButtons = new ArrayList<>();
    private JComboBox nameComboBox;

    public static ArrayList<String> flackerndeIrrlichter;

    private Statement statement;
    private DropdownOptions irrlichtNames;

    public IrrlichtPage(Statement statement, DropdownOptions irrlichtNames) {
        flackerndeIrrlichter = new ArrayList<>();
        this.statement = statement;
        this.irrlichtNames = irrlichtNames;
        generatePage();
        setupPageRefresher();
        refresh();
    }

    @Override
    protected void setupObjects() {
        addIrrlichtButton = new JButton("Hinzufügen");
        labelTable = new PageTable();
        deleteTable = new PageTable();
        nameComboBox = new JComboBox();
    }

    @Override
    public void generatePage() {
        erzählerFrame.pageFactory.generateIrrlichtDropdownPage(page, interactiveElementsDto, statement, irrlichtNames);
    }

    @Override
    public void setupPageRefresher() {
        pageRefresher = new PageRefresher(page);
        pageRefresher.add(new LabelTable(labelTable, IrrlichtPage::getFlackerndeIrrlichter));
        pageRefresher.add(new DeleteButtonTable(deleteTable, deleteButtons, IrrlichtPage.flackerndeIrrlichter::size));
        pageRefresher.add(new Combobox(nameComboBox, this::getNichtFlackerndeIrrlichterStrings));
    }

    private static List<String> getFlackerndeIrrlichter() {
        return flackerndeIrrlichter;
    }

    private List<String> getNichtFlackerndeIrrlichterStrings() {
        List<String> nichtFlackernde = new ArrayList<>(irrlichtNames);
        nichtFlackernde.removeAll(flackerndeIrrlichter);
        return nichtFlackernde;
    }

    @Override
    protected void setupPageElementsDtos() {
        interactiveElementsDto = new OneDropdownDeletePageDto(addIrrlichtButton, labelTable, deleteTable, nameComboBox);
    }

    @Override
    public void processActionEvent(ActionEvent ae) {
        if (addIrrlichtButton.equals(ae.getSource())) {
            addIrrlicht();
        } else if (deleteButtons.contains(ae.getSource())) {
            deleteIrrlicht(ae);
        }
    }

    private void addIrrlicht() {
        if (nameComboBox.getSelectedItem() != null) {
            String spielerName = nameComboBox.getSelectedItem().toString();
            flackerndeIrrlichter.add(spielerName);
            refresh();
        }
    }

    private void deleteIrrlicht(ActionEvent ae) {
        int index = findIndex(ae);

        if (index > 0 && index < deleteButtons.size()) {
            deleteButtons.remove(index);
        }

        flackerndeIrrlichter.remove(index);

        refresh();
    }

    private int findIndex(ActionEvent ae) {
        int keinIrrlichtGefunden = -1;

        for (int i = 0; i < deleteButtons.size(); i++) {
            if (ae.getSource() == deleteButtons.get(i)) {
                return i;
            }
        }

        return keinIrrlichtGefunden;
    }
}
