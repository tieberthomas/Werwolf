package root.mechanics;

import java.util.List;
import java.util.Random;

public class Rand {
    private static Random random = new Random();

    public static <T> T getRandomElement(List<T> list) {
        int randomIndex = random.nextInt(list.size());
        return list.get(randomIndex);
    }
}
