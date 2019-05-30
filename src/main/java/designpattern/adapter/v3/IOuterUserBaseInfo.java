package designpattern.adapter.v3;

import java.util.Map;

/**
 * 用户基本信息接口
 *
 * @author duosheng
 * @since 2019/5/30
 */
public interface IOuterUserBaseInfo {

    /**
     * 基本信息，比如名称、性别、手机号码等
     *
     * @return
     */
    public Map getUserBaseInfo();
}
