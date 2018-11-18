package root.Frontend.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DropdownOptions {
    public List<String> strings;
    private String defaultOption;

    public DropdownOptions() {
        strings = new ArrayList<>();
        defaultOption = null;
    }

    public DropdownOptions(List<String> strings) {
        this(strings, null);
    }

    public DropdownOptions(List<String> strings, String defaultOption) {
        this.strings = strings;
        this.defaultOption = defaultOption;
        sort();
    }

    private void sort() {
        if (defaultOption == null) {
            Collections.sort(strings, String.CASE_INSENSITIVE_ORDER);
            return;
        }

        strings.remove(defaultOption);

        Collections.sort(strings, String.CASE_INSENSITIVE_ORDER);

        strings.add(defaultOption);
    }

    public void add(String string) {
        strings.add(string);
        sort();
    }
}
