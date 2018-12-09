package root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos;

import javax.swing.*;

public class TwoDropdownPageDto {
    public String title;

    public String comboBoxName;
    public JComboBox comboBox;
    public String comboBoxName2;
    public JComboBox comboBox2;
    public JButton next;
    public JButton back;

    public TwoDropdownPageDto(String title, String comboBoxName, JComboBox comboBox, String comboBoxName2, JComboBox comboBox2, JButton next, JButton back) {
        this.title = title;
        this.comboBoxName = comboBoxName;
        this.comboBox = comboBox;
        this.comboBoxName2 = comboBoxName2;
        this.comboBox2 = comboBox2;
        this.next = next;
        this.back = back;
    }
}
