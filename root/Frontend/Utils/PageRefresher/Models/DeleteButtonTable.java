package root.Frontend.Utils.PageRefresher.Models;

import root.Frontend.Factories.ErzählerPageElementFactory;
import root.Frontend.Page.PageTable;

import javax.swing.*;
import java.util.ArrayList;

public class DeleteButtonTable extends RefreshObject{
    private PageTable table;
    private ArrayList<JButton> buttons;

    public DeleteButtonTable(PageTable table, ArrayList<JButton> buttons) {
        this.table = table;
        this.buttons = buttons;
    }

    @Override
    public void refresh() {
        table.tableElements.clear();
        buttons.clear();

        for (int i=0; i<pageRefresher.listSize; i++) {
            JButton deleteButton = ErzählerPageElementFactory.generateDeleteButton();
            table.add(deleteButton);
            buttons.add(deleteButton);
        }
    }
}
