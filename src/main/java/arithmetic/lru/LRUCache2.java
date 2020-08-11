package arithmetic.lru;

import java.util.LinkedHashMap;
import java.util.Set;

/**
 * 使用 Java内置类型 LinkedHashMap 实现
 */
public class LRUCache2 {
    int cap;
    LinkedHashMap<Integer, Integer> cache = new LinkedHashMap<>();

    public LRUCache2(int capacity) {
        this.cap = capacity;
    }

    public int get(int key) {
        if (!cache.containsKey(key)) {
            return -1;
        }
        // 将 key变为最近使用
        makeRecently(key);
        return cache.get(key);
    }

    public void put(int key, int val) {
        if (cache.containsKey(key)) {
            // 修改 key 的值
            cache.put(key, val);
            // 将 key 变为最近使用
            makeRecently(key);
            return;
        }

        if (cache.size() >= this.cap) {
            // 链表头部就是最久未使用的 key
            int oldestKey = cache.keySet().iterator().next();
            cache.remove(oldestKey);
        }
        // 将新的 key 添加链表尾部
        cache.put(key, val);
    }

    private void makeRecently(int key) {
        int val = cache.get(key);
        // 删除 key，重新插入队尾
        cache.remove(key);
        cache.put(key, val);
    }

    public Set<Integer> keySet() {
        return cache.keySet();
    }

    public static void main(String[] args) {
        LRUCache2 cache = new LRUCache2(3);
        cache.put(1, 1);
        cache.put(2, 2);
        cache.put(3, 3);
        System.out.println(cache.keySet());
        cache.get(1);
        System.out.println(cache.keySet());
        cache.put(4, 4);
        System.out.println(cache.keySet());
        System.out.println("finish");
    }
}
