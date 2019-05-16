package designpattern.observer.v3;

/**
 * 所有实现该接口的都是观察者（类似李斯这
 * 样的）
 *
 * @author duosheng
 * @since 2019/5/14
 */
public interface Observer {

    //一发现别人有动静，自己也要行动起来
    void update(String context);
}
