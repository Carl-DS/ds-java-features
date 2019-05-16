package designpattern.observer.v4;

/**
 * @author duosheng
 * @since 2019/5/15
 */
public class ConcreteObserver implements Observer {
    /**
     * 更新方法
     */
    @Override
    public void update() {
        System.out.println("接收到信息，并进行处理！");
    }
}
