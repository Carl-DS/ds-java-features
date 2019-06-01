package designpattern.iterator.v3;

/**
 * 抽象容器
 *
 * @author duosheng
 * @since 2019/6/1
 */
public interface Aggregate {

    /**
     * 是容器必然有元素的增加
     */
    void add(Object object);

    /**
     * 减少元素
     */
    void remove(Object object);

    /**
     * 由迭代器来遍历所有的元素
     */
    Iterator iterator();
}
