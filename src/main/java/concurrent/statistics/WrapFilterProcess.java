package concurrent.statistics;

/**
 * @author duosheng
 * @since 2019/8/9
 */
public class WrapFilterProcess implements FilterProcess {
    /**
     * 处理文本
     *
     * @param msg
     * @return
     */
    @Override
    public String process(String msg) {
        msg = msg.replaceAll("\\s*", "");
        return msg;
    }
}
