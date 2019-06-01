package designpattern.iterator.v3;

/**
 * @author duosheng
 * @since 2019/6/1
 */
public class Client {

    /**
     * 简单地说，迭代器就类似于一个数据库中的游标，可以在一个容器内上下翻滚，遍历所
     * 有它需要查看的元素。
     *
     * @param args
     */
    public static void main(String[] args) {
        //声明出容器
        Aggregate agg = new ConcreteAggregate();
        //产生对象数据放进去
        agg.add("abc");
        agg.add("aaa");
        agg.add("1234");
        //遍历一下
        Iterator iterator = agg.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
