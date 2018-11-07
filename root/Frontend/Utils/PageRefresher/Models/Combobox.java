package root.Frontend.Utils.PageRefresher.Models;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class Combobox implements RefreshObject {
    private JComboBox comboBox;
    private Supplier<ArrayList<String>> comboBoxTexts;

    public Combobox(JComboBox comboBox, Supplier<ArrayList<String>> comboBoxTexts) {
        this.comboBox = comboBox;
        this.comboBoxTexts = comboBoxTexts;
    }

    @Override
    public void refresh() {
        List<String> texts = comboBoxTexts.get();
        DefaultComboBoxModel model = new DefaultComboBoxModel(texts.toArray());
        comboBox.setModel(model);
    }
}
