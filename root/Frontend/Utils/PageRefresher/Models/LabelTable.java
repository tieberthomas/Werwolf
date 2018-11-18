package root.Frontend.Utils.PageRefresher.Models;

import root.Frontend.Page.PageTable;

import javax.swing.*;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class LabelTable implements RefreshObject {
    private PageTable table;
    private Supplier<List<String>> labelTexts;
    private boolean sorted;

    public LabelTable(PageTable table, Supplier<List<String>> labelTexts) {
        this(table, labelTexts, true);
    }

    public LabelTable(PageTable table, Supplier<List<String>> labelTexts, boolean sorted) {
        this.table = table;
        this.labelTexts = labelTexts;
        this.sorted = sorted;
    }

    @Override
    public void refresh() {
        table.tableElements.clear();
        List<String> sortedTexts = labelTexts.get();

        if (sorted) {
            Collections.sort(sortedTexts, String.CASE_INSENSITIVE_ORDER);
        }

        for (String text : sortedTexts) {
            table.add(new JLabel(text));
        }
    }
}
