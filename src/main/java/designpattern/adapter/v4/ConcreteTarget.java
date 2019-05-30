package designpattern.adapter.v4;

/**
 * @author duosheng
 * @since 2019/5/30
 */
public class ConcreteTarget implements Target {
    /**
     * 目标角色有自己的方法
     */
    @Override
    public void request() {
        System.out.println("if you need any help,pls call me!");
    }
}
