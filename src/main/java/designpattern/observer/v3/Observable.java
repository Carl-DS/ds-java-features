package designpattern.observer.v3;

/**
 * 实现该接口的都是被观察者
 *
 * @author duosheng
 * @since 2019/5/15
 */
public interface Observable {
    /**
     * 增加一个观察者
     *
     * @param observer
     */
    void addObserver(Observer observer);

    /**
     * 删除一个观察者
     */
    void deleteObserver(Observer observer);

    /**
     * 既然要观察，我发生改变了他也应该有所动作，通知观察者
     */
    void notifyObservers(String context);

}
