package designpattern.mediator.v3;

/**
 * 为什么同事类要使用构造函数注入中介者，而中介者使用getter/setter方式注入同事类
 * 呢？这是因为同事类必须有中介者，而中介者却可以只有部分同事类。
 *
 * @author duosheng
 * @since 2019/5/20
 */
public class ConcreteColleague2 extends Colleague {

    /**
     * 通过构造函数传递中介者
     *
     * @param _mediator
     */
    public ConcreteColleague2(Mediator _mediator) {
        super(_mediator);
    }

    /**
     * 自有方法 self-method
     */
    public void selfMethod2() {
        //处理自己的业务逻辑
    }

    /**
     * 依赖方法 dep-method
     */
    public void depMethod2() {
        //处理自己的业务逻辑
        //自己不能处理的业务逻辑，委托给中介者处理
        super.mediator.doSomething2();
    }
}
