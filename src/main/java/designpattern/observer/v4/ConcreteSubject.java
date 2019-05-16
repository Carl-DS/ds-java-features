package designpattern.observer.v4;

/**
 * @author duosheng
 * @since 2019/5/15
 */
public class ConcreteSubject extends Subject {

    /**
     * 具体的业务
     */
    public void doSomething() {
        /*
         * do something
         */
        System.out.println("do something");
        super.notifyObservers();
    }
}
