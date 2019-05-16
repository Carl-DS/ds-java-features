package designpattern.observer.v3;

import java.util.ArrayList;

/**
 * 具体的被观察者
 *
 * @author duosheng
 * @since 2019/5/14
 */
public class HanFeiZi implements Observable, IHanFeiZi {
    //定义个变长数组，存放所有的观察者
    private ArrayList<Observer> observerList = new ArrayList<>();

    //韩非子要吃饭了
    @Override
    public void haveBreakfast() {
        System.out.println("韩非子:开始吃饭了...");
        //通知所有的观察者
        this.notifyObservers("韩非子在吃饭");
    }

    //韩非子开始娱乐了
    @Override
    public void haveFun() {
        System.out.println("韩非子:开始娱乐了...");
        this.notifyObservers("韩非子在娱乐");
    }

    /**
     * 增加一个观察者
     *
     * @param observer
     */
    @Override
    public void addObserver(Observer observer) {
        this.observerList.add(observer);
    }

    /**
     * 删除一个观察者
     *
     * @param observer
     */
    @Override
    public void deleteObserver(Observer observer) {
        this.observerList.remove(observer);
    }

    /**
     * 既然要观察，我发生改变了他也应该有所动作，通知观察者
     *
     * @param context
     */
    @Override
    public void notifyObservers(String context) {
        for (Observer observer : observerList) {
            observer.update(context);
        }
    }
}
