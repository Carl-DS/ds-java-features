package arithmetic.lru;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 最简单的是基于 LinkedHashMap 来实现
 */
public class LRU<K, V> extends LinkedHashMap<K, V> {
    private final int CACHE_SIZE;

    /**
     * 传递进来最多能缓存多少数据
     *
     * @param cacheSize 　缓存大小
     */
    public LRU(int cacheSize) {
        // true 表示让 LinkedHashMap 按照访问顺序来进行排序，最近访问的放在头部，最老访问的放在尾部
        super((int) Math.ceil(cacheSize / 0.75) + 1, 0.75f, true);
        CACHE_SIZE = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 当 map 中的数据量大于指定的缓存个数的时候，就自动删除最老的数据
        return size() > CACHE_SIZE;
    }

    public static void main(String[] args) {
        LRU<Integer, String> lru = new LRU<>(3);
        lru.put(1, "A");
        lru.put(2, "B");
        lru.put(3, "C");
        lru.get(1);
        lru.put(4, "D");
        System.out.println(lru.keySet());
    }
}
