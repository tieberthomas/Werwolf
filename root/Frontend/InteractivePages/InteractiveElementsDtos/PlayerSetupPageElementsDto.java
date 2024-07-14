package root.Frontend.InteractivePages.InteractiveElementsDtos;

import root.Frontend.Page.PageTable;

import javax.swing.*;

public class PlayerSetupPageElementsDto {
    public JButton addPlayerButton;
    public JTextField addPlayerTxtField;
    public JLabel counterLabel;
    public PageTable labelTable;
    public PageTable deleteTable;
    public JButton next;
    public JButton back;

    public PlayerSetupPageElementsDto(JButton addPlayerButton, JTextField addPlayerTxtField, JLabel counterLabel,
                                      PageTable labelTable, PageTable deleteTable, JButton next, JButton back) {
        this.addPlayerButton = addPlayerButton;
        this.addPlayerTxtField = addPlayerTxtField;
        this.counterLabel = counterLabel;
        this.labelTable = labelTable;
        this.deleteTable = deleteTable;
        this.next = next;
        this.back = back;
    }

}
