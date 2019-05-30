package designpattern.adapter.v3;

import java.util.Map;

/**
 * 用户工作信息接口
 *
 * @author duosheng
 * @since 2019/5/30
 */
public interface IOuterUserOfficeInfo {

    /**
     * 工作区域信息
     *
     * @return
     */
    public Map getUserOfficeInfo();
}
