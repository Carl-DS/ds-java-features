package designpattern.iterator.v3;

import java.util.Vector;

/**
 * @author duosheng
 * @since 2019/6/1
 */
public class ConcreteAggregate implements Aggregate {

    /**
     * 容纳对象的容器
     */
    private Vector vector = new Vector();

    /**
     * 是容器必然有元素的增加
     *
     * @param object
     */
    @Override
    public void add(Object object) {
        this.vector.add(object);
    }

    /**
     * 减少元素
     *
     * @param object
     */
    @Override
    public void remove(Object object) {
        this.vector.remove(object);
    }

    /**
     * 由迭代器来遍历所有的元素
     */
    @Override
    public Iterator iterator() {
        return new ConcreteIterator(this.vector);
    }
}
