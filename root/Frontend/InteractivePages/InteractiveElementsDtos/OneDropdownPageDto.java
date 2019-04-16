package root.Frontend.InteractivePages.InteractiveElementsDtos;

import javax.swing.*;

public class OneDropdownPageDto {
    public String title;

    public String comboBoxName;
    public JComboBox comboBox;
    public JButton next;
    public JButton back;

    public OneDropdownPageDto(String title, String comboBoxName, JComboBox nameComboBox, JButton next, JButton back) {
        this.title = title;
        this.comboBoxName = comboBoxName;
        this.comboBox = nameComboBox;
        this.next = next;
        this.back = back;
    }
}
