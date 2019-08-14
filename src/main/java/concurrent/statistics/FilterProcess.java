package concurrent.statistics;

/**
 * 定义责任链的抽象接口及处理方法
 *
 * @author duosheng
 * @since 2019/8/9
 */
public interface FilterProcess {

    /**
     * 处理文本
     *
     * @param msg
     * @return
     */
    String process(String msg);
}
