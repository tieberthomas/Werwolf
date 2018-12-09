package root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos;

import root.Frontend.Page.PageTable;

import javax.swing.*;

public class IrrlichPageElementsDto {
    public JButton addIrrlichtButton;
    public PageTable labelTable;
    public PageTable deleteTable;
    public JComboBox nameComboBox;

    public IrrlichPageElementsDto(JButton addIrrlichtButton, PageTable labelTable, PageTable deleteTable, JComboBox nameComboBox) {
        this.addIrrlichtButton = addIrrlichtButton;
        this.labelTable = labelTable;
        this.deleteTable = deleteTable;
        this.nameComboBox = nameComboBox;
    }
}
