package designpattern.mediator.v3;

/**
 * 抽象同事类
 *
 * @author duosheng
 * @since 2019/5/20
 */
public abstract class Colleague {

    protected Mediator mediator;

    public Colleague(Mediator _mediator) {
        this.mediator = _mediator;
    }
}
