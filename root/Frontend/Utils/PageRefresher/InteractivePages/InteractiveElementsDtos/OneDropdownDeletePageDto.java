package root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos;

import root.Frontend.Page.PageTable;

import javax.swing.*;

public class OneDropdownDeletePageDto {
    public JButton addPlayerButton;
    public PageTable labelTable;
    public PageTable deleteTable;
    public JComboBox nameComboBox;

    public OneDropdownDeletePageDto(JButton addPlayerButton, PageTable labelTable, PageTable deleteTable, JComboBox nameComboBox) {
        this.addPlayerButton = addPlayerButton;
        this.labelTable = labelTable;
        this.deleteTable = deleteTable;
        this.nameComboBox = nameComboBox;
    }
}
