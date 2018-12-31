package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Controller.FrontendObject;
import root.Frontend.Utils.DropdownOptions;
import root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos.OneDropdownPageDto;
import root.Frontend.Utils.PageRefresher.Models.InteractivePage;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class OneDropdownPage extends InteractivePage {
    private OneDropdownPageDto interactiveElementsDto;

    private String title;

    private String comboBoxName;
    JComboBox comboBox;
    public JButton next;
    private JButton back;

    private DropdownOptions dropdownOptions;

    OneDropdownPage(DropdownOptions dropdownOptions, String title, String comboBoxName) {
        this.dropdownOptions = dropdownOptions;
        this.title = title;
        this.comboBoxName = comboBoxName;
        setupPageElementsDtos();
        generatePage();
    }

    @Override
    protected void setupObjects() {
        comboBox = new JComboBox();
        next = new JButton();
        back = new JButton();
    }

    @Override
    public void generatePage() {
        erzählerFrame.pageFactory.generateOneDropdownPage(page, interactiveElementsDto, dropdownOptions);
    }

    @Override
    protected void setupPageElementsDtos() {
        interactiveElementsDto = new OneDropdownPageDto(title, comboBoxName, comboBox, next, back);
    }

    @Override
    public void processActionEvent(ActionEvent ae) {
        if (next.equals(ae.getSource())) {
            executeAction();
            FrontendObject.showDayPage();
        } else if (back.equals((ae.getSource()))) {
            FrontendObject.showDayPage();
        }
    }

    public void executeAction() {
    }
}
