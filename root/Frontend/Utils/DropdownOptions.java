package root.Frontend.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DropdownOptions extends ArrayList {
    private String defaultOption;

    public DropdownOptions(List<String> strings) {
        this(strings, null);
    }

    public DropdownOptions(List<String> strings, String defaultOption) {
        this.addAll(strings);
        this.defaultOption = defaultOption;
        sort();
    }

    private void sort() {
        if (defaultOption == null) {
            Collections.sort(this, String.CASE_INSENSITIVE_ORDER);
            return;
        }

        this.remove(defaultOption);

        Collections.sort(this, String.CASE_INSENSITIVE_ORDER);

        this.add(defaultOption);
    }

    public void addSorted(String string) {
        this.add(string);
        sort();
    }
}
