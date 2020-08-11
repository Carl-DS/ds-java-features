package arithmetic.lru;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRU2<K, V> extends LinkedHashMap<K, V> {
    private static final int MAX_ENTRIES = 3;

    LRU2() {
        super(MAX_ENTRIES, 0.75f, true);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > MAX_ENTRIES;
    }

    public static void main(String[] args) {
        LRU2<Integer, String> lru2 = new LRU2<>();
        lru2.put(1, "A");
        lru2.put(2, "B");
        lru2.put(3, "C");
        System.out.println(lru2.keySet());
        lru2.get(1);
        System.out.println(lru2.keySet());
        lru2.put(4, "D");
        System.out.println(lru2.keySet());
    }
}
