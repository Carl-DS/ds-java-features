package designpattern.observer.v2;

/**
 * 监控者
 *
 * @author duosheng
 * @since 2019/5/14
 */
public interface ILiSi {

    //一发现别人有动静，自己也要行动起来
    void update(String context);
}
