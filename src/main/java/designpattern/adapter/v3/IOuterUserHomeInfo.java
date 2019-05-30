package designpattern.adapter.v3;

import java.util.Map;

/**
 * 用户家庭信息接口
 *
 * @author duosheng
 * @since 2019/5/30
 */
public interface IOuterUserHomeInfo {

    /**
     * 用户的家庭信息
     *
     * @return
     */
    public Map getUserHomeInfo();
}
