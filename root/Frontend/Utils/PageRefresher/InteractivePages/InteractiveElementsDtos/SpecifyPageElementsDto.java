package root.Frontend.Utils.PageRefresher.InteractivePages.InteractiveElementsDtos;

import root.Frontend.Page.PageTable;

import javax.swing.*;
import java.util.List;

public class SpecifyPageElementsDto {
    public PageTable playerLabelTable;
    public PageTable mainroleLabelTable;
    public PageTable bonusroleLabelTable;
    public PageTable deleteTable;
    public List<JButton> deleteButtons;
    public JComboBox comboBox1;
    public JComboBox comboBox2;
    public JComboBox comboBox3;
    public JButton next;
    public JButton back;

    public SpecifyPageElementsDto(PageTable playerLabelTable, PageTable mainroleLabelTable, PageTable bonusroleLabelTable,
                                  PageTable deleteTable, List<JButton> deleteButtons, JComboBox comboBox1, JComboBox comboBox2,
                                  JComboBox comboBox3, JButton next, JButton back) {
        this.playerLabelTable = playerLabelTable;
        this.mainroleLabelTable = mainroleLabelTable;
        this.bonusroleLabelTable = bonusroleLabelTable;
        this.deleteTable = deleteTable;
        this.deleteButtons = deleteButtons;
        this.comboBox1 = comboBox1;
        this.comboBox2 = comboBox2;
        this.comboBox3 = comboBox3;
        this.next = next;
        this.back = back;
    }
}
