package designpattern.observer.v4;

import java.util.Vector;

/**
 * @author duosheng
 * @since 2019/5/15
 */
public abstract class Subject {
    /**
     * 定义一个观察者数组
     */
    private Vector<Observer> obsVector = new Vector<>();

    /**
     * 增加一个观察者
     *
     * @param o
     */
    public void addObserver(Observer o) {
        this.obsVector.add(o);
    }

    /**
     * 删除一个观察者
     *
     * @param o
     */
    public void deleteObserver(Observer o) {
        this.obsVector.remove(o);
    }

    /**
     * 通知所有观察者
     */
    public void notifyObservers() {
        for (Observer observer : obsVector) {
            observer.update();
        }
    }
}
