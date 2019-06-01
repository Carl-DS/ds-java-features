package designpattern.iterator.v3;

/**
 * 抽象迭代器
 *
 * @author duosheng
 * @since 2019/6/1
 */
public interface Iterator {

    /**
     * 遍历到下一个元素
     */
    Object next();

    /**
     * 是否已经遍历到尾部
     */
    boolean hasNext();

    /**
     * 删除当前指向的元素
     */
    boolean remove();
}
