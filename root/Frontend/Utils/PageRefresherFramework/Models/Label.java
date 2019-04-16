package root.Frontend.Utils.PageRefresherFramework.Models;

import javax.swing.*;
import java.util.function.Supplier;

public class Label implements RefreshObject {
    private JLabel label;
    private Supplier<String> labelText;

    public Label(JLabel label, Supplier<String> labelText) {
        this.label = label;
        this.labelText = labelText;
    }

    @Override
    public void refresh() {
        label.setText(labelText.get());
    }
}
