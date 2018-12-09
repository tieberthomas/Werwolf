package root.Frontend.Utils.PageRefresher.InteractivePages;

import root.Frontend.FrontendControl;
import root.Frontend.Utils.DropdownOptions;
import root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos.TwoDropdownPageDto;
import root.Frontend.Utils.PageRefresher.Models.InteractivePage;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class TwoDropdownPage extends InteractivePage {
    private TwoDropdownPageDto interactiveElementsDto;

    private String title;

    private String comboBoxName;
    public JComboBox comboBox1;
    private String comboBoxName2;
    public JComboBox comboBox2;
    public JButton next;
    private JButton back;

    private DropdownOptions dropdownOptions;

    TwoDropdownPage(DropdownOptions dropdownOptions, String title, String comboBoxName, String comboBoxName2) {
        this.dropdownOptions = dropdownOptions;
        this.title = title;
        this.comboBoxName = comboBoxName;
        this.comboBoxName2 = comboBoxName2;
        setupPageElementsDtos();
        generatePage();
    }

    @Override
    protected void setupObjects() {
        comboBox1 = new JComboBox();
        comboBox2 = new JComboBox();
        next = new JButton();
        back = new JButton();
    }

    @Override
    public void generatePage() {
        erzählerFrame.pageFactory.generateTwoDropdownPage(page, interactiveElementsDto, dropdownOptions);
    }

    @Override
    protected void setupPageElementsDtos() {
        interactiveElementsDto = new TwoDropdownPageDto(title, comboBoxName, comboBox1, comboBoxName2, comboBox2, next, back);
    }

    @Override
    public void processActionEvent(ActionEvent ae) {
        if (next.equals(ae.getSource())) {
            executeAction();
            FrontendControl.showDayPage();
        } else if (back.equals((ae.getSource()))) {
            FrontendControl.showDayPage();
        }
    }

    public void executeAction() {
    }
}
