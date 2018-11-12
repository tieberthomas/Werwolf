package root.Utils;

import java.util.ArrayList;
import java.util.List;

public class ListHelper {
    public static <T> List<T> cloneList(List<T> list) {
        return (List<T>) ((ArrayList<T>) list).clone();
    }
}
