package root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos;

import root.Frontend.Page.PageTable;

import javax.swing.*;
import java.util.List;

public class RolePageElementsDto {
    public JLabel counterLabel;
    public PageTable roleButtonTable;
    public List<JButton> roleButtons;
    public PageTable labelTable;
    public PageTable deleteTable;
    public JButton addAllRolesButton;
    public JButton deleteAllRolesButton;
    public JButton next;
    public JButton back;

    public RolePageElementsDto(JLabel counterLabel, PageTable roleButtonTable, List<JButton> roleButtons, PageTable labelTable, PageTable deleteTable,
                               JButton addAllRolesButton, JButton deleteAllRolesButton, JButton next, JButton back) {
        this.counterLabel = counterLabel;
        this.roleButtonTable = roleButtonTable;
        this.roleButtons = roleButtons;
        this.labelTable = labelTable;
        this.deleteTable = deleteTable;
        this.addAllRolesButton = addAllRolesButton;
        this.deleteAllRolesButton = deleteAllRolesButton;
        this.next = next;
        this.back = back;
    }
}
