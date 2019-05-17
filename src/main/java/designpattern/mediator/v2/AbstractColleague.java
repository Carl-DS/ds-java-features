package designpattern.mediator.v2;

/**
 * @author duosheng
 * @since 2019/5/17
 */
public abstract class AbstractColleague {
    protected AbstractMediator mediator;

    public AbstractColleague(AbstractMediator _mediator) {
        this.mediator = _mediator;
    }
}
