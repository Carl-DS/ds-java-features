package designpattern.observer.v4;

/**
 * 观察者一般是一个接口，每一个实现该接口的实现类都是具体观察者
 *
 * @author duosheng
 * @since 2019/5/15
 */
public interface Observer {

    /**
     * 更新方法
     */
    void update();
}
