package designpattern.iterator.v3;

import java.util.Vector;

/**
 * 具体迭代器
 *
 * @author duosheng
 * @since 2019/6/1
 */
public class ConcreteIterator implements Iterator {
    private Vector vector = new Vector();

    /**
     * 定义当前游标
     */
    public int cursor = 0;

    public ConcreteIterator(Vector vector) {
        this.vector = vector;
    }

    /**
     * 遍历到下一个元素
     */
    @Override
    public Object next() {
        Object result;
        if (this.hasNext()) {
            result = this.vector.get(this.cursor++);
        } else {
            result = null;
        }
        return result;
    }

    /**
     * 是否已经遍历到尾部
     */
    @Override
    public boolean hasNext() {
        if (this.cursor == this.vector.size()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 删除当前指向的元素
     */
    @Override
    public boolean remove() {
        this.vector.remove(this.cursor);
        return true;
    }

}
